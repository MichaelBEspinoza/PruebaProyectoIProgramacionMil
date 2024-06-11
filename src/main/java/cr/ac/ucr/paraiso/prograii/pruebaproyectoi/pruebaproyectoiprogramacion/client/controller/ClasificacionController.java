package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.common.TCPProtocol;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.DocumentData;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.util.Objects;

public class ClasificacionController {
    @javafx.fxml.FXML
    private TextArea textAreaClassification;
    @javafx.fxml.FXML
    private Text state;
    @javafx.fxml.FXML
    private BorderPane bp;
    Alert alert;

    TCPProtocol protocol = new TCPProtocol();
    DesignPatternData data = new DesignPatternData();
    static DocumentData doc;

    static {
        try {
            doc = new DocumentData("project.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Element documentRoot = doc.getRaiz();

    public ClasificacionController() throws IOException, JDOMException {
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
    public void ConnectionOnAction(ActionEvent actionEvent) {
        loadPage("conexion.fxml");
    }

    @javafx.fxml.FXML
    public void goBackOnAction(ActionEvent actionEvent) {loadPage("menu.fxml");
    }

    @javafx.fxml.FXML
    public void EditOnAction(ActionEvent actionEvent) {
        ChoiceDialog<String> check = new ChoiceDialog<>("Por ID", "Por tipo");
        check.setTitle("Filtrado de patrones");
        check.setHeaderText("Seleccione una opción");
        check.setContentText("Los patrones se mostrarán clasificados según su opción elegida.");
        check.showAndWait();

        if (Objects.equals(check.getSelectedItem(), "Por ID")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(protocol.comandoEscogido("ORDENAR_ID_PATRON",null));
            alert.showAndWait();
            textAreaClassification.setText(data.showContents(documentRoot));
        }else if (Objects.equals(check.getSelectedItem(), "Por tipo")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(protocol.comandoEscogido("ORDENAR_TIPO_PATRON",null));
            alert.showAndWait();
            textAreaClassification.setText(data.showContents(documentRoot));
        }
    }
}