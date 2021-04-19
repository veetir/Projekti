package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SQLKysely {


    public static void main(String[] args) {

        String kysely = "SELECT etunimi, sukunimi FROM asiakas LIMIT 10;";

        try {
            Connection conn = SQL_yhteys.getYhteys();
            Statement stmt = conn.createStatement();
            ResultSet tulokset = stmt.executeQuery(kysely);

            while (tulokset.next()) {
                System.out.println(tulokset.getString("etunimi") + " " + tulokset.getString("sukunimi"));
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