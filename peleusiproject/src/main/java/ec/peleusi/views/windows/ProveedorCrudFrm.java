package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import ec.peleusi.models.entities.TipoIdentificacion;
import ec.peleusi.utils.Formatos;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import ec.peleusi.controllers.DireccionProveedorController;
import ec.peleusi.controllers.ProveedorController;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.models.entities.Proveedor;
import javax.swing.JCheckBox;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.DireccionProveedor;
import javax.swing.JTable;
import java.util.List;

public class ProveedorCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JComboBox<TipoIdentificacion> cmbTipoIdentificacion;
	private JTextField txtIdentificacion;
	private JTextField txtRazonSocial;
	private JFormattedTextField txtDiasCredito;
	private JFormattedTextField txtPorcentajeDescuento;
	private JTextArea txtDescripcion;
	private JPanel pnlDatos;
	private JTabbedPane tpnlProveedor;
	private JPanel pnlDireccion;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtCelular;
	private JTextField txtEmail;
	private JTextField txtCodigoPostal;
	private JTextField txtNombre;
	private JButton btnAgregar;
	private JCheckBox chkPorDefecto;
	private JButton btnBuscarCiudad;
	public DireccionProveedor direccionProveedor;
	private DefaultTableModel modelo;
	private Object[] filaDatosDireccionProveedor;
	private JScrollPane scroollPane;
	private JTable tblDireccionProveedor;
	private Proveedor proveedor;
	private JTextField txtIdProveedor;
	private JScrollPane scrollPane;
	private JTextField cmbCiudad;	
	private CiudadListModalFrm ciudadListModalFrm = new CiudadListModalFrm();
	private Ciudad ciudad;
	
	public ProveedorCrudFrm() {
		setTitle("Creando Proveedor");
		crearControles();
		crearEventos();
		cargarListaTipoIdentificacion();
		tpnlProveedor.setEnabledAt(1, false);
		crearTablaDireccionProveedor();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				llenarCamposConEntidad();
				txtIdentificacion.requestFocus();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarListaTipoIdentificacion() {
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		List<TipoIdentificacion> listaTipoIdentificacion;
		listaTipoIdentificacion = tipoIdentificacionController.tipoIdentificacionList();
		cmbTipoIdentificacion.setModel(new DefaultComboBoxModel(listaTipoIdentificacion.toArray()));
	}

	private void llenarCamposConEntidad() {
		if (proveedor != null && proveedor.getId() != null) {
			this.setTitle("Actualizar Proveedor");
			btnGuardar.setText("Actualizar");
			limpiarCamposDatos();
			cmbTipoIdentificacion.setSelectedItem(proveedor.getTipoIdentificacion());
			txtIdentificacion.setText(proveedor.getIdentificacion());
			txtRazonSocial.setText(proveedor.getRazonSocial());
			txtDiasCredito.setText(Integer.toString(proveedor.getDiasCredito()));
			txtPorcentajeDescuento.setText(Double.toString(proveedor.getPorcentajeDescuento()));
			txtDescripcion.setText(proveedor.getDescripcion());
		} else {
			this.setTitle("Creando Tarifa IVA");
			btnGuardar.setText("Guardar");
			limpiarCamposDatos();
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		proveedor.setTipoIdentificacion((TipoIdentificacion)cmbTipoIdentificacion.getSelectedItem());
		proveedor.setIdentificacion(txtIdentificacion.getText());
		proveedor.setRazonSocial(txtRazonSocial.getText());
		proveedor.setDiasCredito(Integer.parseInt(txtDiasCredito.getText().toString()));
		proveedor.setPorcentajeDescuento(Double.parseDouble(txtPorcentajeDescuento.getText().toString()));
		proveedor.setDescripcion(txtDescripcion.getText());
	}
	
	private void guardarNuevoProveedor() {
		proveedor = new Proveedor();
		llenarEntidadAntesDeGuardar();
		ProveedorController proveedornController = new ProveedorController();
		String error = proveedornController.createProveedor(proveedor);		 
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			txtIdProveedor.setText(proveedor.getId().toString());
			tpnlProveedor.setEnabledAt(1, true);
			tpnlProveedor.setSelectedIndex(1);
			//dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}	

	private void actualizarProveedor() {
		llenarEntidadAntesDeGuardar();
		ProveedorController proveedorController = new ProveedorController();
		String error = proveedorController.updateProveedor(proveedor);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);		

//			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public Proveedor getProveedor() {
		return proveedor;
	}
	
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = new Proveedor();
		this.proveedor = proveedor;
	}
	
	private void limpiarCamposDatos() {
		txtIdentificacion.setText("");
		txtRazonSocial.setText("");
		txtDiasCredito.setText("0");
		txtPorcentajeDescuento.setText("0");
		txtDescripcion.setText("");
	}

	private boolean camposLlenosDatos() {
		boolean llenos = true;
		if (txtIdentificacion.getText().isEmpty() || txtRazonSocial.getText().isEmpty()
				|| txtDiasCredito.getText().isEmpty() || txtPorcentajeDescuento.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearTablaDireccionProveedor() {
		Object[] cabecera = { "Id", "Nombre", "Direccion", "Ciudad", "IdProveedor", "Telefono", "Celular", "Email",
				"CodigoPostal", "PorDefecto" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;
	
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4
						|| columnIndex == 5 || columnIndex == 6 || columnIndex == 7 || columnIndex == 8
						|| columnIndex == 9) {
					return false;
				}
				return true;
			}
		};
		filaDatosDireccionProveedor = new Object[cabecera.length];
		tblDireccionProveedor = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column){
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
				case 6:
					return String.class;
				case 7:
					return String.class;
				case 8:
					return String.class;
				case 9:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		
		tblDireccionProveedor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblDireccionProveedor.setPreferredScrollableViewportSize(tblDireccionProveedor.getPreferredSize());
		tblDireccionProveedor.getTableHeader().setReorderingAllowed(true);
		tblDireccionProveedor.getColumnModel().getColumn(0).setMaxWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(0).setMinWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(1).setPreferredWidth(195);
		tblDireccionProveedor.getColumnModel().getColumn(2).setPreferredWidth(220);
		tblDireccionProveedor.getColumnModel().getColumn(3).setPreferredWidth(112);
		tblDireccionProveedor.getColumnModel().getColumn(4).setMaxWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(4).setMinWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(4).setPreferredWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(5).setMaxWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(5).setMinWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(5).setPreferredWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(6).setMaxWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(6).setMinWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(6).setPreferredWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(7).setMaxWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(7).setMinWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(7).setPreferredWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(8).setMaxWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(8).setMinWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(8).setPreferredWidth(0);
		tblDireccionProveedor.getColumnModel().getColumn(9).setPreferredWidth(20);
		scroollPane.setViewportView(tblDireccionProveedor);
	}

	private Object[] agregarDatosAFila(DireccionProveedor direccionProveedor) {
		filaDatosDireccionProveedor[0] = direccionProveedor.getId();
		filaDatosDireccionProveedor[1] = direccionProveedor.getNombre();
		filaDatosDireccionProveedor[2] = direccionProveedor.getDireccion();
		filaDatosDireccionProveedor[3] = direccionProveedor.getCuidad();
		filaDatosDireccionProveedor[4] = direccionProveedor.getProveedor();
		filaDatosDireccionProveedor[5] = direccionProveedor.getTelefono();
		filaDatosDireccionProveedor[6] = direccionProveedor.getCelular();
		filaDatosDireccionProveedor[7] = direccionProveedor.getEmail();
		filaDatosDireccionProveedor[8] = direccionProveedor.getCodigoPostal();
		filaDatosDireccionProveedor[9] = direccionProveedor.getPorDefecto();
		return filaDatosDireccionProveedor;
	}	

	private void cargarTablaDireccionProveedor() {
		modelo.getDataVector().removeAllElements();
		modelo.fireTableDataChanged();
		DefaultTableModel modelo = (DefaultTableModel) tblDireccionProveedor.getModel();
		DireccionProveedorController direccionProveedorController = new DireccionProveedorController();
		List<DireccionProveedor> listaDireccionProveedor = direccionProveedorController
				.direccionProveedorIdList(proveedor);
		for (DireccionProveedor direccionProveedor : listaDireccionProveedor) {
			modelo.addRow(agregarDatosAFila(direccionProveedor));
		}
	}
	
	private void limpiarCamposDireccion() {
		cmbCiudad.setText("");
		txtNombre.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtCelular.setText("");
		txtEmail.setText("");
		txtCodigoPostal.setText("");
		chkPorDefecto.setSelected(false);
	}

	private boolean camposLlenosDireccion() {
		boolean llenosDireccion = true;
		if (cmbCiudad.getText().isEmpty() || txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty())
			llenosDireccion = false;
		return llenosDireccion;
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				proveedor = new Proveedor();
				llenarCamposConEntidad();
							limpiarCamposDireccion();
				tblDireccionProveedor.setModel(new DefaultTableModel());
				crearTablaDireccionProveedor();
				tpnlProveedor.setSelectedIndex(0);
			}
		});		
		
		btnBuscarCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!ciudadListModalFrm.isVisible()) {
					ciudadListModalFrm.setModal(true);
					ciudadListModalFrm.setVisible(true);
					}	
					ciudad = ciudadListModalFrm.getCiudad();

					if (ciudad != null) {
						System.out.println("Categoría seleccionado: " + ciudad);
						cmbCiudad.setText(ciudad.getNombre());
					}
			}
		});						
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!camposLlenosDatos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (proveedor != null && proveedor.getId() != null) {
					actualizarProveedor();
					/*txtIdProveedor.setText(proveedor.getId().toString());
					tpnlProveedor.setEnabledAt(1, true);
					tpnlProveedor.setSelectedIndex(1);*/
				} else {
					guardarNuevoProveedor();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proveedor = new Proveedor();
				dispose();
			}
		});

		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!camposLlenosDireccion()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				ProveedorController proveedorController = new ProveedorController();
				proveedor = new Proveedor();
				proveedor = proveedorController.getProveedor(Integer.parseInt(txtIdProveedor.getText()));
				DireccionProveedor direccionProveedor = new DireccionProveedor();
				direccionProveedor.setProveedor(proveedor);
				direccionProveedor.setNombre(txtNombre.getText());
				direccionProveedor.setDireccion(txtDireccion.getText());
				direccionProveedor.setTelefono(txtTelefono.getText());
				direccionProveedor.setCelular(txtCelular.getText());
				direccionProveedor.setEmail(txtEmail.getText());
				direccionProveedor.setCodigoPostal(txtCodigoPostal.getText());
				direccionProveedor.setCuidad(ciudad);
				direccionProveedor.setPorDefecto(chkPorDefecto.isSelected());
				DireccionProveedorController direccionPersonaControllers = new DireccionProveedorController();
				String error = direccionPersonaControllers.createDireccionPersona(direccionProveedor);
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
					cargarTablaDireccionProveedor();
					limpiarCamposDireccion();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	private void crearControles() {
		setBounds(100, 100, 611, 398);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(ProveedorCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(25, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(ProveedorCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(230, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(ProveedorCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		tpnlProveedor = new JTabbedPane(JTabbedPane.TOP);
		tpnlProveedor.setBounds(10, 6, 575, 287);
		panelCuerpo.add(tpnlProveedor);

		pnlDatos = new JPanel();
		pnlDatos.setLayout(null);
		pnlDatos.setAlignmentY(1.0f);
		pnlDatos.setAlignmentX(0.0f);
		tpnlProveedor.addTab("Datos", null, pnlDatos, null);

		cmbTipoIdentificacion = new JComboBox<TipoIdentificacion>();
		cmbTipoIdentificacion.setBounds(140, 10, 130, 20);
		pnlDatos.add(cmbTipoIdentificacion);

		JLabel lblTipoIdentificacion = new JLabel("Tipo Identificaciòn");
		lblTipoIdentificacion.setBounds(10, 10, 106, 14);
		pnlDatos.add(lblTipoIdentificacion);

		txtIdentificacion = new JTextField();
		txtIdentificacion.setText("");
		txtIdentificacion.setColumns(10);
		txtIdentificacion.setBounds(140, 45, 130, 20);
		pnlDatos.add(txtIdentificacion);

		JLabel lblIdentificacion = new JLabel("Identificaciòn");
		lblIdentificacion.setBounds(10, 45, 83, 14);
		pnlDatos.add(lblIdentificacion);

		txtRazonSocial = new JTextField();
		txtRazonSocial.setText("");
		txtRazonSocial.setColumns(10);
		txtRazonSocial.setBounds(140, 80, 377, 20);
		pnlDatos.add(txtRazonSocial);

		JLabel lblRazonSocial = new JLabel("Razòn Social");
		lblRazonSocial.setBounds(10, 80, 84, 14);
		pnlDatos.add(lblRazonSocial);

		txtDiasCredito = new JFormattedTextField();
		txtDiasCredito.setText("0");
		txtDiasCredito.setBounds(140, 115, 120, 20);
		txtDiasCredito.setFormatterFactory(new Formatos().getIntegerFormat());
		pnlDatos.add(txtDiasCredito);

		JLabel lblDiasCredito = new JLabel("Dìas Crèdito");
		lblDiasCredito.setBounds(10, 115, 75, 14);
		pnlDatos.add(lblDiasCredito);

		txtPorcentajeDescuento = new JFormattedTextField();
		txtPorcentajeDescuento.setText("0");
		txtPorcentajeDescuento.setBounds(140, 150, 120, 20);
		txtPorcentajeDescuento.setFormatterFactory(new Formatos().getDecimalFormat());
		pnlDatos.add(txtPorcentajeDescuento);

		JLabel lblPorcentajeDescuento = new JLabel("Porcentaje Descuento");
		lblPorcentajeDescuento.setBounds(10, 150, 139, 14);
		pnlDatos.add(lblPorcentajeDescuento);

		JLabel lblDescripcion = new JLabel("Descripciòn");
		lblDescripcion.setBounds(10, 185, 83, 14);
		pnlDatos.add(lblDescripcion);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(137, 244, 432, -62);
		pnlDatos.add(scrollPane);
		pnlDireccion = new JPanel();
		pnlDireccion.setLayout(null);
		tpnlProveedor.addTab("Direcciòn", null, pnlDireccion, null);

		JLabel lblDireccion = new JLabel("Direcciòn");
		lblDireccion.setBounds(6, 70, 64, 14);
		pnlDireccion.add(lblDireccion);
		txtDescripcion = new JTextArea();
		txtDescripcion.setText("");
		txtDescripcion.setBounds(140, 185, 420, 52);
		pnlDatos.add(txtDescripcion);

		txtIdProveedor = new JTextField();
		txtIdProveedor.setVisible(false);
		txtIdProveedor.setBounds(284, 150, 24, 20);
		pnlDatos.add(txtIdProveedor);
		txtIdProveedor.setColumns(10);

		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(65, 70, 243, 20);
		pnlDireccion.add(txtDireccion);

		JLabel lblTelefono = new JLabel("Telèfono");
		lblTelefono.setBounds(343, 40, 51, 14);
		pnlDireccion.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(400, 40, 160, 20);
		pnlDireccion.add(txtTelefono);

		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(352, 10, 64, 14);
		pnlDireccion.add(lblCelular);

		txtCelular = new JTextField();
		txtCelular.setColumns(10);
		txtCelular.setBounds(400, 10, 160, 20);
		pnlDireccion.add(txtCelular);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(6, 100, 46, 14);
		pnlDireccion.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(65, 100, 243, 20);
		pnlDireccion.add(txtEmail);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(6, 10, 46, 14);
		pnlDireccion.add(lblCiudad);

		JLabel lblCodigoPostal = new JLabel("Còdigo Postal");
		lblCodigoPostal.setBounds(320, 70, 80, 14);
		pnlDireccion.add(lblCodigoPostal);

		txtCodigoPostal = new JTextField();
		txtCodigoPostal.setColumns(10);
		txtCodigoPostal.setBounds(400, 70, 160, 20);
		pnlDireccion.add(txtCodigoPostal);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(6, 40, 46, 14);
		pnlDireccion.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setToolTipText("Ingresar Princincipal, matriz o sucursal");
		txtNombre.setColumns(10);
		txtNombre.setBounds(65, 40, 243, 20);
		pnlDireccion.add(txtNombre);

		scroollPane = new JScrollPane();
		scroollPane.setBounds(10, 136, 550, 114);
		pnlDireccion.add(scroollPane);

		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(460, 100, 80, 22);
		pnlDireccion.add(btnAgregar);

		chkPorDefecto = new JCheckBox("Por Defecto");
		chkPorDefecto.setBounds(350, 100, 97, 23);
		pnlDireccion.add(chkPorDefecto);
		
		cmbCiudad = new JTextField();
		cmbCiudad.setEditable(false);
		cmbCiudad.setBounds(65, 7, 208, 20);
		pnlDireccion.add(cmbCiudad);
		cmbCiudad.setColumns(10);
		
		btnBuscarCiudad = new JButton("");		
		btnBuscarCiudad.setIcon(new ImageIcon(ProveedorCrudFrm.class.getResource("/ec/peleusi/utils/images/search_16.png")));
		btnBuscarCiudad.setBounds(280, 6, 43, 26);
		pnlDireccion.add(btnBuscarCiudad);
	}
}
