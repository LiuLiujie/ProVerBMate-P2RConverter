package nl.utwente.proverb.p2rconvertor.tools;

import lombok.SneakyThrows;
import nl.utwente.proverb.p2rconvertor.template.PropertyTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class XMLUtil {

    private XMLUtil() { }

    @SneakyThrows
    public static void formatXMLFile(File file) {

        var document = getDocument(file);
        writeToFile(document, file);
    }

    @SneakyThrows
    public static void writeToFile(Document document,  File file){
        var transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        var source = new DOMSource(document);
        var result = new StreamResult(file);
        transformer.transform(source, result);
    }

    @SneakyThrows
    public static Document getDocument(File file){
        var documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        var documentBuilder = documentBuilderFactory.newDocumentBuilder();
        return documentBuilder.parse(new InputSource(new InputStreamReader(new FileInputStream(file))));
    }

    public static void main(String[] args) {
        try {
            File file = new File("ontology.owl");
            Document document  = getDocument(file);
            var root = document.getDocumentElement();
            var e = PropertyTemplate.convertName(document, "123");
            root.appendChild(e);
            writeToFile(document, file);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
