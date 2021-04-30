package sample;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;




public class SQL_yhteys {
    // Huom! Vaihda user & password, tarkista myös url !
    String url = "jdbc:mysql://localhost:3306/vn?ServerTimezone=Helsinki/Finland";
    String user = "root";
    String password = "-----";

    // Palauttaa SQL yhteys olion
    public static Connection getYhteys() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/vn?serverTimezone=Europe/Helsinki";
        String user = "root", password = "-----";
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

    // hakee asiakas_id:n tietokannasta argumenttien perusteella ja palauttaa sen. Palauttaa -1 jos asiakasta ei löydy.
    public static int getAsiakasId(String etunimi, String sukunimi, String lahiosoite, String email, String puhelinnro, String postinro) throws SQLException {
        int asiakas_id = -1;
        String sql = "SELECT asiakas_id FROM asiakas "+
                "WHERE etunimi = ?"+
                "AND sukunimi = ?"+
                "AND lahiosoite = ?"+
                "AND email = ?"+
                "AND puhelinnro = ?"+
                "AND postinro = ? ;";

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt  = conn.prepareStatement(sql);) {
            stmt.setString(1, etunimi);
            stmt.setString(2, sukunimi);
            stmt.setString(3, lahiosoite);
            stmt.setString(4, email);
            stmt.setString(5, puhelinnro);
            stmt.setString(6, postinro);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                asiakas_id = rs.getInt("asiakas_id");
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return asiakas_id;

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
                "FROM palvelu";
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
                "FROM mokkihaku";
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
                int toimintaAlue_id = rs.getInt("toimintaalue_id");
                String toimintaAlue_nimi = rs.getString("alue");
                String postinro = rs.getString("postinro");
                int henkilomaara = rs.getInt("henkilomaara");
                long hinta = rs.getLong("hinta");

                mokki = new Mokki(mokki_id, toimintaAlue_id, toimintaAlue_nimi, postinro, mokkiNimi, Osoite, Kuvaus, henkilomaara, Varustelu, hinta);
                mokit.add(mokki);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mokit;
    }

    // Hakee tietokannasta mökit jotka ovat vapaana välillä alkupvm - loppupvm, ja palauttaa listana.
    public static ArrayList<Mokki> getMokit(LocalDate alkupvm, LocalDate loppupvm) throws SQLException{
        String sql = " select * from mokkihaku "+
                "where mokki_id not in(select mokki_mokki_id from varaus "+
                "where (varattu_alkupvm between ? and ? - INTERVAL 1 day) "+
                "or (varattu_loppupvm between ? and ? - INTERVAL 1 day));";
        ArrayList<Mokki> mokit = new ArrayList<Mokki>();

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt  = conn.prepareStatement(sql);) {
            stmt.setDate(1, java.sql.Date.valueOf(alkupvm));
            stmt.setDate(2, java.sql.Date.valueOf(loppupvm));
            stmt.setDate(3, java.sql.Date.valueOf(alkupvm));
            stmt.setDate(4, java.sql.Date.valueOf(loppupvm));
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                String mokkiNimi = rs.getString("mokkinimi");
                String Osoite = rs.getString("katuosoite");
                String Kuvaus = rs.getString("kuvaus");
                int mokki_id = rs.getInt("mokki_id");
                String Varustelu = rs.getString("kuvaus");
                int toimintaAlue_id = rs.getInt("toimintaalue_id");
                String toimintaAlue_nimi = rs.getString("alue");
                String postinro = rs.getString("postinro");
                int henkilomaara = rs.getInt("henkilomaara");
                long hinta = rs.getLong("hinta");

                Mokki mokki = new Mokki(mokki_id, toimintaAlue_id, toimintaAlue_nimi, postinro, mokkiNimi, Osoite, Kuvaus, henkilomaara, Varustelu, hinta);
                mokit.add(mokki);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mokit;
    }

    public static void setMokit(String kysely, String alueid, String zip, String nimi, String osoite, String kuvaus, String hlolkm, String varustelu, String hinta) throws SQLException{
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
            pstmt.setString(8, hinta);

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

    //Lisää asiakkaan tietokantaan ja palauttaa luodun asiakkaan asiakas_id:n
    public static int insertAsiakas(String zip, String etunimi, String sukunimi, String osoite, String email, String puhnro) throws SQLException{
        ResultSet rs = null;
        int asiakas_id = 0;
        String sql = "INSERT INTO asiakas(postinro,etunimi, sukunimi, lahiosoite, email, puhelinnro) "+
                "VALUES(?, ?, ?, ?, ?, ?);";
        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, zip);
            pstmt.setString(2, etunimi);
            pstmt.setString(3, sukunimi);
            pstmt.setString(4, osoite);
            pstmt.setString(5, email);
            pstmt.setString(6, puhnro);

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1)
            {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    asiakas_id = rs.getInt(1);

            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }finally {
            try {
                if(rs != null)  rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return asiakas_id;
    }

    //Lisää varauksen tietokantaan ja palauttaa luodun varauksen ID:n
    public static int insertVaraus(int asiakas_id, int mokki_id, LocalDate saapumisPvm, LocalDate lahtoPvm) throws SQLException{
        ResultSet rs = null;
        int varaus_id = 0;
        LocalDate tanaan = LocalDate.now();

        String sql = "INSERT INTO varaus(asiakas_id, mokki_mokki_id, varattu_pvm, varattu_alkupvm, varattu_loppupvm) "+
                "VALUES(?, ?, ?, ?, ?);";
        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, asiakas_id);
            pstmt.setInt(2, mokki_id);
            pstmt.setDate(3, java.sql.Date.valueOf(tanaan));
            pstmt.setDate(4, java.sql.Date.valueOf(saapumisPvm));
            pstmt.setDate(5, java.sql.Date.valueOf(lahtoPvm));

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1)
            {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    varaus_id = rs.getInt(1);

            }
            System.out.println("Uusi varaus numerolla: "+varaus_id);


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }finally {
            try {
                if(rs != null)  rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return varaus_id;
    }

    // Hakee tietokannasta toiminta-alueiden nimet ja palauttaa listana. 
    public static ArrayList<String> getToimintaAlueet() throws SQLException {
        String sql = "SELECT * FROM toimintaalue";
        ArrayList<String> alueet = new ArrayList<String>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                String alue = rs.getString("nimi");
                alueet.add(alue);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return alueet;

    }

}