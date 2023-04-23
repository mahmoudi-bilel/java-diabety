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

   

@Override
public List<String> getalldescription(){
    List<String> description = new ArrayList<>();
            try {
                String sql = "select description from publication";
                Statement ste = cnx.createStatement();
                ResultSet s = ste.executeQuery(sql);
                while (s.next()) {

                
                    description.add(s.getString(1));

                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            return description;
    
}

   public String filterInappropriateWords(String text) {
    String[] inappropriateWords = {"bad", "ugly", "damn", "shit"};
    String filteredText = text.toLowerCase(); // Convertir en minuscules pour la comparaison

    for (String word : inappropriateWords) {
        String asterisks = "";
        for (int i = 0; i < word.length(); i++) {
            asterisks += "*";
        }
        if (filteredText.contains(word)) { // Vérifier si le mot est dans le texte
            filteredText = filteredText.replaceAll(word, asterisks);
            filteredText = filteredText.replaceAll(word.toUpperCase(), asterisks); // Prendre en compte les mots en majuscules
        }
    }

    return filteredText;
}

public void ajouterPublication(publication t) throws SQLException {
    // Filter inappropriate words in description and title
    String filteredDescription = filterInappropriateWords(t.getDescription());
    String filteredTitle = filterInappropriateWords(t.getTitre());

    // Check if filtered description is different from original description
    if (!filteredDescription.equals(t.getDescription())) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText("Il y a des mots interdits dans la description de la publication!");
        alert.showAndWait();
    }

    // Check if filtered title is different from original title
    if (!filteredTitle.equals(t.getTitre())) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText("Il y a des mots interdits dans le titre de la publication!");
        alert.showAndWait();
    }

    // Use the filtered description and title for the query
    String req = "INSERT INTO publication (id, titre, description, email, numerodetel) VALUES ('" + t.getId() + "', '" + filteredTitle + "', '" + filteredDescription + "', '" + t.getEmail() + "', '" + t.getNumerodetel() + "')";
    Statement st = cnx.createStatement();
    st.executeUpdate(req);
    System.out.println("Publication ajoutée avec succès");
}
public void signalerPublication(int id) {
    try {
        String query = "UPDATE publication SET signale = true WHERE id = ?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, id);
        pst.executeUpdate();
        System.out.println("La publication a été signalée avec succès !");
    } catch (SQLException ex) {
        Logger.getLogger(publicationCrud.class.getName()).log(Level.SEVERE, null, ex);
    }
}
}


    


   
    
