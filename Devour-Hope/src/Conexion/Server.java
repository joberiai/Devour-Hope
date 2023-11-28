package Conexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(61000)){
            while (true){
                try(Socket s = server.accept()){



                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
