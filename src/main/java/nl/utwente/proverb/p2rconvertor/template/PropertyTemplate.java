package nl.utwente.proverb.p2rconvertor.template;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class PropertyTemplate {

    private PropertyTemplate() { }

    public static Element convertName(Document document, String name) {
        var e = document.createElement("ontology:name");
        e.setAttribute("xml:lang", "en");
        e.setTextContent(name);
        return e;
    }

    public static Element convertAbstract(Document document, String abs){
        var e = document.createElement("ontology:abstract");
        e.setAttribute("xml:lang", "en");
        e.setTextContent(abs);
        return e;
    }

    public static Element convertPaper(Document document, String doi){
        var e = document.createElement("ontology:relatedpaper");
        e.setAttribute("rdf:resource", doi);
        return e;
    }

    public static Element convertRepository(Document document, String repo) {
        var e = document.createElement("ontology:repository");
        e.setAttribute("rdf:resource", repo);
        return e;
    }

    public static Element convertCategory(Document document, String category) {
        var e = document.createElement("ontology:category");
        e.setAttribute("rdf:resource", "http://slebok.github.io/proverb/ontology#"+category);
        return e;
    }
}
