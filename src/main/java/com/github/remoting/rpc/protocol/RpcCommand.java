package com.github.remoting.rpc.protocol;

import com.github.remoting.CommandCode;
import com.github.remoting.InvokeContext;
import com.github.remoting.ProtocolCode;
import com.github.remoting.RemotingCommand;
import com.github.remoting.utils.ProtocolSwitch;
import com.github.remoting.utils.SystemProperties;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import com.sun.xml.internal.ws.encoding.soap.SerializationException;

/**
 * @author zifeng
 *
 */
public class RpcCommand implements RemotingCommand {

    /** For serialization  */
    private static final long serialVersionUID = -3570261012462596503L;

    /**
     * Code which stands for the command.
     */
    private CommandCode cmdCode;
    /** command version */
    private byte version = 0x1;
    private byte type;
    /**
     * Serializer, see the Configs.SERIALIZER_DEFAULT for the default serializer.
     * Notice: this can not be changed after initialized at runtime.
     */
    private byte serializer = SystemProperties.serializer;
    /**
     * protocol switches
     */
    private ProtocolSwitch protocolSwitch = new ProtocolSwitch();
    private int id;
    /** The length of clazz */
    private short clazzLength = 0;
    private short headerLength = 0;
    private int contentLength = 0;
    /** The class of content */
    private byte[] clazz;
    /** Header is used for transparent transmission. */
    private byte[] header;
    /** The bytes format of the content of the command. */
    private byte[] content;
    /** invoke context of each rpc command. */
    private InvokeContext invokeContext;

    public RpcCommand() {
    }

    public RpcCommand(byte type) {
        this();
        this.type = type;
    }

    public RpcCommand(CommandCode cmdCode) {
        this();
        this.cmdCode = cmdCode;
    }

    public RpcCommand(byte type, CommandCode cmdCode) {
        this(cmdCode);
        this.type = type;
    }

    public RpcCommand(byte version, byte type, CommandCode cmdCode) {
        this(type, cmdCode);
        this.version = version;
    }

    /**
     * Serialize  the class header and content.
     *
     * @throws Exception
     */
    @Override
    public void serialize() throws SerializationException {
        this.serializeClazz();
        this.serializeHeader(this.invokeContext);
        this.serializeContent(this.invokeContext);
    }

    /**
     * Deserialize the class header and content.
     *
     * @throws Exception
     */
    @Override
    public void deserialize() throws DeserializationException {
        this.deserializeClazz();
        this.deserializeHeader(this.invokeContext);
        this.deserializeContent(this.invokeContext);
    }

    public void deserialize(long mask) throws DeserializationException {
        if (mask <= RpcDeserializeLevel.DESERIALIZE_CLAZZ) {
            this.deserializeClazz();
        } else if (mask <= RpcDeserializeLevel.DESERIALIZE_HEADER) {
            this.deserializeClazz();
            this.deserializeHeader(this.getInvokeContext());
        } else if (mask <= RpcDeserializeLevel.DESERIALIZE_ALL) {
            this.deserialize();
        }
    }

    /**
     * Serialize content class.
     *
     * @throws Exception
     */
    public void serializeClazz() throws SerializationException {

    }

    /**
     * Deserialize the content class.
     *
     * @throws Exception
     */
    public void deserializeClazz() throws DeserializationException {

    }

    /**
     * Serialize the header.
     *
     * @throws Exception
     */
    public void serializeHeader(InvokeContext invokeContext) throws SerializationException {
    }

    /**
     * Serialize the content.
     *
     * @throws Exception
     */
    @Override
    public void serializeContent(InvokeContext invokeContext) throws SerializationException {
    }

    /**
     * Deserialize the header.
     *
     * @throws Exception
     */
    public void deserializeHeader(InvokeContext invokeContext) throws DeserializationException {
    }

    /**
     * Deserialize the content.
     *
     * @throws Exception
     */
    @Override
    public void deserializeContent(InvokeContext invokeContext) throws DeserializationException {
    }

    @Override
    public ProtocolCode getProtocolCode() {
        return ProtocolCode.fromBytes(RpcProtocol.PROTOCOL_CODE);
    }

    @Override
    public CommandCode getCommandCode() {
        return cmdCode;
    }

    @Override
    public InvokeContext getInvokeContext() {
        return invokeContext;
    }

    @Override
    public byte getSerializer() {
        return serializer;
    }

    @Override
    public ProtocolSwitch getProtocolSwitch() {
        return protocolSwitch;
    }

    public void setCmdCode(CommandCode cmdCode) {
        this.cmdCode = cmdCode;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setSerializer(byte serializer) {
        this.serializer = serializer;
    }

    public void setProtocolSwitch(ProtocolSwitch protocolSwitch) {
        this.protocolSwitch = protocolSwitch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getHeader() {
        return header;
    }

    public void setHeader(byte[] header) {
        if (header != null) {
            this.header = header;
            this.headerLength = (short) header.length;
        }
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        if (content != null) {
            this.content = content;
            this.contentLength = content.length;
        }
    }

    public short getHeaderLength() {
        return headerLength;
    }

    public int getContentLength() {
        return contentLength;
    }

    public short getClazzLength() {
        return clazzLength;
    }

    public byte[] getClazz() {
        return clazz;
    }

    public void setClazz(byte[] clazz) {
        if (clazz != null) {
            this.clazz = clazz;
            this.clazzLength = (short) clazz.length;
        }
    }

    public void setInvokeContext(InvokeContext invokeContext) {
        this.invokeContext = invokeContext;
    }

}
