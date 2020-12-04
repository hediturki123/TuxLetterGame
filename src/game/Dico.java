package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
    private final String cheminFichierDico;
    private StringBuffer buffer;
    private boolean inMot;
    private int niveauCourant;
    
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
        String res = "";
        switch (verifieNiveau(niveau)) {
            case 1: res = getMotDepuisListe(listeNiveau1); break;   
            case 2: res = getMotDepuisListe(listeNiveau2); break;
            case 3: res = getMotDepuisListe(listeNiveau3); break;
            case 4: res = getMotDepuisListe(listeNiveau4); break;
            case 5: res = getMotDepuisListe(listeNiveau5); break;       
            default: break;
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
        int res = 0;
        if ((niveau >= 1) && (niveau <= 5)) res = niveau;
        return res;
    }
    
    public void ajouteMotADico(int niveau, String mot) {
        
        // Si la taille du mot ne fait pas au moins 3 lettres, on ignore son ajout.
        if (mot.length() < 3) return;
        
        // On ajoute le mot en fonction de son niveau.
        switch(verifieNiveau(niveau)) {
            case 1: listeNiveau1.add(mot); break;
            case 2: listeNiveau2.add(mot); break;       
            case 3: listeNiveau3.add(mot); break;       
            case 4: listeNiveau4.add(mot); break;      
            case 5: listeNiveau5.add(mot); break;   
            default: break;
        }
        
    }
    
    public void lireDictionnaireDOM(String cheminFichierDico) throws IOException, SAXException, ParserConfigurationException {
        File dicoXML = new File(cheminFichierDico);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(dicoXML);
        
        // Liste des mots récupérés depuis le fichier.
        NodeList mots = doc.getElementsByTagName("mot");
        
        // Pour chaque mot, on les ajoute au dictionnaire.
        for (int i = 0; i < mots.getLength(); i++) {
            Element motElement = (Element) mots.item(i);
            String mot = motElement.getFirstChild().getNodeValue();
            int niveau = Integer.parseInt(motElement.getAttribute("niveau"));

            ajouteMotADico(niveau, mot);
        }
    }
    
    public void lireDictionnaireDOM(String path, String filename) throws IOException, SAXException, ParserConfigurationException {
        lireDictionnaireDOM(path+filename);
    }
    
    public void lireDictionnaireDOM() throws IOException, SAXException, ParserConfigurationException {
        lireDictionnaireDOM(cheminFichierDico);
    }
    
    public void lireDictionnaire(String cheminFichierDico) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory fabrique = SAXParserFactory.newInstance(); 
        SAXParser parseur = fabrique.newSAXParser(); 

        File fichier = new File(cheminFichierDico); 
        DefaultHandler gestionnaire = this;
        parseur.parse(fichier, gestionnaire);
    }
    
    public void lireDictionnaire() throws ParserConfigurationException, SAXException, IOException {
        lireDictionnaire(cheminFichierDico);
    }
    
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        buffer = new StringBuffer();
        if (qName.equals("mot")) {
            inMot = true;
            try {
                niveauCourant = Integer.parseInt(attributes.getValue("niveau"));
                if (niveauCourant < 1 || niveauCourant > 5)  niveauCourant = 0;
            } catch (NumberFormatException e) {
                niveauCourant = 0;
            }
        }  
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("mot")){
            int bufLen = buffer.toString().length();
            inMot = false;
            switch(niveauCourant) {
                case 0 :
                    if (bufLen > 7) niveauCourant = 5;
                    else niveauCourant = bufLen - 2;
                    // On ne fait pas de break pour pouvoir prendre en compte le nouveau niveau.
                case 1: listeNiveau1.add(buffer.toString()); break;
                case 2: listeNiveau2.add(buffer.toString()); break;
                case 3: listeNiveau3.add(buffer.toString()); break;
                case 4: listeNiveau4.add(buffer.toString()); break;
                case 5: listeNiveau5.add(buffer.toString()); break;
                default: break;
            }
            buffer = null;
        }
         
    }
        
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String lecture = new String(ch, start, length); 
        if(buffer != null) buffer.append(lecture);
    }

    @Override
    public void startDocument() throws SAXException {
        listeNiveau1 = new ArrayList<>();
        listeNiveau2 = new ArrayList<>();
        listeNiveau3 = new ArrayList<>();
        listeNiveau4 = new ArrayList<>();
        listeNiveau5 = new ArrayList<>();
    }

    @Override
    public void endDocument() throws SAXException {} 

}
