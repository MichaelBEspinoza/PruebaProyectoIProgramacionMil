package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Document;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ordenamiento {

    DesignPatternData dpd = new DesignPatternData();

    public Ordenamiento() throws IOException, JDOMException {
    }

    public String ordenarPatronesPorID(Element raiz, Document documento, String rutaDocumento) throws IOException, JDOMException {
        List<Element> patrones = raiz.getChildren("patron");

        // Ordena los patrones por ID usando un comparador (ahora lambda).
        patrones.sort((p1, p2) -> {
            String id1 = p1.getAttributeValue("idDelPatron");
            String id2 = p2.getAttributeValue("idDelPatron");
            return id1.compareTo(id2);
        });// End of lambda [sort].
        dpd.saveDocument(documento, rutaDocumento);
        return "Los patrones se han ordenado exitosamente por su nombre";
    }// End of method [ordenarPatronesPorID].

    public boolean ordenarPatronesPorTipo(Element raiz, Document documento, String rutaDocumento) throws IOException, JDOMException {
        List<Element> patrones = raiz.getChildren("patron");

        // Ordena los patrones por tipo usando un comparador (ahora lambda).
        patrones.sort((p1, p2) -> {
            String tipo1 = p1.getChildText("tipo");
            String tipo2 = p2.getChildText("tipo");
            return tipo1.compareToIgnoreCase(tipo2);
        });// End of lambda [sort].
        dpd.saveDocument(documento,rutaDocumento);
        return true;
    }// End of method [ordenarPatronesPorTipo].


}
