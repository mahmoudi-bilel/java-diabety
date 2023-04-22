/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entites.Commentaire;
import entites.publication;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.CommentaireCrud;

/**
 * FXML Controller class
 *
 * @author Bilel Mahmoudi
 */
public class AffichercommentaireController implements Initializable {

    @FXML
    private TableColumn<Commentaire, String> publicationcol;
    @FXML
    private TableColumn<Commentaire, String> commentairecol;
    @FXML
    private TableView<Commentaire>tableview;
    @FXML
    private TextField contenuselected;
    @FXML
    private Button modifier;
    @FXML
    private TableColumn<?, ?> identifiant;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        publicationcol.setCellValueFactory(cellData -> {
    return new SimpleStringProperty(cellData.getValue().getId_publication().getDescription());
});

commentairecol.setCellValueFactory(cellData -> {
    return new SimpleStringProperty(cellData.getValue().getContenu());
});

// Récupérer les données de la base de données
CommentaireCrud ps= new CommentaireCrud();
List<Commentaire> playlists = ps.listeDesCommentaires();

// Ajouter les données à la TableView
ObservableList<Commentaire> observablePlaylists = FXCollections.observableArrayList(playlists);
tableview.setItems(observablePlaylists);


    }    
    
   
    

   @FXML
private void select() {
    Commentaire m = tableview.getSelectionModel().getSelectedItem();
    if (m == null) {
        return;
    }
    m.setId_com(m.getId_com()); // Ajoutez cette ligne pour inclure l'ID du commentaire sélectionné
    contenuselected.setText(m.getContenu());
}

@FXML
private void modifier(ActionEvent event) {
    Commentaire selectedcommentaire = tableview.getSelectionModel().getSelectedItem();
    if (selectedcommentaire == null) {
        // Show error message if no publication is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez sélectionner le commentaire à modifier");
        alert.showAndWait();
        return;
    }

    String contenuValue = contenuselected.getText();
    // Validation checks - you can add your own rules
    if (contenuValue.isEmpty()) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez remplir le champ");
        alert.showAndWait();
        return;
    }
    // Update the selected publication with the new data
    selectedcommentaire.setContenu(contenuValue);

    // Refresh the table to show the updated publication
    tableview.refresh();

    // Update the selected publication in the database
    CommentaireCrud pc = new CommentaireCrud();
    pc.updateCommentaire(selectedcommentaire.getId_com(), selectedcommentaire);
    System.out.println(selectedcommentaire.getId_com());
        System.out.println(tableview.getSelectionModel().getSelectedItem().getId_com());

   
        Alert all = new Alert(Alert.AlertType.CONFIRMATION);
        all.setTitle("commentaire");
        all.setContentText("commentaire modifié !!");
        all.show();
    
}


}
