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
    public void ajouterpublication(T t)throws SQLException;
    public void supprimerpublication(int id);
    public void updatepublication(int id,T t);
    public List<T> listedespublications();
}
