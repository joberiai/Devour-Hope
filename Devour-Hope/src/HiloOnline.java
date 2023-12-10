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
    private CyclicBarrier barrera;

    public HiloOnline(Socket s, ObjectInputStream ois, ObjectOutputStream oos, CyclicBarrier b){
        this.socket = s;
        this.ois = ois;
        this.oos = oos;
        this.barrera = b;
    }

    public void run(){
        try{
            Game g = new Game();
            oos.writeObject("--- Juego creado ---\n");
            oos.flush();

            barrera.await();

            Object j = ois.readObject();
            if (j instanceof Jugador){
                g.addJugador((Jugador) j);
            }else{
                System.out.println("Fallo");
            }

            while (!g.haAcabado()) {
                oos.writeObject(g);
                oos.flush();

                g = (Game) ois.readObject();
            }

        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
