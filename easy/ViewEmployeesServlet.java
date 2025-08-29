package com.pri;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewEmployeesServlet")
public class ViewEmployeesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Employee",
                "root",
                "8826"
            );

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

            out.println("<h2>Employee List</h2>");
            out.println("<table border='1' cellpadding='5'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th><th>Hire Date</th><th>Actions</th></tr>");

            while (rs.next()) {
                int id = rs.getInt("id");
                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td>");
                out.println("<td>" + rs.getDouble("salary") + "</td>");
                out.println("<td>" + rs.getDate("hire_date") + "</td>");
                out.println("<td>"
                        + "<a href='DeleteEmployeeServlet?id=" + id + "' onclick=\"return confirm('Are you sure you want to delete this employee?');\">Delete</a> | "
                        + "<a href='UpdateEmployeeServlet?id=" + id + "'>Update</a>"
                        + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='insert.html'>Add New Employee</a>");

            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
