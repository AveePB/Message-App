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
 * This class is used as an easy way to create
 * responses to the clients.
 */
public class Response {
    //Variables:
    private Base64.Encoder encoder;
    private JSONObject jsonObject;

    /**
     * Constructs an empty response object.
     */
    public Response() {
        this.encoder = Base64.getEncoder();
        this.jsonObject = new JSONObject();
    }

    /**
     * Appends a new field in the response.
     * @param key the field name.
     * @param value the field value.
     */
    public void put(String key, String value) {
        this.jsonObject.put(key, value);
    }

    /**
     * Appends a new field in the response.
     * @param key the field name.
     * @param value the field value.
     */
    public void put(String key, int value) {
        this.jsonObject.put(key, value);
    }

    /**
     * Function sends the response as json object to the client.
     * The json object is secured by the Base64 encoding.
     * @param stream the client output stream.
     * @throws IOException java.io.IOException
     */
    public void sendToClient(OutputStream stream) throws IOException {
        byte[] jsonObjBytes = this.jsonObject.toString().getBytes();
        PrintWriter pw = new PrintWriter(stream);

        pw.println(new String(this.encoder.encode(jsonObjBytes)));
        pw.flush();
    }
}
