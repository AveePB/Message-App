package app.windows.mainwindow.actionlisteners;

//Javax Swing (Part of Java Foundation Classes)
import javax.swing.JOptionPane;

//Java Abstract Window Toolkit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Java Language (Fundamental Classes)
import java.lang.String;

//Java Custom Packages
import app.api.Client;


public class AL_CreateNewChatBtn implements ActionListener {
    //The application API:
    Client client;

    /**
     * Constructs an action listener 'create new chat' button object.
     * @param client the application API object.
     */
    public AL_CreateNewChatBtn(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nickname = JOptionPane.showInputDialog("Enter the nickname of user you want to create chat with:");
        if (nickname == null || nickname.length() == 0) return;

        this.client.sendCreateChatRequest(nickname);
    }
}
