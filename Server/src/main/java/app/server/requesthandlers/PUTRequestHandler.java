package app.server.requesthandlers;

//Java Custom
import app.api.APIException;
import app.api.Request;
import app.api.Response;
import app.api.StatusCode;
import app.db.DataBase;
import app.server.DataValidator;

//Java Language
import java.lang.String;

public class PUTRequestHandler {
    //Constants:
    private static String[] REGISTRATION_KEYS = new String[]{"newUserNickname", "newUserPassword"};
    private static String[] CHAT_CREATION_KEYS = new String[]{"newFriendNickname"};
    private static String[] MSG_CREATION_KEYS = new String[]{"recipientNickname", "message"};

    /**
     * This function handles the PUT request that has action called 'REGISTRATION'.
     * @param db the database interface object.
     * @param isUserLogged the boolean value.
     * @param request the PUT request with action 'REGISTRATION'.
     * @return the response object.
     * @throws APIException throws error when processing problem occurred!
     */
    public static Response handleRegistrationAction(DataBase db, boolean isUserLogged, Request request) throws APIException {
        DataValidator.validateRequest(request, isUserLogged, PUTRequestHandler.REGISTRATION_KEYS, false);

        String newUserNickname = request.getString(PUTRequestHandler.REGISTRATION_KEYS[0]);
        String newUserPassword = request.getString(PUTRequestHandler.REGISTRATION_KEYS[1]);

        Response response = request.createResponse();
        if (db.createUser(newUserNickname, newUserPassword))
            response.put("STATUS_CODE", StatusCode.CREATED.toInteger());
        else
            response.put("STATUS_CODE", StatusCode.UNAUTHORIZED.toInteger());

        return response;
    }

    /**
     * This function handles the PUT request that has action called 'CHAT_CREATION'.
     * @param db the database interface object.
     * @param isUserLogged the boolean value.
     * @param currentUserId the current user id.
     * @param request the PUT request with action 'CHAT_CREATION'.
     * @return the response object.
     * @throws APIException throws error when processing problem occurred!
     */
    public static Response handleChatCreationAction(DataBase db, boolean isUserLogged, Integer currentUserId, Request request) throws APIException {
        DataValidator.validateRequest(request, isUserLogged, PUTRequestHandler.CHAT_CREATION_KEYS,  true);

        String newFriendNickname = request.getString(PUTRequestHandler.CHAT_CREATION_KEYS[0]);

        Response response = request.createResponse();
        if (db.createChat(currentUserId, db.getUserId(newFriendNickname)))
            response.put("STATUS_CODE", StatusCode.CREATED.toInteger());
        else
            response.put("STATUS_CODE",  StatusCode.UNAUTHORIZED.toInteger());

        return response;
    }

    /**
     * This function handles the PUT request that has action called 'CHAT_CREATION'.
     * @param db the database interface object.
     * @param isUserLogged the boolean value.
     * @param currentUserId the current user id.
     * @param request the PUT request with action 'CHAT_CREATION'.
     * @return the response object.
     * @throws APIException throws error when processing problem occurred!
     */
    public static Response handleMsgCreationAction(DataBase db, boolean isUserLogged, Integer currentUserId, Request request) throws APIException {
        DataValidator.validateRequest(request, isUserLogged, PUTRequestHandler.MSG_CREATION_KEYS, true);

        String recipientNickname = request.getString(PUTRequestHandler.MSG_CREATION_KEYS[0]);
        String message = request.getString(PUTRequestHandler.MSG_CREATION_KEYS[1]);

        int recipientId = db.getUserId(recipientNickname);
        int chatId = db.getChatId(currentUserId, recipientId);

        Response response = request.createResponse();
        if (db.createMessage(chatId, currentUserId, message))
            response.put("STATUS_CODE", StatusCode.CREATED.toInteger());
        else
            response.put("STATUS_CODE", StatusCode.FORBIDDEN.toInteger());

        return response;
    }
}
