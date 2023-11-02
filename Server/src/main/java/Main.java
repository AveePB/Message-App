//Java Custom
import app.server.Server;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.listen();
        }
        catch (Exception ex) {
            System.out.println("Server setup failed!!!");
            System.out.println(ex.toString());
        }

        /* LIST OF TASKS:
            1.TEST CURRENT SERVER FUNCTIONS (SERVER HANDLER)
            2.CREATE THE LAST FUNCTIONALITY (SENDING MESSAGES)
            3.CREATE THE LOGGER
            4.COMPLETE SERVER DOCS
            4.START DEVELOPMENT ON THE APP!
         */
    }
}
