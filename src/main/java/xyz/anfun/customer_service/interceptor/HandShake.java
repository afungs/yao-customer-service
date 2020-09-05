package xyz.anfun.customer_service.interceptor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


@Component
public class HandShake implements HandshakeInterceptor {

    private static Logger logger = LoggerFactory.getLogger(HandShake.class);

    // 建立连接前要先对token鉴权
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        logger.debug("begin handshake, url: " + request.getURI());
        Map<String, String> paramterMap = parseParameterMap(request.getURI().getQuery());
        String token = paramterMap.get("token");
        System.out.println("token " + token);
        if (token.equals("111")) {
            // 鉴权通过后，设置当前uid
            attributes.put("username", "d1");
            return true;
        } else {
            logger.debug("handshake auth failed, url :" + request.getURI());
            return  false;
        }
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception exception) {
        logger.debug("**********afterHandshake************");
    }

    private Map<String, String> parseParameterMap(String queryString) {
        Map<String, String> parameterMap = new HashMap<>();
        String[] parameters = queryString.split("&");
        for (String parameter : parameters) {
            String[] paramPair = parameter.split("=");
            if (paramPair.length == 2) {
                parameterMap.put(paramPair[0], paramPair[1]);
            }
        }
        return parameterMap;
    }
}
