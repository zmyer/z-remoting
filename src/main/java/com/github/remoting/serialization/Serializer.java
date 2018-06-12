package com.github.remoting.serialization;

/**
 * @author zifeng
 *
 */
public interface Serializer {
    byte[] serialize(final Object obj);

    <T> T deserialize(final byte[] data, final String classOfT);
}
