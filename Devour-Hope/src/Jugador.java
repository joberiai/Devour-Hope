import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class Jugador {
    private String usuario;
    private ArrayList<Carta> mano;

    public Jugador(String n) {
        this.usuario = n;
        this.mano  = new ArrayList<>();
    }

    // Usuario
    public void setUsuario(String u){
        this.usuario = u;
    }
    public String getUsuario() {
        return this.usuario;
    }

    // Mano
    public ArrayList<Carta> getMano() {
        return this.mano;
    }

    // Funciones
    public int numCartas() {
        return mano.size();
    }

    public boolean haGanado() {
        return mano.size() == 0;
    }

    public boolean poderjugar(Carta c) {
        // return Mesa.poderJugar(c);
        return true;
    }

    public boolean tieneCarta(Carta carta) {
        for (int i = 0; i < this.mano.size(); i ++){
            if (this.mano.get(i).getNum() == carta.getNum() && this.mano.get(i).getColor() == carta.getColor()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String infoJugador = "Jugador: " + usuario + '\n' +
                             "Mano: ";
        for(int i = 1; i <= mano.size(); i ++){
            infoJugador = infoJugador + "(" + i + ")" + this.getMano().get(i-1) + ",";
        }
        return infoJugador;
    }
    public void ordenarMano() {
        Collections.sort(this.getMano(), new Comparator<Carta>() {
            @Override
            public int compare(Carta o1, Carta o2) {
                return Integer.compare(o1.getNum(), o2.getNum());
            }
        });
    }

    // Métodos abstractos

    public abstract Carta elegirCarta();

    public abstract void robarCarta(Carta c);

}
