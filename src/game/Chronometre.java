package game;

public class Chronometre {
    private long begin;
    private long end;
    private int limite;
    private boolean running = false;

    public Chronometre(int limite) {
        this.limite = limite;
    }
    
    public void start(){
        begin = System.currentTimeMillis();
        running = true;
    }
 
    public void stop(){
        end = System.currentTimeMillis();
        running = false;
    }
 
    public long getTime() {
       long elapsed;
        if (running) {
             elapsed = (System.currentTimeMillis() - begin);
        }
        else {
            elapsed = (end - begin);
        }
        return elapsed;
    }
 
    public long getMilliseconds() {
        return getTime();
    }
 
    public int getSeconds() {
        return  (int) (getMilliseconds() / 1000.0);
    }
 
    public double getMinutes() {
        return (getSeconds()) / 60.0;
    }
 
    public double getHours() {
        return (getMinutes()) / 60.0;
    }
    
    /**
    * Method to know if it remains time.
    */
    public boolean remainsTime() {
        return getSeconds() <= limite;
    }
     
}
