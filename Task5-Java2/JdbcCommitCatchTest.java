package session42;
import java.sql.*;
public class JdbcCommitCatchTest { // JDK 7 and above
    public static void main(String[] args) throws SQLException {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:8888/ebookshop", "myuser", "xxxx"); // MySQL
                Statement stmt = conn.createStatement();
                ) {
            try {
                // Disable auto-commit
                conn.setAutoCommit(false);

                // Issue two INSERT statements
                stmt.executeUpdate("insert into books values (4001, 'Paul Chan', 'Mahjong 101', 4.4, 4)");
                // Duplicate primary key, which trigger a SQLException
                stmt.executeUpdate("insert into books values (4001, 'Peter Chan', 'Mahjong 102', 4.4, 4)");
                conn.commit(); // Commit chagnes only if all statements succeed.
            } catch (SQLException ex) {
                System.out.println("-- Rolling back changes --");
                conn.rollback(); // Rollback to the last commit.
                ex.printStackTrace();
            }
        }
    }
}
