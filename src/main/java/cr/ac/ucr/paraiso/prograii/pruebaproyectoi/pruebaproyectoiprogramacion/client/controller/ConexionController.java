package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.Utility;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ConexionController implements Initializable { /**TODO FUNCIONAL**/

    private Socket socket;
    @FXML
    private Text estado;
    @FXML
    private BorderPane bp;

    Utility util = new Utility();

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        estado.setText((socket == null || socket.isClosed()) ? "Desconectado" : "Conectado");
    }

    @FXML
    public void disconnectOnAction(ActionEvent actionEvent) {
        if (socket != null && !socket.isClosed()) {
            try {
                socket.close();
                estado.setText("Desconectado");
            } catch (IOException e) {
                estado.setText("Error al desconectar: " + e.getMessage());
            }
        } else {
            estado.setText("No conectado a ningún servidor");
        }
    }

    @FXML
    public void connectOnAction(ActionEvent actionEvent) {
        try {
            socket = new Socket(util.getIP(), 1234);  // Asegúrate de que la dirección y el puerto sean correctos
            estado.setText("Conectado");
            loadPage("menu.fxml");
        } catch (IOException e) {
            estado.setText("Error al conectar: " + e.getMessage());
        }
    }

    @FXML
    public void exitOnAction(ActionEvent actionEvent) {
        System.exit(1);
    }

}
