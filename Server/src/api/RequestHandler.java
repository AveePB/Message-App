package api;

//Java I/O (Input and Output)
import java.io.PrintWriter;

//Java Utilities (Popular Classes)
import java.util.List;
import java.util.Map;
import java.util.Set;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import chat.Chat;
import chat.Message;
import chat.User;
import log.Logger;


public class RequestHandler {
    private Map<String, User> db;
    private PrintWriter pw;
    private Logger log;

    private String ip_address;
    private User user;

    public RequestHandler(PrintWriter pw, Map<String, User> db, Logger log, String ip_address) {
        this.pw = pw;
        this.db = db;
        this.log = log;

        this.ip_address = ip_address;
    }

    private void sendResponse(Response rep, String args) {
        this.log.logInfo(rep.getStr() + args + " sent to " + this.ip_address);
        this.pw.println(rep.getStr() + args);
        this.pw.flush();
    }

    protected boolean isRegisteredEmail(String nickName) {
        return this.db.containsKey(nickName);
    }

    protected boolean isPasswordValid(String nickName, String password) {
        return this.db.get(nickName).getPassword().equals(password);
    }

    protected boolean isUserLoggedIn() {
        return (this.user != null);
    }

    protected void logIn(String nickName, String password) {

        if (isRegisteredEmail(nickName) && isPasswordValid(nickName, password)) {
            this.user = this.db.get(nickName);
            this.user.setStatusOnline(this.pw);

            sendResponse(Response.LOGIN_SUCCESS, "");
        }
        else {
            sendResponse(Response.LOGIN_FAILED, "");
        }
    }

    protected void register(String nickName, String password) {

        if (!isRegisteredEmail(nickName)) {
            this.user = new User(nickName, password);
            this.user.setStatusOnline(this.pw);

            this.db.put(nickName, this.user);
            sendResponse(Response.REGISTER_SUCCESS, "");
        }
        else {
            sendResponse(Response.REGISTER_FAILED, "");
        }
    }

    protected void logOut() {
        if (this.user == null) return;

        this.user.setStatusOffline();
        this.user = null;
    }

    protected void readConversation(String friendNickName) {
        List<Message> messages = this.user.getPreviousMessages(friendNickName);

        if (messages != null) {
            StringBuilder args = new StringBuilder();

            for (Message msg: messages)
                args.append(Server.SEP).append(msg.toString());

            sendResponse(Response.PREVIOUS_MESSAGES, args.toString());
        }
        else {
            sendResponse(Response.INVALID_DATA, "");
        }
    }

    protected void sendMessage(String friendNickName,  String msg) {
        Chat chat = this.user.getChat(friendNickName);

        if (chat != null) {
            chat.sendMessage(msg, this.user);
            sendResponse(Response.OPERATION_SUCCESS, "");
        }
        else {
            sendResponse(Response.INVALID_DATA, "");
        }
    }

    protected void addContact(String friendNickName) {
        if ((!isRegisteredEmail(friendNickName)) || (this.user.isFriend(friendNickName))) {
            sendResponse(Response.INVALID_DATA, "");
            return;
        }

        new Chat(this.user, this.db.get(friendNickName));
        sendResponse(Response.OPERATION_SUCCESS, "");
    }

    protected void listFriends() {
        Set<String> friends = this.user.getAllFriends();
        StringBuilder args = new StringBuilder();

        for (String friend: friends)
            args.append(Server.SEP).append(friend);

        sendResponse(Response.FRIEND_LIST, args.toString());
    }
}
