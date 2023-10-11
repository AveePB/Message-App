//Java I/O (Input and Output)
import java.io.IOException;

//Java Custom Packages
import app.App;


public class Main {
    public static void main(String[] args) {
        try {
            App app = new App();
            app.run();
        }
        catch (IOException ignored) { }
    }
}
