package com.github.remoting.rpc.command;

import com.github.remoting.CommonCommandCode;
import com.github.remoting.ResponseStatus;

/**
 * @author zifeng
 *
 */
public class HeartbeatAckCommand extends ResponseCommand {
    private static final long serialVersionUID = 2584912495844320855L;

    public HeartbeatAckCommand() {
        super(CommonCommandCode.HEARTBEAT);
        this.setResponseStatus(ResponseStatus.SUCCESS);
    }
}
