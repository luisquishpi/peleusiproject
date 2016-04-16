package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Panel;
import javax.swing.border.TitledBorder;

import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.controllers.TipoGastoDeducibleController;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.models.entities.TarifaIce;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.models.entities.TipoGastoDeducible;
import ec.peleusi.models.entities.UnidadMedida;
import javax.swing.UIManager;

public class ProductoCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTabbedPane tabPanel;
	private JPanel pnlConfigPrecios;
	private JPanel pnlDatosGenerales;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtCategoriaProducto;
	private JTextField textField_1;
	private JTextField txtStockMinimo;
	private JButton btnBuscarCategoria;
	private JFormattedTextField txtPeso;
	private JComboBox<UnidadMedida> cmbUnidadMedidaPeso;
	private JComboBox<TarifaIva> cmbIva;
	private JComboBox<TarifaIce> cmbIce;
	private JCheckBox chckbxSePuedeFraccionar;
	private JCheckBox chckbxManejaInventario;
	private JCheckBox chckbxEsDeducible;
	private JComboBox<UnidadMedida> cmbUnidadMedidaCompra;
	private JComboBox<UnidadMedida> cmbUnidadMedidaVenta;
	private JFormattedTextField txtContieneCompra;
	private JFormattedTextField txtContieneVenta;
	private JComboBox<TipoGastoDeducible> cmbTipoGastoDeducible;
	private CategoriaProductoListModalFrm categoriaProductoListModalFrm = new CategoriaProductoListModalFrm();
	private CategoriaProducto categoriaProducto;

	public ProductoCrudFrm() {
		setTitle("Productos");
		crearControles();
		crearEventos();
		cargarCombosUnidaMedida();
		cargarComboTarifaIva();
		cargarComboTarifaIce();
		cargarComboTipoGastoDeducible();
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
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		List<TarifaIva> listaTarifaIva;
		listaTarifaIva = tarifaIvaController.tarifaIvaList();
		cmbIva.setModel(new DefaultComboBoxModel(listaTarifaIva.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarCombosUnidaMedida() {
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		List<UnidadMedida> listaUnidadMedida;
		listaUnidadMedida = unidadMedidaController.unidadMedidaList();
		cmbUnidadMedidaPeso.setModel(new DefaultComboBoxModel(listaUnidadMedida.toArray()));
		cmbUnidadMedidaCompra.setModel(new DefaultComboBoxModel(listaUnidadMedida.toArray()));
		cmbUnidadMedidaVenta.setModel(new DefaultComboBoxModel(listaUnidadMedida.toArray()));
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

		tabPanel = new JTabbedPane(JTabbedPane.TOP);
		tabPanel.setBounds(10, 11, 644, 289);
		panelCuerpo.add(tabPanel);

		pnlDatosGenerales = new JPanel();
		tabPanel.addTab("Datos Generales", null, pnlDatosGenerales, null);
		pnlDatosGenerales.setLayout(null);

		JLabel lblCdigo = new JLabel("Código");
		lblCdigo.setBounds(10, 11, 46, 14);
		pnlDatosGenerales.add(lblCdigo);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(248, 11, 46, 14);
		pnlDatosGenerales.add(lblNombre);

		JLabel lblPeso = new JLabel("Peso");
		lblPeso.setBounds(10, 67, 46, 14);
		pnlDatosGenerales.add(lblPeso);

		chckbxEsDeducible = new JCheckBox("Es deducible");
		chckbxEsDeducible.setBounds(455, 144, 161, 23);
		pnlDatosGenerales.add(chckbxEsDeducible);

		chckbxSePuedeFraccionar = new JCheckBox("Se puede fraccionar");
		chckbxSePuedeFraccionar.setBounds(455, 92, 161, 23);
		pnlDatosGenerales.add(chckbxSePuedeFraccionar);

		chckbxManejaInventario = new JCheckBox("Maneja Inventario");
		chckbxManejaInventario.setToolTipText(
				"Ej: Los servicios no manejan inventarios porque su cantidad no tienes un stock fijo sino es ilimitado ");
		chckbxManejaInventario.setBounds(455, 118, 161, 23);
		pnlDatosGenerales.add(chckbxManejaInventario);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(82, 8, 156, 20);
		pnlDatosGenerales.add(txtCodigo);
		txtCodigo.setColumns(10);

		txtNombre = new JTextField();
		txtNombre.setText("");
		txtNombre.setBounds(297, 8, 319, 20);
		pnlDatosGenerales.add(txtNombre);
		txtNombre.setColumns(10);

		txtPeso = new JFormattedTextField();
		txtPeso.setBounds(82, 64, 86, 20);
		pnlDatosGenerales.add(txtPeso);

		JLabel lblCategora = new JLabel("Categoría");
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

		txtStockMinimo = new JTextField();
		txtStockMinimo.setBounds(437, 64, 86, 20);
		pnlDatosGenerales.add(txtStockMinimo);
		txtStockMinimo.setColumns(10);

		cmbTipoGastoDeducible = new JComboBox<TipoGastoDeducible>();
		cmbTipoGastoDeducible.setBounds(455, 174, 161, 20);
		pnlDatosGenerales.add(cmbTipoGastoDeducible);

		JPanel panel = new JPanel();
		panel.setBounds(10, 92, 430, 57);
		panel.setBorder(new TitledBorder(null, "Impuestos", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		pnlDatosGenerales.add(panel);
		panel.setLayout(null);

		cmbIce = new JComboBox<TarifaIce>();
		cmbIce.setBounds(225, 21, 131, 20);
		panel.add(cmbIce);

		cmbIva = new JComboBox<TarifaIva>();
		cmbIva.setBounds(41, 21, 131, 20);
		panel.add(cmbIva);

		JLabel lblIva = new JLabel("IVA");
		lblIva.setBounds(10, 24, 46, 14);
		panel.add(lblIva);
		lblIva.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblIce = new JLabel("ICE");
		lblIce.setBounds(194, 24, 46, 14);
		panel.add(lblIce);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Medida en compra y Medida para la venta",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 156, 430, 86);
		pnlDatosGenerales.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSeCompraEn = new JLabel("Se compra en");
		lblSeCompraEn.setBounds(10, 25, 88, 14);
		panel_1.add(lblSeCompraEn);

		cmbUnidadMedidaCompra = new JComboBox<UnidadMedida>();
		cmbUnidadMedidaCompra.setBounds(97, 22, 166, 20);
		panel_1.add(cmbUnidadMedidaCompra);

		JLabel lblContiene = new JLabel("Contiene");
		lblContiene.setBounds(273, 22, 57, 14);
		panel_1.add(lblContiene);

		txtContieneCompra = new JFormattedTextField();
		txtContieneCompra.setBounds(329, 22, 75, 20);
		panel_1.add(txtContieneCompra);

		JLabel lblSeVendeEn = new JLabel("Se vende en");
		lblSeVendeEn.setBounds(10, 53, 88, 14);
		panel_1.add(lblSeVendeEn);

		cmbUnidadMedidaVenta = new JComboBox<UnidadMedida>();
		cmbUnidadMedidaVenta.setBounds(97, 50, 166, 20);
		panel_1.add(cmbUnidadMedidaVenta);

		JLabel label_1 = new JLabel("Contiene");
		label_1.setBounds(273, 50, 57, 14);
		panel_1.add(label_1);

		txtContieneVenta = new JFormattedTextField();
		txtContieneVenta.setBounds(329, 50, 75, 20);
		panel_1.add(txtContieneVenta);

		cmbUnidadMedidaPeso = new JComboBox<UnidadMedida>();
		cmbUnidadMedidaPeso.setBounds(178, 64, 161, 20);
		pnlDatosGenerales.add(cmbUnidadMedidaPeso);

		pnlConfigPrecios = new JPanel();
		tabPanel.addTab("Configuración de precios", null, pnlConfigPrecios, null);
		pnlConfigPrecios.setLayout(null);

		JLabel label_2 = new JLabel("Costo");
		label_2.setBounds(10, 14, 46, 14);
		pnlConfigPrecios.add(label_2);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(82, 11, 86, 20);
		pnlConfigPrecios.add(formattedTextField);

		Panel pnlOpcionales = new Panel();
		tabPanel.addTab("Opcionales", null, pnlOpcionales, null);
		pnlOpcionales.setLayout(null);

		JLabel label = new JLabel("Foto");
		label.setBounds(10, 14, 22, 14);
		pnlOpcionales.add(label);

		textField_1 = new JTextField();
		textField_1.setBounds(70, 11, 86, 20);
		textField_1.setColumns(10);
		pnlOpcionales.add(textField_1);
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
					JOptionPane.showMessageDialog(null, "No deje campos vacíos");
					return;
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
		/* Para capturar desde el modal */
		categoriaProductoListModalFrm.addConfirmListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoriaProducto = categoriaProductoListModalFrm.getCategoriaProducto();
				if (categoriaProducto != null)
					txtCategoriaProducto.setText(categoriaProducto.getNombre());
			}
		});
		btnBuscarCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!categoriaProductoListModalFrm.isVisible()) {
					categoriaProductoListModalFrm.setModal(true);
					categoriaProductoListModalFrm.setVisible(true);
				}
			}
		});
	}

	private void limpiarCampos() {
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		return llenos;
	}
}
