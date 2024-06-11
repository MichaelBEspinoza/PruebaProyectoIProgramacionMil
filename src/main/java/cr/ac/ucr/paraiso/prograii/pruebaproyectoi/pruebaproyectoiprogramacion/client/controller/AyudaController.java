package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class AyudaController { //todo funcional
    @javafx.fxml.FXML
    private BorderPane bp;
    @FXML
    private Text estado;
    @FXML
    private TextArea descripcion_ayuda;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize(){

        descripcion_ayuda.setVisible(true);
        descripcion_ayuda.setText("Patrones: Permite acceder a las funciones relacionadas con la gestión de patrones.\n\n" +
                "Ayuda: Proporciona asistencia o información adicional sobre el uso de la aplicación.\n\n" +
                "Clasificaciones y patrones: Permite buscar y ver clasificaciones y patrones específicos.\n\n" +
                "Detalles del patrón: Muestra información detallada sobre un patrón seleccionado.\n\n" +
                "Funciones de conexión: Accede a las opciones de conexión.");
    }
    @FXML
    public void volverOnAction(ActionEvent actionEvent) {

        bp.getChildren().clear();
        loadPage("menu.fxml");
    }


}
