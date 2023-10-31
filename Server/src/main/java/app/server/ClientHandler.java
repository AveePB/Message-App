package app.server;

//Java Custom
import app.api.Request;
import app.api.UnknownRequest;

//Java Input & Output
import java.io.OutputStream;

//Java Networking
import java.net.Socket;

//Java Utilities
import java.util.Map;

/**
 * Client Handler class takes care of the client.
 * It handles all kinds of requests. It is a
 * thread that is set daemon on default.
 */
public class ClientHandler extends Thread {
    //Variables:
    private RequestHandler requestHandler;
    private Socket sock;

    private boolean isListening;

    /**
     * Constructs a client handler object.
     * @param activeUsers the active users.
     * @param sock the client socket.
     */
    public ClientHandler(Map<Integer, OutputStream> activeUsers, Socket sock) {
        this.requestHandler = new RequestHandler(activeUsers, sock);
        this.sock = sock;
        //this.logger = new Logger(Config.ARE_LOGS_APPENDED, Config.LOG_DIR);

        this.isListening = false;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        this.isListening = true;

        while (this.isListening) {
            try {
                //Request <- the Base64 encoded String of a JSON object
                Request request = new Request(this.sock.getInputStream());

                try {
                    if (request.isDELETE())
                        this.requestHandler.handleDELETE(request);
                    else if (request.isPOST())
                        this.requestHandler.handlePOST(request);
                    else if (request.isPUT())
                        this.requestHandler.handlePUT(request);
                    else if (request.isGET())
                        this.requestHandler.handleGET(request);
                    else
                        throw new UnknownRequest("Unknown Request!");
                }
                catch (UnknownRequest ex) {
                    this.requestHandler.handleUNKNOWN(request);
                }
            }
            catch (Exception ex) {
                //logger func
                System.out.println(ex);
                this.isListening = false;
            }
        }
    }
}
