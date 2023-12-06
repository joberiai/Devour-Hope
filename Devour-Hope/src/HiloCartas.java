import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloCartas extends Thread{
    private Socket socket;

    public HiloCartas(Socket s){
        this.socket = s;
    }

    public void run(){
        try(ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream())){

            Game game = new Game(ois.readLine(), 2);
            oos.writeObject(game);
            oos.flush();


        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
