/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class JeuDevineLeMotOrdre extends Jeu {
    private int nbLettresRestantes;
    private Chronometre chrono;
    private static int LIMITE = 30;
    
    public JeuDevineLeMotOrdre() {
        chrono = new Chronometre(LIMITE);
    }

    protected void demarrePartie(Partie partie){
        chrono.start();
        //nbLettresRestantes ?
    }
    
    protected void appliqueRegles(Partie partie) {
    
    }
    
    protected void terminePartie(Partie partie) {
        chrono.stop();
        int tempsPartie = (int)chrono.getTime();
        if (tempsPartie < LIMITE) {
            partie.setTemps(tempsPartie);
        }
        partie.setTrouve(tempsPartie);
    }
    
    public boolean tuxTrouveLettre() {
        //detecte collision
        return false; //TODO
    }
    
    public int getNbLettresRestantes() {
        return nbLettresRestantes;
    }
    
    public int getTemps() {
        return chrono.getSeconds();
    }
}
