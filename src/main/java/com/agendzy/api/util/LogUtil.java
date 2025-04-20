package com.agendzy.api.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class LogUtil {

    private static final Logger LOGGER = Logger.getLogger(LogUtil.class.getName());

    private LogUtil() {
    }

    public static void logInfo(String message) {
        LOGGER.log(Level.INFO, message);
    }

    public static void logWarn(String message) {
        LOGGER.log(Level.WARNING, message);
    }

    public static void logError(String message) {
        LOGGER.log(Level.SEVERE, message);
    }

    public static void logError(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }

}
