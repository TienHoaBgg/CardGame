import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginForm {
    private JPanel panel_main;
    private JButton loginButton;
    private JTextField txtUserName;
    private JLabel statusLabel;


    public LoginForm() {
        statusLabel.setVisible(false);

        loginButton.addActionListener(e -> {
            statusLabel.setVisible(true);
        });
    }

    public static void main(String[] args) {
        LoginForm form = new LoginForm();
        JFrame frame = new JFrame("CardGame");
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(form.panel_main, "Center");
        frame.setSize(new Dimension(450, 180));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

}
