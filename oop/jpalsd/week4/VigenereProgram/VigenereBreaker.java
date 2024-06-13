import java.util.HashSet;

import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        String slicedMessage = "";
        for (int i = whichSlice; i < message.length(); i+=totalSlices) {
            slicedMessage += message.charAt(i);
        }
        return slicedMessage;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        
        for (int i = 0; i < klength; i++) {
            CaesarCracker cracker = new CaesarCracker();
            String slicedMessage = sliceString(encrypted, i, klength);
            int currKey = cracker.getKey(slicedMessage);
            key[i] = currKey;
        }

        return key;
    }

    public void breakVigenere (String language) {
        FileResource fr = new FileResource();
        String message = fr.asString();

        HashSet<String> dictionary = readDictionary(new FileResource("dictionaries/" + language));
        String decryptedMessage = breakForLanguage(message, dictionary);

        System.out.println(decryptedMessage);   
    }

    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> wordDictionary = new HashSet<String>();
        for (String word : fr.lines()) {
            word = word.trim().toLowerCase();
            wordDictionary.add(word);
        } 
        return wordDictionary;
    }

    public int countWords(String message, HashSet<String> dictionary) {
        int totalRealWords = 0;
        String[] words = message.split("\\W+");
        for (int i = 0; i < words.length; i++) {
            String currWord = words[i].toLowerCase();
            if (dictionary.contains(currWord)) {
                totalRealWords++;
            }
        }
        return totalRealWords;
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int maxValidWords = 0;
        int maxKLength = 100;
        String decryptedMessage = "";

        for (int i = 1; i < maxKLength; i++) {
            int[] key = tryKeyLength(encrypted, i, 'e');
            VigenereCipher cipher = new VigenereCipher(key);
            String currDecryptedMessage = cipher.decrypt(encrypted);

            int currValidWords = countWords(currDecryptedMessage, dictionary);
            if (currValidWords > maxValidWords) {
                maxValidWords = currValidWords;
                decryptedMessage = currDecryptedMessage;

            }
        }
        return decryptedMessage; 
    }
}
