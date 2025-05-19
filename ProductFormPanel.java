package ui;

import dao.ProductDAO;
import java.awt.*;
import javax.swing.*;
import model.Product;

public class ProductFormPanel extends JPanel {

    private JTextField nameField;
    private JTextField priceField;
    private JTextField supplierIdField;
    private JButton saveButton;
    private ProductDAO productDAO;

    public ProductFormPanel() {
        productDAO = new ProductDAO();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Labels and Fields
        JLabel nameLabel = new JLabel("Product Name:");
        nameField = new JTextField(20);

        JLabel priceLabel = new JLabel("Price:");
        priceField = new JTextField(20);

        JLabel supplierIdLabel = new JLabel("Supplier ID:");
        supplierIdField = new JTextField(20);

        saveButton = new JButton("Add Product");

        // Add components to panel
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; add(nameLabel, gbc);
        gbc.gridx = 1; add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(priceLabel, gbc);
        gbc.gridx = 1; add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(supplierIdLabel, gbc);
        gbc.gridx = 1; add(supplierIdField, gbc);

        gbc.gridx = 1; gbc.gridy = 3; add(saveButton, gbc);

        // Add button functionality
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            double price;
            int supplierId;

            try {
                price = Double.parseDouble(priceField.getText());
                supplierId = Integer.parseInt(supplierIdField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price or supplier ID");
                return;
            }

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setSupplierId(supplierId);

            boolean success = productDAO.addProduct(product);
            if (success) {
                JOptionPane.showMessageDialog(this, "Product added successfully!");
                nameField.setText("");
                priceField.setText("");
                supplierIdField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add product.");
            }
        });
    }
}







