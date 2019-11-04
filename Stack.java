//Code taken from focus in Larn Java in N Games

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item>
{
    private Pip<Item> front;
    private Pip<Item> back;

    public Stack() {}

    /** adds a back pip*/
    public void addBack(Item item)
    {
        if (isEmpty()) {//creates a new front node
            front = (this.back = new Pip(item, null));
        } else {//adds a back node and moves the value of back to the new back
            back.setNext(new Pip(item, null));
            back = back.getNext();
        }
    }

    /** adds a new front pip*/
    public void addFront(Item item)
    {
        front = new Pip(item, front);
        if (back == null) {
            back = front;
        }
    }

    /**checks to see if our stack is empty*/
    public boolean isEmpty()
    {
        return front == null;
    }

    /** makes the stack iterable*/
    public Iterator<Item> iterator()
    {
        return new StackIterator(front);
    }

    /** removes the back item and updates back appropriatelu*/
    public Item removeBack()
    {
        Item result = back.getItem();
        if (front == back) {
            front = (this.back = null);
        } else {
            Pip<Item> n = front;
            //iterates through the whole stack
            while (n.getNext() != back) {
                n = n.getNext();
            }
            n.setNext(null);
            back = n;
        }
        return result;
    }

    /** returns the size of the stack by counting pips in it by iterating though it*/
    public int size()
    {
        int result = 0;
        Pip<Item> n = front;
        while (n != null) {
            result++;
            n = n.getNext();
        }
        return result;
    }

    /** changes the stack into a string for reading stacks purposes */
    public String toString()
    {
        String result = "<";
        if (!isEmpty()) {
            Pip<Item> n = front;
            while (n != back) {
                result = result + n.getItem() + ", ";
                n = n.getNext();
            }
            result = result + n.getItem();
        }
        return result + ">";
    }
}
