package app.api;

//Java Networking (API)
import java.net.ServerSocket;
import java.net.Socket;

//Java I/O (Input and Output)
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

//Java Utilities (Popular Classes)
import java.util.HashMap;
import java.util.Map;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.chat.User;
import app.log.Logger;


public class Server {
    protected static final String SEP = "_\\+S3WFSDFSFDSFdP\\+_";

    private Map<String, User> db;
    private ServerSocket sock;
    private Logger log;
    private boolean running = true;

    public Server(Logger logger, int port) {
        this.db = new HashMap<>();
        this.log = logger;
        try {
            this.sock = new ServerSocket(port);
            this.log.logInfo("Socket was initialized!");
        }
        catch (IOException e) {
            this.log.logFatal("Socket wasn't initialized!");
        }
    }

    public void closeSocket() {
        try {
            this.sock.close();
        } catch (IOException ignored) { }
    }

    public void run() {
        if (this.sock == null) return;
        this.log.logInfo("Server is running...");

        while (this.running) {
            try {
                Socket clientSock = this.sock.accept();
                InputStream in = clientSock.getInputStream();
                OutputStream out = clientSock.getOutputStream();

                new ClientHandler(in, out, this.db, this.log, clientSock.getInetAddress().getHostAddress()).start();
            }
            catch (IOException e) {
                this.running =  false;
            }
        }
        closeSocket();
        this.log.logInfo("Server closing...");
    }
}
