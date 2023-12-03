import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Mesa mesa;
    private  Baraja baraja;
    private List<Jugador> jugadores;
    public static int turno = 1;

    // Game offline
    public Game(String user, int numRobots){
        this.baraja = new Baraja();
        this.mesa = new Mesa();
        this.jugadores = new ArrayList<>();

        this.baraja.barajear();

        jugadores.add(new JugadorReal(user));

        for (int i = 1; i <= numRobots; i++) {
            jugadores.add(new JugadorRobot("Bot " + i));
            for (int j = 1; j <= 7; j++) {
                this.jugadores.get(i).getMano().add(baraja.sacarCarta());
            }
        }

        this.mesa.getCartasColocadas().add(baraja.sacarCarta());
    }

    // Game online
    public Game(int numRobots, int numReales) {
        this.baraja = new Baraja();
        this.mesa = new Mesa();
        this.jugadores = new ArrayList<>();

        this.baraja.barajear();

        for (int i = 1; i <= numRobots; i++) {
            jugadores.add(new JugadorRobot("Bot " + i));
            for (int j = 1; j <= 7; j++) {
                this.jugadores.get(i-1).getMano().add(baraja.sacarCarta());
            }
        }

        this.mesa.getCartasColocadas().add(baraja.sacarCarta());
    }

    // Getters / Setters
    public Baraja getBaraja() {
        return baraja;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public static int getTurno() {
        return turno;
    }

    // Metodos
    public boolean iniciar() {
        if (this.jugadores.size() <= 4) {
            Collections.shuffle(this.jugadores);

            return true;
        }

        return false;
    }

    public boolean haAcabado() {
        for (int i = 0; i < this.jugadores.size(); i ++){
            if (this.jugadores.get(i).haGanado()){
                return true;
            }
        }

        return false;
    }

    public Jugador ganador() {
        if (this.haAcabado()) {
            for(int i = 0; i < this.jugadores.size(); i ++){
                if (this.jugadores.get(i).haGanado()){
                    return this.jugadores.get(i);
                }
            }
        }

        return null;
    }

    public boolean robar(int numJug){
        for(int i = 0, n =  this.jugadores.get(numJug).getMano().size(); i < n; i ++){
            Carta c1 = this.jugadores.get(numJug).getMano().get(i);
            Carta c2 = this.mesa.obtenerUltimaCarta();

            if (c1.getNum() == c2.getNum() || c1.getColor() == c2.getColor() || c1.getNum() == 13){
                return false;
            }
        }

        return true;
    }

    public boolean jugarCarta(Carta c) {
        if(this.mesa.obtenerUltimaCarta().getColor() == c.getColor() ||
                this.mesa.obtenerUltimaCarta().getNum() == c.getNum()){
            this.mesa.getCartasColocadas().add(c);

            if (c.getNum() == 9){

            }else if (c.getNum() == 10){

            }else if (c.getNum() == 11){

            }else if (c.getNum() == 12){

            }

            return true;
        }

        if (c.getNum() == 13){

        }

        return false;
    }


    @Override
    public String toString() {
        return "Game:" + '\n' +
                "Baraja = " + this.baraja.toString() + '\n' +
                "Mesa = " + this.mesa.toString() + '\n' +
                "Jugadores = " + this.jugadores.toString();
    }

}
