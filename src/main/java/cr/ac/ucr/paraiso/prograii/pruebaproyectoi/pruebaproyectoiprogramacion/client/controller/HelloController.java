package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HelloController {

    @FXML
    private BorderPane bp;
    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    public void detailOnAction(ActionEvent actionEvent) {loadPage("buscar.fxml");
    }

    @FXML
    public void patternsOnAction(ActionEvent actionEvent) {loadPage("patrones.fxml");
    }

    @FXML
    public void helpOnAction(ActionEvent actionEvent) {loadPage("Ayuda.fxml");
    }

    @FXML
    public void classificationOnAction(ActionEvent actionEvent) {loadPage("clasificacion.fxml");
    }

    @FXML
    public void conectionOnAction(ActionEvent actionEvent) {loadPage("conexion.fxml");
    }

}
