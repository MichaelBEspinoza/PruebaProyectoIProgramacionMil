package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoCliente {

    static Utility util = new Utility();
    public static void main(String[] args) throws UnknownHostException {

        InetAddress inetAddress = InetAddress.getByName(util.getIP());
        Socket echoSocket;
        PrintWriter writer;
        BufferedReader reader;

        try {
            echoSocket = new Socket(util.getIP(), 1234);

            writer = new PrintWriter(echoSocket.getOutputStream(),true);
            reader = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            String entrada = reader.readLine();
            String salida;
            BufferedReader lectorTeclado = new BufferedReader(new InputStreamReader(System.in));
            while((salida = lectorTeclado.readLine()) != null) {
                writer.println(salida);
                entrada = reader.readLine(); // Lee lo que viene del servidor.
            }// End of 'while'.
            reader.close();
            writer.close();
            lectorTeclado.close();
            echoSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }// End of 'catch'.

    }// End of method [main].
}// End of class [EchoCliente].

