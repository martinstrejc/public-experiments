package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Martin Strejc (strma17)
 *
 */
public interface BandSearch {
	
	List<Set<Integer>> findBands(Collection<Relation> relations);

	List<Integer> findBandsSize(Collection<Relation> relations);

}
