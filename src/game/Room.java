package game;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class Room {

    private int depth, height, width;
    private String textureBottom, textureNorth, textureEast, textureWest, textureTop, textureSouth;
    Document doc;
    
    public Room() {
        depth = 100;
        width = 100;
        height = 60;
        textureBottom = "textures/floor/AncientFlooring.png";
        textureNorth = "textures/skybox/fantasy/north.png";
        textureEast = "textures/skybox/fantasy/east.png";
        textureWest = "textures/skybox/fantasy/west.png";
    }
    
    public Room(String filename) {
        doc = fromXML(filename);
        height = Integer.parseInt(doc.getElementsByTagName("height").item(0).getTextContent());
        depth = Integer.parseInt(doc.getElementsByTagName("depth").item(0).getTextContent());
        width = Integer.parseInt(doc.getElementsByTagName("width").item(0).getTextContent());
        textureBottom = doc.getElementsByTagName("textureBottom").item(0).getTextContent();
        textureNorth = doc.getElementsByTagName("textureNorth").item(0).getTextContent();
        textureEast = doc.getElementsByTagName("textureEast").item(0).getTextContent();
        textureWest = doc.getElementsByTagName("textureWest").item(0).getTextContent(); 
    }

    public int getDepth() {
        return depth;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getTextureBottom() {
        return textureBottom;
    }

    public String getTextureNorth() {
        return textureNorth;
    }

    public String getTextureEast() {
        return textureEast;
    }

    public String getTextureWest() {
        return textureWest;
    }

    public String getTextureTop() {
        return textureTop;
    }

    public String getTextureSouth() {
        return textureSouth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTextureBottom(String textureBottom) {
        this.textureBottom = textureBottom;
    }

    public void setTextureNorth(String textureNorth) {
        this.textureNorth = textureNorth;
    }

    public void setTextureEast(String textureEast) {
        this.textureEast = textureEast;
    }

    public void setTextureWest(String textureWest) {
        this.textureWest = textureWest;
    }

    public void setTextureTop(String textureTop) {
        this.textureTop = textureTop;
    }

    public void setTextureSouth(String textureSouth) {
        this.textureSouth = textureSouth;
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

}
