package com.github.remoting.rpc.protocol;

import com.github.remoting.RemotingCommand;
import com.github.remoting.ResponseStatus;
import com.github.remoting.rpc.command.ResponseCommand;
import com.github.remoting.rpc.command.RpcRequestCommand;
import com.github.remoting.rpc.command.RpcResponseCommand;
import com.github.remoting.rpc.exception.RpcServerException;

import java.net.InetSocketAddress;

/**
 * @author zifeng
 *
 */
public class RpcCommandFactory implements com.github.remoting.CommandFactory {

    @Override
    public RpcRequestCommand createRequestCommand(final Object request) {
        return new RpcRequestCommand(request);
    }

    @Override
    public RpcResponseCommand createResponse(final Object response, final RemotingCommand request) {
        RpcResponseCommand responseCommand = new RpcResponseCommand(request.getId(), response);
        if (response != null) {
            responseCommand.setResponseClass(response.getClass().getName());
        } else {
            responseCommand.setResponseClass(null);
        }
        responseCommand.setSerializer(request.getSerializer());
        responseCommand.setProtocolSwitch(request.getProtocolSwitch());
        responseCommand.setResponseStatus(ResponseStatus.SUCCESS);
        return responseCommand;
    }

    @Override
    public RpcResponseCommand createExceptionResponse(final int id, final String message) {
        return createExceptionResponse(id, null, message);
    }

    @Override
    public RpcResponseCommand createExceptionResponse(final int id, final Throwable t,
            final String message) {
        RpcResponseCommand responseCommand = null;
        RpcServerException e = null;
        if (t == null) {
            e = new RpcServerException(message);
            responseCommand = new RpcResponseCommand(id, e);
        } else {
            e = new RpcServerException(t.getClass().getName() + ":" + t.getMessage() + ".AdditionalErrMsg:" + message);
            e.setStackTrace(t.getStackTrace());
            responseCommand = new RpcResponseCommand(id, e);
        }
        responseCommand.setResponseClass(e.getClass().getName());
        responseCommand.setResponseStatus(ResponseStatus.SERVER_EXCEPTION);
        return responseCommand;
    }

    @Override
    public RpcResponseCommand createExceptionResponse(final int id, final ResponseStatus status) {
        RpcResponseCommand responseCommand = new RpcResponseCommand();
        responseCommand.setId(id);
        responseCommand.setResponseStatus(status);
        return responseCommand;
    }

    @Override
    public ResponseCommand createTimeoutResponse(final InetSocketAddress address) {
        ResponseCommand responseCommand = new ResponseCommand();
        responseCommand.setResponseStatus(ResponseStatus.TIMEOUT);
        responseCommand.setResponseTimeMillis(System.currentTimeMillis());
        responseCommand.setResponseHost(address);
        return responseCommand;
    }

    @Override
    public RemotingCommand createSendFailedResponse(final InetSocketAddress address,
            final Throwable throwable) {
        ResponseCommand responseCommand = new ResponseCommand();
        responseCommand.setResponseStatus(ResponseStatus.CLIENT_SEND_ERROR);
        responseCommand.setResponseHost(address);
        responseCommand.setResponseTimeMillis(System.currentTimeMillis());
        responseCommand.setCause(throwable);
        return responseCommand;
    }

    @Override
    public RemotingCommand createConnectionClosedResponse(final InetSocketAddress address,
            final String message) {
        ResponseCommand responseCommand = new ResponseCommand();
        responseCommand.setResponseStatus(ResponseStatus.CONNECTION_CLOSED);
        responseCommand.setResponseTimeMillis(System.currentTimeMillis());
        responseCommand.setResponseHost(address);
        return responseCommand;
    }
}
