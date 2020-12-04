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
    private static final int LIMITE = 30;
    
    public JeuDevineLeMotOrdre() {
        chrono = new Chronometre(LIMITE);
    }

    @Override
    protected void demarrePartie(Partie partie){
        chrono.start();
        nbLettresRestantes = getLettres().size();
    }
    
    @Override
    protected void appliqueRegles(Partie partie) {
        if (chrono.remainsTime() && nbLettresRestantes > 0) {
            if (tuxTrouveLettre()) {
                getEnv().removeObject(getLettres().pollFirst());
                nbLettresRestantes--;
            }
        }
    }
    
    @Override
    protected void terminePartie(Partie partie) {
        chrono.stop();
        partie.setTrouve(nbLettresRestantes);
        partie.setTemps(chrono.getSeconds());
        System.out.println("PARTIE TERMINEE");
    }
    
    public boolean tuxTrouveLettre() {     
        return collision(getLettres().get(getLettres().size() - nbLettresRestantes)); 
    }
    
    public int getNbLettresRestantes() {
        return nbLettresRestantes;
    }
    
    public int getTemps() {
        return chrono.getSeconds();
    }
}
