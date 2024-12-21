import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {

    public final static String APP_NAME = "Student Management System Login";
    public final static int[] FRAME_SIZE = {400, 400};
    public static final int TEXTFIELD_SIZE = 15;
    
    public LoginGUI(){
        super(APP_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(FRAME_SIZE[0],FRAME_SIZE[1]);
        setLocationRelativeTo(null);

        addGUIComponents();
    }

    public void addGUIComponents(){
        SpringLayout springLayout = new SpringLayout();
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(springLayout);

        // username
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JTextField usernameField = new JTextField(TEXTFIELD_SIZE);
        usernameField.setFont(new Font("Dialog", Font.PLAIN, 18));

        springLayout.putConstraint(SpringLayout.WEST, usernameLabel, 35, SpringLayout.WEST, loginPanel);
        springLayout.putConstraint(SpringLayout.NORTH, usernameLabel, 85, SpringLayout.NORTH, loginPanel);
        springLayout.putConstraint(SpringLayout.WEST, usernameField, 135, SpringLayout.WEST, loginPanel);
        springLayout.putConstraint(SpringLayout.NORTH, usernameField, 85, SpringLayout.NORTH, loginPanel);

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);

        // password
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Dialog", Font.PLAIN, 18));

        JPasswordField passwordField = new JPasswordField(TEXTFIELD_SIZE);
        passwordField.setFont(new Font("Dialog", Font.PLAIN, 18));

        springLayout.putConstraint(SpringLayout.WEST, passwordLabel, 35, SpringLayout.WEST, loginPanel);
        springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 135, SpringLayout.NORTH, loginPanel);
        springLayout.putConstraint(SpringLayout.WEST, passwordField, 135, SpringLayout.WEST, loginPanel);
        springLayout.putConstraint(SpringLayout.NORTH, passwordField, 135, SpringLayout.NORTH, loginPanel);

        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);

        // login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Dialog", Font.BOLD, 18));
        springLayout.putConstraint(SpringLayout.WEST, loginButton, 150, SpringLayout.WEST, loginPanel);
        springLayout.putConstraint(SpringLayout.NORTH, loginButton, 250, SpringLayout.NORTH, loginPanel);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if(username.equals("admin")  && password.equals("123456"))
                {
                    System.out.println("LOGIN SUCCESSFUL!");
                    setVisible(false);
                    TableGUI.start();
                } 
                else if (username.equals("student") && password.equals("456789"))
                {
                    System.out.println("LOGIN SUCCESSFUL!");
                    setVisible(false);
                    TableGUI.start(); 
                }
                else
                {
                    System.out.println("LOGIN FAILED...");
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        });

        loginPanel.add(loginButton);

        this.getContentPane().add(loginPanel);
    }
}
