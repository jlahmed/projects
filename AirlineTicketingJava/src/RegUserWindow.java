import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import java.io.IOException;
import java.io.File;

public class RegUserWindow extends JFrame {

    private static final Dimension MAX_FIELD_DIMENSION = new Dimension(200, 24);
    private static final Dimension LABEL_DIMENSION = new Dimension(100, 24);

    public RegUserWindow(RegUser regUser) {
        setTitle("Registered User Window"); // Set the title of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setResizable(false); // Make the window not resizable
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null); // Center the window on the screen

        // Main panel with BorderLayout to divide the space
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Left panel for the picture
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 600)); // Half of the window size

        // Resize and add the image
        try {
            BufferedImage originalImage = ImageIO.read(new File("assets/reguser.png"));
            Image resizedImage = originalImage.getScaledInstance(leftPanel.getPreferredSize().width,
                    leftPanel.getPreferredSize().height, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
            leftPanel.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
            leftPanel.add(new JLabel("Image load failed."));
        }

        // Right panel to hold user info and flight list
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        rightPanel.setPreferredSize(new Dimension(400, 600));

        // Upper right panel for user information
        JPanel userInfoPanel = new JPanel();
        JLabel guestLabel = new JLabel("Welcome RegUser Member ID: " + regUser.getMemberN());
        JTextArea userInfoArea = new JTextArea("You are eligible for the following promotions: \n Discount Lounge Access \n Free Companion Tickets (once per year).");
        guestLabel.setFont(new Font(guestLabel.getFont().getName(), Font.PLAIN, 20));
        userInfoPanel.add(guestLabel);
        userInfoPanel.add(userInfoArea);
        userInfoPanel.setBackground(Color.WHITE);

        // Lower right panel for flight actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        Dimension buttonSize = new Dimension(200, 50);

        // "Booking ID" input field
        JPanel bookingIdPanel = createLabeledInputField("Booking ID:");
        buttonPanel.add(bookingIdPanel);
        JButton viewFlightsButton = new JButton("View Booked Flight");
        viewFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String bookingID = getBookingIdFromPanel(bookingIdPanel);
                    if (bookingID.isEmpty()) {
                        JOptionPane.showMessageDialog(RegUserWindow.this, "Please enter a Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Ticket ticket = Ticket.getTicket(bookingID);
                    displayTicket(ticket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        viewFlightsButton.setPreferredSize(buttonSize);
        viewFlightsButton.setMaximumSize(buttonSize);
        viewFlightsButton.setMinimumSize(buttonSize);

        JButton searchFlightsButton = new JButton("Search and Book a Flight");
        searchFlightsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchWindow searchWindow = new SearchWindow();
                searchWindow.setVisible(true); // Show the search window
            }
        });
        searchFlightsButton.setPreferredSize(buttonSize);
        searchFlightsButton.setMaximumSize(buttonSize);
        searchFlightsButton.setMinimumSize(buttonSize);

        JButton creditCardButton = new JButton("Sign Up for Credit Card");
        creditCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Generate a random 8-digit integer
                int cardNumber = (int) ((Math.random() * 90000000) + 10000000);
        
                try {
                    RegUser.insertCredit(cardNumber, regUser.getMemberN());
        
                    // Show confirmation popup
                    JOptionPane.showMessageDialog(RegUserWindow.this, 
                        "You have registered for a credit card.\nYour Credit Card Number is: " + cardNumber,
                        "Credit Card Registration", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        creditCardButton.setPreferredSize(buttonSize);
        creditCardButton.setMaximumSize(buttonSize);
        creditCardButton.setMinimumSize(buttonSize);

        // Add the first button
        buttonPanel.add(viewFlightsButton);
        viewFlightsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the second button
        buttonPanel.add(searchFlightsButton);
        searchFlightsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add the third button
        buttonPanel.add(creditCardButton);
        creditCardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
        exitButton.setMinimumSize(buttonSize);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                LoginWindow loginWindow = new LoginWindow();
                loginWindow.setVisible(true);
            }
        });

        buttonPanel.setBackground(Color.WHITE);

        // Adding panels to the right panel
        rightPanel.add(userInfoPanel);
        rightPanel.add(buttonPanel);

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Add main panel to the frame
        add(mainPanel);
    }

    private JPanel createLabeledInputField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(LABEL_DIMENSION);

        JTextField textField = new JTextField();
        textField.setMaximumSize(MAX_FIELD_DIMENSION);

        panel.add(label);
        panel.add(Box.createHorizontalStrut(5)); // Adds some space between the label and the text field
        panel.add(textField);

        panel.setMaximumSize(new Dimension(200, 50));

        return panel;
    }

    private String getBookingIdFromPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                return ((JTextField) comp).getText();
            }
        }
        return "";
    }

    public void displayTicket(Ticket ticket) {
        if (ticket == null) {
            JOptionPane.showMessageDialog(this, "No ticket found with the provided Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        // Create a panel to display ticket details
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        // Adding ticket details to the panel
        panel.add(new JLabel("Booking ID: " + ticket.BookingID));
        panel.add(new JLabel("Flight ID: " + ticket.FlightID));
        panel.add(new JLabel("Passenger Name: " + ticket.FName + " " + ticket.LName));
        panel.add(new JLabel("Passenger ID: " + ticket.PassengerID));
        panel.add(new JLabel("Card Number: " + ticket.CardN));
        panel.add(new JLabel("Email: " + ticket.Email));
    
        // Create buttons
        JButton cancelButton = new JButton("Cancel Ticket");
        JButton confirmButton = new JButton("Confirm");
    
        // Add action listeners to buttons
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Ticket.cancelTicket(ticket.BookingID);
                    JOptionPane.showMessageDialog(RegUserWindow.this, 
                    "Ticket with BookingID: " + ticket.BookingID + " cancelled successfully.","Ticket Cancelled", JOptionPane.INFORMATION_MESSAGE);
                    
                    Mail cancelMail = new Mail ();
                    try {
						cancelMail.publicSendCancellationMethod(ticket.Email+"", ticket.BookingID);
					} catch (AddressException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegUserWindow.this, 
                        "Error occurred while cancelling the ticket.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
        }});
    
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window dialog = SwingUtilities.windowForComponent(confirmButton);
                if (dialog != null) {
                    dialog.dispose();
                }
            }
        });
    
        // Add buttons to the panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);
        panel.add(buttonPanel);
    
        // Show the panel in a dialog
        JOptionPane.showMessageDialog(this, panel, "Ticket Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
