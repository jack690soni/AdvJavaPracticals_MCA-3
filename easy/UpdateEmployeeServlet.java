package com.pri;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/UpdateEmployeeServlet")
public class UpdateEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Show the update form
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Employee", "root", "8826");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM employees WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Update Employee</h2>");
                out.println("<form method='post' action='UpdateEmployeeServlet'>");
                out.println("<input type='hidden' name='id' value='" + id + "'>");
                out.println("Name: <input type='text' name='name' value='" + rs.getString("name") + "' required><br><br>");
                out.println("Department: <input type='text' name='department' value='" + rs.getString("department") + "'><br><br>");
                out.println("Salary: <input type='number' step='0.01' name='salary' value='" + rs.getDouble("salary") + "' required><br><br>");
                out.println("Hire Date: <input type='date' name='hire_date' value='" + rs.getDate("hire_date") + "' required><br><br>");
                out.println("<input type='submit' value='Update'>");
                out.println("</form>");
            } else {
                out.println("<p style='color:red;'>Employee not found!</p>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }

    // Handle update action
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        double salary = Double.parseDouble(request.getParameter("salary"));
        String hireDate = request.getParameter("hire_date");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Employee", "root", "8826");

            PreparedStatement ps = con.prepareStatement(
                "UPDATE employees SET name=?, department=?, salary=?, hire_date=? WHERE id=?");
            ps.setString(1, name);
            ps.setString(2, department);
            ps.setDouble(3, salary);
            ps.setString(4, hireDate);
            ps.setInt(5, id);

            ps.executeUpdate();
            con.close();

            // Redirect back to view page
            response.sendRedirect("ViewEmployeesServlet");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
