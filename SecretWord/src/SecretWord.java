
/**
 * class contains the methods to be used by SecretWordGUI to read and write
 * to the GUI to show if the guess taken by user is correct and to reveal
 * updated word with the letters guessed.
 * 
 * @Jubayer Ahmed 
 *
 */

import java.util.HashMap;
import java.util.Random;

public class SecretWord {
	private String secretWord;
	private String  displayWord;
	
	/**
	 * SecretWord initializes the secret word and creates a masked secret 
	 * word (display word)
	 * 
	 * @param none
	 * 
	 * @return none
	 */
	public SecretWord() {
        HashMap<Integer, String[]> list = new HashMap<>();

        // Adding arrays of strings to the dictionary making up list of possible secret words.
        list.put(3, new String[]{"the", "and", "for", "are", "not", "you", "all", "any", "can", "her", "has", "him", "his", "how", "man", "new", "now", "old", "out", "see"});
        list.put(4, new String[]{"also", "baby", "back", "best", "call", "case", "come", "does", "even", "feel", "find", "free", "full", "give", "good", "here", "home", "just", "keep", "kind"});
        list.put(5, new String[]{"about", "after", "being", "every", "first", "great", "house", "know", "large", "never", "other", "place", "right", "small", "sound", "still", "their", "under", "water", "where"});
        list.put(6, new String[]{"almost", "always", "before", "better", "family", "father", "little", "moment", "mother", "number", "person", "really", "school", "should", "though", "through", "toward", "without", "wonder", "yellow"});
        list.put(7, new String[]{"against", "between", "country", "evening", "however", "morning", "nothing", "picture", "problem", "program", "question", "thousand", "together", "yourself", "baseball", "building", "different", "everyone", "hospital", "important"});
		
        Random random = new Random();
		this.secretWord = list.get(random.nextInt(5) + 3)[random.nextInt(20)];
		this.displayWord = replaceChars(secretWord);
	}
	
	/**
	 * getDisplayedWord is the getter of displayWord
	 * 
	 * @param none
	 * 
	 * @return displayWord represents the masked word
	 */
	public String getDisplayedWord() {
		return this.displayWord;
	}
	
	/**
	 * getSecretWord is the getter of the secret word
	 * 
	 * @param none
	 * 
	 * @return secretWord is the secret word initialized by the constructor
	 */
	public String getSecretWord() {
		return this.secretWord;
	}
	
	/**
	 * replaceChars is used by the constructor to mask the secret word using ***
	 * 
	 * @param s is the secret word
	 * @param c is the character used to masked the secret word
	 * 
	 * @return new String(dp) is the masked secret word
	 */
	public String replaceChars(String s) {
		char[] dp = s.toCharArray();
		
		for (int i = 0; i < s.length(); i++) {
			dp[i] = '*';
		}
		
		return new String(dp);
	}
	
	/**
	 * replaceChars is used to partially mask the secret word based on the 
	 * character guessed by the user of GUI
	 * 
	 * @param s1 is the secret word
	 * @param s2 is the masked word (display word)
	 * @param ch is the character guessed 
	 * 
	 * @return new String (dp) is the partially masked secret word
	 */
	public String replaceChars(String s1, String s2, char ch) {
		char[] dp = s2.toCharArray();
		
		for(int i = 0; i < s1.length(); i++) {
			if (s1.charAt(i) == ch) {
				dp[i] = ch;
			}
		}
		
		return new String(dp);
	}
	
	/**
	 * makeGuess updates the display word with the masked secret word 
	 * returned by replaceChars. 
	 * 
	 * @param ch is the character guessed
	 * 
	 * @return !s.equals(displayWord) is true if the user guess was correct
	 */
	public boolean makeGuess(char ch) {
		String s = displayWord;
		displayWord = this.replaceChars(secretWord, displayWord, ch);

		return !s.equals(displayWord);
	}
	

}
