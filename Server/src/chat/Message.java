package chat;

//Java Language (Fundamental Classes)
import java.lang.String;


public class Message {
    private final String content;
    private final User author;

    public Message(String content, User author) {
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return this.author.getEmail() + ": " + this.content;
    }

    public String getContent() {
        return this.content;
    }

    public User getAuthor() {
        return this.author;
    }
}
