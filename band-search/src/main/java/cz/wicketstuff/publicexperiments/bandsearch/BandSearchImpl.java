/**
 * 
 */
package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.Collection;
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
		
		Map<Integer, Set<Integer>> members2band = new LinkedHashMap<>();  
		
		relations
			.stream()
			.filter(rel -> rel.person1 != rel.person2)
			.map(rel -> rel.person1 < rel.person2 ? rel : rel.reverse())
			.sorted()
			.forEach(rel ->
			{
	
				Set<Integer> band = members2band.get(rel.person1);
				if (band == null) {
					band = new LinkedHashSet<>();
				}
						
				band.add(rel.person1);
				band.add(rel.person2);
				
				members2band.put(rel.person1, band);
				members2band.put(rel.person2, band);
	
			}
		);
		
		Set<Set<Integer>> ret = new LinkedHashSet<>();
		for (Set<Integer> b : members2band.values()) {
			ret.add(b);
		}
		
		return ret.stream().collect(Collectors.toList());
		
	}
	
	@Override
	public List<Integer> findBandsSize(Collection<Relation> relations) {
		return findBands(relations).stream().map(Set::size).collect(Collectors.toList());
	}

}
