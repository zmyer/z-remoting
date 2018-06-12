package com.github.remoting;

/**
 * @author zifeng
 *
 */
public enum CommonCommandCode implements CommandCode {

    HEARTBEAT(CommandCode.HEARTBEAT_VALUE);

    private short value;

    private CommonCommandCode(short value) {
        this.value = value;
    }

    public static CommonCommandCode valueOf(short value) {
        switch (value) {
        case CommandCode.HEARTBEAT_VALUE:
            return HEARTBEAT;
        default:
            break;
        }
        throw new IllegalArgumentException("Unknown Rpc command code value ," + value);
    }

    public short value() {
        return this.value;
    }
}
