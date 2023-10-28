package app.server;

//Java Custom
import app.Config;

//Java Input & Output
import java.io.IOException;
import java.io.OutputStream;

//Java Networking
import java.net.ServerSocket;
import java.net.Socket;

//Java Utilities
import java.util.HashMap;
import java.util.Map;

/**
 * This class is a heart of the application. The Server
 * class listens for the clients and accepts their connections.
 * It uses the constants from the config file (Config.java).
 */
public class Server {
    //Variables:
    private Map<Integer, OutputStream> activeUsers;
    private ServerSocket sock;
    //private logger;

    private boolean isListening;

    /**
     * Constructs a server object using constants from
     * the config file (Config.java).
     */
    public Server() throws IOException {
        this.activeUsers = new HashMap<>();
        this.sock = new ServerSocket(Config.PORT);
        //this.logger = new Logger(Config.ARE_LOGS_APPENDED, Config.LOG_DIR);

        this.isListening = false;
    }

    /**
     * The server starts listening for incoming
     * client connections.
     */
    public void listen() {
        this.isListening = true;
        while (this.isListening) {
            try {
                Socket clientSock = this.sock.accept();
                //Client Handler Deamon thread function (clientSock)
            }
            catch (Exception ex) {
                //Logger func
                this.isListening = false;
            }
        }
    }
}
