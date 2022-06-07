package nl.utwente.proverb.p2rconvertor;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProVerB2RDF {

    public static void main(String[] args) {
        if (args.length ==2){
            convertAll(args[0], new File(args[1]));
        } else {
            System.err.println("Invalid args");
        }
    }

    private static void convertDemo(){
        convert(new File("Verification-Tool-Overview/Tools/Checkers/AGREE.md"),
                new File("outputAGREE.xml"));
    }

    private static void convertAll(String path, File output){
        var files = getFiles(new ArrayList<>(List.of(new File(path).listFiles())));
        for (File file : files){
            if (file.isFile()){
                convert(file, output);
            }
            if (file.isDirectory()){
                System.err.println("Unexpected dir");
            }
        }
    }

    private static void convert(File input, File output){
        Tool2Tool t2t = new Tool2Tool.Builder()
                .setInputFile(input)
                .setOutputFile(output)
                .convertName()
                .convertRepository()
                .convertPV()
                .convertDOIs()
                .convertAbstract()
                .build();
        t2t.convert();
    }

    private static List<File> getFiles(List<File> files){

        var newfs = new ArrayList<File>();
        for (File file : files){
            if (file.isDirectory()){
                newfs.addAll(getFiles(new ArrayList<>(List.of(file.listFiles()))));
            }
            if (file.isFile()){
                newfs.add(file);
            }
        }
        return newfs;
    }
}
