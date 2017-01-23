package cz.wicketstuff.publicexperiments.bandsearch;

import java.io.Serializable;

/**
 * @author Martin Strejc (strma17)
 *
 */
public class Relation implements Serializable, Comparable<Relation> {
	
	private static final long serialVersionUID = 1L;
	
	public final int person1;
	public final int person2;

	public Relation(int person1, int person2) {
		super();
		this.person1 = person1;
		this.person2 = person2;
	}
	
	public Relation reverse() {
		return new Relation(person2, person1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + person1;
		result = prime * result + person2;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relation other = (Relation) obj;
		if (person1 != other.person1)
			return false;
		if (person2 != other.person2)
			return false;
		return true;
	}

	@Override
	public int compareTo(Relation o) {
		int c1 = o.person1 - o.person2; 
		return c1 == 0 ? o.person1 - o.person2 : c1;
	}

	
}
