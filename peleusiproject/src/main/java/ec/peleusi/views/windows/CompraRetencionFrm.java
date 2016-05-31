package ec.peleusi.views.windows;




import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import ec.peleusi.utils.TipoRetencionEnum;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class CompraRetencionFrm extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCodigoRetencion;
	private JComboBox<TipoRetencionEnum> cmbTipoRetencion;
	
	private void cargarComboTipoRetencion() {
		cmbTipoRetencion.setModel(new DefaultComboBoxModel<TipoRetencionEnum>(TipoRetencionEnum.values()));

	}

	
	public CompraRetencionFrm()
	{
		crearControles();
		crearEventos();
		cargarComboTipoRetencion();
	}
	
	
	public void crearEventos()
	{}
	@SuppressWarnings("rawtypes")
	public void crearControles(){
		setBounds(100, 100, 465, 347);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton button = new JButton("Nuevo");
		button.setBounds(10, 11, 130, 39);
		panel.add(button);
		
		JButton button_1 = new JButton("Guardar");
		button_1.setBounds(150, 11, 130, 39);
		panel.add(button_1);
		
		JButton button_2 = new JButton("Cancelar");
		button_2.setBounds(290, 11, 130, 39);
		panel.add(button_2);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Tipo*");
		label.setBounds(23, 58, 77, 14);
		panel_1.add(label);
		
		cmbTipoRetencion = new JComboBox<TipoRetencionEnum>();
		cmbTipoRetencion.setBounds(23, 72, 77, 20);
		panel_1.add(cmbTipoRetencion);
		
		JLabel lblCdigo = new JLabel("Código*:");
		lblCdigo.setBounds(123, 58, 61, 14);
		panel_1.add(lblCdigo);
		
		cmbCodigoRetencion = new JComboBox();
		cmbCodigoRetencion.setBounds(120, 72, 199, 20);
		panel_1.add(cmbCodigoRetencion);
		
		JLabel lblPorcentaje = new JLabel("Porcentaje");
		lblPorcentaje.setBounds(23, 153, 66, 14);
		panel_1.add(lblPorcentaje);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setToolTipText("");
		formattedTextField.setText("0");
		formattedTextField.setBounds(23, 170, 77, 20);
		panel_1.add(formattedTextField);
		
		JLabel label_2 = new JLabel("Establecimiento");
		label_2.setBounds(23, 11, 97, 14);
		panel_1.add(label_2);
		
		textField = new JTextField();
		textField.setText("");
		textField.setColumns(10);
		textField.setBounds(23, 25, 77, 20);
		panel_1.add(textField);
		
		JLabel label_3 = new JLabel("Punto Emisión");
		label_3.setBounds(117, 11, 97, 14);
		panel_1.add(label_3);
		
		textField_1 = new JTextField();
		textField_1.setText("");
		textField_1.setColumns(10);
		textField_1.setBounds(113, 25, 81, 20);
		panel_1.add(textField_1);
		
		JLabel label_4 = new JLabel("Secuencial");
		label_4.setBounds(210, 11, 89, 14);
		panel_1.add(label_4);
		
		textField_2 = new JTextField();
		textField_2.setText("");
		textField_2.setColumns(10);
		textField_2.setBounds(210, 25, 109, 20);
		panel_1.add(textField_2);
		
		JLabel label_5 = new JLabel("Fecha Registro");
		label_5.setBounds(192, 103, 97, 14);
		panel_1.add(label_5);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(192, 116, 127, 20);
		panel_1.add(dateChooser);
		
		JLabel lblAutorizacin = new JLabel("Autorización");
		lblAutorizacin.setBounds(23, 103, 77, 14);
		panel_1.add(lblAutorizacin);
		
		textField_3 = new JTextField();
		textField_3.setText("");
		textField_3.setColumns(10);
		textField_3.setBounds(23, 116, 159, 20);
		panel_1.add(textField_3);
		
		JLabel lblBaseImponible = new JLabel("Base Imponible");
		lblBaseImponible.setBounds(123, 153, 102, 14);
		panel_1.add(lblBaseImponible);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setToolTipText("");
		formattedTextField_1.setText("0");
		formattedTextField_1.setBounds(125, 170, 75, 20);
		panel_1.add(formattedTextField_1);
		
		JLabel lblValorRetenido = new JLabel("Valor Retenido");
		lblValorRetenido.setBounds(217, 153, 102, 14);
		panel_1.add(lblValorRetenido);
		
		JFormattedTextField formattedTextField_2 = new JFormattedTextField();
		formattedTextField_2.setToolTipText("");
		formattedTextField_2.setText("0");
		formattedTextField_2.setBounds(219, 170, 100, 20);
		panel_1.add(formattedTextField_2);

	}
}
