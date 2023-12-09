import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
             ObjectInputStream ois = new ObjectInputStream(s.getInputStream())) {

            // Menu inicial
            System.out.println("--- DEVOUR HOPE ---");
            System.out.println("1. Juego contra bots");
            System.out.println("2. Juego offline");
            System.out.println("3. Juego online");
            System.out.println("4. Mostrar puntuación");
            System.out.println("5. Salir");

            Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();

            oos.writeObject(n);
            oos.flush();

            Game g = null;

            switch (n) {
                case 1:
                    // Usuario contra bots
                    juegosOffline(g, scan, 2, 1);

                    break;
                case 2:
                    // Player vs Player en el mismo PC
                    System.out.println("Dime el numero de jugadores");
                    int i = scan.nextInt();

                    juegosOffline(g, scan, 0, i);

                    break;
                case 3:
                    // Crear jugador para empezar partida
                    Object obj = ois.readObject();
                    System.out.println((String) obj);

                    Scanner scanner = new Scanner(System.in);

                    System.out.println("Dime tu nombre de usuario: ");
                    String u = scanner.nextLine();
                    if(u != null){
                        Jugador jug = new JugadorReal(u);
                        oos.writeObject(jug);
                        oos.flush();
                    }



                    break;
                case 4:
                    Document doc = (Document) ois.readObject();

                    Element root = doc.getDocumentElement();

                    NodeList list = root.getElementsByTagName("ganador");

                    System.out.println("--- RANKING DE VICTORIAS ---");

                    for (int j = 0; j < list.getLength(); j++) {
                        Element ganador = (Element) list.item(j);

                        System.out.println("Jugador: " + ganador.getElementsByTagName("nombre").item(0).getTextContent() +
                                "   Victorias: " + ganador.getElementsByTagName("numVictorias").item(0).getTextContent());
                    }

                    break;
                case 5:


                    break;
            }

            oos.close();
            ois.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void juegosOffline(Game g, Scanner scanner, int jugBots, int jugReal) {
        g = new Game(jugBots, jugReal);
        g.iniciar();

        for (int i = 0, n = g.getJugadores().size(); i < n; i++) {
            if (!g.haAcabado()) {
                Jugador j = g.getJugadores().get(i);
                System.out.println("Turno " + g.getTurno());
                System.out.println("Turno de " + j.getUsuario());
                System.out.println("Última carta: " + g.obtenerUltimaCarta());

                if (g.robar(i)) {
                    j.robarCarta(g.getBaraja().sacarCarta());

                    if(j instanceof JugadorReal){
                        System.out.println("No tienes carta para jugar (Escribe 'Robar' y pulsa intro)");
                        scanner.next();
                    }

                    System.out.println(j.getUsuario() + " roba carta");
                    j.ordenarMano();
                } else {
                    int num = 0;
                    Carta c = j.elegirCarta(num);

                    while (!g.puedeJugar(c)){
                        if(j instanceof JugadorReal){
                            System.out.println("Carta no jugable");
                        }

                        num ++;
                        c = j.elegirCarta(num);
                    }

                    g.jugarCarta(c);
                    j.getMano().remove(c);
                }

                if (i == g.getJugadores().size() - 1) {
                    i = -1;
                    g.incrementarTurno();
                }

                if(g.getBaraja().numCartas() == 0){
                    g.setBaraja(new Baraja());
                }
            } else {
                i = 4;
            }
        }

        if (g.haAcabado()) {
            System.out.println("Ha ganado " + g.ganador().getUsuario() + "!");
        }
    }

}