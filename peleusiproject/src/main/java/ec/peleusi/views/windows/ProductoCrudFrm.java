package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Panel;
import javax.swing.border.TitledBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.filechooser.FileNameExtensionFilter;

import ec.peleusi.controllers.ProductoPrecioController;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.SeteoController;
import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.controllers.TipoGastoDeducibleController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.models.entities.ProductoPrecio;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.TarifaIce;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.models.entities.TipoGastoDeducible;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.models.entities.UnidadMedida;
import ec.peleusi.utils.Formatos;
import ec.peleusi.utils.UnidadMedidaPesoEnum;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ProductoCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTabbedPane tpnlProductos;
	private JPanel pnlConfigPrecios;
	private JPanel pnlDatosGenerales;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtCategoriaProducto;
	private JFormattedTextField txtStockMinimo;
	private JButton btnBuscarCategoria;
	private JFormattedTextField txtPeso;
	private JComboBox<UnidadMedidaPesoEnum> cmbUnidadMedidaPeso;
	private JCheckBox chkSePuedeFraccionar;
	private JCheckBox chkManejaInventario;
	private JCheckBox chkEsDeducible;
	private JComboBox<UnidadMedida> cmbUnidadMedidaCompra;
	private JComboBox<UnidadMedida> cmbUnidadMedidaVenta;
	private JFormattedTextField txtCantidadCompra;
	private JFormattedTextField txtCantidadVenta;
	private JComboBox<TipoGastoDeducible> cmbTipoGastoDeducible;
	private CategoriaProductoListModalFrm categoriaProductoListModalFrm = new CategoriaProductoListModalFrm();
	private CategoriaProducto categoriaProducto;
	private JLabel lblFoto;
	private JButton btnSeleccionar;
	private JTextField txtImagen;
	private JFormattedTextField txtCostoUnitario;
	private JLabel lblPrecios;
	private DefaultTableModel modeloPreciosUnitario;
	private DefaultTableModel modeloPreciosLote;
	private Object[] filaDatos;
	private JTable tblPreciosUnitario;
	private JTable tblPreciosLote;
	List<TipoPrecio> listaTipoPrecio;
	Double porcentaje = 0.0;
	Double subtotal = 0.0;
	Double iva = 0.0;
	Double ice = 0.0;
	Double total = 0.0;
	Double utilidad = 0.0;
	private TarifaIva tarifaIva = new TarifaIva();
	private TarifaIce tarifaIce = new TarifaIce();
	private JComboBox<TarifaIce> cmbIce;
	private JComboBox<TarifaIva> cmbIva;
	private JLabel label_1;
	private JLabel lblCostoLote;
	private JFormattedTextField txtCostoLote;
	private JLabel lblPrecioDeCompra;
	private JFormattedTextField txtCostoCompra;
	private ProductoPrecio productoPrecio;
	Producto producto;
	String error;
	private JCheckBox chkTieneIva;

	public ProductoCrudFrm() {
		setTitle("Productos");
		crearControles();
		crearEventos();
		cargarCombosUnidaMedida();
		cargarComboTarifaIva();
		cargarComboTarifaIce();
		cargarComboTipoGastoDeducible();
		limpiarCampos();
		crearTablaPrecios();
		tarifaIva = (TarifaIva) cmbIva.getSelectedItem();
		tarifaIce = (TarifaIce) cmbIce.getSelectedItem();
		Double costoProducto = Double.parseDouble(txtCostoUnitario.getText());
		cargarTablaConValores(modeloPreciosUnitario, costoProducto, tarifaIva, tarifaIce);
		cargarTablaConValores(modeloPreciosLote, costoProducto, tarifaIva, tarifaIce);
		/*
		 * if (costoProducto != 0) { cargarTabla(costoProducto, tarifaIva,
		 * tarifaIce); } else { tpnlProductos.setEnabledAt(1, false); }
		 */
	}

	private void crearTablaPrecios() {
		Object[] cabecera = { "Tipo Precio", "% Util.", "Subtotal", "ICE", "IVA", "Total", "Utilidad" };
		final boolean[] canEdit = new boolean[] { false, true, false, false, false, true, false };

		modeloPreciosUnitario = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;
			/*
			 * @SuppressWarnings("rawtypes") Class[] types = new Class[] {
			 * java.lang.String.class, java.lang.Double.class,
			 * java.lang.Double.class, java.lang.Double.class,
			 * java.lang.Double.class, java.lang.Double.class };
			 */

			/*
			 * @Override public boolean isCellEditable(int rowIndex, int
			 * columnIndex) { if (columnIndex == 0 || columnIndex == 2 ||
			 * columnIndex == 3 || columnIndex == 4 || columnIndex == 6) {
			 * return false; } return true; }
			 */

			/*
			 * @SuppressWarnings({ "unchecked", "rawtypes" })
			 * 
			 * @Override public Class getColumnClass(int columnIndex) { return
			 * types[columnIndex]; }
			 */
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		tblPreciosUnitario.setModel(modeloPreciosUnitario);
		tblPreciosUnitario.getTableHeader().setReorderingAllowed(false);
		tblPreciosUnitario.getColumnModel().getColumn(0).setPreferredWidth(130);

		modeloPreciosLote = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		tblPreciosLote.setModel(modeloPreciosLote);
		tblPreciosLote.getTableHeader().setReorderingAllowed(false);
		tblPreciosLote.getColumnModel().getColumn(0).setPreferredWidth(130);

		filaDatos = new Object[cabecera.length];

	}

	private void cargarTablaConValores(DefaultTableModel modelo, Double costoProducto, TarifaIva tarifaIva,
			TarifaIce tarifaIce) {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		listaTipoPrecio = tipoPrecioController.tipoPrecioList();
		for (TipoPrecio tipoPrecio : listaTipoPrecio) {
			porcentaje = tipoPrecio.getPorcentaje();
			calcularFila(costoProducto, porcentaje, tarifaIva, tarifaIce);
			filaDatos[0] = tipoPrecio.getNombre();
			filaDatos[1] = porcentaje;
			filaDatos[2] = subtotal;
			filaDatos[3] = iva;
			filaDatos[4] = ice;
			filaDatos[5] = total;
			filaDatos[6] = utilidad;
			modelo.addRow(filaDatos);
		}

	}

	private void calcularFila(Double costoProducto, Double porcentajeAplicar, TarifaIva tarifaIva,
			TarifaIce tarifaIce) {
		if (tarifaIva == null || tarifaIce == null)
			return;
		subtotal = (costoProducto * (porcentaje / 100)) + costoProducto;
		ice = subtotal * (tarifaIce.getPorcentaje() / 100);
		iva = (subtotal + ice) * (tarifaIva.getPorcentaje() / 100);
		total = subtotal + ice + iva;
		utilidad = subtotal - costoProducto;
	}

	private void calcularFilaInversa(Double total, TarifaIva tarifaIva, TarifaIce tarifaIce, Double costoProducto) {
		Double subtotalConIva = total / ((tarifaIva.getPorcentaje() / 100) + 1);
		iva = total - subtotalConIva;
		Double subtotalConIce = subtotalConIva / ((tarifaIce.getPorcentaje() / 100) + 1);
		ice = subtotalConIva - subtotalConIce;
		subtotal = subtotalConIce;
		porcentaje = ((subtotal - costoProducto) / costoProducto) * 100;
		utilidad = subtotal - costoProducto;
	}

	private void actualizarValoresEnTabla(DefaultTableModel modelo, JTable table, Double costo, int fila) {
		int inicio = 0;
		if (fila == -1) {
			fila = table.getRowCount() - 1;
		} else {
			inicio = fila;
		}
		for (int i = inicio; i <= fila; i++) {
			porcentaje = Double.parseDouble(modelo.getValueAt(i, 1).toString());
			tarifaIva = (TarifaIva) cmbIva.getSelectedItem();
			tarifaIce = (TarifaIce) cmbIce.getSelectedItem();
			calcularFila(costo, porcentaje, tarifaIva, tarifaIce);
			modelo.setValueAt(subtotal, i, 2);
			modelo.setValueAt(ice, i, 3);
			modelo.setValueAt(iva, i, 4);
			modelo.setValueAt(total, i, 5);
			modelo.setValueAt(utilidad, i, 6);
		}

	}

	private void actualizarValoresEnTablaInversa(DefaultTableModel modelo, JTable table, int fila,
			Double costoProducto) {
		int inicio = 0;
		if (fila == -1) {
			fila = table.getRowCount() - 1;
		} else {
			inicio = fila;
		}
		for (int i = inicio; i <= fila; i++) {
			total = Double.parseDouble(modelo.getValueAt(i, 5).toString());
			tarifaIva = (TarifaIva) cmbIva.getSelectedItem();
			tarifaIce = (TarifaIce) cmbIce.getSelectedItem();
			calcularFilaInversa(total, tarifaIva, tarifaIce, costoProducto);
			modelo.setValueAt(porcentaje, i, 1);
			modelo.setValueAt(subtotal, i, 2);
			modelo.setValueAt(ice, i, 3);
			modelo.setValueAt(iva, i, 4);
			modelo.setValueAt(utilidad, i, 6);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarComboTipoGastoDeducible() {
		TipoGastoDeducibleController tipoGastoDeducibleController = new TipoGastoDeducibleController();
		List<TipoGastoDeducible> listaTipoGastoDeducibleController;
		listaTipoGastoDeducibleController = tipoGastoDeducibleController.tipoGastoDeducibleList();
		cmbTipoGastoDeducible.setModel(new DefaultComboBoxModel(listaTipoGastoDeducibleController.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarComboTarifaIce() {
		TarifaIceController tarifaIceController = new TarifaIceController();
		List<TarifaIce> listaTarifaIce;
		listaTarifaIce = tarifaIceController.tarifaIceList();
		cmbIce.setModel(new DefaultComboBoxModel(listaTarifaIce.toArray()));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarComboTarifaIva() {
		SeteoController seteoController = new SeteoController();
		List<Seteo> listaSeteo;
		listaSeteo = seteoController.seteoList();
		tarifaIva = listaSeteo.get(0).getTarifaIva();
		List<TarifaIva> listaTarifaIva = new ArrayList<TarifaIva>();
		listaTarifaIva.add(tarifaIva);
		cmbIva.setModel(new DefaultComboBoxModel(listaTarifaIva.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarComboTarifaIvaCero() {
		tarifaIva = new TarifaIva();
		tarifaIva.setNombre("IVA 0%");
		tarifaIva.setPorcentaje(0.0);
		List<TarifaIva> listaTarifaIva = new ArrayList<TarifaIva>();
		listaTarifaIva.add(tarifaIva);
		cmbIva.setModel(new DefaultComboBoxModel(listaTarifaIva.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarCombosUnidaMedida() {
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		List<UnidadMedida> listaUnidadMedida;
		listaUnidadMedida = unidadMedidaController.UnidadMedidaList();
		cmbUnidadMedidaCompra.setModel(new DefaultComboBoxModel(listaUnidadMedida.toArray()));
		cmbUnidadMedidaVenta.setModel(new DefaultComboBoxModel(listaUnidadMedida.toArray()));
		cmbUnidadMedidaPeso.setModel(new DefaultComboBoxModel<UnidadMedidaPesoEnum>(UnidadMedidaPesoEnum.values()));
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 678, 419);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		tpnlProductos = new JTabbedPane(JTabbedPane.TOP);
		tpnlProductos.setBounds(10, 11, 644, 289);
		panelCuerpo.add(tpnlProductos);

		pnlDatosGenerales = new JPanel();
		tpnlProductos.addTab("Datos Generales", null, pnlDatosGenerales, null);
		pnlDatosGenerales.setLayout(null);

		JLabel lblCdigo = new JLabel("Código*");
		lblCdigo.setBounds(10, 11, 68, 14);
		pnlDatosGenerales.add(lblCdigo);

		JLabel lblNombre = new JLabel("Nombre*");
		lblNombre.setBounds(248, 11, 58, 14);
		pnlDatosGenerales.add(lblNombre);

		JLabel lblPeso = new JLabel("Peso");
		lblPeso.setBounds(10, 67, 46, 14);
		pnlDatosGenerales.add(lblPeso);

		chkEsDeducible = new JCheckBox("Es deducible");
		chkEsDeducible.setBounds(455, 152, 161, 23);
		pnlDatosGenerales.add(chkEsDeducible);

		chkSePuedeFraccionar = new JCheckBox("Se puede fraccionar");
		chkSePuedeFraccionar.setBounds(455, 100, 161, 23);
		pnlDatosGenerales.add(chkSePuedeFraccionar);

		chkManejaInventario = new JCheckBox("Maneja Inventario");
		chkManejaInventario.setToolTipText(
				"Ej: Los servicios no manejan inventarios porque su cantidad no tienes un stock fijo sino es ilimitado ");
		chkManejaInventario.setBounds(455, 126, 161, 23);
		pnlDatosGenerales.add(chkManejaInventario);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(82, 8, 156, 20);
		pnlDatosGenerales.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setText("");
		txtNombre.setBounds(310, 8, 306, 20);
		pnlDatosGenerales.add(txtNombre);
		txtNombre.setColumns(10);

		txtPeso = new JFormattedTextField();
		txtPeso.setFormatterFactory(new Formatos().getDecimalFormat());
		txtPeso.setBounds(82, 64, 86, 20);
		pnlDatosGenerales.add(txtPeso);

		JLabel lblCategora = new JLabel("Categoría*");
		lblCategora.setBounds(10, 36, 68, 14);
		pnlDatosGenerales.add(lblCategora);

		txtCategoriaProducto = new JTextField();
		txtCategoriaProducto.setEditable(false);
		txtCategoriaProducto.setBounds(82, 36, 478, 20);
		pnlDatosGenerales.add(txtCategoriaProducto);
		txtCategoriaProducto.setColumns(10);

		btnBuscarCategoria = new JButton("");
		btnBuscarCategoria
				.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/search_16.png")));
		btnBuscarCategoria.setBounds(570, 33, 46, 23);
		pnlDatosGenerales.add(btnBuscarCategoria);

		JLabel lblStockMnimo = new JLabel("Stock Mínimo");
		lblStockMnimo.setBounds(349, 67, 91, 14);
		pnlDatosGenerales.add(lblStockMnimo);

		txtStockMinimo = new JFormattedTextField();
		txtStockMinimo.setFormatterFactory(new Formatos().getDecimalFormat());
		txtStockMinimo.setBounds(437, 64, 86, 20);
		pnlDatosGenerales.add(txtStockMinimo);
		txtStockMinimo.setColumns(10);

		cmbTipoGastoDeducible = new JComboBox<TipoGastoDeducible>();
		cmbTipoGastoDeducible.setBounds(455, 182, 161, 20);
		pnlDatosGenerales.add(cmbTipoGastoDeducible);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Medida en compra y Medida para la venta",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 96, 430, 154);
		pnlDatosGenerales.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSeCompraEn = new JLabel("Se compra en*");
		lblSeCompraEn.setBounds(10, 25, 88, 14);
		panel_1.add(lblSeCompraEn);

		cmbUnidadMedidaCompra = new JComboBox<UnidadMedida>();
		cmbUnidadMedidaCompra.setBounds(97, 22, 274, 20);
		panel_1.add(cmbUnidadMedidaCompra);

		JLabel lblContiene = new JLabel("Contiene*");
		lblContiene.setBounds(10, 48, 62, 14);
		panel_1.add(lblContiene);

		txtCantidadCompra = new JFormattedTextField();
		txtCantidadCompra.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				preciosCantidadFocusLost();
			}
		});
		txtCantidadCompra.setFormatterFactory(new Formatos().getDecimalFormat());
		txtCantidadCompra.setBounds(97, 50, 75, 20);
		panel_1.add(txtCantidadCompra);

		JLabel lblSeVendeEn = new JLabel("Se vende en*");
		lblSeVendeEn.setBounds(10, 82, 88, 14);
		panel_1.add(lblSeVendeEn);

		cmbUnidadMedidaVenta = new JComboBox<UnidadMedida>();
		cmbUnidadMedidaVenta.setBounds(97, 79, 274, 20);
		panel_1.add(cmbUnidadMedidaVenta);

		JLabel lblContiene_1 = new JLabel("Contiene*");
		lblContiene_1.setBounds(10, 112, 62, 14);
		panel_1.add(lblContiene_1);

		txtCantidadVenta = new JFormattedTextField();
		txtCantidadVenta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				preciosCantidadFocusLost();
			}
		});
		txtCantidadVenta.setFormatterFactory(new Formatos().getDecimalFormat());
		txtCantidadVenta.setBounds(97, 109, 75, 20);
		panel_1.add(txtCantidadVenta);

		lblPrecioDeCompra = new JLabel("Costo de compra*");
		lblPrecioDeCompra.setToolTipText("");
		lblPrecioDeCompra.setBounds(185, 53, 111, 14);
		panel_1.add(lblPrecioDeCompra);

		txtCostoCompra = new JFormattedTextField();
		txtCostoCompra.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				preciosCantidadFocusLost();
			}
		});
		txtCostoCompra.setFormatterFactory(new Formatos().getDecimalFormat());
		txtCostoCompra.setToolTipText("Precio sin impuestos");
		txtCostoCompra.setBounds(296, 53, 75, 20);
		panel_1.add(txtCostoCompra);

		cmbUnidadMedidaPeso = new JComboBox<UnidadMedidaPesoEnum>();
		cmbUnidadMedidaPeso.setBounds(178, 64, 161, 20);
		pnlDatosGenerales.add(cmbUnidadMedidaPeso);

		pnlConfigPrecios = new JPanel();
		tpnlProductos.addTab("Configuración de precios", null, pnlConfigPrecios, null);
		pnlConfigPrecios.setLayout(null);

		JLabel lblCostoUnitario = new JLabel("Costo Unitario");
		lblCostoUnitario.setBounds(10, 45, 92, 14);
		pnlConfigPrecios.add(lblCostoUnitario);

		txtCostoUnitario = new JFormattedTextField();
		txtCostoUnitario.setEditable(false);
		txtCostoUnitario.setFormatterFactory(new Formatos().getDecimalFormat());
		txtCostoUnitario.setBounds(102, 42, 86, 20);
		pnlConfigPrecios.add(txtCostoUnitario);

		lblPrecios = new JLabel("Precios Unitarios");
		lblPrecios.setBounds(263, 71, 164, 14);
		pnlConfigPrecios.add(lblPrecios);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 86, 609, 57);
		pnlConfigPrecios.add(scrollPane);

		final CustomCellRenderer renderer = new CustomCellRenderer();
		tblPreciosUnitario = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return renderer;
			}
		};
		CellEditorListener changeNotificationUnitario = new CellEditorListener() {
			public void editingStopped(ChangeEvent e) {
				if (tblPreciosUnitario.getSelectedColumn() == 5) {
					actualizarValoresEnTablaInversa(modeloPreciosUnitario, tblPreciosUnitario,
							tblPreciosUnitario.getSelectedRow(), Double.parseDouble(txtCostoUnitario.getText()));
				} else {
					actualizarValoresEnTabla(modeloPreciosUnitario, tblPreciosUnitario,
							Double.parseDouble(txtCostoUnitario.getText()), tblPreciosUnitario.getSelectedRow());
				}
			}

			public void editingCanceled(ChangeEvent arg0) {
			}
		};
		tblPreciosUnitario.getDefaultEditor(String.class).addCellEditorListener(changeNotificationUnitario);
		scrollPane.setViewportView(tblPreciosUnitario);

		JLabel lblPreciosPorLote = new JLabel("Precios por Lote");
		lblPreciosPorLote.setBounds(263, 179, 103, 14);
		pnlConfigPrecios.add(lblPreciosPorLote);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 193, 609, 57);
		pnlConfigPrecios.add(scrollPane_1);

		tblPreciosLote = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return renderer;
			}
		};
		CellEditorListener changeNotificationLote = new CellEditorListener() {
			public void editingStopped(ChangeEvent e) {
				if (tblPreciosLote.getSelectedColumn() == 5) {
					actualizarValoresEnTablaInversa(modeloPreciosLote, tblPreciosLote, tblPreciosLote.getSelectedRow(),
							Double.parseDouble(txtCostoLote.getText()));
				} else {
					actualizarValoresEnTabla(modeloPreciosLote, tblPreciosLote,
							Double.parseDouble(txtCostoLote.getText()), tblPreciosLote.getSelectedRow());
				}
			}

			public void editingCanceled(ChangeEvent arg0) {
			}
		};
		tblPreciosLote.getDefaultEditor(String.class).addCellEditorListener(changeNotificationLote);
		scrollPane_1.setViewportView(tblPreciosLote);

		lblCostoLote = new JLabel("Costo por Lote");
		lblCostoLote.setBounds(10, 154, 92, 14);
		pnlConfigPrecios.add(lblCostoLote);

		cmbIva = new JComboBox<TarifaIva>();
		cmbIva.setVisible(false);
		cmbIva.setToolTipText("");
		cmbIva.setEnabled(false);
		cmbIva.setBounds(432, 39, 131, 20);
		pnlConfigPrecios.add(cmbIva);

		cmbIce = new JComboBox<TarifaIce>();
		cmbIce.setBounds(102, 11, 131, 20);
		pnlConfigPrecios.add(cmbIce);

		label_1 = new JLabel("ICE*");
		label_1.setBounds(10, 14, 46, 14);
		pnlConfigPrecios.add(label_1);

		txtCostoLote = new JFormattedTextField();
		txtCostoLote.setEditable(false);
		txtCostoLote.setText("0");
		txtCostoLote.setBounds(102, 151, 86, 20);
		pnlConfigPrecios.add(txtCostoLote);

		chkTieneIva = new JCheckBox("Tiene IVA");
		chkTieneIva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkTieneIva.isSelected()) {
					cargarComboTarifaIva();
					actualizaConValoresTodasLasTablas();
				} else {
					cargarComboTarifaIvaCero();
					actualizaConValoresTodasLasTablas();
				}
			}
		});
		chkTieneIva.setBounds(432, 10, 97, 23);
		pnlConfigPrecios.add(chkTieneIva);
		cmbIce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actualizaConValoresTodasLasTablas();
			}
		});

		Panel pnlOpcionales = new Panel();
		pnlOpcionales.setBackground(new Color(240, 240, 240));
		tpnlProductos.addTab("Opcionales", null, pnlOpcionales, null);
		pnlOpcionales.setLayout(null);

		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/foto.jpg")));
		lblFoto.setBounds(10, 11, 166, 189);
		pnlOpcionales.add(lblFoto);

		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnSeleccionar.setBounds(20, 186, 140, 44);
		pnlOpcionales.add(btnSeleccionar);

		txtImagen = new JTextField();
		txtImagen.setBounds(56, 230, 86, 20);
		pnlOpcionales.add(txtImagen);
		txtImagen.setColumns(10);
		txtImagen.setVisible(false);

	}

	private static byte[] readBytesFromFile(String filePath) throws IOException {
		File inputFile = new File(filePath);
		FileInputStream inputStream = new FileInputStream(inputFile);
		byte[] fileBytes = new byte[(int) inputFile.length()];
		inputStream.read(fileBytes);
		inputStream.close();
		return fileBytes;

	}

	private void llenarConDatosAProducto() {
		producto.setCodigo(txtCodigo.getText());
		producto.setNombre(txtNombre.getText());
		producto.setPeso(Double.parseDouble(txtPeso.getText()));
		producto.setUnidadMedidaPeso((UnidadMedidaPesoEnum) cmbUnidadMedidaPeso.getSelectedItem());
		producto.setCosto(Double.parseDouble(txtCostoCompra.getText()));

		byte[] photoBytes = null;
		if (!txtImagen.getText().isEmpty()) {
			try {
				photoBytes = readBytesFromFile(txtImagen.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		producto.setFoto(photoBytes);
		producto.setEsDeducible(chkEsDeducible.isSelected());
		producto.setTipoGastoDeducible((TipoGastoDeducible) cmbTipoGastoDeducible.getSelectedItem());
		producto.setSePuedeFraccionar(chkSePuedeFraccionar.isSelected());
		producto.setManejaInventario(chkManejaInventario.isSelected());
		producto.setStock(0.0);
		producto.setStockMinimo(Double.parseDouble(txtStockMinimo.getText()));
		producto.setFechaActualizacion(new Date());
		producto.setCategoriaProducto(categoriaProducto);
		producto.setTarifaIce((TarifaIce) cmbIce.getSelectedItem());
		producto.setUnidadMedidaCompra((UnidadMedida) cmbUnidadMedidaCompra.getSelectedItem());
		producto.setCantidadUnidadMedidaCompra(Double.parseDouble(txtCantidadCompra.getText()));
		producto.setUnidadMedidaVenta((UnidadMedida) cmbUnidadMedidaVenta.getSelectedItem());
		producto.setCantidadUnidadMedidaVenta(Double.parseDouble(txtCantidadVenta.getText()));
		producto.setTieneIva(chkTieneIva.isSelected());
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
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atención",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				producto = new Producto();
				llenarConDatosAProducto();
				ProductoController productoController = new ProductoController();
				error = productoController.createProducto(producto);
				if (error == null) {
					Integer numeroVecesGuardadoCorrectamente = 0;
					TipoPrecioController tipoPrecioController = new TipoPrecioController();
					listaTipoPrecio = tipoPrecioController.tipoPrecioList();
					int i = 0;
					for (TipoPrecio tipoPrecio : listaTipoPrecio) {
						productoPrecio = new ProductoPrecio();

						productoPrecio.setProducto(producto);
						productoPrecio.setTipoPrecio(tipoPrecio);
						productoPrecio.setPorcentajeUtilidadUnitario(
								Double.parseDouble(modeloPreciosUnitario.getValueAt(i, 1).toString()));
						productoPrecio.setPorcentajeUtilidadLote(
								Double.parseDouble(modeloPreciosLote.getValueAt(i, 1).toString()));
						productoPrecio.setPrecioBrutoUnitario(
								Double.parseDouble(modeloPreciosUnitario.getValueAt(i, 2).toString()));
						productoPrecio
								.setPrecioBrutoLote(Double.parseDouble(modeloPreciosLote.getValueAt(i, 2).toString()));
						productoPrecio.setUtilidadUnitario(
								Double.parseDouble(modeloPreciosUnitario.getValueAt(i, 6).toString()));
						productoPrecio
								.setUtilidadLote(Double.parseDouble(modeloPreciosLote.getValueAt(i, 6).toString()));

						ProductoPrecioController productoPrecioController = new ProductoPrecioController();
						error = productoPrecioController.createProductoPrecio(productoPrecio);
						if (error == null) {
							numeroVecesGuardadoCorrectamente++;
						}
						i++;
					}
					if (numeroVecesGuardadoCorrectamente == listaTipoPrecio.size()) {
						JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito",
								JOptionPane.PLAIN_MESSAGE);
						limpiarCampos();
					} else {
						JOptionPane.showMessageDialog(null, "Producto guardado correctamente, excepto los precios",
								"Error", JOptionPane.WARNING_MESSAGE);
						limpiarCampos();
					}

				} else {
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
			}
		});
		/*
		 * IMPORTANTE * Para capturar desde el modal
		 */
		categoriaProductoListModalFrm.addConfirmListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoriaProducto = categoriaProductoListModalFrm.getCategoriaProducto();
				if (categoriaProducto != null)
					txtCategoriaProducto.setText(categoriaProducto.getNombre());
			}
		});
		/*
		 * *************************************/
		btnBuscarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!categoriaProductoListModalFrm.isVisible()) {
					categoriaProductoListModalFrm.setModal(true);
					categoriaProductoListModalFrm.setVisible(true);
				}
			}
		});
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileNameExtensionFilter filtro = new FileNameExtensionFilter(
						"Formatos de Archivos JPEG (*.JPG; *.JPEG) ", "jpg", "jpeg");
				JFileChooser archivo = new JFileChooser();
				archivo.addChoosableFileFilter(filtro);
				archivo.setDialogTitle("Abrir archivo");
				int ventana = archivo.showOpenDialog(null);
				if (ventana == JFileChooser.APPROVE_OPTION) {
					File file = archivo.getSelectedFile();

					txtImagen.setText(String.valueOf(file));
					Image foto = getToolkit().getImage(txtImagen.getText());
					foto = foto.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
					lblFoto.setIcon(new ImageIcon(foto));

				}
			}
		});
		chkEsDeducible.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkEsDeducible.isSelected())
					cmbTipoGastoDeducible.setVisible(true);
				else
					cmbTipoGastoDeducible.setVisible(false);
			}
		});
	}

	private void limpiarCampos() {
		txtCodigo.setText("");
		txtNombre.setText("");
		categoriaProducto = null;
		txtCategoriaProducto.setText("");
		txtStockMinimo.setText("0");
		txtPeso.setText("0");
		txtCantidadCompra.setText("1");
		txtCantidadVenta.setText("1");
		chkSePuedeFraccionar.setSelected(false);
		chkManejaInventario.setSelected(false);
		chkEsDeducible.setSelected(false);
		cmbTipoGastoDeducible.setVisible(false);
		lblFoto.setIcon(new ImageIcon(ProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/foto.jpg")));
		txtCostoUnitario.setText("0");
		txtCostoLote.setText("0");
		txtCostoCompra.setText("0");
		chkTieneIva.setSelected(true);
		txtCodigo.requestFocus();
		chkTieneIva.setSelected(true);
		actualizaConValoresTodasLasTablas();
	}

	private void actualizaConValoresTodasLasTablas() {
		actualizarValoresEnTabla(modeloPreciosUnitario, tblPreciosUnitario,
				Double.parseDouble(txtCostoUnitario.getText()), -1);
		actualizarValoresEnTabla(modeloPreciosLote, tblPreciosLote, Double.parseDouble(txtCostoLote.getText()), -1);
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCategoriaProducto.getText().isEmpty()
				|| txtPeso.getText().isEmpty() || cmbUnidadMedidaPeso.getItemCount() == 0
				|| txtStockMinimo.getText().isEmpty() || cmbIva.getItemCount() == 0 || cmbIce.getItemCount() == 0
				|| cmbUnidadMedidaCompra.getItemCount() == 0 || cmbUnidadMedidaVenta.getItemCount() == 0
				|| txtCantidadCompra.getText().isEmpty() || txtCantidadVenta.getText().isEmpty()) {
			llenos = false;
		}
		if (chkEsDeducible.isSelected() && cmbTipoGastoDeducible.getItemCount() == 0) {
			llenos = false;
		}
		return llenos;
	}

	private void preciosCantidadFocusLost() {
		txtCostoLote.setText(txtCostoCompra.getText());
		Double costoUnitario = (Double.parseDouble(txtCostoCompra.getText())
				* Double.parseDouble(txtCantidadVenta.getText())) / Double.parseDouble(txtCantidadCompra.getText());
		txtCostoUnitario.setText(costoUnitario.toString());
		actualizaConValoresTodasLasTablas();
	}

	private class CustomCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component rendererComp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			rendererComp.setForeground(Color.BLACK);
			rendererComp.setBackground(Color.WHITE);
			return rendererComp;
		}

	}
}
