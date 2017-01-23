/**
 * 
 */
package cz.wicketstuff.publicexperiments.bandsearch;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Martin Strejc (strma17)
 *
 */
public class BandSearchImpl implements BandSearch {

	@Override
	public Collection<Set<Integer>> findBands(Collection<Relation> relations) {
		
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
		
		return members2band
				.values()
				.stream()
				.collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);		
	}
	
	@Override
	public Collection<Integer> findBandsSize(Collection<Relation> relations) {
		return findBands(relations)
				.stream()
				.map(Set::size)
				.collect(Collectors.toList());
	}

}
