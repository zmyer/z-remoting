package com.github.remoting;

import io.netty.util.Timeout;

import java.net.InetSocketAddress;

/**
 * @author zifeng
 *
 */
public interface InvokeFuture {
    RemotingCommand waitResponse(final long timeoutMills);

    RemotingCommand waitReponse();

    RemotingCommand createConnectionClosedResponse(InetSocketAddress responseHost);

    void putResponse(final RemotingCommand remotingCommand);

    int invokeId();

    void executeInvokeCallback();

    void tryAsyncExecuteInvokeCallbackAbnormally();

    void setCause(final Throwable cause);

    Throwable getCause();

    InvokeCallback getInvokeCallback();

    void addTimeout(final Timeout timeout);

    void cancelTimeout();

    boolean isDone();

    ClassLoader getAppClassLoader();

    byte getProtocolCode();

    void setInvokeContext(final InvokeContext invokeContext);

    InvokeContext getInvokeContext();

}
