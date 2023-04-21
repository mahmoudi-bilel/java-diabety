/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javax.mail.internet.*;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import service.publicationCrud;
import utils.MyConnection;


/**
 * FXML Controller class
 *
 * @author Bilel Mahmoudi
 */
public class EmailController implements Initializable {

    @FXML
    private TextArea idtext;
    @FXML
    private Button idenv;
     Connection cnx;
 String sql;
 Message message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void envoyerEmail(ActionEvent event) throws MessagingException, SQLException, AddressException{
       
       
         cnx=MyConnection.getInstance().getConn();
        String mail = idtext.getText();
        List<String> emails = new ArrayList<>();
       
        try {
         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   
         sql="select email from publication";
         Statement ste =cnx.createStatement();
         ResultSet res = ste.executeQuery(sql);
         
         while(res.next())
         {
           
             String m = res.getString("email");
         emails.add(m);
         }
     } catch (SQLException ex) {
         Logger.getLogger(publicationCrud.class.getName()).log(Level.SEVERE, null, ex);
     }
       
       
       
       
      cnx=MyConnection.getInstance().getConn();
       // String mail = idtext.getText();
        //List<String> emails = new ArrayList<>();
       
   
         //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   
         sql="select email from publication";
         Statement ste =cnx.createStatement();
         ResultSet res = ste.executeQuery(sql);
         
         while(res.next())
         {
             
         String maill = res.getString("email");
         System.out.println("Preparing to send:");
         Properties properties = new Properties();
       
       
     String myAccountEmail ="bilel.mahmoudi@esprit.tn";
     String password ="zcvkyrsfjsufprlo";
     String recepient =maill;
     String Body= mail;
       
        properties.put("com.hof.email.starttime","20170519094544");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.connectiontimeout","60000");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","25");
        properties.put("mail.smtp.ssl.trust","*");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.timeout","60000");
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
       
       
       
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
       
        Message m;
             m = prepareMessage(session,myAccountEmail,recepient,Body);
       
        Transport.send(m);
       
        System.out.println("message send successfully");
       
   
     }
    }
   
   

   
    private static Message prepareMessage(javax.mail.Session session, String myAccountEmail,String recepient, String Body) throws AddressException, MessagingException{
        Message message = (Message) new MimeMessage(session); // Logger.getLogger(MailEmploye.class.getName()).log(Level.SEVERE, null, ex);
        message.setFrom(new InternetAddress(myAccountEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
        message.setSubject("Information de compte");
        message.setText(Body);
        //message.setText(Act.getListActivite2().toString());
        return message;
       
    }
    
}
