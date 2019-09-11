package ua.hillel.java.elementary1.iterators;

import java.util.AbstractQueue;

/**
 * Type presents base implementation of the linked list.
 */
public abstract class AbstractLinkedQueue extends AbstractQueue<Object> {

    // Linked list node with reference to next element in the list.
    protected static class Node {
        private Object value;
        private Node next;

        Node(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return next;
        }
    }

    // Reference to the head of the queue.
    protected Node head;
    // Reference to the tail of the queue.
    protected Node tail;

    @Override
    public boolean offer(Object o) {
        if (head == null) {
            head = tail = new Node(o);
            return true;
        }
        // 1. Create new tail element.
        // 2. Point existed tail.next element to it.
        // 3. Update tail with new tail
        Node newTail = new Node(o);
        tail.next = newTail;
        tail = newTail;
        return true;
    }

    @Override
    public boolean add(Object o) {
        return offer(o);
    }
}
