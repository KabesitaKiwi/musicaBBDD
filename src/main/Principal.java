package main;

        import bbdd.Conexion;
        import gui.Controlador;
        import gui.Modelo;
        import gui.Vista;

public class Principal {
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Conexion conexion = new Conexion();
        Controlador controlador = new Controlador(modelo,vista, conexion);

    }
}
