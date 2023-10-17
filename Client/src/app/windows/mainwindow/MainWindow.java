package app.windows.mainwindow;

//Javax Swing (Part of Java Foundation Classes)
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

//Java Abstract Window Toolkit
import java.awt.BorderLayout;
import java.awt.Font;

//Java I/O (Input and Output)
import java.io.IOException;

//Java Language (Fundamental Classes)
import java.lang.StringBuilder;
import java.lang.String;

//Java Custom Packages
import app.api.Client;
import app.api.Response;
import app.windows.mainwindow.actionlisteners.AL_CreateNewChatBtn;
import app.windows.mainwindow.actionlisteners.AL_SelectChat;
import app.windows.mainwindow.actionlisteners.AL_SendMessageBtn;


public class MainWindow {
    //The GUI components:
    private JFrame mainFrame;
    private JButton createNewChatButton;
    private JButton messageSendButton;
    private JComboBox<String> chatList;
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
        this.createNewChatButton = new JButton("Create new chat");
        this.createNewChatButton.setBounds(10, 5, 130, 20);

        this.createNewChatButton.addActionListener(new AL_CreateNewChatBtn(this.client));
        this.mainFrame.add(this.createNewChatButton);

        JLabel chatListLabel = new JLabel("Select chat");
        chatListLabel.setBounds(150, 5, 100, 20);
        chatListLabel.setFont(new Font("", Font.PLAIN, 18));
        this.mainFrame.add(chatListLabel);

        //Chat List
        this.chatList = new JComboBox<>();
        this.chatList.setBounds(250, 5, 250, 20);
        this.chatList.addActionListener(new AL_SelectChat(this.chatList, this.client));
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

        this.messageSendButton.addActionListener(new AL_SendMessageBtn(this.chatList, this.messageWorkshopTextArea, this.client));
        this.messageSendButton.setIcon(new ImageIcon("./assets/img/send-msg-btn.png"));
        this.mainFrame.add(this.messageSendButton);
    }

    /**
     * Handles the server responses.
     */
    private void handleResponses() {
        boolean isListening = true;

        while (isListening) {
            try {
                //RESPONSE[0] <- the response type.
                String[] response = this.client.getResponse();

                if ((response.length > 1) && (response[0].equals(Response.CHAT_USERS.toString()))) {
                    for (int i=1; i<response.length; i++)
                        this.chatList.addItem(response[i]);
                }
                else if ((response.length == 2) && (response[0].equals(Response.CHAT_CREATION_SUCCESS.toString()))) {
                    this.chatList.addItem(response[1]);
                }
                else if ((response.length >= 1) && (response[0].equals(Response.CHAT_MESSAGES.toString()))) {
                    StringBuilder chat = new StringBuilder();

                    for (int i=1; i<response.length; i++)
                        chat.append(response[i]).append('\n');

                    this.chatTextArea.setText(chat.toString());
                }
                else if ((response.length == 2) && (response[0].equals(Response.MSG_CREATION_SUCCESS.toString()))) {
                    StringBuilder chat = new StringBuilder();
                    chat.append(this.chatTextArea.getText()).append(this.client.getNickname());
                    chat.append(": ").append(response[1]).append('\n');

                    this.chatTextArea.setText(chat.toString());
                    this.messageWorkshopTextArea.setText("");
                }
                else if ((response.length == 3) && (response[0].equals(Response.NEW_MSG.toString()))) {
                    StringBuilder chat = new StringBuilder();
                    chat.append(this.chatTextArea.getText()).append(response[1]);
                    chat.append(": ").append(response[2]).append('\n');

                    this.chatTextArea.setText(chat.toString());
                }
                else if ((response.length == 2) && (response[0].equals(Response.NEW_CHAT.toString()))) {
                    this.chatList.addItem(response[1]);
                }
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Lost connection with server!", "Message App Error", JOptionPane.ERROR_MESSAGE);
                this.mainFrame.dispose();
                isListening = false;
            }
        }
    }

    /**
     * Initializes the main window.
     */
    public void open() {
        //Initializing the main frame.
        this.mainFrame = new JFrame();
        this.mainFrame.setLayout(null);
        this.mainFrame.setSize(520, 400);
        this.mainFrame.setResizable(false);

        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setIconImage(new ImageIcon("./assets/img/icon.png").getImage());
        this.mainFrame.setTitle("Message App - " + this.client.getNickname());

        //Initializing the main frame parts.
        initializeNorthPart();
        initializeCenterPart();
        initializeSouthPart();

        //Sending the request to server for the list of chats.
        this.client.sendGetChatUsersRequest();

        //Changing the visibility of the main frame.
        this.mainFrame.setVisible(true);

        //Starting the thread that handles responses.
        Thread listenerThread =  new Thread(this::handleResponses);
        listenerThread.setDaemon(true);
        listenerThread.start();
    }
}
