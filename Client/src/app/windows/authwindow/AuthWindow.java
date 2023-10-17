package app.windows.authwindow;

//Javax Swing (Part of Java Foundation Classes)
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

//Java Abstract Window Toolkit
import java.awt.Font;

//Java Custom Packages
import app.api.Client;
import app.windows.authwindow.actionlisteners.AL_LogInBtn;
import app.windows.authwindow.actionlisteners.AL_SignUpBtn;


public class AuthWindow {
    //The GUI components:
    private JButton logInButton;
    private JButton signUpButton;
    private JFrame mainFrame;
    private JLabel logo;
    private JTextField nicknameTextField;
    private JPasswordField passwordField;

    //The application API:
    private Client client;

    /**
     * Constructs an auth window object.
     * @param client the application API object.
     */
    public AuthWindow(Client client) {
        this.client = client;
    }

    /**
     * Initializes the login/signup window.
     */
    public void open() {
        int w=400, h=500;
        //Initializing the main frame.
        this.mainFrame = new JFrame();
        this.mainFrame.setLayout(null);
        this.mainFrame.setSize(w+20, h);
        this.mainFrame.setResizable(false);

        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setIconImage(new ImageIcon("./assets/img/icon.png").getImage());
        this.mainFrame.setTitle("Message App");

        //Initializing the north part.
        this.logo = new JLabel();
        this.logo.setIcon(new ImageIcon("./assets/img/logo.png"));
        this.logo.setBounds(125, 10, 150, 160);

        this.mainFrame.add(this.logo);

        //Initializing the center part.

        //THE NICKNAME SECTION.
        JLabel nicknameLabel = new JLabel("Nickname");
        nicknameLabel.setBounds(110, 200, 100, 20);
        nicknameLabel.setFont(new Font("", Font.PLAIN, 18));
        this.mainFrame.add(nicknameLabel);

        this.nicknameTextField = new JTextField();
        this.nicknameTextField.setBounds(110, 220, 180, 35);
        this.nicknameTextField.setFont(new Font("", Font.PLAIN, 21));
        this.mainFrame.add(this.nicknameTextField);

        //THE PASSWORD SECTION.
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(110, 270, 100, 20);
        passwordLabel.setFont(new Font("", Font.PLAIN, 18));
        this.mainFrame.add(passwordLabel);

        this.passwordField = new JPasswordField();
        this.passwordField.setBounds(110, 290, 180, 35);
        this.passwordField.setFont(new Font("", Font.PLAIN, 21));
        this.mainFrame.add(this.passwordField);

        //THE LOG IN BUTTON SECTION.
        this.logInButton = new JButton("Log in");
        this.logInButton.setBounds(102, 345, 200, 40);
        this.logInButton.setFont(new Font("", Font.PLAIN, 19));
        this.logInButton.addActionListener(new AL_LogInBtn(this.nicknameTextField, this.passwordField, this.mainFrame, this.client));
        this.mainFrame.add(this.logInButton);

        //Initializing the south part.
        this.signUpButton = new JButton("Sign Up");
        this.signUpButton.setBounds(112, 400, 180, 30);
        this.signUpButton.addActionListener(new AL_SignUpBtn(this.nicknameTextField, this.passwordField, this.mainFrame, this.client));
        this.mainFrame.add(this.signUpButton);

        //Changing the visibility of the main frame.
        this.mainFrame.setVisible(true);
    }
}
