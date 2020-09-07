package xyz.anfun.customer_service.netty.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.lettuce.core.ScriptOutputType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import xyz.anfun.customer_service.entity.CustomerService;
import xyz.anfun.customer_service.entity.User;
import xyz.anfun.customer_service.netty.ChannelHandlerPool;
import xyz.anfun.customer_service.netty.model.ChatMessage;
import xyz.anfun.customer_service.netty.model.MessageType;
import xyz.anfun.customer_service.service.CustomerServiceService;
import xyz.anfun.customer_service.service.UserService;
import xyz.anfun.customer_service.util.*;

import java.util.*;

@Slf4j
public class NettyHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {//TextWebSocketFrame是netty用于处理websocket发来的文本对象
    // 用户名
    Long userId;
    // 用户名
    String username;
    // 用户角色
    String role;

    private static RedisTemplate<String, String> redisTemplate;
    private static PropertiesUtils propertiesUtils;
    private static CustomerServiceService customerServiceService;
    private static UserService userService;
    private CryptUtils cryptUtils;

    static {
        redisTemplate = SpringContextUtil.getBean("stringRedisTemplate");
        propertiesUtils = SpringContextUtil.getBean("propertiesUtils");
        customerServiceService = SpringContextUtil.getBean("customerServiceServiceImpl");
        userService = SpringContextUtil.getBean("userServiceImpl");
    }

    //接收到客户都发送的消息
    @Override
    public void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        try {
            ChatMessage chatMessage = JSONUtils.jsonToChatMessage(cryptUtils.decrypt(msg.text()));
            parseData(ctx, chatMessage);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("数据序列化失败");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 首次连接是FullHttpRequest，处理参数
        // 通过token进行鉴权 未通过则关闭连接
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            if (uri.contains("?")){
                String newUri=uri.substring(0,uri.indexOf("?"));
                request.setUri(newUri);
            }

            String token = HttpUtils.getQueryParams(uri)
                                    .getOrDefault("token", "")
                                    .replace(JwtTokenUtils.TOKEN_PREFIX, "");
            if (StringUtils.isEmpty(token)){
                ctx.close();
                return;
            }
            username = JwtTokenUtils.getUsername(token);
            role = JwtTokenUtils.getUserRole(token);
            ChannelHandlerPool.channelGroup.add(ctx.channel());
            ChannelHandlerPool.channelIdMap.put(username, ctx.channel().id());
            cryptUtils = new CryptUtils(username);

            if (role.equals("CUSTOMER_SERVICE")){
                // 设置接待中的人
                BoundHashOperations<String, String, Object> a = redisTemplate.boundHashOps(propertiesUtils.getRedisCustomerServicesKey());
                if (!a.hasKey(username)){
                    Map<String, ArrayList<String>> data = new HashMap<>();
                    data.put("visitors", new ArrayList<String>());
                    data.put("queue_up", new ArrayList<String>());
                    a.put(username, JSONUtils.objectToString(data));
                }
//                log.info("客服{}登陆了", username);
            }else {
//                log.info("用户{}登陆了", username);
            }

        }
        super.channelRead(ctx, msg);
    }

    //客户端建立连接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush(new TextWebSocketFrame("okaass"));
    }

    //关闭连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ChannelHandlerPool.channelGroup.remove(ctx.channel());
        ChannelHandlerPool.channelIdMap.remove(username);
    }

    //出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public void parseData(ChannelHandlerContext ctx, ChatMessage chatMessage)  {
        if (chatMessage == null){
            return;
        }

        switch (chatMessage.getType()){
            case CHAT:
                if (role.equals("CUSTOMER_SERVICE") && !StringUtils.isEmpty(chatMessage.getTo())){
                    chatMessage.setSender(username);
                    sendMessageByUser(ctx, chatMessage, chatMessage.getTo());
                    return;
                }
                String destUserName =(String) redisTemplate.boundHashOps(propertiesUtils.getRedisUsersKey())
                        .get(username);
                chatMessage.setSender(username);
                sendMessageByUser(ctx, chatMessage, destUserName);
                break;
            case CONNECTION:
                if (role.equals("CUSTOMER_SERVICE")){
                    sendMessage(ctx, new ChatMessage("客服无法进行分配", "system"));
                    return;
                }
                CustomerService cs = customerServiceService.assign(username);
                sendMessage(ctx, new ChatMessage(cs, username));
                break;
            case MESSAGE_LIST:
                if (role.equals("CUSTOMER_SERVICE")){
                    List<User> users = userService.findUsersByCustomerServiceUserName(username);
                    try {
                        sendMessage(ctx, new ChatMessage(MessageType.MESSAGE_LIST, JSONUtils.objectToString(users), "system", null));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    //给返回消息
    private void sendMessage(ChannelHandlerContext ctx, ChatMessage data) {
        try {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(cryptUtils.encrypt(JSONUtils.objectToString(data))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageByUser(ChannelHandlerContext ctx, ChatMessage data, String destUserName) {
        try {
            ChannelId destChannelId = ChannelHandlerPool.channelIdMap.get(destUserName);
            if (destChannelId == null){
                ctx.channel().writeAndFlush(new TextWebSocketFrame(cryptUtils.encrypt(JSONUtils.objectToString(new ChatMessage("客户端已下线", username)))));
                return;
            }
            ChannelHandlerPool.channelGroup.find(destChannelId)
                .writeAndFlush(new TextWebSocketFrame(cryptUtils.encrypt(JSONUtils.objectToString(data), destUserName)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    //给每个人发送消息,除发消息人外
    private void sendAllMessages(ChannelHandlerContext ctx) {
        ctx.channel().writeAndFlush(new TextWebSocketFrame("nihao"));
    }
}
