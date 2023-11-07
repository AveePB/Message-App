package app.gui.mainwindow;

//Java Swing
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

//Java Abstract Window Toolkit
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

//Java Language
import java.lang.String;

/**
 * Main window is used to use services
 * provided by the 'message app' server.
 */
public class MainWindow {
    //Constants:
    public static final int WINDOW_WIDTH = 520, WINDOW_HEIGHT = 405;
    public static final String WINDOW_ICON_PATH = "./assets/img/icon.png";
    public static final String WINDOW_TITLE_PREFIX = "Message App - ";

    //Variables:
    //GUI components
    private JButton addFriendBtn, sendMsgBtn;
    private JComboBox<String> friendListCB;
    private JFrame mainFrame;
    private JTextArea chatTA, msgWorkshopTA;

    //User data
    String userNickname;

    /**
     * Constructs a main window object.
     */
    public MainWindow(String userNickname) {
        this.userNickname = userNickname;

        initializeMainFrame();
        initializeUpperPart();
        initializeCenterPart();
        initializeBottomPart();
    }

    /**
     * It creates a totally new JFrame object.
     * That object is set to the main frame variable.
     */
    private void initializeMainFrame() {
        this.mainFrame = new JFrame();

        this.mainFrame.setLayout(null);
        this.mainFrame.setResizable(false);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.mainFrame.setSize(MainWindow.WINDOW_WIDTH, MainWindow.WINDOW_HEIGHT);
        this.mainFrame.setIconImage(new ImageIcon(MainWindow.WINDOW_ICON_PATH).getImage());
        this.mainFrame.setTitle(MainWindow.WINDOW_TITLE_PREFIX + this.userNickname);
    }

    /**
     * It initializes the upper part of the main frame.
     */
    private void initializeUpperPart() {
        Font titleFont = new Font("", Font.PLAIN, 18);
        int btnWidth = 130, btnHeight = 20;
        int friendListWidth = 250, friendListHeight = 20;

        //Add Friend Section
        this.addFriendBtn = new JButton("Add Friend");
        this.addFriendBtn.setBounds(4, 5, btnWidth, btnHeight);
        //this.addFriendBtn.addActionListener(...);

        //Friend List Section
        JLabel friendListTitle = new JLabel("Current chat");
        friendListTitle.setBounds(146, 5, friendListWidth, friendListHeight);
        friendListTitle.setFont(titleFont);

        this.friendListCB = new JComboBox<>();
        this.friendListCB.setBounds(250, 5,friendListWidth, friendListHeight);
        //this.friendListCB.addActionListener(...);


        //Adds GUI components to the frame.
        this.mainFrame.add(this.addFriendBtn);
        this.mainFrame.add(friendListTitle);
        this.mainFrame.add(this.friendListCB);
    }

    /**
     * It initializes the center part of the main frame.
     */
    private void initializeCenterPart() {
        Font chatTAFont = new Font("", Font.PLAIN, 17);

        //Chat Text Area Section
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBounds(2, 30, 500, 270);

        this.chatTA = new JTextArea();
        this.chatTA.setFont(chatTAFont);
        this.chatTA.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        this.chatTA.setEditable(false);
        this.chatTA.setLineWrap(true);
        this.chatTA.setWrapStyleWord(true);
        chatPanel.add(this.chatTA);

        JScrollPane chatScroll = new JScrollPane(this.chatTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatPanel.add(chatScroll);

        //Adds GUI components to the frame.
        this.mainFrame.add(chatPanel);
    }

    /**
     * It initializes the bottom part of the main frame.
     */
    private void initializeBottomPart() {
        Font msgWorkshopTAFont = new Font("", Font.PLAIN, 14);

        //Message Workshop Text Area Section
        JPanel msgWorkshopPanel = new JPanel();
        msgWorkshopPanel.setLayout(new BorderLayout());
        msgWorkshopPanel.setBounds(3, 300, 410, 65);

        //Message Workshop Text Area
        this.msgWorkshopTA = new JTextArea();
        this.msgWorkshopTA.setFont(msgWorkshopTAFont);

        this.msgWorkshopTA.setLineWrap(true);
        this.msgWorkshopTA.setWrapStyleWord(true);
        msgWorkshopPanel.add(this.msgWorkshopTA);

        JScrollPane messageWorkshopScroll = new JScrollPane(this.msgWorkshopTA, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        msgWorkshopPanel.add(messageWorkshopScroll);

        //Send Message Button Section
        this.sendMsgBtn = new JButton("Send");
        this.sendMsgBtn.setBounds(415, 300, 85, 64);
        //this.sendMsgBtn.addActionListener(...);

        //Adds GUI components to the frame.
        this.mainFrame.add(msgWorkshopPanel);
        this.mainFrame.add(this.sendMsgBtn);
    }

    /**
     * Open the authorization window.
     */
    public void open() {
        this.mainFrame.setVisible(true);
    }
}
