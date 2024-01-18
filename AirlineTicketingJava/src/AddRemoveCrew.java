import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class AddRemoveCrew {

	private JFrame frmAddremoveCrew;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRemoveCrew window = new AddRemoveCrew();
					window.frmAddremoveCrew.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddRemoveCrew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddremoveCrew = new JFrame();
		frmAddremoveCrew.setBounds(100, 100, 330, 270);
		frmAddremoveCrew.setLocationRelativeTo(null);
		frmAddremoveCrew.setVisible(true);
		frmAddremoveCrew.setTitle("Add/Remove Crew");
		
		frmAddremoveCrew.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddremoveCrew.getContentPane().setLayout(null);
		
		JLabel lblNewLabel1 = new JLabel();
		lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblNewLabel1.setBounds(45, 132, 209, 30);
		frmAddremoveCrew.getContentPane().add(lblNewLabel1);
		
		JLabel lblNewLabel = new JLabel("FlightID: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 30, 100, 14);
		frmAddremoveCrew.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(158, 25, 96, 20);
		frmAddremoveCrew.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First Name: ");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblFirstName.setBounds(10, 60, 100, 14);
		frmAddremoveCrew.getContentPane().add(lblFirstName);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(158, 55, 96, 20);
		frmAddremoveCrew.getContentPane().add(textField_1);
		
		JLabel lblLastName = new JLabel("Last Name: ");
		lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastName.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblLastName.setBounds(10, 90, 100, 14);
		frmAddremoveCrew.getContentPane().add(lblLastName);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(158, 85, 96, 20);
		frmAddremoveCrew.getContentPane().add(textField_2);
		
		JLabel lblPosition = new JLabel("Position: ");
		lblPosition.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPosition.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		lblPosition.setBounds(10, 120, 100, 14);
		frmAddremoveCrew.getContentPane().add(lblPosition);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(158, 115, 96, 20);
		frmAddremoveCrew.getContentPane().add(textField_3);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Crew crew = new Crew(textField.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText());
				crew.insertCrew();
				lblNewLabel1.setText("Crew Member Added Successfully.");
			}
		});
		btnNewButton.setBounds(40, 165, 89, 23);
		frmAddremoveCrew.getContentPane().add(btnNewButton);
		
		JButton btnRemove = new JButton("REMOVE");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Crew.deleteCrew(textField.getText(), textField_1.getText(), textField_2.getText(), textField_3.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lblNewLabel1.setText("Crew Member Removed Successfully.");
			}
		});
		btnRemove.setBounds(165, 165, 89, 23);
		frmAddremoveCrew.getContentPane().add(btnRemove);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAddremoveCrew.dispose();
				new AdminMainFrame();
			}
		});
		btnBack.setBounds(108, 197, 89, 23);
		frmAddremoveCrew.getContentPane().add(btnBack);
		
		
	}
}
