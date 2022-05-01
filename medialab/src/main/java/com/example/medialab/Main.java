package com.example.medialab;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Scanner;

import static com.example.medialab.TextIO.SeparateTheWords.separateWords;
import static com.example.medialab.hangmanExceptions.*;


public class Main {

    private static HttpURLConnection connection;

    public static <Hangman> void main(String[] args) {


        //TODO: User must input ID of API
        String ID ="OL26869511M";
        class identity {
            private static String id; // private = restricted access

            // Getter
            public static String getId() {
                return id;
            }

            // Setter
            public static String setId(String newID) {
                return id = newID;
            }
        }
        identity.setId(ID);

        //Build a connection
        BufferedWriter bw = null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://openlibrary.org/books/"+ID+".json")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).
                thenApply(HttpResponse::body).
                thenApply(Main::parse).join();

        ArrayList<String> words = readWords("hangman_DICTIONARΥ-"+ID+".txt");
        System.out.println(words);
        //Hangman game = new Hangman(words);
        //game.play();

    }

    public static ArrayList<String> readWords(String fname) {
        ArrayList<String> words = new ArrayList<>();
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File(fname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc2.hasNextLine()) {
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()) {
                String s = s2.next();
                words.add(s);
            }
        }
        return words;
    }

    public static String parse(String responseBody) throws InvalidCountException{
        String ID = HelloController.ID;
        //System.out.println(responseBody);
        org.json.JSONObject obj_JSONObject = new org.json.JSONObject(responseBody);
        String description = obj_JSONObject.getJSONObject("description").getString("value");
        System.out.println(description);

        //Create a temp file to write on
        BufferedWriter bw = null;
        try {
            // Creating a temporary file
            File file = new File("D:/medialab/hangman_DICTIONARΥ-"+ID+".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(description);
            //tempFile.setReadable(true);
            System.out.println(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bw != null)
                    bw.close();
            }
            catch (Exception ex) {
                System.out.println("Error in closing the BufferedWriter"+ex);
            }
        }
        separateWords("hangman_DICTIONARΥ-"+ID+".txt");
        return null;
    }



}