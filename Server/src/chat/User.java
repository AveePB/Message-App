package chat;

//Java I/O (Input and Output)
import java.io.PrintWriter;

//Java Utilities (Popular Classes)
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

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

    public void setStatusOnline(PrintWriter pw) {
        this.pw = pw;
    }

    public void setStatusOffline() {
        this.pw = null;
    }

    public boolean isOnline() {
        return (this.pw != null);
    }

    public boolean isFriend(String email) {
        return this.contacts.containsKey(email);
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<String> getAllContacts() {
        return this.contacts.keySet();
    }

    public List<Message> getPreviousMessages(String contactEmail) {
        Conversation conversation = this.contacts.getOrDefault(contactEmail, null);
        if (conversation == null) return null;

        return conversation.messages;
    }

    public Conversation getConversation(String contactEmail) {
        return this.contacts.getOrDefault(contactEmail, null);
    }

    protected void deliverMessage(Message msg) {
        this.pw.println(Response.NEW_MESSAGE);
        this.pw.println(msg.toString());
        this.pw.flush();
    }

    protected void addContact(String newContactEmail, Conversation newConversation) {
        this.contacts.put(newContactEmail, newConversation);
    }
}
