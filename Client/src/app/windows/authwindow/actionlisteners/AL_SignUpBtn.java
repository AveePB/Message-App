package app.windows.authwindow.actionlistener;

//Java Swing (Part of Java Foundation Classes)
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

//Java Abstract Window Toolkit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Java I/O (Input and Output)
import java.io.IOException;

//Java Custom Packages
import app.api.Client;
import app.api.Response;

public class AL_SignUpBtn implements ActionListener {
    //GUI components:
    JTextField nicknameTextField;
    JPasswordField passwordField;
    JFrame frame;

    //The application API:
    Client client;

    /**
     * Constructs an action listener 'sign up' button object.
     * @param nicknameTextField the nickname text field.
     * @param passwordField the password text field.
     * @param frame the frame.
     * @param client the application API.
     */
    public AL_SignUpBtn(JTextField nicknameTextField, JPasswordField passwordField, JFrame frame, Client client) {
        this.nicknameTextField = nicknameTextField;
        this.passwordField = passwordField;
        this.frame = frame;

        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = this.nicknameTextField.getText();
        String password = String.valueOf(this.passwordField.getPassword());

        //Checking if fields are empty.
        if (nickname.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this.frame, "One of the fields is empty!!!", "INFO", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //Sending request to the server.
        this.client.sendRegisterRequest(nickname, password);

        //Listening and validating the server response.
        try {
            String[] response = this.client.getResponse();

            if ((response.length == 1) && (response[0].equals(Response.REGISTER_SUCCESS.toString()))) {
                //OPENS MAIN WINDOW...
                this.frame.dispose();
            }
            else if ((response.length == 1) && (response[0].equals(Response.REGISTER_FAILED.toString()))) {
                JOptionPane.showMessageDialog(this.frame, "This nickname is already taken!!!", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                System.out.println(response[0]);
                throw new IOException("AN INCORRECT SERVER RESPONSE!!!");
            }
        }
        catch (IOException ex) {
            this.frame.dispose();
            this.client.closeConnection();
        }
    }
}
