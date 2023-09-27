package api;

//Java Language (Fundamental Classes)
import java.lang.String;


public enum Response {
    LOGIN_FAILED("LOGIN_FAILED"), //LOGIN_FAILED
    LOGIN_SUCCESS("LOGIN_SUCCESS"), //LOGIN_SUCCESS
    REGISTER_FAILED("REGISTER_FAILED"), //REGISTER_FAILED
    REGISTER_SUCCESS("REGISTER_SUCCESS"), //REGISTER_SUCCESS
    PREVIOUS_MESSAGES("PREVIOUS_MESSAGES"), //PREVIOUS_MESSAGES ... (read lines till empty)
    NEW_MESSAGE("NEW_MESSAGE"), //NEW_MESSAGE.FULL_MSG
    CONTACT_LIST("CONTACT_LIST"), //CONTACT_LIST ... (read lines till empty)
    INVALID_REQUEST("INVALID_REQUEST"); //INVALID_REQUEST

    private final String str;

    Response(String str) {
        this.str = str;
    }

    public String getStr() {
        return this.str;
    }
}
