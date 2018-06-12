package com.github.remoting.rpc.protocol;

/**
 * @author zifeng
 *
 */
public class RpcCommandType {
    /** rpc response */
    public static final byte RESPONSE = (byte) 0x00;
    /** rpc request */
    public static final byte REQUEST = (byte) 0x01;
    /** rpc oneway request */
    public static final byte REQUEST_ONEWAY = (byte) 0x02;
}
