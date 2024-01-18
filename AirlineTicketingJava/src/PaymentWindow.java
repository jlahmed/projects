import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
public class PaymentWindow extends JFrame{
    Ticket ticket;
    static int passengerID = 1000;
    public PaymentWindow(Seat seat){
        // Create the frame for payment page
        JFrame paymentFrame = new JFrame("Payment Page");
        paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paymentFrame.setSize(800, 600);
        paymentFrame.setLocationRelativeTo(null); // Center the window on the screen

        // Main panel
        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new BoxLayout(paymentPanel, BoxLayout.Y_AXIS));

        // Panel for personal info
        JPanel infoPanel = new JPanel(new GridLayout(0, 2));
        infoPanel.add(new JLabel("First Name:"));
        JTextField fnameField = new JTextField();
        infoPanel.add(fnameField);
        infoPanel.add(new JLabel("Last Name:"));
        JTextField lnameField = new JTextField();
        infoPanel.add(lnameField);

        // Panel for credit card info
        JPanel creditPanel = new JPanel(new FlowLayout());
        creditPanel.add(new JLabel("Credit Card Number:"));
        JTextField creditField = new JTextField(16);
        creditPanel.add(creditField);

        // Panel for billing address
        JPanel addressPanel = new JPanel(new FlowLayout());
        addressPanel.add(new JLabel("Billing Address:"));
        JTextField addressField = new JTextField(20);
        addressPanel.add(addressField);

        // Panel for email confirmation
        JPanel emailPanel = new JPanel(new FlowLayout());
        emailPanel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(20);
        emailPanel.add(emailField);

        // Panel for CVC code 
        JPanel cvcPanel = new JPanel(new FlowLayout());
        cvcPanel.add(new JLabel("CVC:"));
        JTextField cvcField = new JTextField(3);
        cvcPanel.add(cvcField);

        // Button to submit payment
        JButton submitButton = new JButton("Submit Payment");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fname = fnameField.getText();
                String lname = lnameField.getText();
                String ccNum = creditField.getText();
                String billingAddress = addressField.getText();
                String cvc = cvcField.getText();
                String email = emailField.getText();
                if(!fname.isBlank() && !lname.isBlank()  
                && !billingAddress.isBlank() && !cvc.isBlank() && !email.isBlank()){
                    ticket = new Ticket(seat.FlightID, fname, lname, Integer.toString(passengerID++), ccNum, email, seat);
                    ticket.insertTicket();
                    LoginWindow loginWindow = new LoginWindow();
                    loginWindow.setVisible(true);
                    paymentFrame.dispose();
                    JOptionPane.showMessageDialog(null,
                        "Payment Successful.\nBooking ID: " + ticket.BookingID,"Confirmation",1);
                   /* Mail mail = new Mail ();
                    try {
						mail.publicSendMethod(email);
					} catch (AddressException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
                    String fare = String.valueOf(seat.Price);
                    Mail paymentMail = new Mail ();
                    try {
						paymentMail.publicSendConfirmationMethod(email, fare, ticket.BookingID, ticket.FlightID);
					} catch (AddressException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                }
            }
        });
        // Add all components to the main panel
        paymentPanel.add(infoPanel);
        paymentPanel.add(creditPanel);
        paymentPanel.add(addressPanel);
        paymentPanel.add(emailPanel);
        paymentPanel.add(cvcPanel);
        paymentPanel.add(submitButton);

        // Add main panel to frame
        paymentFrame.getContentPane().add(paymentPanel);

        // Display the frame
        paymentFrame.setVisible(true);
    }


}
