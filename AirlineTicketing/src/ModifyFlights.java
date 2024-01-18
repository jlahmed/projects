import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ModifyFlights {

	private JFrame frmModifyFlight;
	private JTextField textField;
	JLabel lblNewLabel_1;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModifyFlights window = new ModifyFlights();
					window.frmModifyFlight.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ModifyFlights() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmModifyFlight = new JFrame();
		frmModifyFlight.setBounds(100, 100, 382, 237);
		frmModifyFlight.setLocationRelativeTo(null);
		frmModifyFlight.setVisible(true);
		frmModifyFlight.setTitle("Modify Flight");
		
		frmModifyFlight.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmModifyFlight.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter New Departure Date: ");
		lblNewLabel.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 220, 14);
		frmModifyFlight.getContentPane().add(lblNewLabel);
		
		JLabel lblSelectEnterNew = new JLabel("Enter New Departure Time: ");
		lblSelectEnterNew.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		lblSelectEnterNew.setBounds(10, 36, 220, 14);
		frmModifyFlight.getContentPane().add(lblSelectEnterNew);
		
		JLabel lblNewLabel_1 = new JLabel("Enter FlightID:");
		lblNewLabel_1.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 119, 220, 14);
		frmModifyFlight.getContentPane().add(lblNewLabel_1);
		
		
		textField = new JTextField();
		textField.setBounds(235, 6, 96, 20);
		frmModifyFlight.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(235, 31, 96, 20);
		frmModifyFlight.getContentPane().add(textField_1);
		
		JLabel lblEnterNewArrival = new JLabel("Enter New Arrival Date: ");
		lblEnterNewArrival.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		lblEnterNewArrival.setBounds(10, 64, 220, 14);
		frmModifyFlight.getContentPane().add(lblEnterNewArrival);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(235, 59, 96, 20);
		frmModifyFlight.getContentPane().add(textField_2);
		
		JLabel lblEnterNewArrival_2 = new JLabel("Enter New Arrival Time: ");
		lblEnterNewArrival_2.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		lblEnterNewArrival_2.setBounds(10, 94, 220, 14);
		frmModifyFlight.getContentPane().add(lblEnterNewArrival_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(235, 89, 96, 20);
		frmModifyFlight.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(235, 115, 96, 20);
		frmModifyFlight.getContentPane().add(textField_4);
		
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDateTime departure = DataTypeFormatConverter.stringToLocalDateTime(textField.getText(), textField_1.getText());
				LocalDateTime arrival = DataTypeFormatConverter.stringToLocalDateTime(textField_2.getText(), textField_3.getText());
				
				try {
					Flight.changeFlightDate(departure, arrival, textField_4.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				lblNewLabel_1.setText("Selected Field Modified Successfully.");
			}
		});
		btnNewButton.setBounds(20, 144, 104, 23);
		frmModifyFlight.getContentPane().add(btnNewButton);
		
		JButton btnBack = new JButton("BACK");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmModifyFlight.dispose();
				new AdminMainFrame();
			}
		});
		btnBack.setBounds(213, 144, 104, 23);
		frmModifyFlight.getContentPane().add(btnBack);


	}
}
