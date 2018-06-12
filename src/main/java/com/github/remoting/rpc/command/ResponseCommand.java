package com.github.remoting.rpc.command;

import com.github.remoting.CommandCode;
import com.github.remoting.ResponseStatus;
import com.github.remoting.rpc.protocol.RpcCommand;
import com.github.remoting.rpc.protocol.RpcCommandType;

import java.net.InetSocketAddress;

/**
 * @author zifeng
 *
 */
public class ResponseCommand extends RpcCommand {

    /** For serialization  */
    private static final long serialVersionUID = -5194754228565292441L;
    private ResponseStatus responseStatus;
    private long responseTimeMillis;
    private InetSocketAddress responseHost;
    private Throwable cause;

    public ResponseCommand() {
        super(RpcCommandType.RESPONSE);
    }

    public ResponseCommand(CommandCode code) {
        super(RpcCommandType.RESPONSE, code);
    }

    public ResponseCommand(int id) {
        super(RpcCommandType.RESPONSE);
        this.setId(id);
    }

    public ResponseCommand(CommandCode code, int id) {
        super(RpcCommandType.RESPONSE, code);
        this.setId(id);
    }

    public ResponseCommand(byte version, byte type, CommandCode code, int id) {
        super(version, type, code);
        this.setId(id);
    }

    /**
     * Getter method for property <tt>responseTimeMillis</tt>.
     *
     * @return property value of responseTimeMillis
     */
    public long getResponseTimeMillis() {
        return responseTimeMillis;
    }

    /**
     * Setter method for property <tt>responseTimeMillis</tt>.
     *
     * @param responseTimeMillis value to be assigned to property responseTimeMillis
     */
    public void setResponseTimeMillis(long responseTimeMillis) {
        this.responseTimeMillis = responseTimeMillis;
    }

    /**
     * Getter method for property <tt>responseHost</tt>.
     *
     * @return property value of responseHost
     */
    public InetSocketAddress getResponseHost() {
        return responseHost;
    }

    /**
     * Setter method for property <tt>responseHost</tt>.
     *
     * @param responseHost value to be assigned to property responseHost
     */
    public void setResponseHost(InetSocketAddress responseHost) {
        this.responseHost = responseHost;
    }

    /**
     * Getter method for property <tt>responseStatus</tt>.
     *
     * @return property value of responseStatus
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Setter method for property <tt>responseStatus</tt>.
     *
     * @param responseStatus value to be assigned to property responseStatus
     */
    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * Getter method for property <tt>cause</tt>.
     *
     * @return property value of cause
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * Setter method for property <tt>cause</tt>.
     *
     * @param cause value to be assigned to property cause
     */
    public void setCause(Throwable cause) {
        this.cause = cause;
    }

}
