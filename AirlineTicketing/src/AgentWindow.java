import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.List;

public class AgentWindow extends JFrame {

    private static final Dimension MAX_FIELD_DIMENSION = new Dimension(200, 24);
    private static final Dimension LABEL_DIMENSION = new Dimension(100, 24);

    public AgentWindow() {
        setTitle("Agent Window"); // Set the title of the window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        setResizable(false); // Make the window not resizable
        setSize(new Dimension(800, 600)); // Alternatively, use setPreferredSize and pack()
        setLocationRelativeTo(null); // Center the window on the screen

        // Main panel with BorderLayout to divide the space
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Left panel for the picture
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, 600)); // Half of the window size

        // Resize and add the image
        try {
            BufferedImage originalImage = ImageIO.read(new File("assets/agent.png")); // Replace with your image
                                                                                          // path
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
        JLabel guestLabel = new JLabel("Using as Agent.");
        JLabel userInfoLabel = new JLabel("Welcome! Please select an option below.");
        guestLabel.setFont(new Font(guestLabel.getFont().getName(), Font.PLAIN, 20)); // Set font size to 20
        userInfoPanel.add(guestLabel);
        userInfoPanel.add(userInfoLabel);
        userInfoPanel.setBackground(Color.WHITE);

        // Lower right panel for flight actions
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Set to BoxLayout for vertical arrangement

        Dimension buttonSize = new Dimension(200, 50); // Width 200, Height 50

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
                        JOptionPane.showMessageDialog(AgentWindow.this, "Please enter a Booking ID.", "Error", JOptionPane.ERROR_MESSAGE);
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

        // "Flight ID" input field
        JPanel flightIdPanel = createLabeledInputField("Flight ID:");
        buttonPanel.add(flightIdPanel);

        JButton viewPassengerListButton = new JButton("View Passenger List");
        viewPassengerListButton.setPreferredSize(buttonSize);
        viewPassengerListButton.setMaximumSize(buttonSize);
        viewPassengerListButton.setMinimumSize(buttonSize);
        viewPassengerListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String flightID = getFlightIdFromPanel(flightIdPanel);
                    if (flightID.isEmpty()) {
                        JOptionPane.showMessageDialog(AgentWindow.this, "Please enter a Flight ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    List<String> passengers = Flight.getPassengers(flightID);
                    displayPassengerList(passengers);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // Handle error (e.g., show a message dialog with the error)
                }
            }
        });

        // Add the first button
        buttonPanel.add(viewFlightsButton);
        viewFlightsButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        // Add the second button
        buttonPanel.add(searchFlightsButton);
        searchFlightsButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        // Add the third button
        buttonPanel.add(viewPassengerListButton);
        viewPassengerListButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.setMaximumSize(buttonSize);
        exitButton.setMinimumSize(buttonSize);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button
        buttonPanel.add(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the current window
                LoginWindow loginWindow = new LoginWindow(); // Create a new login window
                loginWindow.setVisible(true); // Show the login window
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

    private String getFlightIdFromPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                return ((JTextField) comp).getText();
            }
        }
        return "";
    }

    private void displayPassengerList(List<String> passengers) {
        if (passengers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No passengers found for this flight.", "Passenger List", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (String passenger : passengers) {
            sb.append(passenger).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 150)); // Set your preferred size

        JOptionPane.showMessageDialog(this, scrollPane, "Passenger List", JOptionPane.INFORMATION_MESSAGE);
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
                    JOptionPane.showMessageDialog(AgentWindow.this, 
                    "Ticket with BookingID: " + ticket.BookingID + " cancelled successfully.","Ticket Cancelled", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AgentWindow.this, 
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

    // For testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AgentWindow().setVisible(true);
        });
    }
}
