package app.api;

//Java Networking (API)
import java.net.ServerSocket;
import java.net.Socket;

//Java I/O (Input and Output)
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

//Java SQL (Structured Query Language)
import java.sql.SQLException;

//Java Custom Packages
import app.db.Database;
import app.log.Logger;
import app.Settings;


public class Server {
    private ServerSocket sock;
    private Logger logger;
    private Database db;

    private boolean running = true;

    /**
     * Constructs a server object.
     * @param logger the logger object.
     * @param port the server port.
     * @throws SQLException if a database access error occurs or the url is null.
     */
    public Server(Logger logger, int port) throws SQLException {
        this.db = new Database(Settings.SQL_DB_URL, Settings.SQL_DB_USERNAME, Settings.SQL_DB_USER_PASSWORD);
        this.logger = logger;

        try {
            this.sock = new ServerSocket(port);
            this.logger.logInfo("Socket was initialized!");
        }
        catch (IOException e) {
            this.logger.logFatal("Socket wasn't initialized!");
        }
    }

    /**
     * Closes server.
     */
    public void closeSocket() {
        try {
            this.sock.close();
        } catch (IOException ignored) { }
    }

    /**
     * Runs server.
     */
    public void run() {
        if (this.sock == null) return;
        this.logger.logInfo("Server is running...");

        while (this.running) {
            try {
                Socket clientSock = this.sock.accept();
                InputStream in = clientSock.getInputStream();
                OutputStream out = clientSock.getOutputStream();

                new ClientHandler(in, out, this.db, this.logger, clientSock.getInetAddress().getHostAddress()).start();
            }
            catch (IOException e) {
                this.running =  false;
            }
        }
        closeSocket();
        this.logger.logInfo("Server closing...");
    }
}
