package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (Socket socket = new ServerSocket(PORT).accept()) {
            System.out.println("Servidor escuchando en el puerto " + PORT);
            while (true) {
                Socket clientSocket = socket;
                pool.execute(new ServerThread(clientSocket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}