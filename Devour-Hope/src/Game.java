import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Game  implements Serializable {
    private List<Carta> cartasColocadas;
    private Baraja baraja;
    private List<Jugador> jugadores;
    private int turno = 1;

    // Game online
    public Game(){
        this.baraja = new Baraja();
        cartasColocadas = new ArrayList<>();
        this.jugadores = new ArrayList<>();

        this.baraja.barajear();

        this.cartasColocadas.add(baraja.sacarCarta());
    }

    // Game offline
    public Game(int numRobots, int numReales) {
        this.baraja = new Baraja();
        cartasColocadas = new ArrayList<>();
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

    public int getTurno() {
        return this.turno;
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

            if (c1.getNum() == c2.getNum() || c1.getColor().equals(c2.getColor()) || c1.getNum() == 13){
                return false;
            }
        }

        return true;
    }

    public void addJugador(Jugador j){
        for (int i = 1; i <= 7; i ++) {
            j.getMano().add(baraja.sacarCarta());
        }

        this.jugadores.add(j);
    }

    public boolean jugarCarta(Jugador j, Carta c) {
        if(this.obtenerUltimaCarta().getColor() == c.getColor() ||
                this.obtenerUltimaCarta().getNum() == c.getNum()){

            this.getCartasColocadas().add(c);
            Scanner scan = new Scanner(System.in);

            if(c.getNum() == 9){
                if(j instanceof JugadorReal){
                    System.out.println("Dime que numero quieres en la carta: ");
                    int i = scan.nextInt();

                    while(i > 12 || i < 1){
                        System.out.println("Dame un numero valido");
                        System.out.println("Dime que numero quieres en la carta: ");
                        i = scan.nextInt();
                    }

                    this.obtenerUltimaCarta().setNum(i);
                }else{
                    this.obtenerUltimaCarta().setNum(7);
                }
            }if(c.getNum() == 10){
                if(j instanceof JugadorReal){
                    System.out.println("Dime que color quieres en la carta: ");
                    String s = scan.nextLine();

                    while(!s.equals("Rojo") && !s.equals("Azul") && !s.equals("Amarillo") && !s.equals("Verde")){
                        System.out.println("Dame un color valido");
                        System.out.println("Dime que color quieres en la carta: ");
                        s = scan.nextLine();
                    }

                    this.obtenerUltimaCarta().setColor(s);
                }else{
                    this.obtenerUltimaCarta().setColor("Rojo");
                }
            }if(c.getNum() == 11){
                if(j instanceof JugadorReal){
                    System.out.println("Escribe 'Si' en caso que quieras una carta comodin: ");
                    String s = scan.nextLine();

                    if(s.equals("Si") || s.equals("si")){
                        j.robarCarta(new CartaComodin(13));
                        j.ordenarMano();
                    }
                }else{
                    j.robarCarta(new CartaComodin(13));
                }
            }if(c.getNum() == 12){
                if(j instanceof JugadorReal){
                    System.out.println("Dime que carta te quieres descartar: ");
                    int i = scan.nextInt();

                    j.getMano().remove(i - 1);
                    j.ordenarMano();
                }else{
                    j.getMano().remove(0);
                }
            }

            return true;
        }

        return false;
    }

    public boolean puedeJugar(Carta c){
        return c.getNum() == obtenerUltimaCarta().getNum() ||
                c.getColor().equals(obtenerUltimaCarta().getColor()) ||
                c.getNum() == 13;
    }

    public Carta obtenerUltimaCarta(){
        if (cartasColocadas.size() == 0){
            return null;
        }
        return cartasColocadas.get(cartasColocadas.size() - 1);
    }

    public void incrementarTurno(){
        this.turno ++;
    }
    @Override
    public String toString() {
        return "Game:" + '\n' +
                "Baraja = " + this.baraja.toString() + '\n' +
                "Mesa = " + this.obtenerUltimaCarta().toString() + '\n' +
                "Jugadores = " + this.jugadores.toString();
    }

}
