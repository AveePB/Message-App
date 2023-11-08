package app.gui.mainwindow.actionlisteners;

//Java Custom
import app.api.Request;

//Java Swing
import javax.swing.JComboBox;
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
 * Class is the 'Friend List' combo box action listener.
 */
public class FriendListComboBoxListener implements ActionListener {
    //Variables:
    private JComboBox<String> friendListCB;
    private JFrame mainFrame;
    private Socket sock;

    public FriendListComboBoxListener(JComboBox<String> friendListCB, JFrame mainFrame, Socket sock) {
        this.friendListCB = friendListCB;
        this.mainFrame = mainFrame;
        this.sock = sock;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Request.sendReadingChatAction(this.sock.getOutputStream(), (String) this.friendListCB.getSelectedItem());
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is offline ...", "Error", JOptionPane.ERROR_MESSAGE);
            this.mainFrame.dispose();
        }
    }
}
