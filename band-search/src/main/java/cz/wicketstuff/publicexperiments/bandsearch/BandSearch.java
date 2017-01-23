package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.Collection;
import java.util.Set;

/**
 * @author Martin Strejc (strma17)
 *
 */
public interface BandSearch {
	
	Collection<Set<Integer>> findBands(Collection<Relation> relations);

	Collection<Integer> findBandsSize(Collection<Relation> relations);

}
