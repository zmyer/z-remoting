package com.github.remoting.rpc.command;

import com.github.remoting.InvokeContext;
import com.github.remoting.serialization.CustomSerializer;
import com.github.remoting.serialization.CustomSerializerManager;
import com.github.remoting.serialization.SerializerManager;
import com.github.remoting.utils.Configs;
import com.github.remoting.utils.IDGenerator;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

import java.io.UnsupportedEncodingException;

/**
 * @author zifeng
 *
 */
public class RpcRequestCommand extends RequestCommand {
    /** For serialization  */
    private static final long serialVersionUID = -4602613826188210946L;
    private Object requestObject;
    private String requestClass;

    private CustomSerializer customSerializer;
    private Object requestHeader;

    private transient long arriveTime = -1;

    /**
     * create request command without id
     */
    public RpcRequestCommand() {
        super(RpcCommandCode.RPC_REQUEST);
    }

    /**
     * create request command with id and request object
     * @param request
     */
    public RpcRequestCommand(Object request) {
        super(RpcCommandCode.RPC_REQUEST);
        this.requestObject = request;
        this.setId(IDGenerator.nextId());
    }

    @Override
    public void serializeClazz() throws SerializationException {
        if (this.requestClass != null) {
            try {
                byte[] clz = this.requestClass.getBytes(Configs.DEFAULT_CHARSET);
                this.setClazz(clz);
            } catch (UnsupportedEncodingException e) {
                throw new SerializationException("Unsupported charset: " + Configs.DEFAULT_CHARSET,
                        e);
            }
        }
    }

    @Override
    public void deserializeClazz() throws DeserializationException {
        if (this.getClazz() != null && this.getRequestClass() == null) {
            try {
                this.setRequestClass(new String(this.getClazz(), Configs.DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e) {
                throw new DeserializationException("Unsupported charset: "
                        + Configs.DEFAULT_CHARSET, e);
            }
        }
    }

    @Override
    public void serializeHeader(InvokeContext invokeContext) throws SerializationException {
        if (this.getCustomSerializer() != null) {
            try {
                this.getCustomSerializer().serializeHeader(this, invokeContext);
            } catch (SerializationException e) {
                throw e;
            } catch (Exception e) {
                throw new SerializationException(
                        "Exception caught when serialize header of rpc request command!", e);
            }
        }
    }

    @Override
    public void deserializeHeader(InvokeContext invokeContext) throws DeserializationException {
        if (this.getHeader() != null && this.getRequestHeader() == null) {
            if (this.getCustomSerializer() != null) {
                try {
                    this.getCustomSerializer().deserializeHeader(this);
                } catch (DeserializationException e) {
                    throw e;
                } catch (Exception e) {
                    throw new DeserializationException(
                            "Exception caught when deserialize header of rpc request command!", e);
                }
            }
        }
    }

    @Override
    public void serializeContent(InvokeContext invokeContext) throws SerializationException {
        if (this.requestObject != null) {
            try {
                if (this.getCustomSerializer() != null
                        && this.getCustomSerializer().serializeContent(this, invokeContext)) {
                    return;
                } else {
                    this.setContent(SerializerManager.getSerializer(this.getSerializer())
                            .serialize(this.requestObject));
                }
            } catch (SerializationException e) {
                throw e;
            } catch (Exception e) {
                throw new SerializationException(
                        "Exception caught when serialize content of rpc request command!", e);
            }
        }
    }

    @Override
    public void deserializeContent(InvokeContext invokeContext) throws DeserializationException {
        if (this.getRequestObject() == null) {
            try {
                if (this.getCustomSerializer() != null
                        && this.getCustomSerializer().deserializeContent(this)) {
                    return;
                }
                if (this.getContent() != null) {
                    this.setRequestObject(SerializerManager.getSerializer(this.getSerializer())
                            .deserialize(this.getContent(), this.requestClass));
                }
            } catch (DeserializationException e) {
                throw e;
            } catch (Exception e) {
                throw new DeserializationException(
                        "Exception caught when deserialize content of rpc request command!", e);
            }
        }
    }

    /**
     * Getter method for property <tt>requestObject</tt>.
     *
     * @return property value of requestObject
     */
    public Object getRequestObject() {
        return requestObject;
    }

    /**
     * Setter method for property <tt>requestObject</tt>.
     *
     * @param requestObject value to be assigned to property requestObject
     */
    public void setRequestObject(Object requestObject) {
        this.requestObject = requestObject;
    }

    /**
     * Getter method for property <tt>requestHeader</tt>.
     *
     * @return property value of requestHeader
     */
    public Object getRequestHeader() {
        return requestHeader;
    }

    /**
     * Setter method for property <tt>requestHeader</tt>.
     *
     * @param requestHeader value to be assigned to property requestHeader
     */
    public void setRequestHeader(Object requestHeader) {
        this.requestHeader = requestHeader;
    }

    /**
     * Getter method for property <tt>requestClass</tt>.
     *
     * @return property value of requestClass
     */
    public String getRequestClass() {
        return requestClass;
    }

    /**
     * Setter method for property <tt>requestClass</tt>.
     *
     * @param requestClass value to be assigned to property requestClass
     */
    public void setRequestClass(String requestClass) {
        this.requestClass = requestClass;
    }

    /**
     * Getter method for property <tt>customSerializer</tt>.
     *
     * @return property value of customSerializer
     */
    public CustomSerializer getCustomSerializer() {
        if (this.customSerializer != null) {
            return customSerializer;
        }
        if (this.requestClass != null) {
            this.customSerializer = CustomSerializerManager.getCustomSerializer(this.requestClass);
        }
        if (this.customSerializer == null) {
            this.customSerializer = CustomSerializerManager.getCustomSerializer(this.getCommandCode());
        }
        return this.customSerializer;
    }

    /**
     * Getter method for property <tt>arriveTime</tt>.
     *
     * @return property value of arriveTime
     */
    public long getArriveTime() {
        return arriveTime;
    }

    /**
     * Setter method for property <tt>arriveTime</tt>.
     *
     * @param arriveTime value to be assigned to property arriveTime
     */
    public void setArriveTime(long arriveTime) {
        this.arriveTime = arriveTime;
    }

}
