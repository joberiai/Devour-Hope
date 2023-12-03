import java.util.Scanner;

public class JugadorReal extends Jugador{

    public JugadorReal(String n) {
        super(n);
    }

    public Carta elegirCarta(){
        System.out.println("Elige una carta para echar: ");
        Scanner scan = new Scanner(System.in);

        int i = scan.nextInt();
        Carta c = this.getMano().get(i - 1);

        while (!this.poderjugar(c)){
            System.out.println("Carta no jugable");
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
