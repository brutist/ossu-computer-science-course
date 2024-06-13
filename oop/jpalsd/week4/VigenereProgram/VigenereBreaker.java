import java.util.HashMap;
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

    public void breakVigenere () {
        // list of dictionary filenames
        String source = "dictionaries/";
        String[] dictFilenames = new String[] {"Danish","Dutch","English","French",
                                                "German","Italian","Portuguese","Spanish"};
        
        String encryptedMessage = new FileResource().asString();
        HashMap<String,HashSet<String>> langsDictionaries = new HashMap<String,HashSet<String>>();
        for (int i = 0; i < dictFilenames.length; i++) {
            HashSet<String> currLangDict = readDictionary(new FileResource(source+dictFilenames[i]));
            langsDictionaries.put(dictFilenames[i],currLangDict);
        }

        String decryptedMessage = breakForAllLangs(encryptedMessage,langsDictionaries);
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
            char mostCommonChar = mostCommonCharIn(dictionary);
            int[] key = tryKeyLength(encrypted, i, mostCommonChar);
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

    // returns the most common character in the dictionary of words
    public char mostCommonCharIn(HashSet<String> dictionary) {
        HashMap<Character,Integer> charCount = new HashMap<Character,Integer>();
        for (String word : dictionary) {
            for (int i = 0; i < word.length(); i++) {
                char currChar = Character.toLowerCase(word.charAt(i)) ;
                if (charCount.containsKey(currChar)) {
                    int currCharCount = charCount.get(currChar);
                    charCount.put(currChar, currCharCount+1);
                }
                else {
                    charCount.put(currChar,1);
                }
            }
        }
        
        int maxCharCount = 0; 
        char mostCommonChar = '?';
        for (Character c : charCount.keySet()) {
            int currCharCount = charCount.get(c);
            if (currCharCount > maxCharCount) {
                mostCommonChar = c;
                maxCharCount = currCharCount;
            }
        }
        return mostCommonChar;
    }
    public String breakForAllLangs(String encrypted,HashMap<String,HashSet<String>> languages) {
        String decrypted = "";
        int maxValidWords = 0;
        
        for (String language : languages.keySet()) {
            HashSet<String> langDictionary = languages.get(language);
            String currDecryptedMessage = breakForLanguage(encrypted, langDictionary);
            int currValidWords = countWords(currDecryptedMessage, langDictionary);
            if (currValidWords > maxValidWords) {
                maxValidWords = currValidWords;
                decrypted = currDecryptedMessage;
            }
        }
        return decrypted;
    }
}
