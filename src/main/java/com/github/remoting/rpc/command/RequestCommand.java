package com.github.remoting.rpc.command;

import com.github.remoting.CommandCode;
import com.github.remoting.rpc.protocol.RpcCommand;
import com.github.remoting.rpc.protocol.RpcCommandType;

/**
 * @author zifeng
 *
 */
public class RequestCommand extends RpcCommand {

    /** For serialization  */
    private static final long serialVersionUID = -3457717009326601317L;
    /** timeout, -1 stands for no timeout */
    private int timeout = -1;

    public RequestCommand() {
        super(RpcCommandType.REQUEST);
    }

    public RequestCommand(CommandCode code) {
        super(RpcCommandType.REQUEST, code);
    }

    public RequestCommand(byte type, CommandCode code) {
        super(type, code);
    }

    public RequestCommand(byte version, byte type, CommandCode code) {
        super(version, type, code);
    }

    /**
     * Getter method for property <tt>timeout</tt>.
     *
     * @return property value of timeout
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Setter method for property <tt>timeout</tt>.
     *
     * @param timeout value to be assigned to property timeout
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
