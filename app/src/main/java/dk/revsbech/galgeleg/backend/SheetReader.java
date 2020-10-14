package dk.revsbech.galgeleg.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SheetReader {
    String id = "1pQ2xRRJARb6YaTsmYPoUsni_QbM3efr9HQ0ojp8fcNA";
    String data;
    String[] categories;
    HashMap<String,String[]> words;
    private static SheetReader single_instance=null;


    public static SheetReader getInstance(){
        if (single_instance == null){
            single_instance = new SheetReader();
        }
        return single_instance;
    }

    public void readSheet() throws IOException {
        String sheetName = "Ark2";
        String url = "https://docs.google.com/spreadsheets/d/"+id+"/gviz/tq?tqx=out:csv&sheet="+sheetName;
        //String url = "https://docs.google.com/spreadsheets/d/" + id + "/export?format=csv&sheet=" + "902668846";
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
        data = sb.toString();
    }

    public void addWords(ArrayList<String> wordList, String category) throws IOException {
        readSheet(category);
        String[] splitData = data.split("\n");
        for (int i = 0; i < splitData.length; i++) {
            wordList.add(splitData[i].toLowerCase());
        }
    }


    public void readSheet(String sheetName) throws IOException {
        if (sheetName==null){sheetName = "Ark1";}
        String url = "https://docs.google.com/spreadsheets/d/"+id+"/gviz/tq?tqx=out:csv&sheet="+sheetName;

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
        data = sb.toString();


    }



}
