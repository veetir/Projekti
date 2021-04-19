package sample;

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

    
}
