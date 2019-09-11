package ua.hillel.java.elementary1.iterators.implementations.kosenkov;

import ua.hillel.java.elementary1.iterators.AbstractLinkedQueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedQueue extends AbstractLinkedQueue {

    private int size;

    // Collection interface spec:
    // All general-purpose Collection implementation classes
    // (which typically implement Collection indirectly through one of its subinterfaces)
    // should provide two "standard" constructors: a void (no arguments) constructor,
    // which creates an empty collection, and a constructor with a single argument of type Collection,
    // which creates a new collection with the same elements as its argument.
    //
    public LinkedQueue() {
        super();
    }
    //
    public LinkedQueue(Collection collection) {
        super();
        Iterator itr = collection.iterator();
        while (itr.hasNext()) {
            this.add(itr.next());
        }
    }

    // isEmpty() is implemented in AbstractCollection as size() == 0;
    // element() is implemented in AbstractQueue based on peek();
    // remove() is implemented in AbstractQueue based on poll();

    @Override
    public int size() {
        return size;
    }

    // Override to maintain size properly;
    @Override
    public boolean offer(Object o) {
        if (super.offer(o)) {
            size++;
            return  true;
        }
        return false;
    }

    // Retrieves, but does not remove, the head of this queue,
    // or returns null if this queue is empty.
    @Override
    public Object peek() {
        if (head == null) {
            return null;
        }
        return head.getValue();
    }

    //Retrieves and removes the head of this queue,
    // or returns null if this queue is empty.
    @Override
    public Object poll() {
        if (size() == 0) {
            return null;
        }
        Object value = head.getValue();
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.getNext();
        }
        size--;
        return value;
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {

            private Node itr = head;

            @Override
            public boolean hasNext() {
                return itr != null;
            }

            @Override
            public Object next() {
                if (itr == null) {
                    throw new NoSuchElementException();
                }
                Object value = itr.getValue();
                itr = itr.getNext();
                return value;
            }
        };
    }
}
