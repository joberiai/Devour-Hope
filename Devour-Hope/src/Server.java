import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(55555)){
            ExecutorService pool = Executors.newCachedThreadPool();

            while (true){
                try{

                    // Offline
                    Socket s = server.accept();
                    HiloOffline hilo = new HiloOffline(s);

                    pool.execute(hilo);
                    //s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
