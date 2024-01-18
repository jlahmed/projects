import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	AdminMainFrame frame;
	JButton btnNewButton_2;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMainFrame frame = new AdminMainFrame();
					//frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	
	public AdminMainFrame() {
		setTitle("Admin User Main Frame");
		setBounds(100, 100, 600, 650);
		setLocationRelativeTo(null);
		setVisible(true);
		setForeground(new Color(255, 255, 255));
		setFont(new Font("Calibri Light", Font.PLAIN, 12));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));

		setContentPane(contentPane);
		
		JButton btnNewButton_2_1 = new JButton("Modify Flights");
		btnNewButton_2_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnNewButton_2_1.setBounds(175, 381, 250, 100);
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ModifyFlights();	
			}
		});
		contentPane.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Browse Crew");
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BrowseCrew();	
			}
		});
		
		btnNewButton_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnNewButton_1.setBounds(175, 140, 250, 100);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2_1_1 = new JButton("Browse Registered User");
		btnNewButton_2_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BrowseRegisteredUsers();
			}
		});
		btnNewButton_2_1_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnNewButton_2_1_1.setBounds(175, 500, 250, 100);
		contentPane.add(btnNewButton_2_1_1);
		
		JButton btnNewButton_2 = new JButton("Browse Aircraft");
		btnNewButton_2.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnNewButton_2.setBounds(175, 260, 250, 100);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BrowseAircraft();
			}
		});
		
		JButton btnNewButton = new JButton("Browse Flight");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new BrowseFlight();
			}
		});
		btnNewButton.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		btnNewButton.setBounds(175, 20, 250, 100);
		contentPane.add(btnNewButton);
		contentPane.add(btnNewButton_2);
		contentPane.add(btnNewButton_2_1);
	}
}
