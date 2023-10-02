package app.api;

//Java I/O (Input and Output)
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.IOException;

//Java Utilities (Popular Classes)
import java.util.Map;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.chat.User;
import app.log.Logger;


public class ClientHandler extends Thread {
    private BufferedReader br;
    private PrintWriter pw;

    private RequestHandler rh;
    private Logger log;

    private boolean running = true;
    private String ip_address;

    public ClientHandler(InputStream in, OutputStream out, Map<String, User> db, Logger logger, String ip_address) {
        this.br = new BufferedReader(new InputStreamReader(in));
        this.pw = new PrintWriter(out);

        this.rh = new RequestHandler(this.pw, db, logger, ip_address);
        this.log = logger;

        this.ip_address = ip_address;
    }

    private void sendRespondInvalidRequest(String[] req) {
        //REQ[0] <- Request type
        this.log.logWarn(this.ip_address + " has sent invalid request '" + req[0] + "'!");
        this.pw.println(Response.INVALID_REQUEST.getStr());
        this.pw.flush();
    }

    private void handleNotLoggedClientRequest(String[] req) {
        //REQ[0] <- Request type
        //REQ[I] <- Additional arguments
        try {
            if ((req.length == 3) && (req[0].equals(Request.LOGIN.getStr()))) {
                //REQ[1] <- nickName, REQ[2] <- password
                this.rh.logIn(req[1], req[2]);
            }
            else if ((req.length == 3) && (req[0].equals(Request.REGISTER.getStr()))) {
                //REQ[1] <- nickName, REQ[2] <- password
                this.rh.register(req[1], req[2]);
            }
            else {
                throw new IOException();
            }
        }
        catch (IOException e) {
            sendRespondInvalidRequest(req);
        }
    }

    private void handleLoggedClientRequest(String[] req) {
        //REQ[0] <- Request type
        //REQ[I] <- Additional arguments
        try {
            if ((req.length == 1) && (req[0].equals(Request.LOGOUT.getStr()))) {
                this.rh.logOut();
            }
            else if ((req.length == 2) && (req[0].equals(Request.READ_CHAT_MESSAGES.getStr()))) {
                //REQ[1] <- friendNickName
                this.rh.readConversation(req[1]);
            }
            else if ((req.length == 3) && (req[0].equals(Request.SEND_MSG.getStr()))) {
                //REQ[1] <- friendNickName, REQ[2] <- message
                this.rh.sendMessage(req[1], req[2]);
            }
            else if ((req.length == 2) && (req[0].equals(Request.ADD_FRIEND.getStr()))) {
                //REQ[1] <- friendNickName
                this.rh.addContact(req[1]);
            }
            else if ((req.length == 1) && (req[0].equals(Request.LIST_FRIENDS.getStr()))) {
                this.rh.listFriends();
            }
            else {
                throw new IOException();
            }
        }
        catch (IOException e) {
            sendRespondInvalidRequest(req);
        }
    }

    private void closeConnection() {
        try {
            this.br.close();
        } catch (IOException ignored) { }

        this.pw.close();
    }

    @Override
    public void run() {
        this.log.logInfo("New Connection with " + this.ip_address + '!');

        while (this.running) {
            try {
                String[] req = this.br.readLine().split(Server.SEP);
                this.log.logInfo(this.ip_address + " has sent request '" + req[0] + "'!");

                if (this.rh.isUserLoggedIn())
                    handleLoggedClientRequest(req);
                else
                    handleNotLoggedClientRequest(req);
            }
            catch (Exception e) {
                this.running = false;
                this.rh.logOut();

                this.log.logInfo(e.toString());
            }
        }

        closeConnection();
        this.log.logInfo("Lost Connection with " + this.ip_address + '!');
    }
}
