import java.util.*;

public class Mesa {
    private  List<Carta> cartasColocadas;

    public Mesa() {
        this.cartasColocadas = new ArrayList<Carta>();
    }

    public List<Carta> getCartasColocadas() {
        return cartasColocadas;
    }

    public Carta obtenerUltimaCarta(){
        if (this.cartasColocadas.size() == 0){
            return null;
        }
        return this.cartasColocadas.get(this.cartasColocadas.size() - 1);
    }

    public boolean poderJugar(Carta c){
        return this.obtenerUltimaCarta().getNum() == c.getNum() ||
               this.obtenerUltimaCarta().getColor() == c.getColor() ||
               c.getNum() == 13;
    }
    @Override
    public String toString() {
        return "Mesa: " + "\n" +
                "Ultima carta = " + this.obtenerUltimaCarta();
    }
}
