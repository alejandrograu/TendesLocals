package es.tiendaslocales;

public class Poblacio {


    private String codi;
    private String poblacio;
    private String cp;
    private double lat;
    private double lon;
    public void Poblacio(){

    }
    public void Poblacio(String codi, String poblacio,String cp, double lat, double lon){
        this.codi=codi;
        this.poblacio=poblacio;
        this.cp=cp;
        this.lat=lat;
        this.lon=lon;
    }
    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(String poblacio) {
        this.poblacio = poblacio;
    }

    public String getCp(){ return cp;}

    public void setCp(String cp){ this.cp=cp;}

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

}
