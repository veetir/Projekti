package sample;

import java.sql.*;


public class SQL_yhteys {
    // Huom! Vaihda user & password, tarkista myös url
    String url = "jdbc:mysql://localhost:3306/vn?ServerTimezone=Helsinki/Finland";
    String user = "root";
    String password = "Olavi99?";


    public static Connection getYhteys() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/vn?serverTimezone=Europe/Helsinki";
        String user = "root", password = "Olavi99?";
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
    public static void setMokit(String kysely, String alueid, String zip, String nimi, String osoite, String kuvaus, String hlolkm, String varustelu) throws SQLException{
        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, alueid);
            pstmt.setString(2, zip);
            pstmt.setString(3, nimi);
            pstmt.setString(4, osoite);
            pstmt.setString(5, kuvaus);
            pstmt.setString(6, hlolkm);
            pstmt.setString(7, varustelu);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // process further here
            }

            // get candidate id
            int candidateId = 0;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                candidateId = rs.getInt(1);


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    public static void setAsiakkaat(String kysely, String zip, String etunimi, String sukunimi, String osoite, String email, String puhnro) throws SQLException{
        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, zip);
            pstmt.setString(2, etunimi);
            pstmt.setString(3, sukunimi);
            pstmt.setString(4, osoite);
            pstmt.setString(5, email);
            pstmt.setString(6, puhnro);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // process further here
            }

            // get candidate id
            int candidateId = 0;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next())
                candidateId = rs.getInt(1);


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

}