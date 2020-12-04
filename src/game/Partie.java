package game;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class Partie {

    private final String date;
    private final String mot;
    private final int niveau;
    private int trouve;
    private int temps;    
    
    public Partie(String date, String mot, int niveau) {
       this.date = date;
       this.mot = mot;
       this.niveau = niveau;
       trouve = 0;
       temps = 0;
    }
    
    public Partie(Element partieElt) {
        Element motElt = (Element)partieElt.getElementsByTagName("mot").item(0);
        NodeList tempsNodeList = partieElt.getElementsByTagName("temps");
        mot = motElt.getTextContent();
        niveau = Integer.parseInt(motElt.getAttribute("niveau"));
        
        if (tempsNodeList.getLength() != 1) {
            temps = 0;
        } else {
            temps = (int)Double.parseDouble(tempsNodeList.item(0).getTextContent());
        } 
        date = partieElt.getAttribute("date");
        String trouveString = partieElt.getAttribute("trouvé");
        trouve = Integer.parseInt(trouveString.substring(0, trouveString.length() - 1));
      
    }
    
    Element getPartie(Document doc) {
        Element partieElt = doc.createElement("partie");
        partieElt.setAttribute("date", date);
        partieElt.setAttribute("trouvé", trouve + "%");
        Element tempsElt = doc.createElement("temps");
        tempsElt.setTextContent(String.format("%d.0", temps));
        partieElt.appendChild(tempsElt);
        Element motElt = doc.createElement("mot");
        motElt.setTextContent(mot);
        motElt.setAttribute("niveau", niveau + "");
        partieElt.appendChild(motElt);
        
        return partieElt;
    }
    
    public void setTrouve(int nbreLettresRestantes) {
       trouve = (int)(((mot.length() - nbreLettresRestantes) / (double)mot.length())*100);
    }
    
    public void setTemps(int temps) {
        this.temps = temps;
    }
    
    public int getNiveau() {
        return niveau;
    }
    
    public String getMot() {
        return mot;
    }
    
    @Override
    public String toString() {
        return "  Date : " + date + 
                "\n  Mot :" + mot + 
                "\n  Niveau : " + niveau + 
                "\n  Temps : " + temps + 
                "\n  Trouvé : " + trouve + " %";
    }
    
}
