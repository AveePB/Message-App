package app.api;

//Java JSON
import org.json.JSONException;
import org.json.JSONObject;

//Java Input & Output
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//Java Utilities
import java.util.Base64;

//Java Language
import java.lang.Integer;
import java.lang.String;

/**
 * This class is used as an easy way to
 * read the client requests.
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

    //Variables:
    private Base64.Decoder decoder;
    protected JSONObject jsonObject;

    /**
     * Constructs a request object. The Function
     * reads the client request as Base64 encoded
     * bytes. Later converts it to the json object.
     * @param stream the client input stream.
     * @throws IOException java.io.IOException
     */
    public Request(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        this.decoder = Base64.getDecoder();

        byte[] jsonObjBytes = this.decoder.decode(br.readLine());
        this.jsonObject = new JSONObject(new String(jsonObjBytes));
    }

    @Override
    public String toString() {
        return this.jsonObject.toString();
    }

    /**
     * Function compares request types.
     * @param requestType the wanted request type.
     * @return true if request satisfies the condition otherwise false.
     */
    private boolean isRequest(int requestType) {
        try {
            int type = this.jsonObject.getInt("TYPE");
            if (type != requestType) return false;
        }
        catch (JSONException ex) {
            return false;
        }
        return true;
    }

    /**
     * Function checks if request is the DELETE.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isDELETE() {
        return isRequest(Request.DELETE);
    }

    /**
     * Function checks if request is the POST.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isPOST() {
        return isRequest(Request.POST);
    }

    /**
     * Function checks if request is the PATCH.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isPUT() {
        return isRequest(Request.PUT);
    }

    /**
     * Function checks if request is the GET.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isGET() {
        return isRequest(Request.GET);
    }

    /**
     * Checks if request has such field.
     * @param key the field name.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean hasKey(String key) {
        return this.jsonObject.has(key);
    }

    /**
     * Checks if the request field is string.
     * @param key the field name.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isValueString(String key) {
        Object obj = this.jsonObject.get(key);
        return obj instanceof String;
    }

    /**
     * Checks if the request field is int.
     * @param key the field name.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isValueInt(String key) {
        Object obj = this.jsonObject.get(key);
        return obj instanceof Integer;
    }

    /**
     * Returns the static int value.
     * @return the request action type.
     */
    public int getAction() {
        try {
            return this.jsonObject.getInt("ACTION");
        }
        catch (Exception ignored) { }

        return Request.NO_ACTION;
    }


    /**
     * Returns an int value behind the key.
     * @param key the field name.
     * @return the int value.
     */
    public int getInt(String key) {
        return this.jsonObject.getInt(key);
    }

    /**
     * Returns a string value behind the key.
     * @param key the field name.
     * @return the string value.
     */
    public String getString(String key) {
        return this.jsonObject.getString(key);
    }

    /**
     * Creates response based on the current request.
     * @return the response object.
     */
    public Response createResponse() {
        Response response = new Response();
        for (String key: this.jsonObject.keySet()) {
            Object obj = this.jsonObject.get(key);
            if (obj instanceof Integer)
                response.put(key, (Integer) obj);
            else if (obj instanceof String)
                response.put(key, (String) obj);
        }
        return response;
    }
}
