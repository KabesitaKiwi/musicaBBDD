package gui;

import bbdd.Conexion;
import modelo.Autor;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.Date;

public class Controlador implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    private bbdd.Conexion conexion;
    boolean refrescar;
    public Controlador(Modelo modelo, Vista vista, Conexion conexion){
        this.modelo = modelo;
        this.vista = vista;
        this.conexion = conexion;
        conexion.conectar();
        setOptions();
        addActionListeners(this);
        addWindowListeners(this);

    }

    private void addWindowListeners(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    private void addActionListeners(ActionListener listener){
        vista.botonAñadirAutor.addActionListener(listener);
        vista.botonAñadirAutor.setActionCommand("anadirAutor");
        vista.optionDialog.botonGuardarOpciones.addActionListener(listener);
        vista.optionDialog.botonGuardarOpciones.setActionCommand("guardarOpciones");
        vista.itemOpciones.addActionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){

            case "anadirAutor":{
                boolean gira = vista.siRadioButton.isSelected();
                String nombreArtistico = vista.campoNombreArtistico.getText();
                String nombreReal = vista.campoNombreReal.getText();
                int edad = (int) vista.campoEdad.getValue();
                String pais = vista.campoPais.getSelectedItem().toString();
                LocalDate fecha = vista.campoFechaPrimeraPubli.getDate();
                Autor a = new Autor(nombreArtistico,nombreReal,edad,pais,fecha,gira);

                modelo.insertarAutor(a);

                break;
            }
            case "guardarOpciones":
                conexion.setPropValues(vista.optionDialog.campoIp.getText(), vista.optionDialog.campoUsuario.getText(),
                        String.valueOf(vista.optionDialog.campoContra.getPassword()), String.valueOf(vista.optionDialog.campoContraAdmin.getPassword()));
                vista.optionDialog.dispose();
                vista.dispose();
                new Controlador(new Modelo(), new Vista(), new Conexion());
                break;

        }
    }

    @Override
    public void itemStateChanged(ItemEvent itemEvent) {

    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    @Override
    public void valueChanged(ListSelectionEvent listSelectionEvent) {

    }
    private void setOptions(){
        vista.optionDialog.campoIp.setText(conexion.getIp());
        vista.optionDialog.campoUsuario.setText(conexion.getUser());
        vista.optionDialog.campoContra.setText(conexion.getContra());
        vista.optionDialog.campoContraAdmin.setText(conexion.getAdminContra());
    }
}
