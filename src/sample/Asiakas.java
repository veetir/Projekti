package sample;


public class Asiakas {

  public Asiakas(long asiakasId, String postinro, String etunimi, String sukunimi, String lahiosoite, String email, String puhelinnro) {
    this.asiakasId = asiakasId;
    this.postinro = postinro;
    this.etunimi = etunimi;
    this.sukunimi = sukunimi;
    this.lahiosoite = lahiosoite;
    this.email = email;
    this.puhelinnro = puhelinnro;
  }

  private long asiakasId;
  private String postinro;
  private String etunimi;
  private String sukunimi;
  private String lahiosoite;
  private String email;
  private String puhelinnro;





  public long getAsiakasId() {
    return asiakasId;
  }

  public void setAsiakasId(long asiakasId) {
    this.asiakasId = asiakasId;
  }


  public String getPostinro() {
    return postinro;
  }

  public void setPostinro(String postinro) {
    this.postinro = postinro;
  }


  public String getEtunimi() {
    return etunimi;
  }

  public void setEtunimi(String etunimi) {
    this.etunimi = etunimi;
  }


  public String getSukunimi() {
    return sukunimi;
  }

  public void setSukunimi(String sukunimi) {
    this.sukunimi = sukunimi;
  }


  public String getLahiosoite() {
    return lahiosoite;
  }

  public void setLahiosoite(String lahiosoite) {
    this.lahiosoite = lahiosoite;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public String getPuhelinnro() {
    return puhelinnro;
  }

  public void setPuhelinnro(String puhelinnro) {
    this.puhelinnro = puhelinnro;
  }

}
