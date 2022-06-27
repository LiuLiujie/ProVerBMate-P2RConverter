package nl.utwente.proverb.p2rconvertor.service;


import lombok.NonNull;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.thrift.annotation.Nullable;

import java.io.IOException;

public interface OntologyService {

    void addProperty(Resource resource, Property property, @Nullable String literal);

    void addProperty(Resource resource, Property property, @NonNull Resource object);

    Resource createTool(String insName);

    Resource createRepository(String uri);

    Resource createArticle(String doi);

    Resource getPV(String PV);

    void write(String name) throws IOException;
}
