package jpalsd.week1;

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
        
        for (int i = 0; i < inputArr.length; i++) {
            char currInput = inputArr[i];
            boolean currOutput = outputArr[i];
            boolean answer = isVowel(currInput);
            if (currOutput != answer) { System.out.println(String.format("Test %d - Failed", i+1)); }
        }
        System.out.println("isVowel Tests Completed");
    }
    public void testReplaceVowels() {
        String[] inputArr1 = {"Hello World","Systematic","NEW YEAR NEW ME","Hey soul Sisters"};
        Character[] inputArr2 = {'*','q','m','1'};
        String[] outputArr = {"H*ll* W*rld","Systqmqtqc","NmW YmmR NmW Mm","H1y s11l S1st1rs"};
 
        for (int i = 0; i < inputArr1.length; i++) {
            String currInput1 = inputArr1[i];
            char currInput2 = inputArr2[i];
            String answer = outputArr[i];
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
        
        for (int i = 0; i < inputArr1.length; i++) {
            String currInput1 = inputArr1[i];
            char currInput2 = inputArr2[i];
            String answer = outputArr[i];
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
