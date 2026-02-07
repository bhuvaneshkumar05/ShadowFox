import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ContactApp {

    static ArrayList<Contact> contacts = new ArrayList<>();
    static final String FILE_NAME = "contacts.csv";

    public static void main(String[] args) {

        JFrame frame = new JFrame("Contact Management System");
        frame.setSize(750, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        // ---------- TOP PANEL (ADD CONTACT FORM) ----------
        JPanel topPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel phoneLabel = new JLabel("Phone:");
        JTextField phoneField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JButton addButton = new JButton("Add Contact");
        JButton deleteButton = new JButton("Delete Selected Contact");

        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(phoneLabel);
        topPanel.add(phoneField);
        topPanel.add(emailLabel);
        topPanel.add(emailField);
        topPanel.add(addButton);
        topPanel.add(deleteButton);

        // ---------- TABLE ----------
        String[] columns = {"Name", "Phone", "Email"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // ---------- SEARCH PANEL ----------
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel searchLabel = new JLabel("Search Name:");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // Load contacts
        loadContacts(tableModel);

        // ---------- ADD CONTACT ----------
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields");
                return;
            }

            Contact contact = new Contact(name, phone, email);
            contacts.add(contact);
            tableModel.addRow(new Object[]{name, phone, email});
            saveContacts();

            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
        });

        // ---------- DELETE CONTACT ----------
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Select a contact to delete");
            } else {
                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete this contact?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    contacts.remove(selectedRow);
                    tableModel.removeRow(selectedRow);
                    saveContacts();
                }
            }
        });

        // ---------- SEARCH CONTACT ----------
        searchButton.addActionListener(e -> {
            String searchName = searchField.getText().trim().toLowerCase();
            boolean found = false;

            if (searchName.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter name to search");
                return;
            }

            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String tableName = tableModel.getValueAt(i, 0).toString().toLowerCase();
                if (tableName.equals(searchName)) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                    found = true;
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(frame, "Contact not found");
            }
        });

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(searchPanel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ---------- SAVE CONTACTS ----------
    static void saveContacts() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            pw.println("Name,Phone,Email");
            for (Contact c : contacts) {
                pw.println(c.name + "," + c.phone + "," + c.email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------- LOAD CONTACTS ----------
    static void loadContacts(DefaultTableModel tableModel) {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Contact c = new Contact(parts[0], parts[1], parts[2]);
                    contacts.add(c);
                    tableModel.addRow(new Object[]{parts[0], parts[1], parts[2]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

