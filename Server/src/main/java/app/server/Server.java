package app.server;

//Java Custom
import app.Config;
import app.db.DataBase;
import app.log.Logger;

//Java Input & Output
import java.io.IOException;
import java.io.OutputStream;

//Java Networking
import java.net.ServerSocket;
import java.net.Socket;

//Java Utilities
import java.sql.SQLException;
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
    private DataBase db;

    private boolean isListening;

    /**
     * Constructs a server object using constants from
     * the config file (Config.java).
     */
    public Server() throws IOException, SQLException {
        this.activeUsers = new HashMap<>();
        this.sock = new ServerSocket(Config.PORT);
        this.db = new DataBase(Config.MYSQL_USERNAME, Config.MYSQL_PASSWORD, Config.MYSQL_DB_URL);

        this.isListening = false;
    }

    /**
     * The server starts listening for incoming
     * client connections.
     */
    public void listen() {
        this.isListening = true;
        Logger.logINFO("Server has started to listen for clients...");
        
        while (this.isListening) {
            try {
                Socket clientSock = this.sock.accept();
                new ClientHandler(this.activeUsers, clientSock, this.db).start();
            }
            catch (Exception ex) {
                Logger.logEMERG(ex.toString());
                this.isListening = false;
            }
        }
    }
}
