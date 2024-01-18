import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class AddFlightWindow {

	private JFrame frmAddFlight;
	JTextPane textPane;
	JTextPane textPane_1;
	JTextPane textPane_1_2;
	JTextPane textPane_1_1;
	JTextPane textPane_1_1_1;
	JTextPane textPane_1_1_1_1;
	JTextPane textPane_1_1_1_1_1;
	JTextPane textPane_1_1_1_1_1_1;
	JTextPane textPane_1_1_1_1_1_1_1;
	JTextPane textPane_1_1_1_1_1_1_1_1;
	
	private final JButton btnNewButton = new JButton("SUBMIT");
	JLabel lblNewLabel_2;
	private JTextPane textPane_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFlightWindow window = new AddFlightWindow();
					window.frmAddFlight.setLocationRelativeTo(null);
					window.frmAddFlight.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddFlightWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddFlight = new JFrame();
		frmAddFlight.setBounds(100, 100, 442, 815);
		frmAddFlight.setLocationRelativeTo(null);
		frmAddFlight.setVisible(true);
		frmAddFlight.setTitle("Add Flight");
		frmAddFlight.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddFlight.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Flight Number:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(10, 39, 130, 18);
		lblNewLabel.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1_2 = new JLabel("Origin:");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2.setBounds(10, 105, 130, 18);
		lblNewLabel_1_2.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1_2);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(188, 105, 167, 28);
		frmAddFlight.getContentPane().add(textPane_1);
		
		JLabel lblNewLabel_1 = new JLabel("Destination:");
		lblNewLabel_1.setBounds(10, 181, 130, 18);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1);
		
		JTextPane textPane_1_1 = new JTextPane();
		textPane_1_1.setBounds(188, 181, 167, 28);
		frmAddFlight.getContentPane().add(textPane_1_1);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Arrival Time:");
		lblNewLabel_1_2_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2_1.setBounds(10, 261, 130, 18);
		lblNewLabel_1_2_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1_2_1);
		
		JTextPane textPane_1_1_1 = new JTextPane();
		textPane_1_1_1.setBounds(188, 261, 167, 28);
		frmAddFlight.getContentPane().add(textPane_1_1_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Departure Time:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setBounds(10, 343, 130, 18);
		lblNewLabel_1_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1_1);
		
		JTextPane textPane_1_1_1_1 = new JTextPane();
		textPane_1_1_1_1.setBounds(188, 343, 167, 28);
		frmAddFlight.getContentPane().add(textPane_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Arrival Date:");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1.setBounds(10, 429, 130, 18);
		lblNewLabel_1_1_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1_1_1);
		
		JTextPane textPane_1_1_1_1_1 = new JTextPane();
		textPane_1_1_1_1_1.setBounds(188, 429, 167, 28);
		frmAddFlight.getContentPane().add(textPane_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Departure Date:");
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1_1.setBounds(10, 516, 130, 18);
		lblNewLabel_1_1_1_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1_1_1_1);
		
		JTextPane textPane_1_1_1_1_1_1 = new JTextPane();
		textPane_1_1_1_1_1_1.setBounds(188, 506, 167, 28);
		frmAddFlight.getContentPane().add(textPane_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("AircraftID:");
		lblNewLabel_1_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1_1_1.setBounds(10, 594, 130, 18);
		lblNewLabel_1_1_1_1_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Base Price:");
		lblNewLabel_1_1_1_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1_1_1_1.setBounds(10, 658, 130, 18);
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmAddFlight.getContentPane().add(lblNewLabel_1_1_1_1_1_1);
		
		JTextPane textPane_1_1_1_1_1_1_1_1 = new JTextPane();
		textPane_1_1_1_1_1_1_1_1.setBounds(188, 652, 167, 24);
		frmAddFlight.getContentPane().add(textPane_1_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_2 = new JLabel(" ");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(51, 737, 335, 28);
		frmAddFlight.getContentPane().add(lblNewLabel_2);
		
		frmAddFlight.getContentPane().add(btnNewButton);
		
		JTextPane textPane_1_2 = new JTextPane();
		textPane_1_2.setBounds(188, 39, 167, 28);
		frmAddFlight.getContentPane().add(textPane_1_2);
		
		JTextPane textPane_1_1_1_1_1_1_1 = new JTextPane();
		textPane_1_1_1_1_1_1_1.setBounds(188, 580, 167, 32);
		frmAddFlight.getContentPane().add(textPane_1_1_1_1_1_1_1);
		btnNewButton.setBounds(10, 700, 130, 30);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAddFlight.dispose();
				new AdminMainFrame();
			}
		});
		btnBack.setBounds(256, 696, 130, 30);
		frmAddFlight.getContentPane().add(btnBack);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate departureDate = DataTypeFormatConverter.stringToLocalDate(textPane_1_1_1_1_1_1.getText()) ;
				LocalDate arrivalDate = DataTypeFormatConverter.stringToLocalDate(textPane_1_1_1_1_1.getText()) ;
				LocalTime departureTime = DataTypeFormatConverter.stringToLocalTime(textPane_1_1_1.getText()) ;
				LocalTime arrivalTime = DataTypeFormatConverter.stringToLocalTime(textPane_1_1_1_1.getText());
				double baseFare = DataTypeFormatConverter.stringToDouble(textPane_1_1_1_1_1_1_1_1.getText());
				Flight newAdminFlight = new Flight(textPane_1_2.getText(), textPane_1.getText(), textPane_1_1.getText(), departureDate,  
						departureTime, arrivalDate, arrivalTime, textPane_1_1_1_1_1_1_1.getText(), baseFare);
				newAdminFlight.insertFlight();
				lblNewLabel_2.setText("Record added to the Database.");
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				lblNewLabel_2.setText("Record added to the Database.");
				frmAddFlight.dispose();
				new AdminMainFrame();
				
			}
		});

	}
	public JTextPane getTextPane() {
		return textPane_2;
	}
}
