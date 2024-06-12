import edu.duke.*;
import java.util.*;

public class GladLibMap {
	private HashMap<String,ArrayList<String>> identifierMap;
	private ArrayList<String> wordsUsed;
	private ArrayList<String> identifierUsed;
	private Random myRandom;
	
	private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
	private static String dataSourceDirectory = "data";
	
	public GladLibMap(){
		initializeFromSource(dataSourceDirectory);
		myRandom = new Random();
	}
	
	public GladLibMap(String source){
		initializeFromSource(source);
		myRandom = new Random();
	}
	
	private void initializeFromSource(String source) {
		identifierMap = new HashMap<String,ArrayList<String>>();
		identifierUsed = new ArrayList<String>();
		String[] categoriesList = {"adjective","noun","color","country","name","animal",
									"timeframe","verb","fruit"};
		for (int i = 0; i < categoriesList.length; i++) {
			String c = categoriesList[i];
			identifierMap.put(c,readIt(source+"/"+c+".txt"));
		}
		wordsUsed = new ArrayList<String>();
	}
	
	private String randomFrom(ArrayList<String> source){
		int index = myRandom.nextInt(source.size());
		return source.get(index);
	}
	
	private String getSubstitute(String label) {
		if (identifierMap.containsKey(label)) {
			if (!identifierUsed.contains(label)) {
				identifierUsed.add(label);
			}
			return randomFrom(identifierMap.get(label));
		}
		else {
			return "**UNKNOWN**";
		}
	}
	
	private String processWord(String w){
		int first = w.indexOf("<");
		int last = w.indexOf(">",first);
		if (first == -1 || last == -1){
			return w;
		}
		String prefix = w.substring(0,first);
		String suffix = w.substring(last+1);
		String sub = getSubstitute(w.substring(first+1,last));
		while (wordsUsed.contains(sub)) {
			sub = getSubstitute(w.substring(first+1,last));
		}
		wordsUsed.add(sub);
		return prefix+sub+suffix;
	}
	
	private void printOut(String s, int lineWidth){
		int charsWritten = 0;
		for(String w : s.split("\\s+")){
			if (charsWritten + w.length() > lineWidth){
				System.out.println();
				charsWritten = 0;
			}
			System.out.print(w+" ");
			charsWritten += w.length() + 1;
		}
	}
	
	private String fromTemplate(String source){
		String story = "";
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String word : resource.words()){
				story = story + processWord(word) + " ";
			}
		}
		return story;
	}
	
	private ArrayList<String> readIt(String source){
		ArrayList<String> list = new ArrayList<String>();
		if (source.startsWith("http")) {
			URLResource resource = new URLResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		else {
			FileResource resource = new FileResource(source);
			for(String line : resource.lines()){
				list.add(line);
			}
		}
		return list;
	}
	public int totalWordsInMap() {
		int totalWords = 0;
		for (String key : identifierMap.keySet()) {
			int currNumWords = identifierMap.get(key).size();
			totalWords += currNumWords;
		}
		return totalWords;
	}
	public int totalWordsConsidered() {
		int totalNumWords = 0;
		for (String key : identifierUsed) {
			int currNumWords = identifierMap.get(key).size();
			totalNumWords += currNumWords;
		}
		return totalNumWords;
	}
	public void makeStoryTester() {
		makeStory();

		int numWords = totalWordsInMap();
		int numWordsConsidered = totalWordsConsidered();

		System.out.println(String.format("Total no. of words to choose from: %d", numWords));
		System.out.println(String.format("Total words considered: %d", numWordsConsidered));
		System.out.print("Identier used: ");
		for (String identifier : identifierUsed) {
			System.out.print(String.format(" %s ", identifier));
		}
		System.out.print("\n");
	}
	public void makeStory(){
		wordsUsed.clear();

	    System.out.println("\n");
		String story = fromTemplate("data/madtemplate2.txt");
		printOut(story, 60);
		System.out.println("\n");
	}
	public static void main(String[] args) {
		GladLibMap gl = new GladLibMap();
		gl.makeStoryTester();
		
	}
}
