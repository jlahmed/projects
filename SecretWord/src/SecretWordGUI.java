import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 
	
/**
 * @author Jubayer Ahmed
 *
 */
public class SecretWordGUI implements ActionListener{

	private JTextField inputField; 
	private JTextArea display;
    private String inputString;
    private SecretWord secret;
    private int count = 0;

    public SecretWordGUI(String title) {
        this.secret = new SecretWord();

        JFrame jfrm = new JFrame(title);
        jfrm.setLayout(new FlowLayout());
        jfrm.setSize(450, 290);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel prompt2 = new JLabel("You get 10 wrong guesses.");
        jfrm.add(prompt2);

        JLabel prompt1 = new JLabel("Input a single character to guess the secret word (hit ENTER):");
        jfrm.add(prompt1);

        this.inputField = new JTextField(1);
        this.inputField.setText("");
        this.inputField.addActionListener(this);
        jfrm.add(inputField);

        this.display = new JTextArea(10, 30);
        this.display.append("The secret word: " + secret.getDisplayedWord() + "\n");

        // Adding JScrollPane to the JTextArea
        JScrollPane scrollPane = new JScrollPane(display);
        jfrm.add(scrollPane);
        
        //Add restart button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Restart the application by creating a new instance of SecretWordGUI
                jfrm.dispose(); // Close the current window
                new SecretWordGUI("Guess the secret word"); // Open a new window
            }
        });
        jfrm.add(restartButton, BorderLayout.SOUTH);

        jfrm.setVisible(true);
    }

	public void actionPerformed(ActionEvent evt)  { 
		if ( evt.getSource() == inputField && count < 10) { 
			
			this.inputString = inputField.getText();
	        this.inputField.setText("");
	        boolean guess = secret.makeGuess(inputString.charAt(0));
	        
	        if (!solved(secret.getDisplayedWord())) {
		        if (guess == true) 
		            this.display.append ("That Guess Was Right " + secret.getDisplayedWord() + "\n");
		        else 
		            this.display.append ("That Guess Was Wrong " + secret.getDisplayedWord() + "\n");
		        	count++;
		        	if (count > 9) {
		        		this.display.append ("You guessed wrong too many times. Press restart.\n");
		        	}
		        	
			}else {
				this.display.append ("That Guess Was Right " + secret.getDisplayedWord() + "\n");
				this.display.append ("You have won! Press restart to play again or x to quit\n");
			}
		}
	}
	
	public boolean solved(String s) {
		char[] dp = s.toCharArray();
		boolean solved = true;
		for (int i = 0; i < s.length(); i++) {
			if (dp[i] == '*') {
				solved = false;
			}
		}
		
		return solved;
	}

	public static void main(String[] args) {

		//Starting the GUI application
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SecretWordGUI("Guess the secret word");
			}
		});

	}

}

