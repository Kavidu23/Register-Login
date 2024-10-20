import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/signUp"})
public class signUp extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String name = request.getParameter("Name");
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    // Database connection details
    String jdbcURL = "jdbc:mysql://localhost:3306/javaservlet";
    String dbUser = "root";  // replace with your DB username
    String dbPassword = "";  // replace with your DB password

    // Updated SQL to include password
    String sql = "INSERT INTO regdata (name, email, password) VALUES (?, ?, ?)";

    try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, name);     // Setting name
        statement.setString(2, email);    // Setting email
        statement.setString(3, password);  // Setting password

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            response.getWriter().println("<script type=\"text/javascript\">");
            response.getWriter().println("alert('Login successful!');");
            response.getWriter().println("window.location.href = 'index.html';");  // Redirect to another page after alert
            response.getWriter().println("</script>");

        } else {
            response.getWriter().println("<script>alert('Failed to insert data.');</script>");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        response.getWriter().println("<script>alert('Error registering: " + e.getMessage() + "');</script>");
    }
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
