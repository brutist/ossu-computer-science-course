package week2.StringsFirstAssignments;

import edu.duke.URLResource;

public class Part4 {
    
    public void printAll(URLResource url) {
        String searchLink = "youtube.com";
        for (String word : url.words()) {
            String toSearch = word.toLowerCase();
            if (toSearch.indexOf(searchLink) > -1) {
                int startIndex = word.indexOf("\"");
                int endIndex = word.lastIndexOf("\"",word.length());
                System.out.println(word.substring(startIndex+1,endIndex));
            }
        }
    }

    public static void main(String[] args) {
        URLResource urlresc = new URLResource("https://www.dukelearntoprogram.com/course2/data/manylinks.html");
        Part4 test = new Part4();
        test.printAll(urlresc);
    }
}
