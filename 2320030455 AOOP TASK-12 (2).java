import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class StudentDetailsServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "Student";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "password";

    @Override
    public void init() throws ServletException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            Statement statement = connection.createStatement();

            // Create the Student database
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            statement.execute("USE " + DB_NAME);

            // Create the Registration table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Registration ("
                    + "id INT PRIMARY KEY, "
                    + "name VARCHAR(100), "
                    + "address VARCHAR(255), "
                    + "program VARCHAR(100))");

            // Insert records
            statement.executeUpdate("INSERT INTO Registration (id, name, address, program) VALUES "
                    + "(100, 'John Doe', '123 Main St', 'Computer Science'),"
                    + "(101, 'Jane Smith', '456 Oak Ave', 'Mechanical Engineering'),"
                    + "(102, 'Alice Brown', '789 Pine Rd', 'Electrical Engineering'),"
                    + "(103, 'Bob White', '321 Maple Dr', 'Civil Engineering')");

        } catch (SQLException e) {
            throw new ServletException("Database initialization error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try (Connection connection = DriverManager.getConnection(JDBC_URL + DB_NAME, JDBC_USER, JDBC_PASSWORD)) {
            Statement statement = connection.createStatement();
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");

            switch (action) {
                case "display":
                    // Display records
                    ResultSet rs = statement.executeQuery("SELECT * FROM Registration");
                    out.println("<h1>Student Records</h1>");
                    out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Address</th><th>Program</th></tr>");
                    while (rs.next()) {
                        out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("name")
                                + "</td><td>" + rs.getString("address") + "</td><td>" + rs.getString("program") + "</td></tr>");
                    }
                    out.println("</table>");
                    break;

                case "update":
                    // Update records
                    statement.executeUpdate("UPDATE Registration SET program='Data Science' WHERE id=100");
                    statement.executeUpdate("UPDATE Registration SET program='AI Engineering' WHERE id=101");
                    out.println("<h1>Records updated successfully</h1>");
                    break;

                case "delete":
                    // Delete record
                    statement.executeUpdate("DELETE FROM Registration WHERE id=101");
                    out.println("<h1>Record deleted successfully</h1>");
                    break;

                default:
                    out.println("<h1>Invalid action</h1>");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Error processing request", e);
        }
    }
}
