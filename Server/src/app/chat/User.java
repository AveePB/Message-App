package app.chat;

//Java I/O (Input and Output)
import java.io.PrintWriter;

//Java Utilities (Popular Classes)
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.api.Response;


public class User {
    private Map<String, Chat> friends;
    private final String nickName;
    private final String password;
    private PrintWriter pw;

    public User(String nickName, String password) {
        this.friends = new HashMap<>();
        this.nickName = nickName;
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

    public boolean isFriend(String friendNickName) {
        return this.friends.containsKey(friendNickName);
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getPassword() {
        return this.password;
    }

    public Set<String> getAllFriends() {
        return this.friends.keySet();
    }

    public List<Message> getPreviousMessages(String friendNickName) {
        Chat chat = this.friends.getOrDefault(friendNickName, null);
        if (chat == null) return null;

        return chat.messages;
    }

    public Chat getChat(String friendNickName) {
        return this.friends.getOrDefault(friendNickName, null);
    }

    protected void deliverMessage(Message msg) {
        this.pw.println(Response.NEW_MESSAGE);
        this.pw.println(msg.toString());
        this.pw.flush();
    }

    protected void addFriend(String newFriendNickName, Chat newChat) {
        this.friends.put(newFriendNickName, newChat);
    }
}
