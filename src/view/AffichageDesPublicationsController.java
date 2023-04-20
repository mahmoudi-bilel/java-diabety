/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entites.publication;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.publicationCrud;

/**
 * FXML Controller class
 *
 * @author Bilel Mahmoudi
 */
public class AffichageDesPublicationsController implements Initializable {

    @FXML
    private TableView<publication> table;
    @FXML
    private TableColumn<publication, String> titrecol;
    @FXML
    private TableColumn<publication, String> descriptioncol;
    @FXML
    private TableColumn<publication, String> emailcol;
    @FXML
    private TableColumn<publication, Integer> numérodetelcol;
    @FXML
    private TextField rechercheTitre;
    @FXML
    private Button Rechercher;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // bind the columns to the appropriate properties of the publication object
   titrecol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        numérodetelcol.setCellValueFactory(new PropertyValueFactory<>("numerodetel"));

        // load the data into the table
        publicationCrud pc = new publicationCrud();
        List<publication> publicationList = pc.listedespublications();
        ObservableList<publication> observableList = FXCollections.observableArrayList(publicationList);
        table.setItems(observableList);  
    
}

    @FXML
    private void Rechercher(ActionEvent event) throws SQLException {
         String titreRecherche =rechercheTitre.getText().trim();

    // Vérifiez que le champ de recherche n'est pas vide
    if (titreRecherche.isEmpty()) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez saisir un titre pour effectuer la recherche");
        alert.showAndWait();
        return;
    }

    // Effectuez la recherche dans la base de données
    publicationCrud pc = new publicationCrud();
    List<publication> publicationList = pc.rechercherParTitre(titreRecherche);

    // Afficher les résultats dans la table
    ObservableList<publication> observableList = FXCollections.observableArrayList(publicationList);
    table.setItems(observableList);
    }
}
