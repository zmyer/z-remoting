package com.github.remoting.rpc.protocol;

import com.github.remoting.Connection;
import com.github.remoting.rpc.command.RequestCommand;
import com.github.remoting.rpc.command.ResponseCommand;
import com.github.remoting.utils.CrcUtil;
import com.github.remoting.utils.ProtocolSwitch;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author zifeng
 *
 */
public class RpcCommandEncoderV2 implements com.github.remoting.CommandEncoder {
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger("RpcRemoting");

    @Override
    public void encode(ChannelHandlerContext ctx, Serializable msg, ByteBuf out) throws Exception {
        try {
            if (msg instanceof RpcCommand) {
                /*
                 * proto: magic code for protocol
                 * ver: version for protocol
                 * type: request/response/request oneway
                 * cmdcode: code for remoting command
                 * ver2:version for remoting command
                 * requestId: id of request
                 * codec: code for codec
                 * switch: function switch
                 * (req)timeout: request timeout.
                 * (resp)respStatus: response status
                 * classLen: length of request or response class name
                 * headerLen: length of header
                 * cotentLen: length of content
                 * className
                 * header
                 * content
                 * crc (optional)
                 */
                int index = out.writerIndex();
                RpcCommand cmd = (RpcCommand) msg;
                out.writeByte(RpcProtocolV2.PROTOCOL_CODE);
                Attribute<Byte> version = ctx.channel().attr(Connection.VERSION);
                byte ver = RpcProtocolV2.PROTOCOL_VERSION_1;
                if (version != null && version.get() != null) {
                    ver = version.get();
                }
                out.writeByte(ver);
                out.writeByte(cmd.getType());
                out.writeShort(((RpcCommand) msg).getCommandCode().value());
                out.writeByte(cmd.getVersion());
                out.writeInt(cmd.getId());
                out.writeByte(cmd.getSerializer());
                out.writeByte(cmd.getProtocolSwitch().toByte());
                if (cmd instanceof RequestCommand) {
                    //timeout
                    out.writeInt(((RequestCommand) cmd).getTimeout());
                }
                if (cmd instanceof ResponseCommand) {
                    //response status
                    ResponseCommand response = (ResponseCommand) cmd;
                    out.writeShort(response.getResponseStatus().getValue());
                }
                out.writeShort(cmd.getClazzLength());
                out.writeShort(cmd.getHeaderLength());
                out.writeInt(cmd.getContentLength());
                if (cmd.getClazzLength() > 0) {
                    out.writeBytes(cmd.getClazz());
                }
                if (cmd.getHeaderLength() > 0) {
                    out.writeBytes(cmd.getHeader());
                }
                if (cmd.getContentLength() > 0) {
                    out.writeBytes(cmd.getContent());
                }
                if (ver == RpcProtocolV2.PROTOCOL_VERSION_2
                        && cmd.getProtocolSwitch().isOn(ProtocolSwitch.CRC_SWITCH_INDEX)) {
                    // compute the crc32 and write to out
                    byte[] frame = new byte[out.readableBytes()];
                    out.getBytes(index, frame);
                    out.writeInt(CrcUtil.crc32(frame));
                }
            } else {
                String warnMsg = "msg type [" + msg.getClass() + "] is not subclass of RpcCommand";
                logger.warn(warnMsg);
            }
        } catch (Exception e) {
            logger.error("Exception caught!", e);
            throw e;
        }
    }
}
