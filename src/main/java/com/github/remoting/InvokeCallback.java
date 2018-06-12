package com.github.remoting;

import java.util.concurrent.ExecutorService;

/**
 * @author zifeng
 *
 */
public interface InvokeCallback {
    void onResponse(final Object result);

    void onException(final Throwable e);

    ExecutorService getExecutor();

}
