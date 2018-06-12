package com.github.remoting;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @author zifeng
 *
 */
public interface CommandDecoder {
    void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out);
}
