package com.example.medialab;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.json.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import static com.example.medialab.TextIO.SeparateTheWords.separateWords;
import static com.example.medialab.hangmanExceptions.InvalidCountException;

public class HelloController {

    //You can insert here the ID you want! Here are some working examples:

    //static final String ID = "OL26869511M"; //54 words
    static final String ID = "OL31390631M";   //62 words



    ArrayList<String> words = null;

    @FXML
    private Label numberOfWords;
    @FXML
    private Text hiddenWord;
    /*
    @FXML
    public void createNewID(ActionEvent event){

        TextInputDialog dialog = new TextInputDialog("Tran");

        dialog.setTitle("Create new dictionary.");
        dialog.setHeaderText("Give id:");
        dialog.setContentText("ID:");

        Optional<String> result = dialog.showAndWait();

    }*/
    //@FXML
    //private Text hangmanart;

    @FXML
    private ImageView hangmanart;

    @FXML
    private Text secretWordHang;


    @FXML
    private Label totalPoints;
    @FXML
    private Label correctAnswers;
    @FXML
    private Label availChars;
    @FXML
    private TextField guessChar;
    @FXML
    private Button chooseChar;

    @FXML
    private MenuItem getSolution;


    @FXML
    protected void onButtonClick(ActionEvent event) {
        numberOfWords.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void exitTheGame(ActionEvent event){
        Platform.exit();
    };



    @FXML
    public void loadTheGame(ActionEvent e){
        Image hangman1 = new Image("hangman1.jpg");
        hangmanart.setImage(hangman1);
        HttpURLConnection connection;
            //TODO: User must input ID of API

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
            /*If openlibrary servers are down, comment the following command and use an Id
            of an already existing dictionary.
             */
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).
                    thenApply(HttpResponse::body).
                    thenApply(Main::parse).join();
            words = readWords("hangman_DICTIONARΥ-"+ID+".txt");
            System.out.println(words);
            int wordsize = words.size();
            numberOfWords.setText(Integer.toString(wordsize));

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
            JSONObject obj_JSONObject = new JSONObject(responseBody);
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

    @FXML
    public void startTheGame(ActionEvent e) throws InterruptedException {
        System.out.println(words);
        secretWordHang.setText(" ");
        /**
         * Created by Panagiota-Mikaela Ksilia for NTUA- Multimedia course semester project 2021-2022.
         * This class contains everything for a single Hangman game.
         */
        class Hangman {
            /**
             * Gets words from a file and place them as your dictionary.
             * @param words
             */
            public Hangman(ArrayList<String> words) {
                this.dictionary = words;
            }

            String secretWord;
            String word;
            int errors = 0;
            int points = 0;
            int space = 0;
            ArrayList<String> dictionary;
            int lives = 6;
            int loops = 0;


            /**
             * Sets a secret word from dictionary
             * @param secretWord
             */
            public void setSecretWord(String secretWord) {
                this.secretWord = secretWord;
                StringBuilder word = new StringBuilder("");
                word.append("  _".repeat(Math.max(0, secretWord.length())));
                hiddenWord.setText(String.valueOf(word));
            }

            /**
             * Returns word.
             * @return word
             */
            public String getWord() {
                return word;
            }

            /**
             * Returns players current guessing character index.
             * @return
             */
            public int getSpace() {
                return space;
            }

            /**
             * Returns secret words length
             * @return
             */
            public int getLength() {
                return this.secretWord.length();
            }

            /**
             * Returns true if the game is lost.
             * @return
             */
            public boolean isLost() {
                return errors >= 6;
            }

            /**
             * Returns true if the game is won.
             * @return
             */
            public boolean isWon() {
                return errors < 6 && word.equals(secretWord);
            }

            /**
             * Calculates all the words that have the same length
             * as the secret word.
             * @return
             */
            public ArrayList<String> possibleWords() {
                int len = this.secretWord.length();
                ArrayList<String> possibleWords = new ArrayList<>();
                for (String w : this.dictionary) {
                    if (w.length() == len) {
                        possibleWords.add(w);
                    }
                }
                return possibleWords;
            }

            /**
             * Displays the available characters that the player can choose from.
             * @param possibleWords
             * @return
             */
            public List<Character> availableChar(ArrayList<String> possibleWords) {
                List<Character> availableChar = new ArrayList<>();
                int curSpace = getSpace();
                for (String w : possibleWords) {
                    char pchar = w.charAt(curSpace);
                    availableChar.add(pchar);
                }

                return availableChar;
            }

            /**
             * Calculates every characters probability of being in that specific index,
             * based from the other words in dictionary.
             * @param availableChar
             * @return
             */
            public Object[][] calculateProbability(List<Character> availableChar) {
                int totalWords = availableChar.size();
                HashMap<Character, Integer> charCountMap = new HashMap<Character, Integer>();
                for (char c : availableChar) {
                    if (charCountMap.containsKey(c)) {
                        charCountMap.put(c, charCountMap.get(c) + 1);
                    } else {
                        charCountMap.put(c, 1);
                    }
                }

                Object[][] arr = new Object[charCountMap.size()][2];
                Set entries = charCountMap.entrySet();
                Iterator entriesIterator = entries.iterator();

                int i = 0;
                int temp = 0;
                while (entriesIterator.hasNext()) {

                    Map.Entry mapping = (Map.Entry) entriesIterator.next();

                    arr[i][0] = mapping.getKey();
                    arr[i][1] = mapping.getValue();

                    i++;
                }

                for (i = 0; i < arr.length; i++) {
                    temp = (int) arr[i][1];
                    arr[i][1] = (float) temp / totalWords;
                }

                return arr;
            }

            /**
             * Returns true if the letter that the player guessed is correct.
             * @param letter
             * @return
             */
            public boolean guessLetter(char letter) {
                boolean guessed = false;

                //StringBuilder newWord = new StringBuilder(secretWord.length());
                int i = getSpace();
                System.out.println("Hint: The secret word is " +secretWord);
                //System.out.println(i);
                char correct_char = secretWord.charAt(i);
                //System.out.println(correct_char);
                if (correct_char == letter) {
                    secretWordHang.setText(secretWordHang.getText()+"  " + String.valueOf(correct_char));
                    return true;
                } else {
                    return false;
                }
            }

            /**
             * Calculates points and adds them to total.
             * @param prob
             */
            public void givePoints(Float prob) {
                if (prob >= 0.6) {
                    points += 5;
                } else if (prob >= 0.4) {
                    points += 10;
                } else if (prob >= 0.25) {
                    points += 15;
                } else {
                    points += 20;
                }
            }

            /**
             * This method runs based on hangman's flowchart.
             */
            public void play()  {
                getSolution.setOnAction(event -> {
                    lives = 0;
                });
                availChars.setText("Hello");
                ArrayList<String> words = this.dictionary;
                setSecretWord(pickRandom(words));
                System.out.println("Hint: The secret word is "+secretWord);
                ArrayList<String> dic = possibleWords();
                //System.out.println(availableChar(dic));
                //System.out.println(Arrays.deepToString(calculateProbability(availableChar(dic))));
                //System.out.println(availableChar(dic));
                availChars.setWrapText(true);
                availChars.setText(Arrays.deepToString(calculateProbability(availableChar(dic))));
                final boolean[] found = {false};

                    chooseChar.setOnAction(event -> {

                        //System.out.println(availableChar(dic));
                        availChars.setText(Arrays.deepToString(calculateProbability(availableChar(dic))));
                                int loops = 0;
                                boolean gameon = true;


                                // while ((lives > 0) && (gameon)) {
                                //System.out.println(loops);
                                //loops +=1;
                                String sc = guessChar.getText();
                                char guess = sc.toUpperCase().charAt(0);
                                boolean outcome = guessLetter(guess);
                                System.out.println(outcome);

                                if (outcome == true) {
                                    Object[][] arr = calculateProbability(availableChar((dic)));
                                    int i = 0;
                                    Float probability = null;
                                    while (i < arr.length) {

                                        if (arr[i][0].equals(guess)) {
                                            probability = (Float) arr[i][1];
                                            break;
                                        }
                                        i++;
                                    }
                                    //System.out.println(probability);
                                    givePoints(probability);
                                    totalPoints.setText(String.valueOf(points));
                                    if (space == secretWord.length() - 1) {
                                        found[0] = true;
                                        availChars.setText("Congrats you found the word!");
                                        //chooseChar.setDisable(true);
                                    }
                                    space += 1;
                                    correctAnswers.setText(String.valueOf(space));
                                    if (space<secretWord.length()){
                                    availChars.setText(Arrays.deepToString(calculateProbability(availableChar(dic))));}

                                } else {
                                    System.out.println("Lives left:"+lives);
                                    lives -= 1;
                                    if(points<=15){
                                        points = 0;
                                        totalPoints.setText(String.valueOf(points));
                                    }else{
                                        points -=15;
                                        totalPoints.setText(String.valueOf(points));
                                    }
                                    //hangmanart.setText(HangmanArt.getArt(lives));
                                    //hangmanart.setText("Lives left:"+lives);
                                    changeHangmanArt();
                                }
                        if  (lives <=0) {
                            availChars.setText("Oh no, you run out of lives!The secret word is:" + secretWord);
                            //System.out.println("Oh no, you run out of lives!The secret word is:" + secretWord);
                            //chooseChar.setDisable(true);
                        }

                            });



                //Scanner sc = new Scanner(System.in);
                //int loops = secretWord.length();
                //System.out.println("Guess any letter in the word");
                System.out.println("Lives left:"+lives);

                //System.out.println(guess);

            }

            /**
             * Changes hangman's drawing based on remaining lives.
             */
            private void changeHangmanArt() {
                Image hangman2 = new Image(getClass().getResourceAsStream("hangman2.jpg"));
                Image hangman3 = new Image(getClass().getResourceAsStream("hangman3.jpg"));
                Image hangman4 = new Image(getClass().getResourceAsStream("hangman4.jpg"));
                Image hangman5 = new Image(getClass().getResourceAsStream("hangman5.jpg"));
                Image hangman6 = new Image(getClass().getResourceAsStream("hangman6.jpg"));
                Image hangman7 = new Image(getClass().getResourceAsStream("hangman7.jpg"));
                if (lives ==6) {
                    Image hangman1 = new Image("hangman1.jpg");
                    hangmanart.setImage(hangman1);
                }else if (lives == 5){
                    hangmanart.setImage(hangman2);
                }else if (lives == 4){
                    hangmanart.setImage(hangman3);
                }else if (lives == 3){
                    hangmanart.setImage(hangman4);
                }else if (lives == 2){
                    hangmanart.setImage(hangman5);
                }else if (lives == 1){
                    hangmanart.setImage(hangman6);
                }else {
                    hangmanart.setImage(hangman7);
                }
            }

            /**
             * Picks random word from dictionary.
             * @param words
             * @return
             */
            public static String pickRandom(ArrayList<String> words) {
                Random randomizer = new Random();
                ArrayList<String> list = words;
                String random = list.get(randomizer.nextInt(list.size()));
                return random;
            }
        }
        Hangman game = new Hangman(words);
        game.play();

        }

    @FXML
    public void handleButtonClick(ActionEvent e) {
        //ArrayList<String> words = null;
        //Hangman game = new Hangman(words);

    }


}