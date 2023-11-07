package app.api;

//Java JSON
import org.json.JSONObject;

//Java Input & Output
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//Java Utilities
import java.util.Base64;

//Java Language
import java.lang.String;

/**
 * This class is used as an easy way to read
 * responses from the server.
 */
public class Response {
    //Variables:
    private Base64.Decoder decoder;
    private JSONObject jsonObject;

    /**
     * Constructs a response object. The Function
     * reads the server response from Base64 encoded
     * bytes. Later converts it to the json object.
     * @param stream the server input stream.
     * @throws IOException java.io.IOException
     */
    public Response(InputStream stream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        this.decoder = Base64.getDecoder();

        byte[] jsonObjBytes =  this.decoder.decode(br.readLine());
        this.jsonObject = new JSONObject(new String(jsonObjBytes));
    }

    @Override
    public String toString() {
        return this.jsonObject.toString();
    }

    /**
     * Checks if response has such field.
     * @param key the field name.
     * @return true if response satisfies the condition otherwise false.
     */
    public boolean hasKey(String key) {
        return this.jsonObject.has(key);
    }

    /**
     * Checks if the response field is string.
     * @param key the field name.
     * @return true if response satisfies the condition otherwise false.
     */
    public boolean isValueString(String key) {
        Object obj = this.jsonObject.get(key);
        return obj instanceof String;
    }

    /**
     * Checks if the response field is int.
     * @param key the field name.
     * @return true if response satisfies the condition otherwise false.
     */
    public boolean isValueInt(String key) {
        Object obj = this.jsonObject.get(key);
        return obj instanceof Integer;
    }

    /**
     * Returns the static int value.
     * @return the response type.
     */
    public int getType() {
        return this.jsonObject.getInt("TYPE");
    }

    /**
     * Returns the static int value.
     * @return the response action type.
     */
    public int getAction() {
        return this.jsonObject.getInt("ACTION");
    }

    /**
     * Returns the status code object.
     * @return the status code object.
     */
    public StatusCode getStatusCode() {
        return StatusCode.getInstance(this.jsonObject.getInt("STATUS_CODE"));
    }
}
