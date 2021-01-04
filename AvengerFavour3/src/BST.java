import java.util.*;
/**
 * This class is the binary Search Tree Class that implements Iterable.
 * This is where Avenger data is stored and allows other classes(A3) to iterate through
 * by implementing an iterator. There are 2 other classes within this 
 * class: 
 * 
 * - A BST node class that used to get or set data 
 * - Iterator class that helps to iterate through the binary search tree using an 
 * in order traversal
 * 
 * @author johnvaliente
 *
 * @param <T>
 */
public class BST<T extends Comparable<T>> implements Iterable<T>
{
	class BSTNode implements Comparable<BSTNode> 
	{
		private T data;
		private BSTNode left;
		private BSTNode right;

		/**
		 * BSTNode constructor. Takes in data as a parameter to store in the node.
		 * @param d data
		 */
		public BSTNode(T d) 
    {
			setLeft(null);
			setRight(null);
			setData(d);
		}
		public T getData() { return data; }
		public void setData(T d) { data = d; }
		public void setLeft(BSTNode l) { left = l; }
		public void setRight(BSTNode r) { right = r; }
		public BSTNode getLeft() { return left; }
		public BSTNode getRight() { return right; }
		public boolean isLeaf() { return (getLeft() == null) && (getRight() == null); }
		public int compareTo(BSTNode o) {
			return this.getData().compareTo(o.getData());
		}
	}

	private BSTNode root;
	private int size;
	private Comparator<T> comparator;
	private Queue<T> queue = new LinkedList<T>();
	public static final int INORDER = 0;
	public static final int PREORDER = 1;
	public static final int POSTORDER = 2;

	/**
	 * BST parameterless constructor. This BST will be compared using the compare method rather than a comparator.
	 */
	public BST() 
	{
		root = null;
		size = 0;
	}
	
	/**
	 * BST constructor that takes in a comparator as a parameter. Uses that parameter instead of compare.
	 * @param externalComp comparator object
	 */
	public BST(Comparator<T> externalComp) 
	{
		root = null;
		size = 0;
		comparator = externalComp;
	}

	/**
	 * Return the number of nodes in the tree.
	 */
	public int size() 
	{
		return size;
	}
	
	/**
	 * returns data of a specific avenger
	 * 
	 * @param d Avenger type
	 * @return data of Avenger type
	 */
	public T getData(T d)
	{
		return find(d,root);
	}
	
	/**
	 * Return true if element d is present in the tree.
	 */
	public boolean find(T d) 
	{
		
		if (find(d,root) == null)
		{
			return false;
		}
		
		else if (d.equals(find(d,root)))
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}

	/**
	 * Add element d to the tree.
	 */
	public void add(T d) 
	{
		BSTNode n = new BSTNode(d);
		if (root == null) {
			size++;
			root = n;
		} else {
			add(root, n);
		}
	}
	
	/**
	 * Delete a specific node from tree
	 * 
	 * @param d node type Avenger
	 */
	public void delete(T d)
	{
		delete(root, d);
	}

	/**
	 * Return the height of the tree.
	 */
	public int height() 
	{
		return height(root);
	}

	/**
	 * Not used for assignment 3 but added for code reuseability 
	 * 
	 *For printing the tree in order
	 */
	public void printInOrder() 
	{
		traverse(root, INORDER);
	}
	/**
	 * Not used for assignment 3 but added for code reuseability 
	 * 
	 *For printing the tree pre order
	 */
	public void printPreOrder() 
	{
		traverse(root, PREORDER);
	}
	/**
	 * Not used for assignment 3 but added for code reuseability 
	 * 
	 *For printing the tree post order
	 */
	public void printPostOrder() 
	{
		traverse(root, POSTORDER);
	}
	
 
	private T find(T d, BSTNode r) 
	{
		if (r == null)
			return null;
		int c = d.compareTo(r.getData());
		if (c == 0)
			return r.getData();
		else if (c < 0)
			return find(d, r.getLeft());
		else
			return find(d, r.getRight());
	}

	/**
	 * add a new node in the binary tree
	 * 
	 * @param r - root
	 * @param n - node to add
	 */
	private void add(BSTNode r, BSTNode n) 
	{	
		int c = compare(n.getData(), r.getData());
		
		if (c < 0) 
		{				
			if( r.getLeft() == null) 
			{
				r.setLeft(n);
				size++;
			}
			else 
			{
				add(r.getLeft(),n);
			}			
		}
		else if(c > 0) 
		{	
			
			if(r.getRight() == null) 
			{
				r.setRight(n);
				size++;
			}
			else 
			{
				add(r.getRight(),n);	
			}
		}
	}
	
	/**
	 * Compares the alias' of two objects of type T after casting them to Avengers. Uses a comparator for the 
	 * comparison if a comparator was passed into the BST constructor.
	 * @param a1 object 1 - later casted to Avenger
	 * @param a2 object 2 - later casted to Avenger
	 * @return the desired ordering of the two Avengers. The ordering varies depending on the comparator or lack thereof. 
	 */
	private int compare(T a1, T a2)
	{
		
		// If there is no comparator: compares alphabetically by alias
		if (comparator == null)
		{
			return ((Avenger) a1).getHeroAlias().compareTo(((Avenger) a2).getHeroAlias());
		}

		// Uses the comparator that was passed into the constructor
		else
		{
			return comparator.compare(a1, a2);
		}
	}
	
	/**
	 * Deletes the node that contains the data passed into this method as T d
	 * @param root current root of the BST, starts with actual root of tree
	 * @param d the data to be deleted 
	 * @return the updated root of the tree
	 */
	private BSTNode delete(BSTNode root, T d)
	{
		
		if (root == null)
		{
			return null;
		}

		int c = d.compareTo(root.getData());
		
		if (c < 0)
		{	
			//set the current root to updated left child node
			root.setLeft(delete(root.getLeft(), d));
		} 
		
		else if (c > 0)
		{
			//set the current root to updated right child node
			root.setRight(delete(root.getRight(), d));
		} 
		
		else
		{
			// Case3 : 2 Children
			if (root.getLeft() != null && root.getRight() != null)
			{
				BSTNode tempNode = root;			
				// find max of left subtree
				BSTNode maxNodeLeftSub = maxValue(tempNode.getLeft());

				// current root is set to the maxNodeLeftSub
				root.setData(maxNodeLeftSub.getData());

				// setting max node to left side to null(basically deleting it)
				root.setLeft(delete(root.getLeft(), maxNodeLeftSub.getData()));
				
				size--;

			}
			
			//Case2: One Child
			else if (root.getLeft() != null && root.getRight() == null)
			{
				root = root.getLeft();
				size--;
				return root;
			} 
			
			else if (root.getRight() != null && root.getLeft() == null)
			{
				root = root.getRight();
				size--;
				return root;
			} 
			
			//Case 1: No child
			else
			{
				// no child
				root = null;
				size--;
				return root;
			}

		}
		return root;

	}
	
	/**
	 * Finds the minimum value of the root
	 * @param r root
	 * @return minimum value from the left subtree
	 */
	private BSTNode minValue(BSTNode r) 
	{
		
		if(r.getLeft() == null) 
		{
			return r;
		}
		else 
		{
			return minValue(r.getLeft());
		}
		
	}
	
	/**
	 * Finds the maximum value of the root
	 * @param r root 
	 * @return the maximum value from the right subtree
	 */
	private BSTNode maxValue(BSTNode r) 
	{
		
		if(r.getRight() == null) 
		{
			return r;
		}
		else 
		{
			return maxValue(r.getRight());
		}
		
	}

	/**
	 * Returns the height of a tree
	 * 
	 * @param r Node
	 * @return height of the given node
	 */
			
	public int height(BSTNode r) {
		int h = -1;
		int hLeft = 0;
		int hRight = 0;

		if(r == null) 
		{
			return h;
		}
		else 
		{
			hLeft = height(r.getLeft());
			hRight = height(r.getRight());
			
			h = Math.max(hRight, hLeft) + 1;
		}
		
		return h;
	}
	
	/**
	 * Traverses through the a tree in 3 different ways. Inorder, Preorder, and Postorder
	 * @param r
	 * @param orderType
	 */
	private void traverse(BSTNode r, int orderType) {
		
		if(r != null) {
			switch (orderType) 
			{
				case INORDER:
				traverse(r.getLeft(), orderType);
				visit(r);
				traverse(r.getRight(), orderType);
				break;
				case PREORDER:
				visit(r);
				traverse(r.getLeft(), orderType);
				traverse(r.getRight(), orderType);
				break;
				case POSTORDER:
				traverse(r.getLeft(), orderType);
				traverse(r.getRight(), orderType);
				visit(r);
				break;
			
			}
		}
	}
	
	/**
	 * Visits a node and adds the node in a queue
	 * @param r Node
	 */
	private void visit(BSTNode r) 
	{	
		if (r != null) 
		{
			
			queue.add(r.getData());
		}
			
	}
	
	/**
	 * BST class that iterates in order. Implements an iterator. 
	 * 
	 * @author johnvaliente
	 *
	 */
	private class BSTIteratorInOrder implements Iterator
	{
		
		/**
		 * Clears the queue and proceeds to traverse in order starting from the root
		 */
		public BSTIteratorInOrder() 
		{
			queue.clear();
			traverse(root, INORDER);	
		
		}
		/**
		 * returns if queue is not empty 
		 */
		@Override
		public boolean hasNext() 
		{
			return !queue.isEmpty();
		}
		
		/**
		 * returns the Node to be removed inside the queue
		 */
		@Override
		public Object next() 
		{
			return queue.remove();
		}
		
	}

	/**
	 * Creates and returns a new InOrder iterator.
	 * @return the new inOrder iterator
	 */
	@Override
	public Iterator<T> iterator() 
	{
		
		return new BSTIteratorInOrder();
	}


}