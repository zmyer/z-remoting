package com.github.remoting.rpc.command;

import com.github.remoting.CommandCode;

/**
 * @author zifeng
 *
 */
public enum RpcCommandCode implements CommandCode {
    RPC_REQUEST((short) 1),
    RPC_RESPONSE((short) 2);

    private short value;

    RpcCommandCode(short value) {
        this.value = value;
    }

    public short value() {
        return value;
    }

    public static RpcCommandCode valueOf(final short value) {
        switch (value) {
        case 1:
            return RPC_REQUEST;
        case 2:
            return RPC_RESPONSE;
        default:
            break;
        }
        throw new IllegalArgumentException("unknown rpc comand value:" + value);
    }
}
