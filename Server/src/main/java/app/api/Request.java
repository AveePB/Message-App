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
     * (...)
     * }
     */
    //Constants:
    public static final int DELETE = -11111;
    public static final int PATCH = -1111;
    public static final int POST = -111;
    public static final int GET = -11;

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
     * Function checks if request is the PATCH.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isPATCH() {
        return isRequest(Request.PATCH);
    }

    /**
     * Function checks if request is the POST.
     * @return true if request satisfies the condition otherwise false.
     */
    public boolean isPOST() {
        return isRequest(Request.POST);
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
    public boolean isField(String key) {
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
}
