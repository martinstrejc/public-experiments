package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.Collection;
import java.util.Set;

/**
 * @author Martin Strejc (strma17)
 *
 */
public interface BandSearch {
	
	Set<Set<Integer>> findBinds(Collection<Relation> relations);

}
