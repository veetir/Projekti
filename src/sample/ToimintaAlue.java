package sample;

import java.sql.SQLException;
import java.util.ArrayList;

public class ToimintaAlue {
    private int toimintaalue_id;
    private String nimi;

    public ToimintaAlue(String nimi, int tmid) {
        this.nimi = nimi;
        this.toimintaalue_id = tmid;
    }


    public int get_toimintaalue_id() {
        return this.toimintaalue_id;
    }
    public String get_nimi() {
        return this.nimi;
    }

    public void set_toimintaalue_id(int tmid) {
        this.toimintaalue_id = tmid;
    }

    public void set_nimi(String nimi) {
        this.nimi = nimi;
    }

    public String toString() {
        return "toimintaalue_id: " + this.toimintaalue_id + "\nnimi: " + this.nimi;
    }


    // Metodi, joka palauttaa toim.alueen nimeä vastaavan ID:n
    public static int findId(String nimi) throws SQLException {
        ArrayList<ToimintaAlue> areas;
        areas = SQL_yhteys.getToimintaAlueetX();
        System.out.println("HAETAAN NIMELLÄ " + nimi);
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).get_nimi().equalsIgnoreCase(nimi)){
                return areas.get(i).get_toimintaalue_id();
            }
        }
        // Virhekoodi: ei löytynyt ID:tä tällä nimellä
        return -1;
    }
    public static String findNimi(int id) throws SQLException {
        ArrayList<ToimintaAlue> areas;
        areas = SQL_yhteys.getToimintaAlueetX();
        System.out.println("HAETAAN id:llä " + id);
        for (int i = 0; i < areas.size(); i++) {
            if (areas.get(i).get_toimintaalue_id() == id){
                return areas.get(i).get_nimi();
            }
        }
        // Virhekoodi: ei löytynyt ID:tä tällä nimellä
        return "ei löydy";
    }
    
}
