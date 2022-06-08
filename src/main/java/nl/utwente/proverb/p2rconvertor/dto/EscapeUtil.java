package nl.utwente.proverb.p2rconvertor.dto;

public class EscapeUtil {

    private EscapeUtil() { }

    public static String URIEscape(String string){
        string = string.replace("]", "");
        if (string.endsWith("/")){
            string = string.substring(0, string.length()-1);
        }
        return string;
    }
}
