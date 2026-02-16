import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

public class InventoryApp {

    ArrayList<Product> products = new ArrayList<>();
    final String FILE_NAME = "inventory.dat";

    JFrame frame;
    JTextField idField, nameField, qtyField, priceField;
    JTextArea displayArea;

    public InventoryApp() {

        loadFromFile();   // 👈 Load saved data on startup

        frame = new JFrame("Invexa – Inventory Control Dashboard");
        frame.setSize(850, 560);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel title = new JLabel("Invexa – Inventory Control Dashboard", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setOpaque(true);
        title.setBackground(new Color(33, 150, 243));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(15, 10, 15, 10));
        frame.add(title, BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 14));

        tabs.add("Product Entry", createProductEntryPage());
        tabs.add("Inventory Records", createInventoryPage());

        frame.add(tabs, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // ================= PRODUCT ENTRY PAGE =================
    JPanel createProductEntryPage() {

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new TitledBorder("Product Information"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 15);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        idField = new JTextField(18);
        nameField = new JTextField(18);
        qtyField = new JTextField(18);
        priceField = new JTextField(18);

        JTextField[] fields = { idField, nameField, qtyField, priceField };
        for (JTextField f : fields) f.setFont(fieldFont);

        addField(form, gbc, 0, "Product ID", idField, labelFont);
        addField(form, gbc, 1, "Product Name", nameField, labelFont);
        addField(form, gbc, 2, "Quantity", qtyField, labelFont);
        addField(form, gbc, 3, "Price (₹)", priceField, labelFont);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttons.setBackground(Color.WHITE);

        JButton add = createButton("Add", new Color(76, 175, 80));
        JButton update = createButton("Update", new Color(255, 193, 7));
        JButton delete = createButton("Delete", new Color(244, 67, 54));

        buttons.add(add);
        buttons.add(update);
        buttons.add(delete);

        add.addActionListener(e -> addProduct());
        update.addActionListener(e -> updateProduct());
        delete.addActionListener(e -> deleteProduct());

        panel.add(form, BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.CENTER);

        return panel;
    }

    // ================= INVENTORY PAGE =================
    JPanel createInventoryPage() {

        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel("Search Product Name:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JButton searchBtn = createButton("Search", new Color(76, 175, 80));
        JButton refreshBtn = createButton("Refresh", new Color(33, 150, 243));

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(refreshBtn);

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        displayArea.setEditable(false);

        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setBorder(new TitledBorder("Inventory Records"));

        searchBtn.addActionListener(e ->
                searchProductByName(searchField.getText()));

        refreshBtn.addActionListener(e -> viewProducts());

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // ================= HELPERS =================
    void addField(JPanel panel, GridBagConstraints gbc, int y,
                  String text, JTextField field, Font font) {

        gbc.gridx = 0;
        gbc.gridy = y;
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(150, 38));
        return btn;
    }

    // ================= CRUD + FILE SAVE =================
    void addProduct() {
        try {
            int id = Integer.parseInt(idField.getText());

            for (Product p : products) {
                if (p.id == id) {
                    JOptionPane.showMessageDialog(frame, "Product ID already exists!");
                    return;
                }
            }

            products.add(new Product(
                    id,
                    nameField.getText(),
                    Integer.parseInt(qtyField.getText()),
                    Double.parseDouble(priceField.getText())
            ));

            saveToFile();
            JOptionPane.showMessageDialog(frame, "Product Added");
            clear();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input");
        }
    }

    void updateProduct() {
        try {
            int id = Integer.parseInt(idField.getText());

            for (Product p : products) {
                if (p.id == id) {
                    p.name = nameField.getText();
                    p.quantity = Integer.parseInt(qtyField.getText());
                    p.price = Double.parseDouble(priceField.getText());
                    saveToFile();
                    JOptionPane.showMessageDialog(frame, "Product Updated");
                    clear();
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Product Not Found");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input");
        }
    }

    void deleteProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            products.removeIf(p -> p.id == id);
            saveToFile();
            JOptionPane.showMessageDialog(frame, "Product Deleted");
            clear();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Invalid Input");
        }
    }

    void viewProducts() {
        displayArea.setText(
                String.format("%-10s %-20s %-10s %-10s\n",
                        "ID", "NAME", "QTY", "PRICE")
        );
        displayArea.append("----------------------------------------------------------\n");

        for (Product p : products) {
            displayArea.append(
                    String.format("%-10d %-20s %-10d %-10.2f\n",
                            p.id, p.name, p.quantity, p.price)
            );
        }
    }

    void searchProductByName(String keyword) {

        displayArea.setText(
                String.format("%-10s %-20s %-10s %-10s\n",
                        "ID", "NAME", "QTY", "PRICE")
        );
        displayArea.append("----------------------------------------------------------\n");

        boolean found = false;

        for (Product p : products) {
            if (p.name.toLowerCase().contains(keyword.toLowerCase())) {
                displayArea.append(
                        String.format("%-10d %-20s %-10d %-10.2f\n",
                                p.id, p.name, p.quantity, p.price)
                );
                found = true;
            }
        }

        if (!found) {
            displayArea.append("No matching product found.");
        }
    }

    // ================= FILE HANDLING =================
    void saveToFile() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(products);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Error saving data");
        }
    }

    void loadFromFile() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            products = (ArrayList<Product>) ois.readObject();
        } catch (Exception e) {
            products = new ArrayList<>();
        }
    }

    void clear() {
        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args) {
        new InventoryApp();
    }
}