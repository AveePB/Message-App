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
import chat.Conversation;
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

    protected boolean isRegisteredEmail(String email) {
        return this.db.containsKey(email);
    }

    protected boolean isPasswordValid(String email, String password) {
        return this.db.get(email).getPassword().equals(password);
    }

    protected boolean isUserLoggedIn() {
        return (user != null);
    }

    protected void logIn(String email, String password) {

        if (isRegisteredEmail(email) && isPasswordValid(email, password)) {
            this.user = this.db.get(email);
            this.user.setStatusOnline(this.pw);

            sendResponse(Response.LOGIN_SUCCESS, "");
        }
        else {
            sendResponse(Response.LOGIN_FAILED, "");
        }
    }

    protected void register(String email, String password) {

        if (!isRegisteredEmail(email)) {
            this.user = new User(email, password);
            this.user.setStatusOnline(this.pw);

            this.db.put(email, this.user);
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

    protected void readConversation(String contactEmail) {
        List<Message> messages = this.user.getPreviousMessages(contactEmail);

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

    protected void sendMessage(String contactEmail,  String msg) {
        Conversation conversation = this.user.getConversation(contactEmail);

        if (conversation != null) {
            conversation.sendMessage(msg, this.user);
            sendResponse(Response.OPERATION_SUCCESS, "");
        }
        else {
            sendResponse(Response.INVALID_DATA, "");
        }
    }

    protected void addContact(String contactEmail) {
        if ((!isRegisteredEmail(contactEmail)) || (this.user.isFriend(contactEmail))) {
            sendResponse(Response.INVALID_DATA, "");
            return;
        }

        new Conversation(this.user, this.db.get(contactEmail));
        sendResponse(Response.OPERATION_SUCCESS, "");
    }

    protected void listContacts() {
        Set<String> contacts = this.user.getAllContacts();
        StringBuilder args = new StringBuilder();

        for (String contact: contacts)
            args.append(Server.SEP).append(contact);

        sendResponse(Response.CONTACT_LIST, args.toString());
    }
}
