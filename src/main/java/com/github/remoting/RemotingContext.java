package com.github.remoting;

import com.github.remoting.rpc.protocol.RpcCommandType;
import com.github.remoting.utils.StringUtils;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zifeng
 *
 */
public class RemotingContext {
    private ChannelHandlerContext mChannelHandlerContext;
    private boolean serverSide = false;
    private boolean timeoutDiscard = true;
    private long arriveTimestamp;
    private int timeout;
    private RpcCommandType rpcCommandType;
    private ConcurrentHashMap<String, UserProcessor<?>> mUserProcessorConcurrentHashMap;
    private InvokeContext mInvokeContext;

    public RemotingContext(final ChannelHandlerContext ctx) {
        this.mChannelHandlerContext = ctx;
    }

    public RemotingContext(final ChannelHandlerContext ctx, final boolean serverSide) {
        this.mChannelHandlerContext = ctx;
        this.serverSide = serverSide;
    }

    public RemotingContext(final ChannelHandlerContext ctx, final boolean serverSide, final ConcurrentHashMap<String,
            UserProcessor<?>> userProcessors) {
        this.mChannelHandlerContext = ctx;
        this.serverSide = serverSide;
        this.mUserProcessorConcurrentHashMap = userProcessors;
    }

    public RemotingContext(final ChannelHandlerContext ctx, final InvokeContext invokeContext, final boolean
            serverSide, final ConcurrentHashMap<String, UserProcessor<?>> userProcessors) {
        this.mChannelHandlerContext = ctx;
        this.serverSide = serverSide;
        this.mUserProcessorConcurrentHashMap = userProcessors;
        this.mInvokeContext = invokeContext;
    }

    public ChannelFuture writeAndFlush(final RemotingCommand command) {
        return mChannelHandlerContext.writeAndFlush(command);
    }

    public boolean isRequestTimeout() {
        if (this.timeout > 0 && (this.rpcCommandType != RpcCommandType.REQUEST_ONEWAY) && (System.currentTimeMillis()
                - this.arriveTimestamp) > timeout) {
            return true;
        }
        return false;
    }

    public boolean isServerSide() {
        return serverSide;
    }

    public UserProcessor<?> getUserProcessor(final String className) {
        return StringUtils.isBlank(className) ? null : this.mUserProcessorConcurrentHashMap.get(className);
    }

    public Connection
}


