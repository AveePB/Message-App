//Java Custom
import app.Application;

//Java Swing
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) {
        try {
            Application app = new Application();
            app.run();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Server is offline ...", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
