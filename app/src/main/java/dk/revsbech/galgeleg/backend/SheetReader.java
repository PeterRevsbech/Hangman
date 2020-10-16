package dk.revsbech.galgeleg.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SheetReader {
    String id = "1pQ2xRRJARb6YaTsmYPoUsni_QbM3efr9HQ0ojp8fcNA";
    //ArrayList<String> words;
    String currentCategory = null;
    String defaultCategory = "Default";
    String[] categories;
    HashMap<String,ArrayList<String>> words = new HashMap<String,ArrayList<String>>();
    private static SheetReader single_instance=null;

    private SheetReader(){
        try{
            categories=readCategories();
            //Creates entry in words-hashmap for each category
            for (int i = 0; i < categories.length; i++) {
                words.put(categories[i],null);
            }
            readSheet(defaultCategory);
        } catch (IOException e){
            e.printStackTrace();
        }



    }

    public static SheetReader getInstance(){
        if (single_instance == null){
            single_instance = new SheetReader();
        }
        return single_instance;
    }



    public ArrayList<String> readSheet(String sheetName) throws IOException {
        if (sheetName==null){sheetName = defaultCategory;}
        String url = "https://docs.google.com/spreadsheets/d/"+id+"/gviz/tq?tqx=out:csv&sheet="+sheetName;

        //If category is already loaded - return words
        if (words.get(sheetName)!=null){
            return words.get(sheetName);
        } //Otherwise - proceed with loading

        System.out.println("Getting data from " + url);

        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            line = br.readLine();
            if (line ==null){break;}
            String[] splitString = line.split("\"");
            String word = splitString[1];
            sb.append(word+"\n");
        }
        String data = sb.toString();

        String[] splitData = data.split("\n");
        ArrayList<String> result = new ArrayList<String>(splitData.length);
        for (int i = 0; i < splitData.length; i++) {
            result.add(splitData[i].toLowerCase());
        }

        words.put(sheetName,result);
        return result;
    }


    public String[] readCategories() throws IOException{
        if (categories!=null){
            return categories;
        }
        String url = "https://docs.google.com/spreadsheets/d/"+id+"/gviz/tq?tqx=out:csv&sheet="+"Categories";

        System.out.println("Getting data from " + url);

        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            line = br.readLine();
            if (line ==null){break;}
            String[] splitString = line.split("\"");
            String word = splitString[1];
            sb.append(word+"\n");
        }
        String categoriesData = sb.toString();
        categories= categoriesData.split("\n");
        return categories;
    }
    public String[] getCategories(){
        return this.categories;
    }



}
