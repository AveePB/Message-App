package app.server;

//Java Custom
import app.api.Request;
import app.api.UnknownRequest;
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
    //Variables
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

    public void handleDELETE(Request request) throws UnknownRequest {
        //There are no DELETE requests.
        throw new UnknownRequest("UNKNOWN DELETE REQUEST!!!");
    }

    public void handlePOST(Request request) throws UnknownRequest {
        //There are no POST requests.
        throw new UnknownRequest("UNKNOWN POST REQUEST!!!");
    }

    public void handlePUT(Request request) throws UnknownRequest {
        try {
            //REGISTRATION <- newUserNickname, newUserPassword.
            if ((this.currentUserId == null) && (request.hasKey("newUserNickname") && request.hasKey("newUserPassword"))) {
                String newUserNickname = request.getString("newUserNickname");
                String newUserPassword = request.getString("newUserPassword");


                System.out.println("CLIENT WANTS TO CREATE NEW ACCOUNT!!!");
            }
            //CHAT CREATION <- currentUserNickname, chatMemberNickname.
            else if ((this.currentUserId != null) && (request.hasKey("currentUserNickname") && request.hasKey("chatMemberNickname"))) {
                String currentUserNickname = request.getString("currentUserNickname");
                String chatMemberNickname = request.getString("chatMemberNickname");



                System.out.println("CLIENT WANTS TO CREATE NEW CHAT!!!");
            }
            //MESSAGE CREATION <- recipientNickname, message, currentUserNickname.
            else if ((this.currentUserId != null) && (request.hasKey("recipientNickname") && request.hasKey("message") && request.hasKey("currentUserNickname"))) {
                String recipientNickname = request.getString("recipientNickname");
                String message = request.getString("message");
                String currentUserNickname = request.getString("currentUserNickname");



                System.out.println("CLIENT WANTS TO CREATE NEW MESSAGE!!!");
            }
            else {
                throw new UnknownRequest("UNKNOWN PUT REQUEST!!!");
            }
        }
        catch (Exception ex) {
            throw new UnknownRequest("UNKNOWN PUT REQUEST!!!");
        }
    }

    public void handleGET(Request request) throws UnknownRequest {
        try {
            //AUTHENTICATION <- authUserNickname, authUserPassword.
            if ((this.currentUserId == null) && (request.hasKey("authUserNickname") && request.hasKey("authUserPassword"))) {
                String authUserNickname = request.getString("authUserNickname");
                String authUserPassword = request.getString("authUserPassword");



                System.out.println("CLIENT WANTS THE AUTHENTICATION!!!");
            }
            //GETTING CHAT LIST <- currentUserNickname.
            else if ((this.currentUserId != null) && (request.hasKey("currentUserNickname"))) {
                String currentUserNickname = request.getString("currentUserNickname");



                System.out.println("CLIENT WANTS TO GET CHAT LIST!!!");
            }
            //READING CHAT MESSAGES <- currentUserNickname, chatMemberNickname.
            else if ((this.currentUserId != null) && (request.hasKey("currentUserNickname") && request.hasKey("chatMemberNickname"))) {
                String currentUserNickname = request.getString("currentUserNickname");
                String chatMemberNickname = request.getString("chatMemberNickname");



                System.out.println("CLIENT WANTS TO READ CHAT MESSAGES!!!");
            }
            else {
                throw new UnknownRequest("UNKNOWN GET REQUEST!!!");
            }
        }
        catch (Exception ex) {
            throw new UnknownRequest("UNKNOWN GET REQUEST!!!");
        }
    }
}
