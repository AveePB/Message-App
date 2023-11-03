package app.log;

//Java Custom
import app.Config;

//Java New Input & Output
import java.nio.file.Paths;
import java.nio.file.Files;

//Java Input & Output
import java.io.IOException;
import java.io.FileWriter;

//Java Utilities
import java.nio.file.Path;
import java.util.Date;

//Java Language
import java.lang.StringBuilder;
import java.lang.String;

/**
 * The  Logger class is used to store static
 * log functions. Each function has a "Level"
 * associated with it. All logs are saved to
 * file 'LOG_DIR' (Config.java).
 */
public class Logger {
    /**
     * Creates log message constructed from log level and message content.
     * @param lvl the log level.
     * @param msgContent the log message content.
     * @return a string (log message).
     */
    private static String createLogMsg(LogLevel lvl, String msgContent) {
        StringBuilder sb = new StringBuilder(lvl.toString()); //Log Lvl
        sb.append(" {").append(new Date()).append("}: "); //Current Date
        sb.append(msgContent).append('\n'); //Message Content

        if (Config.ARE_SPACES_BETWEEN_LOGS) sb.append('\n');

        return sb.toString();
    }

    /**
     * Creates directory where log files are stored if it doesn't exit.
     */
    private static void createLogDir() {
        Path logDirPath = Paths.get(Config.LOG_DIR);
        if (Files.exists(logDirPath)) return;

        try {
            Files.createDirectory(logDirPath);
        }
        catch (IOException ignored) { }
    }

    /**
     * Writes log messages and prints it to the console.
     * @param lvl the log level.
     * @param msgContent the log message content.
     */
    private static void log(LogLevel lvl, String msgContent) {
        String logMsg = Logger.createLogMsg(lvl, msgContent);
        System.out.print(logMsg);

        try {
            Logger.createLogDir();
            FileWriter fw = new FileWriter(Config.LOG_DIR + Config.LOG_FILE_NAME, Config.ARE_LOGS_APPENDED);
            fw.write(logMsg);
            fw.close();
        }
        catch (IOException ignored) { }
    }

    /**
     * Creates EMERGENCY log level message.
     * @param message the log message content.
     */
    public static void logEMERG(String message) {
        Logger.log(LogLevel.EMERG, message);
    }

    /**
     * Creates ALERT log level message.
     * @param message the log message content.
     */
    public static void logALERT(String message) {
        Logger.log(LogLevel.ALERT, message);
    }

    /**
     * Creates CRITICAL log level message.
     * @param message the log message content.
     */
    public static void logCRIT(String message) {
        Logger.log(LogLevel.CRIT, message);
    }

    /**
     * Creates ERROR log level message.
     * @param message the log message content.
     */
    public static void logERROR(String message) {
        Logger.log(LogLevel.ERROR, message);
    }

    /**
     * Creates WARNING log level message.
     * @param message the log message content.
     */
    public static void logWARN(String message) {
        Logger.log(LogLevel.WARN, message);
    }

    /**
     * Creates NOTICE log level message.
     * @param message the log message content.
     */
    public static void logNOTICE(String message) {
        Logger.log(LogLevel.NOTICE, message);
    }

    /**
     * Creates INFORMATIONAL log level message.
     * @param message the log message content.
     */
    public static void logINFO(String message) {
        Logger.log(LogLevel.INFO, message);
    }

    /**
     * Creates DEBUG log level message.
     * @param message the log message content.
     */
    public static void logDEBUG(String message) {
        Logger.log(LogLevel.DEBUG, message);
    }
}
