package app.gui.authwindow.actionlisteners;

//Java Custom
import app.api.Request;

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

//Java Utilities
import java.util.Arrays;

/**
 * Class is the 'Sign up' button action listener.
 */
public class SignUpButtonListener implements ActionListener {
    //Variables:
    private JFrame mainFrame;
    private JPasswordField passwordPF;
    private JTextField nicknameTF;
    private Socket sock;

    public SignUpButtonListener(JFrame mainFrame, JPasswordField passwordPF, JTextField nicknameTF, Socket sock) {
        this.mainFrame = mainFrame;
        this.passwordPF = passwordPF;
        this.nicknameTF = nicknameTF;
        this.sock = sock;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Request.sendRegistrationAction(this.sock.getOutputStream(), this.nicknameTF.getText(), new String(this.passwordPF.getPassword()));
            Request.sendAuthenticationAction(this.sock.getOutputStream(), this.nicknameTF.getText(), new String(this.passwordPF.getPassword()));
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is offline ...", "Error", JOptionPane.ERROR_MESSAGE);
            this.mainFrame.dispose();
        }
    }
}
