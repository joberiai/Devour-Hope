package Partida;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Juego.Baraja;
import Juego.Carta;
import Juego.Jugador;
import Juego.JugadorReal;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Identity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Client {
    public static void main(String[] args) {
    	Scanner scan = null;
    	
    	try (Socket s = new Socket("localhost", 60006); // Cambio IP --> Conexion remota
             ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(s.getInputStream())) {
    		
            // Menu inicial
            System.out.println("--- DEVOUR HOPE ---");
            System.out.println("1. Juego contra bots");
            System.out.println("2. Juego offline");
            System.out.println("3. Juego online");
            System.out.println("4. Mostrar puntuación");
            System.out.println("5. Salir");

            scan = new Scanner(System.in);
            int n = scan.nextInt();

            oos.writeInt(n);
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
                	boolean seguir = true; 
                	boolean partidaEmpezada = false;
                	
                	do {
                		System.out.println("¿Qué deseas hacer?");
                		System.out.println("1. Crear una sala");
                		System.out.println("2. Unirse a una sala");
                		System.out.println("3. Salir");
                		
                		int select = scan.nextInt();
                		
                		LinkedList<String> salas = (LinkedList<String>) ois.readObject();
                		
                		switch (select) {
						case 1: 
							System.out.println("Dame la ip:");
							int ipSala = scan.nextInt();
							
							System.out.println("Dame el puerto:");
							int portSala = scan.nextInt();
							
							salas.add(ipSala + ":" + portSala);
							
							oos.writeObject("CREAR");
							oos.writeObject(salas);
							oos.flush();
							
							break;
						case 2:	
							System.out.println("--- SALAS DISPONIBLES ---");
							
							for (String sala : salas) {
								System.out.println(sala);
							}
							
							oos.writeObject("ENTRAR");
							oos.flush();
							
							System.out.println("------------");
							System.out.println("Elige la sala a la que quires acceder");
							int idSala = scan.nextInt();
							
							oos.writeObject(idSala);
							oos.flush();
							
							partidaEmpezada = true;
							seguir = false;
							
							break;
						case 3:
							seguir = false;
							
							break;
						default:
							System.out.println("Elige una opción válida");
						}
                		
                	}while(seguir);
                	
                	if(partidaEmpezada) {
                		 System.out.println(ois.readLine());

                         Scanner scanner = new Scanner(System.in);

                         System.out.println("Dime tu nombre de usuario: ");
                         String u = scanner.nextLine();
                         if(u != null){
                             Jugador jug = new JugadorReal(u);
                             oos.writeObject(jug);
                             oos.flush();
                         }

                         g = (Game) ois.readObject();
                         for (int j = 0; j < g.getJugadores().size(); j++) {

                             if (!g.haAcabado()) {
                                 Jugador newJ = g.getJugadores().get(j);

                                 System.out.println("Turno " + g.getTurno());
                                 System.out.println("Turno de " + newJ.getUsuario());
                                 System.out.println("Última carta: " + g.obtenerUltimaCarta());

                                 if (g.robar(j)) {
                                     if(newJ.getUsuario().equals(u)){
                                         System.out.println("No tienes carta para jugar (Escribe 'Robar' y pulsa intro)");
                                         scanner.reset();
                                         scanner.nextLine();

                                         oos.writeObject(g);
                                         oos.flush();
                                     }

                                     newJ.robarCarta(g.getBaraja().sacarCarta());
                                     System.out.println(newJ.getUsuario() + " roba carta");
                                     newJ.ordenarMano();
                                 } else {
                                     if (newJ.getUsuario().equals(u)){
                                         Carta c = newJ.elegirCarta(0);

                                         while (!g.puedeJugar(c)){
                                             System.out.println("Carta no jugable");

                                             c = newJ.elegirCarta(0);
                                         }

                                         g.jugarCarta(newJ, c);
                                         newJ.getMano().remove(c);

                                         oos.writeObject(g);
                                         oos.flush();
                                     }

                                     System.out.println(newJ.getUsuario() + " juega carta");
                                 }

                                 if (j == g.getJugadores().size() - 1) {
                                     j = -1;
                                     g.incrementarTurno();
                                 }

                                 if(g.getBaraja().numCartas() == 0){
                                     g.setBaraja(new Baraja());
                                 }

                             } else {
                                 j = 4;
                             }

                             g = (Game) ois.readObject();
                         }

                         if (g.haAcabado()) {
                             System.out.println("Ha ganado " + g.ganador().getUsuario() + "!");
                         }
                	}
                   
                    break;
                case 4:
                    
                    try {
                    	Document doc = (Document) ois.readObject();

                        Element root = doc.getDocumentElement();

                        NodeList list = root.getElementsByTagName("ganador");

                        String nombreGanador = "";
                        int maxVictorias = 0;

                        System.out.println("Lista de ganadores:");
                        System.out.println("-------------------");

                        // Recorro los nodos de los ganadores
                        for (i = 0; i < list.getLength(); i++) {
                            Node node = list.item(i);
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) node;

                                String nombre = element.getElementsByTagName("nombre").item(0).getTextContent();
                                int numVictorias = Integer.parseInt(element.getElementsByTagName("numVictorias").item(0).getTextContent());

                                System.out.println(nombre + " - " + numVictorias + " victorias");

                                // Actualizo el ganador máximo
                                if (numVictorias > maxVictorias) {
                                    maxVictorias = numVictorias;
                                    nombreGanador = nombre;
                                }
                            }
                        }

                        // Mostrar el ganador con más victorias
                        System.out.println("-------------------");
                        System.out.println("El ganador con más victorias es: " + nombreGanador);
                        System.out.println("Número de victorias: " + maxVictorias);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
                    break;
                case 5:


                    break;
            }

            oos.close();
            ois.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
			if(scan != null) {
				scan.close();
			}
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

                    g.jugarCarta(j, c);
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