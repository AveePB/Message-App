package app.gui.mainwindow.actionlisteners;

//Java Custom
import app.api.Request;

//Java Swing
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

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
 * Class is the 'Send Message' button action listener.
 */
public class SendMessageButtonListener implements ActionListener {
    //Variables:
    private JComboBox<String> friendListCB;
    private JFrame mainFrame;
    private JTextArea messageWorkshopTA;
    private Socket sock;

    public SendMessageButtonListener(JComboBox<String> friendListCB, JTextArea messageWorkshopTA, JFrame mainFrame, Socket sock) {
        this.friendListCB = friendListCB;
        this.messageWorkshopTA = messageWorkshopTA;
        this.mainFrame = mainFrame;
        this.sock = sock;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Request.sendMessageCreationAction(this.sock.getOutputStream(), (String) this.friendListCB.getSelectedItem(), this.messageWorkshopTA.getText());
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Server is offline ...", "Error", JOptionPane.ERROR_MESSAGE);
            this.mainFrame.dispose();
        }
    }
}