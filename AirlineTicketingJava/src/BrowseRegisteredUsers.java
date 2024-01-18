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

public class BrowseRegisteredUsers {

	private JFrame frmBrowseReisteredUsers;
	private JTable table;
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BrowseRegisteredUsers window = new BrowseRegisteredUsers();
					window.frmBrowseReisteredUsers.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BrowseRegisteredUsers() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBrowseReisteredUsers = new JFrame();
		frmBrowseReisteredUsers.setBounds(100, 100, 969, 445);
		frmBrowseReisteredUsers.setLocationRelativeTo(null);
		frmBrowseReisteredUsers.setVisible(true);
		frmBrowseReisteredUsers.setTitle("Browse Reistered Users");
		frmBrowseReisteredUsers.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBrowseReisteredUsers.getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
	    scrollPane.setBounds(27, 11, 910, 350);
	    frmBrowseReisteredUsers.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
	    frmBrowseReisteredUsers.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBounds(25, 29, 600, 350);
		//frmBrowseReisteredUsers.getContentPane().add(table);
		DefaultTableModel model = new DefaultTableModel();
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		btnNewButton = new JButton("BACK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmBrowseReisteredUsers.dispose();
				new AdminMainFrame();
			}
		});
		btnNewButton.setFont(new Font("Calibri Light", Font.PLAIN, 11));
		btnNewButton.setBounds(425, 372, 119, 23);
		frmBrowseReisteredUsers.getContentPane().add(btnNewButton);
		List<RegUser> regUserList = new ArrayList<>();
		
		try {
			regUserList = RegUser.getAllRegUsers();
			model.addColumn("Username");
			model.addColumn("Password");
			model.addColumn("Fisrt Name");
			model.addColumn("Last Name");
			model.addColumn("Address");
			model.addColumn("Email");
			model.addColumn("MemberID");
			
			for (RegUser data : regUserList) {
				
	            model.addRow(new Object[]{data.Username , data.Password , data.FName, data.LName, data.Address, data.Email, data.getMemberN()});
	        }
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
