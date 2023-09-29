package api;

//Java Language (Fundamental Classes)
import java.lang.String;


public enum Request {
    LOGIN("LOGIN"), //LOGIN.EMAIL.PASSWORD
    LOGOUT("LOGOUT"), //LOGOUT
    REGISTER("REGISTER"), //REGISTER.EMAIL.PASSWORD
    ADD_CONTACT("ADD_CONTACT"), //CONTACT.CONTACT_EMAIL
    SEND_MSG("SEND_MSG"), //SEND_MSG.CONTACT_EMAIL.MESSAGE_CONTENT
    READ_CONVERSATION("READ_CONVERSATION"), //READ_CONVERSATION.CONTACT_EMAIL
    LIST_CONTACTS("LIST_CONTACTS"); //LIST_CONTACTS

    private final String str;

    Request(String str) {
        this.str = str;
    }

    public String getStr() {
        return this.str;
    }
}
