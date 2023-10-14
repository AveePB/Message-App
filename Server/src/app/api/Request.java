package app.api;

//Java Utilities (Popular Classes)
import java.util.Base64;

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

    /**
     * Decodes the given string using the base64 key.
     * @param text the string.
     * @return the decoded string.
     */
    public static String decodeText(String text) {
        byte[] bytes = Base64.getDecoder().decode(text.getBytes());
        return new String(bytes);
    }

    @Override
    public String toString() {
        return this.str;
    }
}
