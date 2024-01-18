import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class BookFlightWindow extends JFrame {
    Seat seat;
    public BookFlightWindow(String flightID){
        
        // Create the frame for booking details
        JFrame bookingFrame = new JFrame("Book Flight");
        bookingFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bookingFrame.setSize(800, 600);
        bookingFrame.setLocationRelativeTo(null); // Center the window on the screen

        // Main panel
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.Y_AXIS));


        // infoPanel.add(new JLabel("Email:"));
        // JTextField emailField = new JTextField();
        // infoPanel.add(emailField);

        // Checkbox for insurance
        JCheckBox insuranceCheckbox = new JCheckBox("Flight Insurance");

        // Seat selection
        JPanel seatPanel = new JPanel(new GridLayout(0, 5));
        // Dummy seat buttons
        String[] classes = {"Business", "Premium", "Economy"};
        Set<String> availableSeats = new HashSet<>();
        for (int i = 1; i <= 3; i++) {
            seatPanel.add(new JLabel(classes[i-1]));
            for (char col = 'A'; col <= 'D'; col++) {
                String label = "" + col + i;
                if(Seat.isSeatTaken(label, flightID)){
                    label = "";
                }
                seatPanel.add(new JLabel(label));
                availableSeats.add(label);
            }
        }

        // Text field for chosen seat
        JPanel chosenSeatPanel = new JPanel(new FlowLayout());
        chosenSeatPanel.add(new JLabel("Seat:"));
        JTextField chosenSeatField = new JTextField(5);
        chosenSeatPanel.add(chosenSeatField);

        // Text area for total
        JPanel totalPanel = new JPanel(new FlowLayout());

        // Button to select seat
        JButton seatButton = new JButton("Select Seat");
        chosenSeatPanel.add(seatButton);
        seatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seatID = chosenSeatField.getText();
                if(availableSeats.contains(seatID)){
                    boolean insurance = insuranceCheckbox.isSelected();
                    boolean economy = seatID.charAt(1) == '3' ? true : false;
                    boolean premium = seatID.charAt(1) == '2' ? true : false;  
                    boolean business = seatID.charAt(1) == '1' ? true : false;  
                    
                    seat = new Seat(seatID, economy, premium, business, flightID, insurance);
                    BigDecimal price = new BigDecimal(String.valueOf(seat.Price));
                    price = price.setScale(2, RoundingMode.HALF_EVEN);
                    totalPanel.removeAll();
                    totalPanel.add(new JLabel("Total: " + price));
                    totalPanel.repaint();
                    totalPanel.revalidate();;
                }
                
            }
        });

        // Button to finalize proceed to payment
        JButton payButton = new JButton("Proceed to Payment");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(seat != null){
                    PaymentWindow paymentWindow = new PaymentWindow(seat);
                    paymentWindow.setVisible(true);
                    bookingFrame.dispose();
                }
                
            }
        });

        // Add all components to the main panel
        //bookingPanel.add(infoPanel);
        bookingPanel.add(insuranceCheckbox);
        bookingPanel.add(seatPanel);
        bookingPanel.add(chosenSeatPanel);
        bookingPanel.add(totalPanel);
        bookingPanel.add(payButton);

        // Add main panel to frame
        bookingFrame.add(bookingPanel);

        // Display the frame
        bookingFrame.setVisible(true);
    }

}