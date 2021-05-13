package sample;

public class Raportit2 {

    private Integer palvelu_id;
    private Integer varaus_id;
    private Integer lkm;


    public Raportit2(Integer palvelu_id, Integer varaus_id, Integer lkm) {
        this.palvelu_id = palvelu_id;
        this.varaus_id = varaus_id;
        this.lkm = lkm;
    }

    public Integer getPalvelu_id() {return palvelu_id;}

    public void setPalvelu_id(Integer palvelu_id) {this.palvelu_id = palvelu_id;}

    public Integer getVaraus_id() {return varaus_id;}

    public void setVaraus_id(Integer varaus_id) {this.varaus_id = varaus_id;}

    public Integer getLkm() {return lkm;}

    public void setLkm(Integer lkm) {this.lkm = lkm;}
}
