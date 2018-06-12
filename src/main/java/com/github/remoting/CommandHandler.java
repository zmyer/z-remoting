package com.github.remoting;

import java.util.concurrent.ExecutorService;

/**
 * @author zifeng
 *
 */
public interface CommandHandler {
    void handlerCommmand(final RemotingContext ctx, final Object message);

    void registerProcessor(final CommandCode commandCodem, final RemotingProcessor<?> processor);

    void registerDefaultExecutor(final ExecutorService executorService);

    ExecutorService getDefaultExecutor();

}
