package app.api;

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
    CHAT_CREATION_SUCCESS("CHAT_CREATION_SUCCESS"), //CHAT_CREATION_SUCCESS
    CHAT_CREATION_STOPPED("CHAT_CREATION_STOPPED"), //CHAT_CREATION_STOPPED
    CHAT_CREATION_FAILED("CHAT_CREATION_FAILED"), //CHAT_CREATION_FAILED

    //CREATE_MSG request responses:
    MSG_CREATION_SUCCESS("MSG_CREATION_SUCCESS"), //MSG_CREATION_SUCCESS
    MSG_CREATION_FAILED("MSG_CREATION_FAILED"), //MSG_CREATION_FAILED

    NEW_MSG("NEW_MSG"), //NEW_MSG.AUTHOR_EMAIL.MESSAGE_CONTENT

    //GET_CHAT_USERS request responses:
    CHAT_USERS("CHAT_USERS"); //CHAT_USERS.USER1.USER2...

    private final String str;

    Response(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return this.str;
    }
}
