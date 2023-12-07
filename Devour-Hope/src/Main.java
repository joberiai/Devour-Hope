import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jugadores max 4");
        System.out.print("Numero de Jugadores humanos: ");
        int jhumanos = scanner.nextInt();

        System.out.print("Numero de Jugadores bots: ");
        int jbots = scanner.nextInt();

        Game g = new Game(jbots, jhumanos);
        g.iniciar();

        for (int i = 0, n = g.getJugadores().size(); i < n; i++) {
            if (!g.haAcabado()) {
                Jugador j = g.getJugadores().get(i);
                System.out.println("Turno " + g.getTurno());
                System.out.println("Turno de " + j.getUsuario());
                System.out.println("Última carta: " + g.obtenerUltimaCarta());

                if (g.robar(i)) {
                    j.robarCarta(g.getBaraja().sacarCarta());

                    if(j instanceof JugadorReal){
                        System.out.println("No tienes carta para jugar (Escribe 'Robar' y pulsa intro)");
                        scanner.next();
                    }

                    System.out.println(j.getUsuario() + " roba carta");
                    j.ordenarMano();
                } else {
                    g.jugarCarta(j.elegirCarta());
                }

                if (i == g.getJugadores().size() - 1) {
                    i = -1;
                    Game.turno++;
                }

                if(g.getBaraja().numCartas() == 0){
                    g.setBaraja(new Baraja());
                }
            } else {
                i = 4;
            }
        }

        if (g.haAcabado()) {
            System.out.println("Ha ganado " + g.ganador().getUsuario() + "!");
        }
    }
}