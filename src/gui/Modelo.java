package gui;

import bbdd.Conexion;
import modelo.Album;
import modelo.Autor;
import modelo.Canciones;
import modelo.Productora;

import java.sql.*;
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
                "a.duracionMinutos as 'Duracion en Minutos', a.fechaSalida as 'Fecha de Salida', prod.nombre as 'Productora' FROM album as a "+
                "inner join autor as au" +
                " on a.idAutor = au.idAutor " +
                "inner join productora as prod " +
                "on a.idProductora = prod.idProductora";
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
        String SQL = "INSERT INTO cancion (titulo, idAutor, genero, idProductora, participantes, duracion, idioma, valoracion, idAlbum) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
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
        String SQL =
                "SELECT c.idCancion as 'ID', c.titulo AS 'Título', a.nombreArtistico AS 'Autor', c.genero AS 'Género', p.nombre AS 'Productora', " +
                        "c.participantes AS 'Participantes', c.duracion AS 'Duración', c.idioma AS 'Idioma', " +
                        "c.valoracion AS 'Valoración', al.titulo AS 'Album' FROM cancion c " +
                        "INNER JOIN autor a ON c.idAutor = a.idAutor " +
                        "INNER JOIN productora p ON c.idProductora = p.idProductora " +
                        "INNER JOIN album al ON c.idAlbum = al.idAlbum";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = Conexion.conn.prepareStatement(SQL);
        resultado = sentencia.executeQuery();
        return resultado;
    }


    public boolean existeCancionPorAutor(int idAutor, String titulo) {
        String sql = "SELECT 1 FROM cancion WHERE idAutor = ? AND titulo = ? LIMIT 1";
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

    public boolean existeCancionEnMismoAlbum(String titulo, int idAlbum) {
        String sql = "SELECT 1 FROM cancion WHERE titulo=? AND idAlbum=? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setInt(2, idAlbum);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }






    void eliminarAutor(int idAutor){
        String SQL = "DELETE FROM autor WHERE idAutor = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setInt(1,idAutor);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    boolean autorConCanciones(int idAutor){
        String SQL = "SELECT 1 FROM album WHERE idAutor=? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(SQL)) {
            ps.setInt(1, idAutor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    void eliminarCancion(int idCancion){
        String SQL = "DELETE FROM cancion WHERE idCancion = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setInt(1,idCancion);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void eliminarAlbum(int idAlbum){
        String SQL = "DELETE FROM album WHERE idAlbum = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setInt(1,idAlbum);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }


    void eliminarProductora(int idProductora){
        String SQL = "DELETE FROM productora WHERE idProductora = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setInt(1,idProductora);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    boolean productoraConCanciones(int idProductora){
        String sql = "SELECT 1 FROM cancion WHERE idProductora = ? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(sql)) {
            ps.setInt(1, idProductora);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    boolean modificarAutor(Autor au){
        String SQL = "UPDATE autor set nombreArtistico =?, nombreReal=?, edad =?, pais=?, fechaPrimeraPublicacion=?, gira =?" +
                " where idAutor =?";
        PreparedStatement sentencia = null;
        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setString(1, au.getNombreArtistico());
            sentencia.setString(2, au.getNombreReal());
            sentencia.setInt(3, au.getEdad());
            sentencia.setString(4, au.getPais());
            LocalDate fecha = au.getFechaPublicacion();
            if (fecha == null) {
                sentencia.setNull(5, java.sql.Types.DATE);
            } else {
                sentencia.setDate(5, java.sql.Date.valueOf(fecha)); //aqui por incompatibilidad de LocalDate y Date de la base de datos, convierto el valor de localDate a un Date para que funcione bien
            }
            sentencia.setBoolean(6, au.getGira());
            sentencia.setInt(7,au.getIdAutor());


            return sentencia.executeUpdate() >0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    boolean existeAutorExceptoId(String nombreArtistico, int idAutor) {
        String SQL = "SELECT 1 FROM autor WHERE nombreArtistico=? AND idAutor<>? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(SQL)) {
            ps.setString(1, nombreArtistico);
            ps.setInt(2, idAutor);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean modificarProductora(Productora prod){
        String SQL = "UPDATE productora set nombre =?, localizacion=?, trabajadores =?, fechaFundacion=?, propietario =?" +
                " where idProductora =?";
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
            sentencia.setInt(6,prod.getIdPord());


            return sentencia.executeUpdate() >0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    boolean existeProductoraExceptoId(String nombre, int idProductora) {
        String SQL = "SELECT 1 FROM productora WHERE nombre=? AND idProductora<>? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(SQL)) {
            ps.setString(1, nombre);
            ps.setInt(2, idProductora);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean modificarAlbum(Album al){
        String SQL = "UPDATE album set idAutor =?, titulo=?, numeroCanciones =?, duracionMinutos=?, fechaSalida =?, idProductora = ?" +
                " where idAlbum =?";
        PreparedStatement sentencia = null;
        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setInt(1, al.getIdAutor());
            sentencia.setString(2, al.getTitulo());
            sentencia.setInt(3, al.getNumeroCanciones());
            sentencia.setInt(4,al.getDuracionMins());
            LocalDate fecha = al.getFechaSalida();
            if (fecha == null) {
                sentencia.setNull(5, java.sql.Types.DATE);
            } else {
                sentencia.setDate(5, java.sql.Date.valueOf(fecha)); //aqui por incompatibilidad de LocalDate y Date de la base de datos, convierto el valor de localDate a un Date para que funcione bien
            }
            sentencia.setInt(6, al.getIdProductora());
            sentencia.setInt(7,al.getIdAlbum());


            return sentencia.executeUpdate() >0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public boolean existeAlbumAutorExceptoId(int idAutor, String titulo, int idAlbum) {
        String sql = "SELECT 1 FROM album WHERE idAutor = ? AND titulo = ? AND idAlbum <> ? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(sql)) {
            ps.setInt(1, idAutor);
            ps.setString(2, titulo);
            ps.setInt(3, idAlbum);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean modificarCancion(Canciones c){
        String SQL = "UPDATE cancion set titulo =?, idAutor=?, genero =?, idProductora=?, participantes =?, duracion = ?, " +
                "idioma = ?, valoracion = ?, idAlbum = ? " +
                "WHERE idCancion =?";
        PreparedStatement sentencia = null;
        try {
            sentencia = Conexion.conn.prepareStatement(SQL);
            sentencia.setString(1, c.getTitulo());
            sentencia.setInt(2, c.getAutor());
            sentencia.setString(3,c.getGenero());
            sentencia.setInt(4, c.getProductora());
            sentencia.setInt(5,c.getNumeroParticipantes());
            sentencia.setFloat(6, c.getDuracion());
            sentencia.setString(7, c.getIdioma());
            sentencia.setInt(8, c.getValoracion());
            sentencia.setInt(9, c.getAlbum());
            sentencia.setInt(10,c.getIdCancion());


            return sentencia.executeUpdate() >0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    boolean existeCancionId(String titulo, int idCancion) {
        String SQL = "SELECT 1 FROM cancion WHERE titulo=? AND idCancion<>? LIMIT 1";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(SQL)) {
            ps.setString(1, titulo);
            ps.setInt(2, idCancion);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean buscarNombreCancion(String nombre) throws SQLException {
        String SQL = "SELECT * FROM cancion where nombre =?";
        try (PreparedStatement ps = Conexion.conn.prepareStatement(SQL)){
                ps.setString(1, nombre);

                ResultSet rs = ps.executeQuery();
                return rs.next();
        }
    }

}
