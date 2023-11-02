package app.api;

//Java Language
import java.lang.Exception;
import java.lang.String;

/**
 * This is the Unknown Request exception class.
 * It's form of Throwable that indicates conditions
 * that a reasonable application might want to catch.
 */
public class APIException extends Exception {
    //Variables:
    private StatusCode sc;

    public APIException(String msg, StatusCode sc) {
        super(msg);
        this.sc = sc;
    }

    public APIException(StatusCode sc) {
        super("");
        this.sc = sc;
    }

    public StatusCode getStatusCode() {
        return this.sc;
    }
}
