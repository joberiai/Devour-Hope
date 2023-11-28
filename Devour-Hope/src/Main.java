import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
        Baraja b = new Baraja();
        b.barajear();
        System.out.println(b.toString());
        */

        Scanner scanner= new Scanner(System.in);
        System.out.println("Jugadores max 4");
        System.out.print("Numero de Jugadores humanos: ");
        int jhumanos = scanner.nextInt();
        System.out.print("Numero de Jugadores bots: ");
        int jbots = scanner.nextInt();
        Game g = new Game(jhumanos, jbots);
        g.iniciar();
        //sonidos();
        int i = 0;
        while (i < g.getJugadores().size() && !g.haAcabado()) {
            System.out.println(Game.getTurno());
            System.out.println(g.getMesa().toString());
            if (!g.robar(i)) {
                g.getJugadores().get(i).robarCarta(g.getBaraja().sacarCarta());
                System.out.println(g.getJugadores().get(i).getUsuario() + " roba carta" +
                        g.getJugadores().get(i).getMano().get(g.getJugadores().get(i).getMano().size() - 1));
            } else {
                g.jugarCarta(g.getJugadores().get(i).elegirCarta());
            }
            i++;
            if (i == g.getJugadores().size()) {
                i = 0;
                //Game.turno++;
            }
        }
        if(g.haAcabado()){
            System.out.println(g.ganador().toString());
        }
    }
}