package nl.utwente.proverb.p2rconvertor;


import nl.utwente.proverb.p2rconvertor.dto.Tool;
import nl.utwente.proverb.p2rconvertor.template.InstanceTemplate;
import nl.utwente.proverb.p2rconvertor.template.PropertyTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Tool2Tool {

    private final File inputFile;


    private final boolean conName;

    private final boolean conAbs;

    private final boolean conRepositories;

    private final boolean conDOIs;

    private final boolean conPV;

    private Tool2Tool(Builder builder){
        this.inputFile = builder.inputFile;
        this.conName = builder.conName;
        this.conAbs = builder.conAbs;
        this.conDOIs = builder.conDOIs;
        this.conPV = builder.conPV;
        this.conRepositories = builder.conRepositories;
    }

    public static class Builder{

        File inputFile;
        boolean conName;

        boolean conAbs;

        boolean conRepositories;

        boolean conDOIs;

        boolean conPV;

        public Builder(){
            this.conName = false;
            this.conAbs = false;
            this.conRepositories = false;
            this.conDOIs = false;
            this.conPV = false;
        }

        public Builder setInputFile(File inputFile){
            this.inputFile = inputFile;
            return this;
        }

        public Builder convertName(){
            this.conName = true;
            return this;
        }

        public Builder convertAbstract(){
            this.conAbs = true;
            return this;
        }

        public Builder convertRepository() {
            this.conRepositories = true;
            return this;
        }

        public Builder convertDOIs() {
            this.conDOIs = true;
            return this;
        }

        public Builder convertPV() {
            this.conPV = true;
            return this;
        }

        public Tool2Tool build() {
            return new Tool2Tool(this);
        }
    }

    public void convert(Document document) {
        MDToolReader reader = new MDToolReader();
        var tool = reader.read(inputFile);
        var root = document.getDocumentElement();

        //Convert Tool
        var insName = inputFile.getName().split(".md")[0].replace(" ","_");
        var toolElements = convertToolElements(document, tool);
        root.appendChild(InstanceTemplate.convertToolInstance(document, insName, toolElements));

        if (this.conRepositories) {
            this.convertGithubInstance(document, tool).forEach(root::appendChild);
        }

        if (this.conDOIs) {
            this.convertDOIInstance(document, tool).forEach(root::appendChild);
        }
    }

    private List<Element> convertGithubInstance(Document document, Tool tool){
        var repos = tool.getRepositories();
        var elements = new ArrayList<Element>();
        if (repos != null){
            for (String str : repos){
                elements.add(InstanceTemplate.convertGitHubInstance(document, str));
            }
        }
        return elements;
    }

    private List<Element> convertDOIInstance(Document document, Tool tool){
        var doIs = tool.getDOIs();
        var elements = new ArrayList<Element>();
        if (doIs != null){
            for (String doi : doIs){
                elements.add(InstanceTemplate.convertDoiInstance(document, doi));
            }
        }
        return elements;
    }

    private List<Element> convertToolElements(Document document, Tool tool){
        var elements = new ArrayList<Element>();

        if (this.conName){
            var name = tool.getName();
            if (name != null){
                elements.add(PropertyTemplate.convertName(document, tool.getName()));
            }
        }
        if (this.conRepositories) {
            var repos = tool.getRepositories();
            if (repos != null){
                for (String str : repos){
                    elements.add(PropertyTemplate.convertRepository(document, str));
                }
            }
        }
        if (this.conDOIs) {
            var doIs = tool.getDOIs();
            if (doIs != null){
                for (String str : doIs){
                    elements.add(PropertyTemplate.convertPaper(document, str));
                }
            }

        }
        if (this.conAbs) {
            var abs = tool.getAbstract();
            if (abs != null){
                elements.add(PropertyTemplate.convertAbstract(document, abs));
            }
        }
        if (this.conPV) {
            var pv = tool.getPV();
            if (pv != null){
                elements.add(PropertyTemplate.convertCategory(document, pv));
            }
        }
        return elements;
    }
}
