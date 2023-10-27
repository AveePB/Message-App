package app.log;

//Java I/O (Input and Output)
import java.io.IOException;
import java.io.FileWriter;

//Java New I/O (New Input and Output)
import java.nio.file.Paths;
import java.nio.file.Files;

//Java Utilities (Popular Classes)
import java.nio.file.Path;
import java.util.Date;

//Java Language (Fundamental Classes)
import java.lang.StringBuilder;
import java.lang.String;

//Java Custom Packages
import app.Settings;


public class Logger {
    private String outputDir;

    /**
     * Constructs a logger object.
     * @param fileName the name of file where logs are written to.
     */
    public Logger(String fileName) {
        this.outputDir = Settings.LOGS_DIR + fileName;
    }

    /**
     * Creates log message constructed from log level and message content.
     * @param lvl the log level.
     * @param msgContent the log message content.
     * @return a string (log message).
     */
    private String createLogMsg(LogLvl lvl, String msgContent) {
        StringBuilder sb = new StringBuilder(lvl.toString()); //Log Lvl
        sb.append(" (").append(new Date()).append(") "); //Current Date
        sb.append(msgContent).append('\n'); //Message Content
        
        return sb.toString();
    }

    /**
     * Creates directory where log files are stored if it doesn't exit.
     */
    private void createLogDir() {
        Path logPath = Paths.get(Settings.LOGS_DIR);
        if (Files.exists(logPath)) return;

        try {
            Files.createDirectory(logPath);
        }
        catch (IOException ignored) { }
    }

    /**
     * Writes log messages and prints it to the console.
     * @param lvl the log level.
     * @param msgContent the log message content.
     */
    private void log(LogLvl lvl, String msgContent) {
        String logMsg = createLogMsg(lvl, msgContent);
        System.out.print(logMsg);

        try {
            createLogDir();
            FileWriter fw = new FileWriter(this.outputDir, Settings.LOGS_APPENDED);
            fw.write(logMsg);
            fw.close();
        }
        catch (IOException ignored) { }
    }

    /**
     * Creates Fatal log level message.
     * @param msgContent the log message content.
     */
    public void logFatal(String msgContent) {
        log(LogLvl.FATAL, msgContent);
    }

    /**
     * Creates Error log level message.
     * @param msgContent the log message content.
     */
    public void logError(String msgContent) {
        log(LogLvl.ERROR, msgContent);
    }

    /**
     * Creates Warn log level message.
     * @param msgContent the log message content.
     */
    public void logWarn(String msgContent) {
        log(LogLvl.WARN, msgContent);
    }

    /**
     * Creates Info log level message.
     * @param msgContent the log message content.
     */
    public void logInfo(String msgContent) {
        log(LogLvl.INFO, msgContent);
    }

    /**
     * Creates Debug log level message.
     * @param msgContent the log message content.
     */
    public void logDebug(String msgContent) {
        log(LogLvl.DEBUG, msgContent);
    }
}
