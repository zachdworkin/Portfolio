/** Node in a binary tree. */
public class Node {

	private Node left;
	private Node right;
	private String item;

	/** Creates a leaf node. */
	public Node(String key) {
		this.item=key;
	}

	public Node(String key, Node left, Node right) {
		this.item=key;
		this.left=left;
		this.right=right;
	}

	/** Returns the key of this node. */
	public String getKey() {
		return item;
	}

	/** Returns the left child of this node. */
	public Node getLeft() {
		return left;
	}

	/** Returns the right child of this node. */
	public Node getRight() {
		return right;
	}

	/**
	 * Returns the height of the subtree rooted at this node (depth of the
	 * deepest descendant, or 0 for a leaf).
	 */
	public int height() {
		int tallest = -1;//start at -1 because the root node doesnt count as a part of the height
		if (left != null) {
			tallest = left.height();//go left following the tallest
		}
		if (right != null) {
			tallest = Math.max(tallest, right.height());//go right following the tallest
		}
		return 1 + tallest;//if it was a leaf, it adds one
	}

	/** Returns true if this node is a leaf. */
	public boolean isLeaf() {
		if(left==null&&right==null){//if it has no children, it is a leaf
			return true;
		}
		return false;
	}

	/**
	 * Replaces the key of this node (a leaf) with question and gives it two
	 * children. The left child's key is correct. The right child's key is
	 * this node's old key.
	 */
	public void learn(String correct, String question) {
		// TODO You have to write this
		this.left=new Node(correct);
		this.right=new Node(this.item);
		this.item=question;
	}

	/** Sets the key of this node. */
	public void setKey(String key) {
		this.item=key;
	}

	/** Sets the left child of this node. */
	public void setLeft(Node left) {
		this.left=left;
	}

	/** Sets the right child of this node. */
	public void setRight(Node right) {
		this.right=right;
	}

	@Override
	public String toString() {
		return toString("");
	}

	/**
	 * Returns a String representing the subtree rooted at this node, in outline
	 * form.
	 * 
	 * @indent a String of spaces to be added to be beginning of each line.
	 *         Recursive calls pass in a longer indent String.
	 */
	public String toString(String indent) {
		// TODO You have to write this
		Node current=new Node(getKey(),getLeft(),getRight());
		if(current.isLeaf()){
			return indent+getKey();
		}
		return indent + getKey()+"\n"+left.toString(indent + "  ")+"\n"+right.toString(indent + "  ");
	}

}
