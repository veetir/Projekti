package sample;


public class Lasku {

  private long laskuId;
  private long varausId;
  private double summa;
  private double alv;

  // alvi nähtiin turhaksi, joten jätin sen tästä
  public Lasku(long laskuId, long varausId, double summa) {
    this.laskuId = laskuId;
    this.varausId = varausId;
    this.summa = summa;
  }

  public long getLaskuId() {
    return laskuId;
  }

  public void setLaskuId(long laskuId) {
    this.laskuId = laskuId;
  }


  public long getVarausId() {
    return varausId;
  }

  public void setVarausId(long varausId) {
    this.varausId = varausId;
  }


  public double getSumma() {
    return summa;
  }

  public void setSumma(double summa) {
    this.summa = summa;
  }


  public double getAlv() {
    return alv;
  }

  public void setAlv(double alv) {
    this.alv = alv;
  }

}
