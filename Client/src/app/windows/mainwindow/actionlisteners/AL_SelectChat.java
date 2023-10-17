package app.windows.mainwindow.actionlisteners;

//Javax Swing (Part of Java Foundation Classes)
import javax.swing.JComboBox;

//Java Abstract Window Toolkit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.api.Client;


public class AL_SelectChat implements ActionListener {
    //The GUI components:
    JComboBox chatList;

    //The application API:
    Client client;

    /**
     * Constructs an action listener 'Select Chat' object.
     * @param chatList the chat list.
     * @param client the application API object.
     */
    public AL_SelectChat(JComboBox chatList, Client client) {
        this.chatList = chatList;
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.chatList.getSelectedIndex() == -1) return;
        String nickname = (String) this.chatList.getSelectedItem();

        this.client.sendReadChatMessagesRequest(nickname);
    }
}
