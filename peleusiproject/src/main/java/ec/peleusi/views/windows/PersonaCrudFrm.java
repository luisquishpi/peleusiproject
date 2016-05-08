package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.DireccionPersonaController;
import ec.peleusi.controllers.PersonaController;
import ec.peleusi.controllers.TipoCalificacionPersonaController;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.DireccionPersona;
import ec.peleusi.models.entities.Persona;
import ec.peleusi.models.entities.TipoCalificacionPersona;
import ec.peleusi.models.entities.TipoIdentificacion;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.Formatos;
import ec.peleusi.utils.TipoPersonaEnum;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import java.awt.Component;

public class PersonaCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtIdentificacion;
	private JTextField txtRazonSocial;
	private JTextField txtDiasCredito;
	private JFormattedTextField txtPorcentajeDescuento;
	private Persona personaRetorno;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoPrecio;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoCalificacionPersona;
	private JComboBox<TipoPersonaEnum> cmbTipoPersona;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoIdentificacion;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCiudad;
	@SuppressWarnings("rawtypes")
	private JTabbedPane tpnlPanel;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtCelular;
	private JTextField txtEmail;
	private JTextField txtCodigoPostal;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JScrollPane scrollPane;
	private JButton btnAgregar;
	private DefaultTableModel modelo;
	private JTable tablaDireccionPersona;
	private JCheckBox chkPorDefecto;
	@SuppressWarnings("unused")
	private Object[] filaDatosDireccionPersona;
	private DireccionPersona direccionPersonaRetorno;
	private JTextField txtDireccionPersona;
	private JTextField txtTelefonoPersona;
	private JTextArea txtDescripcion;

	public PersonaCrudFrm() {
		setTitle("Persona");
		crearControles();
		crearEventos();
		crearTablaDireccionPersona();
		cargarCombos();
		limpiarCampos();		
	}

	private void crearTablaDireccionPersona() {
		Object[] cabecera = { "Nombre", "Direcciòn", "Ciudad" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2) {
					return false;
				}
				return true;
			}
		};
		filaDatosDireccionPersona = new Object[cabecera.length];
		cargarTablaDireccionPersona();
		tablaDireccionPersona = new JTable(modelo) {
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		tablaDireccionPersona.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaDireccionPersona.setPreferredScrollableViewportSize(tablaDireccionPersona.getPreferredSize());
		tablaDireccionPersona.getTableHeader().setReorderingAllowed(true);
		tablaDireccionPersona.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaDireccionPersona.getColumnModel().getColumn(1).setPreferredWidth(150);
		tablaDireccionPersona.getColumnModel().getColumn(2).setPreferredWidth(80);
		scrollPane.setViewportView(tablaDireccionPersona);
	}
	
	private Object[] agregarDatosAFila(DireccionPersona direccionPersona) {
		filaDatosDireccionPersona[0] = direccionPersona.getNombre();
		filaDatosDireccionPersona[1] = direccionPersona.getDireccion();
		filaDatosDireccionPersona[2] = direccionPersona.getCuidad();
				return filaDatosDireccionPersona;
	}

		private void cargarTablaDireccionPersona() {
		DireccionPersonaController direccionPersonaController = new DireccionPersonaController();
		List<DireccionPersona> listaDireccionPersona = direccionPersonaController.DireccionPersonaList();
		for (DireccionPersona direccionPersona : listaDireccionPersona) {
			modelo.addRow(agregarDatosAFila(direccionPersona));
		}
	}

	private void cargarCombos() {
		ListaCiudad();
		CargarComboTipoPersona();
		CargarListaTipoIdentificacion();
		CargarListaTipoCalificiacionPersona();
		CargarListaTipoPrecio();			
	}

	private void CargarComboTipoPersona() {
		cmbTipoPersona.setModel(new DefaultComboBoxModel<TipoPersonaEnum>(TipoPersonaEnum.values()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaTipoIdentificacion() {
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		List<TipoIdentificacion> listaTipoIdentificacion;
		listaTipoIdentificacion = tipoIdentificacionController.tipoIdentificacionList();
		cmbTipoIdentificacion.setModel(new DefaultComboBoxModel(listaTipoIdentificacion.toArray()));
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
	private void ListaCiudad() {
		CiudadController ciudadController = new CiudadController();
		List<Ciudad> listaCiudad;
		listaCiudad = ciudadController.CiudadList();
		cmbCiudad.setModel(new DefaultComboBoxModel(listaCiudad.toArray()));		
	}
		
	public Persona getPersona() {
		return personaRetorno;
	}
	
	public DireccionPersona getDireccionPersona() {
		return direccionPersonaRetorno;
	}

	private void limpiarCampos() {
		txtIdentificacion.setText("");
		txtRazonSocial.setText("");
		txtDireccionPersona.setText("");
		txtTelefonoPersona.setText("");
		txtDiasCredito.setText("0");
		txtPorcentajeDescuento.setText("0");
		txtDescripcion.setText("");

	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtIdentificacion.getText().isEmpty() || txtRazonSocial.getText().isEmpty() 
				|| txtDireccionPersona.getText().isEmpty() || txtTelefonoPersona.getText().isEmpty()
				|| txtDiasCredito.getText().isEmpty() || txtPorcentajeDescuento.getText().isEmpty()
				|| txtDescripcion.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	@SuppressWarnings("unused")
	private void limpiarCamposDireccion() {
		txtNombre.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtCodigoPostal.setText("");
		chkPorDefecto.setSelected(false);
	}

	@SuppressWarnings("unused")
	private boolean CamposLlenosDireccion() {
		boolean llenosDireccion = true;
		if (txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()
				|| txtCelular.getText().isEmpty() || txtEmail.getText().isEmpty()
				|| txtCodigoPostal.getText().isEmpty())
			llenosDireccion = false;
		return llenosDireccion;
	}

	private void crearEventos() {

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarCampos();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Boolean tipoPersona = true;
				TipoIdentificacion tipoIdentificacion = (TipoIdentificacion) cmbTipoIdentificacion.getSelectedItem();
				TipoPrecio tipoPrecio = (TipoPrecio) cmbTipoPrecio.getSelectedItem();
				TipoCalificacionPersona tipoCalificacionPersona = (TipoCalificacionPersona) cmbTipoCalificacionPersona
						.getSelectedItem();

				Persona persona = new Persona(tipoIdentificacion, txtIdentificacion.getText(), txtRazonSocial.getText(),
						txtDireccionPersona.getText(), txtTelefonoPersona.getText(), tipoPrecio,
						tipoCalificacionPersona, Integer.parseInt(txtDiasCredito.getText()),
						Double.parseDouble(txtPorcentajeDescuento.getText()), txtDescripcion.getText(), tipoPersona);
				PersonaController personaControllers = new PersonaController();
				String error = personaControllers.createPersona(persona);

				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Ciudad ciudad = (Ciudad) cmbCiudad.getSelectedItem();
				DireccionPersona direccionPersona = new DireccionPersona(Persona(), txtNombre.getText(), txtDireccion.getText(),
						txtTelefono.getText(), txtCelular.getText(), txtEmail.getText(), txtCodigoPostal.getText(),
						ciudad);
				DireccionPersonaController direccionPersonaControllers = new DireccionPersonaController();
				String error = direccionPersonaControllers.createDireccionPersona(direccionPersona);

				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
					limpiarCamposDireccion();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void crearControles() {

		setBounds(100, 100, 611, 399);
		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		tpnlPanel = new JTabbedPane(JTabbedPane.TOP);
		tpnlPanel.setBounds(20, 11, 565, 269);
		panelCuerpo.add(tpnlPanel);

		JPanel pnlPersona = new JPanel();
		pnlPersona.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		pnlPersona.setAlignmentX(Component.LEFT_ALIGNMENT);
		tpnlPanel.addTab("Persona", null, pnlPersona, null);
		pnlPersona.setLayout(null);

		cmbTipoIdentificacion = new JComboBox<TipoIdentificacion>();
		cmbTipoIdentificacion.setBounds(110, 6, 120, 20);
		pnlPersona.add(cmbTipoIdentificacion);

		JLabel lblCodigo = new JLabel("Tipo Identificaciòn");
		lblCodigo.setBounds(8, 6, 87, 14);
		pnlPersona.add(lblCodigo);

		JLabel lblTipoPersona = new JLabel("Tipo Persona ");
		lblTipoPersona.setBounds(320, 6, 65, 14);
		pnlPersona.add(lblTipoPersona);

		cmbTipoPersona = new JComboBox<TipoPersonaEnum>();
		cmbTipoPersona.setBounds(405, 6, 120, 20);
		pnlPersona.add(cmbTipoPersona);

		txtIdentificacion = new JTextField();
		txtIdentificacion.setBounds(110, 36, 120, 20);
		pnlPersona.add(txtIdentificacion);
		txtIdentificacion.setColumns(10);

		JLabel lblIdentificacin = new JLabel("Identificaciòn");
		lblIdentificacin.setBounds(8, 36, 83, 14);
		pnlPersona.add(lblIdentificacin);

		txtRazonSocial = new JTextField();
		txtRazonSocial.setBounds(110, 66, 377, 20);
		pnlPersona.add(txtRazonSocial);
		txtRazonSocial.setColumns(10);

		JLabel lblRaznSocial = new JLabel("Razòn Social");
		lblRaznSocial.setBounds(8, 66, 84, 14);
		pnlPersona.add(lblRaznSocial);

		cmbTipoCalificacionPersona = new JComboBox<TipoCalificacionPersona>();
		cmbTipoCalificacionPersona.setBounds(160, 156, 120, 20);
		pnlPersona.add(cmbTipoCalificacionPersona);

		JLabel lblCalificacinPersona = new JLabel("Tipo Calificaciòn Persona");
		lblCalificacinPersona.setBounds(8, 156, 166, 14);
		pnlPersona.add(lblCalificacinPersona);

		txtDiasCredito = new JTextField();
		txtDiasCredito.setBounds(405, 36, 120, 20);
		pnlPersona.add(txtDiasCredito);
		txtDiasCredito.setColumns(10);

		JLabel lblDasCrdito = new JLabel("Dìas Crèdito");
		lblDasCrdito.setBounds(320, 36, 75, 14);
		pnlPersona.add(lblDasCrdito);

		cmbTipoPrecio = new JComboBox<TipoPrecio>();
		cmbTipoPrecio.setBounds(405, 156, 120, 20);
		pnlPersona.add(cmbTipoPrecio);

		JLabel lblTipoPrecio = new JLabel("Tipo Precio");
		lblTipoPrecio.setBounds(320, 156, 83, 14);
		pnlPersona.add(lblTipoPrecio);

		txtPorcentajeDescuento = new JFormattedTextField();
		txtPorcentajeDescuento.setBounds(405, 126, 120, 20);
		pnlPersona.add(txtPorcentajeDescuento);
		txtPorcentajeDescuento.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblPorcentajeDescuento = new JLabel("Porcentaje Descuento");
		lblPorcentajeDescuento.setBounds(290, 126, 106, 14);
		pnlPersona.add(lblPorcentajeDescuento);

		JLabel lblDescripcin = new JLabel("Descripciòn");
		lblDescripcin.setBounds(10, 186, 83, 14);
		pnlPersona.add(lblDescripcin);
		
		JLabel lblDireccin_1 = new JLabel("Direcciòn ");
		lblDireccin_1.setBounds(8, 96, 87, 14);
		pnlPersona.add(lblDireccin_1);
		
		txtDireccionPersona = new JTextField();
		txtDireccionPersona.setBounds(110, 96, 377, 20);
		pnlPersona.add(txtDireccionPersona);
		txtDireccionPersona.setColumns(10);
		
		JLabel lblTelfono_1 = new JLabel("Telèfono");
		lblTelfono_1.setBounds(8, 126, 87, 14);
		pnlPersona.add(lblTelfono_1);
		
		txtTelefonoPersona = new JTextField();
		txtTelefonoPersona.setBounds(110, 126, 120, 20);
		pnlPersona.add(txtTelefonoPersona);
		txtTelefonoPersona.setColumns(10);
		
		txtDescripcion = new JTextArea();
		txtDescripcion.setText("");
		txtDescripcion.setBounds(105, 187, 420, 38);
		pnlPersona.add(txtDescripcion);
		
		JPanel pnlDireccion = new JPanel();
		tpnlPanel.addTab("Direcciòn", null, pnlDireccion, null);
		pnlDireccion.setLayout(null);

		JLabel lblDireccin = new JLabel("Direcciòn");
		lblDireccin.setBounds(6, 70, 64, 14);
		pnlDireccion.add(lblDireccin);

		txtDireccion = new JTextField();
		txtDireccion.setBounds(80, 70, 220, 20);
		pnlDireccion.add(txtDireccion);
		txtDireccion.setColumns(10);

		JLabel lblTelfono = new JLabel("Telèfono");
		lblTelfono.setBounds(6, 100, 64, 14);
		pnlDireccion.add(lblTelfono);

		txtTelefono = new JTextField();
		txtTelefono.setBounds(80, 100, 165, 20);
		pnlDireccion.add(txtTelefono);
		txtTelefono.setColumns(10);

		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(6, 130, 64, 14);
		pnlDireccion.add(lblCelular);

		txtCelular = new JTextField();
		txtCelular.setBounds(80, 130, 165, 20);
		pnlDireccion.add(txtCelular);
		txtCelular.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(6, 160, 46, 14);
		pnlDireccion.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(80, 160, 220, 20);
		pnlDireccion.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(6, 10, 46, 14);
		pnlDireccion.add(lblCiudad);
		
		JLabel lblCdigoPostal = new JLabel("Còdigo Postal");
		lblCdigoPostal.setBounds(6, 190, 97, 14);
		pnlDireccion.add(lblCdigoPostal);

		txtCodigoPostal = new JTextField();
		txtCodigoPostal.setBounds(80, 190, 165, 20);
		pnlDireccion.add(txtCodigoPostal);
		txtCodigoPostal.setColumns(10);

		lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(6, 40, 46, 14);
		pnlDireccion.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(80, 40, 165, 20);
		pnlDireccion.add(txtNombre);
		txtNombre.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(312, 10, 238, 220);
		pnlDireccion.add(scrollPane);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(200, 215, 80, 22);
		pnlDireccion.add(btnAgregar);

		chkPorDefecto = new JCheckBox("Por Defecto");
		chkPorDefecto.setBounds(80, 215, 97, 23);
		pnlDireccion.add(chkPorDefecto);
		
		cmbCiudad = new JComboBox<Ciudad>();				
		cmbCiudad.setBounds(80, 10, 165, 20);
		pnlDireccion.add(cmbCiudad);

	}
}
