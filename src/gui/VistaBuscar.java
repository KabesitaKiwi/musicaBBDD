package gui;

import javax.swing.*;
import java.awt.*;

public class VistaBuscar extends JDialog{
    private JPanel panel1;
    public JComboBox comboSeleccionarTabla;
    public JTextArea Resultado;
    public JLabel etiquetaCambiante;
    public JTextField campoLgtb;
    private Frame owner;

    public VistaBuscar(Frame owner){
        super(owner, "BUSCAR", true);
        this.owner=owner;
        initDialog();
    }

    private void initDialog() {
        this.setContentPane(panel1);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(new Dimension(this.getWidth()+200,this.getHeight()));

        this.setLocationRelativeTo(owner);
    }
}
