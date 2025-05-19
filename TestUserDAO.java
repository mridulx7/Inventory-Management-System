import java.sql.Connection;

public class TestUserDAO {
    public static void main(String[] args) {
        Connection conn = utils.DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("✅ Successfully connected to MySQL!");
        } else {
            System.out.println("❌ Failed to connect.");
        }
    }
}
