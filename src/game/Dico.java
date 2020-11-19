/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.util.ArrayList;

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
        switch(niveau){
            case 1 : 
                res = "listeNiveau1";
                break;
                
            case 2 : 
                res = "listeNiveau2";
                break;
                
            case 3 : 
                res = "listeNiveau3";
                break;
                
            case 4 : 
                res = "listeNiveau4";
                break;
                
            case 5 : 
                res = "listeNiveau5";
                break;
            default : 
                res = "";
                break;
        }
        return res; 
    }
    
    private String getMotDepuisListe(ArrayList<String> list) {
        return " ";
    }
    
    private int verifieNiveau(int niveau) {
        return 0;
    }
    
    
    
}
