import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class RemoveFlight {

	private JFrame frmRemoveFlight;
	JLabel lblNewLabel_1;
	JTextPane textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RemoveFlight window = new RemoveFlight();
					window.frmRemoveFlight.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RemoveFlight() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmRemoveFlight = new JFrame();
		frmRemoveFlight.setBounds(100, 100, 450, 192);
		frmRemoveFlight.setLocationRelativeTo(null);
		frmRemoveFlight.setVisible(true);
		frmRemoveFlight.setTitle("Remove Flight");
		
		frmRemoveFlight.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRemoveFlight.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Flight Number to be Removed:");
		lblNewLabel.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 30, 235, 25);
		frmRemoveFlight.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel(" ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 111, 416, 25);
		frmRemoveFlight.getContentPane().add(lblNewLabel_1);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(257, 35, 126, 20);
		frmRemoveFlight.getContentPane().add(textPane);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Flight.deleteFlight(textPane.getText());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				lblNewLabel_1.setText("Flight Record Removed Successfully.");
			}
		});
		btnNewButton.setBounds(57, 77, 89, 23);
		frmRemoveFlight.getContentPane().add(btnNewButton);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmRemoveFlight.dispose();
				new AdminMainFrame();
			}
		});
		btnBack.setBounds(282, 77, 89, 23);
		frmRemoveFlight.getContentPane().add(btnBack);
		
		
		

	}
}
