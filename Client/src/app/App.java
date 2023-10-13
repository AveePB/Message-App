package app;

//Java I/O (Input and Output)
import java.io.IOException;

//Java Custom Packages
import app.api.Client;
import app.windows.authwindow.AuthWindow;


public class App {
    //The application window:
    private AuthWindow authWindow;

    //The application API:
    private Client client;

    /**
     * Constructs an app object.
     */
    public App() throws IOException  {
        //Initializing the API.
        this.client = new Client(Client.API_SEPARATOR, Client.API_SERVER_IP_ADDRESS, Client.API_SERVER_PORT);

        //Initializing the application forms.
        this.authWindow = new AuthWindow(this.client);
    }

    /**
     * Runs an application.
     */
    public void run() {
        this.authWindow.open();
    }
}
