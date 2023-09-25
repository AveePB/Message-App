package log;

//Java I/O (Input and Output)
import java.io.IOException;
import java.io.FileWriter;

//Java New I/O (New Input and Output)
import java.nio.file.Paths;
import java.nio.file.Files;

//Java Language (Fundamental Classes)
import java.lang.StringBuilder;
import java.lang.String;

//Java Utilities (Popular Classes)
import java.nio.file.Path;
import java.util.Date;


public class Logger {
    private static final boolean IS_APPENDED = true;
    private static final String LOG_DIR = "./logs/";

    private String outputDir;

    public Logger(String fileName) {
        this.outputDir = Logger.LOG_DIR + fileName;
    }

    private String createLogMsg(LogLvl lvl, String msg) {
        StringBuilder sb = new StringBuilder(lvl.str); //Log Lvl
        sb.append(" (").append(new Date()).append(") "); //Current Date
        sb.append(msg).append('\n'); //Msg
        return sb.toString();
    }

    private void createLogDirIfNotExits() throws IOException {
        Path logPath = Paths.get(LOG_DIR);
        if (Files.exists(logPath)) return;
        Files.createDirectory(logPath);
    }

    private void log(LogLvl lvl, String msg) {
        String logMsg = createLogMsg(lvl, msg);
        System.out.print(logMsg);

        try {
            createLogDirIfNotExits();
            FileWriter fw = new FileWriter(this.outputDir, Logger.IS_APPENDED);
            fw.write(logMsg);
            fw.close();
        }
        catch (IOException ignored) { }
    }

    public void logFatal(String msg) {
        log(LogLvl.FATAL, msg);
    }

    public void logError(String msg) {
        log(LogLvl.ERROR, msg);
    }

    public void logWarn(String msg) {
        log(LogLvl.WARN, msg);
    }

    public void logInfo(String msg) {
        log(LogLvl.INFO, msg);
    }

    public void logDebug(String msg) {
        log(LogLvl.DEBUG, msg);
    }
}
