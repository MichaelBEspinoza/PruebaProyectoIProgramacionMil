package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.utility;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server.patterndata.DesignPatternData;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;

public class DocumentData {

    private final String rutaDocumento;
    private final Element raiz;
    private final Document documento;

    DesignPatternData data = new DesignPatternData();

    public DocumentData(String rutaDocumento) throws IOException, JDOMException {
        File file = new File(rutaDocumento);
        if (!file.exists()) {
            this.rutaDocumento = rutaDocumento;
            this.raiz = new Element("patrones");
            this.documento = new Document(raiz);
            data.saveDocument(this.documento, this.rutaDocumento);
        } else {
            SAXBuilder saxBuilder = new SAXBuilder();
            saxBuilder.setIgnoringElementContentWhitespace(true);
            this.documento = saxBuilder.build(new File(rutaDocumento));
            this.raiz = documento.getRootElement();
            this.rutaDocumento = rutaDocumento;
        }
    }

    public Document getDocumento() {
        return documento;
    }

    public Element getRaiz() {
        return raiz;
    }

    public String getRutaDocumento() {
        return rutaDocumento;
    }
}
