import javax.swing.*; // Allows the use of Swing components like JFrame, JPanel, JLabel, etc.
import javax.swing.table.DefaultTableCellRenderer; // Allows the use of DefaultTableCellRenderer for cell coloring purposes
import javax.swing.table.DefaultTableModel; // Allows the use of DefaultTableModel for creating table
import java.awt.*; // Provides classes for GUI layout, colors, fonts, sizes, etc.
import java.io.*; // provides methods for file handling and serialization

public class TableGUI implements Serializable {
    private static final String DATA_FILE = "data.dat"; // File to store serialized data
    private boolean isStudent; // Indicates if the current user is a student
    private static String username; // Stores the current username
    private int[] highlightedRows; // Array to store indices of highlighted rows in the table

    /**
     * Starts the GUI with the specified user type (student or not).
     *
     * @param userType the type of user (e.g., "student" or "admin")
     */
    public static void start(String userType) {
        username = userType; // Set username
        SwingUtilities.invokeLater(() -> {
            new TableGUI(userType.equals("student")).createGUI(); // Create GUI based on user type
        });
    }

    /**
     * Constructor initializes the TableGUI.
     *
     * @param isStudent whether the user is a student
     */
    public TableGUI(boolean isStudent) {
        this.isStudent = isStudent; // Set user type
        this.highlightedRows = new int[0]; // Initialize highlighted rows
    }

    /**
     * Creates the graphical user interface for the application.
     */
    public void createGUI() {
        // Create the main application frame
        JFrame frame = new JFrame("SMS Application - Logged in as " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setSize(800, 500); // Initial size
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setIconImage(new ImageIcon("assets/database-table.png").getImage()); // Set application icon

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        frame.add(mainPanel);

        // Title label
        JLabel titleLabel = new JLabel("Student Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Title font
        titleLabel.setForeground(new Color(34, 45, 65)); // Title color
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding
        mainPanel.add(titleLabel, BorderLayout.NORTH); // Add title to the top

        // Input panel for form fields
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 248, 255)); // Same background color
        GridBagConstraints gbc = new GridBagConstraints(); // Layout constraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Margins
        gbc.anchor = GridBagConstraints.WEST;

        // Text fields for user input
        JTextField txtID = new JTextField();
        JTextField txtFirstName = new JTextField();
        JTextField txtLastName = new JTextField();
        JTextField txtCourse = new JTextField();
        JTextField txtGPA = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtSearch = new JTextField();

        // Add labeled input fields
        addInputField(inputPanel, "ID", txtID, "assets/id-icon.png", gbc);
        addInputField(inputPanel, "First Name", txtFirstName, "assets/name-icon.png", gbc);
        addInputField(inputPanel, "Last Name", txtLastName, "assets/name-icon.png", gbc);
        addInputField(inputPanel, "Course", txtCourse, "assets/course-icon.png", gbc);
        addInputField(inputPanel, "GPA", txtGPA, "assets/gpa-icon.png", gbc);
        addInputField(inputPanel, "Phone", txtPhone, "assets/phone-icon.png", gbc);
        addInputField(inputPanel, "E-mail", txtEmail, "assets/email-icon.png", gbc);
        addInputField(inputPanel, "Search", txtSearch, "assets/search-icon.png", gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255)); // Match background

        // Table setup
        String[] columnNames = {"ID", "First Name", "Last Name", "Course", "GPA", "Phone", "E-mail"};
        DefaultTableModel tableModel = loadTableModel(columnNames); // Load or create table model
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Prevent editing certain columns if the user is a student
                return !(isStudent && (column == 0 || column == 3 || column == 4));
            }
        };

        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Table font
        table.setRowHeight(30); // Row height
        table.setIntercellSpacing(new Dimension(10, 5)); // Cell spacing
        CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer(isStudent, this);
        table.setDefaultRenderer(Object.class, cellRenderer); // Custom renderer
        JScrollPane tableScrollPane = new JScrollPane(table); // Add table to scroll pane

        // Add buttons for functionalities
        addButton(buttonPanel, "Add", e -> {
            String[] rowData = {
                txtID.getText(),
                txtFirstName.getText(),
                txtLastName.getText(),
                txtCourse.getText(),
                txtGPA.getText(),
                txtPhone.getText(),
                txtEmail.getText()
            };
            tableModel.addRow(rowData); // Add row to the table
            clearInputs(txtID, txtFirstName, txtLastName, txtCourse, txtGPA, txtPhone, txtEmail); // Clear inputs
        });

        if (!isStudent) { // Remove row option only for non-student users
            addButton(buttonPanel, "Remove Row", e -> {
                int selectedRow = table.getSelectedRow(); // Get selected row
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow); // Remove selected row
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to remove."); // Error message
                }
            });
        }

        addButton(buttonPanel, "Clear Input Fields", e -> clearInputs(txtID, txtFirstName, txtLastName, txtCourse, txtGPA, txtPhone, txtEmail, txtSearch));
        addButton(buttonPanel, "Search", e -> {
            String searchValue = txtSearch.getText().toLowerCase(); // Search term
            java.util.List<Integer> matches = new java.util.ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) { // Iterate through rows
                for (int j = 0; j < table.getColumnCount(); j++) {
                    if (table.getValueAt(i, j).toString().toLowerCase().contains(searchValue)) { // Match found
                        matches.add(i); // Add to matches
                        break;
                    }
                }
            }
            highlightedRows = matches.stream().mapToInt(i -> i).toArray(); // Highlight matched rows
            table.repaint(); // Repaint table to show highlights
        });

        // Combine input and button panels into a container
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(inputPanel);
        topContainer.add(buttonPanel);

        // Split pane to separate input fields and table
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topContainer, tableScrollPane);
        splitPane.setDividerLocation(350); // Divider position
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);

        mainPanel.add(splitPane, BorderLayout.CENTER); // Add split pane to the main panel

        // Save data on window close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveTableModel(tableModel); // Save table data
            }
        });

        frame.setVisible(true); // Display the frame
        frame.setSize(799, 699); // Adjust frame size
    }

   // Adds a labeled input field (label and text field) with an icon to a panel
private void addInputField(JPanel panel, String labelText, JTextField field, String iconPath, GridBagConstraints gbc) {
    // Create a label with text, an icon, and left alignment
    JLabel label = new JLabel(labelText, 
        new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)), 
        SwingConstants.LEFT);
    label.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font style and size
    label.setForeground(new Color(34, 45, 65)); // Set label text color

    // Configure GridBagConstraints for the label
    gbc.gridwidth = 1; // Single column width
    gbc.weightx = 0; // No horizontal stretching
    gbc.fill = GridBagConstraints.NONE; // No resizing
    panel.add(label, gbc); // Add label to the panel

    // Configure GridBagConstraints for the text field
    gbc.gridx++; // Move to the next column
    gbc.weightx = 1; // Allow horizontal stretching for the text field
    gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
    panel.add(field, gbc); // Add text field to the panel

    // Reset column position and move to the next row
    gbc.gridx = 0;
    gbc.gridy++;
}

// Adds a button with specified text and action listener to a panel
private void addButton(JPanel panel, String text, java.awt.event.ActionListener action) {
    JButton button = new JButton(text); // Create a button with the specified text
    button.setFont(new Font("Arial", Font.BOLD, 16)); // Set button font
    button.setBackground(new Color(58, 123, 213)); // Set button background color
    button.setForeground(Color.WHITE); // Set button text color
    button.setFocusPainted(false); // Remove focus painting
    button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Add padding
    button.addActionListener(action); // Add the specified action listener to the button
    panel.add(button); // Add the button to the panel
}

// Clears the text in all the specified text fields
public void clearInputs(JTextField... fields) {
    for (JTextField field : fields) {
        field.setText(""); // Set each text field to an empty string
    }
}

// Saves the table model data to a file for persistence
private void saveTableModel(DefaultTableModel tableModel) {
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
        oos.writeObject(tableModel.getDataVector()); // Serialize the table data
    } catch (IOException e) {
        e.printStackTrace(); // Print stack trace in case of an error
    }
}

// Loads the table model data from a file, or creates a new model if no data exists
private DefaultTableModel loadTableModel(String[] columnNames) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
        @SuppressWarnings("unchecked") // Suppress unchecked cast warning
        java.util.Vector<java.util.Vector<Object>> data = 
            (java.util.Vector<java.util.Vector<Object>>) ois.readObject(); // Deserialize data
        return new DefaultTableModel(data, new java.util.Vector<>(java.util.Arrays.asList(columnNames))); // Create table model
    } catch (IOException | ClassNotFoundException e) {
        return new DefaultTableModel(columnNames, 0); // Return an empty table model if loading fails
    }
}

// Custom table cell renderer to handle specific styling and highlighting logic
private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private final boolean isStudent; // Indicates if the user is a student
    private final TableGUI tableGUI; // Reference to the main GUI class for accessing highlighted rows

    public CustomTableCellRenderer(boolean isStudent, TableGUI tableGUI) {
        this.isStudent = isStudent; // Set user type
        this.tableGUI = tableGUI; // Set reference to the GUI class
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Get the default rendering component for the cell
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Gray out specific columns for student users
        if (isStudent && (column == 0 || column == 3 || column == 4)) {
            cell.setBackground(new Color(224, 224, 224)); // Light gray background
        } else {
            cell.setBackground(Color.WHITE); // Default white background
        }

        // Highlight rows that match the search criteria
        if (java.util.Arrays.stream(tableGUI.highlightedRows).anyMatch(r -> r == row)) {
            cell.setBackground(Color.PINK); // Highlight matching rows with pink
        }

        return cell; // Return the styled component
    }
}
}