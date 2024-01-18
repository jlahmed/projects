import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

// This class is not used.

public class AdminWindowOld extends JFrame {

    public AdminWindowOld() {
        /*
        setTitle("Admin Window"); // Set the title of the window
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
            BufferedImage originalImage = ImageIO.read(new File("assets/attendant.png")); // Replace with your image
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
        JLabel guestLabel = new JLabel("Using as Guest.");
        JLabel userInfoLabel = new JLabel("Welcome! Please select an option below.");
        guestLabel.setFont(new Font(guestLabel.getFont().getName(), Font.PLAIN, 20)); // Set font size to 20
        userInfoPanel.add(guestLabel);
        userInfoPanel.add(userInfoLabel);
        userInfoPanel.setBackground(Color.WHITE);

        // Lower right panel for flight actions
        JPanel flightListPanel = new JPanel();
        flightListPanel.setLayout(new BoxLayout(flightListPanel, BoxLayout.Y_AXIS)); // Set to BoxLayout for vertical arrangement

        Dimension buttonSize = new Dimension(200, 50); // Width 200, Height 50

        JButton viewFlightsButton = new JButton("View Booked Flights");
        viewFlightsButton.setPreferredSize(buttonSize);
        viewFlightsButton.setMaximumSize(buttonSize);
        viewFlightsButton.setMinimumSize(buttonSize);

        JButton searchFlightsButton = new JButton("Search and Book a Flight");
        searchFlightsButton.setPreferredSize(buttonSize);
        searchFlightsButton.setMaximumSize(buttonSize);
        searchFlightsButton.setMinimumSize(buttonSize);


        // Add the first button
        flightListPanel.add(viewFlightsButton);
        viewFlightsButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        // Add some space between the buttons
        flightListPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add the second button
        flightListPanel.add(searchFlightsButton);
        searchFlightsButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

        flightListPanel.add(viewFlightsButton);
        flightListPanel.add(searchFlightsButton);
        flightListPanel.setBackground(Color.WHITE);

        flightListPanel.add(viewFlightsButton);
        flightListPanel.add(searchFlightsButton);
        flightListPanel.setBackground(Color.WHITE);

        // Adding panels to the right panel
        rightPanel.add(userInfoPanel);
        rightPanel.add(flightListPanel);

        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Add main panel to the frame
        add(mainPanel);
    }

    // For testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminWindow().setVisible(true);
        });*/
    }
}
