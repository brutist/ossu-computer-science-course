package jpalsd;
import java.util.Arrays;
import java.util.List;

public class WordPlay {
    public boolean isVowel(Character ch) {
        ch = Character.toLowerCase(ch);
        String vowels = "aeiou";
        for (int i = 0; i < vowels.length(); i++) {
            if (ch == vowels.charAt(i)) {
                return true;
            }
        }
        return false;
    }
    public String replaceVowels(String phrase, Character ch) {
        String result = "";
        for (int i = 0; i < phrase.length(); i++) {
            char currChar = phrase.charAt(i);
            if (isVowel(currChar)) {
                result += ch;
            }
            else {
                result += currChar;
            }
        }
        return result;
    }
    public String emphasize(String phrase, Character ch) {
        Character chLowerCase = Character.toLowerCase(ch);
        String result = "";
        for (int i = 0; i < phrase.length();i++) {
            Character currChar = phrase.charAt(i);
            Character currCharLowerCase = Character.toLowerCase(currChar);
            boolean inEvenPos = ((i+1) % 2) == 0;
            if (currCharLowerCase == chLowerCase) {
                if (inEvenPos) {
                    result += '+';
                }
                else {
                    result += '*';
                }
            }
            else {
                result += currChar;
            }
        }
        return result;
    }
    public void testIsVowel() {
        Character[] inputArr = {'F','A','a','a'};
        Boolean[] outputArr = {false,true,true,true};
        List<Character> inputList = Arrays.asList(inputArr);
        List<Boolean> outputList = Arrays.asList(outputArr);
        
        for (int i = 0; i < inputList.size(); i++) {
            char currInput = inputList.get(i);
            boolean currOutput = outputList.get(i);
            boolean answer = isVowel(currInput);
            if (currOutput != answer) { System.out.println(String.format("Test %d - Failed", i+1)); }
        }
        System.out.println("isVowel Tests Completed");
    }
    public void testReplaceVowels() {
        String[] inputArr1 = {"Hello World","Systematic","NEW YEAR NEW ME","Hey soul Sisters"};
        Character[] inputArr2 = {'*','q','m','1'};
        String[] outputArr = {"H*ll* W*rld","Systqmqtqc","NmW YmmR NmW Mm","H1y s11l S1st1rs"};
        List<String> inputList1 = Arrays.asList(inputArr1);
        List<Character> inputList2 = Arrays.asList(inputArr2);
        List<String> outputList = Arrays.asList(outputArr);
        
        for (int i = 0; i < inputList1.size(); i++) {
            String currInput1 = inputList1.get(i);
            char currInput2 = inputList2.get(i);
            String answer = outputList.get(i);
            String currResult = replaceVowels(currInput1, currInput2);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
            }
        }
        System.out.println("replaceVowels Tests Completed");
    }
    public void testEmphasize() {
        String[] inputArr1 = {"dna ctgaaactga","Mary Bella Abracadabra","Mary Bella Abracadabra"};
        Character[] inputArr2 = {'a','a','1'};
        String[] outputArr = {"dn* ctg+*+ctg+","M+ry Bell+ +br*c*d*br+","Mary Bella Abracadabra"};
        List<String> inputList1 = Arrays.asList(inputArr1);
        List<Character> inputList2 = Arrays.asList(inputArr2);
        List<String> outputList = Arrays.asList(outputArr);
        
        for (int i = 0; i < inputList1.size(); i++) {
            String currInput1 = inputList1.get(i);
            char currInput2 = inputList2.get(i);
            String answer = outputList.get(i);
            String currResult = emphasize(currInput1, currInput2);
            if (!currResult.equals(answer)) { 
                System.out.println(String.format("Test %d - Failed", i+1));
                System.out.println(String.format("Result should be: %s", answer));
                System.out.println(String.format("Instead got: %s", currResult));
            }
        }
        System.out.println("emphasize Tests Completed");
    }
    public static void main(String[] args) {
        WordPlay w = new WordPlay();
        w.testIsVowel();
        w.testReplaceVowels();
        w.testEmphasize();
    }
}
