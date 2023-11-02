package app.server;

//Java Custom
import app.api.APIException;
import app.api.Request;
import app.api.StatusCode;
import app.db.DataBase;

//Java Networking
import java.io.OutputStream;
import java.net.Socket;

//Java Utilities
import java.util.Map;

//Java language
import java.lang.Integer;
import java.lang.String;

/**
 * This class handles different kinds
 * of requests.
 */
public class RequestHandler {
    //Variables:
    private Map<Integer, OutputStream> activeUsers;
    private Integer currentUserId;
    private Socket sock;
    private DataBase db;

    /**
     * Constructs a request handler object.
     * @param activeUsers the active users.
     * @param sock the client socket.
     * @param db the database interface.
     */
    public RequestHandler(Map<Integer, OutputStream> activeUsers, Socket sock, DataBase db) {
        this.activeUsers = activeUsers;
        this.sock = sock;
        this.db = db;
    }

    public void handleUNKNOWN(Request request) {
        System.out.println("CLIENT SENT UNKNOWN REQUEST!!");
    }

    public void handleDELETE(Request request) throws APIException {
        //There are no DELETE requests.
        throw new APIException(StatusCode.NOT_IMPLEMENTED);
    }

    public void handlePOST(Request request) throws APIException {
        //There are no POST requests.
        throw new APIException(StatusCode.NOT_IMPLEMENTED);
    }

    public void handlePUT(Request request) throws APIException {

        //REGISTRATION <- newUserNickname, newUserPassword.
        if (request.hasKey("newUserNickname") && request.hasKey("newUserPassword")) {
            if (this.currentUserId != null)
                throw new APIException(StatusCode.FORBIDDEN);

            if (!(request.isValueString("newUserNickname")) || !(request.isValueString("newUserPassword")))
                throw new APIException(StatusCode.BAD_REQUEST);

            String newUserNickname = request.getString("newUserNickname");
            String newUserPassword = request.getString("newUserPassword");

            /*
                CREATING NEW ACCOUNT
             */
        }
        //CHAT CREATION <- currentUserNickname, chatMemberNickname.
        else if (request.hasKey("currentUserNickname") && request.hasKey("chatMemberNickname")) {
            if (this.currentUserId == null)
                throw new APIException(StatusCode.UNAUTHORIZED);

            if (!(request.isValueString("currentUserNickname")) || !(request.isValueString("chatMemberNickname")))
                throw new APIException(StatusCode.BAD_REQUEST);

            String currentUserNickname = request.getString("currentUserNickname");
            String chatMemberNickname = request.getString("chatMemberNickname");

            /*
                CREATING NEW CHAT
             */
        }
        //MESSAGE CREATION <- recipientNickname, message, currentUserNickname.
        else if (request.hasKey("recipientNickname") && request.hasKey("message") && request.hasKey("currentUserNickname")) {
            if (this.currentUserId == null)
                throw new APIException(StatusCode.UNAUTHORIZED);

            if (!(request.isValueString("recipientNickname")) || !(request.isValueString("message")) || !(request.isValueString("currentUserNickname")))
                throw new APIException(StatusCode.BAD_REQUEST);

            String recipientNickname = request.getString("recipientNickname");
            String message = request.getString("message");
            String currentUserNickname = request.getString("currentUserNickname");

            /*
               CREATING NEW MESSAGE
             */
        }
        else {
            throw new APIException(StatusCode.NOT_IMPLEMENTED);
        }
    }

    public void handleGET(Request request) throws APIException {

        //AUTHENTICATION <- authUserNickname, authUserPassword.
        if (request.hasKey("authUserNickname") && request.hasKey("authUserPassword")) {
            if (this.currentUserId != null)
                throw new APIException(StatusCode.FORBIDDEN);

            if (!(request.isValueString("authUserNickname")) || !(request.isValueString("authUserPassword")))
                throw new APIException(StatusCode.BAD_REQUEST);

            String authUserNickname = request.getString("authUserNickname");
            String authUserPassword = request.getString("authUserPassword");

            /*
                AUTHENTICATING
             */
        }
        //READING CHAT MESSAGES <- currentUserNickname, chatMemberNickname.
        else if (request.hasKey("currentUserNickname") && request.hasKey("chatMemberNickname")) {
            if (this.currentUserId == null)
                throw new APIException(StatusCode.UNAUTHORIZED);

            if (!request.isValueString("currentUserNickname") && !(request.isValueString("chatMemberNickname")))
                throw new APIException(StatusCode.BAD_REQUEST);

            String currentUserNickname = request.getString("currentUserNickname");
            String chatMemberNickname = request.getString("chatMemberNickname");

            /*
                READING THE CHAT MESSAGES
             */
        }
        //GETTING CHAT LIST <- currentUserNickname.
        else if (request.hasKey("currentUserNickname")) {
            if (this.currentUserId == null)
                throw new APIException(StatusCode.UNAUTHORIZED);

            if (!request.isValueString("currentUserNickname"))
                throw new APIException(StatusCode.BAD_REQUEST);

            String currentUserNickname = request.getString("currentUserNickname");

            /*
             GETTING CHAT LIST
             */
        }
        else {
            throw new APIException(StatusCode.NOT_IMPLEMENTED);
        }
    }
}
