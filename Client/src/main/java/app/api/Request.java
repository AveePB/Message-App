package app.api;

//Java JSON
import org.json.JSONObject;

//Java Input & Output
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

//Java Utilities
import java.util.Base64;

//Java Language
import java.lang.String;

/**
 * This class is used as an easy way to send
 * requests to the server.
 */
public class Request {
    /* REQUEST STRUCTURE {
     * int TYPE,
     * int ACTION,
     * (...)
     * }
     */
    //Constants:
    //REQUEST TYPES
    public static final int DELETE = -11111;
    public static final int POST = -1111;
    public static final int PUT = -111;
    public static final int GET = -11;

    //ACTION TYPES
    public static final int LISTING_FRIENDS = -1234; //GET
    public static final int AUTHENTICATION = -1233; //GET
    public static final int READING_CHAT = -1232; //GET

    public static final int CHAT_CREATION = -1231; //PUT
    public static final int REGISTRATION = -1230; //PUT
    public static final int MSG_CREATION = -1229; //PUT

    public static final int NO_ACTION = 0;

    //Encoder
    private static Base64.Encoder encoder = Base64.getEncoder();

    /**
     * Creates the GET request asking to list friends.
     * @param stream the server output stream.
     * @throws IOException java.io.IOException
     */
    public static void sendListingFriendsAction(OutputStream stream) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("TYPE", Request.GET);
        obj.put("ACTION", Request.LISTING_FRIENDS);

        Request.sendToServer(stream, obj);
    }

    /**
     * Creates the GET request asking to authenticate.
     * @param stream the server output stream.
     * @param authUserNickname the user nickname.
     * @param authUserPassword the user password.
     * @throws IOException java.io.IOException
     */
    public static void sendAuthenticationAction(OutputStream stream, String authUserNickname, String authUserPassword) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("TYPE", Request.GET);
        obj.put("ACTION", Request.AUTHENTICATION);
        obj.put("authUserNickname", authUserNickname);
        obj.put("authUserPassword", authUserPassword);

        Request.sendToServer(stream, obj);
    }

    /**
     * Creates the GET request asking to read chat messages.
     * @param stream the server output stream.
     * @param friendNickname the user's friend nickname.
     * @throws IOException java.io.IOException
     */
    public static void sendReadingChatAction(OutputStream stream, String friendNickname) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("TYPE", Request.GET);
        obj.put("ACTION", Request.READING_CHAT);
        obj.put("authUserNickname", friendNickname);

        Request.sendToServer(stream, obj);
    }


    /**
     * Creates the PUT request asking to create a new user.
     * @param stream the server output stream.
     * @param newUserNickname the new user nickname.
     * @param newUserPassword the new user password.
     * @throws IOException java.io.IOException
     */
    public static void sendRegistrationAction(OutputStream stream, String newUserNickname, String newUserPassword) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("TYPE", Request.PUT);
        obj.put("ACTION", Request.REGISTRATION);
        obj.put("newUserNickname", newUserNickname);
        obj.put("newUserPassword", newUserPassword);

        Request.sendToServer(stream, obj);
    }


    /**
     * Creates the PUT request asking to create a new chat.
     * @param stream the server output stream.
     * @param newFriendNickname the new friend nickname.
     * @throws IOException java.io.IOException
     */
    public static void sendChatCreationAction(OutputStream stream, String newFriendNickname) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("TYPE", Request.PUT);
        obj.put("ACTION", Request.CHAT_CREATION);
        obj.put("newFriendNickname", newFriendNickname);

        Request.sendToServer(stream, obj);
    }

    /**
     * Creates the PUT request asking to create a new message.
     * @param stream the server output stream.
     * @param recipientNickname the recipient nickname.
     * @param message the message.
     * @throws IOException java.io.IOException
     */
    public static void sendChatCreationAction(OutputStream stream, String recipientNickname, String message) throws IOException {
        JSONObject obj = new JSONObject();
        obj.put("TYPE", Request.PUT);
        obj.put("ACTION", Request.MSG_CREATION);
        obj.put("recipientNickname", recipientNickname);
        obj.put("message", message);

        Request.sendToServer(stream, obj);
    }

    /**
     * Function sends the request as json object to the server.
     * The json object is secured by the Base64 encoding.
     * @param stream the server output stream.
     * @param obj the json object.
     */
    private static void sendToServer(OutputStream stream, JSONObject obj) {
        byte[] jsonObjBytes = obj.toString().getBytes();
        PrintWriter pw = new PrintWriter(stream);

        pw.println(new String(Request.encoder.encode(jsonObjBytes)));
        pw.flush();
    }
}
