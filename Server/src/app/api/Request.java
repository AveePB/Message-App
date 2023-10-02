package app.api;

//Java Language (Fundamental Classes)
import java.lang.String;


public enum Request {
    LOGIN("LOGIN"), //LOGIN.NICK_NAME.PASSWORD
    LOGOUT("LOGOUT"), //LOGOUT
    REGISTER("REGISTER"), //REGISTER.NICK_NAME.PASSWORD
    ADD_FRIEND("ADD_FRIEND"), //ADD_FRIEND.FRIEND_NICK_NAME
    SEND_MSG("SEND_MSG"), //SEND_MSG.FRIEND_NICK_NAME.MESSAGE_CONTENT
    READ_CHAT_MESSAGES("READ_CHAT_MESSAGES"), //READ_CHAT_MESSAGES.FRIEND_NICK_NAME
    LIST_FRIENDS("LIST_FRIENDS"); //LIST_FRIENDS

    private final String str;

    Request(String str) {
        this.str = str;
    }

    public String getStr() {
        return this.str;
    }
}
