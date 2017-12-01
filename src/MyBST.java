import java.util.*;

/**
 * This class creates binary search trees
 * @author taylorrice
 *
 * @param <E>
 */
public class MyBST<E extends Comparable<E>> {
	//root node of the tree and size (number of elements) of the tree
	BSTNode<E> root;
	
	/**
	 * default constructor for the MyBST class
	 */
	public MyBST(){
		root = null;
	}
	
	/**
	 * Public add method that adds an element to the tree in the proper
	 * location
	 * wrapper method
	 * @param e
	 * @return true if element was added, false otherwise
	 * @throws ClassCastException
	 * @throws NullPointerException
	 */
	public boolean add(E e) throws ClassCastException, NullPointerException{
		if (e == null){
			return false;
		}
		if (root == null){
			root = new BSTNode(e);
			return true;
		}else{
			return add(e, root);
		}	
	}

	/**
	 * private helper method for add method
	 * @param e data being added to the tree
	 * @param root root of the tree
	 * @return true if added, false otherwise
	 */
	private boolean add(E e, BSTNode<E> root){
		if(e.compareTo(root.getData())>0){
			if(root.getRight() != null){
				add(e, root.getRight());
			}else{
				root.setRight(new BSTNode<E> (e));
				return true;
			}
		}else{
			if (root.getLeft() != null){
				add(e, root.getLeft());
			}else{
				root.setLeft(new BSTNode<E> (e));
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method that removes an element from the Binary Search Tree, in a way such that the tree
	 * is still a valid binary search tree after removal
	 * @param o object being removed
	 * @return true if object was removed, false otherwise
	 * @throws ClassCastException
	 * @throws NullPointerException
	 */
	public boolean remove(Object o) throws ClassCastException, NullPointerException{
		//if the private remove method returns null, then nothing was removed, return false
		//otherwise, return true
		if(remove(root, o) == null){
			return false;
		}else{
			return true;
		}	
	}
	
	/**
	 * Private helper method that recursively removes items from the binary search tree
	 * while keeping the tree a valid binary search tree
	 * @param node the node being looked at
	 * @param o the object being removed
	 * @return the node that was removed
	 */
	@SuppressWarnings("unchecked")
	private BSTNode<E> remove(BSTNode<E> node, Object o){
		//cast the object to E
		E item = (E) o;
		
		//if the node is null don't do anything
		if (node == null){
		
		//if the item being removed is smaller than the current node, move left
		}else if ( item.compareTo((E) node.getData()) < 0){
			node.setLeft(remove(node.getLeft(), o));
		//if the item being removed is larger than the current node, move right
		}else if (item.compareTo((E) node.getData()) > 0){
			node.setRight(remove(node.getRight(), o));
		
		//if it is this node, remove it
		}else{
			node = remove(node);
		}
		return node;
	}
	
	/**
	 * Method that gets the predecessor of the node being removed
	 * @param node node being removed
	 * @return value of the predecessor
	 */
	private E getPredecessor(BSTNode<E> node){
		//if there is no predecessor, return null
		if (node.getLeft() == null){
			//this shouldn't happen
			return null;
		//otherwise, take one step left, and then continue stepping right down the tree until you
		//hit the leaf and return it
		}else{
			BSTNode<E> current = node.getLeft();
			while (current.getRight() != null){
				current = current.getRight();
			}
			return current.getData();
		}
	}
	
	/**
	 * Private method that actually removes the node and replaces with predecessor
	 * @param node node being removed
	 * @return the node that was just removed
	 */
	private BSTNode<E> remove(BSTNode<E> node){
		
		if (node.getLeft() == null){
			return node.getRight();
		}if (node.getRight() == null){
			return node.getLeft();
		}
		//get data of predecessor
		E data = getPredecessor(node);
		//change removed node to predecessor
		node.setData(data);
		//recursively do this down the tree
		node.setLeft(remove(node.getLeft(), data));
		return node;
	}
	
	/**
	 * Checks to see if an item is in the binary search tree
	 * @param o item we are looking for
	 * @return true if element is present, false otherwise
	 * @throws ClassCastException
	 * @throws NullPointerException
	 */
	public boolean contains(Object o) throws ClassCastException, NullPointerException{
		//return private helper method
		return containsTraversal(root, (E) o);
	}
	
	/**
	 * helper method that recursively looks to see if an element is contained in the BST
	 * @param node current node
	 * @param data item we are looking for
	 * @return
	 */
	private boolean containsTraversal(BSTNode<E> node, E data){
		//if the current node is not null
		if (node != null){
			//recursively look down the left and right subtrees
			containsTraversal(node.getLeft(), data);
			containsTraversal(node.getRight(), data);
			//if the node contains the element, return true, done
			if (node.getData().equals(data)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * finds the smallest element in the BST
	 * @return smallest element in BST
	 * @throws NoSuchElementException
	 */
	public E first() throws NoSuchElementException{
		BSTNode<E> current = root;
		//look at the left child down the tree until there are no more left children,
		//return leftmost leaf
		while (current.getLeft() != null){
			current = current.getLeft();
		}
		return current.getData();
	}

	/**
	 * finds the largest element in the BST
	 * @return largest element in BST
	 * @throws NoSuchElementException
	 */
	public E last() throws NoSuchElementException{
		BSTNode<E> current = root;
		//look as right child down the tree until rightmost leaf is found, return it
		while (current.getRight() != null){
			current = current.getRight();
		}
		return current.getData();
	}
	
	/**
	 * Returns string representation of the BST
	 */
	public String toString(){
		return "[" + inorderTraversal(root) + "]";
	}
	
	/**
	 * Traversal of the BST to produce a string representation
	 * @param node current node
	 * @return string of items in the tree in ascending order, separated by commas
	 */
	private String inorderTraversal(BSTNode<E> node){
		
		StringBuilder BSTString = new StringBuilder();
		//if the node is null, do not add it as a string
		if (node == null) return "";
		
		//use in order traversal to add each item to the string builder in ascending order
		BSTString.append(inorderTraversal(node.getLeft()));
		BSTString.append(node.getData().toString()+ ", ").toString();	
		BSTString.append(inorderTraversal(node.getRight()));
		return (BSTString.toString());
	}
}
