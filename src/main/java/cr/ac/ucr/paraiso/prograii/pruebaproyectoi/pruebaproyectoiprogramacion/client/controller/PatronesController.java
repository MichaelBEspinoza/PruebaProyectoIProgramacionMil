package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.HelloApplication;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.Utility;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import org.jdom2.JDOMException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PatronesController {
    @FXML
    private Text estado;
    @FXML
    private BorderPane bp;

    Utility util = new Utility();
    private final String xmlFilePath = "project.xml"; // Ruta correcta del archivo XML

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
    public void addOnAction(ActionEvent actionEvent) {
        loadPage("Insertar.fxml");
    }

    @FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Modificar Datos");
        dialog.setHeaderText("Ingrese el nombre a modificar");
        dialog.setContentText("Nombre:");

        Optional<String> result = dialog.showAndWait();
        List<DesignPattern> patrones = new ArrayList<>();

        try {
            patrones = Utility.parseXMLToDesignPatternList(xmlFilePath);
            System.out.println("Patrones cargados desde el XML:");
            for (DesignPattern pattern : patrones) {
                System.out.println("Nombre: " + pattern.getName());
            }
        } catch (JDOMException e) {
            showAlert("Error de XML", "Error al parsear el archivo XML: " + e.getMessage());
            return;
        } catch (IOException e) {
            showAlert("Error de I/O", "Error al leer el archivo: " + e.getMessage());
            return;
        }

        if (result.isPresent()) {
            String nombre = result.get().trim();
            System.out.println("Buscando el nombre: " + nombre);  // Añadido para depuración
            DesignPattern foundPattern = null;

            for (DesignPattern pattern : patrones) {
                System.out.println("Comparando con: " + pattern.getName().trim());  // Añadido para depuración
                if (pattern.getName().trim().equalsIgnoreCase(nombre)) {  // Usar equalsIgnoreCase para evitar problemas de mayúsculas/minúsculas
                    foundPattern = pattern;
                    break;
                }
            }

            if (foundPattern != null) {
                showAlert("Resultado", "El nombre '" + nombre + "' fue encontrado en la lista de patrones, abriendo ventana de edición.");
                openEditWindow(foundPattern);
            } else {
                showAlert("Resultado", "El nombre '" + nombre + "' no fue encontrado en la lista de patrones.");
            }
        }
    }

    private void openEditWindow(DesignPattern pattern) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("editarPatron.fxml"));
            BorderPane pane = loader.load();
            EditController controller = loader.getController();
            controller.setCurrentPattern(pattern);
            bp.setCenter(pane);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir la ventana de edición: " + e.getMessage());
        }
    }




    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    public void eliminarOnAction(ActionEvent actionEvent) {
        loadPage("eliminar.fxml");
    }

}
