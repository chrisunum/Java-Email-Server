
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Naishare {

    public String html = "";

    public Naishare(String user, int id) {

        html = getHtml();
        String[] emails = getEmails();
        Arrays.sort(emails);
        try {

            for (String x : emails) {
                try {
                    sendEmail(x, html, "Step By Step Guide To Creating A Fully Functional Contact Form Using Firebase");
                } catch (Exception e) { 
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Naishare.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {
        new Naishare("", 1);

    }

    public void sendEmail(String to, String html, String Subject) throws Exception {

        String subject = Subject;
        final String from = "youremail@gmail.com";
        final String password = "********";

        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });

	    
        Transport transport = session.getTransport();
        InternetAddress addressFrom = new InternetAddress(from, "Naishare");

        MimeMessage message = new MimeMessage(session);

        message.addHeader("List-ID", "1");
        message.addHeader("List-Archive", "1");
        message.setHeader("List-Post", "1");
        message.setHeader("List-Unsubscribe", "1");
        message.setSender(addressFrom);
        message.setFrom(addressFrom);
        message.setSubject(subject);
        message.setContent(html, "text/html");
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse("yourbccemail@yourdomain.com"));

        transport.connect();
        Transport.send(message);
        System.out.println("Message sent");
        transport.close();

    }

    public String[] getEmails() {
        String emails2 = "";
        HashSet<String> array = new HashSet<String>();
        String textName = "", textPath = "";
        String[] userArray = {};
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text file", "txt"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textName = chooser.getSelectedFile().getName();
            textPath = chooser.getSelectedFile().getAbsolutePath();

        }
        try {
            Scanner scan = new Scanner(new File(textPath));
            while (scan.hasNext()) {
                array.add(scan.next());
            }
            userArray = new String[array.size()];
            userArray = array.toArray(userArray);
            System.out.println(array);

            scan.close();
        } catch (Exception e) {
            System.out.println("couldn't locate file");
        }
        return userArray;
    }

    public String getHtml() {
        String html = "";
        ArrayList<String> array = new ArrayList<String>();
        String textName = "", textPath = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("Text file", "txt"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            textName = chooser.getSelectedFile().getName();
            textPath = chooser.getSelectedFile().getAbsolutePath();

        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(textPath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(General.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            html = sb.toString();
            br.close();
        } catch (Exception e) {
            System.out.println("couldn't locate file");
        }

        return html;
    }

}