package game;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Partie {

    private final String date;
    private final String mot;
    private final int niveau;
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
       trouve = (int)((mot.length() - nbreLettresRestantes) / (mot.length())) * 100 ;
    }
    
    public void setTemps(int temps) {
        this.temps = temps;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    @Override
    public String toString() {
        return "La date de cette partie est : " + date + ", le mot est :" + mot + ", le niveau est : " 
                + getNiveau() + ", le temps de cette partie est de : " + temps + ", le pourcentage est de : " + trouve + " %.";
    }
    
}
