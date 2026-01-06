package gui;

import bbdd.Conexion;
import modelo.Album;
import modelo.Autor;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;

public class Controlador extends Component implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

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
        refrescarTodo();

    }

    private void refrescarTodo(){
        refrescarAutores();
        refrescar = false;
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
                    registrarAutor();
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

    public void registrarAutor(){
        if (!Util.comprobarCampoVacio(vista.campoNombreArtistico)){
            Util.lanzaAlertaVacio(vista.campoNombreArtistico);
        }else if(!Util.comprobarCampoVacio(vista.campoNombreReal)){
            Util.lanzaAlertaVacio(vista.campoNombreReal);
        }else if (!Util.comprobarSpinner(vista.campoEdad)){
            JOptionPane.showMessageDialog(null, "El campo edad no puede ser menor que 13");
        }else if(Util.comprobarCombobox(vista.campoPais)){
            Util.lanzaAlertaCombo(vista.campoPais);
        }else if(Util.campoVacioCalendario(vista.campoFechaPrimeraPubli)){
            Util.lanzaAlertaVacioCalendar(vista.campoFechaPrimeraPubli);
        }else{

            boolean gira = vista.siRadioButton.isSelected();
            String nombreArtistico = vista.campoNombreArtistico.getText();
            String nombreReal = vista.campoNombreReal.getText();
            int edad = (int) vista.campoEdad.getValue();
            String pais = vista.campoPais.getSelectedItem().toString();
            LocalDate fecha = vista.campoFechaPrimeraPubli.getDate();
            Autor a = new Autor(nombreArtistico,nombreReal,edad,pais,fecha,gira);

            if (modelo.existeAutor(a.getNombreArtistico())){
                JOptionPane.showMessageDialog(null, "Este autor ya existe, haz otro");
                vista.campoNombreArtistico.setText("");
                return;
            }

            if (modelo.insertarAutor(a)){
                JOptionPane.showMessageDialog(null, "El autor ha sido registrado correctamente");
                borrarCamposAutor();
                refrescarAutores();
            }else {
                JOptionPane.showMessageDialog(null, "Autor no registrado ");
            }
        }

    }

    public void registrarAlbum(){
        if (!Util.comprobarCampoVacio(vista.campoTituloAlbum)){
            Util.lanzaAlertaVacio(vista.campoTituloAlbum);
        }else if(Util.comprobarCombobox(vista.comboAutores)){
            Util.lanzaAlertaCombo(vista.comboAutores);
        }else if (!Util.comprobarSpinner(vista.campoNumCanciones)){
            JOptionPane.showMessageDialog(null, "El campo Numero de canciones no puede ser menor que 0");
        }else if(Util.comprobarSpinner(vista.campoDuracion)){
            JOptionPane.showMessageDialog(null, "El campo Duracion en minutos no puede ser menor que 0");
        }else if(Util.campoVacioCalendario(vista.campoFechaSalidaAlbum)){
            Util.lanzaAlertaVacioCalendar(vista.campoFechaSalidaAlbum);
        }else if(Util.comprobarCombobox(vista.comboProductora)){
            Util.lanzaAlertaCombo(vista.comboProductora);
        }else{
            String titulo = vista.campoTituloAlbum.getText();
            int autor = (int) vista.comboAutores.getSelectedItem();
            int numCanciones = (int)vista.campoNumCanciones.getValue();
            int duracionCanciones = (int)vista.campoDuracion.getValue();
            LocalDate fechaSalida = vista.campoFechaSalidaAlbum.getDate();
            int productora = (int)vista.comboProductora.getSelectedItem();

            Album au = new Album(autor,titulo,numCanciones,duracionCanciones,fechaSalida,productora);

            if (modelo.existeAlbumPorAutor(au.getIdAutor(),au.getTitulo())){
                JOptionPane.showMessageDialog(null, "Este Artista ya tiene un album con ese nombre, cambia el arttista o el nombre del album");
                vista.campoTituloAlbum.setText("");
                vista.campoAutor.setSelectedIndex(0);
                return;
            }

            if (modelo.insertarAlbum(au)){
                JOptionPane.showMessageDialog(null, "El Album ha sido registrado correctamente");
                borrarCamposAutor();
                refrescarAutores();
            }else {
                JOptionPane.showMessageDialog(null, "Album no registrado ");
            }
        }
    }

    private void borrarCamposAutor(){
        vista.campoNombreArtistico.setText("");
        vista.campoNombreReal.setText("");
        vista.campoEdad.setValue(16);
        vista.campoPais.setSelectedIndex(0);
        vista.campoFechaPrimeraPubli.setText("");
        vista.siRadioButton.isSelected();
    }

    private void refrescarAutores(){
        try {
            vista.tablaAutor.setModel(construirTableModelAutores(modelo.consultarAutores()));
            vista.campoAutor.removeAllItems();
            for(int i = 0; i < vista.dtmAutores.getRowCount(); i++) {
                vista.campoAutor.addItem(vista.dtmAutores.getValueAt(i, 0)+" - "+
                        vista.dtmAutores.getValueAt(i, 1));
                vista.comboAutores.addItem(vista.dtmAutores.getValueAt(i, 0)+" - "+
                        vista.dtmAutores.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelAutores(ResultSet rs) throws SQLException {

            ResultSetMetaData metaData = rs.getMetaData();

        //nombre de las columnas
            Vector<String> nombreColumnas = new Vector<>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++){
                nombreColumnas.add(metaData.getColumnName(column));
            }

        //datis de la tabla
            Vector<Vector<Object>> data = new Vector<>();
            setDataVector(rs, columnCount, data);

            vista.dtmAutores.setDataVector(data, nombreColumnas);

            return vista.dtmAutores;

    }



    private DefaultTableModel construirTableModelAlbum(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        //nombre de las columnas
        Vector<String> nombreColumnas = new Vector<>();
        int numeroColumnas = metaData.getColumnCount();
        for (int column = 1; column <= numeroColumnas; column++){
            nombreColumnas.add(metaData.getColumnName(column));
        }

        //datos de la tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, numeroColumnas, data);

        vista.dtmAlbum.setDataVector(data, nombreColumnas);

        return vista.dtmAlbum;
    }

    private void setDataVector(ResultSet rs, int columnCount, Vector<Vector<Object>> data) throws SQLException {
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }
    }
}
