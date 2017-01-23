/**
 * 
 */
package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Martin Strejc (strma17)
 *
 */
public class BandSearchImpl implements BandSearch {

	@Override
	public List<Set<Integer>> findBands(Collection<Relation> relations) {
		
		Map<Integer, Integer> members2owner = new HashMap<>();
		Map<Integer, Set<Integer>> bands = new LinkedHashMap<>();  
		
		relations
			.stream()
			.filter(rel -> rel.person1 != rel.person2)
			.map(rel -> rel.person1 < rel.person2 ? rel : rel.reverse())
			.sorted()
			.forEach(rel ->
			{
	
				Integer owner = members2owner.getOrDefault(rel.person1, rel.person1);
	
				Set<Integer> band = bands.get(owner);
				if (band == null) {
					band = new LinkedHashSet<>();
					bands.put(owner, band);
				}
				
				band.add(rel.person1);
				band.add(rel.person2);
				members2owner.put(rel.person1, owner);
				members2owner.put(rel.person2, owner);
	
			}
		);
		
		return bands.values().stream().collect(Collectors.toList());
	}
	
	@Override
	public List<Integer> findBandsSize(Collection<Relation> relations) {
		return findBands(relations).stream().map(Set::size).collect(Collectors.toList());
	}

}
