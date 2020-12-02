package game;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class LanceurDeJeu {
    public static void main(String[] args) {
        // Declare un Jeu
        Jeu jeu;
        //Instancie un nouveau jeu
        jeu = new JeuDevineLeMotOrdre();
        //Execute le jeu
        jeu.execute();
    }
}
