package app.api;

//Java Utilities (Popular Classes)
import java.util.Base64;

//Java Language (Fundamental Classes)
import java.lang.String;


public enum Response {
    //LOGIN request responses:
    LOGIN_FAILED("LOGIN_FAILED"), //LOGIN_FAILED
    LOGIN_SUCCESS("LOGIN_SUCCESS"), //LOGIN_SUCCESS

    //REGISTER request responses:
    REGISTER_FAILED("REGISTER_FAILED"), //REGISTER_FAILED
    REGISTER_SUCCESS("REGISTER_SUCCESS"), //REGISTER_SUCCESS

    //Response for invalid request:
    INVALID_REQUEST("INVALID_REQUEST"), //INVALID_REQUEST

    //CREATE_CHAT request responses:
    CHAT_CREATION_SUCCESS("CHAT_CREATION_SUCCESS"), //CHAT_CREATION_SUCCESS.NICKNAME
    CHAT_CREATION_STOPPED("CHAT_CREATION_STOPPED"), //CHAT_CREATION_STOPPED
    CHAT_CREATION_FAILED("CHAT_CREATION_FAILED"), //CHAT_CREATION_FAILED

    //CREATE_MSG request responses:
    MSG_CREATION_SUCCESS("MSG_CREATION_SUCCESS"), //MSG_CREATION_SUCCESS.MESSAGE
    MSG_CREATION_FAILED("MSG_CREATION_FAILED"), //MSG_CREATION_FAILED

    NEW_MSG("NEW_MSG"), //NEW_MSG.AUTHOR_NICKNAME.MESSAGE_CONTENT
    NEW_CHAT("NEW_CHAT"), //NEW_CHAT.NICKNAME

    //GET_CHAT_USERS request responses:
    CHAT_USERS("CHAT_USERS"), //CHAT_USERS.USER1.USER2...

    //READ_CHAT_MESSAGES request responses:
    CHAT_MESSAGES("CHAT_MESSAGES"); //CHAT_MESSAGES.MESSAGE1...

    private final String str;

    Response(String str) {
        this.str = str;
    }

    /**
     * Encodes the given string using the base64 key.
     * @param text the string.
     * @return the encoded string.
     */
    public static String encodeText(String text) {
        byte[] bytes = Base64.getEncoder().encode(text.getBytes());
        return new String(bytes);
    }

    @Override
    public String toString() {
        return this.str;
    }
}
