package game;

import env3d.advanced.EnvNode;

/**
 *
 * @author Alexis YVON, Hedi TURKI SANEKLI
 */
public class Lettre extends EnvNode {
    private char lettre;
    private double x;
    private double z;

    public Lettre(char l, double x, double z) {
        lettre = l;
        this.x = x;
        this.z = z;
        setScale(2.0);
        setX(x);
        setZ(z);
        setY(getScale());
        setRotateX(Math.random()*360);
        setRotateY(Math.random()*360);
        setRotateZ(Math.random()*360);
        
        switch (l) {
            case ' ':
            case '-':
                setTexture("models/letter/cube.png");
                break;
            case 'à':
            case 'á':
            case 'â':
            case 'ã':
            case 'ä':
            case 'å':
                setTexture("models/letter/a.png");
                break;
            case 'ç':
            case '¢':
                setTexture("models/letter/c.png");
                break;
            case 'è':
            case 'é':
            case 'ê':
            case 'ë':
                setTexture("models/letter/e.png");
                break;
            case 'ì':
            case 'í':
            case 'î':
            case 'ï':
                setTexture("models/letter/i.png");
                break;
            case 'ò':
            case 'ó':
            case 'ô':
            case 'ö':
            case 'ø':
                setTexture("models/letter/o.png");
                break;
            case 'š':
            case 'þ':
            case 'ß':
                setTexture("models/letter/s.png");
                break;
            case 'ù':
            case 'ú':
            case 'û':
            case 'ü':
                setTexture("models/letter/u.png");
                break;
            case 'ý':
            case 'ÿ':
                setTexture("models/letter/y.png");
                break;
            default:
                setTexture("models/letter/" + l + ".png");
                break;
        }
        setModel("models/letter/cube.obj"); 
    }
    
    public char getLettre() {
        return lettre;
    }
    
    @Override
    public double getX() {
        return x;
    }
    
    @Override
    public double getZ() {
        return z;
    }
    
    @Override
    public void setX(double x) {
        this.x = x;
    }
    
    @Override
    public void setZ(double z) {
        this.z = z;
    }
    
    
    
}
