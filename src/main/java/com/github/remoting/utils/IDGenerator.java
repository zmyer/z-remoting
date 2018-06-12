package com.github.remoting.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zifeng
 *
 */
public class IDGenerator {
    private static final AtomicInteger id = new AtomicInteger(0);

    /**
     * generate the next id
     *
     * @return
     */
    public static int nextId() {
        return id.incrementAndGet();
    }

    public static void resetId() {
        id.set(0);
    }
}
