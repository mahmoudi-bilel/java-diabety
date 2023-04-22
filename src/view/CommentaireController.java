/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entites.Commentaire;
import entites.publication;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.CommentaireCrud;
import service.publicationCrud;
import utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author Bilel Mahmoudi
 */
public class CommentaireController implements Initializable {

    @FXML
    private TextField contenu;
    @FXML
    private ComboBox<String> combobox;
    @FXML
    private Button btnajouter;
    @FXML
    private Button afficher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        publicationCrud p=new publicationCrud();
        ObservableList<String> o = FXCollections.observableArrayList(p.getalldescription());   
    combobox.setItems(o);
    String selected = combobox.getValue();
    if (!o.isEmpty()) {
   combobox.setValue(o.get(0));    
    }
    }    

    @FXML
    private void ajouter(ActionEvent event) {
        Connection cnx = MyConnection.getInstance().getConn();
    if (contenu.getText().isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Ajout non effectué");
        alert.setContentText("Veuillez remplir le champ.");
        alert.showAndWait();
    } else {
      String pselection = combobox.getValue();
String sqlGetIdUser = "SELECT id FROM publication WHERE description='" + pselection + "'";
Statement statement;
ObservableList<Integer> idList = FXCollections.observableArrayList();
try {
    statement = cnx.createStatement();
    ResultSet resultSet = statement.executeQuery(sqlGetIdUser);
    while (resultSet.next()) {
        int id = resultSet.getInt("id");
        idList.add(id);
    }
} catch (SQLException ex) {
    System.out.println(ex.getMessage());
}
if (idList.isEmpty()) {
    System.out.println(" l'utilisateur sélectionné n'existe pas dans la table publication");
    // afficher une erreur et sortir de la méthode
    return;
}
int idUser = idList.get(0);
System.out.println("ID utilisateur: " + idUser);

// insérer la playlist en utilisant l'ID utilisateur récupéré

        publication publication = new publication();
        publication.setId(idUser);

        Commentaire com = new Commentaire();
        com.setContenu(contenu.getText());
        com.setId_publication(publication);
        CommentaireCrud comm=new CommentaireCrud();
        comm.ajouterCommentaire(com, publication);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Ajout effectué");
        alert.setContentText("Playlist ajoutée avec succès.");
        alert.showAndWait();
    }

   
    }

    @FXML
    private void affichercommentaire(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/affichercommentaire.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,600,400);
        nouveauStage.setScene(scene);
    }
    
}
