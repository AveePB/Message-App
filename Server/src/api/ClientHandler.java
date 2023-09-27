package api;

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
import log.Logger;
import chat.User;


public class ClientHandler extends Thread {
    private Map<String, User> db;
    private BufferedReader br;
    private PrintWriter pw;
    private Logger log;

    private boolean running = true;
    private String ip_address;
    private User user;

    public ClientHandler(InputStream in, OutputStream out, Map<String, User> db, Logger logger, String ip_address) {
        this.br = new BufferedReader(new InputStreamReader(in));
        this.pw = new PrintWriter(out);

        this.db = db;
        this.log = logger;
        this.ip_address = ip_address;
    }

    private void handleNotLoggedClientRequest(String req) {
        if (req.equals(Request.LOGIN.getStr())) {
            //...
        }
        else if (req.equals(Request.REGISTER.getStr())) {
            //...
        }
        else {
            this.log.logWarn(this.ip_address + " has sent invalid request!");
            this.pw.println(Response.INVALID_REQUEST.getStr());
            this.pw.flush();
        }
    }

    private void handleLoggedClientRequest(String req) {
        if (req.equals(Request.LOGOUT.getStr())) {
            //...
        }
        else if (req.equals(Request.READ_CONVERSATION.getStr())) {
            //...
        }
        else if (req.equals(Request.SEND_MSG.getStr())) {
            //...
        }
        else if (req.equals(Request.ADD_CONTACT.getStr())) {
            //...
        }
        else if (req.equals(Request.LIST_CONTACTS.getStr())) {
            //...
        }
        else {
            this.log.logWarn(this.ip_address + " has sent invalid request!");
            this.pw.println(Response.INVALID_REQUEST.getStr());
            this.pw.flush();
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
                String req = this.br.readLine();

                if (this.user == null)
                    handleNotLoggedClientRequest(req);
                else
                    handleLoggedClientRequest(req);
            }
            catch (IOException e) {
                this.running =  false;
            }
        }

        closeConnection();
        this.log.logInfo("Lost Connection with " + this.ip_address + '!');
    }
}
