package Juego;
import java.io.Serializable;

public class JugadorRobot extends Jugador implements Serializable {
    // Constructores
    public JugadorRobot(){

    }
    public JugadorRobot(String n) {
        super(n);
    }

    @Override
    public Carta elegirCarta(int i) {
        return this.getMano().get(i);
    }

    @Override
    public void robarCarta(Carta c) {
        this.getMano().add(c);
    }
}
