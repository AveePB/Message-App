package app.windows.mainwindow;

//Javax Swing (Part of Java Foundation Classes)
import javax.swing.*;

//Java Abstract Window Toolkit
import java.awt.*;

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


        //Initializing the north part.
        this.createChatButton = new JButton("Create new chat");
        this.createChatButton.setBounds(10, 5, 130, 20);
        this.mainFrame.add(this.createChatButton);

        JLabel chatListLabel = new JLabel("Select chat");
        chatListLabel.setBounds(150, 5, 100, 20);
        chatListLabel.setFont(new Font("", Font.PLAIN, 18));
        this.mainFrame.add(chatListLabel);

        this.chatList = new JComboBox();
        this.chatList.setBounds(250, 5, 250, 20);
        this.mainFrame.add(this.chatList);


        //Initializing the center part.
        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new BorderLayout());

        chatPanel.setBounds(2, 26, w, 270);
        chatPanel.setBackground(Color.ORANGE);
        this.mainFrame.add(chatPanel);

        this.chatTextArea = new JTextArea();
        this.chatTextArea.setBounds(0, 26, w, h);
        this.chatTextArea.setFont(new Font("", Font.PLAIN, 16));

        this.chatTextArea.setEditable(false);
        this.chatTextArea.setLineWrap(true);
        this.chatTextArea.setWrapStyleWord(true);
        chatPanel.add(this.chatTextArea);

        JScrollPane sp = new JScrollPane(chatTextArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        chatPanel.add(sp);

        //Initializing the south part.
        //...

        //Changing the visibility of the main frame.
        this.mainFrame.setVisible(true);
    }
}
