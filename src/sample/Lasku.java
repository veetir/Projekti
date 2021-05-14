package sample;


public class Lasku {

  private long laskuId;
  private long varausId;
  private double summa;
  private double alv;
  private boolean maksettu;

  // alvi nähtiin turhaksi, joten jätin sen tästä
  public Lasku(long laskuId, long varausId, double summa, boolean maksettu) {
    this.laskuId = laskuId;
    this.varausId = varausId;
    this.summa = summa;
    this.maksettu = maksettu;
  }

  public boolean isMaksettu() {
    return maksettu;
  }

  public void setMaksettu(boolean maksettu) {
    this.maksettu = maksettu;
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
