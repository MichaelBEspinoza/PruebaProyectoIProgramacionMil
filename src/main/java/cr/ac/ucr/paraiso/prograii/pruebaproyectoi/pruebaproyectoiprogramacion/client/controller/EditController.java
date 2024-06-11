package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.DocumentData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.jdom2.JDOMException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class EditController {
    @FXML
    private TextField solutionArea;
    @FXML
    private TextField nameArea;
    @FXML
    private TextField problemArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField contextArea;
    @FXML
    private Text state;
    @FXML
    private BorderPane bp;
    @FXML
    private TextField exampleArea;
    @FXML
    private TextField clasifiArea;
    @FXML
    private TextField descripcionArea;
    private DesignPattern currentPattern;
    private final String xmlFilePath = "project.xml"; // Ruta correcta del archivo XML

    public void setCurrentPattern(DesignPattern pattern) {
        this.currentPattern = pattern;
        fillForm();
    }

    private void fillForm() {
        if (currentPattern != null) {
            nameArea.setText(currentPattern.getName());
            problemArea.setText(currentPattern.getProblem());
            solutionArea.setText(currentPattern.getSolution());
            contextArea.setText(currentPattern.getContext());
            exampleArea.setText(currentPattern.getCodeExamples());
            datePicker.setValue(currentPattern.getFechaAgregado());
            clasifiArea.setText(currentPattern.getType());
            descripcionArea.setText(currentPattern.getDescription());
        }
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
    public void goBackOnAction(ActionEvent actionEvent) {
        bp.getChildren().clear();
        loadPage("menu.fxml");
    }

    @FXML
    public void editOnAction(ActionEvent actionEvent) {
        // Obtener los valores actualizados de los campos de texto
        String name = nameArea.getText().trim();
        String problem = problemArea.getText().trim();
        String solution = solutionArea.getText().trim();
        String context = contextArea.getText().trim();
        String example = exampleArea.getText().trim();
        String type = clasifiArea.getText().trim();
        String description = descripcionArea.getText().trim();
        LocalDate date = datePicker.getValue();

        if (name.isEmpty() || problem.isEmpty() || solution.isEmpty() || context.isEmpty() || example.isEmpty() || type.isEmpty() || description.isEmpty() || date == null) {
            showAlert("Error", "Todos los campos deben estar completos.");
            return;
        }

        // Actualizar el patrón actual con los nuevos datos
        currentPattern.setName(name);
        currentPattern.setProblem(problem);
        currentPattern.setSolution(solution);
        currentPattern.setContext(context);
        currentPattern.setCodeExamples(example);
        currentPattern.setFechaAgregado(date != null ? date : LocalDate.now());
        currentPattern.setType(type);
        currentPattern.setDescription(description);

        try {
            // Cargar la lista de patrones desde el archivo XML
            List<DesignPattern> patterns = Utility.parseXMLToDesignPatternList(xmlFilePath);

            // Encontrar el patrón con el ID correspondiente y reemplazarlo
            for (int i = 0; i < patterns.size(); i++) {  // Cambiar i=1 a i=0 para evitar IndexOutOfBoundsException
                if (patterns.get(i).getId().equals(currentPattern.getId())) {
                    patterns.set(i, currentPattern);
                    break;
                }
            }

            // Guardar la lista de patrones actualizada en el archivo XML
            Utility.saveDesignPatternsToXML(patterns, xmlFilePath);

            showAlert("Éxito", "El patrón ha sido actualizado correctamente.");
            loadPage("menu.fxml");

        } catch (Exception e) {
            showAlert("Error", "Ocurrió un error al guardar el patrón: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
