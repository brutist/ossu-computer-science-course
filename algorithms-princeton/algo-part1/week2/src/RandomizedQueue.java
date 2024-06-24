import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size, topIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        int initialSize = 2;
        items = (Item[]) new Object[initialSize];
        size = 0;
    }

    // resizes items[] into newSize-length array
    //  this also removes all null items in the items[]
    private void resize(int newSize) {
        Item[] resizedItems = (Item[]) new Object[newSize];

        // recopy the array into length newSize
        System.arraycopy(items, 0, resizedItems, 0, size);

        // adjust appropriately the instance variables
        items = resizedItems;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null items are not allowed in the randomized queue");
        }

        if (topIndex == items.length) {
            resize(items.length * 2);
        }

        items[topIndex] = item;
        topIndex++;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot dequeue an empty randomized queue");
        }
        int randomIndex = StdRandom.uniformInt(topIndex);

        Item query = items[randomIndex];

        // save the last item then change it to null to avoid loitering
        Item lastItem = items[topIndex - 1];
        items[topIndex - 1] = null;
        // move the last item to the random index
        items[randomIndex] = lastItem;

        topIndex--;
        size--;

        int oneQuarter = items.length / 4;
        if (size < oneQuarter) {
            resize(items.length / 2);
        }

        return query;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("cannot sample an empty randomized queue");
        }
        int randomIndex = StdRandom.uniformInt(topIndex);
        return items[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        RandomizedQueue<Item> randQIterator;

        // create a RandomizedQueue to iterate through
        public RandomizedQueueIterator() {
            randQIterator = new RandomizedQueue<Item>();

            for (int i = 0; i < topIndex; i++) {
                randQIterator.enqueue(items[i]);
            }
        }

        @Override
        public boolean hasNext() {
            return !randQIterator.isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("cannot call next() on an empty randomized queue");
            }

            return randQIterator.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove operation is not available");
        }

    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] inputsA = {"hey", "Jude", "don't", "make", "it", "bad"};

        // test isEmpty() and size()
        int i = 0;
        boolean resultA = true;
        RandomizedQueue<String> randQ = new RandomizedQueue<String>();
        if (!randQ.isEmpty()) {
            resultA = false;
            System.out.println("isEmpty() test FAILED: Initialization of random queue not empty");
        }
        for (; i < inputsA.length; i++) {
            randQ.enqueue(inputsA[i]);
            if (randQ.size() != i + 1) {
                resultA = false;
                System.out.printf("size() test FAILED: got %d instead of %d\n", randQ.size(), i + 1);
            }
        }
        for (String s : inputsA) {
            randQ.dequeue();
        }
        resultA = resultA && randQ.isEmpty();
        if (resultA) {
            System.out.printf("isEmpty() and size() %d tests PASSED\n", i);
        }

        // test enqueue()
        i = 0;
        boolean resultB = true;
        for (String s : inputsA) {
            randQ.enqueue(s);

            String query = randQ.dequeue();
            if (!query.equals(inputsA[i])) {
                resultB = false;
                System.out.printf("enqueue() test FAILED: got %s instead of %s\n", inputsA[i], query);
            }

            i++;
        }
        if (!randQ.isEmpty()) {
            resultB = false;
            System.out.println("enqueue() test FAILED: " +
                    "calling enqueue n-times in n-length queue should produce an empty queue");
        }
        if (resultB) {
            System.out.printf("enqueue() %d tests PASSED\n", i);
        }

        // test dequeue()
        i = 0;
        boolean resultC = true;
        for (String s : inputsA) {
            randQ.enqueue(s);
        }
        for (String s : inputsA) {
            String query = randQ.dequeue();

            if (query == null) {
                resultC = false;
                System.out.println("dequeue() test FAILED: should not get " +
                        "null when performing dequeue");
            }
            i++;
        }
        if (!randQ.isEmpty()) {
            resultC = false;
            System.out.println("enqueue() test FAILED: " +
                    "calling enqueue n-times in n-length queue should produce an empty queue");
        }
        if (resultC) {
            System.out.printf("dequeue() %d tests PASSED\n", i);
        }

        // test sample()
        i = 0;
        boolean resultD = true;
        for (String s : inputsA) {
            randQ.enqueue(s);
        }
        for (String s : inputsA) {
            String sample = randQ.sample();

            if (sample == null) {
                resultD = false;
                System.out.println("sample() test FAILED: should not get " +
                        "null when performing sample");
            }
            i++;
        }
        if (randQ.size() != i) {
            resultD = false;
            System.out.println("sample() test FAILED: " +
                    "calling n-times should not change the size of the queue");
        }
        if (resultD) {
            System.out.printf("sample() %d tests PASSED\n", i);
        }

        // test iterator()
        // Manual testing (print and make sure the iterator is randomly giving elements)
        RandomizedQueue<String> randQB = new RandomizedQueue<String>();
        for (String s : inputsA) {
            randQB.enqueue(s);
        }
        i = 0;
        for (String s : randQB) {
            if (!s.equals(inputsA[i])) {
                System.out.printf("Not equal: got '%s' instead of '%s' \n", s, inputsA[i]);
            }
            i++;
        }
        if (randQB.size() != inputsA.length) {
            System.out.println("Iterator should not change the size of the object");
        }
    }
}
