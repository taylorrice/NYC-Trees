/**
 * This class creates objects, trees, with representative characteristics that
 * can be used to identify and group them
 * 
 * @author Taylor Rice
 * 
 *
 */
public class Tree implements Comparable<Tree> {
	int tree_id, tree_dbh, zipcode;
	String status, health, spc_common, boroname;
	double x_sp, y_sp;
	
	/**
	 * Creates an object of type Tree
	 * 
	 * @param id unique tree id
	 * @param diam diameter of the tree
	 * @param status status of the tree: "alive", "dead", or "stump"
	 * @param health health of the tree: "good", "fair", or "poor"
	 * @param spc common species name for the tree
	 * @param zip five digit zipcode of the location of the tree
	 * @param boro borough in which the tree is located
	 * @param x 
	 * @param y
	 * 
	 * @throws IllegalArgumentException if parameter is not one of the acceptable values
	 */
	public Tree (int id, int diam, String status, String health, String spc, int zip, String boro, double x, double y){
		spc_common = spc;
		x_sp = x;
		y_sp = y;
		
		//check to make sure ID is valid
		if (id >= 0){
			tree_id = id;
		}else{
			throw new IllegalArgumentException("Tree ID Error");
		}
		//check to make sure diameter is valid
		if (diam >= 0){
			tree_dbh = diam;
		}else{
			throw new IllegalArgumentException("Diameter Error");
		}
		//check to make sure status is valid
		if ((status.equalsIgnoreCase("alive")) || (status.equalsIgnoreCase("dead")) || 
				(status.equalsIgnoreCase("stump")) || (status == null) || (status.equalsIgnoreCase(""))){
			this.status = status;
		}else{
			throw new IllegalArgumentException("Status Error");
		}
		//check to make sure health is valid
		if ((health.equalsIgnoreCase("good")) || (health.equalsIgnoreCase("fair")) || 
				(health.equalsIgnoreCase("poor")) || (health == null) || (health.equalsIgnoreCase(""))){
			this.health = health;
		}else{
			throw new IllegalArgumentException("Health Error");
		} 
		//check to make sure zipcode is valid
		if (zip >= 0 && zip <= 99999){
			zipcode = zip;
		}else{
			throw new IllegalArgumentException("Zipcode Error");
		}
		//check to make sure borough is valid
		if ((boro.equalsIgnoreCase("manhattan")) || (boro.equalsIgnoreCase("bronx")) || 
				(boro.equalsIgnoreCase("brooklyn")) || (boro.equalsIgnoreCase("staten island")) || (boro.equalsIgnoreCase("queens"))){
			boroname = boro;
		}else{
			throw new IllegalArgumentException("Borough Error");
		}	
	}
	
	/**
	 * Returns tree object as a string
	 * 
	 * @return string of tree object
	 * 
	 */
	public String toString() {
		return String.format(tree_id+ ", " + tree_dbh + ", " + status + ", " + health + ", " + 
				spc_common + ", " + "%05d" + ", " + boroname + ", " + x_sp + ", " + y_sp, zipcode);
	}
	
	
	@Override
	/**
	 * Compares two trees and returns an integer which is negative, positive, or 0 depending 
	 * on whether one tree is bigger, smaller, or the same size as the other tree
	 * 
	 * @param tree being compared to the first tree
	 * 
	 * @return positive integer if first tree is bigger, 0 if trees are the same, or 
	 * negative integer if second tree is bigger
	 * 
	 */
	public int compareTo(Tree tree2) {
		int length1 = spc_common.length();
		int length2 = tree2.spc_common.length();
		
		//get smaller of the two string lengths
		int smallestLength = Math.min(length1, length2);
		
		//create an array with the lower case characters of the species name
		char array1[] = spc_common.toLowerCase().toCharArray();
		char array2[] = tree2.spc_common.toLowerCase().toCharArray();
		
		//counter
		int x = 0;
		
		//compares letters of each string
		while (x < smallestLength){
			char c1 = array1[x];
			char c2 = array2[x];
			if (c1 != c2){
				return c1 -c2;
			}
			x++;
		//compares length of two strings
		}if (length1 > length2 || length2 > length1){
			return length1 -length2;
		//compares tree id's in the event that species are the same
		}else if (length1 == length2){
			return tree_id - tree2.tree_id;
		}else{
			return 0;
		}
		
	}
	/**
	 * Checks to see if two trees are equal in tree id and species
	 * 
	 * @param tree2 the second tree being compared
	 * 
	 * @return true if trees have identical tree id's and species, false otherwise
	 * 
	 * @throws IllegalArgumentException if the two trees have the same tree id
	 */
	public boolean equals(Tree tree2) {
		//try to compare the two trees by tree id and species
		try{
			if ((this.tree_id == tree2.tree_id) && (this.spc_common == tree2.spc_common)){
				return true;
			}else if ((this.tree_id != tree2.tree_id) && (this.spc_common == tree2.spc_common)){
				return false;
			}else if((this.tree_id != tree2.tree_id) && (this.spc_common != tree2.spc_common)){
				return false;
			//throw exception if tree id's are the same but species are different
			}else{
				throw new IllegalArgumentException("Error. Two trees cannot have the same tree ID.");
			}
		}catch (IllegalArgumentException e){
			return false;
		}			
	}
	
	/**
	 * Compares the species names of the two trees and returns an integer based on the result
	 * @param t second tree being compared
	 * @return 0 if trees have the same name, or a positive or negative integer if they are different
	 * depending on which is the larger alphabetically
	 */
	public int compareName(Tree t){
		int length1 = spc_common.length();
		int length2 = t.spc_common.length();
		
		//get smaller of the two string lengths
		int smallestLength = Math.min(length1, length2);
		
		//create an array with the lower case characters of the species name
		char array1[] = spc_common.toLowerCase().toCharArray();
		char array2[] = t.spc_common.toLowerCase().toCharArray();
		
		//counter
		int x = 0;
		
		//compares letters of each string
		while (x < smallestLength){
			char c1 = array1[x];
			char c2 = array2[x];
			if (c1 != c2){
				return c1 -c2;
			}
			x++;
		}
		//if no value has been returned and the while loop is completed,
		//then the names are the same and 0 should be returned
		return 0;
		
	}
	
	/**
	 * Checks to see if two trees have the same species name
	 * 
	 * @param t the second tree being compared
	 * @return true if the trees have the same species name, false otherwise
	 */
	public boolean sameName(Tree t){
		int length1 = spc_common.length();
		int length2 = t.spc_common.length();
		
		//if tree names are not the same length, then they cannot be the same
		if (length1 != length2){
			return false;
		}else{
			//create an array with the lower case characters of the species name
			char array1[] = spc_common.toLowerCase().toCharArray();
			char array2[] = t.spc_common.toLowerCase().toCharArray();
			
			//counter
			int x = 0;
			
			//compares letters of each string
			while (x < length1){
				char c1 = array1[x];
				char c2 = array2[x];
				if (c1 != c2){
					return false;
				}
				x++;
			}	
			//if no value has been returned and the while loop is completed,
			//then the names are the same and true should be returned
			return true;
		}
		
	}
}
