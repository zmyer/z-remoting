package com.github.remoting.rpc.protocol;

import com.github.remoting.CommandDecoder;
import com.github.remoting.CommandEncoder;
import com.github.remoting.CommandFactory;
import com.github.remoting.CommandHandler;
import com.github.remoting.HeartbeatTrigger;
import com.github.remoting.Protocol;
import com.github.remoting.rpc.RpcCommandHandler;
import com.github.remoting.rpc.RpcHeartbeatTrigger;

/**
 * @author zifeng
 *
 */
public class RpcProtocol implements Protocol {
    public static final byte PROTOCOL_CODE = (byte) 1;
    private static final int REQUEST_HEADER_LEN = 22;
    private static final int RESPONSE_HEADER_LEN = 20;
    private CommandEncoder encoder;
    private CommandDecoder decoder;
    private HeartbeatTrigger heartbeatTrigger;
    private CommandHandler commandHandler;
    private CommandFactory commandFactory;

    public RpcProtocol() {
        this.encoder = new RpcCommandEncoder();
        this.decoder = new RpcCommandDecoder();
        this.commandFactory = new RpcCommandFactory();
        this.heartbeatTrigger = new RpcHeartbeatTrigger(this.commandFactory);
        this.commandHandler = new RpcCommandHandler(this.commandFactory);
    }

    /**
     * Get the length of request header.
     *
     * @return
     */
    public static int getRequestHeaderLength() {
        return RpcProtocol.REQUEST_HEADER_LEN;
    }

    /**
     * Get the length of response header.
     *
     * @return
     */
    public static int getResponseHeaderLength() {
        return RpcProtocol.RESPONSE_HEADER_LEN;
    }

    // TODO: 2018/4/25 by zmyer
    @Override
    public CommandEncoder getEncoder() {
        return this.encoder;
    }

    @Override
    public CommandDecoder getDecoder() {
        return this.decoder;
    }

    // TODO: 2018/4/23 by zmyer
    @Override
    public HeartbeatTrigger getHeartbeatTrigger() {
        return this.heartbeatTrigger;
    }

    @Override
    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    @Override
    public CommandFactory getCommandFactory() {
        return this.commandFactory;
    }
}
