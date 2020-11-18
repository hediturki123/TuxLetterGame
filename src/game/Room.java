package game;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Room {

    private int depth, height, width;
    private String textureBottom, textureNorth, textureEast, textureWest, textureTop, textureSouth;

    public Room() {
        depth = 100;
        width = 100;
        height = 60;
        textureBottom = "textures/floor/AncientFlooring.png";
        textureNorth = "textures/skybox/fantasy/north.png";
        textureEast = "textures/skybox/fantasy/east.png";
        textureWest = "textures/skybox/fantasy/west.png";
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

}
