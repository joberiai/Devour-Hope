import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloOnline extends Thread{

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public HiloOnline(Socket s, ObjectInputStream ois, ObjectOutputStream oos){
        this.socket = s;
        this.ois = ois;
        this.oos = oos;
    }

    public void run(){
        try{
            Game g = new Game();
            oos.writeObject("--- Juego creado ---\n");
            oos.flush();

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
        }
    }
}
