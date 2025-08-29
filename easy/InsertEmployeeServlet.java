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

@WebServlet("/InsertEmployeeServlet")
public class InsertEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String salaryStr = request.getParameter("salary");
        String hireDate = request.getParameter("hire_date");

        try {
            double salary = Double.parseDouble(salaryStr);

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Employee",
                "root",
                "8826"
            );

            // Insert query
            String sql = "INSERT INTO employees (name, department, salary, hire_date) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, salary);
            ps.setString(4, hireDate);

            int rows = ps.executeUpdate();
            con.close();

            if (rows > 0) {
                // Redirect to ViewEmployeesServlet to show updated table
                response.sendRedirect("ViewEmployeesServlet");
            } else {
                response.getWriter().println("<p style='color:red;'>Failed to insert employee.</p><a href='insert.html'>Try Again</a>");
            }

        } catch (Exception e) {
            response.getWriter().println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
