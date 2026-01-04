package modelo;

import java.sql.Date;

public class Album {
    int idAlbum;
    int idAutor;
    String titulo;
    int numeroCanciones;
    int duracionMins;
    Date fechaSalida;
    int idProductora;

    public Album(int idAutor, String titulo, int numeroCanciones, int duracionMins, Date fechaSalida, int idProductora) {
        this.idAlbum = 0;
        this.idAutor = idAutor;
        this.titulo = titulo;
        this.numeroCanciones = numeroCanciones;
        this.duracionMins = duracionMins;
        this.fechaSalida = fechaSalida;
        this.idProductora = idProductora;
    }

    public Album(int idAlbum, int idAutor, String titulo, int numeroCanciones, int duracionMins, Date fechaSalida, int idProductora) {
        this.idAlbum = idAlbum;
        this.idAutor = idAutor;
        this.titulo = titulo;
        this.numeroCanciones = numeroCanciones;
        this.duracionMins = duracionMins;
        this.fechaSalida = fechaSalida;
        this.idProductora = idProductora;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroCanciones() {
        return numeroCanciones;
    }

    public void setNumeroCanciones(int numeroCanciones) {
        this.numeroCanciones = numeroCanciones;
    }

    public int getDuracionMins() {
        return duracionMins;
    }

    public void setDuracionMins(int duracionMins) {
        this.duracionMins = duracionMins;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getIdProductora() {
        return idProductora;
    }

    public void setIdProductora(int idProductora) {
        this.idProductora = idProductora;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
