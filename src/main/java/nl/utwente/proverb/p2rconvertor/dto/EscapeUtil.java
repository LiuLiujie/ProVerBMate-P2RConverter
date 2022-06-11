package nl.utwente.proverb.p2rconvertor.dto;

public class EscapeUtil {

    private EscapeUtil() { }

    public static String escapeURL(String string){
        string = escapeURLSpecialChar(string);
        string = escapeURLEndWithSpecialChar(string);
        string = escapeEXDOI(string);
        return string;
    }

    private static String escapeURLSpecialChar(String string){
        if (string.contains("]")){
            return string.replace("]", "");
        }
        return string;
    }

    private static String escapeURLEndWithSpecialChar(String string){
        if (string.endsWith("/")){
            return string.substring(0, string.length()-1);
        }
        return string;
    }

    private static String escapeEXDOI(String string){
        if (string.contains("ex.doi")){
            return string.replace("ex.doi", "doi");
        }
        return string;
    }
}
