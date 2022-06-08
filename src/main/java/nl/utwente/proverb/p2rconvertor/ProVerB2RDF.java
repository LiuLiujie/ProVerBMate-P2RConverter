package nl.utwente.proverb.p2rconvertor;


import nl.utwente.proverb.p2rconvertor.tools.XMLUtil;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProVerB2RDF {

    public static void main(String[] args) {
        if (args.length ==2){
            var output = new File(args[1]);
            convertAll(args[0], output);
        } else {
            System.err.println("Invalid args");
        }
    }

    private static void convertDemo(){
        var output = new File("ontology.owl");
        var document  = XMLUtil.getDocument(output);
        convert(new File("Verification-Tool-Overview/Tools/Checkers/AGREE.md"),
                document);
        XMLUtil.writeToFile(document, output);
    }

    private static void convertAll(String path, File output){
        var document  = XMLUtil.getDocument(output);
        var files = getFiles(new ArrayList<>(List.of(new File(path).listFiles())));
        for (File input : files){
            if (input.isFile()){
                convert(input, document);
            }
            if (input.isDirectory()){
                System.err.println("Unexpected dir");
            }
        }
        XMLUtil.writeToFile(document, output);
    }

    private static void convert(File input, Document document){
        Tool2Tool t2t = new Tool2Tool.Builder()
                .setInputFile(input)
                .convertName()
                .convertRepository()
                .convertPV()
                .convertDOIs()
                .convertAbstract()
                .build();
        t2t.convert(document);
    }

    private static List<File> getFiles(List<File> files){

        var newfs = new ArrayList<File>();
        for (File file : files){
            if (file.isDirectory()){
                var fs = file.listFiles();
                if (fs != null){
                    newfs.addAll(getFiles(new ArrayList<>(List.of(fs))));
                }
            }
            if (file.isFile()){
                newfs.add(file);
            }
        }
        return newfs;
    }
}
