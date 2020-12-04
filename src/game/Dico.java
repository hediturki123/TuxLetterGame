/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import jdk.internal.org.xml.sax.helpers.DefaultHandler;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class Dico extends DefaultHandler {
    
    private ArrayList<String> listeNiveau1;
    private ArrayList<String> listeNiveau2;
    private ArrayList<String> listeNiveau3;
    private ArrayList<String> listeNiveau4;
    private ArrayList<String> listeNiveau5;
    private String cheminFichierDico;
    StringBuffer buffer;
    
    public Dico(String cheminFichierDico) {
        super();
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
    
    // Question 8.12
    public void lireDictionnaireDOM(String path, String filename) throws IOException, SAXException, ParserConfigurationException {
        File dicoXML = new File(path + filename);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(dicoXML);
        
        NodeList mots = doc.getElementsByTagName("mot");
        
        for (int i = 0; i < mots.getLength(); i++) {
            Element motElement = (Element) mots.item(i);
            String mot = motElement.getFirstChild().getNodeValue();
            int niveau = Integer.parseInt(motElement.getAttribute("niveau"));

            ajouteMotADico(niveau, mot);
        }
    }
    
    /*public void lireDictionnaire() {
   
    }
    
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(qName.equals("dictionnaire")) {
            
        } else if (qName.equals("mot")){
            
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    
    }
        
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
    
    }

    @Override
    public void startDocument() throws SAXException {
    
    }

    @Override
    public void endDocument() throws SAXException {
    
    } 
    */
    
    
}
