package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.PatternNotFoundException;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.DocumentData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.FromXMLElement;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.ToXMLElement;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.Utility;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DesignPatternData {

    FromXMLElement fromElement = new FromXMLElement();
    ToXMLElement toElement = new ToXMLElement();

    public DesignPatternData() throws IOException, JDOMException {
    }

    public String generateIdentificador() {
        return UUID.randomUUID().toString();
    }

    static DocumentData data;

    static {
        try {
            data = new DocumentData("project.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JDOMException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String FILE_PATH = data.getRutaDocumento();

    public List<DesignPattern> loadPatterns() throws Exception {
        File xmlFile = new File(FILE_PATH);
        if (!xmlFile.exists()) {
            xmlFile.createNewFile();
            Element rootElement = new Element("patterns");
            Document document = new Document(rootElement);
            saveDocument(document, FILE_PATH);
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(xmlFile);
        Element rootElement = document.getRootElement();
        return rootElement.getChildren("pattern").stream()
                .map(FromXMLElement::fromXMLElement)
                .collect(Collectors.toList());
    }

    public void savePatterns(List<DesignPattern> patterns) throws IOException {
        Element rootElement = new Element("patterns");
        Document document = new Document(rootElement);
        for (DesignPattern pattern : patterns) {
            rootElement.addContent(toElement.toXMLElement(pattern));
        }
        saveDocument(document, FILE_PATH);
    }

    //Método para guardar los documentos del patron
    public void saveDocument(Document document, String filePath) throws IOException {
        XMLOutputter xmlOutput = new XMLOutputter();
        xmlOutput.setFormat(Format.getPrettyFormat());
        xmlOutput.output(document, new FileWriter(filePath));
    }

    public String addPattern(DesignPattern pattern) throws Exception {
        List<DesignPattern> patterns = loadPatterns();
        patterns.add(pattern);

        if (patterns.contains(pattern)){
            savePatterns(patterns);
            return "El patrón ha sido añadido exitosamente";
        }

        return "Ha ocurrido un error, favor intente de nuevo";
    }

    public String findPatternByName(Object name) throws Exception {
        List<DesignPattern> patterns = loadPatterns();
        StringBuilder builder = new StringBuilder();

        for (DesignPattern designPattern : patterns) {
            if (Utility.compare(designPattern.getName(), name) == 0) {
                builder.append("El patron fue encontrado\n")
                        .append("Name: ").append(designPattern.getName()).append("\n")
                        .append("Contexto: ").append(designPattern.getContext()).append("\n")
                        .append("Problema: ").append(designPattern.getProblem()).append("\n")
                        .append("Solucion: ").append(designPattern.getSolution()).append("\n")
                        .append("Ejemplos: ").append(designPattern.getCodeExamples()).append("\n")
                        .append("Clasificacion: ").append(designPattern.getType()).append("\n");
                return builder.toString();
            }
        }
        return "El patron no fue encontrado";
    }

    public boolean updatePattern(DesignPattern updatedPattern) throws Exception {
        List<DesignPattern> patterns = loadPatterns();


        for (int i = 0; i < patterns.size(); i++) {
            if (patterns.get(i).getId().equals(updatedPattern.getId())) {
                patterns.set(i, updatedPattern);
                break;
            }
        }
        savePatterns(patterns);
        return true;
    }

    public String deletePattern(Object name) throws Exception { //REVISAR SI BORRA BIEN EL PATRON
        List<DesignPattern> patterns = loadPatterns();

        for (DesignPattern pattern1 : patterns) {

            if (pattern1.getName().equals(name)) {

                patterns.removeIf(pattern -> pattern.getName().equals(name));
                savePatterns(patterns);

                if (!patterns.contains(name)) {
                    return "El objeto ha sido eliminado exitosamente";
                }
            }

        }
        return "El objeto no ha sido encontrado";
    }

    public DesignPattern searchAndReturn(Object id, Element raiz) throws IOException, JDOMException, PatternNotFoundException {
        List<Element> patrones = raiz.getChildren("patron");
        for (Element patron : patrones) {
            String idPatron = patron.getAttributeValue("idDelPatron");
            if (idPatron.equals(id)) {
                String nombre = patron.getChildText("nombre");
                String descripcion = patron.getChildText("descripcion");
                String tipo = patron.getChildText("tipo");
                String codigo = patron.getChildText("ejemploDeCodigo");
                LocalDate fechaAgregado = LocalDate.parse(patron.getChildText("fechaAgregado"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String contexto = patron.getChildText("contexto");
                String problema = patron.getChildText("problema");
                String solution = patron.getChildText("solucion");
                return new DesignPattern(idPatron, nombre, descripcion, tipo, codigo, contexto, problema, solution, fechaAgregado);
            }
        }
        throw new PatternNotFoundException();
    }

    public List<Element> listAll(Element raiz) {
        List<Element> listaCompleta = new ArrayList<>(), elementos = raiz.getChildren();

        for (Element elemento : elementos) {
            Element copiaElemento = new Element(elemento.getName());

            for (Attribute atributo : elemento.getAttributes())
                copiaElemento.setAttribute(atributo.getName(), atributo.getValue());

            for (Content contenido : elemento.getContent())
                copiaElemento.addContent(contenido.clone());

            listaCompleta.add(copiaElemento);
        }
        return listaCompleta;
    }

    public boolean exists(DesignPattern DP, Element raiz) {
        List<Element> patrones = raiz.getChildren("patron");
        if (!patrones.isEmpty())
            for (Element patron : patrones) {
                String comparar = String.valueOf(patron.getAttributeValue("idDelPatron"));
                if (comparar.equals(DP.getId())) return true;
            }
        return false;
    }

    public String showContents(Element raiz) {
        StringBuilder contenidos = new StringBuilder();
        for (Element elemento : raiz.getChildren("pattern")) {
            contenidos.append("\n<pattern");

            // Manejo del atributo 'id'
            Attribute idAttribute = elemento.getAttribute("id");
            if (idAttribute != null) {
                contenidos.append(" id=\"").append(idAttribute.getValue()).append("\"");
            }

            contenidos.append(">\n");

            // Manejo de los elementos hijos
            appendElementContent(elemento, "name", contenidos);
            appendElementContent(elemento, "description", contenidos);
            appendElementContent(elemento, "type", contenidos);
            appendElementContent(elemento, "example", contenidos);
            appendElementContent(elemento, "localDate", contenidos);
            appendElementContent(elemento, "context", contenidos);
            appendElementContent(elemento, "problem", contenidos);
            appendElementContent(elemento, "solution", contenidos);

            contenidos.append("</pattern>\n");
        }
        return contenidos.toString();
    }

    private void appendElementContent(Element parent, String childName, StringBuilder builder) {
        String text = parent.getChildText(childName);
        if (text != null && !text.isEmpty()) {
            builder.append("  <").append(childName).append(">")
                    .append(text)
                    .append("</").append(childName).append(">\n");
        }
    }
}