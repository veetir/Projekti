package sample;


public class Palvelu {

  private long palveluId;
  private long toimintaalueId;
  private String nimi;
  private long tyyppi;
  private String kuvaus;
  private double hinta;
  private double alv;


  public long getPalveluId() {
    return palveluId;
  }

  public void setPalveluId(long palveluId) {
    this.palveluId = palveluId;
  }


  public long getToimintaalueId() {
    return toimintaalueId;
  }

  public void setToimintaalueId(long toimintaalueId) {
    this.toimintaalueId = toimintaalueId;
  }


  public String getNimi() {
    return nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }


  public long getTyyppi() {
    return tyyppi;
  }

  public void setTyyppi(long tyyppi) {
    this.tyyppi = tyyppi;
  }


  public String getKuvaus() {
    return kuvaus;
  }

  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }


  public double getHinta() {
    return hinta;
  }

  public void setHinta(double hinta) {
    this.hinta = hinta;
  }


  public double getAlv() {
    return alv;
  }

  public void setAlv(double alv) {
    this.alv = alv;
  }

}
