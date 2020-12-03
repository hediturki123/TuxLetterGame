package game;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Profil {

    private String nom;
    private String avatar;
    private Date dateNaissance;
    private ArrayList<Partie> parties;
    public Document _doc;

    public Profil(String nom, Date dateNaissance) {
        this.nom = nom;
        parties = new ArrayList<Partie>();
    }
    
    Profil(String nomFichier) {
        _doc = fromXML(nomFichier);
        nom = _doc.getElementsByTagName("nom").item(0).getTextContent();
        avatar = _doc.getElementsByTagName("avatar").item(0).getTextContent();
        String xmldate = _doc.getElementsByTagName("anniversaire").item(0).getTextContent();
        dateNaissance = new Date(Profil.xmlDateToProfileDate(xmldate));
        NodeList partiesNodeList = _doc.getElementsByTagName("partie");
        for (int i = 0; i < partiesNodeList.getLength(); i++) {
            parties.add(new Partie((Element)partiesNodeList.item(i)));
        }
    }
    
    // Cree un DOM à partir d'un fichier XML
    public Document fromXML(String nomFichier) {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(nomFichier));
        } catch (Exception ex) {
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
            transf.transform(source, file);

        } catch (Exception ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterPartie(Partie p) {
        parties.add(p);
    }
    
    public int getDernierNiveau() {
        return parties.get(parties.size() - 1).getNiveau();
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void sauvegarder(String filename) { 
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element profilElt = doc.createElement("profil");
            profilElt.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://myGame/tux");
            profilElt.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            profilElt.setAttribute("xsi:schemaLocation", "http://myGame/tux profil.xsd");
            ///TODO
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
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
