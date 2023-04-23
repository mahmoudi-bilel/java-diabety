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

public class CommentaireCrud implements CommentaireInterface<Commentaire> {

    private Connection cnx;

    public CommentaireCrud() {
        cnx = MyConnection.getInstance().getConn();
    }

    @Override
    public void ajouterCommentaire(Commentaire commentaire, publication publication) {
        String requete = "INSERT INTO commentaire (contenu, id_publication) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(requete, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, commentaire.getContenu());
            preparedStatement.setInt(2, publication.getId());
            preparedStatement.executeUpdate();

            // récupérer l'ID généré automatiquement par la base de données
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    int id_com = resultSet.getInt(1);
                    commentaire.setId_com(id_com);
                }
            }
            System.out.println("Commentaire ajouté avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void supprimerCommentaire(int idCommentaire) {
        String requete = "DELETE FROM commentaire WHERE id_com = ?";

        try (PreparedStatement preparedStatement = cnx.prepareStatement(requete)) {
            preparedStatement.setInt(1, idCommentaire);
            int nombreLignesSupprimees = preparedStatement.executeUpdate();
            if (nombreLignesSupprimees > 0) {
                System.out.println("Commentaire supprimé avec succès !");
            } else {
                System.out.println("Aucun commentaire n'a été supprimé. Vérifiez l'ID du commentaire.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireCrud.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void updateCommentaire(int id, Commentaire C) {
        try {
    String query ="UPDATE `commentaire` SET `contenu`='"+C.getContenu()+"' WHERE id_com="+id;
    Statement st=cnx.createStatement();
    int rowsUpdated = st.executeUpdate(query);
    if (rowsUpdated > 0) {
        System.out.println("Commentaire modifié avec succès.");
    } else {
        System.out.println("Aucun commentaire n'a été modifié. Vérifiez l'ID du commentaire.");
    }
} catch (SQLException ex) {
    Logger.getLogger(CommentaireCrud.class.getName()).log(Level.SEVERE, null, ex);
}
    }

    

     @Override
public List<Commentaire> listeDesCommentaires() {
    List<Commentaire> commentaires = new ArrayList<>();
    try {
        String sql = "SELECT commentaire.id_com, commentaire.contenu, publication.description AS description " +
                     "FROM commentaire " +
                     "JOIN publication ON commentaire.id_publication = publication.id";
        Statement ste = cnx.createStatement();
        ResultSet s = ste.executeQuery(sql);
        
        while (s.next()) {
            Commentaire m = new Commentaire(s.getInt("id_com"), s.getString("contenu"), new publication(s.getString("description")));
            commentaires.add(m);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return commentaires;
}
public List<Commentaire> listeDesCommentairesParPublication(int idPublication) {
    List<Commentaire> commentaires = new ArrayList<>();
    try {
        String sql = "SELECT id_com, contenu FROM commentaire WHERE id_publication = ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setInt(1, idPublication);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Commentaire c = new Commentaire(rs.getInt("id_com"), rs.getString("contenu"));
            commentaires.add(c);
        }
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
    return commentaires;
}

    
    
 
    
}
