package ec.peleusi.views.windows;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import ec.peleusi.controllers.CompraController;
import ec.peleusi.controllers.CompraDetalleController;
import ec.peleusi.controllers.DireccionProveedorController;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.ProveedorController;
import ec.peleusi.controllers.SeteoController;
import ec.peleusi.controllers.SucursalController;
import ec.peleusi.controllers.UsuarioController;
import ec.peleusi.models.entities.Compra;
import ec.peleusi.models.entities.CompraDetalle;
import ec.peleusi.models.entities.DireccionProveedor;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.Proveedor;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.Sucursal;
import ec.peleusi.utils.Formatos;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JFormattedTextField;

import ec.peleusi.models.entities.Usuario;
import javax.swing.JComboBox;

public class CompraCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	public JTextField txtRuc;
	public JTextField txtContribuyente;
	public JTextField txtDireccion;
	public JTextField txtTelefono;
	private JTextField txtEstablecimiento;
	private JTextField txtAutorizacion;
	private JTextField txtPuntoEmision;
	private JTextField txtSecuencial;
	private JTextField txtCodigoProducto;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private JScrollPane scrollPane;
	private JFormattedTextField txtBaseImponibleIvaDiferente0;
	private JFormattedTextField txtDescuento;
	private JFormattedTextField txtMontoIce;
	private JFormattedTextField txtMontoIva;
	private JFormattedTextField txtTotal;
	private JButton btnBuscarProducto;
	public Double precioNetoFila = 0.0;
	public Double precioBrutoFila = 0.0;
	public Double valorDescuentoFila = 0.0;
	public Double subtotalFila = 0.0;
	public Double valorIvaFila = 0.0;
	public Double totalFila = 0.0;
	public Double cantidadFila = 1.0;
	public Double porcentajeDescuentoFila = 0.0;
	public double valorIceFila = 0.0;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnEliminar;
	private JButton btnCancelar;
	private JTable tblProductos;
	private Double subtotalIva0 = 0.0;
	private Double subtotalIvaDiferente0 = 0.0;
	private Double totalDescuento = 0.0;
	private Double totalIva = 0.0;
	private Double total = 0.0;
	private JFormattedTextField txtBaseImponibleIva0;
	private JDateChooser dtcFechaVencimiento;
	private JDateChooser dtcFechaRegistro;
	private JDateChooser dtcFechaEmision;
	private JFormattedTextField txtDiasCredito;
	private JButton btnBuscarProveedor;
	private ProveedorListModalFrm proveedorListModalFrm = new ProveedorListModalFrm();
	private ProductoListModalFrm productoListModalFrm = new ProductoListModalFrm();
	private Proveedor proveedor;
	private JDateChooser dtcFechaAutorizacion;
	private Sucursal sucursal;
	private Usuario usuario;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbUsuario;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbSucursal;

	public CompraCrudFrm() {

		crearControles();
		crearEventos();
		crearTabla();
		limpiarCampos();
		cargarUsuario();
		cargarSucursal();

	}

	private void limpiarCampos() {

		txtEstablecimiento.setText("");
		txtPuntoEmision.setText("");
		txtSecuencial.setText("");
		txtAutorizacion.setText("");
		dtcFechaEmision.setDate(new Date());
		dtcFechaRegistro.setDate(new Date());
		dtcFechaVencimiento.setDate(new Date());
		dtcFechaAutorizacion.setDate(new Date());
		txtRuc.setText("");
		txtContribuyente.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtBaseImponibleIva0.setText("0.0");
		txtBaseImponibleIvaDiferente0.setText("0.0");
		txtDescuento.setText("0.0");
		txtMontoIce.setText("0.0");
		txtMontoIva.setText("0.0");
		txtTotal.setText("0.0");
		txtDiasCredito.setText("0");
		txtCodigoProducto.requestFocus();

	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Código", "Nombre", "Cantidad", "Precio Bruto", "% Desc.", "Descuento",
				"Precio Neto", "Subtotal", "% Iva", "Valor Iva", "Stock", "% Ice", "Valor Ice", "Total" , "IdProducto"};
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 4 || columnIndex == 6
						|| columnIndex == 7 || columnIndex == 8 || columnIndex == 9 || columnIndex == 10
						|| columnIndex == 11 || columnIndex == 12 || columnIndex == 13 || columnIndex == 14) {
					return false;
				}
				return true;
			}

			@SuppressWarnings("unused")
			public boolean isCellVisible(int rowIndex, int columnIndex) {
				if (columnIndex == 0) {
					return false;
				}
				return true;

			}
		};
		tblProductos.setModel(modelo);
		tblProductos.setPreferredScrollableViewportSize(tblProductos.getPreferredSize());
		tblProductos.getTableHeader().setReorderingAllowed(true);
		// tblProductos.getColumnModel().getColumn(0).setPreferredWidth(1);
		tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
		tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(120);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(250);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(4).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(5).setPreferredWidth(60);
		tblProductos.getColumnModel().getColumn(6).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(7).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(8).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(9).setPreferredWidth(60);
		tblProductos.getColumnModel().getColumn(10).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(11).setMaxWidth(0);
		tblProductos.getColumnModel().getColumn(11).setMinWidth(0);
		tblProductos.getColumnModel().getColumn(11).setPreferredWidth(0);
		tblProductos.getColumnModel().getColumn(12).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(13).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(14).setPreferredWidth(80);
		tblProductos.getColumnModel().getColumn(15).setMaxWidth(0);
		tblProductos.getColumnModel().getColumn(15).setMinWidth(0);
		tblProductos.getColumnModel().getColumn(15).setPreferredWidth(0);
		scrollPane.setViewportView(tblProductos);
		filaDatos = new Object[cabecera.length];
	}

	public void modificarCantidad() {
		try {
			Integer fila = tblProductos.getSelectedRow();
			if (tblProductos.getSelectedRow() != -1 && tblProductos.getSelectedColumn() == 3) {
				cantidadFila = Double.parseDouble(tblProductos.getValueAt(fila, 3).toString());
				precioBrutoFila = Double.parseDouble(tblProductos.getValueAt(fila, 4).toString());
				valorDescuentoFila = Double.parseDouble(tblProductos.getValueAt(fila, 6).toString());
				precioNetoFila = precioBrutoFila - valorDescuentoFila;
				subtotalFila = precioNetoFila * cantidadFila;
				tblProductos.setValueAt(precioNetoFila, fila, 7);
				tblProductos.setValueAt(subtotalFila, fila, 8);
				valorIvaFila = subtotalFila * (Double.parseDouble(tblProductos.getValueAt(fila, 9).toString()) / 100);
				valorIceFila = subtotalFila * (Double.parseDouble(tblProductos.getValueAt(fila, 12).toString()) / 100);
				tblProductos.setValueAt(valorIvaFila, fila, 10);
				tblProductos.setValueAt(valorIvaFila, fila, 13);
				Double totalIva = valorIvaFila + subtotalFila + valorIceFila;
				tblProductos.setValueAt(totalIva, fila, 14);
				calcularTotales();

			}
			System.out.println("Fila " + cantidadFila);

		} catch (Exception e) {
		}
	}

	public void modificarDescuento() {

		try {
			Integer fila = tblProductos.getSelectedRow();
			if (tblProductos.getSelectedRow() != -1 && tblProductos.getSelectedColumn() == 5) {
				cantidadFila = Double.parseDouble(tblProductos.getValueAt(fila, 3).toString());
				precioBrutoFila = Double.parseDouble(tblProductos.getValueAt(fila, 4).toString());
				porcentajeDescuentoFila = Double.parseDouble(tblProductos.getValueAt(fila, 5).toString());
				valorDescuentoFila = precioBrutoFila * (porcentajeDescuentoFila / 100);
				tblProductos.setValueAt(valorDescuentoFila, fila, 6);
				precioNetoFila = precioBrutoFila - valorDescuentoFila;
				subtotalFila = precioNetoFila * cantidadFila;
				tblProductos.setValueAt(precioNetoFila, fila, 7);
				tblProductos.setValueAt(subtotalFila, fila, 8);
				valorIvaFila = subtotalFila * (Double.parseDouble(tblProductos.getValueAt(fila, 9).toString()) / 100);
				valorIceFila = subtotalFila * (Double.parseDouble(tblProductos.getValueAt(fila, 12).toString()) / 100);
				tblProductos.setValueAt(valorIvaFila, fila, 10);
				tblProductos.setValueAt(valorIvaFila, fila, 13);
				Double totalIva = valorIvaFila + subtotalFila + valorIceFila;
				tblProductos.setValueAt(totalIva, fila, 14);
				calcularTotales();

			}
			System.out.println("Fila " + cantidadFila);

		} catch (Exception e) {
		}
	}

	private void agregarProductosAFila() {
		try {

			Producto producto = new Producto();
			ProductoController productoController = new ProductoController();
			producto = productoController.getProductoCodigo(txtCodigoProducto.getText());

			filaDatos[0] = "1";
			filaDatos[1] = producto.getCodigo();
			filaDatos[2] = producto.getNombre();
			filaDatos[3] = 1;
			filaDatos[4] = producto.getCosto();
			precioBrutoFila = Double.parseDouble(filaDatos[4].toString());
			filaDatos[5] = "0";
			filaDatos[6] = precioBrutoFila * (Double.parseDouble(filaDatos[5].toString()) / 100);
			valorDescuentoFila = Double.parseDouble(filaDatos[6].toString());
			precioNetoFila = precioBrutoFila - valorDescuentoFila;
			subtotalFila = precioNetoFila * Double.parseDouble(filaDatos[3].toString());
			filaDatos[7] = precioNetoFila;
			filaDatos[8] = subtotalFila;
			filaDatos[9] = cargarPorcentajeIvaProducto(producto.getTieneIva());
			valorIvaFila = subtotalFila * (Double.parseDouble(filaDatos[9].toString()) / 100);
			filaDatos[10] = valorIvaFila;
			filaDatos[11] = 0;
			filaDatos[12] = 0;
			valorIceFila = subtotalFila * (Double.parseDouble(filaDatos[12].toString()) / 100);
			filaDatos[13] = valorIceFila;
			filaDatos[14] = valorIvaFila + subtotalFila + valorIceFila;
			filaDatos[15]=producto;
			modelo.addRow(filaDatos);
			System.out.println(modelo);
			tblProductos.setModel(modelo);
			calcularTotales();

		} catch (Exception e) {
		}

	}

	private Double cargarPorcentajeIvaProducto(Boolean productoConIva) {

		double tarifaIva = 0;
		SeteoController seteoController = new SeteoController();
		List<Seteo> listaSeteo;
		listaSeteo = seteoController.seteoList();
		if (productoConIva == true)
			tarifaIva = listaSeteo.get(0).getTarifaIva().getPorcentaje();
		else
			tarifaIva = 0;
		return tarifaIva;

	}

	private void calcularTotales() {

		subtotalIvaDiferente0 = 0.0;
		subtotalIva0 = 0.0;
		totalIva = 0.0;
		totalDescuento = 0.0;
		total = 0.0;

		for (int i = 0; i < tblProductos.getRowCount(); i++) {

			if (Double.parseDouble(modelo.getValueAt(i, 9).toString()) != 0) {
				subtotalIvaDiferente0 = subtotalIvaDiferente0 + Double.parseDouble(modelo.getValueAt(i, 8).toString());
			} else {
				subtotalIva0 = subtotalIva0 + Double.parseDouble(modelo.getValueAt(i, 8).toString());
			}

			totalIva = totalIva + Double.parseDouble(modelo.getValueAt(i, 10).toString());
			totalDescuento = totalDescuento + Double.parseDouble(modelo.getValueAt(i, 6).toString());
			total = total + Double.parseDouble(modelo.getValueAt(i, 14).toString());

		}
		txtBaseImponibleIva0.setText(subtotalIva0.toString());
		txtBaseImponibleIvaDiferente0.setText(subtotalIvaDiferente0.toString());
		txtDescuento.setText(totalDescuento.toString());
		txtMontoIva.setText(totalIva.toString());
		txtTotal.setText(total.toString());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarUsuario() {
		usuario = new Usuario();
		UsuarioController usuarioController = new UsuarioController();
		List<Usuario> listaUsuario;
		listaUsuario = usuarioController.usuarioList();
		cmbUsuario.setModel(new DefaultComboBoxModel(listaUsuario.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarSucursal() {
		sucursal = new Sucursal();
		SucursalController sucursalController = new SucursalController();
		List<Sucursal> listaSucursal;
		listaSucursal = sucursalController.SucursalList();
		cmbSucursal.setModel(new DefaultComboBoxModel(listaSucursal.toArray()));
	}

	public void calcularFechaVencimiento() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dtcFechaEmision.getDate());
		cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(txtDiasCredito.getText()));
		dtcFechaVencimiento.setDate(cal.getTime());
	}

	private void buscarProveedoridentificacion(String identificacion) {
		ProveedorController proveedoersonaController = new ProveedorController();

		proveedor = new Proveedor();
		proveedor = proveedoersonaController.getProveedorIdentificacion(identificacion);
		if (proveedor != null) {
			System.out.println("Categoría seleccionado: " + proveedor);
			txtRuc.setText(proveedor.getIdentificacion());
			txtContribuyente.setText(proveedor.getRazonSocial());
			cargarDireccionProveedorPorDefecto(proveedor);
		} else {

			txtContribuyente.setText("");
			txtDireccion.setText("");
			txtTelefono.setText("");
			llamarVentanaProveedor();

		}

	}

	private void cargarDireccionProveedorPorDefecto(Proveedor proveedor) {
		DireccionProveedorController direccionProvedorController = new DireccionProveedorController();
		DireccionProveedor direccionProveedor = new DireccionProveedor();
		direccionProveedor = direccionProvedorController.getDireccionProveedorPorDefecto(proveedor);
		if (direccionProveedor != null) {
			txtDireccion.setText(direccionProveedor.getNombre());
			txtTelefono.setText(direccionProveedor.getTelefono());
		} else {
			txtDireccion.setText("");
			txtTelefono.setText("");
		}
	}

	private void llamarVentaProductos() {

		if (!productoListModalFrm.isVisible()) {
			productoListModalFrm.setModal(true);
			productoListModalFrm.setVisible(true);
		}

	}

	private void llamarVentanaProveedor() {
		if (!proveedorListModalFrm.isVisible()) {
			proveedorListModalFrm.setModal(true);
			proveedorListModalFrm.setVisible(true);
		}

		proveedor = proveedorListModalFrm.getProveedor();

		if (proveedor != null) {
			System.out.println("Categoría seleccionado: " + proveedor);

			txtRuc.setText(proveedor.getIdentificacion());
			txtContribuyente.setText(proveedor.getRazonSocial());
			cargarDireccionProveedorPorDefecto(proveedor);

		}

	}

	public void crearEventos() {
		btnBuscarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				llamarVentaProductos();
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarCampos();
			}
		});
		dtcFechaEmision.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				calcularFechaVencimiento();
			}
		});

		txtDiasCredito.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				calcularFechaVencimiento();
			}
		});

		btnBuscarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llamarVentanaProveedor();

			}

		});

		proveedorListModalFrm.addConfirmListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				proveedor = new Proveedor();
				proveedor = proveedorListModalFrm.getProveedor();

			}
		});
		txtCodigoProducto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				char cTeclaPrecionada = arg0.getKeyChar();
				if (cTeclaPrecionada == KeyEvent.VK_ENTER) {
					agregarProductosAFila();
				}
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				double baseImponibleExentoIva = 0.0;
				double baseImponibleNoObjetoIva = 0.0;
				Boolean estado = false;
				Compra compra = new Compra();
				CompraController compraController = new CompraController();
				Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
				Sucursal sucursal = (Sucursal) cmbSucursal.getSelectedItem();

				compra.setBaseImponibleExentoIva(baseImponibleExentoIva);
				compra.setBaseImponibleIva0(Double.parseDouble(txtBaseImponibleIva0.getText()));
				compra.setBaseImponibleIvaDiferente0(Double.parseDouble(txtBaseImponibleIvaDiferente0.getText()));
				compra.setBaseImponibleNoObjetoIva(baseImponibleNoObjetoIva);
				compra.setDiasCredito(Double.parseDouble(txtDiasCredito.getText()));
				compra.setEstablecimiento(txtEstablecimiento.getText());
				compra.setEstado(estado);
				compra.setFechaAutorizacion(dtcFechaAutorizacion.getDate());
				compra.setFechaEmision(dtcFechaEmision.getDate());
				compra.setFechaRegistro(dtcFechaRegistro.getDate());
				compra.setFechaVencimiento(dtcFechaVencimiento.getDate());
				compra.setMontoIce(Double.parseDouble(txtMontoIce.getText()));
				compra.setMontoIva(Double.parseDouble(txtMontoIva.getText()));
				compra.setNumeroAutorizacion(txtAutorizacion.getText());
				compra.setPuntoEmision(txtPuntoEmision.getText());
				compra.setSecuencial(txtSecuencial.getText());
				compra.setValorDescuento(Double.parseDouble(txtDescuento.getText()));
				compra.setProveedor(proveedor);
				compra.setSucursal(sucursal);
				compra.setUsuario(usuario);
				compraController.createCompra(compra);

				for (int filaDetalle = 0; filaDetalle < tblProductos.getRowCount(); filaDetalle++) {
					
					CompraDetalle compraDetalle= new CompraDetalle();
					CompraDetalleController compraDetalleController= new CompraDetalleController();
					compraDetalle.setStock(Double.parseDouble(modelo.getValueAt(filaDetalle, 11).toString()));
					compraDetalle.setCodigoProducto(modelo.getValueAt(filaDetalle, 1).toString());					
					compraDetalle.setCostoProducto(Double.parseDouble(modelo.getValueAt(filaDetalle, 4).toString()));
					compraDetalle.setNombreProducto(modelo.getValueAt(filaDetalle, 2).toString());
					compraDetalle.setPorcentajeDescuentoProducto(Double.parseDouble(modelo.getValueAt(filaDetalle, 6).toString()));
					compraDetalle.setPorcentajeIceProducto(Double.parseDouble(modelo.getValueAt(filaDetalle, 12).toString()));
					compraDetalle.setPorcentajeIvaProducto(Double.parseDouble(modelo.getValueAt(filaDetalle, 9).toString()));
					compraDetalle.setSubtotal(Double.parseDouble(modelo.getValueAt(filaDetalle, 8).toString()));
					compraDetalle.setValorDescuentoProducto(Double.parseDouble(modelo.getValueAt(filaDetalle, 6).toString()));
					compraDetalle.setValorIceProducto(Double.parseDouble(modelo.getValueAt(filaDetalle, 13).toString()));
					compraDetalle.setValorIvaProducto(Double.parseDouble(modelo.getValueAt(filaDetalle, 10).toString()));
					compraDetalle.setCompra(compra);
					Producto producto= (Producto) modelo.getValueAt(filaDetalle, 15);
					compraDetalle.setProducto(producto);					
					compraDetalleController.createCompra(compraDetalle);					

				}

			}
		});
	}

	public void crearControles() {
		setBounds(100, 100, 1217, 579);
		setTitle("Compra de productos");
		setClosable(true);
		setIconifiable(true);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);

		btnNuevo = new JButton("Nuevo");

		btnNuevo.setIcon(new ImageIcon(CompraCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panel.add(btnNuevo);

		btnGuardar = new JButton("Guardar");

		btnGuardar.setIcon(new ImageIcon(CompraCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panel.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(CompraCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panel.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");

		btnCancelar.setIcon(new ImageIcon(CompraCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos proveedor",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_2.setBounds(10, 11, 646, 104);
		panel_1.add(panel_2);
		panel_2.setLayout(null);

		txtRuc = new JTextField();
		txtRuc.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				buscarProveedoridentificacion(txtRuc.getText());

			}
		});
		txtRuc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

				char cTeclaPrecionada = e.getKeyChar();
				if (cTeclaPrecionada == KeyEvent.VK_ENTER) {
					buscarProveedoridentificacion(txtRuc.getText());
				}
			}
		});
		txtRuc.setBounds(10, 33, 153, 20);
		panel_2.add(txtRuc);
		txtRuc.setColumns(10);

		JLabel lblContribuyente = new JLabel("Razón Social");
		lblContribuyente.setBounds(199, 18, 94, 14);
		panel_2.add(lblContribuyente);

		JLabel lblNewLabel = new JLabel("Ruc/CI:");
		lblNewLabel.setBounds(10, 18, 70, 14);
		panel_2.add(lblNewLabel);

		txtContribuyente = new JTextField();
		txtContribuyente.setEditable(false);
		txtContribuyente.setColumns(10);
		txtContribuyente.setBounds(199, 33, 428, 20);
		panel_2.add(txtContribuyente);

		txtDireccion = new JTextField();
		txtDireccion.setEditable(false);
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(199, 70, 428, 20);
		panel_2.add(txtDireccion);

		JLabel lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(10, 55, 70, 14);
		panel_2.add(lblTelfono);

		txtTelefono = new JTextField();
		txtTelefono.setEditable(false);
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(10, 70, 153, 20);
		panel_2.add(txtTelefono);

		JLabel lblDireccin = new JLabel("Dirección:");
		lblDireccin.setBounds(199, 55, 94, 14);
		panel_2.add(lblDireccin);

		btnBuscarProveedor = new JButton("");

		btnBuscarProveedor.setVerticalAlignment(SwingConstants.TOP);
		btnBuscarProveedor
				.setIcon(new ImageIcon(CompraCrudFrm.class.getResource("/ec/peleusi/utils/images/folder_user.png")));
		btnBuscarProveedor.setBounds(165, 31, 24, 26);
		panel_2.add(btnBuscarProveedor);

		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos factura",
				TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_3.setBounds(666, 11, 525, 135);
		panel_1.add(panel_3);

		txtEstablecimiento = new JTextField();
		txtEstablecimiento.setColumns(10);
		txtEstablecimiento.setBounds(10, 33, 81, 20);
		panel_3.add(txtEstablecimiento);

		JLabel lblProveedor = new JLabel("Fecha Registro");
		lblProveedor.setBounds(10, 55, 97, 14);
		panel_3.add(lblProveedor);

		JLabel lblN = new JLabel("Establecimiento");
		lblN.setBounds(10, 18, 97, 14);
		panel_3.add(lblN);

		JLabel lblNroAutorizacin = new JLabel("Autorización:");
		lblNroAutorizacin.setBounds(230, 55, 89, 14);
		panel_3.add(lblNroAutorizacin);

		txtAutorizacion = new JTextField();
		txtAutorizacion.setColumns(10);
		txtAutorizacion.setBounds(230, 70, 220, 20);
		panel_3.add(txtAutorizacion);

		JLabel lblPuntoEmision = new JLabel("Punto Emisión");
		lblPuntoEmision.setBounds(106, 18, 97, 14);
		panel_3.add(lblPuntoEmision);

		txtPuntoEmision = new JTextField();
		txtPuntoEmision.setColumns(10);
		txtPuntoEmision.setBounds(101, 33, 81, 20);
		panel_3.add(txtPuntoEmision);

		JLabel lblSecuencial = new JLabel("Secuencial");
		lblSecuencial.setBounds(201, 18, 89, 14);
		panel_3.add(lblSecuencial);

		txtSecuencial = new JTextField();
		txtSecuencial.setBounds(194, 33, 256, 20);
		panel_3.add(txtSecuencial);
		txtSecuencial.setColumns(10);

		dtcFechaRegistro = new JDateChooser();
		dtcFechaRegistro.setBounds(10, 70, 97, 20);
		panel_3.add(dtcFechaRegistro);

		JLabel lblFechaEmisin = new JLabel("Fecha Emisión");
		lblFechaEmisin.setBounds(124, 55, 97, 14);
		panel_3.add(lblFechaEmisin);

		dtcFechaEmision = new JDateChooser();

		dtcFechaEmision.setBounds(117, 70, 97, 20);
		panel_3.add(dtcFechaEmision);

		JLabel lblFechaVencimiento = new JLabel("Fecha Vencimiento");
		lblFechaVencimiento.setBounds(124, 92, 97, 14);
		panel_3.add(lblFechaVencimiento);

		dtcFechaVencimiento = new JDateChooser();
		dtcFechaVencimiento.setBounds(117, 107, 97, 20);
		panel_3.add(dtcFechaVencimiento);

		JLabel lblDiasCrdito = new JLabel("Dias Crédito");
		lblDiasCrdito.setBounds(10, 92, 97, 14);
		panel_3.add(lblDiasCrdito);

		txtDiasCredito = new JFormattedTextField();

		txtDiasCredito.setBounds(10, 107, 71, 20);
		txtDiasCredito.setFormatterFactory(new Formatos().getIntegerFormat());
		panel_3.add(txtDiasCredito);

		JLabel label = new JLabel("Fecha Registro");
		label.setBounds(237, 92, 97, 14);
		panel_3.add(label);

		dtcFechaAutorizacion = new JDateChooser();
		dtcFechaAutorizacion.setBounds(230, 107, 97, 20);
		panel_3.add(dtcFechaAutorizacion);

		JLabel lblProducto = new JLabel("Producto:");
		lblProducto.setBounds(20, 126, 70, 14);
		panel_1.add(lblProducto);

		txtCodigoProducto = new JTextField();

		txtCodigoProducto.setPreferredSize(new Dimension(420, 38));
		txtCodigoProducto.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtCodigoProducto.setBounds(90, 117, 515, 29);
		panel_1.add(txtCodigoProducto);

		btnBuscarProducto = new JButton("");
		btnBuscarProducto
				.setIcon(new ImageIcon(CompraCrudFrm.class.getResource("/ec/peleusi/utils/images/search_16.png")));
		btnBuscarProducto.setBounds(605, 117, 28, 29);
		panel_1.add(btnBuscarProducto);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 154, 926, 253);
		panel_1.add(scrollPane);

		tblProductos = new JTable();
		scrollPane.setViewportView(tblProductos);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(946, 157, 245, 312);
		panel_1.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblBaseImponibleIva = new JLabel("Base Imp.  Iva 0%");
		lblBaseImponibleIva.setForeground(Color.BLUE);
		lblBaseImponibleIva.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBaseImponibleIva.setBounds(10, 11, 201, 14);
		panel_4.add(lblBaseImponibleIva);

		txtBaseImponibleIva0 = new JFormattedTextField();
		txtBaseImponibleIva0.setHorizontalAlignment(SwingConstants.RIGHT);
		txtBaseImponibleIva0.setText("0");
		txtBaseImponibleIva0.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtBaseImponibleIva0.setBounds(10, 26, 201, 29);
		panel_4.add(txtBaseImponibleIva0);
		txtBaseImponibleIva0.setColumns(10);
		txtBaseImponibleIva0.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblBaseImponibleDiferente = new JLabel("Base Imp.  Iva Diferente  0%");
		lblBaseImponibleDiferente.setForeground(Color.BLUE);
		lblBaseImponibleDiferente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBaseImponibleDiferente.setBounds(10, 58, 225, 14);
		panel_4.add(lblBaseImponibleDiferente);

		txtBaseImponibleIvaDiferente0 = new JFormattedTextField();
		txtBaseImponibleIvaDiferente0.setHorizontalAlignment(SwingConstants.RIGHT);
		txtBaseImponibleIvaDiferente0.setText("0");
		txtBaseImponibleIvaDiferente0.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtBaseImponibleIvaDiferente0.setBounds(10, 74, 201, 29);
		panel_4.add(txtBaseImponibleIvaDiferente0);
		txtBaseImponibleIvaDiferente0.setColumns(10);
		txtBaseImponibleIvaDiferente0.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblMontoIva = new JLabel("Descuento");
		lblMontoIva.setForeground(Color.BLUE);
		lblMontoIva.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMontoIva.setBounds(10, 104, 201, 14);
		panel_4.add(lblMontoIva);

		txtDescuento = new JFormattedTextField();
		txtDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
		txtDescuento.setText("0");
		txtDescuento.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtDescuento.setBounds(10, 119, 201, 29);
		panel_4.add(txtDescuento);
		txtDescuento.setColumns(10);
		txtDescuento.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblMontoIce = new JLabel("Monto ICE");
		lblMontoIce.setForeground(Color.BLUE);
		lblMontoIce.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMontoIce.setBounds(10, 150, 201, 14);
		panel_4.add(lblMontoIce);

		JLabel lblMontoIva_1 = new JLabel("Monto IVA");
		lblMontoIva_1.setForeground(Color.BLUE);
		lblMontoIva_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMontoIva_1.setBounds(10, 195, 201, 14);
		panel_4.add(lblMontoIva_1);

		txtMontoIce = new JFormattedTextField();
		txtMontoIce.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMontoIce.setText("0");
		txtMontoIce.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtMontoIce.setBounds(10, 165, 201, 29);
		panel_4.add(txtMontoIce);
		txtMontoIce.setColumns(10);
		txtMontoIce.setFormatterFactory(new Formatos().getDecimalFormat());

		txtMontoIva = new JFormattedTextField();
		txtMontoIva.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMontoIva.setText("0");
		txtMontoIva.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtMontoIva.setBounds(10, 211, 201, 29);
		panel_4.add(txtMontoIva);
		txtMontoIva.setColumns(10);
		txtMontoIva.setFormatterFactory(new Formatos().getDecimalFormat());

		JLabel lblTotal = new JLabel("TOTAL");
		lblTotal.setForeground(Color.BLUE);
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotal.setBounds(10, 242, 201, 14);
		panel_4.add(lblTotal);

		txtTotal = new JFormattedTextField();
		txtTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotal.setText("0");
		txtTotal.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtTotal.setBounds(10, 258, 201, 29);
		panel_4.add(txtTotal);
		txtTotal.setColumns(10);
		txtTotal.setFormatterFactory(new Formatos().getDecimalFormat());

		cmbUsuario = new JComboBox<Usuario>();
		cmbUsuario.setBounds(63, 429, 137, 20);
		panel_1.add(cmbUsuario);

		cmbSucursal = new JComboBox<Sucursal>();
		cmbSucursal.setBounds(238, 429, 137, 20);
		panel_1.add(cmbSucursal);

		CellEditorListener changeNotification = new CellEditorListener() {
			public void editingStopped(ChangeEvent e) {
				if (tblProductos.getSelectedColumn() == 3) {
					modificarCantidad();
				} else {
					if (tblProductos.getSelectedColumn() == 5) {
						modificarDescuento();
					}
				}
			}

			public void editingCanceled(ChangeEvent arg0) {
			}
		};
		tblProductos.getDefaultEditor(String.class).addCellEditorListener(changeNotification);
	}
}
