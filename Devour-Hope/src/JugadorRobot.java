import java.io.Serializable;

public class JugadorRobot extends Jugador implements Serializable {
    // Constructores
    public JugadorRobot(){

    }
    public JugadorRobot(String n) {
        super(n);
    }

    @Override
    public Carta elegirCarta() {
        Carta c = null;

        for (int i = 0, n = this.numCartas(); i < n; i++) {
            c = this.getMano().get(i);


            System.out.println("Juego carta");

            return c;

        }

        return c;
    }

    @Override
    public void robarCarta(Carta c) {
        this.getMano().add(c);
    }
}
