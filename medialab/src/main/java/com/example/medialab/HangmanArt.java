package com.example.medialab;

public class HangmanArt {
    public static String getArt(int lives) {
        String art = null;
        if (lives == 6){
            art =   " \n"
                    + " ------\n"
                    + "|      |\n"
                    + "|      \n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|";
            return art;
        }else if (lives==5){
            art =   " \n"
                    + " ------\n"
                    + "|      |\n"
                    + "|      O\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|";
            return art;
        }else if (lives==4){
            art =   " \n"
                    + " ------\n"
                    + "|      |\n"
                    + "|      O \n"
                    + "|      |\n"
                    + "|      |\n"
                    + "|      |\n"
                    + "|\n"
                    + "|\n"
                    + "|\n"
                    + "|";
            return art;
        }else if (lives==3){
            art =   " \n"
                    + " ------\n"
                    + "|      |\n"
                    + "|   \\  O  /\\n"
                    + "|    \\ | /\\n"
                    + "|     \\|/ \\n"
                    + "|      |\n"
                    + "|      |\n"
                    + "|     / \\\n"
                    + "|    /   \\\n"
                    + "|   /     \\";
            return art;
        }else if (lives==2){
            art =   " \n"
                    + " ------\n"
                    + "|      |\n"
                    + "|   \\  O  /\\n"
                    + "|    \\ | /\\n"
                    + "|     \\|/ \\n"
                    + "|      |\n"
                    + "|      |\n"
                    + "| \n"
                    + "| \n"
                    + "| ";
            return art;
        }else if (lives==1){
            art =  " \n"
                    + " ------\n"
                    + "|      |\n"
                    + "|   \\  O  /\n"
                    + "|    \\ | /\n"
                    + "|     \\|/ \n"
                    + "|      |\n"
                    + "|      |\n"
                    + "|     /\n"
                    + "|    / \n"
                    + "|   / \n";
            return art;
        }else {
            art =   " \n"
                    + " ------\n"
                    + "|      |\n"
                    + "|   \\  O  /\n"
                    + "|    \\ | /\n"
                    + "|     \\|/\n"
                    + "|      |\n"
                    + "|      |\n"
                    + "|     / \\\n"
                    + "|    /   \\\n"
                    + "|   /     \\";
            return art;

        }


    }
}
