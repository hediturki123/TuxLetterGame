/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;
import game.Dico;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class TestDico {
    
    public static void main(String[] args) {
        Dico dico = new Dico("");
        
        dico.ajouteMotADico(1, "bonjour");
        dico.ajouteMotADico(1, "comment");
        dico.ajouteMotADico(1, "vas");
        dico.ajouteMotADico(1, "tu");
        
        dico.ajouteMotADico(2, "ballon");
        dico.ajouteMotADico(2, "ballon");
    
        dico.ajouteMotADico(3, "bibliothèque");
        dico.ajouteMotADico(3, "bibliothèque");
        
        
        
        
        for (int i = 1; i < 6; i++) {
            System.out.println("Niveau = " + i);
            for (int j = 1; j < 100; j++) {
                System.out.println("Mot = " + dico.getMotDepuisListeNiveau(i));
            }
        }
        

        
        
    }
            
    
}
