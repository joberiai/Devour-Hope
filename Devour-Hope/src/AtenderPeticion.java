import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class AtenderPeticion extends Thread {
    private Socket socket;
    private Game g;
    private CyclicBarrier barrier;
    private List<ObjectInputStream> arrayOIS;
    private List<ObjectOutputStream> arrayOOS;

    public AtenderPeticion(Socket s, Game game, CyclicBarrier b, List<ObjectInputStream> aI, List<ObjectOutputStream> aO) {
        this.socket = s;
        this.g = game;
        this.barrier = b;
        this.arrayOIS = aI;
        this.arrayOOS = aO;
    }

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());

            int n = ois.readInt();

            switch (n) {
                case 1:
                    // Offline

                    break;
                case 2:
                    // Offline

                    break;
                case 3:
                    // Online
                    oos.reset();
                    arrayOIS.add(ois);
                    arrayOOS.add(oos);

                    oos.writeBytes("--- Juego creado ---\n");
                    oos.flush();

                    Object j = ois.readObject();
                    if (j instanceof Jugador){
                        g.addJugador((Jugador) j);
                    }else{
                        System.out.println("Fallo");
                    }

                    HiloOnline hiloOnline = new HiloOnline(arrayOIS, arrayOOS, g, barrier);
                    hiloOnline.start();
                    break;
                case 4:
                    // Ranking
                    HiloRanking hiloRanking = new HiloRanking(socket, ois, oos);
                    hiloRanking.start();

                    break;
                case 5:

                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
