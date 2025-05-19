
package ui;

import dao.ProductDAO;
import dao.SupplierDAO;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Product;
import model.Supplier;

public class InventoryFrame extends JFrame {

    public InventoryFrame() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Inventory Management System");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(20, 30, 100, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(130, 30, 200, 25);

        JLabel priceLabel = new JLabel("Product Price:");
        priceLabel.setBounds(20, 70, 100, 25);
        JTextField priceField = new JTextField();
        priceField.setBounds(130, 70, 200, 25);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(20, 110, 100, 25);
        JTextField quantityField = new JTextField();
        quantityField.setBounds(130, 110, 200, 25);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setBounds(20, 150, 100, 25);
        JComboBox<String> categoryCombo = new JComboBox<>(new String[]{"Electronics", "Clothing", "Food", "Other"});
        categoryCombo.setBounds(130, 150, 200, 25);

        JLabel supplierLabel = new JLabel("Supplier:");
        supplierLabel.setBounds(20, 190, 100, 25);
        JComboBox<String> supplierCombo = new JComboBox<>();
        supplierCombo.setBounds(130, 190, 200, 25);
        Map<String, Integer> supplierNameToIdMap = new HashMap<>();

        JButton addButton = new JButton("Add Product");
        addButton.setBounds(20, 230, 150, 30);

        JButton updateButton = new JButton("Update Product");
        updateButton.setBounds(180, 230, 150, 30);

        JButton deleteButton = new JButton("Delete Product");
        deleteButton.setBounds(100, 270, 150, 30);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(20, 320, 100, 25);
        JTextField searchField = new JTextField();
        searchField.setBounds(130, 320, 200, 25);
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(130, 355, 90, 25);
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(240, 355, 90, 25);

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(categoryLabel);
        panel.add(categoryCombo);
        panel.add(supplierLabel);
        panel.add(supplierCombo);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);
        panel.add(resetButton);

        String[] columns = {"ID", "Name", "Category", "Price", "Quantity", "Supplier"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setBounds(350, 30, 570, 400);
        panel.add(scrollPane);

        add(panel);

        ProductDAO productDAO = new ProductDAO();
        SupplierDAO supplierDAO = new SupplierDAO();

        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        supplierCombo.removeAllItems();
        supplierNameToIdMap.clear();
        for (Supplier s : suppliers) {
            System.out.println("Loading supplier: " + s.getName());
            supplierCombo.addItem(s.getName());
            supplierNameToIdMap.put(s.getName(), s.getId());
        }

        Runnable loadTable = () -> {
            tableModel.setRowCount(0);
            List<Product> products = productDAO.getAllProducts();
            for (Product p : products) {
                String supplierName = suppliers.stream()
                        .filter(s -> s.getId() == p.getSupplierId())
                        .map(Supplier::getName)
                        .findFirst().orElse("");
                tableModel.addRow(new Object[]{
                        p.getId(),
                        p.getName(),
                        p.getCategory(),
                        p.getPrice(),
                        p.getQuantity(),
                        supplierName
                });
            }
        };

        loadTable.run();

        final int[] selectedId = {0};

        productTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = productTable.getSelectedRow();
                selectedId[0] = (int) tableModel.getValueAt(row, 0);
                nameField.setText((String) tableModel.getValueAt(row, 1));
                categoryCombo.setSelectedItem(tableModel.getValueAt(row, 2));
                priceField.setText(String.valueOf(tableModel.getValueAt(row, 3)));
                quantityField.setText(String.valueOf(tableModel.getValueAt(row, 4)));

                String supplierName = (String) tableModel.getValueAt(row, 5);
                supplierCombo.setSelectedItem(supplierName);
            }
        });

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String category = (String) categoryCombo.getSelectedItem();
            String priceStr = priceField.getText();
            String quantityStr = quantityField.getText();
            String supplierName = (String) supplierCombo.getSelectedItem();

            if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || supplierName == null) {
                JOptionPane.showMessageDialog(InventoryFrame.this, "Please fill all fields");
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                int quantity = Integer.parseInt(quantityStr);
                int supplierId = supplierNameToIdMap.get(supplierName);

                Product product = new Product();
                product.setName(name);
                product.setCategory(category);
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setSupplierId(supplierId);

                if (productDAO.addProduct(product)) {
                    loadTable.run();
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    categoryCombo.setSelectedIndex(0);
                    supplierCombo.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Product added successfully!");
                } else {
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Error adding product.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(InventoryFrame.this, "Invalid input for price/quantity.");
            }
        });

        updateButton.addActionListener(e -> {
            if (selectedId[0] == 0) {
                JOptionPane.showMessageDialog(InventoryFrame.this, "Please select a product to update.");
                return;
            }

            try {
                String name = nameField.getText();
                String category = (String) categoryCombo.getSelectedItem();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String supplierName = (String) supplierCombo.getSelectedItem();

                if (supplierName == null) {
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Please select a supplier.");
                    return;
                }

                int supplierId = supplierNameToIdMap.get(supplierName);

                Product product = new Product();
                product.setId(selectedId[0]);
                product.setName(name);
                product.setCategory(category);
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setSupplierId(supplierId);

                if (productDAO.updateProduct(product)) {
                    loadTable.run();
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Product updated.");
                } else {
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Update failed.");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(InventoryFrame.this, "Invalid number input.");
            }
        });

        deleteButton.addActionListener(e -> {
            if (selectedId[0] == 0) {
                JOptionPane.showMessageDialog(InventoryFrame.this, "Select a product to delete.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(InventoryFrame.this, "Are you sure you want to delete this product?");
            if (confirm == JOptionPane.YES_OPTION) {
                if (productDAO.deleteProduct(selectedId[0])) {
                    loadTable.run();
                    selectedId[0] = 0;
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    categoryCombo.setSelectedIndex(0);
                    supplierCombo.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Product deleted.");
                } else {
                    JOptionPane.showMessageDialog(InventoryFrame.this, "Delete failed.");
                }
            }
        });

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim().toLowerCase();
            tableModel.setRowCount(0);
            for (Product p : productDAO.getAllProducts()) {
                if (p.getName().toLowerCase().contains(searchText)) {
                    String supplierName = suppliers.stream()
                            .filter(s -> s.getId() == p.getSupplierId())
                            .map(Supplier::getName)
                            .findFirst().orElse("");
                    tableModel.addRow(new Object[]{
                            p.getId(),
                            p.getName(),
                            p.getCategory(),
                            p.getPrice(),
                            p.getQuantity(),
                            supplierName
                    });
                }
            }
        });

        resetButton.addActionListener(e -> {
            searchField.setText("");
            loadTable.run();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InventoryFrame().setVisible(true));
    }
}











