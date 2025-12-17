package bbdd;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

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
            } catch (SQLException | IOException ex) {
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

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("basedatos.sql"));
        String linea;
        StringBuilder stringBuilder = new StringBuilder();
        while ((linea = reader.readLine()) != null){
            stringBuilder.append(linea);
            stringBuilder.append(" ");
        }

        return stringBuilder.toString();
    }

    private void getPropValues(){
        InputStream inputStream = null;
        try {

            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new FileInputStream(propFileName);

            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            contra = prop.getProperty("pass");
            adminContra = prop.getProperty("admin");

        }catch (Exception e){
            System.out.println("Exception: " + e);
        }finally {
            try {
                if (inputStream != null) inputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
