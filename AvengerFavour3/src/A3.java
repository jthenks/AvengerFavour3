import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * COMP 2503 Winter 2020 Assignment 3
 * 
 * This program must read a input stream and keeps track of the frequency at
 * which an avenger is mentioned either by name or alias. The program uses a BST
 * for storing the data. Multiple BSTs with alternative orderings are
 * constructed to show the required output.
 * 
 * @author Maryam Elahi
 * @date Fall 2020
 */

public class A3
{

	public String[][] avengerRoster =
	{
			{ "captainamerica", "rogers" },
			{ "ironman", "stark" },
			{ "blackwidow", "romanoff" },
			{ "hulk", "banner" },
			{ "blackpanther", "tchalla" },
			{ "thor", "odinson" },
			{ "hawkeye", "barton" },
			{ "warmachine", "rhodes" },
			{ "spiderman", "parker" },
			{ "wintersoldier", "barnes" } };

	private int topN = 4;
	private int totalwordcount = 0;
	private int mentionIndex = 0;
	private Scanner input = new Scanner(System.in);
	private Scanner kb;

	private BST<Avenger> alphabeticalBST = new BST<>();
	private BST<Avenger> mentionBST = new BST<Avenger>(new CompAvengerMentionIndex());
	private BST<Avenger> mostPopularBST = new BST<Avenger> (new CompMostPopAvengers());
	private BST<Avenger> leastPopularBST = new BST<Avenger> (new CompLeastPopAvengers());

	public static void main(String[] args) throws FileNotFoundException
	{
		A3 a3 = new A3();

		a3.run();
	}

	public void run() throws FileNotFoundException
	{
		readInput();
		createdAlternativeOrderBSTs();
		printResults();
	}

	private void createdAlternativeOrderBSTs()
	{
		/*
		 * Deletes the avenger "hawkeye"/"barton" from the alphabetical tree use
		 * the tree iterator to do an in-order traversal of the alphabetical tree, and
		 * add avengers to the other trees
		 */

		for (Avenger a : alphabeticalBST)
		{

			if (a.getHeroAlias().equals("hawkeye"))
			{
				alphabeticalBST.delete(a);
			}
			
			else
			{
				mentionBST.add(a);
				mostPopularBST.add(a);
				leastPopularBST.add(a);
			}
		}

	}

	/**
	 * Read the input stream and keep track how many times avengers are mentioned by
	 * alias or last name.
	 * 
	 */
	private void readInput() throws FileNotFoundException
	{
		/*
		 * While the scanner object has not reached end of stream, - read a word. -
		 * clean up the word - if the word is not empty, add the word count. - Check if
		 * the word is either an avenger alias or last name then - Create a new avenger
		 * object with the corresponding alias and last name. - if this avenger has
		 * already been mentioned, increase the frequency count for the object already
		 * in the list. - if this avenger has not been mentioned before, add the newly
		 * created avenger to the list, remember to set the frequency and the mention
		 * order. (Remember to keep track of the mention order by setting the mention
		 * order for each new avenger.)
		 */

		String words;
		String filename;

		kb = new Scanner(System.in);
		System.out.println("Placeholder - enter file name without extensions");
		filename = kb.next() + ".txt";

		File inFile = new File(filename);
		input = new Scanner(inFile);

		while (input.hasNext())
		{
			// Trims and cleans the input word
			words = cleanWord(input.next());

			// Total word counter
			if (!words.equals(""))
			{
				totalwordcount = totalwordcount + 1;
			}

			for (int row = 0; row < avengerRoster.length; row++)
			{

				// If condition statement to detect input stream match
				if (avengerRoster[row][0].equals(words) || avengerRoster[row][1].equals(words))
				{
					//creates avenger from specific 2d array AND adds the avenger object into the alphabeticalBST
					createAvenger(avengerRoster[row][0], avengerRoster[row][1]);
				}
			}
		}

	}

	/**
	 * Creates a new node type avenger, an if else statement that checks if the
	 * avenger is already a part of the mentionList List. If Avenger is already
	 * there it adds one to its frequency. If it is not there, it creates a new node
	 * avenger and adds to the tail.
	 * 
	 * @param alias - alias of the Avenger
	 * @param name  - name of Avenger
	 * @param freq  - initial amount an Avenger is mentioned
	 * @return
	 */
	public void createAvenger(String alias, String name)
	{
		
		Avenger a = new Avenger();
		a.setHeroAlias(alias);
		a.setHeroName(name);
		
		if (alphabeticalBST.find(a))
		{
			alphabeticalBST.getData(a).freqPlusOne();
		}
		
		else
		{
			alphabeticalBST.add(a);
			mentionIndex++;
			alphabeticalBST.getData(a).setMentionIndex(mentionIndex);
		}


	}

	/*
	 * @author Maryam
	 */
	private String cleanWord(String next)
	{
		// First, if there is an apostrophe, the substring
		// before the apostrophe is used and the rest is ignored.
		// Words are converted to all lowercase.
		// All other punctuation and numbers are skipped.
		String ret;
		int inx = next.indexOf('\'');
		if (inx != -1)
			ret = next.substring(0, inx).toLowerCase().trim().replaceAll("[^a-z]", "");
		else
			ret = next.toLowerCase().trim().replaceAll("[^a-z]", "");
		return ret;
	}


	/**
	 * print the results
	 */
	private void printResults()
	{
		System.out.println("Total number of words: " + totalwordcount);
		// Print the number of mentioned avengers after deleting "Barton".
		System.out.println("Number of Avengers Mentioned: " + alphabeticalBST.size());
		System.out.println();

		System.out.println("All avengers in the order they appeared in the input stream:");
		// Print the list of avengers in the order they appeared in the input
		Iterator<Avenger> mention = mentionBST.iterator();
		
		while (mention.hasNext())
		{
			System.out.println(mention.next());
		}
		
		System.out.println();

		System.out.println("Top " + topN + " most popular avengers:");
		// Print the most popular avengers, see the instructions for tie breaking
		

		Iterator<Avenger> mostPop = mostPopularBST.iterator();
		for (int i = 0; i < 4 && mostPop.hasNext(); i++)
		{
			System.out.println(mostPop.next());
		}

		System.out.println();

		System.out.println("Top " + topN + " least popular avengers:");
		// Print the least popular avengers, see the instructions for tie breaking	
		Iterator<Avenger> leastPop = leastPopularBST.iterator();
		for (int i = 0; i < 4 && leastPop.hasNext(); i++)
		{
			System.out.println(leastPop.next());
		}
		
		System.out.println();

		System.out.println("All mentioned avengers in alphabetical order:");
		// Print the list of avengers in alphabetical order

		Iterator<Avenger> alpha = alphabeticalBST.iterator();
		while (alpha.hasNext())
		{
			System.out.println(alpha.next());
		}

		System.out.println();

		// Print the actual height and the optimal height for each of the four
		// trees.
		System.out.println("Height of the mention order tree is : " + mentionBST.height()
				+ " (Optimal height for this tree is : " + optimalHeight(mentionBST.size()) + ")");
		System.out.println("Height of the alphabetical tree is : " + alphabeticalBST.height()
		 + " (Optimal height for this tree is : " + optimalHeight(alphabeticalBST.size()) + ")");
		System.out.println("Height of the most frequent tree is : " + mostPopularBST.height()
				+ " (Optimal height for this tree is : " + optimalHeight(mostPopularBST.size()) + ")");
		System.out.println("Height of the least frequent tree is : " + leastPopularBST.height()
				+ " (Optimal height for this tree is : " +  optimalHeight(leastPopularBST.size()) + ")");
	}
	
  /**
	 * Finds the optimal height of a tree based on its size
	 * Uses the formula log2(N) to determine optimal height
	 * 
	 * @param n - size of the list
	 * @return - optimal height of a tree
	 */
	private int optimalHeight(int n) {

		double h = Math.log10(n)/Math.log10(2);
		
		if(n ==0) {
			return -1;
		}
		
		return (int)Math.round(h);
	}
}