package com.github.remoting;

import java.io.Serializable;

/**
 * @author zifeng
 *
 */
public interface RemotingCommand extends Serializable {
    ProtocolCode getProtocolCode();

    CommandCode getCommandCode();

    int getId();

    InvokeContext getInvokeContext();

    byte getSerializer();

    ProtocolSwitch getProtocolSwitch();

    void serialize();

    void deserialize();

    void serializeContent(final InvokeContext invokeContext);

    void deserializeContent(final InvokeContext invokeContext);
}
