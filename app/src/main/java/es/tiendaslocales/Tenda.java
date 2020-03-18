package es.tiendaslocales;


public class Tenda {


    String codi;
    String nom;
    double lat;
    double lon;
    String poblacio;
    String adresa;
    String telefon;
    String foto;
    String tipus;

    public void Tenda(){

    }
    public void Tenda(String codi, String nom, double lat, double lon, String poblacio, String adresa, String telefon, String foto, String tipus){
        this.codi=codi;
        this.nom=nom;
        this.lat=lat;
        this.lon=lon;
        this.poblacio=poblacio;
        this.adresa=adresa;
        this.telefon=telefon;
        this.foto=foto;
        this.tipus=tipus;

    }
    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

}
