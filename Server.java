import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Server {
    public static void main(String[] args) {
        
        String txtPath = "donnee.txt";

        Fenetre win = new Fenetre(800, 300, txtPath);

        // try (ServerSocket serverSocket = new ServerSocket(3000)) {
        //     System.out.println("Server ecoute sur le port : " + 3000);
        //     while (true) {
        //         Socket socket = serverSocket.accept();
        //         new ProxyHandler(socket).start();
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }

}
