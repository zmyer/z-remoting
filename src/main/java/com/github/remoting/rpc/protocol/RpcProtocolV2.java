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
public class RpcProtocolV2 implements Protocol {
    /** because the design defect, the version is neglected in RpcProtocol,
     so we design RpcProtocolV2 and add protocol version. */
    public static final byte PROTOCOL_CODE = (byte) 2;
    /** version 1, is the same with RpcProtocol */
    public static final byte PROTOCOL_VERSION_1 = (byte) 1;
    /** version 2, is the protocol version for RpcProtocolV2 */
    public static final byte PROTOCOL_VERSION_2 = (byte) 2;

    /**
     * in contrast to protocol v1,
     * one more byte is used as protocol version,
     * and another one is userd as protocol switch
     */
    private static final int REQUEST_HEADER_LEN = 22 + 2;
    private static final int RESPONSE_HEADER_LEN = 20 + 2;
    private CommandEncoder encoder;
    private CommandDecoder decoder;
    private HeartbeatTrigger heartbeatTrigger;
    private CommandHandler commandHandler;
    private CommandFactory commandFactory;

    public RpcProtocolV2() {
        this.encoder = new RpcCommandEncoderV2();
        this.decoder = new RpcCommandDecoderV2();
        this.commandFactory = new RpcCommandFactory();
        this.heartbeatTrigger = new RpcHeartbeatTrigger(this.commandFactory);
        this.commandHandler = new RpcCommandHandler(this.commandFactory);
    }

    public static int getRequestHeaderLength() {
        return RpcProtocolV2.REQUEST_HEADER_LEN;
    }

    public static int getResponseHeaderLength() {
        return RpcProtocolV2.RESPONSE_HEADER_LEN;
    }

    @Override
    public CommandEncoder getEncoder() {
        return this.encoder;
    }

    @Override
    public CommandDecoder getDecoder() {
        return this.decoder;
    }

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
