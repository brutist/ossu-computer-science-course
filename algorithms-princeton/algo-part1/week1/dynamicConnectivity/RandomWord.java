/* *****************************************************************************
 *  Name:              Mauring Jr
 *  Coursera User ID:  idk
 *  Last modified:     sometime in 2024
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        int counter = 1;
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / counter)) {
                champion = word;
            }
            counter++;
        }
        System.out.println(champion);
    }
}
