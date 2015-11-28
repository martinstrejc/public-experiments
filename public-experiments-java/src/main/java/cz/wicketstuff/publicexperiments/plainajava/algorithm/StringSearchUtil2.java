/**
 * 
 */
package cz.wicketstuff.publicexperiments.plainajava.algorithm;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="https://cz.linkedin.com/in/martin-strejc-2b317116">Martin Strejc@LinkedIn</a> 
 * @author <a href="http://stackoverflow.com/users/2018247/martin-strejc?tab=profile">MartinStrejc@StackOverflow</a>
 *
 */
public class StringSearchUtil2 {

	public String firstNonRepeatingChar(String str) {
		if(str == null) {
			return null;
		}
		Map<Character, Integer> charCounts = new LinkedHashMap<Character, Integer>();
		for (char c : str.toCharArray()) {
			Integer cnt = charCounts.get(c);
			cnt = cnt == null ? 1 : cnt+1;
			charCounts.put(c, cnt);
		}
		for (Map.Entry<Character, Integer> e : charCounts.entrySet()) {
			if(e.getValue() == 1) {
				return e.getKey().toString();
			}
		}
		throw new IllegalStateException("Input String doesn't contain any non-repeating character. Input: " + str);
	}
	
}
