package nl.utwente.proverb.p2rconvertor.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pattern {

    private Pattern () { }

    public static String matchName(String name){
        if (name == null){
            return null;
        }
        name = name.replace("<sup>","");
        name = name.replace("</sup>","");
        return name;
    }

    public static List<String> matchGitHubRepositories(List<String> urIs){
        if (urIs == null){
            return Collections.emptyList();
        }
        var repos = new ArrayList<String>();
        for (String repo : urIs){
            for (String str : repo.split(" ")){
                if (!str.contains("github.com")){
                    continue;
                }
                var url = EscapeUtil.escapeURL(str);
                if (url.contains("www.")){
                    url = url.replace("www.","");
                }
                if (url.contains("/blob")){
                    var pos = url.indexOf("/blob");
                    url = url.substring(0, pos);
                }
                if (url.contains("/wiki")){
                    var pos = url.indexOf("/wiki");
                    url = url.substring(0, pos);
                }
                repos.add(url);
            }
        }
        return repos;
    }

    public static List<String> matchDOIs(List<String> papers){
        if (papers == null){
            return Collections.emptyList();
        }
        var doIs = new ArrayList<String>();
        for (String doi : papers){
            for (String str : doi.split(" ")){
                if (!str.contains("doi.org")){
                    continue;
                }
                doIs.add(EscapeUtil.escapeURL(str));
            }
        }
        return doIs;
    }

    public static String matchAbstract(List<String> meta){
        if (meta == null){
            return null;
        }
        for (String m : meta){
            if (m.startsWith(":: PV")){
                String[] temp = m.split(":: ");
                if (temp.length == 3){
                    return temp[2];
                }
            }
        }
        return null;
    }

    public static String matchPV(List<String> meta){
        if (meta == null){
            return null;
        }
        for (String m : meta){
            if (m.startsWith(":: PV")){
                String[] temp = m.split(":: ");
                if (temp.length == 3){
                    return temp[1].replace(" ", "");
                }
            }
        }
        return null;
    }
}
