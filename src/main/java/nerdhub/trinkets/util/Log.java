package nerdhub.trinkets.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author BrockWS
 */
public class Log {

    private static Logger LOGGER = LogManager.getLogger("trinkets");

    public static void fatal(String message) {
        LOGGER.fatal(message);
    }

    public static void fatal(String message, Object... params) {
        LOGGER.fatal(message, params);
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Object... params) {
        LOGGER.error(message, params);
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void warn(String message, Object... params) {
        LOGGER.warn(message, params);
    }

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void info(String message, Object... params) {
        LOGGER.info(message, params);
    }

    public static void debug(String message) {
        LOGGER.debug(message);
    }

    public static void debug(String message, Object... params) {
        LOGGER.debug(message, params);
    }

    public static void trace(String message) {
        LOGGER.trace(message);
    }

    public static void trace(String message, Object... params) {
        LOGGER.trace(message, params);
    }
}
