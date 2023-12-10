import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class HiloOnline extends Thread{

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Game g;
    CyclicBarrier barrera;

    public HiloOnline(Socket s, ObjectInputStream ois, ObjectOutputStream oos, Game game, CyclicBarrier b){
        this.socket = s;
        this.ois = ois;
        this.oos = oos;
        this.g = game;
        this.barrera = b;
    }

    public void run(){
        try{
            oos.writeBytes("--- Juego creado ---\n");
            oos.flush();

            Object j = ois.readObject();
            if (j instanceof Jugador){
                g.addJugador((Jugador) j);
            }else{
                System.out.println("Fallo");
            }

            barrera.await();


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
