package sample;

public class Mokki {
    private int mokki_id;
    private int toimintaalue_id;
    private String toimintaalue_nimi;
    private String postinro;
    private String mokkinimi;
    private String katuosoite;
    private String kuvaus;
    private int henkilomaara;
    private String varustelu;
    private long hinta;

    public Mokki(int mokki_id, int toimintaalue_id,String toimintaalue_nimi, String postinro, String mokkinimi, String katuosoite,String kuvaus, int henkilomaara,String varustelu, long hinta) {
        this.mokki_id = mokki_id;
        this.toimintaalue_id = toimintaalue_id;
        this.toimintaalue_nimi = toimintaalue_nimi;
        this.postinro = postinro;
        this.mokkinimi = mokkinimi;
        this.katuosoite = katuosoite;
        this.kuvaus = kuvaus;
        this.henkilomaara = henkilomaara;
        this.varustelu = varustelu;
        this.hinta = hinta;

    }



    public String getToimintaalue_nimi() {
        return toimintaalue_nimi;
    }

    public void setToimintaalue_nimi(String toimintaalue_nimi) {
        this.toimintaalue_nimi = toimintaalue_nimi;
    }

    public long getHinta() {
        return hinta;
    }

    public void setHinta(long hinta) {
        this.hinta = hinta;
    }

    public int get_mokki_id() {
        return this.mokki_id;
    }
    public int get_toimintaalue_id() {
        return this.toimintaalue_id;
    }
    public String get_postinro() {
        return this.postinro;
    }
    public String get_mokkinimi() {
        return this.mokkinimi;
    }
    public String get_katuosoite() {
        return this.katuosoite;
    }
    public String get_kuvaus() {
        return this.kuvaus;
    }
    public int get_henkilomaara() {
        return this.henkilomaara;
    }
    public String get_varustelu() {
        return this.varustelu;
    }
    public void set_mokki_id(int i) {
        this.mokki_id = i;
    }
    public void set_toimintaalue_id(int i) {
        this.toimintaalue_id = i;
    }
    public void set_postinro(String s) {
        this.postinro = s;
    }
    public void set_mokkinimi(String s) {
        this.mokkinimi = s;
    }
    public void set_katuosoite(String s) {
        this.katuosoite = s;
    }
    public void set_kuvaus( String s) {
        this.kuvaus = s;
    }
    public void set_henkilomaara(int i) {
        this.henkilomaara = i;
    }
    public void set_varustelu(String s) {
        this.varustelu = s;
    }

    public String toString() {
        return "mokki_id: "+this.mokki_id+"\ntoimintaalue_id_ "+this.toimintaalue_id+"\ntoiminta-alue: "+this.toimintaalue_nimi+"\npostinro: "+this.postinro+"\nmokkinimi: "+this.mokkinimi+"\nkatuosoite: "+this.katuosoite+
        "\nkuvaus: "+this.kuvaus+"\nhenkilomaara: "+this.henkilomaara+"\nvarustelu: "+this.varustelu+"\nhinta: "+this.hinta;
    }
}
