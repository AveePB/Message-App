package chat;

//Java Utilities (Popular Classes)
import java.util.ArrayList;
import java.util.List;

//Java Language (Fundamental Classes)
import java.lang.String;


public class Conversation {
    private List<Message> messages;
    private List<User> members;

    public Conversation(User u1, User u2) {
        this.messages = new ArrayList<>();
        this.members = new ArrayList<>();

        u1.addContact(u2.getEmail(), this);
        u2.addContact(u1.getEmail(), this);

        this.members.add(u1);
        this.members.add(u2);
    }

    public void sendMessage(String text, User author) {
        Message msg = new Message(text, author);
        this.messages.add(msg);

        for (User u: this.members) {
            if (u != author && u.isOnline()) {
                u.sendResponseNewMessage(msg);
            }
        }
    }
}
