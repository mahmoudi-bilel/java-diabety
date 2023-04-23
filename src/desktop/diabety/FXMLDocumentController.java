/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktop.diabety;

import entites.publication;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.publicationCrud;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

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
    @FXML
    private Button read;
    @FXML
    private Button button;
    @FXML
    private Button commenter;
    @FXML
    private TableColumn<publication, Integer> identifiant;
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
    identifiant.setCellValueFactory(new PropertyValueFactory<>("id"));


    // load the data into the table
    publicationCrud pc = new publicationCrud();
    List<publication> publicationList = new ArrayList<>();
    try {
        publicationList = pc.rechercherParTitre("");
    } catch (SQLException ex) {
        System.out.println("Error while fetching publications from database: " + ex.getMessage());
    }
    ObservableList<publication> observableList = FXCollections.observableArrayList(publicationList);
    table.setItems(observableList);
}

   @FXML
private void Ajouter(ActionEvent event) throws SQLException {
    String titreValue = titre.getText().trim();
    String descriptionValue = description.getText().trim();
    String emailValue = email.getText().trim();
    String numdetelValue = numdetel.getText().trim();

    // Validation checks - you can add your own rules
    if (titreValue.isEmpty() || descriptionValue.isEmpty() || emailValue.isEmpty() || numdetelValue.isEmpty()) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez remplir tous les champs");
        alert.showAndWait();
        return;
    }
    
    // Check that the description and title do not contain numbers
    if (titreValue.matches(".*\\d.*") || descriptionValue.matches(".*\\d.*")) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Le titre et la description ne doivent pas contenir de chiffres");
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
    
    // Check that the phone number does not start with 1
    if (numdetelValue.charAt(0) == '1') {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Le numéro de téléphone ne doit pas commencer par 1");
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
    pc.ajouterPublication(newPublication);
    Alert all = new Alert(Alert.AlertType.CONFIRMATION);
    all.setTitle("publication");
    all.setContentText("Publication ajoutée avec succès !");
    all.showAndWait();
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
     Alert all = new Alert(Alert.AlertType.CONFIRMATION);
                    all.setTitle("publication");
                    all.setContentText("publication supprimé avec succés!!");
                    all.show();
        
    }

    @FXML
private void Update(ActionEvent event) {
    // Get the selected publication from the table
    publication selectedPublication = table.getSelectionModel().getSelectedItem();
    //System.out.println(table.getSelectionModel().getSelectedItem().getId());
    if (selectedPublication == null) {
        // Show error message if no publication is selected
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez sélectionner une publication à modifier");
        alert.showAndWait();
        return;
    }

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

    // Check if the title contains numbers
    if (titreValue.matches(".*\\d.*")) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Le titre ne doit pas contenir de chiffres");
        alert.showAndWait();
        return;
    }

    // Check if the description contains numbers
    if (descriptionValue.matches(".*\\d.*")) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("La description ne doit pas contenir de chiffres");
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

    // Check if the phone number starts with 1
    if (numdetelValue.startsWith("1")) {
        // Show error message
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Le numéro de téléphone ne doit pas commencer par 1");
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

    // Update the selected publication with the new data
    selectedPublication.setTitre(titreValue);
    selectedPublication.setDescription(descriptionValue);
    selectedPublication.setEmail(emailValue);
    selectedPublication.setNumerodetel(Integer.parseInt(numdetelValue));

    // Refresh the table to show the updated publication
    table.refresh();

    // Update the selected publication in the database
    publicationCrud pc = new publicationCrud();
    
    pc.updatepublication(selectedPublication.getId(), selectedPublication);
        System.out.println(selectedPublication.getId());
    Alert all = new Alert(Alert.AlertType.CONFIRMATION);
    all.setTitle("publication");
    all.setContentText("publication modifié !!");
    all.show();
}

    @FXML
    
    public void publicationSelect() {
        publication m = table.getSelectionModel().getSelectedItem();
        int num = table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

       // idt.setText(String.valueOf(m.getId()));

        titre.setText(m.getTitre());
       // glucidest.setText(String.valueOf(m.getGlucides()));
        description.setText(m.getDescription());
        //caloriest.setText(String.valueOf(m.getCalories()));
        //proteinst.setText(String.valueOf(m.getProteins()));
        email.setText(m.getEmail());
        numdetel.setText(String.valueOf(m.getNumerodetel()));

        
    
        

}

    

   @FXML
private void read(ActionEvent event) throws IOException {
        Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/AffichageDesPublications.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,825,567);
        nouveauStage.setScene(scene);
}

    @FXML
    private void surffing(ActionEvent event) throws IOException {
        Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/Email.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,635,429);
        nouveauStage.setScene(scene);
        
    }

    @FXML
    private void commenter(ActionEvent event) throws IOException {
         Stage nouveauStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/Commentaire.fxml"));
        nouveauStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,600,400);
        nouveauStage.setScene(scene);
    }
}
