package app.api;

//Java Language (Fundamental Classes)
import java.lang.String;


public enum Request {
    LOGIN("LOGIN"), //LOGIN.NICKNAME.PASSWORD
    LOGOUT("LOGOUT"), //LOGOUT
    REGISTER("REGISTER"), //REGISTER.NICKNAME.PASSWORD
    CREATE_CHAT("CREATE_CHAT"), //CREATE_CHAT.NICKNAME
    CREATE_MSG("CREATE_MSG"), //CREATE_MSG.NICKNAME.MSG
    GET_CHAT_USERS("GET_CHAT_USERS"); //GET_CHAT_USERS

    private final String str;

    Request(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
