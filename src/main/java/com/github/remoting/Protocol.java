package com.github.remoting;

/**
 * @author zifeng
 *
 */
public interface Protocol {
    CommandEncoder getEncoder();

    CommandDecoder getDecoder();

    HeartbeatTrigger getHeartbeatTrigger();

    CommandHandler getCommandHandler();

    CommandFactory getCommandFactory();
}
