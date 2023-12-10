import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HiloOnline extends Thread{
    private List<ObjectInputStream> ois;
    private List<ObjectOutputStream> oos;
    private Game g;
    CyclicBarrier barrera;

    public HiloOnline(List<ObjectInputStream> ois, List<ObjectOutputStream> oos, Game game, CyclicBarrier b){
        this.ois = ois;
        this.oos = oos;
        this.g = game;
        this.barrera = b;
    }

    public void run(){
        try{
            barrera.await();
            while (!g.haAcabado()) {
                oos.get(0).writeObject(g);
                oos.get(0).flush();

                g = (Game) ois.get(1).readObject();

                oos.get(1).writeObject(g);
                oos.get(1).flush();

                g = (Game) ois.get(0).readObject();
            }

        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
