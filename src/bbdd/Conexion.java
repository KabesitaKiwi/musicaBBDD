package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Conexion {
    private String ip;
    private String user;
    private String contra;
    private String adminContra;

    private Connection conn;

    public String getIp() {
        return ip;
    }

    public String getUser() {
        return user;
    }

    public String getContra() {
        return contra;
    }

    public String getAdminContra() {
        return adminContra;
    }

    void conectar(){
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://"+ip+":3306/musica",user,contra
            );
        } catch (SQLException e) {
            try {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://"+ip+":3306/musica",user,contra
                );
                PreparedStatement pst = null;
                String code = leerFichero();
                String[] SQL = code.split("--");
                for(String aSQL :SQL){
                    pst=conn.prepareStatement(aSQL);
                    pst.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }



        }
    }

    void desconectar(){
        try {
            conn.close();
            conn=null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String leerFichero(){
        return "";
    }
}
