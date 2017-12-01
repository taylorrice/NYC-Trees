import java.io.*;
import java.util.*;
/**
 * This class opens a CSV file with data about trees, and organizes the data so that
 * it is searchable for the user. It also calculates percentages based on borough and 
 * type of tree and supplies that information to the user depending on what species they
 * were searching for.
 * 
 * @author Taylor Rice
 *
 */
public class NYCStreetTrees {

	public static void main(String[] args) {
		Scanner fileScan = null;
		TreeCollection allTrees = new TreeCollection();
		
		//try/catch block attempts to open the file input as an argument and parse it 
		//into an array of the information on each line
		try{
			File file = new File(args[0]);
			fileScan = new Scanner(file);
			
			//as long as there is more data, continue to import and create an additional tree
			while (fileScan.hasNextLine()){
				String line = fileScan.nextLine();
				ArrayList<String> treeData = splitCSVLine(line);
				
				//retrieve important data from csv file
				String strId = treeData.get(0);
				String strDiam = treeData.get(3);
				String status = treeData.get(6).toLowerCase();
				String health = treeData.get(7).toLowerCase();
				String spc = treeData.get(9).toLowerCase();
				String strZip = treeData.get(25);
				String boro = treeData.get(29).toLowerCase();
				String strX = treeData.get(39);
				String strY = treeData.get(40);
				
				//try to format the numerical data properly, catch any with invalid argument and skip that line
				try{
					int id = Integer.parseInt(strId);
					int diam = Integer.parseInt(strDiam);
					int zip = Integer.parseInt(strZip);
					double x = Double.parseDouble(strX);
					double y = Double.parseDouble(strY);
					

					Tree tree = new Tree(id, diam, status, health, spc, zip, boro, x, y);
					allTrees.add(tree);
				} catch (IllegalArgumentException e){
					continue;
				}	
			}
			
			
		
		//catch error if there is no file name in the arguments
		}catch(ArrayIndexOutOfBoundsException f){
			System.err.println("Error: No file input as an argument.");
			System.exit(1);
		
		//catch if the file does not exist
		}catch(FileNotFoundException e){
			System.err.printf("Error: File %s Not Found.\n", args[0]);
			System.exit(1);
		}
			fileScan.close();
			
		String userInput = "";
		Scanner userScan = new Scanner(System.in);
		
		//as long as the user has not quit the program continue to run
		while (!(userInput.equalsIgnoreCase("quit"))){
			//prompt user for species name
			System.out.println("Enter the tree species to learn more about it (\"quit\" to stop):");
			userInput = userScan.nextLine();
			ArrayList<String> matchingSpecies = (ArrayList<String>) allTrees.getMatchingSpecies(userInput);
				//if the user quits, exit the loop and end the program, otherwise check for the species they input
				//and return the names of species matching the input
				if (userInput.equalsIgnoreCase("quit")){
					continue;
				}if (matchingSpecies.size() == 0){
					System.out.println("No matching species found.");
				}else{
					System.out.println("All matching entries:");
					for(int x=0; x<matchingSpecies.size(); x++){
						System.out.println(matchingSpecies.get(x));
					}
					
					//arrays to hold numerical data about each borough based on the species entered by the user
					String boroughs[] = {"manhattan", "brooklyn", "bronx", "staten island", "queens"};
					int speciesTotals[] = new int[5];
					int boroughTotals[] = new int[5];
					double percentages[] = new double[5];
					
					
					//iterates through an array of boroughs, and for each borough collect information and put it in the 
					//proper array
					for (int x=0; x<boroughs.length; x++){
						int speciesTotal = 0;
						//finds how many of the species in each borough
						for (int y=0; y<matchingSpecies.size(); y++){
							speciesTotal = speciesTotal + allTrees.getCountByTreeSpeciesBorough(matchingSpecies.get(y), boroughs[x]);
						}
						
						speciesTotals[x] = speciesTotal;
						//finds how many trees in each borough
						int boroughTotal = allTrees.getCountByBorough(boroughs[x]);
						boroughTotals[x] = boroughTotal;
						//finds percentage of trees in each borough that are the given species
						double percentage = getPercent(speciesTotals[x], boroughTotals[x]);
						percentages[x] = percentage;
					}
					
					//collects data on all trees (not borough specific) in relation to the entered species
					int totalTrees = allTrees.getTotalNumberOfTrees();
					int totalSpecies = 0;
					for(int i=0; i<matchingSpecies.size(); i++){
						totalSpecies = totalSpecies + allTrees.getCountByTreeSpecies(matchingSpecies.get(i));
					}
					
					double totalPercent = getPercent(totalSpecies, totalTrees);
					
					//output formatted data about the species entered by the user
					System.out.println("\nPopularity in the City:");
					System.out.printf("%-20s %,d %-3s (%,d) %-10s %.2f%%\n", 
							"NYC:", totalSpecies, "", totalTrees, "\t", totalPercent);
					System.out.printf("%-20s %,d %-3s (%,d) %-10s %.2f%%\n", 
							"Manhattan:", speciesTotals[0], "", boroughTotals[0], "\t", percentages[0]);
					System.out.printf("%-20s %,d %-3s (%,d) %-10s %.2f%%\n", 
							"Bronx:",  speciesTotals[2], "", boroughTotals[2], "\t", percentages[2]);
					System.out.printf("%-20s %,d %-3s (%,d) %-10s %.2f%%\n", 
							"Brooklyn:", speciesTotals[1], "", boroughTotals[1], "\t", percentages[1]);
					System.out.printf("%-20s %,d %-3s (%,d) %-10s %.2f%%\n", 
							"Queens:", speciesTotals[4], "", boroughTotals[4], "\t", percentages[4]);
					System.out.printf("%-20s %,d %-3s (%,d) %-10s %.2f%%\n", 
							"Staten Island:", speciesTotals[3], "", boroughTotals[3], "\t", percentages[3]);	
				}
		}
		userScan.close();
	}
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround  multi-word entries that may contain commas). 
	 * 
	 * @param textLine  line of text to be parsed
	 * @return an ArrayList object containing all individual entries/tokens
	 *         found on the line.
	 */
	public static ArrayList<String> splitCSVLine(String textLine) {
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;
		
		//iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			//handle smart quotes as well as regular quotes 
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') { 
				//change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false; 
				}
				else {
					insideQuotes = true; 
					insideEntry = true; 
				}
			}
			else if (Character.isWhitespace(nextChar)) {
				if  ( insideQuotes || insideEntry ) {
					// add it to the current entry
					nextWord.append( nextChar );
				}
				else  { // skip all spaces between entries 
					continue;
				}
			}
			else if ( nextChar == ',') {
				if (insideQuotes) //comma inside an entry 
					nextWord.append(nextChar);
				else { //end of entry found 
					insideEntry = false; 
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			}
			else {
				//add all other characters to the nextWord 
				nextWord.append(nextChar);
				insideEntry = true; 
			}

		}
		// add the last word (assuming not empty)
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}
	/**
	 * This method takes the supplied Tree ArrayList and counts all trees that match the
	 * supplied species name and borough name, then returns the number of trees in the ArrayList
	 * matching those characteristics
	 * 
	 * @param allTrees ArrayList of trees to be searched through
	 * @param matchingSpecies name of species being looked for
	 * @param boroName name of borough being looked for
	 * @return number of trees matching the species and borough supplied
	 * 
	 */
//	public static int allMatchingSpeciesByBoro(TreeList allTrees, ArrayList<String> matchingSpecies, String boroName){
//		//finds length of the TreeList
//		int length = matchingSpecies.size();
//		//counter
//		int total = 0;
//		//iterates through every tree in the TreeList and counts it if it of the correct species and in the correct borough
//		for (int x=0;x<length;x++){
//			total += allTrees.getCountByTreeSpeciesBorough(matchingSpecies.get(x), boroName);
//		}
//		return total;
//	}
	/**
	 * Searches through the supplied ArrayList and counts all trees of the specified species.
	 * 
	 * @param allTrees ArrayList of all trees being considered
	 * @param matchingSpecies name of species being searched for
	 * @return number of trees matching the species supplied
	 * 
	 */
//	public static int allMatchingSpecies(TreeList allTrees, ArrayList<String> matchingSpecies){
//		//finds length of TreeList
//		int length = matchingSpecies.size();
//		//counter
//		int total = 0;
//		//iterates through TreeList and counts trees if they are the correct species
//		for (int x=0;x<length;x++){
//			total += allTrees.getCountByTreeSpecies(matchingSpecies.get(x));
//		}
//		return total;
//	}
	/**
	 * Calculates what percent of the total trees are of a given species
	 * 
	 * @param species number of trees of the specific species
	 * @param total total number of trees
	 * @return decimal number which is the percent of the trees that are the given species
	 * 
	 */
	public static double getPercent(int species, int total){
		double percent;
		//tries to calculate percent, catches the event that the total is 0 (to avoid errors with dividing by 0)
		if (total > 0){
			percent = (double) (species*100)/total;
		}else{
			percent = 0.00;
		}
			
		
		return percent;
	}
}
