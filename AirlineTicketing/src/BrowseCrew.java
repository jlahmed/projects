import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class BrowseCrew {

	private JFrame frmBrowseCrew;
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
					BrowseCrew window = new BrowseCrew();
					window.frmBrowseCrew.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	public BrowseCrew() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		frmBrowseCrew = new JFrame();
		frmBrowseCrew.setBounds(100, 100, 765, 548);
		frmBrowseCrew.setLocationRelativeTo(null);
		frmBrowseCrew.setVisible(true);
		frmBrowseCrew.setTitle("Browse Crew");
		
		frmBrowseCrew.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBrowseCrew.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Enter Flight Number:");
		lblNewLabel.setBounds(29, 21, 139, 24);
		lblNewLabel.setFont(new Font("Calibri Light", Font.PLAIN, 14));
		frmBrowseCrew.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(190, 21, 96, 20);
		frmBrowseCrew.getContentPane().add(textField);
		textField.setColumns(10);
		DefaultTableModel model = new DefaultTableModel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 81, 729, 383);
	    JScrollPane scrollPane_1 = new JScrollPane(table);
	    scrollPane_1.setBounds(0, 0, 0, 0);
	    frmBrowseCrew.getContentPane().add(scrollPane_1);
	    frmBrowseCrew.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBounds(29, 67, 607, 309);
		table.setModel(model);
		//frmBrowseCrew.getContentPane().add(table);
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 56, 729, 20);
		frmBrowseCrew.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.setBounds(332, 20, 89, 23);
		clickcounter = 0;
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clickcounter > 0) {
					lblNewLabel_1.setText("You are clicking Submit multiple times, please go back to main window and come back. ");
				}
				else {
					clickcounter++;
					try {
					List<Crew> crewList = new ArrayList<>();
					crewList = Crew.getCrewList(textField.getText());
					model.addColumn("FlightID");
					model.addColumn("First Name");
					model.addColumn("Last Name");
					model.addColumn("Position");
					
					for (Crew data : crewList) {
						
			            model.addRow(new Object[]{data.FlightID, data.FName, data.LName, data.Position});
			        }
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				table.getTableHeader().setReorderingAllowed(false);
			}
			}
		});
		frmBrowseCrew.getContentPane().add(btnNewButton);
		

		
		JButton btnAddremoveCrewFrom = new JButton("ADD/REMOVE CREW FROM THIS FLIGHT");
		btnAddremoveCrewFrom.setBounds(217, 475, 383, 23);
		btnAddremoveCrewFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBrowseCrew.dispose();
				new AddRemoveCrew ();
			}
		});
		frmBrowseCrew.getContentPane().add(btnAddremoveCrewFrom);
		

		

	}
}
