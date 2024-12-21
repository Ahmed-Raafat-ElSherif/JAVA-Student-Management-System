import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class TableGUI implements Serializable {
    public static void start() {
        new TableGUI().createGUI();
    }

    private void createGUI() {
        JFrame frame = new JFrame("SMS Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        
        // Layout setup
        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);
        

        // Input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 6, 5, 5));
        JTextField txtID = new JTextField();
        JTextField txtFirstName = new JTextField();
        JTextField txtLastName = new JTextField();
        JTextField txtCourse = new JTextField();
        JTextField txtGPA = new JTextField();
        JTextField txtPhone = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtSearch = new JTextField();

        inputPanel.add(new JLabel("ID"));
        inputPanel.add(txtID);
        inputPanel.add(new JLabel("First Name"));
        inputPanel.add(txtFirstName);
        inputPanel.add(new JLabel("Last Name"));
        inputPanel.add(txtLastName);
        inputPanel.add(new JLabel("Course"));
        inputPanel.add(txtCourse);
        inputPanel.add(new JLabel("GPA"));
        inputPanel.add(txtGPA);
        inputPanel.add(new JLabel("Phone"));
        inputPanel.add(txtPhone);
        inputPanel.add(new JLabel("E-mail"));
        inputPanel.add(txtEmail);
        inputPanel.add(new JLabel("Search"));
        inputPanel.add(txtSearch);
        
        panel.add(inputPanel, BorderLayout.NORTH);

        // Table to display data
        String[] columnNames = {"ID", "First Name", "Last Name", "Course", "GPA", "Phone", "E-mail"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        panel.add(tableScrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton btnAdd = new JButton("Add");
        JButton btnRemove = new JButton("Remove");
        JButton btnEdit = new JButton("Edit");
        JButton btnSearch = new JButton("Search");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnSearch);
        panel.add(buttonPanel, BorderLayout.SOUTH);

/*        //Database Management
        File file = new File("TableInfo.ser");
        if (file.exists()){
            //JOptionPane.showMessageDialog(frame, "Database already exists. Loading data...");
            try {
                //FileReader reader = new FileReader(file);
                TableGUI gui = null;
                FileInputStream fileIn  = new FileInputStream("TableInfo.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                try {
                    gui = (TableGUI) in.readObject();
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                in.close();
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                //JOptionPane.showMessageDialog(frame, "First Launch. Creating database...");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
*/
        // Button Actions
        btnAdd.addActionListener(e -> {
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

        btnRemove.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row to remove.");
            }
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.setValueAt(txtID.getText(), selectedRow, 0);
                tableModel.setValueAt(txtFirstName.getText(), selectedRow, 1);
                tableModel.setValueAt(txtLastName.getText(), selectedRow, 2);
                tableModel.setValueAt(txtCourse.getText(), selectedRow, 3);
                tableModel.setValueAt(txtGPA.getText(), selectedRow, 4);
                tableModel.setValueAt(txtPhone.getText(), selectedRow, 5);
                tableModel.setValueAt(txtEmail.getText(), selectedRow, 6);
                clearInputs(txtID, txtFirstName, txtLastName, txtCourse, txtGPA, txtPhone, txtEmail);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a row to edit.");
            }
        });

        btnSearch.addActionListener(e -> {
            String searchValue = txtSearch.getText().toLowerCase();
            for (int i = 0; i < table.getRowCount(); i++) {
                boolean match = false;
                for (int j = 0; j < table.getColumnCount(); j++) {
                    if (table.getValueAt(i, j).toString().toLowerCase().contains(searchValue)) {
                        match = true;
                        break;
                    }
                }
                table.setRowSelectionAllowed(true);
                table.addRowSelectionInterval(i, i);
                if (!match) {
                    table.removeRowSelectionInterval(i, i);
                }
            }
        });

        frame.setVisible(true);
    }

    private void clearInputs(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

/*     private void dataManagement(){
        TableGUI tableGUI = new TableGUI();
        try {
            FileOutputStream fileOut = new FileOutputStream("TableInfo.ser");
            try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                out.writeObject(tableGUI);
                out.close();
            } catch (FileNotFoundException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
*/
}