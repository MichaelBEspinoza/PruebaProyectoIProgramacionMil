package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {


    //static init
    static {
    }

    public static String format(double value) {
        return new DecimalFormat("###,###,###.##").format(value);
    }

    public static String $format(double value) {
        return new DecimalFormat("$###,###,###.##").format(value);
    }

    public static String show(int[] a, int size) {
        String result = "";
        for (int i = 0; i < size; i++) {
            result += a[i];
        }
        return result;
    }

    public static void fill(int[] a, int bound) {
        for (int i = 0; i < a.length; i++) {
            a[i] = new Random().nextInt(bound);
        }
    }

    public static int getRandom(int bound) {
        return new Random().nextInt(bound) + 1;
    }

    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "Integer":
                Integer int1 = (Integer) a;
                Integer int2 = (Integer) b;
                return int1 < int2 ? -1 : int1 > int2 ? 1 : 0; //0 == equal
            case "String":
                String st1 = (String) a;
                String st2 = (String) b;
                return st1.compareTo(st2) < 0 ? -1 : st1.compareTo(st2) > 0 ? 1 : 0;
            case "Character":
                Character c1 = (Character) a;
                Character c2 = (Character) b;
                return c1.compareTo(c2) < 0 ? -1 : c1.compareTo(c2) > 0 ? 1 : 0;

        }
        return 2; //Unknown
    }

    private static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) return "Integer";
        if (a instanceof String && b instanceof String) return "String";
        if (a instanceof Character && b instanceof Character) return "Character";
        return "Unknown";
    }

    public String getIP() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            return localhost.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("Error al obtener la dirección IP local", e);
        }
    }

    public static List<DesignPattern> parseXMLToDesignPatternList(String filePath) throws JDOMException, IOException {
        List<DesignPattern> patrones = new ArrayList<>();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(filePath);

        if (!xmlFile.exists()) {
            System.out.println("El archivo XML no existe: " + filePath);
            return patrones;
        }

        synchronized (Utility.class) {
            try {
                Document document = builder.build(xmlFile);
                Element rootNode = document.getRootElement();
                List<Element> list = rootNode.getChildren("pattern");

                for (Element node : list) {
                    DesignPattern pattern = new DesignPattern();
                    pattern.setId(node.getAttributeValue("id"));
                    pattern.setName(node.getChildText("name"));
                    pattern.setDescription(node.getChildText("description"));
                    pattern.setType(node.getChildText("type"));
                    pattern.setContext(node.getChildText("context"));
                    pattern.setProblem(node.getChildText("problem"));
                    pattern.setSolution(node.getChildText("solution"));
                    pattern.setCodeExamples(node.getChildText("example"));
                    String localDate = node.getChildText("localDate");
                    if (localDate != null) {
                        pattern.setFechaAgregado(LocalDate.parse(localDate));
                    }

                    patrones.add(pattern);
                    System.out.println("Patrón cargado: " + pattern.getName());
                }
            } catch (JDOMException | IOException e) {
                e.printStackTrace();
                throw e;
            }
        }
        return patrones;
    }


    public static void saveDesignPatternsToXML(List<DesignPattern> designPatterns, String filePath) throws IOException, JDOMException {
        Element rootElement = new Element("patterns");

        for (DesignPattern pattern : designPatterns) {
            Element patternElement = new Element("pattern");
            patternElement.setAttribute("id", pattern.getId());
            patternElement.addContent(new Element("name").setText(pattern.getName()));
            patternElement.addContent(new Element("description").setText(pattern.getDescription()));
            patternElement.addContent(new Element("type").setText(pattern.getType()));
            patternElement.addContent(new Element("example").setText(pattern.getCodeExamples()));

            LocalDate fecha = pattern.getFechaAgregado();
            if (fecha == null) {
                fecha = LocalDate.now();
            }
            patternElement.addContent(new Element("localDate").setText(fecha.format(DateTimeFormatter.ISO_LOCAL_DATE)));

            patternElement.addContent(new Element("context").setText(pattern.getContext()));
            patternElement.addContent(new Element("problem").setText(pattern.getProblem()));
            patternElement.addContent(new Element("solution").setText(pattern.getSolution()));

            rootElement.addContent(patternElement);
        }

        Document updatedDocument = new Document(rootElement);

        synchronized (Utility.class) {
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(updatedDocument, fileWriter);
            }
        }
    }


}
