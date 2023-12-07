import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game  implements Serializable {
    private static List<Carta> cartasColocadas;
    private Baraja baraja;
    private List<Jugador> jugadores;
    public static int turno = 1;

    // Game offline
    public Game(String user, int numRobots){
        this.baraja = new Baraja();
        this.cartasColocadas = new ArrayList<>();
        this.jugadores = new ArrayList<>();

        this.baraja.barajear();

        jugadores.add(new JugadorReal(user));

        for (int i = 1; i <= numRobots; i++) {
            jugadores.add(new JugadorRobot("Bot " + i));
            for (int j = 1; j <= 7; j++) {
                this.jugadores.get(i).getMano().add(baraja.sacarCarta());
            }
        }

        this.cartasColocadas.add(baraja.sacarCarta());
    }

    // Game online
    public Game(int numRobots, int numReales) {
        this.baraja = new Baraja();
        this.cartasColocadas = new ArrayList<>();
        this.jugadores = new ArrayList<>();

        this.baraja.barajear();

        for (int i = 1; i <= numRobots; i++) {
            jugadores.add(new JugadorRobot("Bot " + i));
            for (int j = 1; j <= 7; j++) {
                this.jugadores.get(i-1).getMano().add(baraja.sacarCarta());
            }
        }

        for (int i = 1; i <= numReales; i++) {
            System.out.println("Dime el nombre de usuario:");
            Scanner scan = new Scanner(System.in);
            String user = scan.nextLine();

            jugadores.add(new JugadorReal(user));
            for (int j = 1; j <= 7; j++) {
                this.jugadores.get(this.jugadores.size() - 1).getMano().add(baraja.sacarCarta());
            }

            this.jugadores.get(i-1).ordenarMano();
        }

        this.cartasColocadas.add(baraja.sacarCarta());
    }

    // Getters / Setters
    public Baraja getBaraja() {
        return baraja;
    }

    public void setBaraja(Baraja b) {
        this.baraja = b;
    }

    public List<Carta> getCartasColocadas() {
        return cartasColocadas;
    }

    public void setCartasColocadas(List<Carta> list) {
        cartasColocadas = list;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> list){
        jugadores = list;
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
            Carta c2 = this.obtenerUltimaCarta();

            if (c1.getNum() == c2.getNum() || c1.getColor() == c2.getColor() || c1.getNum() == 13){
                return false;
            }
        }

        return true;
    }

    public boolean jugarCarta(Carta c) {
        if(this.obtenerUltimaCarta().getColor() == c.getColor() ||
                this.obtenerUltimaCarta().getNum() == c.getNum()){
            this.getCartasColocadas().add(c);

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

    public static boolean puedeJugar(Carta c){
        return c.getNum() == obtenerUltimaCarta().getNum() ||
                c.getColor() == obtenerUltimaCarta().getColor() ||
                c.getNum() == 13;
    }

    public static Carta obtenerUltimaCarta(){
        if (cartasColocadas.size() == 0){
            return null;
        }
        return cartasColocadas.get(cartasColocadas.size() - 1);
    }

    @Override
    public String toString() {
        return "Game:" + '\n' +
                "Baraja = " + this.baraja.toString() + '\n' +
                "Mesa = " + this.obtenerUltimaCarta().toString() + '\n' +
                "Jugadores = " + this.jugadores.toString();
    }

}
