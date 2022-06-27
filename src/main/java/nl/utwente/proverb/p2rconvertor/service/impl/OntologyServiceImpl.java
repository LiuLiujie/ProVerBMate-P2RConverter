package nl.utwente.proverb.p2rconvertor.service.impl;

import lombok.NonNull;
import nl.utwente.proverb.p2rconvertor.dto.ontology.PROVERB;
import nl.utwente.proverb.p2rconvertor.service.OntologyService;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.apache.thrift.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class OntologyServiceImpl implements OntologyService {

    private final Model model;

    public OntologyServiceImpl(File modelFile){
        this.model = ModelFactory.createDefaultModel().read(modelFile.getAbsolutePath());
    }

    @Override
    public void addProperty(Resource resource, Property property, @Nullable String literal) {
        if (literal != null && !literal.isBlank()){
            resource.addProperty(property, literal);
        }
    }

    @Override
    public void addProperty(Resource resource, Property property, @NonNull Resource object) {
        resource.addProperty(property, object);
    }

    @Override
    public Resource createTool(String insName) {
        var toolResource = model.createResource(PROVERB.getURI() + insName);
        this.addProperty(toolResource, RDF.type, PROVERB.R_TOOL);
        return toolResource;
    }



    @Override
    public Resource createRepository(String uri) {
        var resource = model.createResource(uri);
        this.addProperty(resource, RDF.type, PROVERB.R_REPOSITORY);
        return resource;
    }

    @Override
    public Resource createArticle(String doi) {
        var resource = model.createResource(doi);
        this.addProperty(resource, RDF.type, PROVERB.R_ARTICLE);
        return resource;
    }

    @Override
    public Resource getPV(String PV) {
        return model.createResource(PROVERB.getURI() + PV);
    }

    @Override
    public void write(String name) throws IOException {
        File outputFile = new File(name);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        try (var outputStream = new FileOutputStream(outputFile, false)) {
            RDFDataMgr.write(outputStream , model, Lang.RDFXML);
        }
    }

    private String escapeURIName(String str){
        str = str.replace(" ", "_");
        return str;
    }
}
