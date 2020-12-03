package game;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Profil {
    
    private String nom;
    private String avatar;
    private Date anniversaire;
    private ArrayList<Partie> parties;

    public Profil(String nom) {
        this.nom = nom;
        parties = new ArrayList<Partie>();
    }
    
}
