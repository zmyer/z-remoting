package com.github.remoting;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zifeng
 *
 */
public class Connection {
    private static final Logger logger = LoggerFactory.getLogger("Connection");
    private Channel mChannel;
    private final ConcurrentHashMap<Integer, InvokeFuture> mInvokeFutureConcurrentHashMap = new ConcurrentHashMap<>();

    public static final AttributeKey<Connection> CONNECTION = AttributeKey.valueOf("connection");
    public static final AttributeKey<Integer> HEARTBEAT_COUNT = AttributeKey.valueOf("heartbeatCount");
    public static final AttributeKey<Boolean> HEARTBEAT_SWITCH = AttributeKey.valueOf("heartbeatSwitch");
    public static final AttributeKey<ProtocolCode> PROTOCOL = AttributeKey.valueOf("protocol");
    public ProtocolCode mProtocolCode;
    public static final AttributeKey<Byte> VERSION = AttributeKey.valueOf("version");
    private byte version = RpcProtocolV2

}
