package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class SQL_yhteys {

    String url = "jdbc:mysql://localhost:3306/vn?ServerTimezone=UTC+2";
    String user = "root";
    String password = "allukys123";

    public static Connection getYhteys() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/vn?serverTimezone=UTC+2";
        String user = "root";
        String password = "allukys123";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

        }
        return conn;



    }

}