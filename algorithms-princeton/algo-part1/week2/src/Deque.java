import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    int size;
    Node lastNode, firstNode;
    private class Node {
        Item item;
        Node prev, next;
    }

    // construct an empty deque
    public Deque() {
        lastNode = null;
        firstNode = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null items are not allowed in the deque");
        }
        // create a new node with appropriate instance vals
        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = null;
        // set the new node to be the new first
        newFirst.prev = firstNode;

        firstNode = newFirst;
        size++;

        if (size == 1) {
            lastNode = firstNode;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null items are not allowed in the deque");
        }
        // create a new node with appropriate instance vals
        Node newLast = new Node();
        newLast.item = item;
        // set the node to be the new last
        newLast.next = lastNode;
        newLast.prev = null;

        lastNode = newLast;
        size++;

        if (size == 1) {
            firstNode = lastNode;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }

        Item query = firstNode.item;

        if (size == 1) {
            firstNode = null;
            lastNode = null;
        }
        else {
            firstNode = firstNode.prev;
            firstNode.next = null;
        }

        size--;
        return query;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }

        Item query = lastNode.item;

        if (size == 1) {
            firstNode = null;
            lastNode = null;
        }
        else {
            lastNode = lastNode.next;
            lastNode.prev = null;
        }

        size--;
        return query;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }


    // implements iterator
    private class DequeIterator implements Iterator<Item> {

        @Override
        public boolean hasNext() {
            return !isEmpty();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more elements in the iterator");
            }
            Node currentNode = firstNode;
            Item query = currentNode.item;
            currentNode = currentNode.next;
            return query;
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
        Deque<String> dequeA = new Deque<String>();
        boolean resultA = dequeA.isEmpty();

        int i = 0;
        for (; i < inputsA.length; i++) {
            dequeA.addLast(inputsA[i]);
            if (dequeA.size() != i + 1) {
                resultA = false;
            }
            if (i == 1 || i == 2) {
                dequeA.removeFirst();
            }

            if (dequeA.size() != inputsA.length - 2) {
                resultA = false;
            }
        }

        if (resultA ) { System.out.printf("isEmpty() and size() %d tests passed\n", i + 1); }

        // test addLast() and removeFirst()
        boolean resultB = true;
        Deque<String> dequeB = new Deque<String>();
        i = 0;
        for (; i < inputsA.length; i++) {
            dequeB.addLast(inputsA[i]);
            //System.out.printf("%s  => ", dequeB.removeFirst());
            //dequeB.addLast(inputsA[i]);
            if (dequeB.isEmpty()) {
                resultB = false;
                System.out.print("test addLast() and removeFirst(): deque should not be empty");
            }
        }
        for (String s : inputsA) {
            String first = dequeB.removeFirst();
            if (!first.equals(s)) {
                resultB = false;
                System.out.print("test addLast() and removeFirst(): removeFirst() is getting wrong answers");
            }
        }
        if (resultB) { System.out.printf("addLast() and removeFirst() %d tests passed\n", i + 1); }
    }
}
