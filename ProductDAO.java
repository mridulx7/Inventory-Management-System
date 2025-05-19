package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import utils.DatabaseConnection;

public class ProductDAO {

public boolean addProduct(Product product) {
    String sql = "INSERT INTO products (name, price, category, quantity) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, product.getName());
        stmt.setDouble(2, product.getPrice());
        stmt.setString(3, product.getCategory());
        stmt.setInt(4, product.getQuantity());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            System.out.println("No rows inserted.");
            return false;
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            }
        }

        return true;

    } catch (Exception e) {
        System.out.println("Error adding product: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}


    // Fetch all products from the database
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setCategory(rs.getString("category"));
                p.setQuantity(rs.getInt("quantity"));
                products.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public boolean updateProduct(Product product) {
    String sql = "UPDATE products SET name = ?, price = ?, category = ?, quantity = ? WHERE id = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, product.getName());
        stmt.setDouble(2, product.getPrice());
        stmt.setString(3, product.getCategory());
        stmt.setInt(4, product.getQuantity());
        stmt.setInt(5, product.getId());

        int rowsUpdated = stmt.executeUpdate();
        return rowsUpdated > 0;

    } catch (Exception e) {
        System.out.println("Error updating product: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}


    // Delete product by ID
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

                } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }
}
