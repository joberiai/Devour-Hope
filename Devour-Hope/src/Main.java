import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jugadores max 4");
        System.out.print("Numero de Jugadores humanos: ");
        int jhumanos = scanner.nextInt();

        System.out.print("Numero de Jugadores bots: ");
        int jbots = scanner.nextInt();

        Game g = new Game(jhumanos, jbots);
        g.iniciar();

        for (int i = 0, n = g.getJugadores().size(); i < n; i++) {
            if (!g.haAcabado()) {
                Jugador j = g.getJugadores().get(i);
                System.out.println("Turno " + g.getTurno());
                System.out.println("Turno de " + j.getUsuario());
                System.out.println(g.getMesa().toString());

                if (g.robar(i)) {
                    j.robarCarta(g.getBaraja().sacarCarta());
                    System.out.println(j.getUsuario() + " roba carta");
                } else {
                    g.jugarCarta(j.elegirCarta());
                }

                if (i == g.getJugadores().size()) {
                    i = 0;
                    Game.turno++;
                }
            } else {
                i = 4;
            }
        }

        if (g.haAcabado()) {
            System.out.println(g.ganador().toString());
        }
    }
}