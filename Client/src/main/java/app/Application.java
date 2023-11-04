package app;

//Java Custom
import app.api.Client;
import app.Config;
import app.gui.authwindow.AuthWindow;

/**
 * This is the application class used to
 * start the authorization window.
 */
public class Application {
    //Variables:
    private AuthWindow authWindow;
    private Client client;

    public Application() {
        //Initializing the API interface.
        //this.client = new Client(Config.SERVER_IP_ADDRESS, Config.SERVER_PORT);

        //Initializing the application authorization window.
        //this.authWindow = new AuthWindow(this.client);
    }

    public void run() {
        //this.authWindow.open();
    }
}
