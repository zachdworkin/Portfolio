public class Pip<T> {

    private T item;//value of this node

    private Pip<T> next;//where the node is in the stack

    /**creates a node*/
    public Pip(T item, Pip<T>next){
        this.item=item;
        this.next=next;
    }

    /**finds the next node and returns it*/
    public Pip<T> getNext() {
        return next;
    }

    /**returns the value inside this node*/
    public T getItem() {
        return item;
    }

    /** sets the item inside this node*/
    public void setItem(T item) {
        this.item = item;
    }

    /** sets the placement of this Node */
    public void setNext(Pip<T> next) {
        this.next = next;
    }
}

