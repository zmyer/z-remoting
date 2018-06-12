package com.github.remoting.rpc.command;

import com.github.remoting.CommonCommandCode;
import com.github.remoting.rpc.protocol.RpcCommand;
import com.github.remoting.utils.IDGenerator;

/**
 * @author zifeng
 *
 */
public class HeartbeatCommand extends RequestCommand {
    private static final long serialVersionUID = -1;
    public HeartbeatCommand() {
        super(CommonCommandCode.HEARTBEAT);
        this.setId(IDGenerator.nextId());
    }
}
