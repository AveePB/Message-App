//Java Custom
import app.Application;

//Java Swing
import javax.swing.JOptionPane;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //1.I need to create the API !!! (Request, Response, StatusCode, RequestAction, RequestTYPE)


        try {
            Application app = new Application();
            app.run();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Server is offline ...", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
