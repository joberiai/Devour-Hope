package Juego;
import java.io.Serializable;

public class Carta implements Serializable {
    private int num;
    private String color;

    public Carta(){

    }
    public Carta(int n, String c){
        this.num = n;
        this.color = c;
    }

    // Numero
    public void setNum(int n){
        this.num = n;
    }

    public int getNum(){
        return this.num;
    }

    // Color
    public void setColor(String c){
        this.color = c;
    }

    public String getColor(){
        return this.color;
    }

    // Mostrar
    public String toString() {
        // ---- Cartas especiales ----
        // Cartas 9
        if (this.getNum() == 9){
            if (this.getColor().equals("Rojo")){
                return "Remus Lupin (9 Rojo)";
            }else if(this.getColor().equals("Azul")){
                return "Quirinus Quirrell (9 Azul)";
            }else if(this.getColor().equals("Verde")){
                return "Draco Malfoy (9 Verde)";
            }else if(this.getColor().equals("Amarillo")){
                return "Cedric Diggory (9 Amarillo)";
            }
        // Cartas 10
        }else if (this.getNum() == 10){
            if (this.getColor().equals("Rojo")){
                return "Sirius Black (10 Rojo)";
            }else if(this.getColor().equals("Azul")){
                return "Myrtle Warren (10 Azul)";
            }else if(this.getColor().equals("Verde")){
                return "Bellatrix Lestrange (10 Verde)";
            }else if(this.getColor().equals("Amarillo")){
                return "Theseus Scamander (10 Amarillo)";
            }
        // Cartas 11
        }else if (this.getNum() == 11){
            if (this.getColor().equals("Rojo")){
                return "Albus Dumbledore (11 Rojo)";
            }else if(this.getColor().equals("Azul")){
                return "Gilderoy Lockhart (11 Azul)";
            }else if(this.getColor().equals("Verde")){
                return "Severus Snape (11 Verde)";
            }else if(this.getColor().equals("Amarillo")){
                return "Nymphadora Tonks (11 Amarillo)";
            }
        // Cartas 12
        }else if (this.getNum() == 12){
            if (this.getColor().equals("Rojo")){
                return "Harry Potter (12 Rojo)";
            }else if(this.getColor().equals("Azul")){
                return "Luna Lovegood (12 Azul)";
            }else if(this.getColor().equals("Verde")){
                return "Voldemort (12 Verde)";
            }else if(this.getColor().equals("Amarillo")){
                return "Newt Scamander (12 Amarillo)";
            }
        }

        // ---- Cartas normales ----
        return "El " + this.getNum() + " " + this.getColor();
    }
}
