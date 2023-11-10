package app.gui.mainwindow.actionlisteners;

//Java Custom
import app.api.Request;

//Java Swing
import javax.swing.JFrame;
import javax.swing.JOptionPane;

//Java Abstract Window Toolkit
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Java Input & Output
import java.io.IOException;

//Java Networking
import java.net.Socket;

//Java Language
import java.lang.String;

/**
 * Class is the 'Add Friend' button action listener.
 */
public class AddFriendButtonListener implements ActionListener {
    //Variables:
    private JFrame mainFrame;
    private Socket sock;

    public AddFriendButtonListener(JFrame mainFrame, Socket sock) {
        this.mainFrame = mainFrame;
        this.sock = sock;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String newFriendNickname = JOptionPane.showInputDialog("Enter the nickname of user you want to create chat with:");
            Request.sendChatCreationAction(this.sock.getOutputStream(), newFriendNickname);
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is offline ...", "Error", JOptionPane.ERROR_MESSAGE);
            this.mainFrame.dispose();
        }
    }
}
