package ec.peleusi.views.windows;


import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import ec.peleusi.models.entities.TipoIdentificacion;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.ClienteController;
import ec.peleusi.controllers.TipoCalificacionPersonaController;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.models.entities.TipoCalificacionPersona;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.Formatos;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClienteCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtIdentificacion;
	private JTextField txtRazonSocial;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtCelular;
	private JTextField txtEmail;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoIdentificacion;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCiudad;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoCalificacionPersona;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoPrecio;
	private JFormattedTextField txtDiasCredito;
	private JFormattedTextField txtPorcentajeDescuento;
	private JTextArea txtDescripcion;
	private JButton btnCancelar;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private Cliente clienteRetorno;

	public ClienteCrudFrm() {

		crearControles();
		crearEventos();
		CargarListaTipoIdentificacion();
		CargarListaTipoPrecio();
		CargarListaTipoCalificiacionPersona();
		CargarListaCiudad();

	}
	public Cliente getCliente() {
		return clienteRetorno;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaTipoPrecio() {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		List<TipoPrecio> listaTipoPrecio;
		listaTipoPrecio = tipoPrecioController.tipoPrecioList();
		cmbTipoPrecio.setModel(new DefaultComboBoxModel(listaTipoPrecio.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaTipoCalificiacionPersona() {
		TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
		List<TipoCalificacionPersona> listaTipoCalificacionPersona;
		listaTipoCalificacionPersona = tipoCalificacionPersonaController.tipoCalificacionPersonaList();
		cmbTipoCalificacionPersona.setModel(new DefaultComboBoxModel(listaTipoCalificacionPersona.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaCiudad() {
		CiudadController ciudadController = new CiudadController();
		List<Ciudad> listaCiudad;
		listaCiudad = ciudadController.CiudadList();
		cmbCiudad.setModel(new DefaultComboBoxModel(listaCiudad.toArray()));
	}

	private void limpiarCampos() {

		txtIdentificacion.setText("");
		txtRazonSocial.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtDiasCredito.setText("0");
		txtPorcentajeDescuento.setText("0");
		txtDescripcion.setText("");
		txtIdentificacion.requestFocus();

	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtIdentificacion.getText().isEmpty() || txtRazonSocial.getText().isEmpty()
				|| txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()
				|| txtDiasCredito.getText().isEmpty() || txtPorcentajeDescuento.getText().isEmpty()
				)
			llenos = false;
		return llenos;
	}

	private void crearEventos() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					if (!isCamposLlenos()) {
						JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
								JOptionPane.WARNING_MESSAGE);
						return;
					}

					TipoIdentificacion tipoIdentificacion = (TipoIdentificacion) cmbTipoIdentificacion
							.getSelectedItem();
					TipoPrecio tipoPrecio = (TipoPrecio) cmbTipoPrecio.getSelectedItem();
					TipoCalificacionPersona tipoCalificacionPersona = (TipoCalificacionPersona) cmbTipoCalificacionPersona
							.getSelectedItem();
					Ciudad ciudad = (Ciudad) cmbCiudad.getSelectedItem();

					Cliente cliente = new Cliente(tipoIdentificacion, txtIdentificacion.getText(),
							txtRazonSocial.getText(), txtDireccion.getText(), txtTelefono.getText(), tipoPrecio,
							tipoCalificacionPersona, Integer.parseInt(txtDiasCredito.getText()),
							Double.parseDouble(txtPorcentajeDescuento.getText()), txtDescripcion.getText(),
							txtEmail.getText(), txtCelular.getText(), ciudad);
					ClienteController clienteController = new ClienteController();

					String error = clienteController.createCliente(cliente);

					if (error == null) {
						
						JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito",								
								JOptionPane.PLAIN_MESSAGE);
						clienteRetorno=cliente;
					} else {
						JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
					}

				}

				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

	}

	private void crearControles() {

		setBounds(100, 100, 680, 426);		
		setTitle("Ingreso de datos cliente");
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);

		btnNuevo = new JButton("Nuevo");

		btnNuevo.setIcon(new ImageIcon(ClienteCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panel.add(btnNuevo);

		btnGuardar = new JButton("Guardar");

		btnGuardar.setIcon(new ImageIcon(ClienteCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panel.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(ClienteCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panel.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");

		btnCancelar.setIcon(new ImageIcon(ClienteCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Datos personales",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_2.setBounds(10, 11, 644, 200);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		panel_2.setAlignmentY(1.0f);
		panel_2.setAlignmentX(0.0f);

		cmbTipoIdentificacion = new JComboBox<TipoIdentificacion>();
		cmbTipoIdentificacion.setBounds(101, 20, 139, 20);
		panel_2.add(cmbTipoIdentificacion);

		JLabel label = new JLabel("Tipo Identificaciòn");
		label.setBounds(8, 20, 87, 14);
		panel_2.add(label);

		txtIdentificacion = new JTextField();
		txtIdentificacion.setText("");
		txtIdentificacion.setColumns(10);
		txtIdentificacion.setBounds(350, 20, 258, 20);
		panel_2.add(txtIdentificacion);

		JLabel label_2 = new JLabel("Identificaciòn");
		label_2.setBounds(270, 20, 83, 14);
		panel_2.add(label_2);

		txtRazonSocial = new JTextField();
		txtRazonSocial.setText("");
		txtRazonSocial.setColumns(10);
		txtRazonSocial.setBounds(101, 45, 507, 20);
		panel_2.add(txtRazonSocial);

		JLabel label_3 = new JLabel("Razòn Social");
		label_3.setBounds(8, 45, 84, 14);
		panel_2.add(label_3);

		JLabel label_8 = new JLabel("Descripciòn");
		label_8.setBounds(8, 145, 83, 14);
		panel_2.add(label_8);

		JLabel label_9 = new JLabel("Direcciòn ");
		label_9.setBounds(270, 70, 83, 14);
		panel_2.add(label_9);

		txtDireccion = new JTextField();
		txtDireccion.setText("");
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(350, 70, 258, 20);
		panel_2.add(txtDireccion);

		JLabel label_10 = new JLabel("Telèfono");
		label_10.setBounds(8, 95, 87, 14);
		panel_2.add(label_10);

		txtTelefono = new JTextField();
		txtTelefono.setText("");
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(101, 95, 139, 20);
		panel_2.add(txtTelefono);

		txtDescripcion = new JTextArea();
		txtDescripcion.setForeground(UIManager.getColor("InternalFrame.inactiveTitleForeground"));
		txtDescripcion.setBackground(Color.WHITE);
		txtDescripcion.setText("");
		txtDescripcion.setBounds(101, 145, 507, 44);		
		panel_2.add(txtDescripcion);

		JLabel lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(270, 95, 78, 14);
		panel_2.add(lblEmail);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(8, 70, 83, 14);
		panel_2.add(lblCiudad);

		cmbCiudad = new JComboBox<TipoCalificacionPersona>();
		cmbCiudad.setBounds(101, 70, 139, 20);
		panel_2.add(cmbCiudad);

		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(8, 120, 50, 14);
		panel_2.add(lblCelular);

		txtCelular = new JTextField();
		txtCelular.setText("");
		txtCelular.setColumns(10);
		txtCelular.setBounds(101, 120, 139, 20);
		panel_2.add(txtCelular);

		txtEmail = new JTextField();
		txtEmail.setText("");
		txtEmail.setColumns(10);
		txtEmail.setBounds(350, 95, 258, 20);
		panel_2.add(txtEmail);
		
		JLabel label_1 = new JLabel("*");
		label_1.setForeground(Color.BLUE);
		label_1.setBounds(610, 23, 12, 14);
		panel_2.add(label_1);
		
		JLabel label_11 = new JLabel("*");
		label_11.setForeground(Color.BLUE);
		label_11.setBounds(610, 51, 12, 14);
		panel_2.add(label_11);
		
		JLabel label_12 = new JLabel("*");
		label_12.setForeground(Color.BLUE);
		label_12.setBounds(610, 76, 12, 14);
		panel_2.add(label_12);
		
		JLabel label_13 = new JLabel("*");
		label_13.setForeground(Color.BLUE);
		label_13.setBounds(610, 98, 12, 14);
		panel_2.add(label_13);
		
		JLabel label_14 = new JLabel("*");
		label_14.setForeground(Color.BLUE);
		label_14.setBounds(242, 23, 12, 14);
		panel_2.add(label_14);
		
		JLabel label_15 = new JLabel("*");
		label_15.setForeground(Color.BLUE);
		label_15.setBounds(242, 76, 12, 14);
		panel_2.add(label_15);
		
		JLabel label_16 = new JLabel("*");
		label_16.setForeground(Color.BLUE);
		label_16.setBounds(242, 100, 12, 14);
		panel_2.add(label_16);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Datos financieros",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_3.setBounds(10, 215, 644, 85);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		JLabel label_7 = new JLabel("Porcentaje Descuento");
		label_7.setBounds(8, 24, 147, 14);
		panel_3.add(label_7);

		txtPorcentajeDescuento = new JFormattedTextField();
		txtPorcentajeDescuento.setBounds(165, 21, 120, 20);
		txtPorcentajeDescuento.setFormatterFactory(new Formatos().getDecimalFormat());
		panel_3.add(txtPorcentajeDescuento);
		txtPorcentajeDescuento.setText("0");

		JLabel label_5 = new JLabel("Dìas Crèdito");
		label_5.setBounds(320, 24, 75, 14);
		panel_3.add(label_5);

		JLabel label_4 = new JLabel("Tipo Calificaciòn Persona");
		label_4.setBounds(8, 49, 147, 14);
		panel_3.add(label_4);

		cmbTipoCalificacionPersona = new JComboBox<TipoCalificacionPersona>();
		cmbTipoCalificacionPersona.setBounds(165, 50, 120, 20);
		panel_3.add(cmbTipoCalificacionPersona);

		JLabel label_6 = new JLabel("Tipo Precio");
		label_6.setBounds(320, 49, 64, 14);
		panel_3.add(label_6);

		cmbTipoPrecio = new JComboBox<TipoPrecio>();
		cmbTipoPrecio.setBounds(410, 46, 120, 20);
		panel_3.add(cmbTipoPrecio);

		txtDiasCredito = new JFormattedTextField();
		txtDiasCredito.setBounds(410, 21, 120, 20);
		txtDiasCredito.setFormatterFactory(new Formatos().getIntegerFormat());
		txtDiasCredito.setText("0");
		panel_3.add(txtDiasCredito);
		
		JLabel label_17 = new JLabel("*");
		label_17.setForeground(Color.BLUE);
		label_17.setBounds(287, 53, 12, 14);
		panel_3.add(label_17);
		
		JLabel label_18 = new JLabel("*");
		label_18.setForeground(Color.BLUE);
		label_18.setBounds(531, 49, 12, 14);
		panel_3.add(label_18);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaTipoIdentificacion() {
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		List<TipoIdentificacion> listaTipoIdentificacion;
		listaTipoIdentificacion = tipoIdentificacionController.tipoIdentificacionList();
		cmbTipoIdentificacion.setModel(new DefaultComboBoxModel(listaTipoIdentificacion.toArray()));
	}
}
