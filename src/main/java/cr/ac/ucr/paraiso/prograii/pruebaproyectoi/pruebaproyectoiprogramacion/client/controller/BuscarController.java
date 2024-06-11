package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.common.TCPProtocol;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.PatternNotFoundException;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.DocumentData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;

import java.io.IOException;

public class BuscarController {

    Alert alert;
    TCPProtocol protocol = new TCPProtocol();

    @javafx.fxml.FXML
    private TextArea textArea;
    @javafx.fxml.FXML
    private BorderPane bp;

    DesignPatternData data = new DesignPatternData();
    @javafx.fxml.FXML
    private TextField nameArea;

    public BuscarController() throws IOException, JDOMException {
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void goBackOnAction(ActionEvent actionEvent) {
        loadPage("menu.fxml");
    }

    @javafx.fxml.FXML
    public void searchOnAction(ActionEvent actionEvent) throws PatternNotFoundException, IOException, JDOMException {
        if (!nameArea.getText().isEmpty()) {
            try {
                String result = protocol.comandoEscogido("BUSCAR_PATRON", nameArea.getText());
                if (!result.equals("El patron no fue encontrado")) {
                    textArea.setText(result);
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("El patrón no está registrado.");
                    alert.show();
                }
            } catch (Exception e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ocurrió un error al buscar el patrón.");
                alert.show();
            }
        } else {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("ERROR\nEl campo de ID está vacío y/o no hay patrones a retornar.");
            alert.show();
        }
    }
}
