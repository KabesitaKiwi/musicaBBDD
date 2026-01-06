package gui;

import bbdd.Conexion;
import modelo.Album;
import modelo.Autor;
import modelo.Canciones;
import modelo.Productora;

import javax.swing.*;
import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;

public class Modelo {


    boolean insertarAutor(Autor al){
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
            return true;
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
        return false;
    }

    ResultSet consultarAutores() throws SQLException{
        String SQL = "SELECT idAutor as 'ID', nombreArtistico as 'Nombre Artístico', nombreReal as 'Nombre', "
                + "edad as 'Edad', pais as 'Pais', fechaPrimeraPublicacion as'Fecha primera canción', gira as '¿De gira?' FROM autor";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = Conexion.conn.prepareStatement(SQL);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    public boolean existeAutor(String nombreArtistico) {
        String sql = "SELECT 1 FROM autor WHERE nombreArtistico = ? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(sql)) {
            ps.setString(1, nombreArtistico);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    boolean insertarAlbum(Album al){

        String SQL = "INSERT INTO album (idAutor, titulo, numeroCanciones, duracionMinutos, fechaSalida, idProductora) " +
                    "VALUES (?,?,?,?,?,?)";
        PreparedStatement sentencia = null;
        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setInt(1, al.getIdAutor());
            sentencia.setString(2, al.getTitulo());
            sentencia.setInt(3, al.getNumeroCanciones());
            sentencia.setInt(4, al.getDuracionMins());
            LocalDate fecha = al.getFechaSalida();
            if (fecha == null) {
                sentencia.setNull(5, java.sql.Types.DATE);
            } else {
                sentencia.setDate(5, java.sql.Date.valueOf(fecha)); //aqui por incompatibilidad de LocalDate y Date de la base de datos, convierto el valor de localDate a un Date para que funcione bien
            }
            sentencia.setInt(6, al.getIdProductora());

            sentencia.executeUpdate();
            return true;
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
        return false;

    }

    public boolean existeAlbumPorAutor(int idAutor, String titulo) {
        String sql = "SELECT 1 FROM album WHERE idAutor = ? AND titulo = ? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(sql)) {
            ps.setInt(1, idAutor);
            ps.setString(2, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    ResultSet consultarAlbum() throws SQLException {
        String SQL = "SELECT a.idAlbum as 'ID', au.nombreArtistico as 'Autor', a.titulo as 'titulo', a.numeroCanciones as 'Numero Canciones', "+
                "a.duracionMinutos as 'Duracion en Minutos', a.fechaSalida as 'Fecha de Salida', a.idProductora as 'Porductora' FROM album as a "+
                "inner join autor as au" +
                " on a.idAutor = au.idAutor";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = Conexion.conn.prepareStatement(SQL);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    boolean insertarProductora(Productora prod){
        String SQL = "INSERT INTO productora(nombre, localizacion, trabajadores, fechaFundacion, propietario) VALUES" +
                "(?,?,?,?,?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setString(1, prod.getNombre());
            sentencia.setString(2, prod.getLocalizacion());
            sentencia.setInt(3, prod.getTrabajadores());
            LocalDate fecha = prod.getFechaFundacion();
            if (fecha == null) {
                sentencia.setNull(4, java.sql.Types.DATE);
            } else {
                sentencia.setDate(4, java.sql.Date.valueOf(fecha)); //aqui por incompatibilidad de LocalDate y Date de la base de datos, convierto el valor de localDate a un Date para que funcione bien
            }
            sentencia.setString(5, prod.getPropietario());

            sentencia.executeUpdate();
            return true;
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
        return false;
    }

    ResultSet consultarProductora() throws SQLException {
        String SQL = "SELECT idProductora as 'ID', nombre as 'Autor', localizacion as 'localizacion', trabajadores as 'Trabajadores', "+
                " fechaFundacion as 'Fecha Fundación', propietario as 'Propietario' FROM productora";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = Conexion.conn.prepareStatement(SQL);
        resultado = sentencia.executeQuery();
        return resultado;
    }

    public boolean existeProductora(String nombreProd) {
        String sql = "SELECT 1 FROM productora WHERE nombre = ? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(sql)) {
            ps.setString(1, nombreProd);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean insertarCanciones(Canciones can){
        String SQL = "INSERT INTO cancion(titulo as 'titulo', idAutor as 'Autor', genero as 'Género', idProductora as 'Productora', " +
                "principiantes as 'Principiantes', duracion as 'Duración', idioma as 'Idioma', valoracion as 'Valoración', idAlbum as 'Album')VALUES" +
                "(?,?,?,?,?,?,?,?,?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setString(1, can.getTitulo());
            sentencia.setInt(2, can.getAutor());
            sentencia.setString(3, can.getGenero());
            sentencia.setInt(4, can.getProductora());
            sentencia.setInt(5, can.getNumeroParticipantes());
            sentencia.setFloat(6, can.getDuracion());
            sentencia.setString(7,can.getIdioma());
            sentencia.setInt(8, can.getValoracion());
            sentencia.setInt(9,can.getAlbum());
            sentencia.executeUpdate();
            return true;
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
        return false;
    }

    ResultSet consultarCancion() throws SQLException {
        String SQL = "SELECT titulo as 'Título', idAutor as 'Autor', genero as 'Género', idProductora as 'Productora', " +
                "principiantes as 'Participantes', duracion as 'Duración', idioma as 'Idioma', valoracion as 'Valoración', " +
                "idAlbum as 'Album' FROM cancion";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = Conexion.conn.prepareStatement(SQL);
        resultado = sentencia.executeQuery();
        return resultado;
    }


    public boolean existeCancionPorAlbum(int idAutor, String titulo) {
        String sql = "SELECT 1 FROM album WHERE idAutor = ? AND titulo = ? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(sql)) {
            ps.setInt(1, idAutor);
            ps.setString(2, titulo);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
