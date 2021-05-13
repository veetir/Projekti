package sample;


import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

public class Varaus {
    private long asiakasId;
    private String etunimi;
    private String sukunimi;
    private long mokkiMokkiId;
    private String mokkinimi;
    private String katuosoite;
    private String toimintaalue;
    private int toimintaalueId;
    private Date varattuPvm;
    private Date vahvistusPvm;
    private Date varattuAlkupvm;
    private Date varattuLoppupvm;

    private long varausId;

    public Varaus(long varausId, long asiakasId, String etunimi, String sukunimi, long mokkiMokkiId, String mokkinimi,
                  String katuosoite, String toimintaalue, Date varattuPvm, Date varattuAlkupvm,
                  Date varattuLoppupvm) {
        this.varausId = varausId;
        this.asiakasId = asiakasId;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.mokkiMokkiId = mokkiMokkiId;
        this.mokkinimi = mokkinimi;
        this.katuosoite = katuosoite;
        this.toimintaalue = toimintaalue;
        this.varattuPvm = varattuPvm;
        this.varattuAlkupvm = varattuAlkupvm;
        this.varattuLoppupvm = varattuLoppupvm;
    }
    public int getToimintaalueId() {
        return toimintaalueId;
    }
    public void setToimintaalueId(int toimintaalueId) {
        this.toimintaalueId = toimintaalueId;
    }
    public Varaus(long varausId, long asiakasId, String etunimi, String sukunimi, long mokkiMokkiId, String mokkinimi,
                  String katuosoite, String toimintaalue,int toimintaalueId, Date varattuPvm, Date varattuAlkupvm,
                  Date varattuLoppupvm) {
        this.varausId = varausId;
        this.asiakasId = asiakasId;
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.mokkiMokkiId = mokkiMokkiId;
        this.mokkinimi = mokkinimi;
        this.katuosoite = katuosoite;
        this.toimintaalue = toimintaalue;
        this.setToimintaalueId(toimintaalueId);
        this.varattuPvm = varattuPvm;
        this.varattuAlkupvm = varattuAlkupvm;
        this.varattuLoppupvm = varattuLoppupvm;
    }

    public static int getVarauksenMokki(int varausId) throws SQLException {
        ArrayList<Varaus> varaukset;
        varaukset = SQL_yhteys.getVaraukset();
        for (int i = 0; i < varaukset.size(); i++) {
            if (varaukset.get(i).getVarausId() == varausId) {
                return (int)varaukset.get(i).getMokkiMokkiId();
            }
        }
        return -1; // ei löydy
    }

    public static String getVarauksenHlo(int varausId) throws SQLException {
        ArrayList<Varaus> varaukset;
        varaukset = SQL_yhteys.getVaraukset();
        for (int i = 0; i < varaukset.size(); i++) {
            if (varaukset.get(i).getVarausId() == varausId) {
                return varaukset.get(i).getEtunimi() + " "
                        + varaukset.get(i).getSukunimi();
            }
        }
        return null; // ei löydy
    }

    public String getToimintaalue() {
        return toimintaalue;
    }

    public void setToimintaalue(String toimintaalue) {
        this.toimintaalue = toimintaalue;
    }

    public String getKatuosoite() {
        return katuosoite;
    }

    public void setKatuosoite(String katuosoite) {
        this.katuosoite = katuosoite;
    }

    public String getMokkinimi() {
        return mokkinimi;
    }

    public void setMokkinimi(String mokkinimi) {
        this.mokkinimi = mokkinimi;
    }

    public String getSukunimi() {
        return sukunimi;
    }

    public void setSukunimi(String sukunimi) {
        this.sukunimi = sukunimi;
    }

    public String getEtunimi() {
        return etunimi;
    }

    public void setEtunimi(String etunimi) {
        this.etunimi = etunimi;
    }

    public long getVarausId() {
        return varausId;
    }

    public void setVarausId(long varausId) {
        this.varausId = varausId;
    }


    public long getAsiakasId() {
        return asiakasId;
    }

    public void setAsiakasId(long asiakasId) {
        this.asiakasId = asiakasId;
    }


    public long getMokkiMokkiId() {
        return mokkiMokkiId;
    }

    public void setMokkiMokkiId(long mokkiMokkiId) {
        this.mokkiMokkiId = mokkiMokkiId;
    }


    public java.sql.Date getVarattuPvm() {
        return varattuPvm;
    }

    public void setVarattuPvm(java.sql.Date varattuPvm) {
        this.varattuPvm = varattuPvm;
    }


    public java.sql.Date getVahvistusPvm() {
        return vahvistusPvm;
    }

    public void setVahvistusPvm(java.sql.Date vahvistusPvm) {
        this.vahvistusPvm = vahvistusPvm;
    }


    public java.sql.Date getVarattuAlkupvm() {
        return varattuAlkupvm;
    }

    public void setVarattuAlkupvm(java.sql.Date varattuAlkupvm) {
        this.varattuAlkupvm = varattuAlkupvm;
    }


    public Date getVarattuLoppupvm() {
        return varattuLoppupvm;
    }

    public void setVarattuLoppupvm(java.sql.Date varattuLoppupvm) {
        this.varattuLoppupvm = varattuLoppupvm;
    }

}
