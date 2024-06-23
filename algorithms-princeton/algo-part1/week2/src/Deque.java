import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    int size;
    Node lastNode, firstNode, currentNode;
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
            currentNode = newFirst;
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
            currentNode = newLast;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item query = firstNode.item;
        firstNode = firstNode.prev;

        size--;
        return query;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty");
        }
        Item query = lastNode.item;
        lastNode = lastNode.next;

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

    }
}
