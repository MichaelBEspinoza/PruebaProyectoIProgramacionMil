package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.common;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.domain.DesignPattern;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.DocumentData;
import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility.Ordenamiento;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import java.io.IOException;

public class TCPProtocol {

    private static final int ESPERANDO = 0;
    private static final int ESPERANDO_COMANDO = 0;

    private static final String INSERTAR_PATRON = "INSERTAR_PATRON";
    private static final String ELIMINAR_PATRON = "ELIMINAR_PATRON";
    private static final String ACTUALIZAR_PATRON = "ACTUALIZAR_PATRON";
    private static final String BUSCAR_PATRON = "BUSCAR_PATRON";
    private static final String ORDENAR_ID_PATRON = "ORDENAR_ID_PATRON";
    private static final String ORDENAR_TIPO_PATRON = "ORDENAR_TIPO_PATRON";

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

    private static final String documentRoute = data.getRutaDocumento();
    private Document document =  data.getDocumento();
    private Element root = data.getRaiz();
    private int esperado;
    DesignPatternData operations = new DesignPatternData();
    Ordenamiento order = new Ordenamiento();

    public TCPProtocol() throws IOException, JDOMException { esperado = ESPERANDO;}

    DesignPatternData designPattern = new DesignPatternData();

    public String procesarEntrada(String entrada, Object elemento) {
        String salida = null;

        if (esperado == ESPERANDO) {
            salida = "Esperando comando de entrada para ejecutar accion correspondiente...";
            esperado = ESPERANDO_COMANDO;
        } else if (esperado == ESPERANDO_COMANDO) {
            salida = comandoEscogido(entrada, elemento);
            esperado = ESPERANDO;
        }
        return salida;
    }

    public String comandoEscogido(String entrada, Object elemento) {
        String salida = null;
        try {
            salida = switch (entrada) {
                case INSERTAR_PATRON -> operations.addPattern((DesignPattern) elemento);
                case BUSCAR_PATRON -> operations.findPatternByName(elemento);
                case ELIMINAR_PATRON -> operations.deletePattern(elemento);
                case ACTUALIZAR_PATRON -> String.valueOf(operations.updatePattern((DesignPattern) elemento));
                case ORDENAR_ID_PATRON -> String.valueOf(order.ordenarPatronesPorID(root, document, documentRoute));
                case ORDENAR_TIPO_PATRON -> String.valueOf(order.ordenarPatronesPorTipo(root, document, documentRoute));
                default -> salida;
            };
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo XML", e);
        }
        return salida;
    }
}
