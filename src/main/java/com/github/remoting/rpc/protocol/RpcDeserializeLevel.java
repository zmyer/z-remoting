package com.github.remoting.rpc.protocol;

/**
 * @author zifeng
 *
 */
public class RpcDeserializeLevel {
    /** deserialize clazz, header, contents all three parts of rpc command */
    public final static int DESERIALIZE_ALL = 0x02;
    /** deserialize both header and clazz parts of rpc command */
    public final static int DESERIALIZE_HEADER = 0x01;
    /** deserialize only the clazz part of rpc command */
    public final static int DESERIALIZE_CLAZZ = 0x00;

    /**
     * Convert to String.
     *
     * @param value
     * @return
     */
    public static String valueOf(int value) {
        switch (value) {
        case 0x00:
            return "DESERIALIZE_CLAZZ";
        case 0x01:
            return "DESERIALIZE_HEADER";
        case 0x02:
            return "DESERIALIZE_ALL";
        default:
            break;
        }
        throw new IllegalArgumentException("Unknown deserialize level value ," + value);
    }
}
