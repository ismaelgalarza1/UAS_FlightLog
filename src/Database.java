import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:uas_flight_log.db";

    // Initialize database connection and create tables
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // Create Operator table
                String createOperatorTable = "CREATE TABLE IF NOT EXISTS operators (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "numOfFlights INTEGER," +
                        "hours REAL," +
                        "airframeName TEXT)";
                stmt.execute(createOperatorTable);

                System.out.println("Database initialized and tables created.");
            }
        } catch (SQLException e) {
            System.out.println("Database initialization failed: " + e.getMessage());
        }
    }

    // Add operator
    public static void addOperator(Operator operator) {
        String sql = "INSERT INTO operators(name, numOfFlights, hours, airframeName) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, operator.getName());
            pstmt.setInt(2, operator.getNumOfFlights());
            pstmt.setDouble(3, operator.getHours());
            pstmt.setString(4, operator.getAirframeName());
            pstmt.executeUpdate();

            System.out.println("Operator added to database.");
        } catch (SQLException e) {
            System.out.println("Failed to add operator: " + e.getMessage());
        }
    }

    // Get all operators
    public static List<Operator> getAllOperators() {
        String sql = "SELECT * FROM operators";
        List<Operator> operators = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Operator operator = new Operator(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("numOfFlights"),
                        rs.getDouble("hours"));
                operator.setAirframeName(rs.getString("airframeName"));
                operators.add(operator);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving operators: " + e.getMessage());
        }
        return operators;
    }

    // Find operator by ID
    public static Operator findOperatorById(int id) {
        String sql = "SELECT * FROM operators WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Operator operator = new Operator(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("numOfFlights"),
                        rs.getDouble("hours"));
                operator.setAirframeName(rs.getString("airframeName"));
                return operator;
            }
        } catch (SQLException e) {
            System.out.println("Error finding operator by ID: " + e.getMessage());
        }
        return null;
    }

    // Update operator
    public static void updateOperator(Operator operator) {
        String sql = "UPDATE operators SET numOfFlights = ?, hours = ?, airframeName = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, operator.getNumOfFlights());
            pstmt.setDouble(2, operator.getHours());
            pstmt.setString(3, operator.getAirframeName());
            pstmt.setInt(4, operator.getId());
            pstmt.executeUpdate();

            System.out.println("Operator updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating operator: " + e.getMessage());
        }
    }

    // Delete operator by ID
    public static boolean deleteOperatorById(int id) {
        String sql = "DELETE FROM operators WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0; // Return true if an operator was deleted
        } catch (SQLException e) {
            System.out.println("Error deleting operator: " + e.getMessage());
            return false;
        }
    }
}
