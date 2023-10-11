package app.api;

public class Client {
    //API
    public static final String API_SEPARATOR = "_+S3WFSDFSFDSFdP+_";
    public static final String API_SERVER_IP_ADDRESS = "localhost";
    public static final int API_SERVER_PORT = 55555;

    //The Client parameters:
    private String separator;
    private String serverIp;
    private int serverPort;

    /**
     * Constructs a client object.
     * @param separator the request/response element separator.
     * @param serverIp the server ip address.
     * @param serverPort the server port.
     */
    public Client(String separator, String serverIp, int serverPort) {
        this.separator = separator;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }
}
