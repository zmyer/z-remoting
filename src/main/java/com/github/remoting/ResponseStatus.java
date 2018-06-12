package com.github.remoting;

/**
 * @author zifeng
 *
 */
public enum ResponseStatus {
    SUCCESS,
    // Ok
    ERROR,
    // Error caught
    SERVER_EXCEPTION,
    // Exception caught
    UNKNOWN,
    // Unknown...
    SERVER_THREADPOOL_BUSY,
    // Process thread pool busy
    ERROR_COMM,
    // Error of communication
    NO_PROCESSOR,
    // No processor find
    TIMEOUT,
    // Timeout
    CLIENT_SEND_ERROR,
    // Send failed
    CODEC_EXCEPTION,
    // Exception in encode or decode
    CONNECTION_CLOSED,
    // Connection closed.
    SERVER_SERIAL_EXCEPTION,
    // server serialize exception
    SERVER_DESERIAL_EXCEPTION // server deserialize exception
    ;

    /**
     * Convert to short.
     * @return
     */
    public short getValue() {
        switch (this) {
        case SUCCESS:
            return 0x0000;
        case ERROR:
            return 0x0001;
        case SERVER_EXCEPTION:
            return 0x0002;
        case UNKNOWN:
            return 0x0003;
        case SERVER_THREADPOOL_BUSY:
            return 0x0004;
        case ERROR_COMM:
            return 0x0005;
        case NO_PROCESSOR:
            return 0x0006;
        case TIMEOUT:
            return 0x0007;
        case CLIENT_SEND_ERROR:
            return 0x0008;
        case CODEC_EXCEPTION:
            return 0x0009;
        case CONNECTION_CLOSED:
            return 0x0010;
        case SERVER_SERIAL_EXCEPTION:
            return 0x0011;
        case SERVER_DESERIAL_EXCEPTION:
            return 0x0012;
        default:
            break;
        }
        throw new IllegalArgumentException("Unknown status," + this);
    }

    /**
     * Convert to ResponseStatus.
     *
     * @param value
     * @return
     */
    public static ResponseStatus valueOf(short value) {
        switch (value) {
        case 0x0000:
            return SUCCESS;
        case 0x0001:
            return ERROR;
        case 0x0002:
            return SERVER_EXCEPTION;
        case 0x0003:
            return UNKNOWN;
        case 0x0004:
            return SERVER_THREADPOOL_BUSY;
        case 0x0005:
            return ERROR_COMM;
        case 0x0006:
            return NO_PROCESSOR;
        case 0x0007:
            return TIMEOUT;
        case 0x0008:
            return CLIENT_SEND_ERROR;
        case 0x0009:
            return CODEC_EXCEPTION;
        case 0x0010:
            return CONNECTION_CLOSED;
        case 0x0011:
            return SERVER_SERIAL_EXCEPTION;
        case 0x0012:
            return SERVER_DESERIAL_EXCEPTION;
        default:
            break;
        }
        throw new IllegalArgumentException("Unknown status value ," + value);
    }
}
