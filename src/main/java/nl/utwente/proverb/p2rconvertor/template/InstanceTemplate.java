package nl.utwente.proverb.p2rconvertor.template;

public class InstanceTemplate {

    public static String convertToolInstance(String iName, String properties) {
        StringBuilder builder = new StringBuilder();
        builder.append("<owl:NamedIndividual rdf:about=\"http://slebok.github.io/proverb/ontology#").append(iName).append("\">").append("\n");
        builder.append("<rdf:type rdf:resource=\"http://slebok.github.io/proverb/ontology#Tool\"/>").append("\n");
        builder.append(properties);
        builder.append("</owl:NamedIndividual>").append("\n");
        return builder.toString();
    }

    /**
     *
     * @param ghURL         github URL
     *
     * @return              OWL instance
     */
    public static String convertGitHubInstance(String ghURL) {
        StringBuilder builder = new StringBuilder();
        builder.append("<owl:NamedIndividual rdf:about=\"").append(ghURL).append("\">").append("\n");
        builder.append("<rdf:type rdf:resource=\"http://slebok.github.io/proverb/ontology#Repository\"/>").append("\n");
        builder.append("</owl:NamedIndividual>").append("\n");
        return builder.toString();
    }

    /**
     *
     * @param ghURL         github URL
     * @param type          CodeContributor or Repository
     * @param properties    name, abstract, contributor(for type Repo),
     *                     sameAs(for possible relationship in CodeContributor and Author)
     *
     * @return              OWL instance
     */
    public static String convertGitHubInstance(String ghURL, String type, String properties) {
        StringBuilder builder = new StringBuilder();
        builder.append("<owl:NamedIndividual rdf:about=\"").append(ghURL).append("\">").append("\n");
        builder.append("<rdf:type rdf:resource=\"http://slebok.github.io/proverb/ontology#").append(type).append("\"/>").append("\n");
        builder.append(properties);
        builder.append("</owl:NamedIndividual>").append("\n");
        return builder.toString();
    }

    /**
     *
     * @param doiURL        full url with doi
     * @param type          Article
     * @param properties    name, abstract
     * @return              OWL instance
     */
    public static String convertDoiInstance(String doiURL, String type, String properties){
        StringBuilder builder = new StringBuilder();
        builder.append("<owl:NamedIndividual rdf:about=\"").append(doiURL).append("\">").append("\n");
        builder.append("<rdf:type rdf:resource=\"http://slebok.github.io/proverb/ontology#").append(type).append("\"/>").append("\n");
        builder.append("</owl:NamedIndividual>").append("\n");
        return builder.toString();
    }

    /**
     *
     * @param doiURL        full url with doi
     * @return              OWL instance
     */
    public static String convertDoiInstance(String doiURL){
        StringBuilder builder = new StringBuilder();
        builder.append("<owl:NamedIndividual rdf:about=\"").append(doiURL).append("\">").append("\n");
        builder.append("<rdf:type rdf:resource=\"http://slebok.github.io/proverb/ontology#Article\"/>").append("\n");
        builder.append("</owl:NamedIndividual>").append("\n");
        return builder.toString();
    }
}
