package app.api;

//Java Language (Fundamental Classes)
import java.lang.String;


public enum Response {
    LOGIN_FAILED("LOGIN_FAILED"), //LOGIN_FAILED
    LOGIN_SUCCESS("LOGIN_SUCCESS"), //LOGIN_SUCCESS
    REGISTER_FAILED("REGISTER_FAILED"), //REGISTER_FAILED
    REGISTER_SUCCESS("REGISTER_SUCCESS"), //REGISTER_SUCCESS
    PREVIOUS_MESSAGES("PREVIOUS_MESSAGES"), //PREVIOUS_MESSAGES.MESSAGE1.(...)
    NEW_MESSAGE("NEW_MESSAGE"), //NEW_MESSAGE.FULL_MSG
    FRIEND_LIST("FRIEND_LIST"), //FRIEND_LIST.FRIEND1.(...)
    INVALID_REQUEST("INVALID_REQUEST"), //INVALID_REQUEST
    INVALID_DATA("INVALID_DATA"), //INVALID_DATA
    OPERATION_SUCCESS("OPERATION_SUCCESS"); //OPERATION_SUCCESS

    private final String str;

    Response(String str) {
        this.str = str;
    }

    public String getStr() {
        return this.str;
    }
}
