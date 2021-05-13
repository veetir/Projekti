package sample;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class SQL_yhteys {
    // Huom! Vaihda user & password, tarkista myös url !
    String url = "jdbc:mysql://localhost:3306/vn";
    String user = "root";
    String password = "Umberto05!";

    // Palauttaa SQL yhteys olion
    public static Connection getYhteys() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/vn?serverTimezone=Europe/Helsinki";
        String user = "root", password = "Umberto05!";
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

    public static ArrayList<Asiakas> getAsiakkaat() throws SQLException {
        String sql = "SELECT * " +
                "FROM asiakas";
        ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

    public static ArrayList<Asiakas> getAsiakkaat(String puh) throws SQLException {
        puh = puh + "%";
        String sql = "SELECT * " +
                "FROM asiakas "+
                "WHERE puhelinnro like ?;";
        ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
        ResultSet rs = null;
        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            
            stmt.setString(1, puh);
            rs = stmt.executeQuery();
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
        String sql = "SELECT asiakas_id FROM asiakas " +
                "WHERE etunimi = ?" +
                "AND sukunimi = ?" +
                "AND lahiosoite = ?" +
                "AND email = ?" +
                "AND puhelinnro = ?" +
                "AND postinro = ? ;";

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
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

    public static ArrayList<Varaus> getVaraukset() throws SQLException {
        String sql = "SELECT * " +
                "FROM varaushaku " +
                "ORDER BY varaus_id DESC";
        ArrayList<Varaus> varaukset = new ArrayList<Varaus>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Varaus varaus = null;

            // loop through the result set
            while (rs.next()) {
                int varausId = rs.getInt("varaus_id");
                int asiakasId = rs.getInt("asiakas_id");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                int mokkiId = rs.getInt("mokki_id");
                String mokkinimi = rs.getString("mokkinimi");
                String katuosoite = rs.getString("katuosoite");
                String toimintaalue = rs.getString("toimintaalue");
                int toimintaalueId = rs.getInt("toimintaalue_id");
                Date varattuPvm = rs.getDate("varattu_pvm");
                Date varattuAlkupvm = rs.getDate("varattu_alkupvm");
                Date varattuLoppupvm = rs.getDate("varattu_loppupvm");

                varaus = new Varaus(varausId, asiakasId, etunimi, sukunimi, mokkiId, mokkinimi, katuosoite, toimintaalue, toimintaalueId, varattuPvm, varattuAlkupvm, varattuLoppupvm);
                varaukset.add(varaus);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return varaukset;
    }
    public static ArrayList<Varaus> getVarauksetNoOrder() throws SQLException {
        String sql = "SELECT * " +
                "FROM varaus";
        ArrayList<Varaus> varaukset = new ArrayList<Varaus>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Varaus varaus = null;

            // loop through the result set
            while (rs.next()) {
                int varausId = rs.getInt("varaus_id");
                int asiakasId = rs.getInt("asiakas_id");
                int mokkiId = rs.getInt("mokki_mokki_id");
                Date varattuPvm = rs.getDate("varattu_pvm");
                Date varattuAlkupvm = rs.getDate("varattu_alkupvm");
                Date varattuLoppupvm = rs.getDate("varattu_loppupvm");

                varaus = new Varaus(varausId, asiakasId, null, null, mokkiId,
                        null, null, null, varattuPvm, varattuAlkupvm, varattuLoppupvm);
                varaukset.add(varaus);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return varaukset;
    }

    public static ArrayList<Varaus> getVaraukset(String haku) throws SQLException {
        haku = "%" + haku + "%";
        String sql = "SELECT * " +
                "FROM varaushaku " +
                "WHERE concat(etunimi, ' ', sukunimi) like ? " +
                "OR mokkinimi LIKE ? " +
                "OR katuosoite LIKE ? " +
                "ORDER BY varaus_id DESC";
        ArrayList<Varaus> varaukset = new ArrayList<Varaus>();
        ResultSet rs = null;

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, haku);
            stmt.setString(2, haku);
            stmt.setString(3, haku);


            rs = stmt.executeQuery();
            Varaus varaus = null;

            // loop through the result set
            while (rs.next()) {
                int varausId = rs.getInt("varaus_id");
                int asiakasId = rs.getInt("asiakas_id");
                String etunimi = rs.getString("etunimi");
                String sukunimi = rs.getString("sukunimi");
                int mokkiId = rs.getInt("mokki_id");
                String mokkinimi = rs.getString("mokkinimi");
                String katuosoite = rs.getString("katuosoite");
                String toimintaalue = rs.getString("toimintaalue");
                int toimintaalueId = rs.getInt("toimintaalue_id");
                Date varattuPvm = rs.getDate("varattu_pvm");
                Date varattuAlkupvm = rs.getDate("varattu_alkupvm");
                Date varattuLoppupvm = rs.getDate("varattu_loppupvm");

                varaus = new Varaus(varausId, asiakasId, etunimi, sukunimi, mokkiId, mokkinimi, katuosoite, toimintaalue,toimintaalueId, varattuPvm, varattuAlkupvm, varattuLoppupvm);
                varaukset.add(varaus);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return varaukset;
    }

    public static ArrayList<Palvelu> getPalvelut() throws SQLException {
        String sql = "SELECT * " +
                "FROM palvelu";
        ArrayList<Palvelu> palvelut = new ArrayList<Palvelu>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Palvelu palvelu = null;

            // loop through the result set
            while (rs.next()) {
                int palveluId = rs.getInt("palvelu_id");
                int talueId = rs.getInt("toimintaalue_id");
                String palveluNimi = rs.getString("nimi");
                String palveluKuvaus = rs.getString("kuvaus");
                Double palveluHinta = rs.getDouble("hinta");

                palvelu = new Palvelu(palveluId, talueId, palveluNimi, palveluKuvaus, palveluHinta);
                palvelut.add(palvelu);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return palvelut;
    }

    public static ArrayList<Mokki> getAlueenMokit(String alue) throws SQLException {
        String area = String.valueOf(ToimintaAlue.findId(alue));
        System.out.println(area);
        String sql = "SELECT * " +
                "FROM mokki " +
                "WHERE toimintaalue_id = ?;";
        ArrayList<Mokki> mokit = new ArrayList<>();

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, area);
            ResultSet rs = stmt.executeQuery();
            Mokki mokki = null;
            while (rs.next()) {
                int mokki_id = rs.getInt("mokki_id");
                int toimintaalueen_id = rs.getInt("toimintaalue_id");
                String mokkinimix = rs.getString("mokkinimi");
                String katuosoite = rs.getString("katuosoite");
                String postinumero = rs.getString("postinro");
                String kuvaus = rs.getString("kuvaus");
                int hlomaara = Integer.valueOf(rs.getString("henkilomaara"));
                String varustelu = rs.getString("varustelu");
                long hinta = rs.getLong("hinta");

                mokki = new Mokki(mokki_id, toimintaalueen_id, ToimintaAlue.findNimi(toimintaalueen_id),
                        postinumero, mokkinimix, katuosoite, kuvaus, hlomaara, varustelu, hinta);
                mokit.add(mokki);
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return mokit;
    }

    public static ArrayList<Palvelu> getAlueenPalvelut(int alueId) throws SQLException {
        String toimintaalue_id = String.valueOf(alueId);
        String sql = "SELECT * " +
                "FROM palvelu " +
                "WHERE toimintaalue_id = ?;";
        ArrayList<Palvelu> palvelut = new ArrayList<>();

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, toimintaalue_id);
            ResultSet rs = stmt.executeQuery();
            Palvelu palvelu = null;
            while (rs.next()) {
                int palveluId = rs.getInt("palvelu_id");
                int talueId = rs.getInt("toimintaalue_id");
                String palveluNimi = rs.getString("nimi");
                String palveluKuvaus = rs.getString("kuvaus");
                Double palveluHinta = rs.getDouble("hinta");

                palvelu = new Palvelu(palveluId, talueId, palveluNimi, palveluKuvaus, palveluHinta);
                palvelut.add(palvelu);
                //palvelut.add(palveluNimi);
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return palvelut;
    }

    public static ArrayList<VarauksenPalvelu_Hallinta> getVarauksenPalvelut(int varaus_id) {
        String sql = "Select vp.varaus_id as varaus_id, p.palvelu_id as palvelu_id, p.nimi as palvelu, vp.lkm as lkm, p.hinta as hinta " +
                "from palvelu p join varauksen_palvelut vp " +
                "on p.palvelu_id = vp.palvelu_id " +
                "where varaus_id = ?;";
        ArrayList<VarauksenPalvelu_Hallinta> palvelut = new ArrayList<VarauksenPalvelu_Hallinta>();
        ResultSet rs = null;

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, varaus_id);
            rs = stmt.executeQuery();

            VarauksenPalvelu_Hallinta palvelu = null;

            // loop through the result set
            while (rs.next()) {
                int palvelu_id = rs.getInt("palvelu_id");
                int varausId = rs.getInt("varaus_id");
                String palveluNimi = rs.getString("palvelu");
                int lkm = rs.getInt("lkm");
                float hinta = rs.getFloat("hinta");

                palvelu = new VarauksenPalvelu_Hallinta(varausId, palvelu_id, palveluNimi, lkm, hinta);
                palvelut.add(palvelu);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return palvelut;
    }


    public static void insertVarauksenPalvelut(ArrayList<VarauksenPalvelu> vpLista, int varausId) {
        ResultSet rs = null;

        String sql = "INSERT INTO varauksen_palvelut(varaus_id, palvelu_id, lkm) " +
                "VALUES(" + varausId + ", ?, ?);";
        try
                (Connection conn = SQL_yhteys.getYhteys();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            for (VarauksenPalvelu vp : vpLista) {
                pstmt.setInt(1, (int) vp.getPalvelu().getPalveluId());
                pstmt.setInt(2, vp.getLkm());


                int rowAffected = pstmt.executeUpdate();
                if (rowAffected == 1) {
                    // get candidate id
                    rs = pstmt.getGeneratedKeys();
                    if (rs.next())
                        System.out.println("en tiiä");

                }
                System.out.println("varaukseen lisättiin palvelu: " + vp);
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void poistaVarauksenPalvelu(int varausId, int palveluId) {
        ResultSet rs = null;

        String sql = "DELETE FROM varauksen_palvelut where varaus_id = ? AND palvelu_id = ? ;";
        try (Connection conn = SQL_yhteys.getYhteys();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            
                pstmt.setInt(1, varausId );
                pstmt.setInt(2, palveluId);


                int rowAffected = pstmt.executeUpdate();
                if (rowAffected == 1) {
                    // get candidate id
                    rs = pstmt.getGeneratedKeys();
                    if (rs.next())
                        System.out.println("poistettiin onnistuneesti.");

                }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void updateVarauksenPalvelu(int varausId, int palveluId, int lkm) {
        ResultSet rs = null;

        String sql = "UPDATE varauksen_palvelut SET lkm = ? WHERE varaus_id = ? AND palvelu_id = ? ;";
        try (Connection conn = SQL_yhteys.getYhteys();
                 PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            
                pstmt.setInt(1, lkm);
                pstmt.setInt(2, varausId);
                pstmt.setInt(3, palveluId);
                
                int rowAffected = pstmt.executeUpdate();
                if (rowAffected == 1) {
                    // get candidate id
                    rs = pstmt.getGeneratedKeys();
                    if (rs.next())
                        System.out.println("poistettiin onnistuneesti.");

                }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static ArrayList<Mokki> getMokit() throws SQLException {
        String sql = "SELECT * " +
                "FROM mokkihaku";
        ArrayList<Mokki> mokit = new ArrayList<Mokki>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Mokki mokki = null;

            // loop through the result set
            while (rs.next()) {
                String mokkiNimi = rs.getString("mokkinimi");
                String Osoite = rs.getString("katuosoite");
                String Kuvaus = rs.getString("kuvaus");
                int mokki_id = rs.getInt("mokki_id");
                String Varustelu = rs.getString("varustelu");
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

    public static ArrayList<Lasku> getLaskut() throws SQLException {
        String sql = "SELECT * " +
                "FROM lasku";
        ArrayList<Lasku> laskut = new ArrayList<Lasku>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            Lasku lasku = null;

            // loop through the result set
            while (rs.next()) {
                Long laskuId = rs.getLong("lasku_id");
                Long varausId = rs.getLong("varaus_id");
                Double summa = rs.getDouble("summa");


                lasku = new Lasku(laskuId, varausId, summa);
                laskut.add(lasku);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return laskut;
    }

    // Hakee tietokannasta mökit jotka ovat vapaana välillä alkupvm - loppupvm, ja palauttaa listana.
    public static ArrayList<Mokki> getMokit(LocalDate alkupvm, LocalDate loppupvm) throws SQLException {
        String sql = " select * from mokkihaku " +
                "where mokki_id not in(select mokki_mokki_id from varaus " +
                "where (varattu_alkupvm between ? and ? - INTERVAL 1 day) " +
                "or (varattu_loppupvm between ? and ? - INTERVAL 1 day));";
        ArrayList<Mokki> mokit = new ArrayList<Mokki>();

        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(sql);) {
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
                String Varustelu = rs.getString("varustelu");
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

    public static void insertLasku(Varaus varaus, Double hinta) throws SQLException {
        String varaus_id, summa = String.valueOf(hinta);
        varaus_id = String.valueOf(varaus.getVarausId());

        String kysely = "INSERT INTO lasku (varaus_id, summa) \n" +
                "VALUES (?, ?);";

        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, varaus_id);
            pstmt.setString(2, summa);

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

    public static void insertPostinro(String postinro, String alue) throws SQLException {
        String zip = postinro, area = alue;
        String kysely = "INSERT INTO posti (postinro, toimipaikka) \n" +
                "VALUES (?, ?);";
        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, zip);
            pstmt.setString(2, area);

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
    public static boolean hasPostinro(String postinro) throws SQLException {
        String zip = postinro;
        boolean found = false;
        String kysely = "SELECT * FROM posti WHERE postinro = ?";
        ResultSet rs = null;
        try (Connection conn = SQL_yhteys.getYhteys();
             PreparedStatement stmt = conn.prepareStatement(kysely);) {
            
            stmt.setString(1, zip);
            rs = stmt.executeQuery();
            // loop through the result set
            while (rs.next()) {
                found = true;
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return found;
    }

    public static void setMokit(Mokki uusimokki, boolean paivita, boolean poista, String poistoId) throws SQLException {
        String mokkiId = "", alueid = "", zip = "", nimi = "", osoite = "", kuvaus = "", hlolkm = "", varustelu = "", hinta = "", kysely = "";
        if (!poista) {
            mokkiId = String.valueOf(uusimokki.get_mokki_id());
            alueid = String.valueOf(uusimokki.get_toimintaalue_id());
            zip = uusimokki.get_postinro();
            nimi = uusimokki.get_mokkinimi();
            osoite = uusimokki.get_katuosoite();
            kuvaus = uusimokki.get_kuvaus();
            hlolkm = String.valueOf(uusimokki.get_henkilomaara());
            varustelu = uusimokki.get_varustelu();
            hinta = String.valueOf(uusimokki.getHinta());
        } else {
            mokkiId = poistoId;
        }

        if (!paivita & !poista) {
            kysely = "INSERT INTO mokki (toimintaalue_id, postinro, mokkinimi, katuosoite, kuvaus, henkilomaara, varustelu, hinta) \n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        } else if (paivita & !poista) {
            kysely = "UPDATE mokki " +
                    "SET toimintaalue_id = ?, postinro =?, mokkinimi=?, katuosoite=?, kuvaus=?, henkilomaara=?, varustelu=?, hinta=? " +
                    "WHERE mokki_id = ?;";
        } else if (poista) {
            kysely = "DELETE FROM mokki WHERE mokki_id = ?";
        }

        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            if (!poista) {
                pstmt.setString(1, alueid);
                pstmt.setString(2, zip);
                pstmt.setString(3, nimi);
                pstmt.setString(4, osoite);
                pstmt.setString(5, kuvaus);
                pstmt.setString(6, hlolkm);
                pstmt.setString(7, varustelu);
                pstmt.setString(8, hinta);
            }
            if (paivita) {
                pstmt.setString(9, mokkiId);
            }
            if (poista) {
                pstmt.setString(1, mokkiId);
            }

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

    public static void setPalvelu(Palvelu palvelu, boolean paivita, boolean poista, String poistoId) throws SQLException {
        // TODO: poisto
        String kysely = "", nimi = "", kuvaus = "", toimintaalue_id = "", hinta = "", palvelu_id = "";

        if (!poista) {
            toimintaalue_id = String.valueOf(palvelu.getToimintaalueId());
            nimi = palvelu.getNimi();
            kuvaus = palvelu.getKuvaus();
            hinta = String.valueOf(palvelu.getHinta());
            if (paivita) {
                palvelu_id = String.valueOf(palvelu.getPalveluId());
            }
        } else {
            palvelu_id = poistoId;
        }

        if (!paivita & !poista) {
            kysely = "INSERT INTO palvelu (toimintaalue_id, nimi, kuvaus, hinta) \n" +
                    "VALUES (?,?,?,?);";
        } else if (paivita & !poista) {
            kysely = "UPDATE palvelu " +
                    "SET  nimi=?, kuvaus=?, hinta=?" +
                    "WHERE palvelu_id = ?;";
        } else if (poista) {
            kysely = "DELETE FROM palvelu WHERE palvelu_id = ?";
        }

        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(kysely,
                    Statement.RETURN_GENERATED_KEYS);
            if (!poista & !paivita) {
                pstmt.setString(1, toimintaalue_id);
                pstmt.setString(2, nimi);
                pstmt.setString(3, kuvaus);
                pstmt.setString(4, hinta);
            } else if (paivita & !poista) {
                pstmt.setString(1, nimi);
                pstmt.setString(2, kuvaus);
                pstmt.setString(3, hinta);
                pstmt.setString(4, palvelu_id);
            } else {
                pstmt.setString(1, palvelu_id);
            }

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

    public static void setAsiakkaat(String kysely, String zip, String etunimi, String sukunimi, String osoite, String email, String puhnro) throws SQLException {
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
    public static int insertAsiakas(String zip, String etunimi, String sukunimi, String osoite, String email, String puhnro) throws SQLException {
        ResultSet rs = null;
        int asiakas_id = 0;
        String sql = "INSERT INTO asiakas(postinro,etunimi, sukunimi, lahiosoite, email, puhelinnro) " +
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
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    asiakas_id = rs.getInt(1);

            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return asiakas_id;
    }


    public static int muokkaaAsiakas(String id, String etunimi, String sukunimi, String lahiosoite, String postinro, String puhelinnro, String email, boolean update, boolean poisto, int poistoid) throws SQLException {
        ResultSet rs = null;
        int asiakas_id = 0;
        String sql;
        if (update & !poisto) {
            sql = "UPDATE asiakas " +
                    "SET etunimi = ?, sukunimi = ?, lahiosoite = ?, postinro = ?, puhelinnro = ?, email = ? " +
                    "WHERE asiakas_id = ?;";
        } else {
            sql = "DELETE FROM asiakas " +
                    "WHERE asiakas_id = ?;";
        }
        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            if (update & !poisto) {
                pstmt.setString(1, etunimi);
                pstmt.setString(2, sukunimi);
                pstmt.setString(3, lahiosoite);
                pstmt.setString(4, postinro);
                pstmt.setString(5, puhelinnro);
                pstmt.setString(6, email);
                pstmt.setString(7, id);

            } else {
                pstmt.setString(1, id);
            }
            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    asiakas_id = rs.getInt(1);
            }

        } catch(
        SQLException e)

        {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
        } finally

        {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        return asiakas_id;
    }


    //Lisää uuden toiminta-alueen tietokantaan ja palauttaa luodun ...?
    public static int insertToiminta(String nimi, String id, boolean update, boolean poisto) throws SQLException {
        ResultSet rs = null;
        int toimintaalue_id = 0;
        String sql;
        if (!update & !poisto) {
            sql = "INSERT INTO toimintaalue(nimi) " +
                    "VALUES(?);";
        } else if (update & !poisto) {
            sql = "UPDATE toimintaalue " +
                    "SET nimi = ?" +
                    "WHERE toimintaalue_id = ?;";
        } else {
            System.out.println("XXXX");
            sql = "DELETE FROM toimintaalue" +
                    " WHERE toimintaalue_id = ?;";
        }

        try {
            Connection conn = SQL_yhteys.getYhteys();
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            if (!update & !poisto) {
                pstmt.setString(1, nimi);
            } else if (update & !poisto) {
                pstmt.setString(1, nimi);
                pstmt.setString(2, id);
            } else {
                System.out.println("x");
                pstmt.setString(1, id);
            }

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    toimintaalue_id = rs.getInt(1);

            }


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return toimintaalue_id;
    }

    //Lisää varauksen tietokantaan ja palauttaa luodun varauksen ID:n
    public static int insertVaraus(int asiakas_id, int mokki_id, LocalDate saapumisPvm, LocalDate lahtoPvm) throws SQLException {
        ResultSet rs = null;
        int varaus_id = 0;
        LocalDate tanaan = LocalDate.now();

        String sql = "INSERT INTO varaus(asiakas_id, mokki_mokki_id, varattu_pvm, varattu_alkupvm, varattu_loppupvm) " +
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
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    varaus_id = rs.getInt(1);

            }
            System.out.println("Uusi varaus numerolla: " + varaus_id);


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return varaus_id;
    }

    public static void peruutaVaraus(int varaus_id) {
        String sql = "DELETE from varaus " +
                "WHERE varaus_id = ?";
        ResultSet rs = null;

        try (
                Connection conn = SQL_yhteys.getYhteys();
                PreparedStatement pstmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);) {
            pstmt.setInt(1, varaus_id);
            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    varaus_id = rs.getInt(1);

            }
            System.out.println("Varaus peruutettu onnistuneesti");


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    // Hakee tietokannasta toiminta-alueiden nimet ja palauttaa listana.

    public static ArrayList<String> getToimintaAlueet() throws SQLException {
        String sql = "SELECT * FROM toimintaalue";
        ArrayList<String> alueet = new ArrayList<String>();
        ArrayList alueetid = new ArrayList<>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt("toimintaalue_id");
                alueetid.add(id);
                String alue = rs.getString("nimi");
                alueet.add(alue);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return alueet;

    }

    // Hakee tietokannasta toiminta-alueiden nimet ja palauttaa listana, jonka alkiot ovat ToimintaAlue -tyyppisiä
    public static ArrayList<ToimintaAlue> getToimintaAlueetX() throws SQLException {
        String sql = "SELECT * FROM toimintaalue";
        ArrayList<ToimintaAlue> alueet = new ArrayList<>();

        try (Connection conn = SQL_yhteys.getYhteys();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                int id = rs.getInt("toimintaalue_id");
                String alue = rs.getString("nimi");
                ToimintaAlue tAlue = new ToimintaAlue(alue, id);
                alueet.add(tAlue);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return alueet;
    }

    // Metodi poistaa mökin tietokannasta, voidaan käyttää mökin hallinnassa liitettynä nappiin.
    public static EventHandler<ActionEvent> poistaMokki(int mokki_id) throws SQLException {
        String sql = "DELETE * FROM mokki WHERE mokki_id = ?";
        ResultSet rs = null;

        try (
                Connection conn = SQL_yhteys.getYhteys();
                PreparedStatement pstmt = conn.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);) {
            pstmt.setInt(1, mokki_id);
            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if (rs.next())
                    mokki_id = rs.getInt(1);

            }
            System.out.println("Mökki poistettu");


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        return null;
    }
}