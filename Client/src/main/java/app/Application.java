package app;

//Java Custom
import app.gui.authwindow.AuthWindow;

//Java Input & Output
import java.io.IOException;

//Java Networking
import java.net.Socket;

/**
 * This is the application class used to
 * start the authorization window.
 */
public class Application {
    //Variables:
    private AuthWindow authWindow;

    public Application() throws IOException {
        //Initializing the socket.
        Socket sock = new Socket(Config.SERVER_IP, Config.SERVER_PORT);

        //Initializing the application authorization window.
        this.authWindow = new AuthWindow(sock);
    }

    public void run() {
        this.authWindow.open();
    }
}
