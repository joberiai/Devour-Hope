import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloOnline extends Thread{

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Game g;

    public HiloOnline(Socket s, ObjectInputStream ois, ObjectOutputStream oos, Game game){
        this.socket = s;
        this.ois = ois;
        this.oos = oos;
        this.g = game;
    }

    public void run(){
        try{
            oos.writeObject("--- Juego creado ---\n");
            oos.flush();

            while (g.getJugadores().size() < 2){
                Object j = ois.readObject();
                if (j instanceof Jugador){
                    g.addJugador((Jugador) j);
                }else{
                    System.out.println("Fallo");
                }
            }

            while (!g.haAcabado()) {
                oos.writeObject(g);
                oos.flush();

                g = (Game) ois.readObject();
            }

        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
