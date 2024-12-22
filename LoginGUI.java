import javax.swing.*; // Allows the use of Swing components like JFrame, JPanel, JLabel, etc.
import java.awt.*; // Provides classes for GUI layout, colors, fonts, sizes, etc.

public class LoginGUI extends JFrame {

    // Constants for application name and text field size
    public static final String APP_NAME = "Student Management System Login";
    public static final int TEXTFIELD_SIZE = 15;

    // Method to resize an image icon to specified width and height
    private ImageIcon resizeIcon(String filePath, int width, int height) {
        ImageIcon icon = new ImageIcon(filePath); // Load the image icon from the file path
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH); // Resize the image
        return new ImageIcon(image); // Return the resized icon
    }

    // Constructor to initialize the Login GUI
    public LoginGUI() {
        super(APP_NAME); // Set the title of the JFrame
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exit application when the frame is closed
        setSize(400, 400); // Set the window size to 400x400 pixels
        setLocationRelativeTo(null); // Center the window on the screen
        setIconImage(Toolkit.getDefaultToolkit().getImage("assets/log-in.png")); // Set the application icon
        addGUIComponents(); // Add components to the GUI
    }

    // Method to add GUI components to the main panel
    public void addGUIComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout()); // Use GridBagLayout for flexible component placement
        mainPanel.setBackground(new Color(240, 248, 255)); // Set light pastel blue background color

        GridBagConstraints gbc = new GridBagConstraints(); // Define layout constraints for components
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make components fill horizontally

        // Header Label
        JLabel headerLabel = new JLabel(APP_NAME, JLabel.CENTER); // Create a centered label for the application name
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
        headerLabel.setForeground(new Color(34, 45, 65)); // Set dark text color
        gbc.gridx = 0; // Column position
        gbc.gridy = 0; // Row position
        gbc.gridwidth = 3; // Span 3 columns
        mainPanel.add(headerLabel, gbc); // Add the header label to the panel

        // Username Section
        JLabel usernameIcon = new JLabel(resizeIcon("assets/username-icon.png", 24, 24)); // Add an icon for the username
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Single column width
        mainPanel.add(usernameIcon, gbc);

        JLabel usernameLabel = new JLabel("Username: "); // Add a label for the username
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size
        usernameLabel.setForeground(new Color(34, 45, 65)); // Set text color
        gbc.gridx = 1; // Next column
        mainPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(TEXTFIELD_SIZE); // Add a text field for username input
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size
        gbc.gridx = 2; // Next column
        mainPanel.add(usernameField, gbc);

        // Password Section
        JLabel passwordIcon = new JLabel(resizeIcon("assets/password-icon.png", 24, 24)); // Add an icon for the password
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordIcon, gbc);

        JLabel passwordLabel = new JLabel("Password: "); // Add a label for the password
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size
        passwordLabel.setForeground(new Color(34, 45, 65)); // Set text color
        gbc.gridx = 1;
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(TEXTFIELD_SIZE); // Add a password field for secure input
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font and size
        gbc.gridx = 2;
        mainPanel.add(passwordField, gbc);

        // Login Button
        JButton loginButton = new JButton("Login"); // Create a button with the label "Login"
        loginButton.setFont(new Font("Arial", Font.BOLD, 16)); // Set font and size
        loginButton.setBackground(new Color(58, 123, 213)); // Set blue background color
        loginButton.setForeground(Color.WHITE); // Set white text color
        loginButton.setFocusPainted(false); // Remove focus painting on button
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding inside the button
        loginButton.addActionListener(e -> handleLogin(usernameField.getText(), new String(passwordField.getPassword()))); // Handle button click
        gbc.gridx = 0; // Position the button
        gbc.gridy = 3; // Below the password field
        gbc.gridwidth = 3; // Span 3 columns
        mainPanel.add(loginButton, gbc);

        add(mainPanel); // Add the main panel to the frame
    }

    // Method to handle the login process
    private void handleLogin(String username, String password) {
        // Check if the username and password match predefined credentials
        if ("admin".equals(username) && "123456".equals(password)) {
            System.out.println("LOGIN SUCCESSFUL!"); // Print success message
            setVisible(false); // Hide the login window
            TableGUI.start("admin"); // Open the admin interface
        } else if ("student".equals(username) && "456789".equals(password)) {
            System.out.println("LOGIN SUCCESSFUL!"); // Print success message
            setVisible(false); // Hide the login window
            TableGUI.start("student"); // Open the student interface
        } else {
            System.out.println("LOGIN FAILED..."); // Print failure message
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }

    // Start method to launch the application
    public static void Start(String[] args) {
        SwingUtilities.invokeLater(() -> { // Ensure GUI is created on the Event Dispatch Thread
            LoginGUI loginGUI = new LoginGUI(); // Create an instance of the Login GUI
            loginGUI.setVisible(true); // Make the GUI visible
        });
    }
}
