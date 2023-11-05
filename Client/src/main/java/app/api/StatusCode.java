package app.api;

//Java Language
import java.lang.String;

/**
 * This is an enum class used as easy way
 * to display status codes.
 */
public enum StatusCode {
    //SUCCESS 2xx
    OK("OK", 200), //Indicates that the request has succeeded.
    CREATED("CREATED", 201), //Indicates that the request has succeeded and a new resource has been created as a result.

    //CLIENT ERROR 4xx
    BAD_REQUEST("BAD_REQUEST", 400), //The request could not be understood by the server due to incorrect syntax. The client SHOULD NOT repeat the request without modifications.
    UNAUTHORIZED("UNAUTHORIZED", 401), //Indicates that the request requires user authentication information. The client MAY repeat the request with a suitable Authorization header field
    FORBIDDEN("FORBIDDEN",  403), //Unauthorized request. The client does not have access rights to the content. Unlike 401, the client’s identity is known to the server.

    //SERVER ERROR 5xx
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 500), //The server encountered an unexpected condition that prevented it from fulfilling the request.
    NOT_IMPLEMENTED("NOT_IMPLEMENTED", 501); //The method is not supported by the server and cannot be handled

    //Variables:
    private String  strValue;
    private int codeValue;

    StatusCode(String strValue, int codeValue) {
        this.strValue = strValue;
        this.codeValue = codeValue;
    }

    @Override
    public String toString() {
        return this.strValue;
    }

    public int toInteger() {
        return this.codeValue;
    }
}
