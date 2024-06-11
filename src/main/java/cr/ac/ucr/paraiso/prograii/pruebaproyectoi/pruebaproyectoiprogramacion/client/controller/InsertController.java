package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.common.TCPProtocol;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.GeneratePatternCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;

import java.io.IOException;

public class InsertController {

    @FXML
    private TextField nameArea;
    @FXML
    private TextField contextArea;
    @FXML
    private TextField exampleArea;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField problemArea;
    @FXML
    private TextField solutionArea;
    @FXML
    private DatePicker datePicker;

    private DesignPattern newPattern;
    private TCPProtocol protocol;
    private Alert alert;
    private GeneratePatternCode generatePatternCode;

    @FXML
    private TextField descriptionArea;
    @FXML
    private ComboBox cb_classifications;

    ObservableList<String> list = FXCollections.observableArrayList("CREACIONAL", "ESTRUCTURAL", "COMPORTAMIENTO");

    public void initialize(){
        this.cb_classifications.setItems(list);
    }

    public InsertController() throws IOException, JDOMException {
        this.newPattern = new DesignPattern();
        this.protocol = new TCPProtocol();
        this.alert = new Alert(Alert.AlertType.ERROR);
        this.generatePatternCode = new GeneratePatternCode();
    }

    private void loadPage(String page) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        try {
            this.bp.setCenter(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void ConnectionOnAction(ActionEvent actionEvent) {
        loadPage("conexion.fxml");
    }

    @FXML
    public void goBackOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("menu.fxml");
    }

    @FXML
    public void insertOnAction(ActionEvent actionEvent) {
        if (!nameArea.getText().isEmpty() && !(cb_classifications.getValue() == null) &&
                !contextArea.getText().isEmpty() && !exampleArea.getText().isEmpty() &&
                !problemArea.getText().isEmpty() && !solutionArea.getText().isEmpty() &&
                datePicker.getValue() != null) {

            protocol.comandoEscogido("INSERTAR_PATRON",newPattern = new DesignPattern(
                    generatePatternCode.generatePatternCode(),
                    nameArea.getText(),
                    descriptionArea.getText(),
                    cb_classifications.getValue().toString(),
                    exampleArea.getText(),
                    contextArea.getText(),
                    problemArea.getText(),
                    solutionArea.getText(),
                    datePicker.getValue()
            ));
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Patrón insertado exitosamente.");
            alert.showAndWait();
        } else {
            alert.setContentText("ERROR\nUno o más campos no contienen los parámetros necesarios.");
            alert.showAndWait();
        }
    }
}