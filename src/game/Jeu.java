package game;

import env3d.Env;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.lwjgl.input.Keyboard;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public abstract class Jeu {

    enum MENU_VAL {
        MENU_SORTIE, MENU_CONTINUE, MENU_JOUE, MENU_DICO
    }

    public static final String CHEMIN_DICO = "src/xml/dico.xml";

    private final Env env;
    private Tux tux;
    private Room mainRoom;
    private final Room menuRoom;
    private LinkedList<Lettre> lettres;
    private Profil profil;
    private final Dico dico;
    protected EnvTextMap menuText;

    public Jeu() {

        // Crée un nouvel environnement
        env = new Env();

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
        dico = new Dico(Jeu.CHEMIN_DICO);

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
        menuText.addText("3. Gérer le dictionnaire ?", "GererDico", 250, 240);
        menuText.addText("4. Sortir du jeu ?", "Principal3", 250, 220);
        menuText.addText("Niveau : ", "Niveau", 200, 300);
        menuText.addText("Mot : ", "Mot", 200, 300);
        menuText.addText("Quelle est votre date de naissance (YYYY-MM-DD) ? ", "DateNaissance", 100, 300);
        menuText.addText("Gagné !!!", "Gagne", 100, 300);
        menuText.addText("Vous avez déjà trouvé tous les mots de la dernière partie !\nFaites une nouvelle partie.", "ChargerErreur", 100, 150);
        menuText.addText("1. Consulter le dictionnaire ?", "MenuDico1", 250, 280);
        menuText.addText("2. Ajouter un mot au dictionnaire ?", "MenuDico2", 250, 260);
        menuText.addText("3. Revenir au menu principal ?", "MenuDico3", 250, 240);
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

    // Fonction permettant de demander la saisie du nom du joueur.
    private String getNomJoueur() {
        String nom;
        menuText.getText("NomJoueur").display();
        nom = menuText.getText("NomJoueur").lire(true);
        menuText.getText("NomJoueur").clean();
        return nom;
    }

    // Fonction permettant de demander au joueur un mot à intégrer au dictionnaire.
    private String getMotDico() {
        String mot;
        do {
            menuText.getText("Mot").display();
            mot = menuText.getText("Mot").lire(true);
            menuText.getText("Mot").clean();
        } while (mot.length() < 3);
        return mot;
    }

    // Fonction permettant de demander la saisie du niveau d'un mot.
    private int getNiveau() {
        int niveau;
        menuText.getText("Niveau").display();
        niveau = Integer.parseInt(menuText.getText("Niveau").lire(true));
        menuText.getText("Niveau").clean();
        if (niveau < 1) niveau = 1;
        else if (niveau > 5)  niveau = 5;
        return niveau;
    }

    // Fonction permettant de demander la saisie d'une date de naissance au joueur pour l'intégrer à son profil.
    private String getDateNaissance() {
        String anniv;

        // Demande de la date de naissance.
        menuText.getText("DateNaissance").display();

        // Récupération de la date au format XML (la saisie n'accepte pas le caractère "/" ; amélioration posssible).
        anniv = menuText.getText("DateNaissance").lire(true);

        // Si la date ne respecte pas le format XML, on l'initialise au 1er janvier 1970 par défaut.
        if (!anniv.matches("^\\d{4}\\-(0[1-9]|1[0-2])\\-(0[1-9]|[12]\\d|3[01])$")) {
            anniv = "1970-01-01";
        }

        // On convertit la date au format du profil.
        anniv = Profil.xmlDateToProfileDate(anniv);

        // On nettoie l'affichage de la demande.
        menuText.getText("DateNaissance").clean();

        return anniv;

    }

    
    public void setRoomTux(int niveau) {
        if (niveau < 1) niveau = 1;
        else if (niveau > 5)  niveau = 5;
        mainRoom = new Room("src/xml/plateau"+niveau+".xml");
        env.setRoom(mainRoom);
    }

    
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
            if (!lettres.isEmpty()) {
                lettres.clear();
                //On clear les lettres qui sont sur le terrain si on s'arrete au milieu d'une partie et qu'on veut en lancer un nouvelle
            }

            // et décide quoi faire en fonction de la touche pressée
            switch (touche) {
                // -----------------------------------------
                // Touche 1 : Commencer une nouvelle partie
                // -----------------------------------------                
                case Keyboard.KEY_1: // choisi un niveau et charge un mot depuis le dico

                    niveau = getNiveau();
                    menuText.getText("Niveau").display();

                    setRoomTux(niveau);

                    // Lecture du dictionnaire.
                    try {
                        dico.lireDictionnaire(Jeu.CHEMIN_DICO);
                    } catch (IOException | SAXException | ParserConfigurationException ex) {
                        Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // récupération d'un mot d'un niveau
                    String mot = dico.getMotDepuisListeNiveau(niveau);
                    menuText.getText("Niveau").clean();
                    menuText.getText("Mot").addTextAndDisplay("", " " + mot);

                    delayedClean("Mot", 5000);

                    partie = new Partie(date, mot, niveau);

                    profil.sauvegarder("src/profil/" + profil.getNom() + ".xml");

                    // joue
                    joue(partie);

                    playTheGame = MENU_VAL.MENU_JOUE;
                    break;

                // -----------------------------------------
                // Touche 2 : Charger une partie existante
                // -----------------------------------------                
                case Keyboard.KEY_2: // charge une partie existante

                    // Charger la dernière partie faite si trouve != 100%, sinon afficher un msg dans le menu
                    if (profil.getDernierTrouve() != 100) {

                        menuText.getText("Mot").addTextAndDisplay("", " " + profil.getDernierMot());

                        delayedClean("Mot", 5000);

                        partie = new Partie(date, profil.getDernierMot(), profil.getDernierNiveau());
                        setRoomTux(profil.getDernierNiveau());

                    } else {
                        menuText.getText("ChargerErreur").display();
                        delayedClean("ChargerErreur", 5000);
                        break;
                    }
                    // ..........
                    // joue
                    joue(partie);

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
        menuText.getText("GererDico").display();

        // vérifie qu'une touche 1, 2 ou 3 est pressée
        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3 || touche == Keyboard.KEY_4)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("Principal1").clean();
        menuText.getText("Principal2").clean();
        menuText.getText("Principal3").clean();
        menuText.getText("GererDico").clean();

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
                    // Si le joueur n'existe pas, on crée un nouveau profil
                    profil = new Profil(nom, getDateNaissance());
                    profil.sauvegarder("src/profil/" + profil.getNom() + ".xml");
                    choix = menuJeu();
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
                //consulter ou ajouter un mot
                choix = menuDico();
                break;
            case Keyboard.KEY_4:
                choix = MENU_VAL.MENU_SORTIE;
        }
        return choix;
    }

    private MENU_VAL menuDico() {

        MENU_VAL choix = MENU_VAL.MENU_DICO;
        EditeurDico ed = new EditeurDico();
        env.setRoom(menuRoom);

        menuText.getText("Question").display();
        menuText.getText("MenuDico1").display();
        menuText.getText("MenuDico2").display();
        menuText.getText("MenuDico3").display();

        int touche = 0;
        while (!(touche == Keyboard.KEY_1 || touche == Keyboard.KEY_2 || touche == Keyboard.KEY_3)) {
            touche = env.getKey();
            env.advanceOneFrame();
        }

        menuText.getText("Question").clean();
        menuText.getText("MenuDico1").clean();
        menuText.getText("MenuDico2").clean();
        menuText.getText("MenuDico3").clean();

        switch (touche) {
            case Keyboard.KEY_1:
                afficherDictionnaire();
                break;
            case Keyboard.KEY_2: {
                try {
                    ed.lireDOM(Jeu.CHEMIN_DICO);
                    ed.ajouterMot(getMotDico(), getNiveau());
                    ed.ecrireDOM(Jeu.CHEMIN_DICO);

                } catch (ParserConfigurationException | SAXException | IOException | TransformerException | NullPointerException ex) {
                    Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case Keyboard.KEY_3:
                choix = menuPrincipal();
                break;
        }

        return choix;
    }

    public void joue(Partie partie) {

        // On instancie la liste de lettres du mot à retrouver.
        for (int i = 0; i < partie.getMot().length(); i++) {
            Lettre l = new Lettre(partie.getMot().charAt(i), (Math.random() * (100 - 2)) + 2, (Math.random() * (100 - 2)) + 2);
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
            if (env.getKey() == Keyboard.KEY_ESCAPE) {
                finished = true;
            }

            if (lettres.isEmpty()) { // on arrete la partie quand le joueur a trouvé ttes les lettres
                menuText.getText("Gagne").display();
                delayedClean("Gagne", 4000);
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
        profil.sauvegarder("src/profil/" + profil.getNom() + ".xml");

    }

    protected abstract void demarrePartie(Partie partie);

    protected abstract void appliqueRegles(Partie partie);

    protected abstract void terminePartie(Partie partie);

    public String getDate() {
        LocalDateTime date = LocalDateTime.now();
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(date);
    }

    public double distance(Lettre lettre) {
        return Math.sqrt(Math.pow(this.tux.getX() - lettre.getX(), 2) + Math.pow(this.tux.getZ() - lettre.getZ(), 2));
    }

    public boolean collision(Lettre lettre) {
        boolean res = false;
        char l1 = lettre.getLettre();
        if (distance(lettre) < tux.getScale() + lettre.getScale()) {
            res = true;
        }
        return res;
    }

    // Fonction permettant de nettoyer l'affichage après un temps donné.
    public void delayedClean(String chaine, int tempsMilli) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                menuText.getText(chaine).clean();
            }
        }, tempsMilli); // Pour attendre 5 secondes, il faut entrer 5000 par exemple.
    }

    public void afficherDictionnaire() {
        String cheminHTML = "src/html/dico.html";
        try {
            Document docXML = XMLUtil.DocumentFactory.fromFile(Jeu.CHEMIN_DICO);
            String docHTML = XMLUtil.DocumentTransform.fromXSLTransformation("src/xml/dico.xsl", docXML);
            try (FileWriter fw = new FileWriter(new File(cheminHTML))) {
                fw.write(docHTML);
            }
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI("src/html/dico.html"));
            }
        } catch (Exception e) {
            System.err.println("Le dictionnaire n'a pu être affiché. Veuillez consulter le fichier '"+cheminHTML+"'");
        }
    }

}
