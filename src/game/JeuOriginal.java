package game;

import env3d.Env;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public abstract class JeuOriginal {
    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    private List<Lettre> lettres;
    private Dico dico;

    public JeuOriginal() {
        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        room = new Room();

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil();
        
        // Instancie les lettres par défaut
        lettres = new ArrayList<>();
        
        // Instancie le dictionnaire.
        dico = new Dico("src/xml/dico.xml");
    }
    
    public void execute() {
 
        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        String date = getDate();
        // TODO
        joue(new Partie("02/12/2020", "mot", 1));
         
        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }
    
    public void joue(Partie partie) {
 
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);
        
        // Lecture du dictionnaire.
        try {
            dico.lireDictionnaireDOM("src/xml/", "dico.xml");
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // TEMPORAIRE : récupération d'un mot d'un niveau
        String mot = dico.getMotDepuisListeNiveau((int) (Math.random()*4)+1);
        
        // On instancie la liste de lettres du mot à retrouver.
        for (int i = 0; i < mot.length(); i++) {
            Lettre l = new Lettre(mot.charAt(i), (Math.random()*(100-2))+2, (Math.random()*(100-2))+2);
            lettres.add(l);
        }
        
        // On ajoute les lettres à l'environnement de jeu.
        lettres.forEach(l -> {
            env.addObject(l);
        });
 
        // Instancie un Tux
        tux = new Tux(env, room);
        env.addObject(tux);
         
        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);
         
        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {
 
            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == Keyboard.KEY_ESCAPE) {
                finished = true;
            }
 
            // Contrôles des déplacements de Tux (gauche, droite, ...)
            tux.deplace();
 
            // Ici, on applique les regles
            appliqueRegles(partie);
 
            // Fait avancer le moteur de jeu (mise à jour de l'affichage, de l'écoute des événements clavier...)
            env.advanceOneFrame();
        }
 
        // Ici on peut calculer des valeurs lorsque la partie est terminée
        terminePartie(partie);
 
    }
    
    abstract void demarrePartie(Partie partie);
    
    abstract void appliqueRegles(Partie partie);
    
    abstract void terminePartie(Partie partie);
    
    public double distance (Lettre lettre) {
        return Math.sqrt (Math.pow(this.tux.getX() - lettre.getX(), 2) + Math.pow(this.tux.getZ() - lettre.getZ(), 2));
    } 
    
    public boolean collision (Lettre lettre) {
        boolean res = false;
        if (distance(lettre) < tux.getScale() + lettre.getScale()) {
            res = true;
        }
        return res;
    }
    
    
    public String getDate() {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
        return dateFormat.format(date);
    }
    
    
}
