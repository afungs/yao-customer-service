package xyz.anfun.customer_service.netty;

import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// Channel线程池
public class ChannelHandlerPool {
    public static ConcurrentHashMap<String, ChannelId> channelIdMap = new ConcurrentHashMap<>();

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
