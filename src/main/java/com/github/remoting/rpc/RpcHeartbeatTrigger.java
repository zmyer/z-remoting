package com.github.remoting.rpc;

import com.github.remoting.CommandFactory;
import com.github.remoting.Connection;
import com.github.remoting.HeartbeatTrigger;
import com.github.remoting.utils.SystemProperties;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zifeng
 *
 */
public class RpcHeartbeatTrigger implements HeartbeatTrigger {
    private static final Logger logger = LoggerFactory.getLogger("RpcRemoting");
    public static Integer maxCount = SystemProperties.tcp_idle_maxtimes();
    public static final long heartbeatTimeoutMills = 1000;
    private CommandFactory mCommandFactory;

    public RpcHeartbeatTrigger(final CommandFactory commandFactory) {
        this.mCommandFactory = commandFactory;
    }

    @Override

    public void heartbeatTriggered(final ChannelHandlerContext ctx) {
        Integer heartbeatTimes = ctx.channel().attr(Connection.HEARTBEAT_COUNT).get();
        final Connection connection = ctx.channel().attr(Connection.CONNECTION).get();
        if (heartbeatTimes >= maxCount) {
            try {
                conn.close();
                logger.error(
                        "Heartbeat failed for {} times, close the connection from client side: {} ",
                        heartbeatTimes, RemotingUtil.parseRemoteAddress(ctx.channel()));
            } catch (Exception e) {
                logger.warn("Exception caught when closing connection in HeartbeatHandler.", e);
            }
        } else {

        }
    }
}
