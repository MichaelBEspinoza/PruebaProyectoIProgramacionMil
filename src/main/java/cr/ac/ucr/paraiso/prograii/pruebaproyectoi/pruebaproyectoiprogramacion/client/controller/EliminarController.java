package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.common.TCPProtocol;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.PatternNotFoundException;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.DocumentData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;

import java.io.IOException;

public class EliminarController { //todo 100% funcional

    @FXML
    private BorderPane bp;

    private TCPProtocol protocol;
    private DesignPatternData data;
    private DocumentData doc;
    private Alert alert;
    @FXML
    private TextField nameArea;

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void initialize() {
        try {
            protocol = new TCPProtocol();
            data = new DesignPatternData();
            doc = new DocumentData("project.xml");
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goBackOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("patrones.fxml");
    }

    @FXML
    public void eliminarOnAction(ActionEvent actionEvent) throws PatternNotFoundException, IOException, JDOMException {
        if (!nameArea.getText().isEmpty() && !data.listAll(doc.getRaiz()).isEmpty()) {
            try {
                String result = protocol.comandoEscogido("ELIMINAR_PATRON", nameArea.getText());
                if (result.equals("El objeto ha sido eliminado exitosamente")) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(result);
                    alert.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(result);
                    alert.show();
                }
            } catch (Exception e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ocurrió un error al eliminar el patrón.");
                alert.show();
            }
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("ERROR\nEl campo de ID está vacío y/o no hay patrones a retornar.");
            alert.show();
        }
    }
}
