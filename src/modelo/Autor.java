package modelo;

import java.sql.Date;
import java.time.LocalDate;

public class Autor {
    Integer idAutor;
    String nombreArtistico;
    String nombreReal;
    int edad;
    String pais;
    LocalDate fechaPublicacion;
    boolean gira;

    public Autor(String nombreArtistico, String nombreReal, int edad, String pais, LocalDate fechaPublicacion, boolean gira) {
        this.idAutor = 0;
        this.nombreArtistico = nombreArtistico;
        this.nombreReal = nombreReal;
        this.edad = edad;
        this.pais = pais;
        this.fechaPublicacion = fechaPublicacion;
        this.gira = gira;
    }

    public Autor(Integer idAutor, String nombreArtistico, String nombreReal, int edad, String pais, LocalDate fechaPublicacion, boolean gira) {
        this.idAutor = idAutor;
        this.nombreArtistico = nombreArtistico;
        this.nombreReal = nombreReal;
        this.edad = edad;
        this.pais = pais;
        this.fechaPublicacion = fechaPublicacion;
        this.gira = gira;
    }


    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombreArtistico() {
        return nombreArtistico;
    }

    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public boolean getGira() {
        return gira;
    }

    public void setGira(boolean gira) {
        this.gira = gira;
    }

    @Override
    public String toString() {
        return nombreArtistico;
    }
}
