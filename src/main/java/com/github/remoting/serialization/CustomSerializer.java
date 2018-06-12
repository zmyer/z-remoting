package com.github.remoting.serialization;

import com.github.remoting.InvokeContext;
import com.github.remoting.rpc.command.RequestCommand;
import com.github.remoting.rpc.command.ResponseCommand;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

/**
 * @author zifeng
 *
 */
public interface CustomSerializer {
    <T extends RequestCommand> boolean serializeHeader(final T request, final InvokeContext invokeContext);

    <T extends ResponseCommand> boolean serializeHeader(T request);

    <T extends RequestCommand> boolean deserializeHeader(final T request);

    <T extends ResponseCommand> boolean deserializeHeader(final T request, final InvokeContext invokeContext);

    public <T extends RequestCommand> boolean serializeContent(T request,
            InvokeContext invokeContext)
            throws SerializationException;

    public <T extends ResponseCommand> boolean serializeContent(T response)
            throws SerializationException;

    public <T extends RequestCommand> boolean deserializeContent(T request)
            throws DeserializationException;

    public <T extends ResponseCommand> boolean deserializeContent(T response,
            InvokeContext invokeContext)
            throws DeserializationException;
}
