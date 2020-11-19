/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Dico {
    
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;
    
    public Dico(String cheminFichierDico) {
        this.cheminFichierDico = cheminFichierDico;
        listeNiveau1 = new ArrayList<>();
        listeNiveau2 = new ArrayList<>();
        listeNiveau3 = new ArrayList<>();
        listeNiveau4 = new ArrayList<>();
        listeNiveau5 = new ArrayList<>(); 
    } 
    
    public String getMotDepuisListeNiveau(int niveau) {
        String res;
        switch(verifieNiveau(niveau)){
            case 1 : 
                res = getMotDepuisListe(listeNiveau1);
                break;
                
            case 2 : 
                res = getMotDepuisListe(listeNiveau2);
                break;
                
            case 3 : 
                res = getMotDepuisListe(listeNiveau3);
                break;
                
            case 4 : 
                res = getMotDepuisListe(listeNiveau4);
                break;
                
            case 5 : 
                res = getMotDepuisListe(listeNiveau5);
                break;
                
            default : 
                res = "";
        }
        return res; 
    }
    
    private String getMotDepuisListe(ArrayList<String> list) {
        String mot = "";
        Random random = new Random();
        int nbreAleatoire;
        
        if (!list.isEmpty()) {
            nbreAleatoire = random.nextInt(list.size());
            mot = list.get(nbreAleatoire);
        }
        return mot;
    }
    
    private int verifieNiveau(int niveau) {
        int res = -1;
        
        if ((niveau >= 1) && (niveau <= 5)) {
            res = niveau;
        }
        return res;
    }
    
    public void ajouteMotADico(int niveau, String mot) {
        
        switch(verifieNiveau(niveau)) {
            case 1 :
                listeNiveau1.add(mot);
                break;
                
            case 2 :
                listeNiveau2.add(mot);
                break;
                
            case 3 :
                listeNiveau3.add(mot);
                break;
                
            case 4 :
                listeNiveau4.add(mot);
                break;
                
            case 5 :
                listeNiveau5.add(mot);
                break;
                
            default : 
        }
        
    }
    
    
    
}
