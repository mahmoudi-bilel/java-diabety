/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import interfaces.CommentaireInterface;
import entites.Commentaire;
import entites.publication;

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

/**
 *
 * @author user
 */
public class CommentaireCrud implements CommentaireInterface<Commentaire> {
     Connection cnx;
    public CommentaireCrud() {
        cnx =MyConnection.getInstance().getConn();
    }
     @Override
     public void ajouterCommentaire(Commentaire t,publication p) {
        try {
            String requete = "INSERT INTO `commentaire`(`id_com`, `contenu`,`id_publication`)"
                    + "VALUES (?,?,?)";
            PreparedStatement pst = cnx
                                    .prepareStatement(requete);
            pst.setInt(1, t.getId_com());
            pst.setString(2, t.getContenu());
             pst.setInt(3, p.getId());
        
            pst.executeUpdate();
            System.out.println("Done!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
     
     
     @Override
     public void supprimerCommentaire(int id){
        try {
            String query ="DELETE FROM `commentaire` WHERE id_com="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
}
     @Override
     public void updateCommentaire(int id,Commentaire C){
         
    try {
            String query ="UPDATE `commentaire` SET `contenu`='"+C.getContenu()+"' WHERE id_com="+id;
            Statement st=cnx.createStatement();
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    

     @Override
    public List<Commentaire> listeDesCommentaires() {

         List<Commentaire> commentaires= new ArrayList<>();
    try {
        String sql = "SELECT commentaire.id_com,commentaire.contenu, publication.description AS description " +
                     "FROM commentaire " +
                     "JOIN publication ON commentaire.id_publication = publication.id";
        Statement ste = cnx.createStatement();
        ResultSet s = ste.executeQuery(sql);
        
        while (s.next()) {
            Commentaire m = new Commentaire(s.getString("contenu"),new publication(s.getString("description")));
            commentaires.add(m);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return commentaires;
    }
    
    
 
    
}
