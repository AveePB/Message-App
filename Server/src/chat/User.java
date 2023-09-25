package chat;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Utilities (Popular Classes)
import java.util.HashMap;
import java.util.Map;


public class User {
    private Map<String, Conversation> contacts;
    private final String email;
    private final String password;

    public User(String email, String password) {
        this.contacts = new HashMap<>();
        this.email = email;
        this.password = password;
    }

    public void addContact(String newContactEmail, Conversation newConversation) {
        this.contacts.put(newContactEmail, newConversation);
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
