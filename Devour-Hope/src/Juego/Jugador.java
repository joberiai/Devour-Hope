package Juego;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class Jugador implements Serializable {
    private String usuario;
    private ArrayList<Carta> mano;

    // Constructores
    public Jugador(){

    }
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
    public void setMano(ArrayList<Carta> lMano){
        this.mano = lMano;
    }

    // Funciones
    public int numCartas() {
        return mano.size();
    }

    public boolean haGanado() {
        return mano.size() == 0;
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
        String infoJugador = "Mano: ";

        for(int i = 1; i <= mano.size(); i ++){
            infoJugador = infoJugador + "[" + i + "]" + this.getMano().get(i-1).toString() + " / ";
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

    public abstract Carta elegirCarta(int i);

    public abstract void robarCarta(Carta c);

}
