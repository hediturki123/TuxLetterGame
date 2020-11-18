package game;

import env3d.Env;
import env3d.advanced.EnvNode;
import org.lwjgl.input.Keyboard;

/**
 *
 * @author Alexis YVON, Hedi Turki SANEKLI
 */
public class Tux extends EnvNode {
    private Env env;
    private Room room;

    public Tux(Env env, Room room) {
        this.env = env;
        this.room = room;
        setScale(4.0);
        setX(this.room.getWidth()/2);// positionnement au milieu de la largeur de la room
        setY(getScale() * 1.1); // positionnement en hauteur basé sur la taille de Tux
        setZ(this.room.getDepth()/2); // positionnement au milieu de la profondeur de la room
        setTexture("models/tux/tux_special.png");
        setModel("models/tux/tux.obj");
    }
    
    public void deplace() {
        double targetX, targetZ;
        switch (env.getKeyDown()) {
            // Haut
            case Keyboard.KEY_Z: // Z
            case Keyboard.KEY_UP: // Flèche 'haut'
                this.setRotateY(180);
                targetZ = this.getZ() - 1.0;
                if (testeRoomCollision(this.getX(), targetZ))
                    this.setZ(targetZ);
                break;
                
            // Bas
            case Keyboard.KEY_S: // S
            case Keyboard.KEY_DOWN: // Flèche 'bas'
                this.setRotateY(0);
                targetZ = this.getZ() + 1.0;
                if (testeRoomCollision(this.getX(), targetZ))
                    this.setZ(targetZ);
                break;
                
            // Gauche
            case Keyboard.KEY_Q: // Q
            case Keyboard.KEY_LEFT: // Flèche 'gauche'
                this.setRotateY(270);
                targetX = this.getX() - 1.0;
                if (testeRoomCollision(targetX, this.getZ()))
                    this.setX(targetX);
                break;
                
            // Droite
            case Keyboard.KEY_D: // D
            case Keyboard.KEY_RIGHT: // Flèche 'droite'
                this.setRotateY(90);
                targetX = this.getX() + 1.0;
                if (testeRoomCollision(targetX, this.getZ()))
                    this.setX(targetX);
                break;
                
            default:
                break;
        }
    }
    
    private boolean testeRoomCollision(double x, double z) {
            double span = this.getScale();
            return x <= (room.getWidth()-span) && x >= span && z >= span && z <= (room.getDepth()-span);
        }
}
