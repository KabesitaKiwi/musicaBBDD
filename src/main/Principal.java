package main;

        import bbdd.Conexion;
        import gui.Controlador;
        import gui.Modelo;
        import gui.Vista;
        import javax.swing.*;
        import com.formdev.flatlaf.FlatLightLaf;
public class Principal {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Conexion conexion = new Conexion();
        Controlador controlador = new Controlador(modelo,vista, conexion);

    }
}
