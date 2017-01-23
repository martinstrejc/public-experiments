/**
 * 
 */
package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Martin Strejc (strma17)
 *
 */
public class BandSearchImpl implements BandSearch {

	@Override
	public List<Set<Integer>> findBands(Collection<Relation> relations) {
		final Stream<Relation> normalizedRelations = normalize(relations);
		
		Map<Integer, Integer> members2owner = new HashMap<>();
		Map<Integer, Set<Integer>> bands = new LinkedHashMap<>();  
		
		normalizedRelations
			.filter(rel -> rel.person1 != rel.person2)
			.forEach(rel ->
		{
		
			final int p1 = rel.person1;
			final int p2 = rel.person2;

			Integer owner = members2owner.get(p1);
			if (owner == null) {
				owner = p1;
				members2owner.put(p1, owner);
			}

			Set<Integer> band = bands.get(owner);
			if (band == null) {
				band = new LinkedHashSet<>();
				band.add(p1);
				bands.put(owner, band);
			} else {
				band.add(p1);
			}
			
			band.add(p2);
			members2owner.put(p2, owner);

		}
		);
		
		return bands.values().stream().collect(Collectors.toList());
	}
	
	@Override
	public List<Integer> findBandsSize(Collection<Relation> relations) {
		List<Integer> ret = new ArrayList<>(relations.size());
		findBands(relations).forEach(band -> ret.add(band.size()));
		return ret;
	}

	private static Stream<Relation> normalize(Collection<Relation> relations) {
		Set<Relation> norm = new LinkedHashSet<>(relations.size());
		for (Relation rel : relations) {
			norm.add(rel.person1 < rel.person2 ? rel : rel.reverse());
		}
		return norm.stream().sorted();
	}
	
}
