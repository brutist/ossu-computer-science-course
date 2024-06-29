import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node lastNode, firstNode;

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

        if (!isEmpty()) {
            firstNode.next = newFirst;
        }
        else {
            lastNode = newFirst;
        }

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

        if (!isEmpty()) {
            lastNode.prev = newLast;
        }
        else {
            firstNode = newLast;
        }

        lastNode = newLast;
        size++;
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
        Node currentNode = firstNode;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("no more elements in the iterator");
            }
            Item query = currentNode.item;
            currentNode = currentNode.prev;
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
        }
        for (; i > 0; i--) {
            dequeA.removeFirst();
        }
        if (resultA && dequeA.isEmpty()) { System.out.printf("isEmpty() and size() %d tests passed\n", i + 1); }


        // test addLast() and removeFirst()
        boolean resultB = true;
        Deque<String> dequeB = new Deque<String>();
        i = 0;
        for (; i < inputsA.length; i++) {
            dequeB.addLast(inputsA[i]);
            if (dequeB.isEmpty()) {
                resultB = false;
                System.out.print("test addLast() and removeFirst(): deque should not be empty");
            }
            String first = dequeB.removeFirst();
            if (!first.equals(inputsA[i])) {
                resultB = false;
                System.out.print("test addLast() and removeFirst(): removeFirst() is getting wrong answers");
            }
        }
        if (resultB) { System.out.printf("addLast() and removeFirst() %d tests passed\n", i); }


        // test addFirst() and removeLast()
        Deque<String> dequeC = new Deque<String>();
        for (String s : inputsA) {
            dequeC.addFirst(s);
        }
        i = 0;
        boolean resultC = true;
        for (; i < inputsA.length; i++) {
            String ans = dequeC.removeLast();
            if (!ans.equals(inputsA[i])) {
                resultC = false;
                System.out.printf("test addFirst() and removeLast(): got '%s' instead of '%s'\n", ans, inputsA[i]);
            }
        }
        if (resultC) { System.out.printf("addFirst() and removeLast() %d tests passed\n", i); }


        // test addLast() and removeLast()
        Deque<String> dequeD = new Deque<String>();
        for (String s : inputsA) {
            dequeD.addLast(s);
        }
        i = inputsA.length - 1;
        boolean resultD = true;
        for (; i >= 0; i--) {
            String ans = dequeD.removeLast();
            if (!ans.equals(inputsA[i])) {
                resultD = false;
                System.out.printf("test addLast() and removeLast(): got '%s' instead of '%s'\n", ans, inputsA[i]);
            }
        }
        if (resultD) { System.out.printf("addLast() and removeLast() %d tests passed\n", inputsA.length - 1 - i); }



        // test addFirst() and removeFirst()
        Deque<String> dequeE = new Deque<String>();
        for (String s : inputsA) {
            dequeE.addFirst(s);
        }
        i = inputsA.length  - 1;
        boolean resultE = true;
        for (; i >= 0; i--) {
            String ans = dequeE.removeFirst();
            if (!ans.equals(inputsA[i])) {
                resultE = false;
                System.out.printf("test addFirst() and removeFirst(): got '%s' instead of '%s'\n", ans, inputsA[i]);
            }
        }
        if (resultE) { System.out.printf("addFirst() and removeFirst() %d tests passed\n", inputsA.length - 1 - i); }


        // test iterator()
        Deque<String> dequeF = new Deque<String>();
        for (String string : inputsA) {
            dequeF.addLast(string);
        }
        // test for iterator
        i = 0;
        boolean resultF = true;
        for (String s : dequeF) {
            if (!s.equals(inputsA[i])) {
                resultF = false;
                System.out.printf("test iterator(): got '%s' instead of '%s'\n", s, inputsA[i]);
            }
            i++;
        }
        if (resultF && i == inputsA.length) { System.out.printf("iterator() %d tests passed\n", i); }

        // test corner cases
        // null arguments for addFirst() or addLast() throw IllegalArgumentException
        Deque<String> dequeG = new Deque<String>();
        try {
            dequeG.addFirst(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("addFirst() null argument test passed");
        }

        try {
            dequeG.addLast(null);
        }
        catch (IllegalArgumentException e) {
            System.out.println("addLast() null argument test passed");
        }

        // removeFirst() or removeLast() when the deque is empty throw NoSuchElementException
        try {
            dequeG.removeFirst();
        }
        catch (NoSuchElementException e) {
            System.out.println("removeFirst() empty deque test passed");
        }

        try {
            dequeG.removeLast();
        }
        catch (NoSuchElementException e) {
            System.out.println("removeLast() empty deque test passed");
        }

        // next() no more items to return throw NoSuchElementException
        try {
            dequeG.iterator().next();
        }
        catch (NoSuchElementException e) {
            System.out.println("iterator().next() empty deque test passed");
        }

        // remove() throw UnsupportedOperationException
        try {
            dequeG.addFirst("test");
            dequeG.iterator().remove();
        }
        catch (UnsupportedOperationException e) {
            System.out.println("iterator().remove() empty deque test passed");
        }
    }
}
