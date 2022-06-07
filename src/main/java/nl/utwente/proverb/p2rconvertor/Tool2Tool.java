package nl.utwente.proverb.p2rconvertor;


import nl.utwente.proverb.p2rconvertor.dto.Tool;
import nl.utwente.proverb.p2rconvertor.template.InstanceTemplate;
import nl.utwente.proverb.p2rconvertor.template.PropertyTemplate;

import java.io.*;

public class Tool2Tool {

    private final File inputFile;

    private final File outputFile;

    private final boolean conName;

    private final boolean conAbs;

    private final boolean conRepositories;

    private final boolean conDOIs;

    private final boolean conPV;

    private Tool2Tool(Builder builder){
        this.inputFile = builder.inputFile;
        this.outputFile = builder.outputFile;
        this.conName = builder.conName;
        this.conAbs = builder.conAbs;
        this.conDOIs = builder.conDOIs;
        this.conPV = builder.conPV;
        this.conRepositories = builder.conRepositories;
    }

    public static class Builder{

        File inputFile;

        File outputFile;
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

        public Builder setOutputFile(File outputFile){
            this.outputFile = outputFile;
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

    public void convert() {
        MDToolReader reader = new MDToolReader();
        Tool tool = reader.read(inputFile);
        StringBuilder instances = new StringBuilder();

        //Convert Tool
        var insName = inputFile.getName().split(".md")[0].replace(" ","_");
        String properties = this.convertToolProperties(tool);
        instances.append(InstanceTemplate.convertToolInstance(insName, properties));

        //Convert GitHub
        if (this.conRepositories){
            instances.append(convertGithubInstance(tool));
        }

        //Convert DOI
        if (this.conDOIs) {
            instances.append(convertDOIInstance(tool));
        }

        //Save file
        try {
            if (!outputFile.exists()){
                outputFile.createNewFile();
            }
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
                writer.write(instances.toString());
                writer.flush();
            }
        }catch (IOException e){
            System.err.println(e.getStackTrace());
        }
    }

    private String convertGithubInstance(Tool tool){
        StringBuilder builder = new StringBuilder();
        var repos = tool.getRepositories();
        if (repos != null){
            for (String str : repos){
                builder.append(InstanceTemplate.convertGitHubInstance(str));
            }
        }
        return builder.toString();
    }

    private String convertDOIInstance(Tool tool){
        StringBuilder builder = new StringBuilder();
        var DOIs = tool.getDOIs();
        if (DOIs != null){
            for (String doi : DOIs){
                builder.append(InstanceTemplate.convertDoiInstance(doi));
            }
        }
        return builder.toString();
    }

    private String convertToolProperties(Tool tool) {
        StringBuilder builder = new StringBuilder();
        if (this.conName){
            var name = tool.getName();
            if (name != null){
                builder.append(PropertyTemplate.convertName(tool.getName()));
            }

        }
        if (this.conRepositories) {
            var repos = tool.getRepositories();
            if (repos != null){
                for (String str : repos){
                    builder.append(PropertyTemplate.convertRepository(str));
                }
            }
        }
        if (this.conDOIs) {
            var DOIs = tool.getDOIs();
            if (DOIs != null){
                for (String str : DOIs){
                    builder.append(PropertyTemplate.convertPaper(str));
                }
            }

        }
        if (this.conAbs) {
            var abs = tool.getAbstract();
            if (abs != null){
                builder.append(PropertyTemplate.convertAbstract(abs));
            }
        }
        if (this.conPV) {
            var PV = tool.getPV();
            if (PV != null){
                builder.append(PropertyTemplate.convertCategory(PV));
            }
        }
        return builder.toString();
    }
}
