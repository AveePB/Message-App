package app.server.handlers;

//Java Custom
import app.api.APIException;
import app.api.Request;
import app.api.Response;
import app.api.StatusCode;
import app.db.DataBase;
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
        Response response = request.createResponse();
        response.put("STATUS_CODE", ex.getStatusCode().toInteger());

        response.sendToClient(this.sock.getOutputStream());
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
        Response response = request.createResponse();

        if (request.getAction() == Request.REGISTRATION) {
            DataValidator.validateRequest(request, this.currentUserId, RequestHandler.REGISTRATION_KEYS, false);

            String newUserNickname = request.getString(RequestHandler.REGISTRATION_KEYS[0]);
            String newUserPassword = request.getString(RequestHandler.REGISTRATION_KEYS[1]);


            if (this.db.createUser(newUserNickname, newUserPassword))
                response.put("STATUS_CODE", StatusCode.OK.toInteger());
            else
                response.put("STATUS_CODE", StatusCode.UNAUTHORIZED.toInteger());
            response.sendToClient(this.sock.getOutputStream());
        }

        else if (request.getAction() == Request.CHAT_CREATION) {
            DataValidator.validateRequest(request, this.currentUserId, RequestHandler.CHAT_CREATION_KEYS,  true);

            String newFriendNickname = request.getString(RequestHandler.CHAT_CREATION_KEYS[0]);


            if (this.db.createChat(this.currentUserId, this.db.getUserId(newFriendNickname)))
                response.put("STATUS_CODE", StatusCode.OK.toInteger());
            else
                response.put("STATUS_CODE",  StatusCode.UNAUTHORIZED.toInteger());
            response.sendToClient(this.sock.getOutputStream());
        }

        else if (request.getAction() == Request.MSG_CREATION) {
            DataValidator.validateRequest(request, this.currentUserId, RequestHandler.MSG_CREATION_KEYS, true);

            String recipientNickname = request.getString(RequestHandler.MSG_CREATION_KEYS[0]);
            String message = request.getString(RequestHandler.MSG_CREATION_KEYS[1]);

            int recipientId = this.db.getUserId(recipientNickname);
            int chatId = this.db.getChatId(this.currentUserId, recipientId);


            if (this.db.createMessage(chatId, this.currentUserId, message)) {
                response.put("STATUS_CODE", StatusCode.OK.toInteger());

                if (this.activeUsers.containsKey(recipientId))
                    response.sendToClient(this.activeUsers.get(recipientId));
                response.sendToClient(this.sock.getOutputStream());
            }
            else {
                response.put("STATUS_CODE", StatusCode.FORBIDDEN.toInteger());
                response.sendToClient(this.sock.getOutputStream());
            }
        }

        else {
            throw new APIException(StatusCode.NOT_IMPLEMENTED);
        }
    }

    public void handleGET(Request request) throws APIException, IOException {
        Response response = request.createResponse();

        if (request.getAction() == Request.AUTHENTICATION) {
            DataValidator.validateRequest(request, this.currentUserId, RequestHandler.AUTH_KEYS, false);

            String authUserNickname = request.getString(RequestHandler.AUTH_KEYS[0]);
            String authUserPassword = request.getString(RequestHandler.AUTH_KEYS[1]);


            if (this.db.validateUserData(authUserNickname, authUserPassword)) {
                response.put("STATUS_CODE", StatusCode.OK.toInteger());
                this.currentUserId = this.db.getUserId(authUserNickname);
            }
            else {
                response.put("STATUS_CODE", StatusCode.UNAUTHORIZED.toInteger());
            }

            response.sendToClient(this.sock.getOutputStream());
        }

        else if (request.getAction() == Request.READING_CHAT) {
            DataValidator.validateRequest(request, this.currentUserId, RequestHandler.CHAT_READING_KEYS, true);

            String friendNickname = request.getString(RequestHandler.CHAT_READING_KEYS[0]);
            int chatId = this.db.getChatId(this.currentUserId, this.db.getUserId(friendNickname));

            response.put("STATUS_CODE", StatusCode.OK.toInteger());
            response.put("chatMessages", this.db.getMessages(chatId));
            response.sendToClient(this.sock.getOutputStream());
        }

        else if (request.getAction() == Request.LISTING_FRIENDS) {
            DataValidator.validateRequest(request, this.currentUserId, new String[]{}, true);

            response.put("STATUS_CODE", StatusCode.OK.toInteger());
            response.put("FRIENDS", this.db.getFriendList(this.currentUserId));

            response.sendToClient(this.sock.getOutputStream());
        }

        else {
            throw new APIException(StatusCode.NOT_IMPLEMENTED);
        }
    }
}
