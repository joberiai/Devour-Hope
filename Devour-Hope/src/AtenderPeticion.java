import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AtenderPeticion extends Thread {
    private Socket socket;
    private Game g;

    public AtenderPeticion(Socket s, Game game) {
        this.socket = s;
        this.g = game;
    }

    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream());

            int n = (Integer) ois.readObject();

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

                    HiloOnline hiloOnline = new HiloOnline(socket, ois, oos, g);
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
            e.printStackTrace();
        }
    }
}
