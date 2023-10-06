//Java SQL (Structured Query Language)
import java.sql.SQLException;

//Java Custom Packages
import app.api.Server;
import app.log.Logger;
import app.Settings;


public class Main {
    public static void main(String[] args) {
        Logger logger = new Logger(Settings.LOGS_FILE_NAME);

        try {
            Server server = new Server(logger, Settings.API_PORT);
            server.run();
        }
        catch (SQLException e) {
            logger.logFatal("The MySQL Database isn't connected!");
            logger.logFatal(e.toString());
        }
    }
}
