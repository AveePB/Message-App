package app.api;

//Java I/O (Input and Output)
import java.io.PrintWriter;

//Java Language (Fundamental Classes)
import java.lang.StringBuilder;
import java.lang.String;

//Java Custom Packages
import app.Settings;
import app.db.Database;
import app.db.User;
import app.log.Logger;


public class RequestHandler {
    private PrintWriter pw;
    private Database db;
    private Logger logger;

    private String ipAddress;
    private User user;

    /**
     * Constructs request handler object.
     * @param pw the client's print writer object.
     * @param db the MySQL database interface object.
     * @param logger the server logger.
     * @param ipAddress the client's ip address.
     */
    public RequestHandler(PrintWriter pw, Database db, Logger logger, String ipAddress) {
        this.pw = pw;
        this.db = db;

        this.logger = logger;
        this.ipAddress = ipAddress;
    }

    /**
     * Sends an encoded response to client.
     * @param rep the request type.
     * @param args additional compressed arguments.
     */
    public void sendResponse(Response rep, String args) {
        this.logger.logInfo(rep + args + " sent to " + this.ipAddress);

        this.pw.println(Response.encodeText(rep+args));
        this.pw.flush();
    }

    /**
     * Returns true if a user is logged in otherwise false.
     * @return the user's activity.
     */
    protected boolean isUserLoggedIn() {
        return (this.user != null);
    }

    /**
     * Handles LOG_IN request.
     * @param nickname the user's nickname.
     * @param password the user's password.
     */
    protected void logIn(String nickname, String password) {
        this.user = this.db.getUser(nickname, password);

        if (isUserLoggedIn()) {
            this.db.setUserStatusOnline(this.user.getId(), this.pw);
            sendResponse(Response.LOGIN_SUCCESS, "");
        }
        else {
            sendResponse(Response.LOGIN_FAILED, "");
        }
    }

    /**
     * Handles REGISTER request.
     * @param nickname the user's nickname.
     * @param password the user's password.
     */
    protected void register(String nickname, String password) {
        this.user = this.db.registerUser(nickname, password);

        if (isUserLoggedIn()) {
            this.db.setUserStatusOnline(this.user.getId(), this.pw);
            sendResponse(Response.REGISTER_SUCCESS, "");
        }
        else {
            sendResponse(Response.REGISTER_FAILED, "");
        }
    }

    /**
     * Handles LOG_OUT request.
     */
    protected void logOut() {
        if (this.user == null) return;
        this.db.setUserStatusOffline(this.user.getId());
        this.user = null;
    }

    /**
     * Handles CREATE_CHAT request.
     * @param nickname the user's nickname.
     */
    protected void createChat(String nickname) {
        try {
            User user2 = this.db.getUser(nickname);
            int ans = this.db.createChat(this.user.getId(), user2.getId());

            if (ans == 1) {
                sendResponse(Response.CHAT_CREATION_SUCCESS, (Settings.API_SEPARATOR + nickname));
                this.db.sendNewChatNotification(user2.getId(), this.user.getNickname());
            }
            else if (ans == 0) {
                sendResponse(Response.CHAT_CREATION_STOPPED, "");
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e) {
            sendResponse(Response.CHAT_CREATION_FAILED, "");
        }
    }

    /**
     * Handles CREATE_MESSAGE request.
     * @param nickname the user's nickname.
     * @param msgContent the message content.
     */
    protected void createMessage(String nickname, String msgContent) {
        try {
            User recipient = this.db.getUser(nickname);
            if (this.db.createMessage(this.user.getId(), recipient.getId(), msgContent)) {
                this.db.sendMessage(recipient.getId(),  this.user.getNickname(), msgContent);

                sendResponse(Response.MSG_CREATION_SUCCESS, (Settings.API_SEPARATOR + msgContent));
            }
            else {
                throw new Exception();
            }
        }
        catch (Exception e) {
            sendResponse(Response.MSG_CREATION_FAILED, "");
        }
    }

    /**
     * Handles GET_CHAT_USERS request.
     */
    protected void getChatUsers() {
        try {
            StringBuilder args = new StringBuilder();
            for (String nickname: this.db.getChatUsers(this.user.getId()))
                args.append(Settings.API_SEPARATOR).append(nickname);

            sendResponse(Response.CHAT_USERS, args.toString());
        }
        catch (Exception e) {
            sendResponse(Response.CHAT_USERS, "");
        }
    }

    /**
     * Handles READ_CHAT_MESSAGES request.
     * @param nickname
     */
    protected void readChatMessages(String nickname) {
        try {
            User secondUser = this.db.getUser(nickname);
            StringBuilder args = new StringBuilder();

            for (String msg: this.db.getMessages(this.user, secondUser))
                args.append(Settings.API_SEPARATOR).append(msg);

            sendResponse(Response.CHAT_MESSAGES, args.toString());
        }
        catch (Exception e) {
            sendResponse(Response.CHAT_MESSAGES, "");
        }
    }
}
