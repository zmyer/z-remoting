package com.github.remoting;

import java.util.concurrent.ExecutorService;

/**
 * @author zifeng
 *
 */
public interface RemotingProcessor<T extends RemotingCommand> {
    void process(final RemotingContext ctx, final T message, final ExecutorService defaultExecutor);

    ExecutorService getExecutor();

    void setExecutor(final ExecutorService executor);
}
