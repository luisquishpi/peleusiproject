package ec.peleusi.views.windows;



import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;

import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;

import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.utils.Formatos;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class SeteoCrudFrm extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	@SuppressWarnings("rawtypes")
	private JComboBox cmdIva;

	
	public SeteoCrudFrm() {
		crearControles();
		cargarComboTarifaIva();
			
	}
	
	private void crearControles()
	{
		setTitle("Configuración de parametros para el sistema");
		setBounds(100, 100, 624, 444);
		
		JPanel pnlCabecera = new JPanel();
		pnlCabecera.setLayout(null);
		pnlCabecera.setPreferredSize(new Dimension(200, 70));
		pnlCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(pnlCabecera, BorderLayout.NORTH);
		
		JButton btNuevo = new JButton("Nuevo");
		btNuevo.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btNuevo.setBounds(10, 11, 130, 39);
		pnlCabecera.add(btNuevo);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		pnlCabecera.add(btnGuardar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		pnlCabecera.add(btnEliminar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		pnlCabecera.add(btnCancelar);
		
		JPanel pnlCuerpo = new JPanel();
		getContentPane().add(pnlCuerpo, BorderLayout.CENTER);
		pnlCuerpo.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBounds(21, 11, 577, 310);
		pnlCuerpo.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Generales", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblTarifaIva = new JLabel("Porcentaje Iva:");
		lblTarifaIva.setBounds(10, 28, 111, 14);
		panel.add(lblTarifaIva);
		
		JLabel lblNmeroDcimales = new JLabel("Número décimales:");
		lblNmeroDcimales.setBounds(10, 65, 111, 14);
		panel.add(lblNmeroDcimales);
		
		JFormattedTextField txtNumeroDecimales = new JFormattedTextField();
		txtNumeroDecimales.setBounds(159, 62, 124, 20);
		txtNumeroDecimales.setFormatterFactory(new Formatos().getDecimalFormat());		
		
		panel.add(txtNumeroDecimales);
		
		JLabel lblIdentificacinDcimales = new JLabel("Identificación décimales:");
		lblIdentificacinDcimales.setBounds(10, 99, 135, 14);
		panel.add(lblIdentificacinDcimales);
		
		JComboBox cmbIdentifiacionDecimal = new JComboBox();
		cmbIdentifiacionDecimal.setModel(new DefaultComboBoxModel(new String[] {".", ","}));
		cmbIdentifiacionDecimal.setBounds(159, 96, 124, 20);
		panel.add(cmbIdentifiacionDecimal);
		
		JLabel lblSignoMoneda = new JLabel("Signo de moneda");
		lblSignoMoneda.setBounds(10, 137, 135, 14);
		panel.add(lblSignoMoneda);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"$", "e"}));
		comboBox.setBounds(159, 134, 124, 20);
		panel.add(comboBox);
		
		cmdIva = new JComboBox<TarifaIva>();
		cmdIva.setBounds(159, 28, 124, 20);
		panel.add(cmdIva);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Facturación", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cliente por defecto para facturaci\u00F3n", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_3.setBounds(10, 21, 552, 64);
		panel_1.add(panel_3);
		
		textField = new JTextField();
		textField.setText("");
		textField.setColumns(10);
		textField.setBounds(10, 33, 153, 20);
		panel_3.add(textField);
		
		JLabel label = new JLabel("Contribuyente:");
		label.setBounds(199, 18, 94, 14);
		panel_3.add(label);
		
		JLabel label_1 = new JLabel("Ruc/CI:");
		label_1.setBounds(10, 18, 70, 14);
		panel_3.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setText("");
		textField_1.setColumns(10);
		textField_1.setBounds(199, 33, 343, 20);
		panel_3.add(textField_1);
		
		JButton button_4 = new JButton("");
		button_4.setVerticalAlignment(SwingConstants.TOP);
		button_4.setBounds(165, 31, 24, 26);
		panel_3.add(button_4);
		
		JLabel lblNmeroItemsFacturacin = new JLabel("Nombre Servicio Adicional");
		lblNmeroItemsFacturacin.setBounds(20, 96, 139, 14);
		panel_1.add(lblNmeroItemsFacturacin);
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(195, 96, 201, 20);
		panel_1.add(formattedTextField);
		
		JLabel lblPorcentajeServicioAdicional = new JLabel("% servicio adicional facturación");
		lblPorcentajeServicioAdicional.setVerticalAlignment(SwingConstants.TOP);
		lblPorcentajeServicioAdicional.setBounds(20, 133, 168, 14);
		panel_1.add(lblPorcentajeServicioAdicional);
		
		JLabel lblNewLabel_1 = new JLabel(" para ventas");
		lblNewLabel_1.setBounds(20, 109, 122, 14);
		panel_1.add(lblNewLabel_1);
		
		JFormattedTextField formattedTextField_1 = new JFormattedTextField();
		formattedTextField_1.setBounds(195, 130, 201, 20);
		panel_1.add(formattedTextField_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Contabilidad", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblMtodoDeRegistro = new JLabel("Método de registro de inventario");
		lblMtodoDeRegistro.setBounds(29, 43, 167, 14);
		panel_2.add(lblMtodoDeRegistro);
		
		JComboBox cmbTipoInventario = new JComboBox();
		cmbTipoInventario.setModel(new DefaultComboBoxModel(new String[] {"FIFO", "LIFO", "PROMEDIO"}));
		cmbTipoInventario.setBounds(206, 40, 167, 20);
		panel_2.add(cmbTipoInventario);

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarComboTarifaIva() {
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		List<TarifaIva> listaTarifaIva;
		listaTarifaIva = tarifaIvaController.tarifaIvaList();
		cmdIva.setModel(new DefaultComboBoxModel(listaTarifaIva.toArray()));

	}

}
