import java.util.*;

/**
 * This class creates a collection of tree objects that is a binary search tree
 * @author taylorrice
 *
 */
public class TreeCollection extends MyBST<Tree> {
	//arrays that store species and borough counts for more efficient retrieval
	ArrayList<String> speciesList = new ArrayList<String>();
	ArrayList<Borough> boroughList = new ArrayList<Borough>();
	//counter to keep track of total trees
	int totalTrees = 0;
	
	/**
	 * Default constructor for TreeCollection class
	 */
	public TreeCollection(){
		
	}
	
	/**
	 * Method that returns the total number of trees
	 * @return size variable which stores the number of nodes in the binary
	 * search tree
	 */
	public int getTotalNumberOfTrees(){	
		return totalTrees;
		
	}
	
	/**
	 * Method that returns the number of trees matching the given species
	 * @param speciesName species being counted
	 * @return total number of trees of that species
	 */
	public int getCountByTreeSpecies(String speciesName){		
		
		return getCountSpeciesE(speciesName, root);
		
	}
	/**
	 * Private helper method that looks through the BST and counts all trees matching the
	 * given species 
	 * @param speciesName species we are looking for
	 * @param current current node of BST
	 * @return number of trees matching parameter
	 */
	private int getCountSpeciesE(String speciesName, BSTNode<Tree> current){
		//base case
		if (current == null){
			return 0;
		}
		//if it is of the correct species, add and search both children
		if (current.getData().spc_common.equalsIgnoreCase(speciesName)){
			return getCountSpeciesE(speciesName, current.getLeft())+getCountSpeciesE(speciesName, current.getRight())+1;
		
		//otherwise only search the correct child based on comparison
		}if (current.getData().spc_common.compareToIgnoreCase(speciesName) > 0){
			return getCountSpeciesE(speciesName, current.getLeft());
		}else{
			return getCountSpeciesE(speciesName, current.getRight());
		}
	}

	
	/**
	 * Method that counts the total number of trees in the given borough
	 * @param boroName borough being counted
	 * @return number of trees in the given borough
	 */
	public int getCountByBorough(String boroName){
		int count = 0;
		//look through the list of all boroughs, when you find the borough you're looking for,
		//return its count
		for(int i=0; i<boroughList.size(); i++){
			if(boroughList.get(i).name.equalsIgnoreCase(boroName)){
				count = boroughList.get(i).getCount();
			}
		}
		
		return count;
		
	}
	
	
	/**
	 * Method that counts trees of a given species in a given borough
	 * @param speciesName the species being searched for
	 * @param boroname the borough being searched in
	 * @return number of trees of that species in that borough
	 */
	public int getCountByTreeSpeciesBorough(String speciesName, String boroName){
		
		return getCountSpeciesBorough(speciesName, boroName, root);
		
	}
	
	/**
	 * Private helper method that searches the BST and finds objects matching both the
	 * given species and borough and then counts them
	 * @param speciesName species we are searching for
	 * @param boroName borough we are searching in
	 * @param current current node
	 * @return number of tree objects matching the given parameters
	 */
	private int getCountSpeciesBorough(String speciesName, String boroName, BSTNode<Tree> current){
		//base case
		if (current == null){
			return 0;
		}
		//if tree object matches, keep searching in both right and left and add 1
		if (current.getData().spc_common.equalsIgnoreCase(speciesName) && current.getData().boroname.equalsIgnoreCase(boroName)){
			return getCountSpeciesBorough(speciesName,boroName, current.getLeft())+getCountSpeciesBorough(speciesName, boroName, current.getRight())+1;
		//if right species but wrong borough keep searching both right and left but don't add
		}if (current.getData().spc_common.equalsIgnoreCase(speciesName)){
			return getCountSpeciesBorough(speciesName,boroName, current.getLeft())+getCountSpeciesBorough(speciesName, boroName, current.getRight());
		
		//if doesn't match, compare and keep searching
		}if (current.getData().spc_common.compareToIgnoreCase(speciesName) > 0){
			return getCountSpeciesBorough(speciesName, boroName, current.getLeft());
		}else{
			return getCountSpeciesBorough(speciesName, boroName, current.getRight());
		}
	}
	
	/**
	 * Method that takes a string and finds all species names containing that string
	 * @param speciesName String being searched for within the species names
	 * @return a list of all species which contain that String
	 */
	public ArrayList<String> getMatchingSpecies(String speciesName){
		//convert to lower case since all species names are stored in lower case when added
		String specName = speciesName.toLowerCase();
		
		//create a place to store matching species
		ArrayList<String> matchingSpec = new ArrayList<String>();
		
		//iterate through all possible species
		for (int i=0; i<speciesList.size(); i++){
			//if the species contains the string, add that species to the matching list
			if(speciesList.get(i).contains(specName)){
				matchingSpec.add(speciesList.get(i));
			}
		}
		
		//return the matching list
		return matchingSpec;
		
	}
	
	/**
	 * Overridden add method which adds a tree object to the binary search tree, but also keeps
	 * track of the different species and boroughs and keeps counts of both
	 */
	@Override
	public boolean add(Tree tree){
		//array of borough names
		ArrayList<String> boroughs = new ArrayList<String>();
		
		//if the species is already listed, do nothing
		if (speciesList.contains(tree.spc_common)){
			//do nothing
		}else{
			//add new species to the list
			speciesList.add(tree.spc_common);
		}
		
		//add all borough names to borough name array
		for (int i=0; i<boroughList.size();i++){
			boroughs.add(boroughList.get(i).name);
		}
		
		//if the borough of the new tree being added is already present, increment counter up one
		if (boroughs.contains(tree.boroname)){
			int index = boroughs.indexOf(tree.boroname);
			boroughList.get(index).increaseCount();
		}else{
			//otherwise create new borough object and add it to the borough list
			boroughList.add(new Borough(tree.boroname));
		}
		
		//increment up number of total trees
		totalTrees++;
		
		//call superclass add method to actually add to the BST
		return super.add(tree);	
	}
	
	/**
	 * Override of the MyBST remove method. Calls superclass remove method, but also adjusts
	 * totalTree count
	 */
	@Override
	public boolean remove(Object tree){
		boolean removed = super.remove(tree);
		
		if (removed){
			totalTrees -= 1;
		}
		
		return removed;
	}
	
	/**
	 * Override of toString method from MyBST class.
	 * Returns a string of each tree in the Collection
	 * string has tree id, species, and borough; each tree is on a new line
	 */
	@Override
	public String toString(){
		return treeCollectTraversal(root);
	}
	
	/**
	 * Private helper method to traverse the tree and create a string
	 * @param node current node
	 * @return string representation of BST
	 */
	private String treeCollectTraversal(BSTNode<Tree> node){
		
		StringBuilder BSTString = new StringBuilder();
		//only add non-null nodes
		if (node == null) return "";
		
		//in order traversal, creates string of tree_id, species, and borough
		//appends to the string builder
		BSTString.append(treeCollectTraversal(node.getLeft()));
		BSTString.append(node.getData().tree_id + ", " + node.getData().spc_common + ", "+node.getData().boroname + "\n").toString();	
		BSTString.append(treeCollectTraversal(node.getRight()));
		return (BSTString.toString());
	}
}
