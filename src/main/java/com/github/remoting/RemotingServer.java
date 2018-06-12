package com.github.remoting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zifeng
 *
 */
public abstract class RemotingServer {
    private static final Logger logger = LoggerFactory.getLogger(RemotingServer.class);
    protected int port;
    private AtomicBoolean inited = new AtomicBoolean(false);
    private AtomicBoolean started = new AtomicBoolean(false);

    public RemotingServer(int port) {
        this.port = port;
    }

    public boolean start() {
        this.init();
        if (started.compareAndSet(false, true)) {
            try {
                logger.warn("Server started on:" + port);
                return this.doStart();
            } catch (Throwable e) {
                started.set(false);
                logger.error("ERROR: Failed to start the server");
                return false;
            }
        } else {
            logger.error("ERROR: The Server has already started");
            return false;
        }
    }

    public boolean start(final String ip) {
        this.init();
        if (started.compareAndSet(false, true)) {
            try {
                logger.warn("Server started on" + ip + ":" + port);
                return this.doStart(ip);
            } catch (Throwable e) {
                started.set(false);
                logger.error("ERROR: Failed to start the server");
                return false;
            }
        } else {
            logger.error("ERROR: The Server has already started");
            return false;
        }
    }

    public void stop() {
        if (started.compareAndSet(true, false)) {
            this.doStop();
        } else {
            throw new IllegalStateException("ERROR: The Server has already stopped");
        }
    }

    protected void init() {
        if (inited.compareAndSet(false, true)) {
            logger.warn("Initialize the server");
            this.doInit();
        } else {
            logger.warn("Server has been inited already");
        }
    }

    protected abstract void doInit();

    protected abstract void doStop();

    protected abstract boolean doStart();

    protected abstract boolean doStart(final String ip);

    public abstract void registerProcessor(final byte protocolCode, final CommandCode commandCode,
            final RemotingProcessor<?> processor);

    public abstract void registerDefaultExecutor(final byte protocolCode, final ExecutorService executorService);

    public abstract void registerUserProcessor(final UserProcessor<?> userProcessor);

}
