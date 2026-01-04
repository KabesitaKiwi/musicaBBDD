package gui;

import bbdd.Conexion;
import modelo.Album;
import modelo.Autor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class Modelo {


    void insertarAutor(Autor al){
        String SQL = "INSERT INTO autor(nombreArtistico, nombreReal, edad, pais, fechaPrimeraPublicacion, gira) VALUES" +
                "(?,?,?,?,?,?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setString(1, al.getNombreArtistico());
            sentencia.setString(2, al.getNombreReal());
            sentencia.setInt(3, al.getEdad());
            sentencia.setString(4, al.getPais());
            LocalDate fecha = al.getFechaPublicacion();
            if (fecha == null) {
                sentencia.setNull(5, java.sql.Types.DATE);
            } else {
                sentencia.setDate(5, java.sql.Date.valueOf(fecha)); //aqui por incompatibilidad de LocalDate y Date de la base de datos, convierto el valor de localDate a un Date para que funcione bien
            }
            sentencia.setBoolean(6, al.getGira());

            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (sentencia != null){
                try {
                    sentencia.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
