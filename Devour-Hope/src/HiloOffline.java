import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloOffline extends Thread{
    private Socket socket;

    public HiloOffline(Socket s){
        this.socket = s;
    }

    public void run(){
        try(ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(this.socket.getOutputStream())){

            Game g = (Game) ois.readObject();
            System.out.println(g.toString());

        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
