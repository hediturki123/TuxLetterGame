/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class JeuDevineLeMotOrdre extends Jeu {
    private int nbLettresRestantes;
    private Chronometre chrono;
    
    public JeuDevineLeMotOrdre() {
        
    }

    protected void demarrePartie(Partie partie){
        
    }
    
    protected void appliqueRegles(Partie partie) {
    
    }
    
    protected void terminePartie(Partie partie) {
    
    }
    
    public boolean tuxTrouveLettre() {
        return false; //TODO
    }
    
    public int getNbLettresRestantes() {
        return nbLettresRestantes;
    }
    
    public int getTemps() {
        return chrono.getSeconds();
    }
    
}
