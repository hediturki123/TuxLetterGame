package game;

public class Chronometre {
    private long begin;
    private long end;
    private final int LIMITE;
    private boolean running = false;

    public Chronometre(int limite) {
        this.LIMITE = limite;
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
        return (running ? System.currentTimeMillis() : end) - begin;
    }
 
    public long getMilliseconds() {
        return getTime();
    }
 
    public int getSeconds() {
        return  (int) (getMilliseconds() / 1000.0);
    }
 
    public double getMinutes() {
        return getSeconds() / 60.0;
    }
 
    public double getHours() {
        return getMinutes() / 60.0;
    }
    
    public boolean remainsTime() {
        return getSeconds() < LIMITE;
    }
     
}
