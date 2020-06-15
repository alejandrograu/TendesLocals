package es.tiendaslocales;

public class Producte {

    private String nom;
    private String poblacio;
    private String tenda;
    private String pvp;
    private String categoria;
    private String subcategoria;
    private String descripcio;
    private String image;

    public void Producte(){

    }
    public void Producte(String nom,String poblacio, String tenda, String pvp, String categoria, String subcategoria, String descripcio, String image){
        this.nom=nom;
        this.poblacio=poblacio;
        this.tenda=tenda;
        this.pvp=pvp;
        this.categoria=categoria;
        this.subcategoria=subcategoria;
        this.descripcio=descripcio;
        this.image=image;
    }

    public String getNom(){ return nom; }

    public void setNom(String nom) {this.nom=nom; }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public String getTenda(){return tenda;}

    public void setTenda(String tenda){ this.tenda=tenda;}

    public String getImage(){return image;}

    public void setImage(String image){this.image=image;}

    public String getPvp(){ return pvp; }

    public void setPvp(String pvp){ this.pvp=pvp; }

    public String getDescripcio(){ return descripcio; }

    public void setDescripcio(String descripcio){ this.descripcio=descripcio; }

    public String getCategoria(){return categoria; }

    public void setCategoria(String categoria){ this.categoria=categoria; }

    public String getSubcategoria(){ return subcategoria; }

    public void setSubcategoria(String subcategoria){ this.subcategoria=subcategoria; }


}
