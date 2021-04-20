package sample;

import java.sql.*;


public class SQL_yhteys {
    // Huom! Vaihda user & password, tarkista myös url
    String url = "jdbc:mysql://localhost:3306/vn?ServerTimezone=Helsinki/Finland";
    String user = "root";
    String password = "scape123";


    public static Connection getYhteys() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/vn?serverTimezone=Europe/Helsinki";
        String user = "root", password = "scape123";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());

        }
        return conn;


    }
    // metodi, joka tulostaa tietokannan jokaisen asiakkaan etunimen, sukunimen ja lähiosoitteen
    public static void getAsiakkaat() throws SQLException{
        String sql = "SELECT * " +
                "FROM asiakas";

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("etunimi") + "\t" +
                        rs.getString("sukunimi")  + "\t" +
                        rs.getString("lahiosoite"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // metodi, joka tulostaa tietokannan jokaisen mökin nimen, katuosoitteen ja kuvauksen
    public static void getMokit() throws SQLException{
        String sql = "SELECT * " +
                "FROM mokki";

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("mokkinimi") + "\t" +
                        rs.getString("katuosoite")  + "\t" +
                        rs.getString("kuvaus"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


}