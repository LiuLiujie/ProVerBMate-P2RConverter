package nl.utwente.proverb.p2rconvertor.dto;

public class EscapeUtil {

    private EscapeUtil() { }

    public static String escapeURL(String string){
        string = escapeURLSpecialChar(string);
        string = escapeURLEndWithSpecialChar(string);
        string = escapeDXDOI(string);
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

    private static String escapeDXDOI(String string){
        if (string.contains("dx.doi")){
            return string.replace("dx.doi", "doi");
        }
        return string;
    }
}
