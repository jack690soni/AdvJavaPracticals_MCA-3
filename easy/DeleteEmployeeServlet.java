package com.pri;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DeleteEmployeeServlet")
public class DeleteEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        try {
            int id = Integer.parseInt(idStr);

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Employee",
                "root",
                "8826"
            );

            // Delete query
            String sql = "DELETE FROM employees WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            con.close();

            // Redirect back to view page
            response.sendRedirect("ViewEmployeesServlet");

        } catch (Exception e) {
            response.getWriter().println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
