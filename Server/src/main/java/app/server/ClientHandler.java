package app.server;

//Java Custom
import app.api.APIException;
import app.api.Request;
import app.api.StatusCode;
import app.db.DataBase;
import app.log.Logger;
import app.server.requesthandlers.RequestHandler;

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
     * @param db the database interface.
     */
    public ClientHandler(Map<Integer, OutputStream> activeUsers, Socket sock, DataBase db) {
        this.requestHandler = new RequestHandler(activeUsers, sock, db);
        this.sock = sock;

        this.isListening = false;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        Logger.logINFO("Server accepted connection with " + this.sock.getInetAddress().getHostAddress());
        this.isListening = true;

        while (this.isListening) {
            try {
                //Request <- the Base64 encoded String of a JSON object
                Request request = new Request(this.sock.getInputStream());
                Logger.logINFO(this.sock.getInetAddress().getHostAddress() + "  has sent request " + request);

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
                        throw new APIException(StatusCode.NOT_IMPLEMENTED);
                }
                catch (APIException ex) {
                    this.requestHandler.handleUNKNOWN(request, ex);
                }
            }
            catch (Exception ex) {
                Logger.logERROR(ex.toString());

                closeConnection();
                this.isListening = false;
            }
        }
        Logger.logINFO("Server lost connection with " + this.sock.getInetAddress().getHostAddress());
    }

    /**
     * Instantly closes connection with the client.
     */
    private void closeConnection() {
        try {
            this.sock.close();
        }
        catch (Exception ignored) { }
    }
}
