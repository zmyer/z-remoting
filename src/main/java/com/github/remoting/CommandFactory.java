package com.github.remoting;

import java.net.InetSocketAddress;

/**
 * @author zifeng
 *
 */
public interface CommandFactory {
    <T extends RemotingCommand> T createRequestCommand(final Object request);

    <T extends RemotingCommand> T createResponse(final Object response, final RemotingCommand request);

    <T extends RemotingCommand> T createExceptionResponse(final int id, final String message);

    <T extends RemotingCommand> T createExceptionResponse(final int id, final Throwable t, final String message);

    <T extends RemotingCommand> T createExceptionResponse(final int id, final ResponseStatus status);

    <T extends RemotingCommand> T createTimeoutResponse(final InetSocketAddress address);

    <T extends RemotingCommand> T createSendFailedResponse(final InetSocketAddress address, Throwable throwable);

    <T extends RemotingCommand> T createConnectionClosedResponse(final InetSocketAddress address, final String message);


}
