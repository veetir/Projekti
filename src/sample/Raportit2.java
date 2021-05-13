package sample;

public class Raportit2 {

    private String nimi;
    private Integer palvelu_id;
    private Integer varaus_id;
    private Integer lkm;
    private Double hinta;
    private Integer toimintaalue_id;



    public Raportit2(String nimi, Integer palvelu_id, Integer varaus_id, Integer lkm, Double hinta, Integer toimintaalue_id) {
        this.nimi = nimi;
        this.palvelu_id = palvelu_id;
        this.varaus_id = varaus_id;
        this.lkm = lkm;
        this.hinta = hinta;
        this.toimintaalue_id = toimintaalue_id;

    }

    public String getNimi() {return nimi;}

    public void setNimi(String nimi) {this.nimi = nimi;}

    public Integer getPalvelu_id() {return palvelu_id;}

    public void setPalvelu_id(Integer palvelu_id) {this.palvelu_id = palvelu_id;}

    public Integer getVaraus_id() {return varaus_id;}

    public void setVaraus_id(Integer varaus_id) {this.varaus_id = varaus_id;}

    public Integer getLkm() {return lkm;}

    public void setLkm(Integer lkm) {this.lkm = lkm;}

    public double getHinta() {return hinta;}

    public void setHinta(Double hinta) {this.hinta = hinta;}

    public Integer getToimintaalue_id() {return toimintaalue_id;}

    public void setToimintaalue_id(Integer toiminta_alue_id) {this.toimintaalue_id = toimintaalue_id;}
}
