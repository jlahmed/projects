import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BrowseAircraft {

	private JFrame frmBrowseAircraft;
	BrowseAircraft window;
	private JTable table;
	private JButton btnNewButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrowseAircraft window = new BrowseAircraft();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BrowseAircraft() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmBrowseAircraft = new JFrame();
		frmBrowseAircraft.setBounds(100, 100, 711, 465);
		frmBrowseAircraft.setLocationRelativeTo(null);
		frmBrowseAircraft.setVisible(true);
		frmBrowseAircraft.setTitle("Browse Aircraft");
		
		frmBrowseAircraft.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBrowseAircraft.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(25, 11, 633, 369);
	    frmBrowseAircraft.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	    frmBrowseAircraft.getContentPane().add(scrollPane);
	    
		table = new JTable();
		table.setBounds(25, 29, 378, 203);
		//frmBrowseAircraft.getContentPane().add(table);
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		btnNewButton_1 = new JButton("BACK");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBrowseAircraft.dispose();
				new AdminMainFrame();
			}
		});
		btnNewButton_1.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		btnNewButton_1.setBounds(300, 391, 119, 23);
		frmBrowseAircraft.getContentPane().add(btnNewButton_1);
		
		List<Aircraft> aircraftList = new ArrayList<>();
		try {
			aircraftList = Aircraft.getAircrafts();
			model.addColumn("AircraftID");
			model.addColumn("Type");
			model.addColumn("Company");
			
			for (Aircraft data : aircraftList) {
				
	            model.addRow(new Object[]{data.AircraftID , data.Type , data.Company});
	        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
