import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int counter = 0;

        RandomizedQueue<String> randQ = new RandomizedQueue<String>();
        // reservoir sampling
        while (!StdIn.isEmpty()) {
            String currString = StdIn.readString();
            int k = StdRandom.uniformInt(counter + 1);

            if (counter < n) {
                randQ.enqueue(currString);
            }

            else if (k < n) {
                randQ.dequeue();
                randQ.enqueue(currString);
            }
            counter++;
        }

        for (String s : randQ) {
            System.out.println(s);
        }
    }
}
