//Code taken from focus in Learn Java in N games

import java.util.Iterator;

/**Itereates through a stack*/
public class StackIterator<Item> implements Iterator<Item> {
    private Pip<Item> node;

    public StackIterator(Pip<Item> front) {
        node = front;
    }

    public boolean hasNext() {
        return node != null;
    }

    public Item next() {
        Item result = node.getItem();
        node = node.getNext();
        return result;
    }
}
