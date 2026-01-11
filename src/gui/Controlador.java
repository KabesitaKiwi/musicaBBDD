package gui;

import bbdd.Conexion;
import modelo.Album;
import modelo.Autor;
import modelo.Canciones;
import modelo.Productora;
import util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Vector;

public class Controlador extends Component implements ActionListener, ItemListener, ListSelectionListener, WindowListener {

    private Modelo modelo;
    private Vista vista;
    private bbdd.Conexion conexion;
    boolean refrescar;

    public Controlador(Modelo modelo, Vista vista, Conexion conexion) {
        this.modelo = modelo;
        this.vista = vista;
        this.conexion = conexion;
        conexion.conectar();
        setOptions();
        addActionListeners(this);
        addWindowListeners(this);
        refrescarTodo();
        iniciar();

    }

    private void refrescarTodo() {
        refrescarCanciones();
        refrescarAutores();
        refrescarAlbum();
        refrescarProductoras();
        refrescar = false;
    }

    private void addWindowListeners(WindowListener listener) {
        vista.addWindowListener(listener);
    }

    private void addActionListeners(ActionListener listener) {
        vista.botonAñadirAutor.addActionListener(listener);
        vista.botonAñadirAutor.setActionCommand("anadirAutor");
        vista.botonAñadirAlbum.addActionListener(listener);
        vista.botonAñadirAlbum.setActionCommand("anadirAlbum");
        vista.botonAñadirProductora.addActionListener(listener);
        vista.botonAñadirProductora.setActionCommand("anadirProd");
        vista.añadirButton.addActionListener(listener);
        vista.añadirButton.setActionCommand("anadirCancion");
        vista.eliminarButton.addActionListener(listener);
        vista.eliminarButton.setActionCommand("eliminarCancion");
        vista.botonEliminarAutor.addActionListener(listener);
        vista.botonEliminarAutor.setActionCommand("eliminarAutor");
        vista.botonEliminarAlbum.addActionListener(listener);
        vista.botonEliminarAlbum.setActionCommand("eliminarAlbum");
        vista.botonEliminarProd.addActionListener(listener);
        vista.botonEliminarProd.setActionCommand("eliminarProductora");
        vista.botonModificarAutor.addActionListener(listener);
        vista.botonModificarAutor.setActionCommand("actualizarAutor");
        vista.botonModificarAlbum.addActionListener(listener);
        vista.botonModificarAlbum.setActionCommand("actualizarAlbum");
        vista.botonModificarProd.addActionListener(listener);
        vista.botonModificarProd.setActionCommand("actualizarProductora");
        vista.modificarButton.addActionListener(listener);
        vista.modificarButton.setActionCommand("actualizarCancion");
        vista.optionDialog.botonGuardarOpciones.addActionListener(listener);
        vista.optionDialog.botonGuardarOpciones.setActionCommand("guardarOpciones");
        vista.itemOpciones.addActionListener(listener);
        vista.itemSalir.addActionListener(listener);
        vista.itemDesconectar.addActionListener(listener);
        vista.botonValidar.addActionListener(listener);
        vista.itemConectar.addActionListener(listener);
        vista.botonBuscar.addActionListener(listener);
        vista.botonBuscar.setActionCommand("Buscar");
        vista.vistaBuscar.botonBusqueda.addActionListener(listener);
        vista.vistaBuscar.botonBusqueda.setActionCommand("Busqueda");
        vista.vistaBuscar.comboSeleccionarTabla.addActionListener(listener);
        vista.vistaBuscar.comboSeleccionarTabla.setActionCommand("CambiarCosicas");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Opciones":
                vista.adminPasswordDialog.setVisible(true);
                break;

            case "Desconectar":
                conexion.desconectar();
                JOptionPane.showMessageDialog(null, "Usted ha desconectado con la base de datos ten en cuenta que no va a funcionar casi nada hasta que lo vuelvas a conectar");
                break;

            case "Salir":
                System.exit(0);
                break;
            case "Conectar":
                conexion.conectar();
                JOptionPane.showMessageDialog(null, "Usted ha vuelto a conectar con la base de datos, todo funcionará a la perfeccion nuevamente");
                break;

            case "Buscar":
                vista.vistaBuscar.setVisible(true);
                break;

            case "abrirOpciones":
                if(String.valueOf(vista.campoContrasenya.getPassword()).equals(conexion.getAdminContra())) {
                    vista.campoContrasenya.setText("");
                    vista.adminPasswordDialog.dispose();
                    vista.optionDialog.setVisible(true);
                } else {
                    Util.showErrorAlert("La contraseña introducida no es correcta.");
                }
                break;
            case "anadirCancion": {
                registrarCancion();
                break;
            }

            case "anadirAutor": {
                registrarAutor();
                break;
            }
            case "guardarOpciones": {
                conexion.setPropValues(vista.optionDialog.campoIp.getText(), vista.optionDialog.campoUsuario.getText(),
                        String.valueOf(vista.optionDialog.campoContra.getPassword()), String.valueOf(vista.optionDialog.campoContraAdmin.getPassword()));
                vista.optionDialog.dispose();
                vista.dispose();
                new Controlador(new Modelo(), new Vista(), new Conexion());
                break;
            }
            case "anadirAlbum": {
                registrarAlbum();
                break;
            }
            case "anadirProd": {
                registrarProductora();
                break;
            }
            case "eliminarCancion": {
                eliminarCancion();
                break;
            }
            case "eliminarAutor": {
                eliminarAutor();
                break;
            }
            case "eliminarAlbum": {
                eliminarAlbum();
                break;
            }
            case "eliminarProductora": {
                eliminarProductora();
                break;
            }

            case"actualizarAutor":{
                actualizarAutor();
                break;
            }

            case "actualizarAlbum":{
                actualizarAlbum();
                break;
            }
            case "actualizarProductora":{
                actualizarProductora();
                break;
            }
            case "actualizarCancion": {
                actualizarCancion();
                break;
            }
            case "Busqueda":{
                try {
                    buscar();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                break;
            }
            case "CambiarCosicas":{
                actualizarCombo();
            }

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
        System.exit(0);
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
    public void valueChanged(ListSelectionEvent e) {
    }

    private void setOptions() {
        vista.optionDialog.campoIp.setText(conexion.getIp());
        vista.optionDialog.campoUsuario.setText(conexion.getUser());
        vista.optionDialog.campoContra.setText(conexion.getContra());
        vista.optionDialog.campoContraAdmin.setText(conexion.getAdminContra());
    }

    public void registrarAutor() {
        if (!Util.comprobarCampoVacio(vista.campoNombreArtistico)) {
            Util.lanzaAlertaVacio(vista.campoNombreArtistico);
        } else if (!Util.comprobarCampoVacio(vista.campoNombreReal)) {
            Util.lanzaAlertaVacio(vista.campoNombreReal);
        } else if (!Util.comprobarSpinner(vista.campoEdad)) {
            JOptionPane.showMessageDialog(null, "El campo edad no puede ser menor que 13");
        } else if (Util.comprobarCombobox(vista.campoPais)) {
            Util.lanzaAlertaCombo(vista.campoPais);
        } else if (Util.campoVacioCalendario(vista.campoFechaPrimeraPubli)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaPrimeraPubli);
        } else {

            boolean gira = vista.siRadioButton.isSelected();
            String nombreArtistico = vista.campoNombreArtistico.getText();
            String nombreReal = vista.campoNombreReal.getText();
            int edad = (int) vista.campoEdad.getValue();
            String pais = vista.campoPais.getSelectedItem().toString();
            LocalDate fecha = vista.campoFechaPrimeraPubli.getDate();
            Autor a = new Autor(nombreArtistico, nombreReal, edad, pais, fecha, gira);

            if (modelo.existeAutor(a.getNombreArtistico())) {
                JOptionPane.showMessageDialog(null, "Este autor ya existe, haz otro");
                vista.campoNombreArtistico.setText("");
                return;
            }

            if (modelo.insertarAutor(a)) {
                JOptionPane.showMessageDialog(null, "El autor ha sido registrado correctamente");
                borrarCamposAutor();
                refrescarAutores();
            } else {
                JOptionPane.showMessageDialog(null, "Autor no registrado ");
            }
        }

    }


    void eliminarAutor() {
        int fila = vista.tablaAutor.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona algun autor en la tabla");
            return;
        }

        Integer idAutor = (Integer) vista.tablaAutor.getValueAt(fila, 0);
        Object nombre = vista.tablaAutor.getValueAt(fila, 1);

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Seguro que quieres eliminar este autor?\n(" + nombre + ", con ID " + idAutor + ")",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (modelo.autorConCanciones(idAutor)) {
            JOptionPane.showMessageDialog(vista, "No se puede eliminar porque este autor tiene canciones y álbumes asociados.");
            return;
        }

        if (opcion == JOptionPane.YES_OPTION) {
            modelo.eliminarAutor(idAutor);
            borrarCamposAutor();
            refrescarAutores();
            JOptionPane.showMessageDialog(vista, "Autor eliminado.");
        }

    }

    public void registrarAlbum() {
        if (!Util.comprobarCampoVacio(vista.campoTituloAlbum)) {
            Util.lanzaAlertaVacio(vista.campoTituloAlbum);
        } else if (!Util.comprobarSpinner(vista.campoNumCanciones)) {
            JOptionPane.showMessageDialog(null, "El campo Numero de canciones no puede ser menor que 0");
        } else if (!Util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "El campo Duracion en minutos no puede ser menor que 0");
        } else if (Util.campoVacioCalendario(vista.campoFechaSalidaAlbum)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaSalidaAlbum);
        } else if (Util.comprobarCombobox(vista.comboProductora)) {
            Util.lanzaAlertaCombo(vista.comboProductora);
        } else {
            String titulo = vista.campoTituloAlbum.getText();
            String itemAutor = vista.comboAutores.getSelectedItem().toString();
            int autor = Integer.parseInt(itemAutor.split(" - ")[0].trim());
            int numCanciones = ((Number) vista.campoNumCanciones.getValue()).intValue();
            int duracionAlbum = ((Number) vista.campoNumDuracion.getValue()).intValue();
            LocalDate fechaSalida = vista.campoFechaSalidaAlbum.getDate();
            String itemProd = vista.comboProductora.getSelectedItem().toString();
            int productora = Integer.parseInt(itemProd.split(" - ")[0].trim());

            Album au = new Album(autor, titulo, numCanciones, duracionAlbum, fechaSalida, productora);

            if (modelo.existeAlbumPorAutor(au.getIdAutor(), au.getTitulo())) {
                JOptionPane.showMessageDialog(null, "Este Artista ya tiene un album con ese nombre, cambia el arttista o el nombre del album");
                vista.campoTituloAlbum.setText("");
                vista.campoAutor.setSelectedIndex(0);
                return;
            }

            if (modelo.insertarAlbum(au)) {
                JOptionPane.showMessageDialog(null, "El Album ha sido registrado correctamente");
                refrescarAlbum();
                borrarCamposAlbum();

            } else {
                JOptionPane.showMessageDialog(null, "Album no registrado ");
            }
        }
    }

    void eliminarAlbum() {
        int fila = vista.tablaAlbum.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona algun álbum en la tabla");
            return;
        }

        Integer idAlbum = (Integer) vista.tablaAlbum.getValueAt(fila, 0);
        Object nombre = vista.tablaAlbum.getValueAt(fila, 2);

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Seguro que quieres eliminar este álbum?\n(" + nombre + ", con ID " + idAlbum + ")",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (modelo.albumConCanciones(idAlbum)) {
            JOptionPane.showMessageDialog(vista, "No se puede eliminar porque hay canciones asociadas a esta productora.");
            return;
        }

        if (opcion == JOptionPane.YES_OPTION) {
            modelo.eliminarAlbum(idAlbum);
            borrarCamposAlbum();
            refrescarAlbum();
            JOptionPane.showMessageDialog(vista, "Álbum eliminado.");
        }

    }


    public void registrarProductora() {
        if (!Util.comprobarCampoVacio(vista.campoNombreProd)) {
            Util.lanzaAlertaVacio(vista.campoNombreProd);
        } else if (Util.comprobarCombobox(vista.comboLocalizacion)) {
            Util.lanzaAlertaCombo(vista.comboLocalizacion);
        } else if (!Util.comprobarSpinner(vista.campoNumTrabajadores)) {
            JOptionPane.showMessageDialog(null, "El campo Trabajadores no puede ser menor que 0");
        } else if (Util.campoVacioCalendario(vista.campoFechaFundacion)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaFundacion);
        } else if (!Util.comprobarCampoVacio(vista.campoPropietario)) {
            Util.lanzaAlertaVacio(vista.campoPropietario);
        } else {

            String nombre = vista.campoNombreProd.getText();
            String localizacion = vista.comboLocalizacion.getSelectedItem().toString();
            int numeroTrabajadores = (int) vista.campoNumTrabajadores.getValue();
            LocalDate fechaFundacion = vista.campoFechaFundacion.getDate();
            String propietario = vista.campoPropietario.getText();

            Productora prod = new Productora(nombre, localizacion, numeroTrabajadores, fechaFundacion, propietario);

            if (modelo.existeProductora(prod.getNombre())) {
                JOptionPane.showMessageDialog(null, "Esta Productora ya existe, cambia el nombre");
                vista.campoNombreProd.setText("");
                return;
            }

            if (modelo.insertarProductora(prod)) {
                JOptionPane.showMessageDialog(null, "La Productora ha sido registrado correctamente");
                borrarCamposProductora();
                refrescarProductoras();

            } else {
                JOptionPane.showMessageDialog(null, "Productora no registrado ");
            }

        }
    }

    void eliminarProductora() {
        int fila = vista.tablaProductora.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona alguna productora en la tabla");
            return;
        }

        Integer idProductora = (Integer) vista.tablaProductora.getValueAt(fila, 0);
        Object nombre = vista.tablaProductora.getValueAt(fila, 1);

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Seguro que quieres eliminar esta productora?\n(" + nombre + ", con ID " + idProductora + ")",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (modelo.productoraConCanciones(idProductora)) {
            JOptionPane.showMessageDialog(vista, "No se puede eliminar porque hay canciones asociadas a esta productora.");
            return;
        }

        if (opcion == JOptionPane.YES_OPTION) {
            modelo.eliminarProductora(idProductora);
            borrarCamposProductora();
            refrescarProductoras();
            JOptionPane.showMessageDialog(vista, "Productora eliminado.");
        }

    }

    public void registrarCancion() {

        if (!Util.comprobarCampoVacio(vista.campoTituloCancion)) {
            Util.lanzaAlertaVacio(vista.campoTituloCancion);
        } else if (!Util.comprobarCampoVacio(vista.campoGenero)) {
            Util.lanzaAlertaVacio(vista.campoGenero);
        } else if (!Util.comprobarSpinner(vista.campoNumParticipantes)) {
            JOptionPane.showMessageDialog(null, "El Nº de participantes no puede ser menor que 0");
        } else if (!Util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "La duración no puede ser menor o igual que 0");
        } else if (!vista.españolCheckBox.isSelected() && !vista.inglesCheckBox.isSelected() && !vista.dembowCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Selecciona al menos un idioma");
        } else {
            String titulo = vista.campoTituloCancion.getText();
            String genero = vista.campoGenero.getText();

            String itemAlbum = vista.campoALbum.getSelectedItem().toString();
            int Album = Integer.parseInt(itemAlbum.split(" - ")[0]);

            String itemAutor = vista.campoAutor.getSelectedItem().toString();
            int Autor = Integer.parseInt(itemAutor.split(" - ")[0]);

            String itemProd = vista.campoProd.getSelectedItem().toString();
            int Productora = Integer.parseInt(itemProd.split(" - ")[0]);

            int participantes = ((Number) vista.campoNumParticipantes.getValue()).intValue();
            float duracion = ((Number) vista.campoDuracion.getValue()).floatValue();
            int valoracion = vista.campoValoracion.getValue();

            StringBuilder idioma = new StringBuilder();
            if (vista.españolCheckBox.isSelected()) idioma.append("Español,");
            if (vista.inglesCheckBox.isSelected()) idioma.append("Ingles,");
            if (vista.dembowCheckBox.isSelected()) idioma.append("Dembow,");
            idioma.deleteCharAt(idioma.length() - 1);

            Canciones c = new Canciones(titulo, Album, Autor, genero, Productora,
                    participantes, duracion, idioma.toString(), valoracion);

            if (modelo.existeCancionPorAutor(Album, titulo)) {
                JOptionPane.showMessageDialog(null, "Esa canción ya existe en ese álbum. Cambia el título o el álbum.");
                vista.campoTituloCancion.setText("");
                return;
            }
            if (modelo.existeCancionEnMismoAlbum(titulo, Album)) {
                JOptionPane.showMessageDialog(null, "Esa canción ya existe en ese álbum. Cambia el título o el álbum.");
                vista.campoTituloCancion.setText("");
                return;
            }

            if (modelo.insertarCanciones(c)) {
                JOptionPane.showMessageDialog(null, "La canción se ha registrado correctamente");
                borrarCamposCancion();
                refrescarCanciones();
            } else {
                JOptionPane.showMessageDialog(null, "Canción no registrada");
            }
        }
    }

    void eliminarCancion() {
        int fila = vista.tablaCanciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona alguna canción en la tabla");
            return;
        }

        Integer idCancion = (Integer) vista.tablaCanciones.getValueAt(fila, 0);
        Object nombre = vista.tablaCanciones.getValueAt(fila, 1);

        int opcion = JOptionPane.showConfirmDialog(
                vista,
                "¿Seguro que quieres eliminar esta canción?\n(" + nombre + ", con ID " + idCancion + ")",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            modelo.eliminarCancion(idCancion);
            borrarCamposCancion();
            refrescarCanciones();
            JOptionPane.showMessageDialog(vista, "Canción eliminado.");
        }

    }

    private void borrarCamposCancion() {
        vista.campoTituloCancion.setText("");
        vista.campoALbum.setSelectedIndex(0);
        vista.campoAutor.setSelectedIndex(0);
        vista.campoGenero.setText("");
        vista.campoProd.setSelectedIndex(0);
        vista.campoNumParticipantes.setValue(0);
        vista.campoDuracion.setValue(0);
        vista.españolCheckBox.setSelected(false);
        vista.inglesCheckBox.setSelected(false);
        vista.dembowCheckBox.setSelected(false);
    }


    private void borrarCamposProductora() {
        vista.campoNombreProd.setText("");
        vista.comboLocalizacion.setSelectedIndex(0);
        vista.campoNumTrabajadores.setValue(0);
        vista.campoFechaFundacion.setText("");
        vista.campoPropietario.setText("");
    }

    private void borrarCamposAutor() {
        vista.campoNombreArtistico.setText("");
        vista.campoNombreReal.setText("");
        vista.campoEdad.setValue(16);
        vista.campoPais.setSelectedIndex(0);
        vista.campoFechaPrimeraPubli.setText("");
        vista.siRadioButton.isSelected();
    }

    private void borrarCamposAlbum() {
        vista.campoTituloAlbum.setText("");
        vista.comboAutores.setSelectedIndex(0);
        vista.campoNumCanciones.setValue(1);
        vista.campoDuracion.setValue(30);
        vista.campoFechaSalidaAlbum.setText("");
        vista.comboProductora.setSelectedIndex(0);
    }

    private void refrescarAutores() {
        try {
            vista.tablaAutor.setModel(construirTableModelAutores(modelo.consultarAutores()));
            vista.comboAutores.removeAllItems();
            vista.campoAutor.removeAllItems();
            for (int i = 0; i < vista.dtmAutores.getRowCount(); i++) {
                vista.campoAutor.addItem(vista.dtmAutores.getValueAt(i, 0) + " - " +
                        vista.dtmAutores.getValueAt(i, 1));
                vista.comboAutores.addItem(vista.dtmAutores.getValueAt(i, 0) + " - " +
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
        for (int column = 1; column <= columnCount; column++) {
            nombreColumnas.add(metaData.getColumnLabel(column));
        }

        //datis de la tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, columnCount, data);

        vista.dtmAutores.setDataVector(data, nombreColumnas);

        return vista.dtmAutores;

    }

    private void refrescarAlbum() {
        try {
            vista.tablaAlbum.setModel(construirTableModelAlbum(modelo.consultarAlbum()));
            vista.campoALbum.removeAllItems();
            for (int i = 0; i < vista.dtmAlbum.getRowCount(); i++) {
                vista.campoALbum.addItem(vista.dtmAlbum.getValueAt(i, 0) + " - " +
                        vista.dtmAlbum.getValueAt(i, 2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelAlbum(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        //nombre de las columnas
        Vector<String> nombreColumnas = new Vector<>();
        int numeroColumnas = metaData.getColumnCount();
        for (int column = 1; column <= numeroColumnas; column++) {
            nombreColumnas.add(metaData.getColumnLabel(column));
        }

        //datos de la tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, numeroColumnas, data);

        vista.dtmAlbum.setDataVector(data, nombreColumnas);

        return vista.dtmAlbum;
    }

    private void refrescarProductoras() {
        try {
            vista.tablaProductora.setModel(construirTableModelProductora(modelo.consultarProductora()));
            vista.campoProd.removeAllItems();
            for (int i = 0; i < vista.dtmProductora.getRowCount(); i++) {
                vista.campoProd.addItem(vista.dtmProductora.getValueAt(i, 0) + " - " +
                        vista.dtmProductora.getValueAt(i, 1));
                vista.comboProductora.addItem(vista.dtmProductora.getValueAt(i, 0) + " - " +
                        vista.dtmProductora.getValueAt(i, 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DefaultTableModel construirTableModelProductora(ResultSet rs) throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        //nombre de las columnas

        Vector<String> nombreColumnas = new Vector<>();
        int numeroColumnas = metaData.getColumnCount();
        for (int column = 1; column <= numeroColumnas; column++) {
            nombreColumnas.add(metaData.getColumnLabel(column));
        }

        //datos de la tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, numeroColumnas, data);

        vista.dtmProductora.setDataVector(data, nombreColumnas);

        return vista.dtmProductora;
    }

    private DefaultTableModel construirTableModelCanciones(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        //nombre de las columnas

        Vector<String> nombreColumnas = new Vector<>();
        int numeroColumnas = metaData.getColumnCount();
        for (int column = 1; column <= numeroColumnas; column++) {
            nombreColumnas.add(metaData.getColumnLabel(column));
        }

        //datos de la tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, numeroColumnas, data);

        vista.dtmCanciones.setDataVector(data, nombreColumnas);

        return vista.dtmCanciones;
    }

    private DefaultTableModel construirTableModelBusqueda(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        //nombre de las columnas

        Vector<String> nombreColumnas = new Vector<>();
        int numeroColumnas = metaData.getColumnCount();
        for (int column = 1; column <= numeroColumnas; column++) {
            nombreColumnas.add(metaData.getColumnLabel(column));
        }

        //datos de la tabla
        Vector<Vector<Object>> data = new Vector<>();
        setDataVector(rs, numeroColumnas, data);

        vista.vistaBuscar.dtmBuscar.setDataVector(data, nombreColumnas);

        return vista.vistaBuscar.dtmBuscar;
    }


    private void refrescarCanciones() {
        try {
            vista.tablaCanciones.setModel(construirTableModelCanciones(modelo.consultarCancion()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    void iniciar() {
        vista.tablaAutor.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel = vista.tablaAutor.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaAutor.getSelectionModel())) {
                        int row = vista.tablaAutor.getSelectedRow();
                        vista.campoNombreArtistico.setText(String.valueOf(vista.tablaAutor.getValueAt(row, 1)));
                        vista.campoNombreReal.setText(String.valueOf(vista.tablaAutor.getValueAt(row, 2)));
                        vista.campoEdad.setValue(vista.tablaAutor.getValueAt(row, 3));
                        vista.campoPais.setSelectedItem(String.valueOf(vista.tablaAutor.getValueAt(row, 4)));
                        vista.campoFechaPrimeraPubli.setDate(Date.valueOf(String.valueOf(vista.tablaAutor.getValueAt(row, 5))).toLocalDate());
                        boolean gira = Boolean.parseBoolean(String.valueOf(vista.tablaAutor.getValueAt(row, 6)));
                        vista.siRadioButton.setSelected(gira);
                        vista.noRadioButton.setSelected(!gira);
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaAutor.getSelectionModel())) {
                            borrarCamposAutor();
                        } else if (e.getSource().equals(vista.tablaProductora.getSelectionModel())) {
                            borrarCamposProductora();
                        } else if (e.getSource().equals(vista.tablaAlbum.getSelectionModel())) {
                            borrarCamposAlbum();
                        } else if (e.getSource().equals(vista.tablaCanciones.getSelectionModel())) {
                            borrarCamposCancion();
                        }
                    }
                }
            }
        });

        vista.tablaProductora.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel2 = vista.tablaProductora.getSelectionModel();
        cellSelectionModel2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaProductora.getSelectionModel())) {
                        int row = vista.tablaProductora.getSelectedRow();
                        vista.campoNombreProd.setText(String.valueOf(vista.tablaProductora.getValueAt(row, 1)));
                        vista.comboLocalizacion.setSelectedItem(String.valueOf(vista.tablaProductora.getValueAt(row, 2)));
                        vista.campoNumTrabajadores.setValue(vista.tablaProductora.getValueAt(row, 3));
                        vista.campoFechaFundacion.setDate((Date.valueOf(String.valueOf(vista.tablaProductora.getValueAt(row, 4)))).toLocalDate());
                        vista.campoPropietario.setText(String.valueOf(vista.tablaProductora.getValueAt(row, 5)));
                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaAutor.getSelectionModel())) {
                            borrarCamposAutor();
                        } else if (e.getSource().equals(vista.tablaProductora.getSelectionModel())) {
                            borrarCamposProductora();
                        } else if (e.getSource().equals(vista.tablaAlbum.getSelectionModel())) {
                            borrarCamposAlbum();
                        } else if (e.getSource().equals(vista.tablaCanciones.getSelectionModel())) {
                            borrarCamposCancion();
                        }
                    }
                }
            }
        });

        vista.tablaAlbum.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel3 = vista.tablaAlbum.getSelectionModel();
        cellSelectionModel3.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel3.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaAlbum.getSelectionModel())) {
                        int row = vista.tablaAlbum.getSelectedRow();
                        vista.campoTituloAlbum.setText(String.valueOf(vista.tablaAlbum.getValueAt(row, 2)));
                        seleccionarComboPorNombre(vista.comboAutores, String.valueOf(vista.tablaAlbum.getValueAt(row, 1)));
                        vista.campoNumCanciones.setValue(vista.tablaAlbum.getValueAt(row, 3));
                        vista.campoNumDuracion.setValue(vista.tablaAlbum.getValueAt(row, 4));
                        vista.campoFechaSalidaAlbum.setDate((Date.valueOf(String.valueOf(vista.tablaAlbum.getValueAt(row, 5)))).toLocalDate());
                        seleccionarComboPorNombre(vista.comboProductora, String.valueOf(vista.tablaAlbum.getValueAt(row, 6)));

                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaAutor.getSelectionModel())) {
                            borrarCamposAutor();
                        } else if (e.getSource().equals(vista.tablaProductora.getSelectionModel())) {
                            borrarCamposProductora();
                        } else if (e.getSource().equals(vista.tablaAlbum.getSelectionModel())) {
                            borrarCamposAlbum();
                        } else if (e.getSource().equals(vista.tablaCanciones.getSelectionModel())) {
                            borrarCamposCancion();
                        }
                    }
                }
            }
        });

        vista.tablaCanciones.setCellSelectionEnabled(true);
        ListSelectionModel cellSelectionModel4 = vista.tablaCanciones.getSelectionModel();
        cellSelectionModel4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel4.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()
                        && !((ListSelectionModel) e.getSource()).isSelectionEmpty()) {
                    if (e.getSource().equals(vista.tablaCanciones.getSelectionModel())) {
                        int row = vista.tablaCanciones.getSelectedRow();
                        vista.campoTituloCancion.setText(String.valueOf(vista.tablaCanciones.getValueAt(row, 1)));
                        seleccionarComboPorNombre(vista.campoALbum, String.valueOf(vista.tablaCanciones.getValueAt(row, 9)));
                        seleccionarComboPorNombre(vista.campoAutor, String.valueOf(vista.tablaCanciones.getValueAt(row, 2)));
                        vista.campoGenero.setText(String.valueOf(vista.tablaCanciones.getValueAt(row, 3)));
                        seleccionarComboPorNombre(vista.campoProd,  String.valueOf(vista.tablaCanciones.getValueAt(row, 4)));
                        vista.campoNumParticipantes.setValue(vista.tablaCanciones.getValueAt(row, 5));
                        vista.campoDuracion.setValue(vista.tablaCanciones.getValueAt(row, 6));
                        vista.campoValoracion.setValue(Integer.parseInt((String.valueOf(vista.tablaCanciones.getValueAt(row, 8)))));
                        String idiomas = String.valueOf(vista.tablaCanciones.getValueAt(row, 7)).toLowerCase();
                        for (String t : idiomas.split(",")) {
                            t = t.trim();

                            if (t.equals("español") || t.equals("español")) vista.españolCheckBox.setSelected(true);
                            if (t.equals("ingles") || t.equals("inglés")) vista.inglesCheckBox.setSelected(true);
                            if (t.equals("dembow")) vista.dembowCheckBox.setSelected(true);

                        }

                    } else if (e.getValueIsAdjusting()
                            && ((ListSelectionModel) e.getSource()).isSelectionEmpty() && !refrescar) {
                        if (e.getSource().equals(vista.tablaAutor.getSelectionModel())) {
                            borrarCamposAutor();
                        } else if (e.getSource().equals(vista.tablaProductora.getSelectionModel())) {
                            borrarCamposProductora();
                        } else if (e.getSource().equals(vista.tablaAlbum.getSelectionModel())) {
                            borrarCamposAlbum();
                        } else if (e.getSource().equals(vista.tablaCanciones.getSelectionModel())) {
                            borrarCamposCancion();
                        }
                    }
                }
            }
        });
    }
    private void seleccionarComboPorNombre(javax.swing.JComboBox combo, String nombre) {
        String n = nombre.trim().toLowerCase();

        for (int i = 0; i < combo.getItemCount(); i++) {
            String item = String.valueOf(combo.getItemAt(i)).trim().toLowerCase();
            if (item.equals(n) || item.endsWith(" - " + n)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private void actualizarAutor(){
        int row = vista.tablaAutor.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un autor en la tabla para modificar.");
            return;
        }
        if (!Util.comprobarCampoVacio(vista.campoNombreArtistico)) {
            Util.lanzaAlertaVacio(vista.campoNombreArtistico);
        } else if (!Util.comprobarCampoVacio(vista.campoNombreReal)) {
            Util.lanzaAlertaVacio(vista.campoNombreReal);
        } else if (!Util.comprobarSpinner(vista.campoEdad)) {
            JOptionPane.showMessageDialog(null, "El campo edad no puede ser menor que 13");
        } else if (Util.comprobarCombobox(vista.campoPais)) {
            Util.lanzaAlertaCombo(vista.campoPais);
        } else if (Util.campoVacioCalendario(vista.campoFechaPrimeraPubli)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaPrimeraPubli);
        } else {
            int idAutor = Integer.parseInt(String.valueOf(vista.tablaAutor.getValueAt(row, 0)));

            boolean gira = vista.siRadioButton.isSelected();
            String nombreArtistico = vista.campoNombreArtistico.getText();
            String nombreReal = vista.campoNombreReal.getText();
            int edad = (int) vista.campoEdad.getValue();
            String pais = vista.campoPais.getSelectedItem().toString();
            LocalDate fecha = vista.campoFechaPrimeraPubli.getDate();
            Autor a = new Autor(nombreArtistico, nombreReal, edad, pais, fecha, gira);
            a.setIdAutor(idAutor);

            if (modelo.existeAutorExceptoId( nombreArtistico,  idAutor)){
                JOptionPane.showMessageDialog(null, "Ya existe otro autor con este nombre ");
                vista.campoNombreArtistico.setText("");
                return;
            }

            if (modelo.modificarAutor(a)) {
                JOptionPane.showMessageDialog(null, "El autor ha sido actualizado correctamente");
                borrarCamposAutor();
                refrescarAutores();
            } else {
                JOptionPane.showMessageDialog(null, "El autor no se ha modificado");
            }
        }

    }
    private void actualizarProductora(){
        int row = vista.tablaProductora.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una productora en la tabla para modificar.");
            return;
        }
        if (!Util.comprobarCampoVacio(vista.campoNombreProd)) {
            Util.lanzaAlertaVacio(vista.campoNombreProd);
        } else if (Util.comprobarCombobox(vista.comboLocalizacion)) {
            Util.lanzaAlertaCombo(vista.comboLocalizacion);
        } else if (!Util.comprobarSpinner(vista.campoNumTrabajadores)) {
            JOptionPane.showMessageDialog(null, "El campo Trabajadores no puede ser menor que 0");
        } else if (Util.campoVacioCalendario(vista.campoFechaFundacion)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaFundacion);
        } else if (!Util.comprobarCampoVacio(vista.campoPropietario)) {
            Util.lanzaAlertaVacio(vista.campoPropietario);
        } else {

            int idProductora = Integer.parseInt(String.valueOf(vista.tablaProductora.getValueAt(row, 0)));

            String nombre = vista.campoNombreProd.getText();
            String localizacion = vista.comboLocalizacion.getSelectedItem().toString();
            int numeroTrabajadores = (int) vista.campoNumTrabajadores.getValue();
            LocalDate fechaFundacion = vista.campoFechaFundacion.getDate();
            String propietario = vista.campoPropietario.getText();

            Productora prod = new Productora(nombre, localizacion, numeroTrabajadores, fechaFundacion, propietario);
            prod.setIdPord(idProductora);

            if (modelo.existeProductoraExceptoId(nombre,idProductora)) {
                JOptionPane.showMessageDialog(null, "Esta Productora ya existe, cambia el nombre");
                vista.campoNombreProd.setText("");
                return;
            }

            if (modelo.modificarProductora(prod)) {
                JOptionPane.showMessageDialog(null, "La Productora ha sido actualizada correctamente");
                borrarCamposProductora();
                refrescarProductoras();

            } else {
                JOptionPane.showMessageDialog(null, "Productora no Actualizada ");
            }

        }
    }

    private void actualizarAlbum(){
        int row = vista.tablaAlbum.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona un album en la tabla para modificar.");
            return;
        }
        if (!Util.comprobarCampoVacio(vista.campoTituloAlbum)) {
            Util.lanzaAlertaVacio(vista.campoTituloAlbum);
        } else if (!Util.comprobarSpinner(vista.campoNumCanciones)) {
            JOptionPane.showMessageDialog(null, "El campo Numero de canciones no puede ser menor que 0");
        } else if (!Util.comprobarSpinner(vista.campoNumDuracion)) {
            JOptionPane.showMessageDialog(null, "El campo Duracion en minutos no puede ser menor que 0");
        } else if (Util.campoVacioCalendario(vista.campoFechaSalidaAlbum)) {
            Util.lanzaAlertaVacioCalendar(vista.campoFechaSalidaAlbum);
        } else if (Util.comprobarCombobox(vista.comboProductora)) {
            Util.lanzaAlertaCombo(vista.comboProductora);
        } else {

            int idAlbum = Integer.parseInt(String.valueOf(vista.tablaAlbum.getValueAt(row, 0)));

            String titulo = vista.campoTituloAlbum.getText();
            String itemAutor = vista.comboAutores.getSelectedItem().toString();
            int autor = Integer.parseInt(itemAutor.split(" - ")[0].trim());
            int numCanciones = ((Number) vista.campoNumCanciones.getValue()).intValue();
            int duracionCanciones = ((Number) vista.campoNumDuracion.getValue()).intValue();
            LocalDate fechaSalida = vista.campoFechaSalidaAlbum.getDate();
            String itemProd = vista.comboProductora.getSelectedItem().toString();
            int productora = Integer.parseInt(itemProd.split(" - ")[0].trim());

            Album au = new Album(autor, titulo, numCanciones, duracionCanciones, fechaSalida, productora);
            au.setIdAlbum(idAlbum);

            if (modelo.existeAlbumAutorExceptoId(autor,titulo,idAlbum)) {
                JOptionPane.showMessageDialog(null, "Este Album ya existe, cambia el nombre");
                vista.campoNombreProd.setText("");
                return;
            }

            if (modelo.modificarAlbum(au)) {
                JOptionPane.showMessageDialog(null, "El album ha sido actualizado correctamente");
                borrarCamposAlbum();
                refrescarAlbum();

            } else {
                JOptionPane.showMessageDialog(null, "Álbum no Actualizado ");
            }
        }
    }

    private void actualizarCancion(){
        int row = vista.tablaCanciones.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(null, "Selecciona una cancion en la tabla para modificar.");
            return;
        }
        if (!Util.comprobarCampoVacio(vista.campoTituloCancion)) {
            Util.lanzaAlertaVacio(vista.campoTituloCancion);
        } else if (!Util.comprobarCampoVacio(vista.campoGenero)) {
            Util.lanzaAlertaVacio(vista.campoGenero);
        } else if (!Util.comprobarSpinner(vista.campoNumParticipantes)) {
            JOptionPane.showMessageDialog(null, "El Nº de participantes no puede ser menor que 0");
        } else if (!Util.comprobarSpinner(vista.campoDuracion)) {
            JOptionPane.showMessageDialog(null, "La duración no puede ser menor o igual que 0");
        } else if (!vista.españolCheckBox.isSelected() && !vista.inglesCheckBox.isSelected() && !vista.dembowCheckBox.isSelected()) {
            JOptionPane.showMessageDialog(null, "Selecciona al menos un idioma");
        } else {
            int idCancion = Integer.parseInt(String.valueOf(vista.tablaCanciones.getValueAt(row, 0)));

            String titulo = vista.campoTituloCancion.getText();
            String genero = vista.campoGenero.getText();

            String itemAlbum = vista.campoALbum.getSelectedItem().toString();
            int Album = Integer.parseInt(itemAlbum.split(" - ")[0]);

            String itemAutor = vista.campoAutor.getSelectedItem().toString();
            int Autor = Integer.parseInt(itemAutor.split(" - ")[0]);

            String itemProd = vista.campoProd.getSelectedItem().toString();
            int Productora = Integer.parseInt(itemProd.split(" - ")[0]);

            int participantes = ((Number) vista.campoNumParticipantes.getValue()).intValue();
            float duracion = ((Number) vista.campoDuracion.getValue()).floatValue();
            int valoracion = vista.campoValoracion.getValue();

            StringBuilder idioma = new StringBuilder();
            if (vista.españolCheckBox.isSelected()) idioma.append("Español,");
            if (vista.inglesCheckBox.isSelected()) idioma.append("Ingles,");
            if (vista.dembowCheckBox.isSelected()) idioma.append("Dembow,");
            idioma.deleteCharAt(idioma.length() - 1);

            Canciones c = new Canciones(titulo, Album, Autor, genero, Productora,
                    participantes, duracion, idioma.toString(), valoracion);

            c.setIdCancion(idCancion);

            if (modelo.existeCancionId(titulo,idCancion)) {
                JOptionPane.showMessageDialog(null, "Esta cancion ya existe en ese album cambia el nombre o el album");
                vista.campoNombreProd.setText("");
                return;
            }



            if (modelo.modificarCancion(c)) {
                JOptionPane.showMessageDialog(null, "La cancion ha sido actualizado correctamente");
                borrarCamposCancion();
                refrescarCanciones();

            } else {
                JOptionPane.showMessageDialog(null, "Canción no Actualizada ");
            }
        }
    }

    void buscar() throws SQLException {


        if(Util.comprobarCombobox(vista.vistaBuscar.comboSeleccionarTabla)){
            Util.lanzaAlertaCombo(vista.vistaBuscar.comboSeleccionarTabla);
        }else if(!Util.comprobarCampoVacio(vista.vistaBuscar.campoLgtb)){
            Util.lanzaAlertaVacio(vista.vistaBuscar.campoLgtb);
        }else{

            String valor = String.valueOf(vista.vistaBuscar.comboSeleccionarTabla.getSelectedItem());
            String titulo = vista.vistaBuscar.campoLgtb.getText().trim();

            ResultSet rs = null;

            switch (valor){
                case "Canciones":{
                    vista.vistaBuscar.etiquetaCambiante.setText("Título de la Canción");
                    rs = modelo.buscarNombreCancion(titulo);
                    break;
                }
                case "Productoras":{
                    vista.vistaBuscar.etiquetaCambiante.setText("Nombre de la Productora");
                    rs = modelo.buscarNombreProductora(titulo);
                    break;
                }
                case "Autores":{
                    vista.vistaBuscar.etiquetaCambiante.setText("Nombre Artístico");
                    rs = modelo.buscarNombreAutor(titulo);
                    break;
                }
                case "Álbumes":{
                    vista.vistaBuscar.etiquetaCambiante.setText("Título del Álbum");
                    rs = modelo.buscarNombreAlbum(titulo);
                    break;
                }


            }

            DefaultTableModel model = construirTableModelBusqueda(rs);
            vista.vistaBuscar.tablaResultado.setModel(model);

        }
    }

    void actualizarCombo(){
        Object sel = vista.vistaBuscar.comboSeleccionarTabla.getSelectedItem();
        if (sel == null) return;

        String valor = sel.toString();

        switch (valor){
            case "Canciones":
                vista.vistaBuscar.etiquetaCambiante.setText("Titulo de la Canción");
                break;
            case "Productoras":
                vista.vistaBuscar.etiquetaCambiante.setText("Nombre de la productora");
                break;
            case "Autores":
                vista.vistaBuscar.etiquetaCambiante.setText("Nombre Artístico");
                break;
            case "Álbumes":
                vista.vistaBuscar.etiquetaCambiante.setText("Título del album");
                break;
        }

    }


}
