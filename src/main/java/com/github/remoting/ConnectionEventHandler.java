package com.github.remoting;

import io.netty.channel.ChannelDuplexHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zifeng
 *
 */
public class ConnectionEventHandler extends ChannelDuplexHandler {
    private static final Logger logger = LoggerFactory.getLogger("ConnectionEvent");
    private ConnectionManager mConnectionManager;
    private ConnectionEventListener mConnectionEventListener;
    private ConnectionEventExecutor mConnectionEventExecutor;

}
