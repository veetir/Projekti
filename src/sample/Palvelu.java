package sample;


import java.sql.SQLException;
import java.util.ArrayList;

public class Palvelu {

    private long palveluId;
    private long toimintaalueId;
    private String nimi;
    private String kuvaus;
    private double hinta;

    public Palvelu(long palveluId, long toimintaalueId, String nimi, String kuvaus, double hinta) {
        this.palveluId = palveluId;
        this.toimintaalueId = toimintaalueId;
        this.nimi = nimi;
        this.kuvaus = kuvaus;
        this.hinta = hinta;
    }

    public ArrayList<Palvelu> getAlueenPalvelut(ToimintaAlue alue) throws SQLException {
        ArrayList<Palvelu> palveluLista;
        palveluLista = SQL_yhteys.getAlueenPalvelut(alue.get_toimintaalue_id());
        for (int i = 0; i < palveluLista.size(); i++) {
            System.out.println(palveluLista.get(i));
        }
        return palveluLista;
    }

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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.nimi + ", "+this.hinta+"€/kpl";
    }

}
