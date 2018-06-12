package com.github.remoting.exception;

/**
 * @author zifeng
 *
 */
public class RemotingException extends Exception {

    /** For serialization */
    private static final long serialVersionUID = 6183635628271812505L;

    /**
     * Constructor.
     */
    public RemotingException() {

    }

    /**
     * Constructor.
     */
    public RemotingException(String message) {
        super(message);
    }

    /**
     * Constructor.
     */
    public RemotingException(String message, Throwable cause) {
        super(message, cause);
    }


}
