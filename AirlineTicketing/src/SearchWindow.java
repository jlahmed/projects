import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchWindow extends JFrame {

    public SearchWindow(){
        
        JFrame searchFrame = new JFrame("Flight Search");
        searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        searchFrame.setSize(800, 600);
        searchFrame.setLocationRelativeTo(null); // Center the window on the screen

        // Create the main panel
        JPanel panel = new JPanel();
        // Set the layout
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create the From label and text field
        JPanel fromPanel = new JPanel();
        fromPanel.add(new JLabel("Origin:"));
        JTextField fromField = new JTextField(20);
        fromPanel.add(fromField);

        // Create the To label and text field
        JPanel toPanel = new JPanel();
        toPanel.add(new JLabel("Destination:"));
        JTextField toField = new JTextField(20);
        toPanel.add(toField);
        
  
        // Create the list to display flights
        
        JList<String> flightList = new JList<>();
    

        // Create the Search button
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origin = fromField.getText();
                String destination = toField.getText();
                List<String> flightArrList = new ArrayList<>();
                try {
                    flightArrList = getFlightInfo(Flight.getFlights(origin, destination));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                // Convert ArrayList to DefaultListModel
                DefaultListModel<String> listModel = new DefaultListModel<>();
                for (String element : flightArrList) {
                    listModel.addElement(element);
                }
                flightList.setModel(listModel);
            }
        });
        
        JScrollPane listScroller = new JScrollPane(flightList);
        listScroller.setPreferredSize(new Dimension(250, 80));
        
        // Create the FlightID label and text field
        JPanel flightIDPanel = new JPanel();
        flightIDPanel.add(new JLabel("FlightID:"));
        JTextField flightIDField = new JTextField(20);
        flightIDPanel.add(flightIDField);

        // Create the Book button
        JButton bookButton = new JButton("Book");
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightIDInput = flightIDField.getText();
                ListModel flightListModel = flightList.getModel();
                for(int i = 0; i < flightListModel.getSize(); i++){
                    String flight = (String) flightListModel.getElementAt(i);
                    if(flight.subSequence(0, 5).equals(spacePad(flightIDInput, "12345"))){
                        BookFlightWindow bookFlightWindow = new BookFlightWindow(flightIDInput);
                        bookFlightWindow.setVisible(true);
                        searchFrame.dispose();
                    }
                }
            }
        });


        // Add components to the main panel
        panel.add(fromPanel);
        panel.add(toPanel);
        panel.add(searchButton);
        panel.add(listScroller);
        panel.add(flightIDPanel);
        panel.add(bookButton);

        // Add the panel to the frame
        searchFrame.add(panel);

        // Display the frame
        searchFrame.setVisible(true);
    }

    public List<String> getFlightInfo(List<Flight> flightList){
        List<String> flights = new ArrayList<>();
        String firstRow = "Flight ID | Departure Date | Departure Time | Arrival Date | Arrival Time | Base Price";
        flights.add(firstRow);
        String delim = " | ";
        for(Flight flight : flightList){
            String row = spacePad(flight.FlightID, "Flight ID") + delim + 
                        spacePad(flight.DepartureDate.toString(), " Departure Date  ") + delim +
                        spacePad(flight.DepartureTime.toString(), " Departure Time      ") + delim + 
                        spacePad(flight.ArrivalDate.toString(), "Arrival Date") + delim +
                        spacePad(flight.ArrivalTime.toString(), " Arrival Time  ") + delim + 
                        spacePad(flight.BasePrice + "", " Base Price ");
            flights.add(row);
        }
        return flights;
    }
    
    public String spacePad(String str, String str2){
        if(str2.length() <= str.length()){
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for(int i = 0; i < str2.length() - str.length(); i++){
            sb.append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        new SearchWindow();
    }
}
