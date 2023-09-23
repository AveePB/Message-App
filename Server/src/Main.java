import api.Server;

public class Main {
    private static final String LOG_FILE_NAME = "ServerLogs.log";
    private static final int PORT = 55555;

    public static void main(String[] args) {
        Server server = new Server(LOG_FILE_NAME, PORT);
        server.run();
    }
}
