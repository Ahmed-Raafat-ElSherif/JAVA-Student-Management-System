import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginGUI extends JFrame {

    public static final String APP_NAME = "Student Management System Login";
    public static final int TEXTFIELD_SIZE = 15;

    private ImageIcon resizeIcon(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public LoginGUI() {
        super(APP_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage("assets/log-in.png")); // Add your icon path here
        addGUIComponents();
    }

    public void addGUIComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light pastel blue background
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Header Label
        JLabel headerLabel = new JLabel(APP_NAME, JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(new Color(34, 45, 65)); // Dark text color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(headerLabel, gbc);
    
        // Username Section
        JLabel usernameIcon = new JLabel(resizeIcon("assets/username-icon.png", 24, 24));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(usernameIcon, gbc);
    
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameLabel.setForeground(new Color(34, 45, 65));
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameLabel, gbc);
    
        JTextField usernameField = new JTextField(TEXTFIELD_SIZE);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 2;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);
    
        // Password Section
        JLabel passwordIcon = new JLabel(resizeIcon("assets/password-icon.png", 24, 24));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        mainPanel.add(passwordIcon, gbc);
    
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordLabel.setForeground(new Color(34, 45, 65));
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);
    
        JPasswordField passwordField = new JPasswordField(TEXTFIELD_SIZE);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 2;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);
    
        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(58, 123, 213)); // Blue button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginButton.addActionListener(e -> handleLogin(usernameField.getText(), new String(passwordField.getPassword())));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        mainPanel.add(loginButton, gbc);
    
        add(mainPanel);
    }
    

    private void handleLogin(String username, String password) {
        if ("admin".equals(username) && "123456".equals(password)) {
            System.out.println("LOGIN SUCCESSFUL!");
            setVisible(false);
            TableGUI.start("admin");
        } else if ("student".equals(username) && "456789".equals(password)) {
            System.out.println("LOGIN SUCCESSFUL!");
            setVisible(false);
            TableGUI.start("student");
        } else {
            System.out.println("LOGIN FAILED...");
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });
    }
}
