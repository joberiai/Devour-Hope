package Partida;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import Juego.Jugador;

public class HiloOnline extends Thread{

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Game g;
    private CyclicBarrier barrera;
    private LinkedList<String> salas;

    public HiloOnline(Socket s, ObjectInputStream ois, ObjectOutputStream oos, Game game, CyclicBarrier b, LinkedList<String> rooms){
        this.socket = s;
        this.ois = ois;
        this.oos = oos;
        this.g = game;
        this.barrera = b;
        this.salas = rooms;
    }

    public void run(){
        try{
            oos.writeObject(this.salas);
            oos.flush();
        	
        	String option = ois.readObject() + "";        	
        	
        	if(option.equals("CREAR")) {
        		this.salas = (LinkedList<String>) ois.readObject();
        		
        	}else if(option.equals("ENTRAR")) {
        		try {
        			oos.writeBytes("--- Juego creado ---\n");
                    oos.flush();

                    Object j = ois.readObject();
                    if (j instanceof Jugador){
                        g.addJugador((Jugador) j);
                    }else{
                        System.out.println("Fallo");
                    }

                    barrera.await();
                    while (!g.haAcabado()) {
                        oos.writeObject(g);
                        oos.flush();

                        g = (Game) ois.readObject();
                    }
        		} catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        	}
        	
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
