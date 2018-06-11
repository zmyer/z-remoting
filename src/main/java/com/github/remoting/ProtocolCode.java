package com.github.remoting;

import java.util.Arrays;

/**
 * @author zifeng
 *
 */
public class ProtocolCode {
    byte[] version;

    private ProtocolCode(final byte... version) {
        this.version = version;
    }

    public static ProtocolCode fromBytes(byte... version) {
        return new ProtocolCode(version);
    }

    public byte getFirstByte() {
        return version[0];
    }

    public int length() {
        return version.length;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ProtocolCode that = (ProtocolCode) obj;
        return Arrays.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(version);
    }

    @Override
    public String toString() {
        return "ProtocolVersion{" + "version=" + Arrays.toString(version) + '}';
    }
}
