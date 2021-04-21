package sample;

import java.sql.*;
import java.util.ArrayList;


public class SQL_yhteys {
    // Huom! Vaihda user & password, tarkista myös url !
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

    public static ArrayList<Asiakas> getAsiakkaat() throws SQLException{
        String sql = "SELECT * " +
                "FROM asiakas";
        ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            Asiakas asiakas = null;

            // loop through the result set
            while (rs.next()) {
                String asiakasEtuNimi = rs.getString("etunimi");
                String asiakasSukuNimi = rs.getString("sukunimi");
                String asiakasOsoite = rs.getString("lahiosoite");
                int asiakas_id = rs.getInt("asiakas_id");
                String asiakasEmail = rs.getString("email");
                String asiakasPuhNro = rs.getString("puhelinnro");
                String asiakasPostinro = rs.getString("postinro");

                asiakas = new Asiakas(asiakas_id, asiakasPostinro, asiakasEtuNimi, asiakasSukuNimi, asiakasOsoite, asiakasEmail, asiakasPuhNro);
                asiakkaat.add(asiakas);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return asiakkaat;
    }

    public static ArrayList<Varaus> getVaraukset() throws SQLException{
        String sql = "SELECT * " +
                "FROM varaus";
        ArrayList<Varaus> varaukset = new ArrayList<Varaus>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            Varaus varaus = null;

            // loop through the result set
            while (rs.next()) {
                int varausId = rs.getInt("varaus_id");
                int asiakasId = rs.getInt("asiakas_id");
                int mokkiId = rs.getInt("mokki_mokki_id");
                Timestamp varattuPvm = rs.getTimestamp("varattu_pvm");
                Timestamp vahvistusPvm = rs.getTimestamp("vahvistus_pvm");
                Timestamp varattuAlkuPvm = rs.getTimestamp("varattu_alkupvm");
                Timestamp varattuLoppuPvm = rs.getTimestamp("varattu_loppupvm");

                varaus = new Varaus(varausId, asiakasId, mokkiId, varattuPvm, vahvistusPvm, varattuAlkuPvm, varattuLoppuPvm);
                varaukset.add(varaus);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return varaukset;
    }

    public static ArrayList<Palvelu> getPalvelut() throws SQLException{
        String sql = "SELECT * " +
                "FROM asiakas";
        ArrayList<Palvelu> palvelut = new ArrayList<Palvelu>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            Palvelu palvelu = null;

            // loop through the result set
            while (rs.next()) {
                int palveluId = rs.getInt("palvelu_id");
                int talueId = rs.getInt("toimintaalue_id");
                String palveluNimi = rs.getString("nimi");
                int palveluTyyppi = rs.getInt("tyyppi");
                String palveluKuvaus = rs.getString("kuvaus");
                Double palveluHinta = rs.getDouble("hinta");
                Double palveluAlv = rs.getDouble("alv");

                palvelu = new Palvelu(palveluId, talueId, palveluNimi, palveluTyyppi, palveluKuvaus, palveluHinta, palveluAlv);
                palvelut.add(palvelu);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return palvelut;
    }


    public static ArrayList<Mokki> getMokit() throws SQLException{
        String sql = "SELECT * " +
                "FROM mokki";
                ArrayList<Mokki> mokit = new ArrayList<Mokki>();
        
        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            Mokki mokki = null;

            // loop through the result set
            while (rs.next()) {
                String mokkiNimi = rs.getString("mokkinimi");
                String Osoite = rs.getString("katuosoite");
                String Kuvaus = rs.getString("kuvaus");
                int mokki_id = rs.getInt("mokki_id");
                String Varustelu = rs.getString("kuvaus");
                int toimintaAlue = rs.getInt("toimintaalue_id");
                String postinro = rs.getString("postinro");
                int henkilomaara = rs.getInt("henkilomaara");

                mokki = new Mokki(mokki_id, toimintaAlue, postinro, mokkiNimi, Osoite, Kuvaus, henkilomaara, Varustelu);
                mokit.add(mokki);


            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mokit;
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