import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ContactServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ContactDB";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASSWORD = "password";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        if (name == null || email == null || phone == null) {
            out.println("<h1>Validation failed: All fields are required!</h1>");
            return;
        }

        // JDBC connection and data persistence
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO ContactDetails (name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, phone);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                out.println("<h1>Contact details submitted successfully!</h1>");
            } else {
                out.println("<h1>Failed to submit contact details!</h1>");
            }
        } catch (SQLException e) {
            out.println("<h1>Error: Unable to connect to the database!</h1>");
            e.printStackTrace(out);
        }
    }
}
