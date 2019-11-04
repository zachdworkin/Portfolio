import java.util.*;

/** Deque (double-ended stack queue)*/

public class Deque<T> implements Iterable<T> {

    private int length;

    private Node<T> front;

    private Node<T> back;

    /** constructor, sets length to zero*/
    public Deque() {
        length = 0;
    }

    /** checks if the deque is empty*/
    public boolean isEmpty() {
        return front==null;
    }

    /** adds an item to the front*/
    public void addFront(T add) {

        front = new Node<T>(add, front);

        //sets the back node when there is only one node at that point
        if (front.getNext()==null) {
            back=front;
        }

        //length increased since an item is added
        length++;
    }

    /** adds an item to the back*/
    public void addBack(T add) {

        Node<T> current = new Node<T>(add, null);

        //if it's empty and someone tries to use addBack, it will add through
        //addFront (and in addFront it makes front=back)
        if(front == null) {
            addFront(add);
        }

        //if it's not empty it adds to the back and increases length
        else {
            back.setNext(current);
            back = current;
            length++;
        }
    }

    /** removes an item from the front*/
    public T removeFront() {

        if(front!=null && front.getNext()!=null) {
            T removed= front.getItem();
            front = front.getNext();
            length--;
            return removed;
        }

        else if(front!=null && front.getNext()==null){
            T removed= front.getItem();
            front=null;
            length--;
            return removed;
        }

        return null;
    }

    /** removes an item from the back*/
    public T removeBack() {
        if (front==null) {
            return null;
        }

        if(front!=null && front.getNext()==null){
            return removeFront();
        }

        else {
            T removed = back.getItem();
            Node<T> current = front;

            while(current.getNext()!=back) {
                current = current.getNext();
            }

            current.setNext(null);
            back = current;
            length--;
            return removed;
        }


    }

    /** returns the size of the stack*/
    public int size() {

        return length;
    }

    /** converts the sequence of items into a string*/
    public String toString() {

        Node<T> current = front;
        if(front == null) {
            return "<>";
        }
        else if(front.getNext()==null) {
            return "<" + front.getItem() +">";
        }

        else{
            String s = "<";
            while(current.getNext()!=null) {
                s = s + current.getItem()+ ", ";
                current = current.getNext();
            }

            return s + current.getItem() +">";
        }

    }

    @Override

    public Iterator<T> iterator() {

        return new DequeIterator<T>(front);
    }

}
