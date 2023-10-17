package app.windows.mainwindow.actionlisteners;

//Javax Swing (Part of Java Foundation Classes)
import javax.swing.JComboBox;
import javax.swing.JTextArea;

//Java Abstract Window Toolkit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.api.Client;


public class AL_SendMessageBtn implements ActionListener {
    //The GUI components:
    JComboBox chatList;
    JTextArea messageWorkshopTextArea;

    //The application API:
    Client client;

    /**
     * Constructs an action listener 'Send Message' button object.
     * @param chatList the chat list.
     * @param messageWorkshopTextArea the message workshop text area.
     * @param client the application API object.
     */
    public AL_SendMessageBtn(JComboBox chatList, JTextArea messageWorkshopTextArea, Client client) {
        this.chatList = chatList;
        this.messageWorkshopTextArea = messageWorkshopTextArea;

        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = this.messageWorkshopTextArea.getText();
        if (this.chatList.getSelectedIndex() == -1 || msg == null || msg.length() == 0) return;

        String nickname = (String) this.chatList.getSelectedItem();
        this.client.sendCreateMessageRequest(nickname, msg);
    }
}
