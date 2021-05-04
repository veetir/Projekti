package sample;


import java.sql.Timestamp;

public class Varaus {
  private long asiakasId;
  private String etunimi;
  private String sukunimi;
  private long mokkiMokkiId;
  private String mokkinimi;
  private String katuosoite;
  private String toimintaalue;
  private java.sql.Timestamp varattuPvm;
  private java.sql.Timestamp vahvistusPvm;
  private java.sql.Timestamp varattuAlkupvm;
  private java.sql.Timestamp varattuLoppupvm;

  private long varausId;
  public Varaus(long varausId, long asiakasId, String etunimi, String sukunimi, long mokkiMokkiId, String mokkinimi,
      String katuosoite, String toimintaalue, Timestamp varattuPvm, Timestamp varattuAlkupvm,
      Timestamp varattuLoppupvm) {
    this.varausId = varausId;
    this.asiakasId = asiakasId;
    this.setEtunimi(etunimi);
    this.setSukunimi(sukunimi);
    this.mokkiMokkiId = mokkiMokkiId;
    this.setMokkinimi(mokkinimi);
    this.setKatuosoite(katuosoite);
    this.setToimintaalue(toimintaalue);
    this.varattuPvm = varattuPvm;
    this.varattuAlkupvm = varattuAlkupvm;
    this.varattuLoppupvm = varattuLoppupvm;
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


  public java.sql.Timestamp getVarattuPvm() {
    return varattuPvm;
  }

  public void setVarattuPvm(java.sql.Timestamp varattuPvm) {
    this.varattuPvm = varattuPvm;
  }


  public java.sql.Timestamp getVahvistusPvm() {
    return vahvistusPvm;
  }

  public void setVahvistusPvm(java.sql.Timestamp vahvistusPvm) {
    this.vahvistusPvm = vahvistusPvm;
  }


  public java.sql.Timestamp getVarattuAlkupvm() {
    return varattuAlkupvm;
  }

  public void setVarattuAlkupvm(java.sql.Timestamp varattuAlkupvm) {
    this.varattuAlkupvm = varattuAlkupvm;
  }


  public java.sql.Timestamp getVarattuLoppupvm() {
    return varattuLoppupvm;
  }

  public void setVarattuLoppupvm(java.sql.Timestamp varattuLoppupvm) {
    this.varattuLoppupvm = varattuLoppupvm;
  }

}
