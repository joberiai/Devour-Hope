public class JugadorRobot extends Jugador{

    public JugadorRobot(String n) {
        super(n);
    }

    @Override
    public Carta elegirCarta() {
        Carta c = null;

        for (int i = 0; i <= this.numCartas(); i++) {
            c = this.getMano().get(i);

            if (this.poderjugar(c)) {
                System.out.println("Juego carta");
                this.getMano().remove(c);

                return c;
            }
        }

        return c;
    }
}
