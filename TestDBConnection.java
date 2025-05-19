package utils;

import java.sql.Connection;

public class TestDBConnection {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Connection test successful!");
            try {
                conn.close();
                System.out.println("✅ Connection closed.");
            } catch (Exception e) {
                System.out.println("⚠️ Failed to close connection.");
                e.printStackTrace();
            }
        } else {
            System.out.println("❌ Connection test failed.");
        }
    }
}
