/**
 * Class that creates borough objects which store the name of the borough and how many trees total are in that borough
 * @author taylorrice
 *
 */
public class Borough {
	protected String name;
	private int count = 0;
	
	/**
	 * Constructor that creates a new borough with one tree
	 * @param name name of new borough
	 */
	public Borough(String name){
		this.name = name;
		count++;
	}
	
	/**
	 * Getter method for count data field
	 * @return number of trees in the borough
	 */
	public int getCount(){
		return count;
	}
	
	/**
	 * Method that adds one to the current count
	 */
	public void increaseCount(){
		count++;
	}
	
	/**
	 * Method that returns borough as a String
	 */
	public String toString(){
		return name + ", "+count;
	}
}
