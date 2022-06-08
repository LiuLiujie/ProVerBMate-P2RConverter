package nl.utwente.proverb.p2rconvertor.template;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class InstanceTemplate {

    private InstanceTemplate() { }

    public static Element convertToolInstance(Document document, String iName, List<Element> elementList) {
        var e = createNamedIndividual(document, "http://slebok.github.io/proverb/ontology#"+iName);
        e.appendChild(createToolType(document));
        for (var element : elementList){
            e.appendChild(element);
        }
        return e;
    }

    public static Element convertGitHubInstance(Document document, String ghURL) {
        var e = createNamedIndividual(document, ghURL);
        e.appendChild(createRepositoryType(document));
        return e;
    }

    public static Element convertDoiInstance(Document document, String doiURL) {
        var e = createNamedIndividual(document, doiURL);
        e.appendChild(createArticleType(document));
        return e;
    }

    private static Element createNamedIndividual(Document document, String about){
        var e = document.createElement("owl:NamedIndividual");
        e.setAttribute("rdf:about", about);
        return e;
    }

    private static Element createToolType(Document document){
        var e = document.createElement("rdf:type");
        e.setAttribute("rdf:resource", "http://slebok.github.io/proverb/ontology#Tool");
        return e;
    }

    private static Element createRepositoryType(Document document){
        var e = document.createElement("rdf:type");
        e.setAttribute("rdf:resource", "http://slebok.github.io/proverb/ontology#Repository");
        return e;
    }

    private static Element createArticleType(Document document){
        var e = document.createElement("rdf:type");
        e.setAttribute("rdf:resource", "http://slebok.github.io/proverb/ontology#Article");
        return e;
    }
}
