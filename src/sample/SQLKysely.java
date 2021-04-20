package sample;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class SQLKysely {


    public static void main(String[] args) {

        // Kysely hakee kaikki toiminta-alueet: niitä pitäisi olla tässä vaiheessa kolme (ks. discord) -- ne on laitettu
        // suoraan tietokantaan mysql:ssä
       /* String kysely = "SELECT toimintaalue_id, nimi FROM toimintaalue LIMIT 3;";

        try {
            Connection conn = SQL_yhteys.getYhteys();
            Statement stmt = conn.createStatement();
            ResultSet tulokset = stmt.executeQuery(kysely);

            while (tulokset.next()) {
                System.out.println(tulokset.getString("toimintaalue_id") + " " + tulokset.getString("nimi"));
            }
            stmt.close();
            tulokset.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }*/
        try {
             SQL_yhteys.getMokit();

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }



    }
}