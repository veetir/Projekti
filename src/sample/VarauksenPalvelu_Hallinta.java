package sample;

public class VarauksenPalvelu_Hallinta {
    private int varaus_id;
    private int palvelu_id;
    private String palvelu;
    private int lkm;
    private float hinta;

    public VarauksenPalvelu_Hallinta(int varaus_id, int palvelu_id, String palvelu, int lkm, float hinta) {
        this.varaus_id = varaus_id;
        this.palvelu_id = palvelu_id;
        this.palvelu = palvelu;
        this.lkm = lkm;
        this.hinta = hinta;
    }

    public int getVaraus_id() {
        return varaus_id;
    }

    public float getHinta() {
        return hinta;
    }

    public void setHinta(float hinta) {
        this.hinta = hinta;
    }

    public int getLkm() {
        return lkm;
    }

    public void setLkm(int lkm) {
        this.lkm = lkm;
    }

    public String getPalvelu() {
        return palvelu;
    }

    public void setPalvelu(String palvelu) {
        this.palvelu = palvelu;
    }

    public int getPalvelu_id() {
        return palvelu_id;
    }

    public void setPalvelu_id(int palvelu_id) {
        this.palvelu_id = palvelu_id;
    }

    public void setVaraus_id(int varaus_id) {
        this.varaus_id = varaus_id;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.getPalvelu()+", "+this.getLkm()+" kpl";
    }
}
