//Java Custom Packages
import api.Server;
import log.Logger;


public class Main {
    private static final String LOG_FILE_NAME = "ServerLogs.log";
    private static final int PORT = 55555;

    public static void main(String[] args) {
        Logger logger = new Logger(LOG_FILE_NAME);
        Server server = new Server(logger, PORT);
        server.run();

        /*
         * TO DO:
         * 7.Save All Data
         * 8.Load All Data
         */
    }
}
