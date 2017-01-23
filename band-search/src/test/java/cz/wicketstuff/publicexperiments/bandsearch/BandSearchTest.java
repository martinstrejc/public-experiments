package cz.wicketstuff.publicexperiments.bandsearch;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

public class BandSearchTest {

	private BandSearch search = new BandSearchImpl();

	@Test
	public void findBandsSize_complexTest() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(1, 2));
		relations.add(new Relation(2, 3));
		relations.add(new Relation(4, 3));
		relations.add(new Relation(4, 1));
		relations.add(new Relation(2, 1));
		
		relations.add(new Relation(1, 8));
		relations.add(new Relation(7, 5));
		relations.add(new Relation(5, 6));
		relations.add(new Relation(8, 9));
		relations.add(new Relation(10, 10));
		
		assertThat(search.findBandsSize(relations), CoreMatchers.hasItems(6, 3));
	}

	@Test
	public void findBands_complexTest() {
		List<Relation> relations = new LinkedList<>();
		relations.add(new Relation(4, 3));
		relations.add(new Relation(1, 2));
		relations.add(new Relation(2, 3));
		relations.add(new Relation(4, 1));
		relations.add(new Relation(2, 1));
		
		relations.add(new Relation(1, 8));
		relations.add(new Relation(7, 5));
		relations.add(new Relation(5, 6));
		relations.add(new Relation(8, 9));
		relations.add(new Relation(10, 10));
		
		List<Set<Integer>> actual = new ArrayList<>(search.findBands(relations));
		assertThat(actual.get(0), CoreMatchers.hasItems(1, 2, 3, 4, 8, 9));
		assertThat(actual.get(1), CoreMatchers.hasItems(5, 6, 7));
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
