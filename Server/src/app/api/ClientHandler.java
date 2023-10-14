package app.api;

//Java I/O (Input and Output)
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.db.Database;
import app.log.Logger;
import app.Settings;


public class ClientHandler extends Thread {
    private BufferedReader br;
    private PrintWriter pw;
    private RequestHandler rh;
    private Logger logger;

    private boolean running = true;
    private String ipAddress;

    /**
     * Constructs client handler object.
     * @param in the input stream.
     * @param out the output stream.
     * @param db the MySQL database interface object.
     * @param logger the server logger.
     * @param ipAddress the client's ip address.
     */
    public ClientHandler(InputStream in, OutputStream out, Database db, Logger logger, String ipAddress) {
        this.br = new BufferedReader(new InputStreamReader(in));
        this.pw = new PrintWriter(out);

        this.rh = new RequestHandler(this.pw, db, logger, ipAddress);
        this.logger = logger;

        this.ipAddress = ipAddress;
    }

    /**
     * Handles requests from the client.
     * @param req the full form request.
     */
    private void handleClientRequest(String[] req) {
        //REQ[0] <- Request type, REQ[J] <- Additional arguments
        try {
            if ((req.length == 3) && (req[0].equals(Request.LOGIN.toString()))) {
                //REQ[1] <- nickname, REQ[2] <- password
                this.rh.logIn(req[1], req[2]);
            }
            else if ((req.length == 3) && (req[0].equals(Request.REGISTER.toString()))) {
                //REQ[1] <- nickname, REQ[2] <- password
                this.rh.register(req[1], req[2]);
            }
            else {
                throw new IOException("INVALID REQUEST!!!");
            }
        }
        catch (IOException e) {
            this.rh.sendResponse(Response.INVALID_REQUEST, "");
        }
    }

    /**
     * Handles requests from the logged in client.
     * @param req the full form request.
     */
    private void handleLoggedClientRequest(String[] req) {
        //REQ[0] <- Request type, REQ[J] <- Additional arguments
        try {
            if ((req.length == 1) && (req[0].equals(Request.LOGOUT.toString()))) {
                this.rh.logOut();
            }
            else if ((req.length == 2) && (req[0].equals(Request.CREATE_CHAT.toString()))) {
                //REQ[1] <- nickname
                this.rh.createChat(req[1]);
            }
            else if ((req.length == 3) && (req[0].equals(Request.CREATE_MSG.toString()))) {
                //REQ[1] <- nickname, REQ[2] <- message
                this.rh.createMessage(req[1], req[2]);
            }
            else if ((req.length == 1) && (req[0].equals(Request.GET_CHAT_USERS.toString()))) {
                this.rh.getChatUsers();
            }
            else {
                throw new IOException("INVALID REQUEST!!!");
            }
        }
        catch (IOException e) {
            this.rh.sendResponse(Response.INVALID_REQUEST, "");
        }
    }

    /**
     * Safely closes connection with the client.
     */
    private void closeConnection() {
        try {
            this.br.close();
        } catch (IOException ignored) { }

        this.pw.close();
    }

    @Override
    public void run() {
        this.logger.logInfo("New Connection with " + this.ipAddress + '!');

        while (this.running) {
            try {
                String[] req = Request.decodeText(this.br.readLine()).split(Settings.API_SEPARATOR);
                this.logger.logInfo(this.ipAddress + " has sent request '" + req[0] + "'!");

                if (this.rh.isUserLoggedIn())
                    handleLoggedClientRequest(req);
                else
                    handleClientRequest(req);
            }
            catch (Exception e) {
                this.running = false;
                this.rh.logOut();

                this.logger.logInfo(e.toString());
            }
        }

        closeConnection();
        this.logger.logInfo("Lost Connection with " + this.ipAddress + '!');
    }
}
