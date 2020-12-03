/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import env3d.Env;
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
        nbLettresRestantes = getLettres().size();
    }
    
    protected void appliqueRegles(Partie partie) {
        if (chrono.remainsTime() && nbLettresRestantes > 0) {
            if (tuxTrouveLettre()) {
                
                nbLettresRestantes--;
                
                getEnv().removeObject(getLettres().pollFirst());
                partie.setTrouve(nbLettresRestantes);
                partie.setTemps(chrono.getSeconds());
                System.out.println((int)(((partie.getMot().length() - nbLettresRestantes) / (double)partie.getMot().length())*100));
            }
        }
    }
    
    protected void terminePartie(Partie partie) {
        chrono.stop();
        partie.setTemps(chrono.getSeconds());
        System.out.println("PARTIE TERMINEE");
    }
    
    public boolean tuxTrouveLettre() {
        //detecte collision : et renvoie 
        return collision(getLettres().get(getLettres().size() - nbLettresRestantes)); 
    }
    
    public int getNbLettresRestantes() {
        return nbLettresRestantes;
    }
    
    public int getTemps() {
        return chrono.getSeconds();
    }
}
