

package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;

public class SupplierDAO {

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        

        
        String url = "jdbc:mysql://localhost:3306/inventory_db";
        String user = "root";
        String pass = "12345";

        String sql = "SELECT id, name, contact_info FROM suppliers";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Supplier s = new Supplier();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setContactInfo(rs.getString("contact_info"));
                suppliers.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suppliers;
    }
}
