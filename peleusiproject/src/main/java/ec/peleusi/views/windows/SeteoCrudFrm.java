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

import ec.peleusi.controllers.SeteoController;
import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.utils.Formatos;
import ec.peleusi.utils.IdentificacionDecimalEnum;
import ec.peleusi.utils.SignoMonedaEnum;
import ec.peleusi.utils.TipoInventarioEnum;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;

public class SeteoCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtIdentificacion;
	private JTextField txtRazonSocial;
	@SuppressWarnings("rawtypes")
	private JComboBox cmdIva;
	private JComboBox<IdentificacionDecimalEnum> cmbIdentifiacionDecimal;
	private JComboBox<SignoMonedaEnum> cmbSignoMoneda;
	private JComboBox<TipoInventarioEnum> cmbTipoInventario;
	private JButton btnCliente;
	ClienteListModal clienteListModal = new ClienteListModal();
	Cliente cliente;
	private JFormattedTextField txtPorcentajeServicioAdicional;
	private JFormattedTextField txtNumeroDecimales;
	private JButton btnCancelar;
	private JButton btnGuardar;
	private JCheckBox chkConServicioAdicional;
	private JPanel pnlServicioAdicional;

	private JTextField txtNombreCampoServicioAdicional;
	Seteo seteo = new Seteo();
	SeteoController seteoController = new SeteoController();
	String error = "";

	public SeteoCrudFrm() {

		crearControles();
		crearEventos();
		cargarComboTarifaIva();
		cargarIdentificacionDecimalEnum();
		cargarSignoMonedaEnum();
		cargarTipoInventarioEnum();
		txtNumeroDecimales.setText("0");
		txtPorcentajeServicioAdicional.setText("0.00");

		txtNombreCampoServicioAdicional = new JTextField();
		txtNombreCampoServicioAdicional.setBounds(104, 34, 438, 20);
		pnlServicioAdicional.add(txtNombreCampoServicioAdicional);
		txtNombreCampoServicioAdicional.setColumns(10);

		cargarDatosSeteos();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarComboTarifaIva() {
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		List<TarifaIva> listaTarifaIva;
		listaTarifaIva = tarifaIvaController.TarifaIvaList();
		cmdIva.setModel(new DefaultComboBoxModel(listaTarifaIva.toArray()));
	}

	private void cargarDatosSeteos() {
		seteo = new Seteo();
		List<Seteo> listaSeteo;
		listaSeteo = seteoController.seteoList();

		if (listaSeteo.size() != 0) {
			seteo = listaSeteo.get(0);

			txtIdentificacion.setText(seteo.getCliente().getIdentificacion());
			txtRazonSocial.setText(seteo.getCliente().getRazonSocial());
			cmdIva.getModel().setSelectedItem(seteo.getTarifaIva());
			txtNumeroDecimales.setText(seteo.getNumeroDecimales().toString());
			cmbIdentifiacionDecimal.setSelectedItem(seteo.getIdentificacionDecimal());
			cmbSignoMoneda.setSelectedItem(seteo.getSignoMoneda());
			cliente = seteo.getCliente();
			if (seteo.getConServicioAdicional() == true) {
				pnlServicioAdicional.setVisible(true);
				chkConServicioAdicional.setSelected(true);
				txtNombreCampoServicioAdicional.setText(seteo.getNombrePercentajeServicioAdicional());
				txtPorcentajeServicioAdicional.setText(seteo.getPorcentajeServicioAdicional().toString());
			} else {
				chkConServicioAdicional.setSelected(false);
				pnlServicioAdicional.setVisible(false);

			}
			cmbTipoInventario.setSelectedItem(seteo.getTipoInventario());
		} else
			seteo = null;

	}

	private void cargarIdentificacionDecimalEnum() {
		cmbIdentifiacionDecimal
				.setModel(new DefaultComboBoxModel<IdentificacionDecimalEnum>(IdentificacionDecimalEnum.values()));
	}

	private void cargarSignoMonedaEnum() {
		cmbSignoMoneda.setModel(new DefaultComboBoxModel<SignoMonedaEnum>(SignoMonedaEnum.values()));
	}

	private void cargarTipoInventarioEnum() {
		cmbTipoInventario.setModel(new DefaultComboBoxModel<TipoInventarioEnum>(TipoInventarioEnum.values()));
	}

	private void crearEventos() {
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llamarVentanaPersona();

			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					TarifaIva tarifaIva = (TarifaIva) cmdIva.getSelectedItem();
					IdentificacionDecimalEnum identificacionDecimal = (IdentificacionDecimalEnum) cmbIdentifiacionDecimal
							.getSelectedItem();
					SignoMonedaEnum signoMoneda = (SignoMonedaEnum) cmbSignoMoneda.getSelectedItem();
					TipoInventarioEnum tipoInventario = (TipoInventarioEnum) cmbTipoInventario.getSelectedItem();
					if (seteo == null) {
						Seteo seteo = new Seteo(tarifaIva, Integer.parseInt(txtNumeroDecimales.getText()),
								identificacionDecimal, signoMoneda, cliente, txtNombreCampoServicioAdicional.getText(),
								Double.parseDouble(txtPorcentajeServicioAdicional.getText()), tipoInventario,
								chkConServicioAdicional.isSelected());
						error = seteoController.createSeteos(seteo);
					} else {

						seteo.setTarifaIva(tarifaIva);
						seteo.setNumeroDecimales(Integer.parseInt(txtNumeroDecimales.getText()));
						seteo.setIdentificacionDecimal(identificacionDecimal);
						seteo.setSignoMoneda(signoMoneda);
						seteo.setCliente(cliente);
						seteo.setNombrePercentajeServicioAdicional(txtNombreCampoServicioAdicional.getText());
						seteo.setPorcentajeServicioAdicional(
								Double.parseDouble(txtPorcentajeServicioAdicional.getText()));
						seteo.setTipoInventario(tipoInventario);
						seteo.setConServicioAdicional(chkConServicioAdicional.isSelected());
						System.out.println("Categoría seleccionado: " + chkConServicioAdicional.isSelected());
						seteoController.update(seteo);
					}

					if (error == null) {
						JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito",
								JOptionPane.PLAIN_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		chkConServicioAdicional.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (chkConServicioAdicional.isSelected() == true) {
					pnlServicioAdicional.setEnabled(true);
					pnlServicioAdicional.setVisible(true);
				} else {
					pnlServicioAdicional.setEnabled(false);
					pnlServicioAdicional.setVisible(false);
				}
			}
		});

	}

	private void llamarVentanaPersona() {
		if (!clienteListModal.isVisible()) {
			clienteListModal.setModal(true);
			clienteListModal.setVisible(true);
		}
		cliente = clienteListModal.getCliente();
		if (cliente != null) {
			txtIdentificacion.setText(cliente.getIdentificacion());
			txtRazonSocial.setText(cliente.getRazonSocial());

		}

	}

	private void crearControles() {
		setTitle("Configuración de parametros para el sistema");
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 624, 444);

		JPanel pnlCabecera = new JPanel();
		pnlCabecera.setLayout(null);
		pnlCabecera.setPreferredSize(new Dimension(200, 70));
		pnlCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(pnlCabecera, BorderLayout.NORTH);

		JButton btNuevo = new JButton("Nuevo");
		btNuevo.setEnabled(false);
		btNuevo.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btNuevo.setBounds(10, 11, 130, 39);
		pnlCabecera.add(btNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		pnlCabecera.add(btnGuardar);

		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		pnlCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");

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

		JLabel lblIdentificacinDcimales = new JLabel("Identificación décimales:");
		lblIdentificacinDcimales.setBounds(10, 99, 135, 14);
		panel.add(lblIdentificacinDcimales);

		cmbIdentifiacionDecimal = new JComboBox<IdentificacionDecimalEnum>();
		cmbIdentifiacionDecimal.setBounds(159, 96, 124, 20);
		panel.add(cmbIdentifiacionDecimal);

		JLabel lblSignoMoneda = new JLabel("Signo de moneda");
		lblSignoMoneda.setBounds(10, 137, 135, 14);
		panel.add(lblSignoMoneda);

		cmbSignoMoneda = new JComboBox<SignoMonedaEnum>();
		cmbSignoMoneda.setBounds(159, 134, 124, 20);
		panel.add(cmbSignoMoneda);

		cmdIva = new JComboBox<TarifaIva>();
		cmdIva.setBounds(159, 28, 124, 20);
		panel.add(cmdIva);

		txtNumeroDecimales = new JFormattedTextField();
		txtNumeroDecimales.setText("0");
		txtNumeroDecimales.setBounds(159, 62, 129, 20);
		txtNumeroDecimales.setFormatterFactory(new Formatos().getIntegerFormat());
		panel.add(txtNumeroDecimales);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Facturación", null, panel_1, null);
		panel_1.setLayout(null);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Cliente por defecto para facturaci\u00F3n", TitledBorder.LEFT, TitledBorder.TOP, null,
				new Color(0, 0, 255)));
		panel_3.setBounds(10, 21, 552, 64);
		panel_1.add(panel_3);

		txtIdentificacion = new JTextField();
		txtIdentificacion.setText("");
		txtIdentificacion.setColumns(10);
		txtIdentificacion.setBounds(10, 33, 153, 20);
		panel_3.add(txtIdentificacion);

		JLabel label = new JLabel("Contribuyente:");
		label.setBounds(199, 18, 94, 14);
		panel_3.add(label);

		JLabel label_1 = new JLabel("Ruc/CI:");
		label_1.setBounds(10, 18, 70, 14);
		panel_3.add(label_1);

		txtRazonSocial = new JTextField();
		txtRazonSocial.setEditable(false);
		txtRazonSocial.setText("");
		txtRazonSocial.setColumns(10);
		txtRazonSocial.setBounds(199, 33, 343, 20);
		panel_3.add(txtRazonSocial);

		btnCliente = new JButton("");
		btnCliente.setIcon(new ImageIcon(SeteoCrudFrm.class.getResource("/ec/peleusi/utils/images/folder_user.png")));
		btnCliente.setVerticalAlignment(SwingConstants.TOP);
		btnCliente.setBounds(165, 31, 24, 26);
		panel_3.add(btnCliente);

		pnlServicioAdicional = new JPanel();
		pnlServicioAdicional.setLayout(null);
		pnlServicioAdicional.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Servicio adicional que se cobrara en la factura de venta", TitledBorder.LEFT, TitledBorder.TOP, null,
				new Color(0, 0, 255)));
		pnlServicioAdicional.setBounds(10, 122, 552, 113);
		panel_1.add(pnlServicioAdicional);

		txtPorcentajeServicioAdicional = new JFormattedTextField();
		txtPorcentajeServicioAdicional.setBounds(104, 65, 438, 20);
		pnlServicioAdicional.add(txtPorcentajeServicioAdicional);
		txtPorcentajeServicioAdicional.setText("0.0");
		txtPorcentajeServicioAdicional.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 37, 84, 14);
		pnlServicioAdicional.add(lblNombre);

		JLabel lblPorcentaje = new JLabel("Porcentaje:");
		lblPorcentaje.setBounds(10, 68, 96, 14);
		pnlServicioAdicional.add(lblPorcentaje);

		chkConServicioAdicional = new JCheckBox("Agregar rubro adicional a la factura de venta");

		chkConServicioAdicional.setBounds(10, 92, 297, 23);

		panel_1.add(chkConServicioAdicional);

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Contabilidad", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel lblMtodoDeRegistro = new JLabel("Método de registro de inventario");
		lblMtodoDeRegistro.setBounds(29, 31, 167, 14);
		panel_2.add(lblMtodoDeRegistro);

		cmbTipoInventario = new JComboBox<TipoInventarioEnum>();
		cmbTipoInventario.setBounds(194, 28, 167, 20);
		panel_2.add(cmbTipoInventario);

	}
}
