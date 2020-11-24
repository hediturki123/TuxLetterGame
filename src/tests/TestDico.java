/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;
import game.Dico;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class TestDico {
    
    public static void main(String[] args) throws SAXException, IOException {
        Dico dico = new Dico("src/xml/dico.xml");
        
        try {
            dico.lireDictionnaireDOM("src/xml/", "dico.xml");
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TestDico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
            for (int j = 1; j < 50; j++) {
                System.out.println("Mot = " + dico.getMotDepuisListeNiveau(i));
            }
        }
    }
            
    
}
