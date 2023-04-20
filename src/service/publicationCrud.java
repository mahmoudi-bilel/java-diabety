/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entites.publication;
import interfaces.publicationInterface;

import utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.activation.DataSource;

/**
 *
 * @author user
 */
public class publicationCrud implements publicationInterface<publication> {
     Connection cnx;
    public publicationCrud() {
        cnx =MyConnection.getInstance().getConn();
    }
     @Override
    
public void ajouterpublication(publication t) throws SQLException{
     String req = "INSERT INTO publication (id, titre, description,email,numerodetel) VALUES ('" + t.getId() + "', '" + t.getTitre() + "', '" + t.getDescription() + "', '" + t.getEmail() + "', '" + t.getNumerodetel() + "')";
        
     Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("publication ajouté avec succés");    }


     @Override
     public void supprimerpublication(int id){
        try {
            String query ="DELETE FROM `publication` WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(publicationCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
}
     @Override
     public void updatepublication(int id, publication C ){
    try {
             String query ="UPDATE `publication` SET `titre`='"+C.getTitre()+"',`description`='"+C.getDescription()+"',`email`='"+C.getEmail()+"',`numerodetel`='"+C.getNumerodetel()+"' WHERE id="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(publicationCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    

     public List<publication> rechercherParTitre(String titre) throws SQLException {
    Connection cnx = MyConnection.getInstance().getConn();
    List<publication> publicationList = new ArrayList<>();
    String req = "SELECT * FROM publication WHERE titre LIKE ?";
    PreparedStatement pst = cnx.prepareStatement(req);
    pst.setString(1, "%" + titre + "%");
    ResultSet rs = pst.executeQuery();
    while (rs.next()) {
        publication publication = new publication();
        publication.setId(rs.getInt("id"));
        publication.setTitre(rs.getString("titre"));
        publication.setDescription(rs.getString("description"));
        publication.setEmail(rs.getString("email"));
        publication.setNumerodetel(rs.getInt("numerodetel"));
        publicationList.add(publication);
    }
    if (publicationList.size() == 0) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Il n'y a aucun publication correspondant à ce titre.");
        alert.showAndWait();
    }
    return publicationList;
}
      @Override
    public List<publication> listedespublications() {

         List<publication> myList = new ArrayList<>();
        try {

            String requete = "SELECT * FROM publication";
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(requete);
            
            while(rs.next()){
                  publication p = new publication();
                p.setTitre(rs.getString("titre"));
                p.setDescription(rs.getString("description"));
                 p.setEmail(rs.getString("email"));
                p.setNumerodetel(rs.getInt("numerodetel"));

                myList.add(p);
                
               
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }

    

    

   
}


    


   
    
