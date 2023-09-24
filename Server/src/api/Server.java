package api;

//Java Networking (API)
import java.net.ServerSocket;
import java.net.Socket;

//Java I/O (Input and Output)
import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;

//Java Custom Packages
import log.Logger;


public class Server {
    ServerSocket sock;
    Logger log;

    public Server(Logger logger, int port) {
        this.log = logger;
        try {
            this.sock = new ServerSocket(port);
            this.log.logInfo("Socket was initialized!");
        }
        catch (IOException e) {
            this.log.logFatal("Socket wasn't initialized!");
        }
    }

    public void run() {
        if (this.sock == null) return;
        this.log.logInfo("Server is running...");

        while (true) {
            try {
                Socket clientSock = this.sock.accept();
                InputStream in = clientSock.getInputStream();
                OutputStream out = clientSock.getOutputStream();

                new ClientHandler(in, out).start();
            }
            catch (IOException e) {
                break;
            }
        }
        this.log.logInfo("Server closing...");
    }
}
