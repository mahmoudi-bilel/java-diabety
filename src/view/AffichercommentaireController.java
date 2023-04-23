package view;

import entites.Commentaire;
import entites.publication;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
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

public class AffichercommentaireController implements Initializable {

    @FXML
    private TableColumn<Commentaire, String> publicationcol;
    @FXML
    private TableColumn<Commentaire, String> commentairecol;
    @FXML
    private TableView<Commentaire> tableview;
    @FXML
    private TextField contenuselected;
    @FXML
    private Button modifier;
    @FXML
    private TableColumn<Commentaire, Integer> identifiant;
    @FXML
    private Button supprimer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        publicationcol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getId_publication().getDescription());
        });

        commentairecol.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getContenu());
        });

        identifiant.setCellValueFactory(cellData -> {
            return new SimpleObjectProperty<>(cellData.getValue().getId_com());
        });

        CommentaireCrud ps = new CommentaireCrud();
        List<Commentaire> commentaires = ps.listeDesCommentaires();

        ObservableList<Commentaire> observableCommentaires = FXCollections.observableArrayList(commentaires);
        tableview.setItems(observableCommentaires);
    }

    @FXML
    private void select() {
        Commentaire m = tableview.getSelectionModel().getSelectedItem();
        int num = tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }
        if (m == null) {
            return;
        }
        m.setId_com(m.getId_com()); // Ajoutez cette ligne pour inclure l'ID du commentaire sélectionné
        contenuselected.setText(m.getContenu());
    }

    @FXML
    private void modifier(ActionEvent event) {
        Commentaire selectedCommentaire = tableview.getSelectionModel().getSelectedItem();
        if (selectedCommentaire == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner le commentaire à modifier");
            alert.showAndWait();
            return;
        }

        String contenuValue = contenuselected.getText();
        if (contenuValue.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir le champ");
            alert.showAndWait();
            return;
        }

        selectedCommentaire.setContenu(contenuValue);

        tableview.refresh();

        CommentaireCrud pc = new CommentaireCrud();
        pc.updateCommentaire(selectedCommentaire.getId_com(), selectedCommentaire);

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("commentaire");
        alert.setContentText("commentaire modifié !!");
        alert.show();
    }

    @FXML
private void supprimer(ActionEvent event) {
    Commentaire selectedCommentaire = tableview.getSelectionModel().getSelectedItem();
    if (selectedCommentaire == null) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText("Veuillez sélectionner le commentaire à supprimer");
        alert.showAndWait();
        return;
    }

    int idPublication = selectedCommentaire.getId_publication().getId();
    int idCommentaire = selectedCommentaire.getId_com();

    CommentaireCrud pc = new CommentaireCrud();
    pc.supprimerCommentaire(idCommentaire);

    // Mettre à jour la liste des commentaires
    List<Commentaire> commentaires = pc.listeDesCommentaires();
    ObservableList<Commentaire> observableCommentaires = FXCollections.observableArrayList(commentaires);
    tableview.setItems(observableCommentaires);

    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Commentaire");
    alert.setContentText("Commentaire supprimé !!");
    alert.show();
}

    }

