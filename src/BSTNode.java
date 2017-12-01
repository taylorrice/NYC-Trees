/**
 * The class creates the nodes for the binary search tree
 * @author taylorrice
 *
 * @param <E>
 */
public class BSTNode<E extends Comparable<E>> implements Comparable<BSTNode<E>> {
	//data fields for left and right children of the node, and the data contained in the node
	private BSTNode<E> left;
	private BSTNode<E> right;
	private E data;
	
	/**
	 * Constructor for BSTNode that creates a new node with the given data
	 * @param data information being stored in the node
	 */
	public BSTNode(E data){
		//check if data is null
		if (data == null) throw new IllegalArgumentException();
		//make data of the node the data given by the parameter
		this.data = data;
	}
	
	/**
	 * Method that returns the right child of the current node
	 * @return right child node
	 */
	public BSTNode<E> getRight(){
			return right;
	}
	
	/**
	 * Method that sets a node as the right child of the current node
	 * @param right node being set as the child
	 */
	public void setRight(BSTNode<E> right){
		this.right = right;
	}
	
	/**
	 * Method that returns the left child of the current node
	 * @return left child node
	 */
	public BSTNode<E> getLeft(){
		return left;
	}
	
	/**
	 * Method that sets a node as the left child of the current node
	 * @param left node being set as the child
	 */
	public void setLeft(BSTNode<E> left){
		this.left = left;
	}
	
	/**
	 * Method that retrieves the data being stored in the current node
	 * @return data stored in current node
	 */
	public E getData(){
		return data;
	}
	
	/**
	 * Method that sets the data of the current node to the parameter given
	 * @param data information being stored in current node
	 */
	public void setData(E data){
		this.data = data;
	}
	
	/**
	 * Method that compares current node to node given in parameter based on the data
	 * stored in the nodes
	 * 
	 * @return integers based on the comparison of the data, negative if it comes before,
	 * 0 if they are the same, and positive if it comes after
	 *
	 */
	@Override
	public int compareTo(BSTNode<E> node2) {
		E data2 = node2.data;
		
		return data.compareTo(data2);

	}
}
