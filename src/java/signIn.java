import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

@WebServlet(urlPatterns = {"/signIn"})
public class signIn extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet signIn</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet signIn at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("ema");  // Input name should match your form
        String password = request.getParameter("pass"); // Input name should match your form
        
        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/javaservlet";
        String dbUser = "root";  // replace with your DB username
        String dbPassword = "";  // replace with your DB password

        // SQL query to check if user exists with the provided email and password
        String sql = "SELECT * FROM regdata WHERE email = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
             
            statement.setString(1, email);     // Set the email in the query
            statement.setString(2, password);   // Set the password in the query

            ResultSet resultSet = statement.executeQuery(); // Execute the query
            
            // Check if a result is returned
            if (resultSet.next()) {
                // User exists, start the session
                HttpSession session = request.getSession();  // Create a new session if one doesn't exist
                session.setAttribute("userEmail", email);    // Store the user's email in the session
                
                // Redirect to a welcome page or dashboard
                response.getWriter().println("<script>alert('Login successful!'); window.location.href='welcomePage.html';</script>");
            } else {
                // User does not exist
                response.getWriter().println("<script>alert('Invalid email or password.'); window.location.href='loginPage.html';</script>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("<script>alert('Error checking login: " + e.getMessage() + "');</script>");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
