package com.github.remoting;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

/**
 * @author zifeng
 *
 */
public interface CommandEncoder {
    void encode(final ChannelHandlerContext ctx, final Serializable message, final ByteBuf out) throws Exception;
}
