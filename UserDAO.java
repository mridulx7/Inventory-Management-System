package dao;

import java.sql.*;
import model.User;

public class UserDAO {

    private Connection conn;

    public UserDAO() {
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_database", "root", "12345");
        System.out.println("✅ Connected to database successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    public User login(String email, String password) {
        System.out.println("Connected to DB: " + (conn != null));
        System.out.println("Email: " + email + ", Password: " + password);

        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password); // ⚠ Password should be hashed in real apps
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
