package com.github.remoting.rpc;

import com.github.remoting.CommandCode;
import com.github.remoting.CommandHandler;
import com.github.remoting.RemotingContext;
import com.github.remoting.RemotingProcessor;

import java.util.concurrent.ExecutorService;

/**
 * @author zifeng
 *
 */
public class RpcCommandHandler implements CommandHandler {
    @Override
    public void handlerCommmand(final RemotingContext ctx, final Object message) {

    }

    @Override
    public void registerProcessor(final CommandCode commandCodem, final RemotingProcessor<?> processor) {

    }

    @Override
    public void registerDefaultExecutor(final ExecutorService executorService) {

    }

    @Override
    public ExecutorService getDefaultExecutor() {
        return null;
    }
}
