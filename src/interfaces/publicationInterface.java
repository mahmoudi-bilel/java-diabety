/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import entites.publication;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author user
 * @param <T>
 */
public interface publicationInterface<T> {
    public void supprimerpublication(int id);
    public void updatepublication(int id,publication t);
    public List<T> listedespublications();
 public List<publication> rechercherParTitre(String titre) throws SQLException;
 public String filterInappropriateWords(String title) ;
     public List<String> getalldescription();
     public void ajouterPublication(publication t) throws SQLException ;
public void signalerPublication(int id);



}
