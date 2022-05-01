package com.example.medialab;

public class hangmanExceptions {
    public static class InvalidCountException extends RuntimeException {
        public InvalidCountException(String errorMessage) {
            super(errorMessage);

        }
    }

    public static class UndersizeException extends RuntimeException {
        public UndersizeException(String errorMessage) {
            super(errorMessage);
        }
    }
    //This exception is not used, we only insert words with more than 6 characters in our dictionary.
    public static class InvalidRangeException extends RuntimeException {
        public InvalidRangeException(String errorMessage) {
            super(errorMessage);
        }
    }
    public static class UnbalancedException extends RuntimeException {
        public UnbalancedException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static void checkExceptions(int count, int percentage){
        if (count<20)
            throw new UndersizeException("Not enough words to make a dictionary. Try a different book");
        if(percentage<20)
            throw new UnbalancedException("Dictionary doesn't contain many big words. Try a different book");
    }
}
