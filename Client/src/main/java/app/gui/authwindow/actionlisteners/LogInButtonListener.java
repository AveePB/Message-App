package app.gui.authwindow.actionlisteners;

//Java Custom
import app.api.Request;
import app.api.Response;
import app.api.StatusCode;
import app.gui.mainwindow.MainWindow;

//Java Swing
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Java Abstract Window Toolkit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Java Input & Output
import java.io.IOException;

//Java Networking
import java.net.Socket;

/**
 * Class is the 'Log in' button action listener.
 */
public class LogInButtonListener implements ActionListener {
    //Variables:
    private JFrame mainFrame;
    private JPasswordField passwordPF;
    private JTextField nicknameTF;
    private Socket sock;

    public LogInButtonListener(JFrame mainFrame, JPasswordField passwordPF, JTextField nicknameTF, Socket sock) {
        this.mainFrame = mainFrame;
        this.passwordPF = passwordPF;
        this.nicknameTF = nicknameTF;
        this.sock = sock;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Request.sendAuthenticationAction(this.sock.getOutputStream(), this.nicknameTF.getText(), new String(this.passwordPF.getPassword()));
            Response authResponse = new Response(this.sock.getInputStream());

            if (authResponse.getStatusCode() != StatusCode.OK) {
                JOptionPane.showMessageDialog(null, "Login Failed", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            MainWindow mainWindow = new MainWindow(this.sock, this.nicknameTF.getText());
            mainWindow.open();
            this.mainFrame.dispose();
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is offline ...", "Error", JOptionPane.ERROR_MESSAGE);
            this.mainFrame.dispose();
        }
    }
}
