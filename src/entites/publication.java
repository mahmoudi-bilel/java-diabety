/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/**
 *
 * @author Bilel Mahmoudi
 */
public class publication {
    private int id;
    private String titre;
    private String description;
    private String email;
    private int numerodetel;

    public publication(String description) {
        this.description = description;
    }

    
    public publication(String titre, String description, String email, int numerodetel) {
        this.titre = titre;
        this.description = description;
        this.email = email;
        this.numerodetel = numerodetel;
    }

    public publication() {
    }
    public publication(String titreValue, String descriptionValue, String emailValue, String numdetelValue) {
    this.titre = titreValue;
    this.description = descriptionValue;
    this.email = emailValue;
    this.numerodetel = Integer.parseInt(numdetelValue);
}

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumerodetel() {
        return numerodetel;
    }

    public void setNumerodetel(int numerodetel) {
        this.numerodetel = numerodetel;
    }

   
    
    

   
   

}
