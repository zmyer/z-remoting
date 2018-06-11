package com.github.remoting;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zifeng
 *
 */
public class InvokeContext {
    /**
     * invoke context keys of client side
     */
    public final static String CLIENT_LOCAL_IP = "remoting.client.local.ip";
    public final static String CLIENT_LOCAL_PORT = "remoting.client.local.port";
    public final static String CLIENT_REMOTE_IP = "remoting.client.remote.ip";
    public final static String CLIENT_REMOTE_PORT = "remoting.client.remote.port";
    /** time consumed during connection creating, this is a timespan */
    public final static String CLIENT_CONN_CREATETIME = "remoting.client.conn.createtime";

    /**
     * invoke context keys of server side
     */
    public final static String SERVER_LOCAL_IP = "remoting.server.local.ip";
    public final static String SERVER_LOCAL_PORT = "remoting.server.local.port";
    public final static String SERVER_REMOTE_IP = "remoting.server.remote.ip";
    public final static String SERVER_REMOTE_PORT = "remoting.server.remote.port";

    /**
     * invoke context keys of bolt client and server side
     */
    public final static String BOLT_INVOKE_REQUEST_ID = "remoting.invoke.request.id";
    /** time consumed start from the time when request arrive, to the time when request be processed, this is a timespan */
    public final static String REMOTING_PROCESS_WAIT_TIME = "remoting.invoke.wait.time";
    public final static String REMOTING_CUSTOM_SERIALIZER = "remoting.invoke.custom.serializer";
    public final static String REMOTING_CRC_SWITCH = "remoting.invoke.crc.switch";

    public final static int INITIAL_SIZE = 8;

    /** context */
    private ConcurrentHashMap<String, Object> context;

    /**
     * default construct
     */
    public InvokeContext() {
        this.context = new ConcurrentHashMap<String, Object>(INITIAL_SIZE);
    }

    /**
     * put if absent
     *
     * @param key
     * @param value
     */
    public void putIfAbsent(String key, Object value) {
        this.context.putIfAbsent(key, value);
    }

    /**
     * put
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        this.context.put(key, value);
    }

    /**
     * get
     *
     * @param key
     * @return
     */
    public <T> T get(String key) {
        return (T) this.context.get(key);
    }

    /**
     * get and use default if not found
     *
     * @param key
     * @param defaultIfNotFound
     * @param <T>
     * @return
     */
    public <T> T get(String key, T defaultIfNotFound) {
        return this.context.get(key) != null ? (T) this.context.get(key) : defaultIfNotFound;
    }

    /**
     * clear all mappings.
     */
    public void clear() {
        this.context.clear();
    }
}
