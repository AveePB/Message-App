package app.api;

//Java Language
import java.lang.Exception;
import java.lang.String;

/**
 * This is the Unknown Request exception class.
 * It's form of Throwable that indicates conditions
 * that a reasonable application might want to catch.
 */
public class UnknownRequest extends Exception {
    public UnknownRequest(String message) {
        super(message);
    }
}
