package nl.utwente.proverb.p2rconvertor;


import nl.utwente.proverb.p2rconvertor.converter.Converter;
import nl.utwente.proverb.p2rconvertor.converter.Tool2Tool;
import nl.utwente.proverb.p2rconvertor.service.OntologyService;
import nl.utwente.proverb.p2rconvertor.service.impl.OntologyServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProVerB2RDF {

    private static String modelPath = "ProVerB-Ontology/ProVerB_1.4.0.owl";

    private static String toolsPath = "Verification-Tool-Overview/Tools";

    private static final String demoTool = "Verification-Tool-Overview/Tools/Checkers/AGREE.md";

    public static void main(String[] args) {
        if (args.length ==2){
            toolsPath = args[0];
            modelPath = args[1];
        }

        var model = loadModelFile();
        var tools = loadTools();
        //var tools = loadDemo();
        Converter converter;
        OntologyService ontologyService = new OntologyServiceImpl(model);
        for (File tool : tools){
            converter = new Tool2Tool.Builder(ontologyService, tool)
                    .convertName()
                    .convertRepository()
                    .convertPV()
                    .convertDOIs()
                    .convertAbstract()
                    .build();
            converter.convert();
        }
        try {
            ontologyService.write("extracted_"+model.getName());
        }catch (IOException e){
            System.err.println("Write to file fail");
        }
    }

    private static File loadModelFile(){
        return new File(modelPath);
    }

    private static List<File> loadDemo(){
        var list = new ArrayList<File>(1);
        list.add(new File(demoTool));
        return list;
    }

    private static List<File> loadTools(){
        var path = new File(toolsPath);
        if (!path.isDirectory()){
            throw new IllegalArgumentException("Invalid tools path");
        }
        var rootFiles = path.listFiles();
        if (rootFiles == null || rootFiles.length == 0){
            throw new IllegalArgumentException("Invalid tools path");
        }
        return getFiles(new ArrayList<>(List.of(rootFiles)));
    }

    private static List<File> getFiles(List<File> rootFiles){

        var newFiles = new ArrayList<File>();
        for (File file : rootFiles){
            if (file.isDirectory()){
                var fs = file.listFiles();
                if (fs != null){
                    newFiles.addAll(getFiles(new ArrayList<>(List.of(fs))));
                }
            }
            if (file.isFile()){
                newFiles.add(file);
            }
        }
        return newFiles;
    }
}
