import java.io.Serializable;
import java.util.Scanner;

public class JugadorReal extends Jugador implements Serializable {

    public JugadorReal(String n) {
        super(n);
    }

    public Carta elegirCarta(){
        System.out.println(this.toString());

        System.out.println("Elige una carta para echar: ");
        Scanner scan = new Scanner(System.in);

        int i = scan.nextInt();

        while (i <= 0){
            System.out.println("No es valido, escribe de nuevo");
            System.out.println("Elige una carta para echar: ");
            i = scan.nextInt();
        }

        Carta c = this.getMano().get(i - 1);

        while (!Game.puedeJugar(c)){
            System.out.println("Carta no jugable");
            System.out.println(this.toString());
            System.out.println("Elige una carta para echar: ");

            i = scan.nextInt();
            c = this.getMano().get(i - 1);
        }

        this.getMano().remove(c);
        return c;
    }

    @Override
    public void robarCarta(Carta c) {
        System.out.println("No tienes una carta para jugar");
        this.getMano().add(c);
    }

}
