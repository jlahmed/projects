import java.io.IOException;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Message;

public class Mail {

    Session newSession = null;
    MimeMessage mimeMessage = null;

    public void publicSendCancellationMethod(String email, String ID) throws AddressException, MessagingException, IOException {
        setupServerProperties();
        draftEmailCancel(email, ID);
        sendEmail();
    }

    public void publicSendConfirmationMethod(String email, String fare, String bookID, String flightID) throws AddressException, MessagingException, IOException {
        setupServerProperties();
        draftEmailConfirm(email, fare, bookID, flightID);
        sendEmail();
    }

    private void sendEmail() throws MessagingException {
        String fromUser = "AirlineENSF@outlook.com";
        String fromUserPassword = "kknpkkzdxtosftav";
        String emailHost = "smtp.outlook.com";

        // Explicitly set the protocol to "smtp"
        Transport transport = newSession.getTransport("smtp");

        transport.connect(emailHost, fromUser, fromUserPassword);

        // Set the "From" address in the MimeMessage
        mimeMessage.setFrom(new InternetAddress(fromUser));

        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
        System.out.println("Email successfully sent!!!");
    }

    private MimeMessage draftEmailCancel(String email, String ID) throws AddressException, MessagingException, IOException {
        String emailReceipients = email;
        String emailSubject = "Booking Cancelled";
        String emailBody = "Your reservation has been cancelled for bookingID: " + ID + " successfully.";
        mimeMessage = new MimeMessage(newSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients));
        mimeMessage.setSubject(emailSubject);
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(emailBody);
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;
    }

    private MimeMessage draftEmailConfirm(String email, String fare, String bookID, String flightID) throws AddressException, MessagingException, IOException {
        String emailReceipients = email;
        String emailSubject = "Booking Confirmation";
        String emailBody = "Thank you for Booking your flights with us. \n\nPlease login to the App to Manage your booking. \n\n\n"
                + "Your payment for $ " + fare + " has been received successfully.\n"
                + "Your Flight Number is: " + flightID
                + "\nYour BookingID is: " + bookID;
        mimeMessage = new MimeMessage(newSession);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailReceipients));
        mimeMessage.setSubject(emailSubject);
        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setText(emailBody);
        MimeMultipart multiPart = new MimeMultipart();
        multiPart.addBodyPart(bodyPart);
        mimeMessage.setContent(multiPart);
        return mimeMessage;
    }

    private void setupServerProperties() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.outlook.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        newSession = Session.getDefaultInstance(properties, null);
    }
}
