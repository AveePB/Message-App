package app.windows.authwindow.actionlisteners;

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

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.api.Client;
import app.api.Response;
import app.windows.mainwindow.MainWindow;


public class AL_SignUpBtn implements ActionListener {
    //GUI components:
    JTextField nicknameTextField;
    JPasswordField passwordField;
    JFrame mainFrame;

    //The application API:
    Client client;

    /**
     * Constructs an action listener 'sign up' button object.
     * @param nicknameTextField the nickname text field.
     * @param passwordField the password text field.
     * @param mainFrame the main frame.
     * @param client the application API.
     */
    public AL_SignUpBtn(JTextField nicknameTextField, JPasswordField passwordField, JFrame mainFrame, Client client) {
        this.nicknameTextField = nicknameTextField;
        this.passwordField = passwordField;
        this.mainFrame = mainFrame;

        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = this.nicknameTextField.getText();
        String password = String.valueOf(this.passwordField.getPassword());

        //Checking if fields are empty.
        if (nickname.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this.mainFrame, "One of the fields is empty!!!", "INFO", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        //Sending request to the server.
        this.client.sendRegisterRequest(nickname, password);

        //Listening and validating the server response.
        try {
            String[] response = this.client.getResponse();

            if ((response.length == 1) && (response[0].equals(Response.REGISTER_SUCCESS.toString()))) {
                this.client.setStatusOnline();
                new MainWindow(this.client).open();
                this.mainFrame.dispose();
            }
            else if ((response.length == 1) && (response[0].equals(Response.REGISTER_FAILED.toString()))) {
                JOptionPane.showMessageDialog(this.mainFrame, "This nickname is already taken!!!", "INFO", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                System.out.println(response[0]);
                throw new IOException("AN INCORRECT SERVER RESPONSE!!!");
            }
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is offline!", "Message App Error", JOptionPane.ERROR_MESSAGE);
            this.mainFrame.dispose();
            this.client.closeConnection();
        }
    }
}
