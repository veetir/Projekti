package sample;

public class VarauksenPalvelu {

    int lkm;
    Palvelu palvelu;

    public VarauksenPalvelu(Palvelu p, int lkm) {
        this.palvelu = p;
        this.lkm = lkm;
    }

    public int getLkm() {
        return lkm;
    }

    public void setLkm(int lkm) {
        this.lkm = lkm;
    }

    public Palvelu getPalvelu() {
        return palvelu;
    }
    
    public void setPalvelu(Palvelu palvelu) {
        this.palvelu = palvelu;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "" + this.palvelu.getNimi() + ", ID:  "+this.palvelu.getPalveluId()+ ", lkm: "+this.getLkm();
    }





    
}
