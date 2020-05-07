package es.tiendaslocales;

public class User {

    private String nom;
    private String clau;
    private String cognoms;
    private String direccio;
    private String email;
    private String telefon;
    private String imgperfil;

    public User(){

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getClau() {
        return clau;
    }

    public void setClau(String clau) {
        this.clau = clau;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getDireccio() {
        return direccio;
    }

    public void setDireccio(String direccio) {
        this.direccio = direccio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getImgperfil() {
        return imgperfil;
    }

    public void setImgperfil(String imgperfil) {
        this.imgperfil = imgperfil;
    }

    public String getText(){
        String text="nom="+nom+"\n";
        text+="clau="+clau+"\n";
        text+="clau="+cognoms+"\n";
        text+="clau="+direccio+"\n";
        text+="clau="+email+"\n";
        text+="clau="+telefon+"\n";
        text+="clau="+imgperfil+"\n";
        return text;
    }

}

