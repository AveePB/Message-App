package app.chat;

//Java Utilities (Popular Classes)
import java.util.ArrayList;
import java.util.List;

//Java Language (Fundamental Classes)
import java.lang.String;


public class Chat {
    protected List<Message> messages;
    private List<User> members;

    public Chat(User u1, User u2) {
        this.messages = new ArrayList<>();
        this.members = new ArrayList<>();

        u1.addFriend(u2.getNickName(), this);
        u2.addFriend(u1.getNickName(), this);

        this.members.add(u1);
        this.members.add(u2);
    }

    public void sendMessage(String text, User author) {
        Message msg = new Message(text, author);
        this.messages.add(msg);

        for (User u: this.members) {
            if (u != author && u.isOnline()) {
                u.deliverMessage(msg);
            }
        }
    }
}
