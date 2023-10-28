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
    }
}
