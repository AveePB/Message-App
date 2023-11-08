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

public class GETRequestHandler {
    //Constants:
    private static String[] AUTH_KEYS = new String[]{"authUserNickname", "authUserPassword"};
    private static String[] CHAT_READING_KEYS = new String[]{"friendNickname"};

    /**
     * This function handles the GET request that has action called 'AUTHENTICATION'.
     * @param db the database interface object.
     * @param isUserLogged the boolean value.
     * @param request the GET request with action 'AUTHENTICATION'.
     * @return the response object.
     * @throws APIException throws error when processing problem occurred!
     */
    public static Response handleAuthenticationAction(DataBase db, boolean isUserLogged , Request request) throws APIException {
        DataValidator.validateRequest(request, isUserLogged, GETRequestHandler.AUTH_KEYS, false);

        String authUserNickname = request.getString(GETRequestHandler.AUTH_KEYS[0]);
        String authUserPassword = request.getString(GETRequestHandler.AUTH_KEYS[1]);

        Response response = request.createResponse();
        if (db.validateUserData(authUserNickname, authUserPassword))
            response.put("STATUS_CODE", StatusCode.OK.toInteger());
        else
            response.put("STATUS_CODE", StatusCode.UNAUTHORIZED.toInteger());

        return response;
    }

    /**
     * This function handles the GET request that has action called 'READING_CHAT'.
     * @param db the database interface object.
     * @param isUserLogged the boolean value.
     * @param currentUserId the current user id.
     * @param request the GET request with action 'READING_CHAT'.
     * @return the response object.
     * @throws APIException throws error when processing problem occurred!
     */
    public static Response handleReadingChatAction(DataBase db, boolean isUserLogged, Integer currentUserId, Request request) throws APIException {
        DataValidator.validateRequest(request, isUserLogged, GETRequestHandler.CHAT_READING_KEYS, true);

        String friendNickname = request.getString(GETRequestHandler.CHAT_READING_KEYS[0]);
        int chatId = db.getChatId(currentUserId, db.getUserId(friendNickname));

        Response response = request.createResponse();
        response.put("STATUS_CODE", StatusCode.OK.toInteger());
        response.put("chatMessages", db.getMessages(chatId));

        return response;
    }

    /**
     * This function handles the GET request that has action called 'LISTING_FRIENDS'.
     * @param db the database interface object.
     * @param isUserLogged the boolean value.
     * @param currentUserId the current user id.
     * @param request the GET request with action 'LISTING_FRIENDS'.
     * @return the response object.
     * @throws APIException throws error when processing problem occurred!
     */
    public static Response handleListingFriendsAction(DataBase db, boolean isUserLogged, Integer currentUserId, Request request) throws APIException{
        DataValidator.validateRequest(request, isUserLogged, new String[]{}, true);

        Response response = request.createResponse();
        response.put("STATUS_CODE", StatusCode.OK.toInteger());
        response.put("friendList", db.getFriendList(currentUserId));

        return response;
    }
}
