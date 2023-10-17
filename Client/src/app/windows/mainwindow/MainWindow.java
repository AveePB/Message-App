package app.windows.mainwindow;

//Javax Swing (Part of Java Foundation Classes)
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

//Java Abstract Window Toolkit
import java.awt.BorderLayout;
import java.awt.Font;

//Java Custom Packages
import app.api.Client;


public class MainWindow {
    //The GUI components:
    private JFrame mainFrame;
    private JButton createChatButton;
    private JButton messageSendButton;
    private JComboBox chatList;
    private JTextArea chatTextArea;
    private JTextArea messageWorkshopTextArea;

    //The application API:
    private Client client;

    /**
     * Constructs a main window object.
     * @param client the application API object.
     */
    public MainWindow(Client client) {
        this.client = client;
    }

    /**
     * Initializes the north part of window.
     */
    private void initializeNorthPart() {
        //Initializing the north part.
        this.createChatButton = new JButton("Create new chat");
        this.createChatButton.setBounds(10, 5, 130, 20);
        this.mainFrame.add(this.createChatButton);

        JLabel chatListLabel = new JLabel("Select chat");
        chatListLabel.setBounds(150, 5, 100, 20);
        chatListLabel.setFont(new Font("", Font.PLAIN, 18));
        this.mainFrame.add(chatListLabel);

        //Chat List
        this.chatList = new JComboBox();
        this.chatList.setBounds(250, 5, 250, 20);
        this.mainFrame.add(this.chatList);
    }

    /**
     * Initializes the center part of window.
     */
    private void initializeCenterPart() {
        //Initializing the center part.
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());
        chatPanel.setBounds(2, 26, 500, 270);
        this.mainFrame.add(chatPanel);

        //Chat Text Area
        this.chatTextArea = new JTextArea();
        this.chatTextArea.setFont(new Font("", Font.PLAIN, 16));

        this.chatTextArea.setEditable(false);
        this.chatTextArea.setLineWrap(true);
        this.chatTextArea.setWrapStyleWord(true);
        chatPanel.add(this.chatTextArea);

        JScrollPane chatScroll = new JScrollPane(this.chatTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatPanel.add(chatScroll);
    }

    /**
     * Initializes the south part of window.
     */
    private void initializeSouthPart() {
        //Initializing the south part.
        JPanel messageWorkshopPanel = new JPanel();
        messageWorkshopPanel.setLayout(new BorderLayout());
        messageWorkshopPanel.setBounds(0, 300, 410, 64);
        this.mainFrame.add(messageWorkshopPanel);

        //Message Workshop Text Area
        this.messageWorkshopTextArea = new JTextArea();
        this.messageWorkshopTextArea.setFont(new Font("", Font.PLAIN, 14));

        this.messageWorkshopTextArea.setLineWrap(true);
        this.messageWorkshopTextArea.setWrapStyleWord(true);
        messageWorkshopPanel.add(this.messageWorkshopTextArea);

        JScrollPane messageWorkshopScroll = new JScrollPane(this.messageWorkshopTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        messageWorkshopPanel.add(messageWorkshopScroll);

        //Send Message Button
        this.messageSendButton = new JButton();
        this.messageSendButton.setBounds(411, 300, 90, 58);

        this.messageSendButton.setIcon(new ImageIcon("./assets/img/send-msg-btn.png"));
        this.mainFrame.add(this.messageSendButton);
    }


    /**
     * Initializes the main window.
     */
    public void open() {
        int w=500, h=400;
        //Initializing the main frame.
        this.mainFrame = new JFrame();
        this.mainFrame.setLayout(null);
        this.mainFrame.setSize(w+20, h);
        this.mainFrame.setResizable(false);

        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setIconImage(new ImageIcon("./assets/img/icon.png").getImage());
        this.mainFrame.setTitle("Message App");

        initializeNorthPart();
        initializeCenterPart();
        initializeSouthPart();

        //Changing the visibility of the main frame.
        this.mainFrame.setVisible(true);
    }
}
