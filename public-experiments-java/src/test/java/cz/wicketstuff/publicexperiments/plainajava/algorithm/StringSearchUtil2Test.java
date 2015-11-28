/**
 * 
 */
package cz.wicketstuff.publicexperiments.plainajava.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author <a href="https://cz.linkedin.com/in/martin-strejc-2b317116">Martin Strejc@LinkedIn</a> 
 * @author <a href="http://stackoverflow.com/users/2018247/martin-strejc?tab=profile">MartinStrejc@StackOverflow</a>
 *
 */
public class StringSearchUtil2Test {
	
	private StringSearchUtil2 algorithm = new StringSearchUtil2();

	@Test
	public void test_firstNonRepeatingChar_happy_path() {
		String input = "abcdxyzabc";
		String expectedOutput = "d";
		String output = algorithm.firstNonRepeatingChar(input);
		assertEquals(expectedOutput, output);
	}

	@Test
	public void test_firstNonRepeatingChar_null() {
		assertNull(algorithm.firstNonRepeatingChar(null));
	}

	@Test(expected = IllegalStateException.class)
	public void test_firstNonRepeatingChar_all_repeats() {
		String input = "xabcdabcdeex";
		String output = algorithm.firstNonRepeatingChar(input);
	}

	@Test(expected = IllegalStateException.class)
	public void test_firstNonRepeatingChar_empty_string() {
		String input = "xabcdabcdeex";
		String output = algorithm.firstNonRepeatingChar(input);
	}

	@Test
	public void test_firstNonRepeatingChar_long_repeat() {
		String input = "daaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		String expectedOutput = "d";
		String output = algorithm.firstNonRepeatingChar(input);
		assertEquals(expectedOutput, output);
	}

	@Test
	public void test_firstNonRepeatingChar_long_repeat2() {
		String input = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaad";
		String expectedOutput = "d";
		String output = algorithm.firstNonRepeatingChar(input);
		assertEquals(expectedOutput, output);
	}

	@Test
	public void test_firstNonRepeatingChar_single_char() {
		String input = "d";
		String expectedOutput = "d";
		String output = algorithm.firstNonRepeatingChar(input);
		assertEquals(expectedOutput, output);
	}

}
