package modelo;


import java.sql.Date;
import java.time.LocalDate;

public class Productora {
    int idPord;
    String nombre;
    String localizacion;
    int trabajadores;
    LocalDate fechaFundacion;
    String propietario;

    public Productora(int idPord, String nombre, String localizacion, int trabajadores, LocalDate fechaFundacion, String propietario) {
        this.idPord = idPord;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.trabajadores = trabajadores;
        this.fechaFundacion = fechaFundacion;
        this.propietario = propietario;
    }

    public Productora(String nombre, String localizacion, int trabajadores, LocalDate fechaFundacion, String propietario) {
        this.idPord = 0;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.trabajadores = trabajadores;
        this.fechaFundacion = fechaFundacion;
        this.propietario = propietario;
    }

    public int getIdPord() {
        return idPord;
    }

    public void setIdPord(int idPord) {
        this.idPord = idPord;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public int getTrabajadores() {
        return trabajadores;
    }

    public void setTrabajadores(int trabajadores) {
        this.trabajadores = trabajadores;
    }

    public LocalDate getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(LocalDate fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
