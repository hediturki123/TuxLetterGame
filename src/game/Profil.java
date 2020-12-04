package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class Profil {

    private String nom;
    private String avatar;
    private String dateNaissance;
    private ArrayList<Partie> parties;
    public Document _doc;
    private boolean present = false;

    public Profil(String nom, String dateNaissance) {
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        parties = new ArrayList<>();
        present = true;
    }
    
    public Profil(String nomFichier) {
        
        try {
            _doc = fromXML(nomFichier);
            nom = _doc.getElementsByTagName("nom").item(0).getTextContent();
            avatar = _doc.getElementsByTagName("avatar").item(0).getTextContent();
            String xmldate = _doc.getElementsByTagName("anniversaire").item(0).getTextContent();
            dateNaissance = Profil.xmlDateToProfileDate(xmldate);
            NodeList partiesNodeList = _doc.getElementsByTagName("partie");
            parties = new ArrayList<>();

            for (int i = 0; i < partiesNodeList.getLength(); i++) {
              parties.add(new Partie((Element)partiesNodeList.item(i)));
            }  
            present = true;
            
        } catch(Exception e) {
            present = false;
        }
    }
    
    public boolean charge(String nomJoueur) {
        if (nomJoueur.equals(nom)) {
            present = true;
        }
        return present;
    }
    
    public String getNom() {
        return nom;
    }

    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(nomFichier));
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Sauvegarde un DOM en XML
    public void toXML(String nomFichier) {
       
        try {
            Transformer transf = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(_doc);
            StreamResult file = new StreamResult(new File(nomFichier));
            transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transf.setOutputProperty(OutputKeys.INDENT, "yes");
            transf.setOutputProperty(OutputKeys.STANDALONE, "yes");
            transf.transform(source, file);

        } catch (IllegalArgumentException | TransformerException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterPartie(Partie p) {
        parties.add(p);
    }
    
    public int getDernierNiveau() {
        return parties.get(parties.size() - 1).getNiveau();
    }
    
    public String getDernierMot() {
        return parties.get(parties.size() - 1).getMot();
    }
    
    public int getDernierTrouve() {
        return parties.get(parties.size() - 1).getTrouve();
    }

    @Override
    public String toString() {
        String res = "---- Profil :" +
                " Nom : " + nom + 
                "\n Avatar : " + avatar +
                "\n Anniversaire " + dateNaissance +
                "\n\n----> Parties :\n";
        for (Partie p : parties) {
            res += p.toString();
        }
        return res;
    }
    
    public void sauvegarder(String filename) { 
        try {
            // Document de sortie.
            _doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            _doc.setXmlVersion("1.0");
            _doc.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"../xslt/profil.xsl\"");
            _doc.setXmlStandalone(true);
            
            // Element racine "profil".
            Element profilElt = _doc.createElement("profil");
            
            // Déclaration des espaces de nom utilisés par le document.
            profilElt.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://myGame/tux");
            profilElt.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            profilElt.setAttribute("xsi:schemaLocation", "http://myGame/tux profil.xsd");
            
            // Création du nom.
            Element nomElt = _doc.createElement("nom");
            nomElt.setTextContent(nom);
            
            // Création de l'avatar.
            Element avatarElt = _doc.createElement("avatar");
            avatarElt.setTextContent(avatar);
            
            // Création de l'anniversaire.
            Element annivElt = _doc.createElement("anniversaire");
            annivElt.setTextContent(profileDateToXmlDate(dateNaissance));
            
            // Création de la liste des parties.
            Element partiesElt = _doc.createElement("parties");

            parties.forEach(p -> {
                partiesElt.appendChild(p.getPartie(_doc));
            });
            
            // Ajout de tous les éléments au profil.
            profilElt.appendChild(nomElt);
            profilElt.appendChild(avatarElt);
            profilElt.appendChild(annivElt);
            profilElt.appendChild(partiesElt);
            _doc.appendChild(profilElt);
            
            // Sauvegarde du document.
            toXML(filename);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(toString());
    } 
    
/// Takes a date in XML format (i.e. ????-??-??) and returns a date
    /// in profile format: dd/mm/yyyy
    public static String xmlDateToProfileDate(String xmlDate) {
        String date;
        // récupérer le jour
        date = xmlDate.substring(xmlDate.lastIndexOf("-") + 1, xmlDate.length());
        date += "/";
        // récupérer le mois
        date += xmlDate.substring(xmlDate.indexOf("-") + 1, xmlDate.lastIndexOf("-"));
        date += "/";
        // récupérer l'année
        date += xmlDate.substring(0, xmlDate.indexOf("-"));

        return date;
    }

    /// Takes a date in profile format: dd/mm/yyyy and returns a date
    /// in XML format (i.e. ????-??-??)
    public static String profileDateToXmlDate(String profileDate) {
        String date;
        // Récupérer l'année
        date = profileDate.substring(profileDate.lastIndexOf("/") + 1, profileDate.length());
        date += "-";
        // Récupérer  le mois
        date += profileDate.substring(profileDate.indexOf("/") + 1, profileDate.lastIndexOf("/"));
        date += "-";
        // Récupérer le jour
        date += profileDate.substring(0, profileDate.indexOf("/"));

        return date;
    }
    
}
