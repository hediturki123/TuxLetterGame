package game;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Partie {

    private String date;
    private String mot;
    private int niveau;
    private int trouve;
    private int temps;    
    
    public Partie(String date, String mot, int niveau) {
       this.date = date;
       this.mot = mot;
       this.niveau = niveau;
       trouve = 0;
       temps = 0;
    }
    
    public void setTrouve(int nbreLettresRestantes) {
       //TODO
    }
    
    public void setTemps(int temps) {
        this.temps = temps;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    @Override
    public String toString() {
        return "";//TODO
    }
    
}
