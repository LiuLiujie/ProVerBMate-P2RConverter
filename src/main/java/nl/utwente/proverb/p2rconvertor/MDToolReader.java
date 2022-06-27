package nl.utwente.proverb.p2rconvertor;

import nl.utwente.proverb.p2rconvertor.dto.Tool;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MDToolReader {

    private static final String PREFIX = "#### ";

    private static final String NAME = PREFIX + "Name";

    private static final String URIS = PREFIX + "URIs";

    private static final String PAPERS = PREFIX + "List of related papers";

    private static final String META = PREFIX + "Meta";

    public Tool read(File input){
        Tool tool = new Tool();
        try (var reader = new InputStreamReader(new FileInputStream(input));
             var br = new BufferedReader(reader)){
            String line;
            String name;
            line = br.readLine();
            while (line != null) {
                if (line.startsWith(NAME)) {
                    name = readTilEmpty(br).stream().findFirst().orElse("UNKNOWN TOOL");
                    tool.setName(name);
                } else if (line.startsWith(URIS)) {
                    List<String> urIs = readTilEmpty(br);
                    tool.setURIs(urIs);
                } else if (line.startsWith(PAPERS)) {
                    List<String> papers = readTilEmpty(br);
                    tool.setPapers(papers);
                } else if (line.startsWith(META)) {
                    List<String> meta = readTilEmpty(br);
                    tool.setMeta(meta);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            System.err.println("Reading file error:" +e.getMessage());
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        return tool;
    }

    public List<String> readTilEmpty(BufferedReader br) throws IOException{
        String line = br.readLine();
        List<String> content = new ArrayList<>();
        while (line != null && !line.isEmpty()){
            content.add(line);
            line = br.readLine();
        }
        return content;
    }
}
