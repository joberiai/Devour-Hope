import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket s = new Socket("localhost", 55555);
             ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(s.getInputStream())){

            // Menu inicial
            System.out.println("--- DEVOUR HOPE ---");
            System.out.println("1. Juego contra bots");
            System.out.println("2. Juego offline");
            System.out.println("3. Juego online");
            System.out.println("4. Salir");

            Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();


            Game g;

            switch (n){
                case 1:
                    // Poner nombre usuario




                    break;
                case 2:
                    // Poner cantidad de jugadores
                    g = new Game(2, 1);
                    oos.writeObject(g);
                    oos.flush();

                    break;
                case 3:
                    // Poner cantidad de jugadores
                    System.out.println("Dime el nombre de usuario: ");




                    g = (Game) ois.readObject();
                    System.out.println("Última carta: " + g.obtenerUltimaCarta());

                    break;
                case 4:


                    break;

            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
