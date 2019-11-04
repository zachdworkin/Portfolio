/** Deque iterator
 * keeps track of where you are in the list*/

public class DequeIterator<T> implements java.util.Iterator<T> {

    private Node<T> current;

    /** constructor*/
    public DequeIterator(Node<T> front) {

        current= new Node<T>(null, front);

    }

    @Override
    public boolean hasNext() {

        if(current!=null) {
            return current.getNext()!=null;
        }

        return false;
    }

    @Override
    public T next() {
        current= current.getNext();
        return current.getItem();
    }

}
