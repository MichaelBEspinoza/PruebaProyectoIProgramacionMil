package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.DesignPattern;
import org.jdom2.Element;

import java.time.LocalDate;

public class FromXMLElement {
    public static DesignPattern fromXMLElement(Element element) {
        String id = element.getAttributeValue("id");
        String name = element.getChildText("name");
        String description = element.getChildText("description");
        String type = element.getChildText("type");
        String example = element.getChildText("example");
        LocalDate date = LocalDate.parse(element.getChildText("localDate"));
        String context = element.getChildText("context");
        String problem = element.getChildText("problem");
        String solution = element.getChildText("solution");

        return new DesignPattern(id, name, description, type, example, context, problem, solution, date);
    }
}
