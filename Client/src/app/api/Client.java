package app.api;

//Java Networking (API)
import java.io.InputStreamReader;
import java.net.Socket;

//Java I/O (Input and Output)
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

//Java Language (Fundamental Classes)
import java.lang.String;


public class Client {
    //API
    public static final String API_SEPARATOR = "_+S3WFSDFSFDSFdP+_";
    public static final String API_SERVER_IP_ADDRESS = "localhost";
    public static final int API_SERVER_PORT = 55555;

    //The Client parameters:
    private boolean isLogged;
    private String separator;
    private String serverIp;
    private int serverPort;

    //The client objects:
    private BufferedReader br;
    private PrintWriter pw;
    private Socket sock;

    //The client nickname & password:
    private String nickname;
    private String password;

    /**
     * Constructs a client object.
     * @param separator the request/response element separator.
     * @param serverIp the server ip address.
     * @param serverPort the server port.
     */
    public Client(String separator, String serverIp, int serverPort) throws IOException {
        this.separator = separator;
        this.serverIp = serverIp;
        this.serverPort = serverPort;

        this.sock = new Socket(this.serverIp, this.serverPort);
        this.isLogged = false;

        this.br = new BufferedReader(new InputStreamReader(this.sock.getInputStream()));
        this.pw = new PrintWriter(this.sock.getOutputStream());
    }

    /**
     * Sends the login request to the server.
     * @param nickname the user nickname.
     * @param password the user password.
     */
    public void sendLoginRequest(String nickname, String password) {
        if (this.isLogged) return;

        this.pw.println(Request.LOGIN + this.separator + nickname + this.separator + password);
        this.pw.flush();

        this.nickname = nickname;
        this.password = password;
    }

    /**
     * Sends the logout request to the server.
     */
    public void sendLogoutRequest() {
        if (!this.isLogged) return;

        this.pw.println(Request.LOGOUT);
        this.pw.flush();
    }

    /**
     * Sends the register request to the server.
     * @param nickname the user nickname.
     * @param password the user password.
     */
    public void sendRegisterRequest(String nickname, String password) {
        if (this.isLogged) return;

        this.pw.println(Request.REGISTER + this.separator + nickname + this.separator + password);
        this.pw.flush();

        this.nickname = nickname;
        this.password = password;
    }

    /**
     * Sends the create chat request to the server.
     * @param nickname the user nickname.
     */
    public void sendCreateChatRequest(String nickname) {
        if (!this.isLogged) return;

        this.pw.println(Request.CREATE_CHAT + this.separator + nickname);
        this.pw.flush();
    }

    /**
     * Sends the create message request to the server.
     * @param nickname the user nickname.
     * @param message the message.
     */
    public void sendCreateMessageRequest(String nickname, String message) {
        if (!this.isLogged) return;

        this.pw.println(Request.CREATE_MSG + this.separator + nickname + this.separator + message);
        this.pw.flush();
    }

    /**
     * Sends the get chat users request to the server.
     */
    public void sendGetChatUsersRequest() {
        if (!this.isLogged) return;

        this.pw.println(Request.GET_CHAT_USERS);
        this.pw.flush();
    }

    /**
     * Returns the client's nickname.
     * @return the nickname (String).
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Returns the client's password.
     * @return the password (String).
     */
    public String getPassword() {
        return this.password;
    }

    /*
        2.CREATE FUNCTION LISTENING FOR RESPONSES.
        3.CREATE THREAD FUNCTION THAT LISTENS FOR RESPONSES.
     */
}
