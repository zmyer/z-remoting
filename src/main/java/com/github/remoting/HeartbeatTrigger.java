package com.github.remoting;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author zifeng
 *
 */
public interface HeartbeatTrigger {
    void heartbeatTriggered(final ChannelHandlerContext ctx);
}
