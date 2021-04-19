package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SQLKysely {


    public static void main(String[] args) {

        String kysely = "SELECT first_name, last_name FROM actor LIMIT 10;";

        try {
            Connection conn = SQL_yhteys.getYhteys();
            Statement stmt = conn.createStatement();
            ResultSet tulokset = stmt.executeQuery(kysely);

            while (tulokset.next()) {
                System.out.println(tulokset.getString("first_name") + " " + tulokset.getString("last_name"));
            }
            stmt.close();
            tulokset.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }


    }
}