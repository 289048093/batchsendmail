package utils;

import org.apache.log4j.FileAppender;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;


/**
 * User: Jarvis.Li(李朝)
 * Date: 14-2-12
 * Time: 下午5:18
 * To change this template use File | Settings | File Templates.
 */
public class LogUtils {
    /**
     * 私有化构造器
     */
    private LogUtils() {

    }

    /**
     * 获取InfoLogger
     *
     * @return
     */
    public static Logger getInfoLogger() {
        return Logger.getLogger("sendmailInfoLogger");
    }

    /**
     * 获取WarnLogger
     *
     * @return
     */
    public static Logger getWarnLogger() {
        return Logger.getLogger("sendmailWarnLogger");
    }

    /**
     * 获取ErrorLogger
     *
     * @return
     */
    public static Logger getErrorLogger() {
        return Logger.getLogger("sendmailErrorLogger");
    }

    /**
     * 获取DebugLogger
     *
     * @return
     */
    public static Logger getDebugLogger() {
        return Logger.getLogger("sendmailDebugLogger");
    }

    /**
     * 获取FatalLogger
     *
     * @return
     */
    public static Logger getFatalLogger() {
        return Logger.getLogger("sendmailFatalLogger");
    }

    /**
     * error log
     *
     * @param message
     */
    public static void error(String message) {
        getErrorLogger().error(message);
    }

    /**
     * error log
     *
     * @param throwable
     */
    public static void error(Throwable throwable) {
        getErrorLogger().error("", throwable);
    }

    /**
     * error log
     *
     * @param throwable
     */
    public static void error(String message, Throwable throwable) {
        getErrorLogger().error(message, throwable);
    }

    /**
     * debug log
     *
     * @param throwable
     */
    public static void debug(Throwable throwable) {
        getDebugLogger().debug("", throwable);
    }

    /**
     * debug log
     *
     * @param message
     */
    public static void debug(String message) {
        getDebugLogger().debug(message);
    }

    /**
     * debug log
     *
     * @param message
     * @param throwable
     */
    public static void debug(String message, Throwable throwable) {
        getDebugLogger().debug(message, throwable);
    }

    /**
     * info log
     *
     * @param throwable
     */
    public static void info(Throwable throwable) {
        getInfoLogger().info("", throwable);
    }

    /**
     * info log
     *
     * @param message
     */
    public static void info(String message) {
        getInfoLogger().info(message);
    }

    /**
     * info log
     *
     * @param message
     * @param throwable
     */
    public static void info(String message, Throwable throwable) {
        getInfoLogger().info(message, throwable);
    }

    /**
     * fatal log
     *
     * @param message
     */
    public static void fatal(String message) {
        getFatalLogger().fatal(message);
    }

    /**
     * fatal log
     *
     * @param throwable
     */
    public static void fatal(Throwable throwable) {
        getFatalLogger().fatal("", throwable);
    }

    /**
     * fatal log
     *
     * @param message
     * @param throwable
     */
    public static void fatal(String message, Throwable throwable) {
        getFatalLogger().fatal(message, throwable);
    }

    /**
     * warn log
     *
     * @param message
     */
    public static void warn(String message) {
        getWarnLogger().warn(message);
    }

    /**
     * warn log
     *
     * @param throwable
     */
    public static void warn(Throwable throwable) {
        getFatalLogger().warn(null, throwable);
    }

    /**
     * warn log
     *
     * @param message
     * @param throwable
     */
    public static void warn(String message, Throwable throwable) {
        getFatalLogger().warn(message, throwable);
    }

    public static boolean isDebugEnabled() {
        return Logger.getRootLogger().isDebugEnabled();
    }

    public static boolean isTraceEnabled() {
        return Logger.getRootLogger().isTraceEnabled();
    }

    public static boolean isInfoEnabled() {
        return Logger.getRootLogger().isInfoEnabled();
    }

    /**
     * 邮件发送成功
     *
     * @return
     */
    public static Logger getSuccessLogger() {
        return Logger.getLogger("sendmailSuccessLogger");
    }


    public static void success(String message) {
        getSuccessLogger().info(message);
    }

    /**
     * 邮件发送失败
     */
    public static Logger getFailureLogger() {
        return Logger.getLogger("sendmailFailureLogger");
    }

    public static void failure(String message) {
        getFailureLogger().info(message);
    }

    /**
     * 发送结果
     *
     * @return
     */
    public static Logger getResultLogger() {
        return Logger.getLogger("sendmailResultLogger");
    }

    public static void result(String message) {
        getResultLogger().info(message);
    }

    public static void clearLogs() {
        FileWriter writer = null;
        String logdir = FileUtils.getLogDir();
        try {
            writer = new FileWriter(new File(logdir, "result.log"));
            writer.write("");
            writer.close();
            writer = new FileWriter(new File(logdir, "successlist.log"));
            writer.write("");
            writer.close();
            writer = new FileWriter(new File(logdir, "failurelist.log"));
            writer.write("");
            writer.close();
        } catch (IOException e) {
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException e) {
            }
        }
    }

    public static void flushAllLogs() {
        try {
            Set<FileAppender> flushedFileAppenders = new HashSet<FileAppender>();
            Enumeration currentLoggers = LogManager.getLoggerRepository().getCurrentLoggers();
            while (currentLoggers.hasMoreElements()) {
                Object nextLogger = currentLoggers.nextElement();
                if (nextLogger instanceof Logger) {
                    Logger currentLogger = (Logger) nextLogger;
                    Enumeration allAppenders = currentLogger.getAllAppenders();
                    while (allAppenders.hasMoreElements()) {
                        Object nextElement = allAppenders.nextElement();
                        if (nextElement instanceof FileAppender) {
                            FileAppender fileAppender = (FileAppender) nextElement;
                            if (!flushedFileAppenders.contains(fileAppender) && !fileAppender.getImmediateFlush()) {
                                flushedFileAppenders.add(fileAppender);
                                //log.info("Appender "+fileAppender.getName()+" is not doing immediateFlush ");
                                fileAppender.setImmediateFlush(true);
                                currentLogger.info("FLUSH");
                                fileAppender.setImmediateFlush(false);
                            } else {
                                //log.info("fileAppender"+fileAppender.getName()+" is doing immediateFlush");
                            }
                        }
                    }
                }
            }
        } catch (RuntimeException e) {
            error("Failed flushing logs", e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        failure("test failure");
        success("test success");
        result("test result");
//        flushAllLogs();
        System.exit(0);
    }
}
