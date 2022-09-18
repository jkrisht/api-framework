package com.commons;

import org.slf4j.LoggerFactory;

public class Logger {

    private static org.slf4j.Logger logger;

    private Logger(org.slf4j.Logger logger) {
        Logger.logger = logger;
    }

    public static <T> Logger getLogger(Class<T> clazz) {
        org.slf4j.Logger logger = LoggerFactory.getLogger(clazz);
        return new Logger(logger);
    }

    public void warning(String message) {
        logger.warn(message);
        ReportingListener.warning(message);
    }

    public void info(String message) {
        logger.info(message);
        ReportingListener.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
        ReportingListener.info(message);
    }

    public void error(String message) {
        logger.error(message);
        ReportingListener.fail(message);
    }

    public void pass(String message) {
        logger.info(message);
        ReportingListener.pass(message);
    }

    public void trace(Throwable throwable) {
        logger.trace(throwable.getMessage());
        ReportingListener.error(throwable);
    }

    public void trace(String message, Throwable e) {
        logger.trace(message, e);
        ReportingListener.error(message, e);
    }

    public void code(String message) {
        logger.trace(message);
        ReportingListener.code(message);
    }
}
