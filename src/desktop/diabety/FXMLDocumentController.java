/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.diabety;

import entites.publication;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private TextField titre;
    @FXML
    private TextField description;
    @FXML
    private TextField email;
    @FXML
    private TextField numdetel;
    @FXML
    private Button Ajouter;
   @FXML
private TableView<publication> table;
    @FXML
private TableColumn<publication, String> titrecolumn;
@FXML
private TableColumn<publication, String> descriptioncolumn;
@FXML
private TableColumn<publication, String> emailcolumn;
@FXML
private TableColumn<publication, Integer> numcolumn;
@FXML
private Button delete;
    @FXML
    private Button Update;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // bind the columns to the appropriate properties of the publication object
        titrecolumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptioncolumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        emailcolumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        numcolumn.setCellValueFactory(new PropertyValueFactory<>("numerodetel"));

        // load the data into the table
        publicationCrud pc = new publicationCrud();
        List<publication> publicationList = pc.listedespublications();
        ObservableList<publication> observableList = FXCollections.observableArrayList(publicationList);
        table.setItems(observableList);
    }    

   @FXML
private void Ajouter(ActionEvent event) throws SQLException {
    String titreValue = titre.getText();
    String descriptionValue = description.getText();
    String emailValue = email.getText();
    String numdetelValue = numdetel.getText();

    // Validation checks - you can add your own rules
    if (titreValue.isEmpty() || descriptionValue.isEmpty() || emailValue.isEmpty() || numdetelValue.isEmpty()) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
        return;
    }
    
    // Email validation check
    if (!isValidEmail(emailValue)) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez saisir une adresse email valide");
        alert.showAndWait();
        return;
    }
    
    // Phone number validation check
    if (!isValidPhoneNumber(numdetelValue)) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez saisir un numéro de téléphone valide (8 chiffres)");
        alert.showAndWait();
        return;
    }

    // Create a new Publication object from the data entered in the text fields
    publication newPublication = new publication(titreValue, descriptionValue, emailValue, numdetelValue);
    
    // Add the new Publication object to the table
    table.getItems().add(newPublication);

    // Clear the text fields
    titre.clear();
    description.clear();
    email.clear();
    numdetel.clear();
    
    // Ajouter la publication à la base de données en utilisant la classe publicationCrud
    publicationCrud pc = new publicationCrud();
    pc.ajouterpublication(newPublication);
}

private boolean isValidEmail(String email) {
    // Regular expression for email validation
    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    Pattern pattern = Pattern.compile(emailRegex);
    return pattern.matcher(email).matches();
}

private boolean isValidPhoneNumber(String phoneNumber) {
    // Regular expression for phone number validation (8 digits)
    String phoneRegex = "^\\d{8}$";
    Pattern pattern = Pattern.compile(phoneRegex);
    return pattern.matcher(phoneNumber).matches();
}

    @FXML
    private void delete(ActionEvent event) {
    // Get the selected publication from the table
    publication selectedPublication = table.getSelectionModel().getSelectedItem();

    if (selectedPublication == null) {
        // Show error message if no publication is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez sélectionner une publication à supprimer");
        alert.showAndWait();
        return;
    }

    // Remove the selected publication from the table
    table.getItems().remove(selectedPublication);

    // Delete the selected publication from the database
    publicationCrud pc = new publicationCrud();
    pc.supprimerpublication(selectedPublication.getId());
        
    }

    @FXML
    private void Update(ActionEvent event) {
    }

}
