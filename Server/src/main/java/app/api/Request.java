package app.api;

//Java JSON
import org.json.JSONObject;

//Java Input & Output
import java.io.IOException;
import java.io.InputStream;

//Java Utilities
import java.util.Arrays;
import java.util.Base64;

//Java Language
import java.lang.String;

/**
 * This class is used as an easy way to
 * read the client requests.
 */
public class Request {
    //Constants:
    public static final int DELETE = -11111;
    public static final int PATCH = -1111;
    public static final int POST = -111;
    public static final int GET = -11;

    //Variables:
    private Base64.Decoder decoder;
    private JSONObject jsonObject;

    /**
     * Constructs a request object. The Function
     * reads the client request as Base64 encoded
     * bytes. Later converts it to the json object.
     * @param stream the client input stream.
     * @throws IOException java.io.IOException
     */
    public Request(InputStream stream) throws IOException {
        this.decoder = Base64.getDecoder();

        byte[] jsonObjBytes = this.decoder.decode(stream.readAllBytes());

        this.jsonObject = new JSONObject(new StringBuilder(Arrays.toString(jsonObjBytes)));
    }
}
