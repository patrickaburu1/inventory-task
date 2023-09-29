package com.patrick.inventorymanagementtask.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author patrick on 4/8/20
 * @project shop-pos
 */

@Transactional(readOnly=true)
@Service("loggerService")
public class LoggerService implements LoggerInterface {
    final private Logger logger = LoggerFactory.getLogger(LoggerService.class);

    /**
     * When one would like to log the stack trace in a more user friendlier
     * manner
     *
     * @param ex
     */
    @Override
    public void logStackTrace(Exception ex) {
        logStackTrace(ex, new StringBuilder());
    }

    /**
     * When one would like to log the stack trace in a more user friendlier
     * manner
     *
     * @param ex
     * @param url
     */
    @Override
    public void logStackTrace(Exception ex, String url) {
        StringBuilder messageLog = new StringBuilder();

        if (null != url && !url.isEmpty())
            messageLog.append("URL: ").append(url).append("\n");
        messageLog.append("Error: ").append(ex.toString()).append("\n");

        logStackTrace(ex, messageLog);
    }

    /**
     * When one would like to log the stack trace in a more user friendlier
     * manner
     *
     * @param ex
     * @param messageLog
     */
    @Override
    public void logStackTrace(Exception ex, StringBuilder messageLog) {
        logger.error("An error occurred: ", ex);
    }

    /**
     * Return the name of this <code>Logger</code> instance.
     *
     * @return name of this logger instance
     */
    @Override
    public String getName() {
        return logger.getName();
    }

    /**
     * Is the logger instance enabled for the TRACE level?
     *
     * @return True if this Logger is enabled for the TRACE level,
     * false otherwise.
     * @since 1.4
     */
    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }


    /**
     * Log a message at the TRACE level.
     *
     * @param msg the message string to be logged
     * @since 1.4
     */
    @Override
    public void trace(String msg) {
        logger.trace(msg);
    }

    /**
     * Log a message at the TRACE level according to the specified format
     * and arguments.
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the TRACE level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for TRACE. The variants taking {@link #trace(String, Object) one} and
     * {@link #trace(String, Object, Object) two} arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     * @since 1.4
     */
    @Override
    public void trace(String format, Object... arguments) {
        logger.trace(format, arguments);
    }

    /**
     * Log an exception (throwable) at the TRACE level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     * @since 1.4
     */
    @Override
    public void trace(String msg, Throwable t) {
        logger.trace(msg, t);
    }


    /**
     * Is the logger instance enabled for the DEBUG level?
     *
     * @return True if this Logger is enabled for the DEBUG level,
     * false otherwise.
     */
    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }


    /**
     * Log a message at the DEBUG level.
     *
     * @param msg the message string to be logged
     */
    @Override
    public void debug(String msg) {
        logger.debug(msg);
    }

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the DEBUG level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for DEBUG. The variants taking
     * {@link #debug(String, Object) one} and {@link #debug(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    @Override
    public void debug(String format, Object... arguments) {
        logger.debug(format, arguments);
    }

    /**
     * Log an exception (throwable) at the DEBUG level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    @Override
    public void debug(String msg, Throwable t) {
        logger.debug(msg, t);
    }


    /**
     * Is the logger instance enabled for the INFO level?
     *
     * @return True if this Logger is enabled for the INFO level,
     * false otherwise.
     */
    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }


    /**
     * Log a message at the INFO level.
     *
     * @param msg the message string to be logged
     */
    @Override
    public void info(String msg) {
        logger.info(msg);
    }

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for INFO. The variants taking
     * {@link #info(String, Object) one} and {@link #info(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    @Override
    public void info(String format, Object... arguments) {
        logger.info(format, arguments);
    }

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    @Override
    public void info(String msg, Throwable t) {
        logger.info(msg, t);
    }


    /**
     * Is the logger instance enabled for the WARN level?
     *
     * @return True if this Logger is enabled for the WARN level,
     * false otherwise.
     */
    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    /**
     * Log a message at the WARN level.
     *
     * @param msg the message string to be logged
     */
    @Override
    public void warn(String msg) {
        logger.warn(msg);
    }


    /**
     * Log a message at the WARN level according to the specified format
     * and arguments.
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the WARN level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for WARN. The variants taking
     * {@link #warn(String, Object) one} and {@link #warn(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    @Override
    public void warn(String format, Object... arguments) {
        logger.warn(format, arguments);
    }

    /**
     * Log an exception (throwable) at the WARN level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    @Override
    public void warn(String msg, Throwable t) {
        logger.warn(msg, t);
    }


    /**
     * Is the logger instance enabled for the ERROR level?
     *
     * @return True if this Logger is enabled for the ERROR level,
     * false otherwise.
     */
    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    /**
     * Log a message at the ERROR level.
     *
     * @param msg the message string to be logged
     */
    @Override
    public void error(String msg) {
        logger.error(msg);
    }

    /**
     * Log a message at the ERROR level according to the specified format
     * and arguments.
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the ERROR level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for ERROR. The variants taking
     * {@link #error(String, Object) one} and {@link #error(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    @Override
    public void error(String format, Object... arguments) {
        logger.error(format, arguments);
    }

    /**
     * Log an exception (throwable) at the ERROR level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    @Override
    public void error(String msg, Throwable t) {
        logger.error(msg, t);
    }
}
