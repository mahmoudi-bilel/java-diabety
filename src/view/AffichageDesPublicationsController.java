/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entites.publication;
import java.io.File;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;



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
    @FXML
    private Button butonnqr;
    @FXML
    private ImageView imageqr;
    @FXML
    private TableColumn<publication , Integer> Identifiant;
    @FXML
    private Button signaler;

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
    Identifiant.setCellValueFactory(new PropertyValueFactory<>("id"));

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

    @FXML
    private void qrcode(ActionEvent event) throws WriterException {
         
        publication selectedPiece = table.getSelectionModel().getSelectedItem();
        if (selectedPiece != null) {
            String qrText = selectedPiece.getDescription();
            createQR(qrText);
        } else {
            alert("Please select a row.");
        }
    }
    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void setQRImage(String path) {
        try {
            FileInputStream stream = new FileInputStream(path);
            Image image = new Image(stream);
            imageqr.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void createQR(String qrText) throws WriterException {
        try {
            String path = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + "qr_code.png";
            BitMatrix matrix = new MultiFormatWriter().encode(qrText, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
            alert("QR Code Created");
            setQRImage(path);
            //hl.setVisible(false);
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

   @FXML
private void signaler(ActionEvent event) {
    publication selectedPublication = table.getSelectionModel().getSelectedItem();
    if (selectedPublication != null) {
        publicationCrud pc = new publicationCrud();
        pc.signalerPublication(selectedPublication.getId());
        // Afficher un message de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("La publication a été signalée avec succès");
        alert.showAndWait();
    } else {
        // Si aucune publication n'est sélectionnée
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Veuillez sélectionner une publication à signaler");
        alert.showAndWait();
    }
}
    
}
