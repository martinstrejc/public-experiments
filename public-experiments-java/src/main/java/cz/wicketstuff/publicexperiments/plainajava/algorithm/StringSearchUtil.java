/**
 * 
 */
package cz.wicketstuff.publicexperiments.plainajava.algorithm;

/**
 * @author <a href="https://www.linkedin.com/profile/view?id=ADcAABOzX8UBRcpGQXblfcKkZbqATvUjev70sEM&authType=name&authToken=ybcI">Mohit Uniyal</a>
 *
 */
public class StringSearchUtil {

	public String firstNonRepeatingChar(String str) {
		char c = ' ';
		int pos = 0;
		int len = str.length();
		for (int i = 0; i < len; i++) {
			c = str.charAt(i);
			pos = str.indexOf(c);
			pos = str.indexOf(c, pos + 1);
			if (pos == -1) {
				// System.out.println("non repeating char : " + c);
				return String.valueOf(c);
			}
		}
		return null;
	}
	
}
