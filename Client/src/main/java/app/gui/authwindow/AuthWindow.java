package app.gui.authwindow;

//Java Custom
import app.api.Client;

//Java Swing
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

//Java Abstract Window Toolkit
import java.awt.Font;

//Java Language
import java.lang.String;

/**
 * Authorization window is used to get access
 * to services provided by the 'message app' server.
 */
public class AuthWindow {
    //Constants:
    public static final int WINDOW_WIDTH = 435, WINDOW_HEIGHT = 510;
    public static final String WINDOW_ICON_PATH = "./assets/img/icon.png";
    public static final String WINDOW_TITLE = "Message App";

    //Variables:
    //GUI components
    private JButton logInBtn, signUpBtn;
    private JFrame mainFrame;
    private JLabel appLogo;
    private JTextField nicknameTF;
    private JPasswordField passwordPF;

    //API interface
    private Client client;

    /**
     * Constructs an auth window object.
     * @param client the application API object.
     */
    public AuthWindow(Client client) {
        //this.client = client;

        initializeMainFrame();
        initializeLogoPanel();
        initializeInputPanel();
        initializeDecisionPanel();
    }

    /**
     * It creates a totally new JFrame object.
     * That object is set to the main frame variable.
     */
    private void initializeMainFrame() {
        this.mainFrame = new JFrame();

        this.mainFrame.setLayout(null);
        this.mainFrame.setResizable(false);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.mainFrame.setSize(AuthWindow.WINDOW_WIDTH, AuthWindow.WINDOW_HEIGHT);
        this.mainFrame.setIconImage(new ImageIcon(AuthWindow.WINDOW_ICON_PATH).getImage());
        this.mainFrame.setTitle(AuthWindow.WINDOW_TITLE);
    }

    /**
     * It initializes the logo panel.
     */
    private void initializeLogoPanel() {
        String logoPath = "./assets/img/logo.png";
        int logoSize = 250;

        //Logo Section
        this.appLogo = new JLabel(new ImageIcon(logoPath));
        this.appLogo.setBounds(87, 0, logoSize, logoSize);

        //Adds GUI components to the frame.
        this.mainFrame.add(this.appLogo);
    }

    /**
     * It initializes the input panel.
     */
    private void initializeInputPanel() {
        Font titleFont = new Font("", Font.BOLD, 17);
        Font inputFont = new Font("", Font.PLAIN, 20);
        int fieldWidth = 200, fieldHeight = 40;

        //Nickname Section
        JLabel nicknameTitle = new JLabel("Nickname");
        nicknameTitle.setBounds(115, 245, fieldWidth, fieldHeight);
        nicknameTitle.setFont(titleFont);

        this.nicknameTF = new JTextField();
        this.nicknameTF.setBounds(110, 275, fieldWidth, fieldHeight);
        this.nicknameTF.setFont(inputFont);

        //Password Section
        JLabel passwordTitle = new JLabel("Password");
        passwordTitle.setBounds(115, 310, fieldWidth, fieldHeight);
        passwordTitle.setFont(titleFont);

        this.passwordPF = new JPasswordField();
        this.passwordPF.setBounds(110, 340, fieldWidth, fieldHeight);
        this.passwordPF.setFont(inputFont);

        //Adds GUI components to the frame.
        this.mainFrame.add(nicknameTitle);
        this.mainFrame.add(this.nicknameTF);
        this.mainFrame.add(passwordTitle);
        this.mainFrame.add(this.passwordPF);
    }

    /**
     * It initializes the decision panel.
     */
    private void initializeDecisionPanel() {
        Font btnFont = new Font("", Font.PLAIN, 20);
        int btnWidth = 115, btnHeight = 40;

        //Sign up Button Section
        this.signUpBtn = new JButton("Sign up");
        this.signUpBtn.setBounds(90, 400, btnWidth, btnHeight);
        this.signUpBtn.setFont(btnFont);
        //this.signUpBtn.addActionListener(new SignUpBtnActionListener(this.client));

        //Log in Button Section
        this.logInBtn = new JButton("Log in");
        this.logInBtn.setBounds(215, 400, btnWidth, btnHeight);
        this.logInBtn.setFont(btnFont);
        //this.logInBtn.addActionListener(new LogInBtnListener(this.client));

        //Adds GUI components to the frame.
        this.mainFrame.add(this.signUpBtn);
        this.mainFrame.add(this.logInBtn);
    }

    /**
     * Open the authorization window.
     */
    public void open() {
        this.mainFrame.setVisible(true);
    }
}
