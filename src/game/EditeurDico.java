package game;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EditeurDico {
    private Document document;

    public EditeurDico() {}

    public EditeurDico(String fichier) throws ParserConfigurationException, SAXException, IOException {
        lireDOM(fichier);
    }
    
    public void lireDOM(String fichier) throws ParserConfigurationException, SAXException, IOException {

        // Ouverture du fichier dictionnaire dont le nom est passé en entrée.
        File dicoXML = new File(fichier);

        // Création d'une nouvelle instance de BuilderFactory.
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        // Création d'un nouveau builder depuis la factory.
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Récupération du document grâce au builder.
        document = builder.parse(dicoXML);

        // TODO : Vérification de la validité du document ?
    }
    
    public void ecrireDOM(String fichier) throws TransformerConfigurationException, TransformerException, NullPointerException {

        // On vérifie que le document que l'on veut écrire existe bien.
        if (document == null)
            throw new NullPointerException("L'éditeur de dico n'est associé à aucun document.");

        // Préparation du fichier dictionnaire de sortie.
        File file = new File(fichier);

        // Création d'une nouvelle instance de TransformerFactory.
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        // Création d'un nouveau transformateur depuis la factory.
        Transformer transf = transformerFactory.newTransformer();

        // On définit notre document comme étant la source DOM de la transformation.
        DOMSource source = new DOMSource(document);

        // On définit notre fichier comme étant la destination de la transformation.
        StreamResult fileStream = new StreamResult(file);

        // Redéfinition des propriétés du document de sortie pour remise au propre si besoin est.
        transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transf.setOutputProperty(OutputKeys.INDENT, "yes");

        // Écriture du document de sortie.
        transf.transform(source, fileStream);

    }
    
    public void ajouterMot(String mot, int niveau) throws NullPointerException {

        // On vérifie que le document auquel on veut ajouter le mot existe bien.
        if (document == null)
            throw new NullPointerException("L'éditeur de dico n'est associé à aucun document.");

        // Vérification du niveau passé en entrée ; doit être compris entre 1 et 5.
        if ((niveau < 1) || (niveau > 5)) {
            System.err.println("Le niveau d'un mot à ajouter doit être compris entre 1 et 5.");
            return;
        }

        // Récupération de la liste des noeuds "dictionnaire" pour faire une vérification sommaire du format du document.
        NodeList dico = document.getElementsByTagName("dictionnaire");

        // Si le document ne comporte pas exactement un noeud "dictionnaire", on le considère comme invalide.
        if (dico.getLength() != 1) {
            System.err.println("Le document n'est pas un dictionnaire valide.");
            return;
        }

        // On récupère l'élement "dictionnaire" pour pouvoir lui ajouter un mot.
        Element dicoElem = (Element) dico.item(0);

        // On crée le mot à ajouter à partir du paramètre "mot".
        Element motElem = document.createElement("mot");
        motElem.setTextContent(mot);

        // On lui donne son attribut "niveau" à partir du paramètre homonyme.
        motElem.setAttribute("niveau", ""+niveau);

        // On ajoute le mot au dictionnaire, et donc au document.
        dicoElem.appendChild(motElem);

    }
    
    /*public static void main(String[] args) {
        EditeurDico ed;
        try {
            ed = new EditeurDico("src/xml/dico.xml");
            ed.ajouterMot("brouette", 5);
            try {
                ed.ecrireDOM("src/xml/dico2.xml");
            } catch (TransformerException | NullPointerException ex) {
                Logger.getLogger(EditeurDico.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(EditeurDico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }*/

}
