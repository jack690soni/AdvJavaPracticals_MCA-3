package com.aj;

import java.sql.*;

public class EmpDao {

    public static Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String username = "root";
        String password = "password";
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // updated for newer driver
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established: " + con);
        } catch (Exception e) {
            System.out.println("Connection error: " + e);
        }
        return con;
    }

    public static int save(Emp e) {
        int status = 0;
        try {
            Connection con = EmpDao.getConnection();
            if (con == null) {
                throw new RuntimeException("Database connection is null.");
            }

            PreparedStatement ps = con.prepareStatement(
                "insert into user905(name,password,email,country) values (?,?,?,?)");
            ps.setString(1, e.getName());
            ps.setString(2, e.getPassword());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getCountry());

            status = ps.executeUpdate();
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return status;
    }
}


