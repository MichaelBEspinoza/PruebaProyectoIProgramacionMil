package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.server;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.common.TCPProtocol;
import org.jdom2.JDOMException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        super("PatternServerThread");
        this.socket = socket;
    }

    public void run() {
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            TCPProtocol protocolo = new TCPProtocol();
            String salida = protocolo.procesarEntrada(null,null);
            writer.println(salida);

            String entrada;
            while ((entrada = reader.readLine()) != null) {
                salida = protocolo.procesarEntrada(entrada,null);
                writer.println(salida);
                if (salida.contains("<status>¡Chao!</status>"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
