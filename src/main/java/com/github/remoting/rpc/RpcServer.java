package com.github.remoting.rpc;

import com.github.remoting.CommandCode;
import com.github.remoting.RemotingProcessor;
import com.github.remoting.RemotingServer;
import com.github.remoting.UserProcessor;
import com.github.remoting.utils.GlobalSwitch;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

/**
 * @author zifeng
 *
 */
public class RpcServer extends RemotingServer {
    private static final Logger logger = LoggerFactory.getLogger("RpcRemoting");
    private ServerBootstrap mServerBootstrap;
    private ChannelFuture mChannelFuture;
    private GlobalSwitch mGlobalSwitch = new GlobalSwitch();
    private ConnectionEventHandler

    @Override
    protected void doInit() {

    }

    @Override
    protected void doStop() {

    }

    @Override
    protected boolean doStart() {
        return false;
    }

    @Override
    protected boolean doStart(final String ip) {
        return false;
    }

    @Override
    public void registerProcessor(final byte protocolCode, final CommandCode commandCode,
            final RemotingProcessor<?> processor) {

    }

    @Override
    public void registerDefaultExecutor(final byte protocolCode, final ExecutorService executorService) {

    }

    @Override
    public void registerUserProcessor(final UserProcessor<?> userProcessor) {

    }
}
