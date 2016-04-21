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
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
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
import javax.swing.JFileChooser;

import java.awt.Panel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.controllers.TipoGastoDeducibleController;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.TarifaIce;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.models.entities.TipoGastoDeducible;
import ec.peleusi.models.entities.UnidadMedida;
import ec.peleusi.utils.Formatos;
import ec.peleusi.utils.UnidadMedidaPesoEnum;

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
	private JFormattedTextField txtStockMinimo;
	private JButton btnBuscarCategoria;
	private JFormattedTextField txtPeso;
	private JComboBox<UnidadMedidaPesoEnum> cmbUnidadMedidaPeso;
	private JComboBox<TarifaIva> cmbIva;
	private JComboBox<TarifaIce> cmbIce;
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

	public ProductoCrudFrm() {
		setTitle("Productos");
		crearControles();
		crearEventos();
		cargarCombosUnidaMedida();
		cargarComboTarifaIva();
		cargarComboTarifaIce();
		cargarComboTipoGastoDeducible();
		limpiarCampos();
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

		tabPanel = new JTabbedPane(JTabbedPane.TOP);
		tabPanel.setBounds(10, 11, 644, 289);
		panelCuerpo.add(tabPanel);

		pnlDatosGenerales = new JPanel();
		tabPanel.addTab("Datos Generales", null, pnlDatosGenerales, null);
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
		chkEsDeducible.setBounds(455, 144, 161, 23);
		pnlDatosGenerales.add(chkEsDeducible);

		chkSePuedeFraccionar = new JCheckBox("Se puede fraccionar");
		chkSePuedeFraccionar.setBounds(455, 92, 161, 23);
		pnlDatosGenerales.add(chkSePuedeFraccionar);

		chkManejaInventario = new JCheckBox("Maneja Inventario");
		chkManejaInventario.setToolTipText(
				"Ej: Los servicios no manejan inventarios porque su cantidad no tienes un stock fijo sino es ilimitado ");
		chkManejaInventario.setBounds(455, 118, 161, 23);
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

		JLabel lblIva = new JLabel("IVA*");
		lblIva.setBounds(10, 24, 46, 14);
		panel.add(lblIva);
		lblIva.setHorizontalAlignment(SwingConstants.LEFT);

		JLabel lblIce = new JLabel("ICE*");
		lblIce.setBounds(194, 24, 46, 14);
		panel.add(lblIce);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(
				new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Medida en compra y Medida para la venta",
						TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 156, 430, 86);
		pnlDatosGenerales.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSeCompraEn = new JLabel("Se compra en*");
		lblSeCompraEn.setBounds(10, 25, 88, 14);
		panel_1.add(lblSeCompraEn);

		cmbUnidadMedidaCompra = new JComboBox<UnidadMedida>();
		cmbUnidadMedidaCompra.setBounds(97, 22, 166, 20);
		panel_1.add(cmbUnidadMedidaCompra);

		JLabel lblContiene = new JLabel("Contiene*");
		lblContiene.setBounds(273, 22, 62, 14);
		panel_1.add(lblContiene);

		txtCantidadCompra = new JFormattedTextField();
		txtCantidadCompra.setFormatterFactory(new Formatos().getDecimalFormat());
		txtCantidadCompra.setBounds(345, 19, 75, 20);
		panel_1.add(txtCantidadCompra);

		JLabel lblSeVendeEn = new JLabel("Se vende en*");
		lblSeVendeEn.setBounds(10, 53, 88, 14);
		panel_1.add(lblSeVendeEn);

		cmbUnidadMedidaVenta = new JComboBox<UnidadMedida>();
		cmbUnidadMedidaVenta.setBounds(97, 50, 166, 20);
		panel_1.add(cmbUnidadMedidaVenta);

		JLabel lblContiene_1 = new JLabel("Contiene*");
		lblContiene_1.setBounds(273, 50, 62, 14);
		panel_1.add(lblContiene_1);

		txtCantidadVenta = new JFormattedTextField();
		txtCantidadVenta.setFormatterFactory(new Formatos().getDecimalFormat());
		txtCantidadVenta.setBounds(345, 47, 75, 20);
		panel_1.add(txtCantidadVenta);

		cmbUnidadMedidaPeso = new JComboBox<UnidadMedidaPesoEnum>();
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
				Producto producto = new Producto();
				producto.setCodigo(txtCodigo.getText());
				producto.setNombre(txtNombre.getText());
				producto.setPeso(Double.parseDouble(txtPeso.getText()));
				producto.setUnidadMedidaPeso((UnidadMedidaPesoEnum) cmbUnidadMedidaPeso.getSelectedItem());
				producto.setCosto(0.0);

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
				producto.setTarifaIva((TarifaIva) cmbIva.getSelectedItem());
				producto.setTarifaIce((TarifaIce) cmbIce.getSelectedItem());
				producto.setUnidadMedidaCompra((UnidadMedida) cmbUnidadMedidaCompra.getSelectedItem());
				producto.setCantidadunidadmedidacompra(Double.parseDouble(txtCantidadCompra.getText()));
				producto.setUnidadMedidaVenta((UnidadMedida) cmbUnidadMedidaVenta.getSelectedItem());
				producto.setCantidadunidadmedidaventa(Double.parseDouble(txtCantidadVenta.getText()));
				ProductoController productoController = new ProductoController();
				String error = productoController.createProducto(producto);
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
					limpiarCampos();
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
		txtCodigo.requestFocus();
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
}
