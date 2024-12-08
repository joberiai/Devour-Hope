package Partida;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.CyclicBarrier;

public class AtenderPeticion extends Thread {
    private Socket socket;
    private Game g;
    private CyclicBarrier barrier;
    private LinkedList<String> salas; 

    public AtenderPeticion(Socket s, Game game, CyclicBarrier b, LinkedList<String> rooms) {
        this.socket = s;
        this.g = game;
        this.barrier = b;
        this.salas = rooms;
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

                    HiloOnline hiloOnline = new HiloOnline(socket, ois, oos, g, barrier, salas);
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
        }
    }
}