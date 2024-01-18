import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

public class BrowseFlight {

	private JFrame frmBrowseFlights;
	private JTextField textField;
	private JTable table;
	int clickcounter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrowseFlight window = new BrowseFlight();
					window.frmBrowseFlights.setLocationRelativeTo(null);
					window.frmBrowseFlights.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BrowseFlight() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBrowseFlights = new JFrame();
		frmBrowseFlights.setBounds(100, 100, 1003, 574);
		frmBrowseFlights.setLocationRelativeTo(null);
		frmBrowseFlights.setVisible(true);
		frmBrowseFlights.setTitle("Browse Flights");
		frmBrowseFlights.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBrowseFlights.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Departure Date: ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		lblNewLabel.setBounds(22, 11, 188, 30);
		frmBrowseFlights.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		textField.setColumns(10);
		textField.setBounds(220, 11, 163, 27);
		frmBrowseFlights.getContentPane().add(textField);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(22, 47, 698, 27);
		frmBrowseFlights.getContentPane().add(lblNewLabel_1);
		DefaultTableModel model = new DefaultTableModel();
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 79, 977, 387);
        frmBrowseFlights.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        frmBrowseFlights.getContentPane().add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(model);
		
		JButton btnNewButton_3 = new JButton("SUBMIT");
		clickcounter = 0;
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clickcounter > 0)
					lblNewLabel_1.setText("You are clicking Submit multiple times, please go back to main window and come back. ");
				else {
				//if (checkDateFormat.isValidDateFormat(textField.getText())) {
				lblNewLabel_1.setText("Flight results shown below: ");
				clickcounter++;
					try {
						List<Flight> flightList = new ArrayList<>();
						flightList = Flight.getFlightsAdmin(textField.getText());
						model.addColumn("FlightID");
						model.addColumn("Origin");
						model.addColumn("Departure");
						model.addColumn("DepartureDate");
						model.addColumn("DepartureTime");
						model.addColumn("ArrivalDate");
						model.addColumn("ArrivalTime");
						model.addColumn("AircraftID");
						model.addColumn("BaseRate");
						
						for (Flight data : flightList) {
							
				            model.addRow(new Object[]{data.FlightID, data.Origin, data.Destination, 
				            		data.DepartureDate, data.DepartureTime, data.ArrivalDate, data.ArrivalTime, data.AircraftID, data.BasePrice});
				        }
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					table.getTableHeader().setReorderingAllowed(false);
                    table.repaint();
                    table.revalidate();;
					
			}
			}
		});
		
		btnNewButton_3.setFont(new Font("Calibri Light", Font.PLAIN, 12));
		btnNewButton_3.setBounds(430, 15, 108, 23);
		frmBrowseFlights.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_1 = new JButton("ADD");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBrowseFlights.dispose();
				new AddFlightWindow();	
			}
		});
		btnNewButton_1.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		btnNewButton_1.setBounds(22, 477, 119, 23);
		frmBrowseFlights.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("REMOVE");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBrowseFlights.dispose();
				new RemoveFlight();	
			}
		});
		btnNewButton_2.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		btnNewButton_2.setBounds(858, 477, 119, 23);
		frmBrowseFlights.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_1_1 = new JButton("BACK");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBrowseFlights.dispose();
				new AdminMainFrame();
			}
		});
		btnNewButton_1_1.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		btnNewButton_1_1.setBounds(431, 477, 119, 23);
		frmBrowseFlights.getContentPane().add(btnNewButton_1_1);
		


	}
}
