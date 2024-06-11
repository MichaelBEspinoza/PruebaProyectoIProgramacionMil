module cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.jdom2;
    requires java.xml;  // Añadir acceso al módulo java.xml

    exports cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller;

    opens cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion to javafx.fxml;
    opens cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.client.controller to javafx.fxml;
    exports cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion;
}
