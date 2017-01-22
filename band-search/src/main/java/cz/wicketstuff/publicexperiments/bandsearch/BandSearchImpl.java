/**
 * 
 */
package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Martin Strejc (strma17)
 *
 */
public class BandSearchImpl implements BandSearch {

	@Override
	public List<Set<Integer>> findBands(Collection<Relation> relations) {
		Set<Integer> band = new HashSet<>();
		relations.forEach(r -> { band.add(r.person1); band.add(r.person2);});
		List<Set<Integer>> ret = new LinkedList<>();
		ret.add(band);
		return ret;
	}

	@Override
	public List<Integer> findBandsSize(Collection<Relation> relations) {
		List<Integer> ret = new ArrayList<>(relations.size());
		findBands(relations).forEach(band -> ret.add(band.size()));
		return ret;
	}

}
