package cz.wicketstuff.publicexperiments.bandsearch;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class BandSearchTest {

	private BandSearch search = null;
	
	
	public void findBands() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(1, 2));
		fail("Not yet implemented");
	}
	
	@Test
	public void findBandsSize_1a() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(1, 2));
		assertThat(search.findBandsSize(relations), CoreMatchers.hasItems(2));
	}

	@Test
	public void findBandsSize_1b() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(1, 2));
		relations.add(new Relation(1, 3));
		assertThat(search.findBandsSize(relations), CoreMatchers.hasItems(3));
	}

	@Test
	public void findBandsSize_1c() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(1, 2));
		relations.add(new Relation(2, 3));
		assertThat(search.findBandsSize(relations), CoreMatchers.hasItems(3));
	}

	@Test
	public void findBandsSize_2a() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(1, 2));
		relations.add(new Relation(2, 3));
		relations.add(new Relation(4, 5));
		assertThat(search.findBandsSize(relations), CoreMatchers.hasItems(3, 2));
	}

	@Test
	public void findBandsSize_2b() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(1, 2));
		relations.add(new Relation(2, 3));
		relations.add(new Relation(4, 5));
		relations.add(new Relation(6, 1));
		relations.add(new Relation(7, 5));
		assertThat(search.findBandsSize(relations), CoreMatchers.hasItems(4, 3));
	}

}
