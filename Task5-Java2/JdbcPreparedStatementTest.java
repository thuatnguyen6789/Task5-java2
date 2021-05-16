package session42;
import java.sql.*; // Use classes in java.sql package
public class JdbcPreparedStatementTest { // JDK 7 and above
    public static void main(String[] args) {
        try (
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:8888/ebookshop", "myuser", "xxxx"
                ); // MySQL
                // Two PreparedStatements, one for INSERT and one for SELECT
                PreparedStatement pstmt = conn.prepareStatement(
                        "insert into books values (?, ?, ?, ?, ?)"); // Five parameters 1 to 5
                PreparedStatement pstmtSelect = conn.prepareStatement("select * from books");
                ) {
            pstmt.setInt(1, 7001); // Set values for parameters 1 to 5
            pstmt.setString(2, "Mahjong 101");
            pstmt.setString(3, "Kumar");
            pstmt.setDouble(4, 88.88);
            pstmt.setInt(5, 88);
            int rowsInserted = pstmt.executeUpdate(); // Execute statement
            System.out.println(rowsInserted + "rows affected. ");

            pstmt.setInt(1, 7002); // Change values for parameters 1 and 2
            pstmt.setString(2, "Mahjong 102");
            // No change in values for parameters 3 to 5
            rowsInserted = pstmt.executeUpdate();
            System.out.println(rowsInserted + "rows affected. ");

            // Issue a SELECT to check the changes
            ResultSet rset = pstmtSelect.executeQuery();
            while (rset.next()) {
                System.out.println(rset.getInt("id") + ", "
                        + rset.getString("author") + ", "
                        + rset.getString("title") + ", "
                        + rset.getDouble("price") + ", "
                        + rset.getInt("qty")
                );
            }
        } catch(SQLException ex) {
                ex.printStackTrace();
            }
            // Step 5: Close the resources - Done automatically by try-with-resources
        }
}
