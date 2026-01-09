package gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.sun.jdi.JDIPermission;
import gui.base.enums.LocalizacionProductora;
import gui.base.enums.PaisAutor;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;

public class Vista extends JFrame{

    private JPanel panel1;
    private final static String TITULO_FRAME="Canciones info";
    public JTabbedPane tabbedPane1;

    //canciones
    public JPanel PanelCanciones;
    public JTextField campoTituloCancion;
    public JComboBox campoALbum;
    public JComboBox campoAutor;
    public JComboBox campoProd;
    public JTextField campoGenero;
    public JSpinner campoNumParticipantes;
    public JSpinner campoDuracion;
    public JSlider campoValoracion;
    public JCheckBox dembowCheckBox;
    public JCheckBox inglesCheckBox;
    public JCheckBox españolCheckBox;
    public JButton añadirButton;
    public JButton modificarButton;
    public JButton eliminarButton;
    public JTable tablaCanciones;

    //autores
    public JPanel JpanelAutor;
    public JTextField campoNombreArtistico;
    public JTextField campoNombreReal;
    public JSpinner campoEdad;
    public JComboBox campoPais;
    public JRadioButton siRadioButton;
    public JRadioButton noRadioButton;
    public JButton botonAñadirAutor;
    public JButton botonModificarAutor;
    public JButton botonEliminarAutor;
    public JTable tablaAutor;
    public DatePicker campoFechaPrimeraPubli;

    //album
    public JPanel JpanelAlbum;
    public JTextField campoTituloAlbum;
    public JSpinner campoNumCanciones;
    public JSpinner campoNumDuracion;
    public JButton botonAñadirAlbum;
    public JButton botonModificarAlbum;
    public JButton botonEliminarAlbum;
    public JTable tablaAlbum;
    public DatePicker campoFechaSalidaAlbum;

    //productora
    public JPanel JpanelProductora;
    public JTextField campoNombreProd;
    public JComboBox comboLocalizacion;
    public JSpinner campoNumTrabajadores;
    public JTextField campoPropietario;
    public JButton botonAñadirProductora;
    public JButton botonModificarProd;
    public JButton botonEliminarProd;
    public JTable tablaProductora;
    public JScrollPane tabla;
    public DatePicker campoFechaFundacion;
    public JComboBox comboAutores;
    public JComboBox comboProductora;

    //busqueda
    private JLabel etiquetasEstado;

    //default table model
    DefaultTableModel dtmCanciones;
    DefaultTableModel dtmAutores;
    DefaultTableModel dtmAlbum;
    DefaultTableModel dtmProductora;

    //menuBar
    JMenuItem itemOpciones;
    JMenuItem itemDesconectar;
    JMenuItem itemSalir;
    JMenuItem itemConectar;

    //cuadro dialogo
    OptionDialog optionDialog;
    JDialog adminPasswordDialog;
    JButton botonValidar;
    JPasswordField campoContrasenya;

    public Vista(){
        super(TITULO_FRAME);
        initFrame();
        initComponents();
    }

    public void initFrame() {
        this.setContentPane(panel1);

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.pack();

        this.setSize(new Dimension(this.getWidth()+100,this.getHeight()));
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        //crear cuadro del dialogo
        optionDialog = new OptionDialog(this);
        //llama al menu
        setMenu();
        //lamo cuadro de dialogo admin
        setAdminDialog();
        //cargoEnumerados
        setEnumComboBox();
        //cargo tableModels
        setTableModels();
    }

    private void setMenu() {
        JMenuBar mbBar = new JMenuBar();
        JMenu menu = new JMenu("Archivo");
        itemOpciones = new JMenuItem("Opciones");
        itemOpciones.setActionCommand("Opciones");
        itemDesconectar = new JMenuItem("Desconectar");
        itemDesconectar.setActionCommand("Desconectar");
        itemSalir = new JMenuItem("Salir");
        itemSalir.setActionCommand("Salir");
        itemConectar = new JMenuItem("Conectar");
        itemConectar.setActionCommand("Conectar");
        menu.add(itemOpciones);
        menu.add(itemDesconectar);
        menu.add(itemSalir);
        mbBar.add(menu);
        mbBar.add(Box.createHorizontalGlue());
        this.setJMenuBar(mbBar);
    }

    private void setAdminDialog(){
        botonValidar = new JButton("Validar");
        botonValidar.setActionCommand("abrirOpciones");
        campoContrasenya = new JPasswordField();
        //dimension Cuadro de texto
        campoContrasenya.setPreferredSize(new Dimension(100, 26));
        Object[] options = new Object[] {campoContrasenya, botonValidar};
        JOptionPane jop = new JOptionPane("introduce la contraseña", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, options);

        adminPasswordDialog = new JDialog(this, "Opciones", true);
        adminPasswordDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        adminPasswordDialog.setContentPane(jop);
        adminPasswordDialog.pack();
        adminPasswordDialog.setLocationRelativeTo(this);
    }

    private void setTableModels(){
        //tablas de autores, de canciones, de albumes y de productoras
        this.dtmCanciones = new DefaultTableModel();
        this.tablaCanciones.setModel(dtmCanciones);

        this.dtmAlbum = new DefaultTableModel();
        this.tablaAlbum.setModel(dtmAlbum);

        this.dtmAutores = new DefaultTableModel();
        this.tablaAutor.setModel(dtmAutores);

        this.dtmProductora = new DefaultTableModel();
        this.tablaProductora.setModel(dtmProductora);
    }

    private void setEnumComboBox(){
        //recorrer los enumerados y los cargo en el comboBox correspondiente
        //.values cogemos valores del enumerado
        //.getValor los añadimos al combo

        for(PaisAutor constant: PaisAutor.values()){
            campoPais.addItem(constant.getValor());
        }
        // se coloca en una posicion que no tenga valor
        campoPais.setSelectedIndex(-1);

        for (LocalizacionProductora cosntant: LocalizacionProductora.values()){
            comboLocalizacion.addItem(cosntant.getValor());
        }
        comboLocalizacion.setSelectedIndex(-1);
    }
    public void initComponents(){
        //valor predeterminado en diferentes spiner

        SpinnerNumberModel duracionCancion = new SpinnerNumberModel(3.5, 0.0, 600.0, 0.5);
        campoDuracion.setModel(duracionCancion);


        SpinnerNumberModel duracionAlbum = new SpinnerNumberModel(15, 0.0, 600.0, 1);
        campoNumDuracion.setModel(duracionAlbum);

        SpinnerNumberModel edad = new SpinnerNumberModel(16, 14, 100, 1);
        campoEdad.setModel(edad);

        SpinnerNumberModel participantes = new SpinnerNumberModel(1, 1, 10, 1);
        campoNumParticipantes.setModel(participantes);
        ((JSpinner.DefaultEditor) campoNumParticipantes.getEditor()).getTextField().setEditable(false);

        SpinnerNumberModel numeroCanciones = new SpinnerNumberModel(1, 1, 40, 1);
        campoNumCanciones.setModel(numeroCanciones);

        SpinnerNumberModel numeroTrabajadores = new SpinnerNumberModel(1, 1, 100, 1);
        campoNumTrabajadores.setModel(numeroTrabajadores);

        //valores de slider
        campoValoracion.setMinimum(0);     // Valor mínimo
        campoValoracion.setMaximum(10);    // Valor máximo
        campoValoracion.setValue(5);       // Valor inicialç
        campoValoracion.setMajorTickSpacing(1);  // Separación grande entre marcas
        campoValoracion.setPaintLabels(true);    // Mostrar los números debajo

        comboLocalizacion.setSelectedIndex(0);
        campoPais.setSelectedIndex(0);
    }


}
