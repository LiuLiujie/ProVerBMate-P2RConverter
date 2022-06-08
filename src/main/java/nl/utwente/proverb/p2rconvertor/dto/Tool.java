package nl.utwente.proverb.p2rconvertor.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@NoArgsConstructor
public class Tool {

    private String name;

    private List<String> URIs;

    private List<String> papers;

    private List<String> meta;

    public String getName() {
        if (name == null){
            return null;
        }
        return this.name
                .replace("<sup>","")
                .replace("</sup>","");
    }

    public List<String> getRepositories() {
        if (URIs == null){
            return null;
        }
        var repos = new ArrayList<String>();
        for (String repo : URIs){
            for (String str : repo.split(" ")){
                if (str.contains("github.com")){
                    repos.add(EscapeUtil.URIEscape(str));
                }
            }
        }
        return repos;
    }

    public List<String> getDOIs() {
        if (papers == null){
            return null;
        }
        var DOIs = new ArrayList<String>();
        for (String doi : papers){
            for (String str : doi.split(" ")){
                if (str.contains("doi.org")){
                    DOIs.add(EscapeUtil.URIEscape(str));
                }
            }
        }
        return DOIs;
    }

    public String getAbstract() {
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

    public String getPV() {
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
