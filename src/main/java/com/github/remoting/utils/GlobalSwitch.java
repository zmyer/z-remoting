package com.github.remoting.utils;

import java.util.BitSet;

/**
 * @author zifeng
 *
 */
public class GlobalSwitch implements Switch {
    public static final int CONN_RECONNECT_SWITCH = 0;
    public static final int CONN_MONITER_SWITCH = 1;
    public static final int SERVER_MANAGE_CONNECTION_SWITCH = 2;
    public static final int SERVER_SYNC_STOP = 3;

    private static BitSet systemSettings = new BitSet();
    private BitSet userSettings = new BitSet();

    static {
        init();
    }

    private static void init() {
        if (SystemProperties.conn_reconnect_switch()) {
            systemSettings.set(CONN_RECONNECT_SWITCH);
        } else {
            systemSettings.clear(CONN_RECONNECT_SWITCH);
        }
        if (SystemProperties.conn_monitor_switch()) {
            systemSettings.set(CONN_MONITER_SWITCH);
        } else {
            systemSettings.clear(CONN_MONITER_SWITCH);
        }
    }

    @Override
    public void turnOn(final int index) {
        userSettings.set(index);
    }

    @Override
    public boolean isOn(final int index) {
        return systemSettings.get(index) || userSettings.get(index);
    }
}
