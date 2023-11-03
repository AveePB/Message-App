//Java Custom
import app.log.Logger;
import app.server.Server;

public class Main {

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.listen();
        }
        catch (Exception ex) {
            Logger.logEMERG(ex.toString());
        }

        /* LIST OF TASKS:
            3.COMPLETE SERVER DOCS
            4.START DEVELOPMENT ON THE APP!
         */
    }
}
