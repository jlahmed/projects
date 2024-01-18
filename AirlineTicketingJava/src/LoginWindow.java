import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

public class LoginWindow extends JFrame{
    private static final Dimension MAX_FIELD_DIMENSION = new Dimension(200, 24);
    private static final Dimension LABEL_DIMENSION = new Dimension(100, 24);

    public LoginWindow() {
        final JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(1100, 800));

        // Set the main layout manager for the frame
        frame.getContentPane().setLayout(new BorderLayout());

        // Create and add the top panel with the image
        JPanel topPanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon("assets/plane.png"); 

        if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.out.println("Image not loaded properly");
        }
        
        JLabel imageLabel = new JLabel(imageIcon);
        topPanel.add(imageLabel, BorderLayout.CENTER);
        frame.add(topPanel, BorderLayout.NORTH);

        // Create a panel to hold both the login and sign up panels
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        // Create the login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createTitledBorder("LOGIN"));
        loginPanel.add(createLabeledField("Username:"));
        loginPanel.add(createLabeledField("Password:"));
        loginPanel.add(createDropdown(new String[]{"User", "Attendant", "Agent", "Admin"}));

        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.X_AXIS));
        errorPanel.setBackground(Color.WHITE); // Set background to match loginPanel
        errorPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20)); // Set fixed height

        JLabel errorMessageLabel = new JLabel("  ");
        errorMessageLabel.setForeground(Color.RED);
        errorPanel.add(Box.createHorizontalGlue()); // Glue before the label
        errorPanel.add(errorMessageLabel);
        errorPanel.add(Box.createHorizontalGlue()); // Glue after the label

        loginPanel.add(errorPanel);

        loginPanel.add(Box.createVerticalStrut(45));

        JButton loginButton = createButton("Login");
        loginPanel.add(loginButton);
        loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = getTextFromPanel((JPanel) loginPanel.getComponent(0));
            String password = getPasswordFromPanel((JPanel) loginPanel.getComponent(1));
            String role = getSelectedItem((JPanel) loginPanel.getComponent(2));

            if (username.isEmpty() || password.isEmpty()) {
                errorMessageLabel.setText("One or more fields are empty.");
                return; // Stop the method if fields are empty
            }

            try {
                boolean isValidUser = false;
                if ("Attendant".equals(role)) {
                    isValidUser = FUser.checkValidUser(username, password);
                } else if ("Admin".equals(role)) {
                    isValidUser = AdminUser.checkValidUser(username, password);
                } else if ("Agent".equals(role)) {
                    isValidUser = AgentUser.checkValidUser(username, password);
                } else if ("User".equals(role)) {
                    isValidUser = RegUser.checkValidUser(username, password);
                }

                if (isValidUser) {
                    if ("Attendant".equals(role)) {
                        AttendantWindow attendantWindow = new AttendantWindow();
                        attendantWindow.setVisible(true);
                        frame.dispose();
                    } else if ("Admin".equals(role)) {
                        // AdminWindowOld adminWindow = new AdminWindowOld();
                        AdminMainFrame adminWindow = new AdminMainFrame();
                        adminWindow.setVisible(true);
                        frame.dispose();
                    } else if ("Agent".equals(role)) {
                        AgentWindow agentWindow = new AgentWindow();
                        agentWindow.setVisible(true);
                        frame.dispose();
                    } else if ("User".equals(role)) {
                        RegUser regUser = RegUser.getRegUser(username, password);
                        RegUserWindow agentWindow = new RegUserWindow(regUser);
                        agentWindow.setVisible(true);
                        frame.dispose();
                    }

                } else {
                    errorMessageLabel.setText("User/Password not found.");
                    loginPanel.revalidate();
                    loginPanel.repaint();
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        });

        JButton guestButton = createButton("Continue as Guest");
        loginPanel.add(guestButton);
        guestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GuestWindow guestWindow = new GuestWindow();
                guestWindow.setVisible(true);
                frame.dispose();
            }
        });

        // Create the sign up panel
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new BoxLayout(signupPanel, BoxLayout.Y_AXIS));
        signupPanel.setBackground(Color.WHITE);
        signupPanel.setBorder(BorderFactory.createTitledBorder("SIGN UP"));
        signupPanel.add(createLabeledField("First Name:"));
        signupPanel.add(createLabeledField("Last Name:"));
        signupPanel.add(createLabeledField("Email:"));
        signupPanel.add(createLabeledField("Address:"));
        signupPanel.add(createLabeledField("Set Username:"));
        signupPanel.add(createLabeledField("Set Password:"));
        signupPanel.add(Box.createVerticalStrut(10));

        JButton signUpButton = createButton("Sign Up");
        signUpButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String firstName = getTextFromPanel((JPanel) signupPanel.getComponent(0));
            String lastName = getTextFromPanel((JPanel) signupPanel.getComponent(1));
            String email = getTextFromPanel((JPanel) signupPanel.getComponent(2));
            String address = getTextFromPanel((JPanel) signupPanel.getComponent(3));
            String username = getTextFromPanel((JPanel) signupPanel.getComponent(4));
            String password = getPasswordFromPanel((JPanel) signupPanel.getComponent(5));

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || 
                address.isEmpty() || username.isEmpty() || password.isEmpty()) {
                return; // Stop the method if any field is empty
            }

            // Create a new RegUser instance
            RegUser newUser = new RegUser(username, password, firstName, lastName, address, email);

            // Call the insertUser method to save the user to the database
            newUser.insertUser();

            // Clear the panel and show confirmation
            signupPanel.removeAll();
            signupPanel.setLayout(new FlowLayout());
            signupPanel.add(new JLabel("Registration successful for user: " + username));
            frame.revalidate();
            frame.repaint();
            }
        });

        signupPanel.add(signUpButton);

        // Add both panels to the center panel
        centerPanel.add(loginPanel);
        centerPanel.add(signupPanel);

        // Add the center panel to the center area of the frame
        frame.add(centerPanel, BorderLayout.CENTER);

        // Pack the frame and make it visible
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private static JPanel createLabeledField(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setFont(new Font("Calibri", Font.PLAIN, 12));

        JLabel label = new JLabel(labelText);
        label.setPreferredSize(LABEL_DIMENSION);
        label.setMaximumSize(LABEL_DIMENSION);

        JComponent field;
        if ("Password:".equals(labelText) || "Set Password:".equals(labelText)) {
            field = new JPasswordField();
        } else {
            field = new JTextField();
        }
        field.setMaximumSize(MAX_FIELD_DIMENSION);
        field.setPreferredSize(MAX_FIELD_DIMENSION);

        panel.add(label);
        panel.add(field);
        return panel;
    }

    private static JButton createButton(String buttonText) {
        JButton button = new JButton(buttonText);
        button.setMaximumSize(MAX_FIELD_DIMENSION);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private static JPanel createDropdown(String[] userTypes) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setFont(new Font("Calibri", Font.PLAIN, 12));

        JLabel label = new JLabel("Login As:");
        label.setPreferredSize(LABEL_DIMENSION);
        label.setMaximumSize(LABEL_DIMENSION);
        label.setAlignmentY(Component.TOP_ALIGNMENT);
        
        JComboBox<String> comboBox = new JComboBox<>(userTypes);
        comboBox.setMaximumSize(MAX_FIELD_DIMENSION);
        comboBox.setAlignmentY(Component.TOP_ALIGNMENT);

        panel.add(label);
        panel.add(comboBox);

        return panel;
    }

    private static String getTextFromPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                return ((JTextField) comp).getText();
            }
        }
        return "";
    }
    
    private static String getPasswordFromPanel(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JPasswordField) {
                return new String(((JPasswordField) comp).getPassword());
            }
        }
        return "";
    }

    private static String getSelectedItem(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JComboBox) {
                JComboBox comboBox = (JComboBox) comp;
                return (String) comboBox.getSelectedItem();
            }
        }
        return null;
    }
}


