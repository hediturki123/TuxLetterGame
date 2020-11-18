package game;

import env3d.Env;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Jeu {
    private Env env;
    private Room room;
    private Profil profil;
    private Tux tux;
    private List<Lettre> lettres;

    public Jeu() {
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
    }
    
    public void execute() {
 
        // pour l'instant, nous nous contentons d'appeler la méthode joue comme cela
        // et nous créons une partie vide, juste pour que cela fonctionne
        joue(new Partie());
         
        // Détruit l'environnement et provoque la sortie du programme 
        env.exit();
    }
    
    public void joue(Partie partie) {
 
        // TEMPORAIRE : on règle la room de l'environnement. Ceci sera à enlever lorsque vous ajouterez les menus.
        env.setRoom(room);
        
        // TEMPORAIRE : on initialise la liste de lettres.
        lettres.add(new Lettre('s',25.0,25.0));
        lettres.add(new Lettre('a',25.0,75.0));
        lettres.add(new Lettre('g',75.0,75.0));
        lettres.add(new Lettre('e',75.0,25.0));
        lettres.forEach(l -> {
            env.addObject(l);
        });
 
        // Instancie un Tux
        tux = new Tux(env,room);
        env.addObject(tux);
         
        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        démarrePartie(partie);
         
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
    
    protected void démarrePartie(Partie partie) {}
    
    protected void appliqueRegles(Partie partie) {}
    
    protected void terminePartie(Partie partie) {}
}
