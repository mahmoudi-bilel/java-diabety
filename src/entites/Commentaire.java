/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;


public class Commentaire {
    private int id_com;
   private String contenu;
   private publication id_publication;

    public Commentaire(String contenu, publication id_publication) {
        this.contenu = contenu;
        this.id_publication = id_publication;
    }
   

    public Commentaire(int id_com, String contenu, publication id_publication) {
        this.id_com = id_com;
        this.contenu = contenu;
        this.id_publication = id_publication;
    }

    public publication getId_publication() {
        return id_publication;
    }

    public void setId_publication(publication id_publication) {
        this.id_publication = id_publication;
    }
    
    

    

    
public int getId_com() {
    return this.id_com;
}
    public Commentaire(int id_com, String contenu) {
        this.id_com = id_com;
        this.contenu = contenu;
    }

    public Commentaire() {
         this.id_com = 0;
        this.contenu = "";
    }

    

    public String getContenu() {
        return contenu;
    }

    public void setId_com(int id_com) {
        this.id_com = id_com;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id_com=" + id_com + ", contenu=" + contenu + '}';
    }
    
}


