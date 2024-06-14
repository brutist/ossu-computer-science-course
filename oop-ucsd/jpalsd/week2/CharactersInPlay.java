import java.util.ArrayList;

import edu.duke.FileResource;

public class CharactersInPlay {
    private ArrayList<String> names;
    private ArrayList<Integer> dialogCount;

    public CharactersInPlay() {
        names = new ArrayList<String>();
        dialogCount = new ArrayList<Integer>();
    }
    public void update(String person) {
        person = person.toUpperCase();
        if (names.contains(person)) {
            int indexOfPerson = names.indexOf(person);
            int personDialogCount = dialogCount.get(indexOfPerson);
            dialogCount.set(indexOfPerson, personDialogCount+1);
        }
        else {
            names.add(person.toUpperCase());
            dialogCount.add(1);
        }
    }
    public void findAllCharacters() {
        names.clear();
        dialogCount.clear();

        FileResource fr = new FileResource();
        for (String line : fr.lines()) {
            int indexOfPeriod = line.indexOf(".");
            if (indexOfPeriod >= 0) {
                String possibleCharacterName = line.substring(0, indexOfPeriod);
                update(possibleCharacterName);
            }
        }
    }
    public void tester() {
        // use the bigger minimum for the whole play
        //int minSpeakParts = 0;
        int minSpeakParts = 30;
        findAllCharacters();
        for (int i = 0; i < names.size(); i++) {
            int currNameDialogCount = dialogCount.get(i);
            if (currNameDialogCount > minSpeakParts) {
                System.out.println(String.format("%s  %d", names.get(i), currNameDialogCount));
            }
        }
        System.out.println("Characters with less than or equal 60 but more than or equal to 20 dialogues");
        charactersWithNumParts(20, 60);
    }
    public void charactersWithNumParts(int num1, int num2) {
        findAllCharacters();

        for (int i = 0; i < names.size(); i++) {
            int currValue = dialogCount.get(i);
            boolean withinRange = (num1 <= currValue && currValue <= num2);
            if (withinRange) {
                System.out.println(String.format("%s has %d dialogue", names.get(i),currValue));
            }
        }
    }
    public static void main(String[] args) {
        CharactersInPlay cp = new CharactersInPlay();
        //cp.tester();

        cp.charactersWithNumParts(10, 15);
    }
}
