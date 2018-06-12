package com.github.remoting.rpc.protocol;

import com.github.remoting.CommandCode;
import com.github.remoting.ResponseStatus;
import com.github.remoting.rpc.command.HeartbeatAckCommand;
import com.github.remoting.rpc.command.HeartbeatCommand;
import com.github.remoting.rpc.command.RequestCommand;
import com.github.remoting.rpc.command.ResponseCommand;
import com.github.remoting.rpc.command.RpcCommandCode;
import com.github.remoting.rpc.command.RpcRequestCommand;
import com.github.remoting.rpc.command.RpcResponseCommand;
import com.github.remoting.utils.CrcUtil;
import com.github.remoting.utils.ProtocolSwitch;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author zifeng
 *
 */
public class RpcCommandDecoderV2 implements com.github.remoting.CommandDecoder {
    private static final Logger logger = LoggerFactory.getLogger("RpcRemoting");

    private int lessLen;

    {
        lessLen = RpcProtocolV2.getResponseHeaderLength() < RpcProtocolV2.getRequestHeaderLength() ? RpcProtocolV2
                .getResponseHeaderLength() : RpcProtocolV2.getRequestHeaderLength();
    }

    @Override
    public void decode(final ChannelHandlerContext ctx, final ByteBuf in, final List<Object> out) {
        // the less length between response header and request header
        if (in.readableBytes() >= lessLen) {
            in.markReaderIndex();
            byte protocol = in.readByte();
            in.resetReaderIndex();
            if (protocol == RpcProtocolV2.PROTOCOL_CODE) {
                /*
                 * ver: version for protocol
                 * type: request/response/request oneway
                 * cmdcode: code for remoting command
                 * ver2:version for remoting command
                 * requestId: id of request
                 * codec: code for codec
                 * switch: function switch
                 * (req)timeout: request timeout
                 * (resp)respStatus: response status
                 * classLen: length of request or response class name
                 * headerLen: length of header
                 * contentLen: length of content
                 * className
                 * header
                 * content
                 */
                if (in.readableBytes() > 2 + 1) {
                    int startIndex = in.readerIndex();
                    in.markReaderIndex();
                    in.readByte(); //protocol code
                    byte version = in.readByte(); //protocol version
                    byte type = in.readByte(); //type
                    if (type == RpcCommandType.REQUEST || type == RpcCommandType.REQUEST_ONEWAY) {
                        //decode request
                        if (in.readableBytes() >= RpcProtocolV2.getRequestHeaderLength() - 3) {
                            short cmdCode = in.readShort();
                            byte ver2 = in.readByte();
                            int requestId = in.readInt();
                            byte serializer = in.readByte();
                            byte protocolSwitchValue = in.readByte();
                            int timeout = in.readInt();
                            short classLen = in.readShort();
                            short headerLen = in.readShort();
                            int contentLen = in.readInt();
                            byte[] clazz = null;
                            byte[] header = null;
                            byte[] content = null;

                            // decide the at-least bytes length for each version
                            int lengthAtLeastForV1 = classLen + headerLen + contentLen;
                            boolean crcSwitchOn = ProtocolSwitch.isOn(
                                    ProtocolSwitch.CRC_SWITCH_INDEX, protocolSwitchValue);
                            int lengthAtLeastForV2 = classLen + headerLen + contentLen;
                            if (crcSwitchOn) {
                                lengthAtLeastForV2 += 4;// crc int
                            }

                            // continue read
                            if ((version == RpcProtocolV2.PROTOCOL_VERSION_1 &&
                                    in.readableBytes() >= lengthAtLeastForV1)
                                    || (version == RpcProtocolV2.PROTOCOL_VERSION_2 && in
                                    .readableBytes() >= lengthAtLeastForV2)) {
                                if (classLen > 0) {
                                    clazz = new byte[classLen];
                                    in.readBytes(clazz);
                                }
                                if (headerLen > 0) {
                                    header = new byte[headerLen];
                                    in.readBytes(header);
                                }
                                if (contentLen > 0) {
                                    content = new byte[contentLen];
                                    in.readBytes(content);
                                }
                                if (version == RpcProtocolV2.PROTOCOL_VERSION_2 && crcSwitchOn) {
                                    checkCRC(in, startIndex);
                                }
                            } else {// not enough data
                                in.resetReaderIndex();
                                return;
                            }
                            RequestCommand command;
                            if (cmdCode == CommandCode.HEARTBEAT_VALUE) {
                                command = new HeartbeatCommand();
                            } else {
                                command = createRequestCommand(cmdCode);
                            }
                            command.setType(type);
                            command.setVersion(ver2);
                            command.setId(requestId);
                            command.setSerializer(serializer);
                            command.setProtocolSwitch(ProtocolSwitch.create(protocolSwitchValue));
                            command.setTimeout(timeout);
                            command.setClazz(clazz);
                            command.setHeader(header);
                            command.setContent(content);

                            out.add(command);

                        } else {
                            in.resetReaderIndex();
                            return;
                        }
                    } else if (type == RpcCommandType.RESPONSE) {
                        //decode response
                        if (in.readableBytes() >= RpcProtocolV2.getResponseHeaderLength() - 3) {
                            short cmdCode = in.readShort();
                            byte ver2 = in.readByte();
                            int requestId = in.readInt();
                            byte serializer = in.readByte();
                            byte protocolSwitchValue = in.readByte();
                            short status = in.readShort();
                            short classLen = in.readShort();
                            short headerLen = in.readShort();
                            int contentLen = in.readInt();
                            byte[] clazz = null;
                            byte[] header = null;
                            byte[] content = null;

                            // decide the at-least bytes length for each version
                            int lengthAtLeastForV1 = classLen + headerLen + contentLen;
                            boolean crcSwitchOn = ProtocolSwitch.isOn(
                                    ProtocolSwitch.CRC_SWITCH_INDEX, protocolSwitchValue);
                            int lengthAtLeastForV2 = classLen + headerLen + contentLen;
                            if (crcSwitchOn) {
                                lengthAtLeastForV2 += 4;// crc int
                            }

                            // continue read
                            if ((version == RpcProtocolV2.PROTOCOL_VERSION_1 &&
                                    in.readableBytes() >= lengthAtLeastForV1)
                                    || (version == RpcProtocolV2.PROTOCOL_VERSION_2 && in
                                    .readableBytes() >= lengthAtLeastForV2)) {
                                if (classLen > 0) {
                                    clazz = new byte[classLen];
                                    in.readBytes(clazz);
                                }
                                if (headerLen > 0) {
                                    header = new byte[headerLen];
                                    in.readBytes(header);
                                }
                                if (contentLen > 0) {
                                    content = new byte[contentLen];
                                    in.readBytes(content);
                                }
                                if (version == RpcProtocolV2.PROTOCOL_VERSION_2 && crcSwitchOn) {
                                    checkCRC(in, startIndex);
                                }
                            } else {// not enough data
                                in.resetReaderIndex();
                                return;
                            }
                            ResponseCommand command;
                            if (cmdCode == CommandCode.HEARTBEAT_VALUE) {
                                command = new HeartbeatAckCommand();
                            } else {
                                command = createResponseCommand(cmdCode);
                            }
                            command.setType(type);
                            command.setVersion(ver2);
                            command.setId(requestId);
                            command.setSerializer(serializer);
                            command.setProtocolSwitch(ProtocolSwitch.create(protocolSwitchValue));
                            command.setResponseStatus(ResponseStatus.valueOf(status));
                            command.setClazz(clazz);
                            command.setHeader(header);
                            command.setContent(content);
                            command.setResponseTimeMillis(System.currentTimeMillis());
                            command.setResponseHost((InetSocketAddress) ctx.channel()
                                    .remoteAddress());

                            out.add(command);
                        } else {
                            in.resetReaderIndex();
                            return;
                        }
                    } else {
                        String emsg = "Unknown command type: " + type;
                        logger.error(emsg);
                        throw new RuntimeException(emsg);
                    }
                }

            } else {
                String emsg = "Unknown protocol: " + protocol;
                logger.error(emsg);
                throw new RuntimeException(emsg);
            }
        }
    }

    private void checkCRC(ByteBuf in, int startIndex) {
        int endIndex = in.readerIndex();
        int expectedCrc = in.readInt();
        byte[] frame = new byte[endIndex - startIndex];
        in.getBytes(startIndex, frame, 0, endIndex - startIndex);
        int actualCrc = CrcUtil.crc32(frame);
        if (expectedCrc != actualCrc) {
            String err = "CRC check failed!";
            logger.error(err);
            throw new RuntimeException(err);
        }
    }

    private ResponseCommand createResponseCommand(short cmdCode) {
        ResponseCommand command = new RpcResponseCommand();
        command.setCmdCode(RpcCommandCode.valueOf(cmdCode));
        return command;
    }

    private RpcRequestCommand createRequestCommand(short cmdCode) {
        RpcRequestCommand command = new RpcRequestCommand();
        command.setCmdCode(RpcCommandCode.valueOf(cmdCode));
        command.setArriveTime(System.currentTimeMillis());
        return command;
    }
}
