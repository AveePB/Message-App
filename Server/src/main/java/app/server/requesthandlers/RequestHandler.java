package app.server.requesthandlers;

//Java Custom
import app.api.APIException;
import app.api.Request;
import app.api.Response;
import app.api.StatusCode;
import app.db.DataBase;
import app.log.Logger;
import app.server.DataValidator;

//Java Networking
import java.io.IOException;
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
    //CONSTANTS:
    private static String[] AUTH_KEYS = new String[]{"authUserNickname", "authUserPassword"};
    private static String[] CHAT_READING_KEYS = new String[]{"friendNickname"};

    private static String[] REGISTRATION_KEYS = new String[]{"newUserNickname", "newUserPassword"};
    private static String[] CHAT_CREATION_KEYS = new String[]{"newFriendNickname"};
    private static String[] MSG_CREATION_KEYS = new String[]{"recipientNickname", "message"};

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

    public void handleUNKNOWN(Request request, APIException ex) throws IOException {
        Logger.logWARN(this.sock.getInetAddress().getHostAddress() + " has caused " + ex.toString());

        Response response = request.createResponse();
        response.put("STATUS_CODE", ex.getStatusCode().toInteger());

        response.sendToClient(this.sock.getOutputStream());
        Logger.logINFO("STATUS CODE " + response.getStatusCode() + " sent to " + this.sock.getInetAddress().getHostAddress());
    }

    public void handleDELETE(Request request) throws APIException {
        //There are no DELETE requests.
        throw new APIException(StatusCode.NOT_IMPLEMENTED);
    }

    public void handlePOST(Request request) throws APIException {
        //There are no POST requests.
        throw new APIException(StatusCode.NOT_IMPLEMENTED);
    }

    public void handlePUT(Request request) throws APIException, IOException {
        boolean isUserLoggedIn = (this.currentUserId != null);
        Response response = null;

        if (request.getAction() == Request.REGISTRATION) {
            response = PUTRequestHandler.handleRegistrationAction(this.db, isUserLoggedIn, request);
        }

        else if (request.getAction() == Request.CHAT_CREATION) {
            response = PUTRequestHandler.handleChatCreationAction(this.db, isUserLoggedIn, this.currentUserId, request);

            int newFriendId = this.db.getUserId(request.getString("newFriendNickname"));

            //Sends information to the friend about new friendship.
            if (this.activeUsers.containsKey(newFriendId))
                response.sendToClient(this.activeUsers.get(newFriendId));
        }

        else if (request.getAction() == Request.MSG_CREATION) {
            response = PUTRequestHandler.handleMsgCreationAction(this.db, isUserLoggedIn, this.currentUserId, request);

            int friendId = this.db.getUserId(request.getString("recipientNickname"));

            //Sends information to the friend about new message.
            if (this.activeUsers.containsKey(friendId))
                response.sendToClient(this.activeUsers.get(friendId));
        }

        else {
            throw new APIException(StatusCode.NOT_IMPLEMENTED);
        }

        response.sendToClient(this.sock.getOutputStream());
        Logger.logINFO("STATUS CODE " + response.getStatusCode() + " sent to " + this.sock.getInetAddress().getHostAddress());
    }

    public void handleGET(Request request) throws APIException, IOException {
        boolean isUserLoggedIn = (this.currentUserId != null);
        Response response = null;

        if (request.getAction() == Request.AUTHENTICATION) {
            response = GETRequestHandler.handleAuthenticationAction(this.db, isUserLoggedIn, request);

            //Sets current user id if authentication was successful.
            if (response.getStatusCode() == StatusCode.OK.toInteger())
                this.currentUserId = this.db.getUserId(request.getString("authUserNickname"));
        }

        else if (request.getAction() == Request.READING_CHAT) {
            response = GETRequestHandler.handleReadingChatAction(this.db, isUserLoggedIn, this.currentUserId, request);
        }

        else if (request.getAction() == Request.LISTING_FRIENDS) {
            response = GETRequestHandler.handleListingFriendsAction(this.db, isUserLoggedIn, this.currentUserId, request);
        }

        else {
            throw new APIException(StatusCode.NOT_IMPLEMENTED);
        }

        response.sendToClient(this.sock.getOutputStream());
        Logger.logINFO("STATUS CODE " + response.getStatusCode() + " sent to " + this.sock.getInetAddress().getHostAddress());
    }
}
