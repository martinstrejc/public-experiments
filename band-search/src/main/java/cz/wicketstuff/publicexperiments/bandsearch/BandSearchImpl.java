/**
 * 
 */
package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Martin Strejc (strma17)
 *
 */
public class BandSearchImpl implements BandSearch {

	@Override
	public List<Set<Integer>> findBands(Collection<Relation> relations) {
		final Collection<Relation> normalizedRelations = normalize(relations);
		
		Map<Integer, Integer> members2owner = new HashMap<>();
		Map<Integer, Set<Integer>> bands = new LinkedHashMap<>();  
		
		
		for (Relation rel : normalizedRelations) {
			final int p1 = rel.person1;
			final int p2 = rel.person2;
			if (p1 == p2) {
				continue;
			}
			Integer owner = members2owner.get(p1);
			if (owner == null) {
				owner = p1;
				members2owner.put(p1, owner);
				Set<Integer> band = bands.get(owner);
				if (band == null) {
					band = new LinkedHashSet<>();
					band.add(p1);
					bands.put(owner, band);
				} else {
					band.add(p1);
				}
			} else {
				Set<Integer> band = bands.get(owner);
				if (band == null) {
					band = new LinkedHashSet<>();
					band.add(p1);
					bands.put(owner, band);
				} else {
					band.add(p1);
				}				
			}

			
			owner = members2owner.get(p1);
			if (owner == null) {
				owner = p1;
				members2owner.put(p2, owner);
				Set<Integer> band = bands.get(owner);
				if (band == null) {
					band = new LinkedHashSet<>();
					band.add(p2);
					bands.put(owner, band);
				} else {
					band.add(p2);
				}
			} else {
				Set<Integer> band = bands.get(owner);
				if (band == null) {
					band = new LinkedHashSet<>();
					band.add(p2);
					bands.put(owner, band);
				} else {
					band.add(p2);
				}				
			}
			members2owner.put(p2, owner);


		}
		
		final List<Set<Integer>> ret = new LinkedList<>();
		bands.values().forEach(ret::add);
		return ret;
	}

	@Override
	public List<Integer> findBandsSize(Collection<Relation> relations) {
		List<Integer> ret = new ArrayList<>(relations.size());
		findBands(relations).forEach(band -> ret.add(band.size()));
		return ret;
	}

	private static Collection<Relation> normalize(Collection<Relation> relations) {
		Set<Relation> norm = new LinkedHashSet<>(relations.size());
		for (Relation rel : relations) {
			norm.add(rel.person1 < rel.person2 ? rel : rel.reverse());
		}
		return norm;
	}
	
}
