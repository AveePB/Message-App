package chat;

//Java I/O (Input and Output)
import java.io.PrintWriter;

//Java Utilities (Popular Classes)
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import api.Response;


public class User {
    private Map<String, Conversation> contacts;
    private final String email;
    private final String password;
    private PrintWriter pw;

    public User(String email, String password) {
        this.contacts = new HashMap<>();
        this.email = email;
        this.password = password;
    }

    public void addContact(String newContactEmail, Conversation newConversation) {
        this.contacts.put(newContactEmail, newConversation);
    }

    public void setStatusOnline(PrintWriter pw) {
        this.pw = pw;
    }

    public void setStatusOffline() {
        this.pw = null;
    }

    public boolean isOnline() {
        return (this.pw != null);
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void sendResponseListContacts() {
        this.pw.println(Response.CONTACT_LIST);

        for (String contactEmail: this.contacts.keySet())
            this.pw.println(contactEmail);

        this.pw.flush();
    }

    public void sendResponsePreviousMessages(List<Message> messages) {
        this.pw.println(Response.PREVIOUS_MESSAGES.getStr());

        for (Message msg: messages)
            this.pw.println(msg.toString());

        this.pw.flush();
    }

    public void sendResponseNewMessage(Message msg) {
        this.pw.println(Response.NEW_MESSAGE);

        this.pw.println(msg.toString());

        this.pw.flush();
    }


    public void sendResponse(Response rep) {
        this.pw.println(rep.getStr());

        this.pw.flush();
    }
}
