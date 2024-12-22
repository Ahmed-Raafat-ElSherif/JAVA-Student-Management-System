import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class TableGUI implements Serializable {
    private static final String DATA_FILE = "data.dat";
    private boolean isStudent;
    private static String username;
    private int[] highlightedRows;

    public static void start(String userType) {
        username = userType;
        SwingUtilities.invokeLater(() -> {
            new TableGUI(userType.equals("student")).createGUI();
        });
    }

    public TableGUI(boolean isStudent) {
        this.isStudent = isStudent;
        this.highlightedRows = new int[0];
    }

    public void createGUI() {
        JFrame frame = new JFrame("SMS Application - Logged in as " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon("assets/database-table.png").getImage());

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        frame.add(mainPanel);

        // Title
        JLabel titleLabel = new JLabel("Student Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(34, 45, 65));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Input Panel with GridBagLayout
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JTextField txtID = new JTextField();
        JTextField txtFirstName = new JTextField();
        JTextField txtLastName = new JTextField();
        JTextField txtCourse = new JTextField();
        JTextField txtGPA = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtSearch = new JTextField();

        addInputField(inputPanel, "ID", txtID, "assets/id-icon.png", gbc);
        addInputField(inputPanel, "First Name", txtFirstName, "assets/name-icon.png", gbc);
        addInputField(inputPanel, "Last Name", txtLastName, "assets/name-icon.png", gbc);
        addInputField(inputPanel, "Course", txtCourse, "assets/course-icon.png", gbc);
        addInputField(inputPanel, "GPA", txtGPA, "assets/gpa-icon.png", gbc);
        addInputField(inputPanel, "Phone", txtPhone, "assets/phone-icon.png", gbc);
        addInputField(inputPanel, "E-mail", txtEmail, "assets/email-icon.png", gbc);
        addInputField(inputPanel, "Search", txtSearch, "assets/search-icon.png", gbc);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        // Table
        String[] columnNames = {"ID", "First Name", "Last Name", "Course", "GPA", "Phone", "E-mail"};
        DefaultTableModel tableModel = loadTableModel(columnNames);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return !(isStudent && (column == 0 || column == 3 || column == 4));
            }
        };

        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setIntercellSpacing(new Dimension(10, 5));
        CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer(isStudent, this);
        table.setDefaultRenderer(Object.class, cellRenderer);
        JScrollPane tableScrollPane = new JScrollPane(table);

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
            tableModel.addRow(rowData);
            clearInputs(txtID, txtFirstName, txtLastName, txtCourse, txtGPA, txtPhone, txtEmail);
        });

        if (!isStudent) {
            addButton(buttonPanel, "Remove Row", e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    tableModel.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row to remove.");
                }
            });
        }

        addButton(buttonPanel, "Clear Input Fields", e -> clearInputs(txtID, txtFirstName, txtLastName, txtCourse, txtGPA, txtPhone, txtEmail, txtSearch));
        addButton(buttonPanel, "Search", e -> {
            String searchValue = txtSearch.getText().toLowerCase();
            java.util.List<Integer> matches = new java.util.ArrayList<>();
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    if (table.getValueAt(i, j).toString().toLowerCase().contains(searchValue)) {
                        matches.add(i);
                        break;
                    }
                }
            }
            highlightedRows = matches.stream().mapToInt(i -> i).toArray();
            table.repaint();
        });

        // Combine Input and Button Panels into a Container
        JPanel topContainer = new JPanel();
        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
        topContainer.add(inputPanel);
        topContainer.add(buttonPanel);

        // JSplitPane to split topContainer and table
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topContainer, tableScrollPane);
        splitPane.setDividerLocation(350); // Adjust divider position
        splitPane.setResizeWeight(0.5);
        splitPane.setOneTouchExpandable(true);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveTableModel(tableModel);
            }
        });

        frame.setVisible(true);
        frame.setSize(799, 699);
    }

    private void addInputField(JPanel panel, String labelText, JTextField field, String iconPath, GridBagConstraints gbc) {
        JLabel label = new JLabel(labelText, new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)), SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(34, 45, 65));

        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(label, gbc);

        gbc.gridx++;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
    }

    private void addButton(JPanel panel, String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(58, 123, 213));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.addActionListener(action);
        panel.add(button);
    }

    public void clearInputs(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void saveTableModel(DefaultTableModel tableModel) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(tableModel.getDataVector());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel loadTableModel(String[] columnNames) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            @SuppressWarnings("unchecked")
            java.util.Vector<java.util.Vector<Object>> data = (java.util.Vector<java.util.Vector<Object>>) ois.readObject();
            return new DefaultTableModel(data, new java.util.Vector<>(java.util.Arrays.asList(columnNames)));
        } catch (IOException | ClassNotFoundException e) {
            return new DefaultTableModel(columnNames, 0);
        }
    }

    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private final boolean isStudent;
        private final TableGUI tableGUI;

        public CustomTableCellRenderer(boolean isStudent, TableGUI tableGUI) {
            this.isStudent = isStudent;
            this.tableGUI = tableGUI;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isStudent && (column == 0 || column == 3 || column == 4)) {
                cell.setBackground(new Color(224, 224, 224));
            } else {
                cell.setBackground(Color.WHITE);
            }
            if (java.util.Arrays.stream(tableGUI.highlightedRows).anyMatch(r -> r == row)) {
                cell.setBackground(Color.PINK);
            }
            return cell;
        }
    }
}
