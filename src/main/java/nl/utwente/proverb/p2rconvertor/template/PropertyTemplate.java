package nl.utwente.proverb.p2rconvertor.template;

public class PropertyTemplate {

    public static String convertName(String name) {
        return "<ontology:name xml:lang=\"en\">"+name+"</ontology:name>"+"\n";
    }

    public static String convertAbstract(String abs){
        return "<ontology:abstract xml:lang=\"en\">"+abs+"</ontology:abstract>"+"\n";
    }

    public static String convertPaper(String doi){
        return "<ontology:relatedpaper rdf:resource=\""+doi+"\"/>"+"\n";
    }

    public static String convertContributor(String contributor) {
        return "<ontology:contributor rdf:resource=\""+contributor+"\"/>"+"\n";
    }

    public static String convertRepository(String repo) {
        return "<ontology:repository rdf:resource=\""+repo+"\"/>"+"\n";
    }

    public static String convertCategory(String category) {
        return "<ontology:category rdf:resource=\"http://slebok.github.io/proverb/ontology#"+category+"\"/>"+"\n";
    }
}
