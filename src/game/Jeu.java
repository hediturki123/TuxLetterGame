package game;

import env3d.Env;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.lwjgl.input.Keyboard;
import org.xml.sax.SAXException;

/**
 *
 * @author gladen
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE
    }

    private final Env env;
    private Tux tux;
    private final Room mainRoom;
    private final Room menuRoom;
    private LinkedList<Lettre> lettres;
    private Profil profil;
    private final Dico dico;
    protected EnvTextMap menuText;
    
    
    
    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();

        // Instancie une Room
        mainRoom = new Room();

        // Instancie une autre Room pour les menus
        menuRoom = new Room();
        menuRoom.setTextureEast("textures/black.png");
        menuRoom.setTextureWest("textures/black.png");
        menuRoom.setTextureNorth("textures/black.png");
        menuRoom.setTextureBottom("textures/black.png");

        // Règle la camera
        env.setCameraXYZ(50, 60, 175);
        env.setCameraPitch(-20);

        // Désactive les contrôles par défaut
        env.setDefaultControl(false);

        // Instancie un profil par défaut
        profil = new Profil("inconnu", "01/01/1970");
        
        // Instancie les lettres par défaut
        lettres = new LinkedList<>();
        
        // Dictionnaire
        dico = new Dico("src/xml/dico.xml");

        // instancie le menuText
        menuText = new EnvTextMap(env);
        
        // Textes affichés à l'écran
        menuText.addText("Voulez vous ?", "Question", 200, 300);
        menuText.addText("1. Commencer une nouvelle partie ?", "Jeu1", 250, 280);
        menuText.addText("2. Charger une partie existante ?", "Jeu2", 250, 260);
        menuText.addText("3. Sortir de ce jeu ?", "Jeu3", 250, 240);
        menuText.addText("4. Quitter le jeu ?", "Jeu4", 250, 220);
        menuText.addText("Choisissez un nom de joueur : ", "NomJoueur", 200, 300);
        menuText.addText("1. Charger un profil de joueur existant ?", "Principal1", 250, 280);
        menuText.addText("2. Créer un nouveau joueur ?", "Principal2", 250, 260);
        menuText.addText("3. Sortir du jeu ?", "Principal3", 250, 240);
        menuText.addText("Choisir un niveau : ", "Niveau", 200, 300);
        menuText.addText("Mot à trouver : ", "Mot", 200, 450);
        menuText.addText("Quelle est votre date de naissance ?", "DateNaissance", 200, 300);
        
    }

    /**
     * Gère le menu principal
     *
     */
    public void execute() {

        MENU_VAL mainLoop;
        mainLoop = MENU_VAL.MENU_SORTIE;
        do {
            mainLoop = menuPrincipal();
        } while (mainLoop != MENU_VAL.MENU_SORTIE);
        this.env.setDisplayStr("Au revoir !", 300, 30);
        env.exit();
    }
    
    
    public LinkedList<Lettre> getLettres() {
        return lettres;
    }
    
    public Env getEnv() {
        return env;
    }


    // fourni
    private String getNomJoueur() {
        String nom;
        menuText.getText("NomJoueur").display();
        nom = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nom;
    }
    
    private int getNiveau(){
        int niveau;
        menuText.getText("Niveau").display();
        niveau = Integer.parseInt(menuText.getText("Niveau").lire(true));
        menuText.getText("Niveau").clean();
        if (niveau < 0) niveau = 1;
        else if (niveau > 6) niveau = 5;
        return niveau;
    }
    
    private String getDateNaissance() {
        String anniv;
        menuText.getText("DateNaissance").display();
        anniv = menuText.getText("DateNaissance").lire(true);
        if (!anniv.matches("^(0[1-9]|[12]\\d|3[01])\\/(0[1-9]|1[0-2])\\/\\d{4}$")) anniv = "01/01/1970";
        menuText.getText("DateNaissance").clean();
        return anniv;
    }

    
    // fourni, à compléter
    private MENU_VAL menuJeu() {

        MENU_VAL playTheGame;
        playTheGame = MENU_VAL.MENU_JOUE;
        Partie partie;
        int niveau;
        String date = getDate();
        
        do {
            // restaure la room du menu
            env.setRoom(menuRoom);
            // affiche menu
            menuText.getText("Question").display();
            menuText.getText("Jeu1").display();
            menuText.getText("Jeu2").display();
            menuText.getText("Jeu3").display();
            menuText.getText("Jeu4").display();
            
            // vérifie qu'une touche 1, 2, 3 ou 4 est pressée
            int touche = 0;
            while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
                touche = env.getKey();
                env.advanceOneFrame();
            }

            // nettoie l'environnement du texte
            menuText.getText("Question").clean();
            menuText.getText("Jeu1").clean();
            menuText.getText("Jeu2").clean();
            menuText.getText("Jeu3").clean();
            menuText.getText("Jeu4").clean();

            // restaure la room du jeu
            env.setRoom(mainRoom);

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico
                    // .......... dico.******
                    // crée un nouvelle partie
                    niveau = getNiveau();
                    menuText.getText("Niveau").display();

                     // Lecture du dictionnaire.
                    try {
                        dico.lireDictionnaireDOM("src/xml/", "dico.xml");
                    } catch (IOException | SAXException | ParserConfigurationException ex) {
                        Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    // récupération d'un mot d'un niveau
                    String mot = dico.getMotDepuisListeNiveau(niveau);
                    menuText.getText("Niveau").clean();
                    menuText.getText("Mot").addTextAndDisplay("", " " + mot);

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            menuText.getText("Mot").clean();
                        }
                    }, 5000);

                    partie = new Partie(date, mot, niveau);
                    
                    profil.sauvegarder("src/profil/" + profil.getNom() + ".xml");
                    
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;


                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante
                    
                    partie = new Partie(date, "test", 1); //XXXXXXXXX
                    // Recupère le mot de la partie existante
                    // ..........
                    // joue
                    joue(partie);
                    // enregistre la partie dans le profil --> enregistre le profil
                    // .......... profil.******
                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 3 : Sortie de ce jeu
                // -----------------------------------------                
                case Keyboard.KEY_3:
                    playTheGame = MENU_VAL.MENU_CONTINUE;
                    break;

                // -----------------------------------------
                // Touche 4 : Quitter le jeu
                // -----------------------------------------                
                case Keyboard.KEY_4:
                    playTheGame = MENU_VAL.MENU_SORTIE;
            }
        } while (playTheGame == MENU_VAL.MENU_JOUE);
        return playTheGame;
    }

    private MENU_VAL menuPrincipal() {

        MENU_VAL choix = MENU_VAL.MENU_CONTINUE;
        

        // restaure la room du menu
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("Principal1").display();
        menuText.getText("Principal2").display();
        menuText.getText("Principal3").display();
               
        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();

        // et décide quoi faire en fonction de la touche pressée
        switch (touche) {
            // -------------------------------------
            // Touche 1 : Charger un profil existant
            // -------------------------------------
            case Keyboard.KEY_1:
                // demande le nom du joueur existant
                String nom = getNomJoueur();
                // charge le profil de ce joueur si possible
                profil = new Profil("src/profil/" + nom + ".xml");
                
                if (profil.charge(nom)) {
                    choix = menuJeu();
                } else {
                    choix = MENU_VAL.MENU_SORTIE;//CONTINUE;
                }
                break;

            // -------------------------------------
            // Touche 2 : Créer un nouveau joueur
            // -------------------------------------
            case Keyboard.KEY_2:            
                // crée un profil avec le nom d'un nouveau joueur
                profil = new Profil(getNomJoueur(), getDateNaissance());
                profil.sauvegarder("src/profil/" + profil.getNom() + ".xml");
                choix = menuJeu();
                break;

            // -------------------------------------
            // Touche 3 : Sortir du jeu
            // -------------------------------------
            case Keyboard.KEY_3:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    public void joue(Partie partie) {

        // On instancie la liste de lettres du mot à retrouver.
        for (int i = 0; i < partie.getMot().length(); i++) {
            Lettre l = new Lettre(partie.getMot().charAt(i), (Math.random()*(100-2))+2, (Math.random()*(100-2))+2);
            lettres.add(l);
        }
        
        // On ajoute les lettres à l'environnement de jeu.
        lettres.forEach(l -> {
            env.addObject(l);
        });
 
        // Instancie un Tux
        tux = new Tux(env, mainRoom);
        env.addObject(tux);
         
        // Ici, on peut initialiser des valeurs pour une nouvelle partie
        demarrePartie(partie);
         
        // Boucle de jeu
        Boolean finished;
        finished = false;
        while (!finished) {
 
            // Contrôles globaux du jeu (sortie, ...)
            //1 is for escape key
            if (env.getKey() == Keyboard.KEY_ESCAPE || lettres.isEmpty()) {
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
        
        profil.ajouterPartie(partie);
        profil.sauvegarder("src/profil/"+profil.getNom()+".xml");

    }

    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);
    
    public String getDate() {
        LocalDateTime date = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date);
    }
    
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

}
