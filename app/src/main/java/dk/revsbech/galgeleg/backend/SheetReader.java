package dk.revsbech.galgeleg.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

public class SheetReader {
    String id = "1RnwU9KATJB94Rhr7nurvjxfg09wAHMZPYB3uySBPO6M";
    String data;
    String[] categories;
    HashMap<String,String[]> words;
    private static SheetReader single_instance=null;


    private SheetReader(){
        //gør noget
    }



    public static SheetReader getInstance(){
        if (single_instance == null){
            single_instance = new SheetReader();
        }
        return single_instance;
    }

    public void readSheet() throws IOException {
        String url = "https://docs.google.com/spreadsheets/d/" + id + "/export?format=csv&id=" + id;
        System.out.println("Getting data from " + url);
        boolean categories;
        String currentCategory;

        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = br.readLine();
        }
    }



}
