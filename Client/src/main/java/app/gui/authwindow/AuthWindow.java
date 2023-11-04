package app.gui.authwindow;

import app.api.Client;
import app.gui.mainwindow.MainWindow;

import javax.swing.*;

/**
 * Authorization window is used to get access
 * to services provided by the 'message app' server.
 */
public class AuthWindow {
    //Constants:
    public static final int WINDOW_WIDTH = 420, WINDOW_HEIGHT = 500;
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
    public void initializeMainFrame() {
        this.mainFrame = new JFrame();

        this.mainFrame.setLayout(null);
        this.mainFrame.setResizable(false);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.mainFrame.setSize(AuthWindow.WINDOW_WIDTH, AuthWindow.WINDOW_HEIGHT);
        this.mainFrame.setIconImage(new ImageIcon(AuthWindow.WINDOW_ICON_PATH).getImage());
        this.mainFrame.setTitle(AuthWindow.WINDOW_TITLE);
    }

    public void initializeLogoPanel() {


    }

    public void initializeInputPanel() {

    }

    public void initializeDecisionPanel() {

    }

    public void open() {
        this.mainFrame.setVisible(true);
    }
}
