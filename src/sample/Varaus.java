package sample;


public class Varaus {

  private long varausId;
  private long asiakasId;
  private long mokkiMokkiId;
  private java.sql.Timestamp varattuPvm;
  private java.sql.Timestamp vahvistusPvm;
  private java.sql.Timestamp varattuAlkupvm;
  private java.sql.Timestamp varattuLoppupvm;


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
