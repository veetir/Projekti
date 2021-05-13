package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.sql.Date;

public class Raportit {

    private Integer varaus_id;
    private Integer asiakas_id;
    private Integer mokki_mokki_id;
    private Date varattu_pvm;
    private String etunimi;
    private String sukunimi;

    public Raportit(String etunimi, String sukunimi, Integer varaus_id, Integer asiakas_id, Integer mokki_mokki_id, Date varattu_pvm) {

        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.varaus_id = varaus_id;
        this.asiakas_id = asiakas_id;
        this.mokki_mokki_id = mokki_mokki_id;
        this.varattu_pvm = varattu_pvm;
    }

    public String getEtunimi() {return etunimi;}

    public void setEtunimi(String etunimi) {this.etunimi = etunimi;}

    public String getSukunimi() {return sukunimi;}

    public void setSukunimi(String sukunimi) {this.sukunimi = sukunimi;}

    public Integer getVaraus_id() {return varaus_id;}

    public void setVaraus_id(Integer varaus_id) {this.varaus_id = varaus_id;}


    public Integer getAsiakas_id() {return asiakas_id;}

    public void setAsiakas_id(Integer asiakasId) {this.asiakas_id = asiakasId;}


    public Integer getMokki_mokki_id() {return mokki_mokki_id;}

    public void setMokki_mokki_id(Integer mokki_mokki_id) {this.mokki_mokki_id = mokki_mokki_id;}


    public Date getVarattu_pvm() {return varattu_pvm;}

    public void setVarattu_pvm(Date varattu_pvm) {this.varattu_pvm = varattu_pvm;}

    /*https://www.youtube.com/watch?v=UKzx4NRaZyM */

}