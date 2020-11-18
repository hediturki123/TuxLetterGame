package game;

import env3d.advanced.EnvNode;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Lettre extends EnvNode {
    private char lettre;

    public Lettre(char l, double x, double z) {
        lettre = l;
        setScale(2.0);
        setX(x);
        setZ(z);
        setY(getScale());
        setRotateX(Math.random()*360);
        setRotateY(Math.random()*360);
        setRotateZ(Math.random()*360);
        setTexture("models/letter/"+this.lettre+".png");
        setModel("models/letter/cube.obj");
    }
    
    
}
