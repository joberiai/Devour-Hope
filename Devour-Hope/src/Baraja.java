import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja implements Serializable {
    private ArrayList<Carta> cartas;
    public Baraja(){
        this.cartas = new ArrayList<>();

        for(int i = 1; i <= 12; i ++){
            Carta cartaRoja = new Carta(i,"Rojo");
            Carta cartaAzul = new Carta(i,"Azul");
            Carta cartaVerde = new Carta(i,"Verde");
            Carta carataAmarilla = new Carta(i,"Amarillo");

            cartas.add(cartaRoja);
            cartas.add(cartaAzul);
            cartas.add(cartaVerde);
            cartas.add(carataAmarilla);
        }

        for (int cant = 0; cant < 2; cant ++){
            CartaComodin snitch = new CartaComodin(13);

            cartas.add(snitch);
        }

    }

    // Getters/Setters
    public List<Carta> getCartas(){
        return this.cartas;
    }
    public void setCartas(ArrayList<Carta> listCartas){
        this.cartas = listCartas;
    }

    // Metodos
    public Carta sacarCarta(){
        Carta carta = cartas.get(cartas.size() - 1);
        cartas.remove(cartas.size() - 1);

        // Añadir comprobacion para no haber suficientes cartas

        return carta;
    }
    public int numCartas(){
        return cartas.size();
    }
    public void barajear(){
        Collections.shuffle(cartas);
    }
    public boolean estaVacia(){
        return cartas.isEmpty();
    }

    @Override
    public String toString() {
        String acumCartas = "Baraja completa: ";

        for(int i = 0; i < cartas.size(); i ++){
            acumCartas = acumCartas + "\n" + cartas.get(i).toString();
        }

        return acumCartas;
    }
}
