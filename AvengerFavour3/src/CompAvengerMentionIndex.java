
import java.util.Comparator;

/**
 * This class extends the Comparator interface so that it can compare two Avenger
 * objects so that they can be sorted into the desired total ordering.
 * Date: 11/23/2020
 * @author Jordan Henkelman 
 */
public class CompAvengerMentionIndex extends Avenger implements Comparator<Avenger>{


	/**
	 * Takes in two Avenger objects and compares them by mention index.
	 * @param two Avenger objects for comparing and sorting. 
     * @return the order of the two Avenger objects based on the comparison.
	 */
	@Override
	public int compare(Avenger one, Avenger two) 
	{
		
		return one.getMentionIndex() - two.getMentionIndex();
	}

}