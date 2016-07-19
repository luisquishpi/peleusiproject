package ec.peleusi.views.fx.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.bouncycastle.mail.smime.CMSProcessableBodyPartInbound;

import ec.peleusi.controllers.CategoriaProductoController;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.ProductoPrecioController;
import ec.peleusi.controllers.SeteoController;
import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.controllers.TipoGastoDeducibleController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.ProductoPrecio;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.TablaPreciosProducto;
import ec.peleusi.models.entities.TarifaIce;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.models.entities.TipoGastoDeducible;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.models.entities.UnidadMedida;
import ec.peleusi.utils.UnidadMedidaPesoEnum;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.ImageUtils;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

public class ProductoListFxController extends GenericController {

	@FXML
	private TextField txtCodigo;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtCategoriaProducto;
	@FXML
	private TextField txtPeso;
	@FXML
	private TextField txtStockMinimo;
	@FXML
	private TextField txtCantidadCompra;
	@FXML
	private TextField txtCostoCompra;
	@FXML
	private TextField txtCantidadVenta;
	@FXML
	private TextField txtCostoUnitario;
	@FXML
	private TextField txtCostoLote;
	@FXML
	private ComboBox<UnidadMedidaPesoEnum> cmbUnidadMedidaPeso;
	@FXML
	private ComboBox<UnidadMedida> cmbUnidadMedidaCompra;
	@FXML
	private ComboBox<UnidadMedida> cmbUnidadMedidaVenta;
	@FXML
	private ComboBox<TipoGastoDeducible> cmbTipoGastoDeducible;
	@FXML
	private ComboBox<TarifaIva> cmbIva;
	@FXML
	private ComboBox<TarifaIce> cmbIce;

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnBuscarCategoria;
	@FXML
	private Button btnNuevo;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnBuscar;
	@FXML
	private TableView<Producto> tblLista;
	@FXML
	private TableView<TablaPreciosProducto> tblPreciosUnitario;
	@FXML
	private TableView<TablaPreciosProducto> tblPreciosLote;
	@FXML
	private Pagination pagination;
	@FXML
	private ImageView imgProducto;
	@FXML
	VBox vbParaTabla;

	ObservableList<Producto> productosList;
	ObservableList<TablaPreciosProducto> preciosUnitarioList;
	ObservableList<TablaPreciosProducto> preciosLoteList;

	private Integer posicionObjetoEnTabla;
	private ProductoController productoController = new ProductoController();
	private ProductoPrecioController productoPrecioController = new ProductoPrecioController();
	private String error = null;
	private Producto producto;
	final static int rowsPerPage = 100;
	private Image imgOriginal;
	private Image imgSinFoto;
	private TarifaIva tarifaIva = new TarifaIva();
	private TarifaIce tarifaIce = new TarifaIce();
	private List<TipoPrecio> listaTipoPrecio;
	Double porcentaje = 0.0;
	Double subtotal = 0.0;
	Double iva = 0.0;
	Double ice = 0.0;
	Double total = 0.0;
	Double utilidad = 0.0;
	TablaPreciosProducto tablaPreciosProducto;
	private CategoriaProducto categoriaProducto;
	private ProductoPrecio productoPrecio;

	@FXML
	private TableColumn<TablaPreciosProducto, String> tipoPrecioCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> porcentajeUtilidadCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> subtotalCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> iceCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> ivaCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> totalCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> utilidadCol;

	@FXML
	private TableColumn<TablaPreciosProducto, String> tipoPrecioLoteCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> porcentajeUtilidadLoteCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> subtotalLoteCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> iceLoteCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> ivaLoteCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> totalLoteCol;
	@FXML
	private TableColumn<TablaPreciosProducto, Double> utilidadLoteCol;

	@FXML
	CheckBox chkTieneIva;
	@FXML
	CheckBox chkSePuedeFraccionar;
	@FXML
	CheckBox chkManejaInventario;
	@FXML
	CheckBox chkEsDeducible;

	@FXML
	private void initialize() {

		crearTablaListaProductos();
		paginar();
		cargarCombosUnidaMedida();
		cargarComboTarifaIva();
		cargarComboTarifaIce();
		cargarComboTipoGastoDeducible();

		tarifaIva = cmbIva.getValue();
		tarifaIce = cmbIce.getValue();
		limpiarCampos();

		txtCostoCompra.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txtCantidadCostoCompraFocusLost();
				}
			}
		});
		txtCantidadCompra.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txtCantidadCostoCompraFocusLost();
				}
			}
		});

		porcentajeUtilidadCol.setCellFactory(
				TextFieldTableCell.<TablaPreciosProducto, Double> forTableColumn(new DoubleStringConverter()));
		porcentajeUtilidadCol.setOnEditCommit(new EventHandler<CellEditEvent<TablaPreciosProducto, Double>>() {
			@Override
			public void handle(CellEditEvent<TablaPreciosProducto, Double> t) {
				((TablaPreciosProducto) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setPorcentajeUtilidad(t.getNewValue());
				actualizarValoresEnTabla(preciosUnitarioList, tblPreciosUnitario,
						Double.parseDouble(txtCostoUnitario.getText()), t.getTablePosition().getRow());
			}
		});
		totalCol.setCellFactory(
				TextFieldTableCell.<TablaPreciosProducto, Double> forTableColumn(new DoubleStringConverter()));
		totalCol.setOnEditCommit(new EventHandler<CellEditEvent<TablaPreciosProducto, Double>>() {
			@Override
			public void handle(CellEditEvent<TablaPreciosProducto, Double> t) {
				((TablaPreciosProducto) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setTotal(t.getNewValue());
				actualizarValoresEnTablaInversa(preciosUnitarioList, tblPreciosUnitario,
						Double.parseDouble(txtCostoUnitario.getText()), t.getTablePosition().getRow());
			}
		});

		porcentajeUtilidadLoteCol.setCellFactory(
				TextFieldTableCell.<TablaPreciosProducto, Double> forTableColumn(new DoubleStringConverter()));
		porcentajeUtilidadLoteCol.setOnEditCommit(new EventHandler<CellEditEvent<TablaPreciosProducto, Double>>() {
			@Override
			public void handle(CellEditEvent<TablaPreciosProducto, Double> t) {
				((TablaPreciosProducto) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setPorcentajeUtilidad(t.getNewValue());
				actualizarValoresEnTabla(preciosLoteList, tblPreciosLote, Double.parseDouble(txtCostoLote.getText()),
						t.getTablePosition().getRow());
			}
		});
		totalLoteCol.setCellFactory(
				TextFieldTableCell.<TablaPreciosProducto, Double> forTableColumn(new DoubleStringConverter()));
		totalLoteCol.setOnEditCommit(new EventHandler<CellEditEvent<TablaPreciosProducto, Double>>() {
			@Override
			public void handle(CellEditEvent<TablaPreciosProducto, Double> t) {
				((TablaPreciosProducto) t.getTableView().getItems().get(t.getTablePosition().getRow()))
						.setTotal(t.getNewValue());
				actualizarValoresEnTablaInversa(preciosLoteList, tblPreciosLote,
						Double.parseDouble(txtCostoLote.getText()), t.getTablePosition().getRow());
			}
		});

		// txtBuscar.setOnKeyReleased(this::txtBuscarReleased);
		/*
		 * txtBuscar.setOnKeyReleased(new EventHandler<KeyEvent>() {
		 * 
		 * @Override public void handle(KeyEvent event) { System.out.println(
		 * "hi> "+event.getText()); txtBuscarReleased(event); }
		 * 
		 * } );
		 */
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
				CategoriaProductoController catCon = new CategoriaProductoController();
				categoriaProducto = catCon.CategoriaProductoList().get(0);
				txtCategoriaProducto.setText(categoriaProducto.getNombre());
				// eliminar 3 lineas anteriores
			}
		});
	}

	private void txtCantidadCostoCompraFocusLost() {
		txtCostoLote.setText(txtCostoCompra.getText());
		Double costoUnitario = (Double.parseDouble(txtCostoCompra.getText())
				* Double.parseDouble(txtCantidadVenta.getText())) / Double.parseDouble(txtCantidadCompra.getText());
		txtCostoUnitario.setText(costoUnitario.toString());
		actualizaConValoresTodasLasTablas();
	}

	private void crearTablaPreciosUnitario() {

		if (preciosUnitarioList != null)
			preciosUnitarioList.clear();
		preciosUnitarioList = tblPreciosUnitario.getItems();
		tipoPrecioCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, String>("nombre"));
		porcentajeUtilidadCol
				.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("porcentajeUtilidad"));
		subtotalCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("subtotal"));
		iceCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("ice"));
		ivaCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("iva"));
		totalCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("total"));
		utilidadCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("utilidad"));

		Double costoProducto = 0.0;
		Double.parseDouble(txtCostoUnitario.getText());
		cargarTablaConValores(preciosUnitarioList, costoProducto, tarifaIva, tarifaIce);
		tblPreciosUnitario.setItems(preciosUnitarioList);

	}

	private void crearTablaPreciosLote() {
		if (preciosLoteList != null)
			preciosLoteList.clear();
		preciosLoteList = tblPreciosLote.getItems();
		tipoPrecioLoteCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, String>("nombre"));
		porcentajeUtilidadLoteCol
				.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("porcentajeUtilidad"));
		subtotalLoteCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("subtotal"));
		iceLoteCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("ice"));
		ivaLoteCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("iva"));
		totalLoteCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("total"));
		utilidadLoteCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("utilidad"));

		Double costoProducto = 0.0;
		Double.parseDouble(txtCostoLote.getText());
		cargarTablaConValores(preciosLoteList, costoProducto, tarifaIva, tarifaIce);
		tblPreciosLote.setItems(preciosLoteList);
	}

	private void paginar() {
		int count = productosList.size() / rowsPerPage;
		if (count < ((double) (productosList.size()) / rowsPerPage))
			count++;
		if (count == 0 && productosList.size() > 0)
			count++;
		if (productosList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, productosList.size());
					tblLista.setItems(FXCollections.observableArrayList(productosList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	private void crearTablaListaProductos() {
		tblLista = new TableView<Producto>();
		TableColumn<Producto, String> codigoCol = new TableColumn<Producto, String>("Código");
		codigoCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
		TableColumn<Producto, String> nombreCol = new TableColumn<Producto, String>("Nombre");
		nombreCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
		TableColumn<Producto, String> stockCol = new TableColumn<Producto, String>("Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("stock"));
		TableColumn<Producto, String> categoriaCol = new TableColumn<Producto, String>("Categoría");
		categoriaCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("categoriaProducto"));

		tblLista.getColumns().add(codigoCol);
		tblLista.getColumns().add(nombreCol);
		tblLista.getColumns().add(stockCol);
		tblLista.getColumns().add(categoriaCol);

		productosList = FXCollections.observableList(productoController.productoList());
		tblLista.setItems(productosList);
		final ObservableList<Producto> productosListSelected = tblLista.getSelectionModel().getSelectedItems();
		productosListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<Producto> escuchaCambiosEnTabla = new ListChangeListener<Producto>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Producto> c) {
			cargarObjetoSeleccionadaEnFormulario();
		}
	};

	private void cargarObjetoSeleccionadaEnFormulario() {
		producto = (Producto) getObjetoSeleccionadoDeTabla();
		if (producto != null) {
			posicionObjetoEnTabla = productosList.indexOf(producto);
			txtCodigo.setText(producto.getCodigo());
			txtNombre.setText(producto.getNombre());
			categoriaProducto = producto.getCategoriaProducto();
			txtCategoriaProducto.setText(categoriaProducto.getNombre());
			txtPeso.setText(producto.getPeso().toString());
			cmbUnidadMedidaPeso.getSelectionModel().select(producto.getUnidadMedidaPeso());
			txtStockMinimo.setText(producto.getStockMinimo().toString());
			cmbUnidadMedidaCompra.getSelectionModel().select(producto.getUnidadMedidaCompra());
			txtCantidadCompra.setText(producto.getCantidadUnidadMedidaCompra().toString());
			txtCostoCompra.setText(producto.getCosto().toString());
			cmbUnidadMedidaVenta.getSelectionModel().select(producto.getUnidadMedidaVenta());
			txtCantidadVenta.setText(producto.getCantidadUnidadMedidaVenta().toString());
			chkSePuedeFraccionar.setSelected(producto.getSePuedeFraccionar());
			chkManejaInventario.setSelected(producto.getManejaInventario());

			chkEsDeducible.setSelected(producto.getEsDeducible());
			cmbTipoGastoDeducible.getSelectionModel().select(producto.getTipoGastoDeducible());
			if (chkEsDeducible.isSelected())
				cmbTipoGastoDeducible.setVisible(true);
			else
				cmbTipoGastoDeducible.setVisible(false);

			chkTieneIva.setSelected(producto.getTieneIva());
			if (chkTieneIva.isSelected())
				cmbIva.setVisible(true);
			else
				cmbIva.setVisible(false);

			cmbIce.getSelectionModel().select(producto.getTarifaIce());

			if (producto.getFoto() != null) {
				try {
					InputStream in = new ByteArrayInputStream(producto.getFoto());
					BufferedImage bufferedImage = null;
					bufferedImage = ImageIO.read(in);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					imgProducto.setImage(image);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				imgProducto.setImage(imgSinFoto);
			}

			txtCantidadCostoCompraFocusLost();

			// cargar precios
			// FXCollections.observableList(productoPrecioController.productoPrecioList(producto));
			List<ProductoPrecio> productoPrecioList = productoPrecioController.productoPrecioList(producto);
			if (productoPrecioList != null) {
				for (int i = 0; i < listaTipoPrecio.size(); i++) {
					for (int j = 0; j < productoPrecioList.size(); j++) {
						if (listaTipoPrecio.get(i).getNombre()
								.equals(productoPrecioList.get(j).getTipoPrecio().getNombre())) {
							preciosUnitarioList.get(i)
									.setPorcentajeUtilidad(productoPrecioList.get(j).getPorcentajeUtilidadUnitario());
							preciosLoteList.get(i)
									.setPorcentajeUtilidad(productoPrecioList.get(j).getPorcentajeUtilidadLote());
						}
					}
				}
			}else{
				AlertsUtil.alertWarning("El producto no tiene precios, pulse Actualizar");
			}
			actualizaConValoresTodasLasTablas();

			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	public Object getObjetoSeleccionadoDeTabla() {
		if (tblLista != null) {
			if (tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				return tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
		return null;
	}

	private void cargarCombosUnidaMedida() {
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		List<UnidadMedida> listaUnidadMedida;
		listaUnidadMedida = unidadMedidaController.unidadMedidaList();
		cmbUnidadMedidaCompra.setItems(FXCollections.observableArrayList(listaUnidadMedida));
		if (cmbUnidadMedidaCompra != null)
			cmbUnidadMedidaCompra.getSelectionModel().select(0);
		cmbUnidadMedidaVenta.setItems(FXCollections.observableArrayList(listaUnidadMedida));
		if (cmbUnidadMedidaVenta != null)
			cmbUnidadMedidaVenta.getSelectionModel().select(0);
		cmbUnidadMedidaPeso.setItems(FXCollections.observableArrayList(UnidadMedidaPesoEnum.values()));
		if (cmbUnidadMedidaPeso != null)
			cmbUnidadMedidaPeso.getSelectionModel().select(0);
	}

	private void cargarComboTarifaIva() {
		SeteoController seteoController = new SeteoController();
		List<Seteo> listaSeteo;
		listaSeteo = seteoController.seteoList();
		TarifaIva tarifaIva = listaSeteo.get(0).getTarifaIva();
		List<TarifaIva> listaTarifaIva = new ArrayList<TarifaIva>();
		listaTarifaIva.add(tarifaIva);
		cmbIva.setItems(FXCollections.observableArrayList(listaTarifaIva));
		if (cmbIva != null)
			cmbIva.getSelectionModel().select(0);
	}

	private void cargarComboTarifaIvaCero() {
		tarifaIva = new TarifaIva();
		tarifaIva.setNombre("IVA 0%");
		tarifaIva.setPorcentaje(0.0);
		List<TarifaIva> listaTarifaIva = new ArrayList<TarifaIva>();
		listaTarifaIva.add(tarifaIva);
		cmbIva.setItems(FXCollections.observableArrayList(listaTarifaIva));
		if (cmbIva != null)
			cmbIva.getSelectionModel().select(0);
	}

	private void cargarComboTarifaIce() {
		TarifaIceController tarifaIceController = new TarifaIceController();
		List<TarifaIce> listaTarifaIce = tarifaIceController.tarifaIceList();
		cmbIce.setItems(FXCollections.observableArrayList(listaTarifaIce));
		if (cmbIce != null)
			cmbIce.getSelectionModel().select(0);

	}

	private void cargarComboTipoGastoDeducible() {
		TipoGastoDeducibleController tipoGastoDeducibleController = new TipoGastoDeducibleController();
		List<TipoGastoDeducible> listaTipoGastoDeducibleController = tipoGastoDeducibleController
				.tipoGastoDeducibleList();
		cmbTipoGastoDeducible.setItems(FXCollections.observableArrayList(listaTipoGastoDeducibleController));
		if (cmbTipoGastoDeducible != null)
			cmbTipoGastoDeducible.getSelectionModel().select(0);
	}

	private void cargarTablaConValores(ObservableList<TablaPreciosProducto> modelo, Double costoProducto,
			TarifaIva tarifaIva, TarifaIce tarifaIce) {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		listaTipoPrecio = tipoPrecioController.tipoPrecioList();
		for (TipoPrecio tipoPrecio : listaTipoPrecio) {
			tablaPreciosProducto = new TablaPreciosProducto();
			porcentaje = tipoPrecio.getPorcentaje();
			calcularFila(costoProducto, tarifaIva, tarifaIce);
			tablaPreciosProducto.setNombre(tipoPrecio.getNombre());
			tablaPreciosProducto.setPorcentajeUtilidad(porcentaje);
			tablaPreciosProducto.setSubtotal(subtotal);
			tablaPreciosProducto.setIva(iva);
			tablaPreciosProducto.setIce(ice);
			tablaPreciosProducto.setTotal(total);
			tablaPreciosProducto.setUtilidad(utilidad);
			modelo.add(tablaPreciosProducto);
		}

	}

	private void calcularFila(Double costoProducto, TarifaIva tarifaIva, TarifaIce tarifaIce) {
		if (tarifaIva == null || tarifaIce == null)
			return;
		subtotal = (costoProducto * (porcentaje / 100)) + costoProducto;
		ice = subtotal * (tarifaIce.getPorcentaje() / 100);
		iva = (subtotal + ice) * (tarifaIva.getPorcentaje() / 100);
		total = subtotal + ice + iva;
		utilidad = subtotal - costoProducto;
	}

	private void actualizaConValoresTodasLasTablas() {
		if (preciosUnitarioList != null) {
			actualizarValoresEnTabla(preciosUnitarioList, tblPreciosUnitario,
					Double.parseDouble(txtCostoUnitario.getText()), -1);
		}
		if (preciosLoteList != null) {
			actualizarValoresEnTabla(preciosLoteList, tblPreciosLote, Double.parseDouble(txtCostoLote.getText()), -1);
		}
	}

	private void actualizarValoresEnTabla(ObservableList<TablaPreciosProducto> modelo,
			TableView<TablaPreciosProducto> table, Double costoProducto, int filaPosition) {
		int inicio = 0;
		if (filaPosition == -1) {
			filaPosition = modelo.size() - 1;
		} else {
			inicio = filaPosition;
		}
		for (int i = inicio; i <= filaPosition; i++) {
			porcentaje = Double.parseDouble(modelo.get(i).getPorcentajeUtilidad().toString());
			tarifaIva = (TarifaIva) cmbIva.getValue();
			tarifaIce = (TarifaIce) cmbIce.getValue();
			calcularFila(costoProducto, tarifaIva, tarifaIce);

			modelo.get(i).setSubtotal(subtotal);
			modelo.get(i).setIce(ice);
			modelo.get(i).setIva(iva);
			modelo.get(i).setTotal(total);
			modelo.get(i).setUtilidad(utilidad);

		}
		table.refresh();

	}

	private void actualizarValoresEnTablaInversa(ObservableList<TablaPreciosProducto> modelo,
			TableView<TablaPreciosProducto> table, Double costoProducto, int filaPosition) {
		int inicio = 0;
		if (filaPosition == -1) {
			filaPosition = modelo.size() - 1;
		} else {
			inicio = filaPosition;
		}
		for (int i = inicio; i <= filaPosition; i++) {
			total = Double.parseDouble(modelo.get(i).getTotal().toString());
			tarifaIva = (TarifaIva) cmbIva.getValue();
			tarifaIce = (TarifaIce) cmbIce.getValue();
			calcularFilaInversa(total, tarifaIva, tarifaIce, costoProducto);
			modelo.get(i).setPorcentajeUtilidad(porcentaje);
			modelo.get(i).setSubtotal(subtotal);
			modelo.get(i).setIce(ice);
			modelo.get(i).setIva(iva);
			modelo.get(i).setUtilidad(utilidad);
		}
		table.refresh();
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
		chkTieneIva.setSelected(true);
		cmbIva.setVisible(true);
		imgSinFoto = new Image(getClass().getResourceAsStream("../images/foto.jpg"));
		imgOriginal = imgSinFoto;
		imgProducto.setImage(imgSinFoto);

		txtCostoUnitario.setText("0");
		txtCostoLote.setText("0");
		txtCostoCompra.setText("0");
		txtCodigo.requestFocus();
		crearTablaPreciosUnitario();
		crearTablaPreciosLote();
		actualizaConValoresTodasLasTablas();
		tblPreciosUnitario.refresh();
		tblPreciosLote.refresh();
	}

	private void llenarEntidadAntesDeGuardar() {
		producto.setCodigo(txtCodigo.getText());
		producto.setNombre(txtNombre.getText());
		producto.setPeso(Double.parseDouble(txtPeso.getText()));
		producto.setUnidadMedidaPeso((UnidadMedidaPesoEnum) cmbUnidadMedidaPeso.getValue());
		producto.setCosto(Double.parseDouble(txtCostoCompra.getText()));
		if (imgProducto.getImage() != imgSinFoto) {
			producto.setFoto(ImageUtils.getByteFoto(imgProducto.getImage()));
		} else {
			producto.setFoto(null);
		}
		producto.setEsDeducible(chkEsDeducible.isSelected());
		producto.setTipoGastoDeducible((TipoGastoDeducible) cmbTipoGastoDeducible.getValue());
		producto.setSePuedeFraccionar(chkSePuedeFraccionar.isSelected());
		producto.setManejaInventario(chkManejaInventario.isSelected());
		producto.setStock(0.0);
		producto.setStockMinimo(Double.parseDouble(txtStockMinimo.getText()));
		producto.setFechaActualizacion(new Date());
		producto.setCategoriaProducto(categoriaProducto);
		producto.setTarifaIce((TarifaIce) cmbIce.getValue());
		producto.setUnidadMedidaCompra((UnidadMedida) cmbUnidadMedidaCompra.getValue());
		producto.setCantidadUnidadMedidaCompra(Double.parseDouble(txtCantidadCompra.getText()));
		producto.setUnidadMedidaVenta((UnidadMedida) cmbUnidadMedidaVenta.getValue());
		producto.setCantidadUnidadMedidaVenta(Double.parseDouble(txtCantidadVenta.getText()));
		producto.setTieneIva(chkTieneIva.isSelected());
	}

	private void guardarNuevo() {
		producto = new Producto();
		llenarEntidadAntesDeGuardar();
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
				productoPrecio.setPorcentajeUtilidadUnitario(preciosUnitarioList.get(i).getPorcentajeUtilidad());
				productoPrecio.setPorcentajeUtilidadLote(preciosLoteList.get(i).getPorcentajeUtilidad());
				productoPrecio.setPrecioBrutoUnitario(preciosUnitarioList.get(i).getSubtotal());
				productoPrecio.setPrecioBrutoLote(preciosLoteList.get(i).getSubtotal());
				productoPrecio.setUtilidadUnitario(preciosUnitarioList.get(i).getUtilidad());
				productoPrecio.setUtilidadLote(preciosLoteList.get(i).getUtilidad());

				ProductoPrecioController productoPrecioController = new ProductoPrecioController();
				error = productoPrecioController.createProductoPrecio(productoPrecio);
				if (error == null) {
					numeroVecesGuardadoCorrectamente++;
				}
				i++;
			}
			if (numeroVecesGuardadoCorrectamente == listaTipoPrecio.size()) {
				AlertsUtil.alertExito("Guardado correctamente");
				limpiarCampos();
				txtBuscar.setText("");
				btnBuscarClick(null);
			} else {
				AlertsUtil.alertError("Producto guardado correctamente, excepto los precios");
				limpiarCampos();
				txtBuscar.setText("");
				btnBuscarClick(null);
			}

		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {

	}

	private void eliminar() {
		List<ProductoPrecio> productoPrecioList = productoPrecioController.productoPrecioList(producto);
		if (productoPrecioList != null) {
			for (ProductoPrecio productoPrecio : productoPrecioList) {
				productoPrecioController.deleteProductoPrecio(productoPrecio);
			}
		}
		error = productoController.deleteProducto(producto);
		if (error == null) {
			btnNuevoClick(null);
			AlertsUtil.alertExito("Eliminado Correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
		btnNuevoClick(null);
		btnBuscarClick(null);
	}

	@FXML
	private void btnNuevoClick(ActionEvent event) {
		producto = new Producto();
		limpiarCampos();
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
	}

	@FXML
	private void btnGuardarClick(ActionEvent event) {
		producto.setNombre(txtNombre.getText());
		if (isCamposLlenos()) {
			if (btnGuardar.getText().toLowerCase().equals("actualizar")) {
				actualizar();
			} else {
				guardarNuevo();
			}
		} else {
			AlertsUtil.alertWarning("Datos incompletos, no es posible guardar");
		}

	}

	@FXML
	private void btnEliminarClick(ActionEvent event) {
		Optional<ButtonType> result = AlertsUtil
				.alertConfirmation("Está seguro que desea eliminar: \n" + producto.getNombre());
		if (result.get() == ButtonType.OK) {
			eliminar();
		}
	}

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<Producto> ciudadList = productoController.productoList(txtBuscar.getText());
		if (ciudadList != null) {
			productosList = FXCollections.observableList(ciudadList);
			tblLista.setItems(productosList);
		} else {
			productosList.clear();
		}
		paginar();
		btnNuevoClick(null);
		tblLista.requestFocus();
	}

	@FXML
	private void btnBuscarCategoriaClick(ActionEvent event) {

	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		Button btnCloseTab = (Button) event.getSource();
		Scene btnScene = btnCloseTab.getScene();
		TabPane thisTabPane = (TabPane) btnScene.lookup("#tpPrincipal");
		thisTabPane.getTabs().remove(tabIndex);
	}

	private void txtBuscarReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			btnBuscarClick(null);
			// System.out.println("Aqui error");
		}
	}

	@FXML
	private void btnCargarImagenClick(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				imgProducto.setImage(image);
			} catch (Exception ex) {
				AlertsUtil.alertError("Archivo no es imagen o imagen es muy grande");
			}
		}
	}

	@FXML
	private void btnEliminarImagenClick(ActionEvent event) {
		imgProducto.setImage(imgSinFoto);
	}

	@FXML
	private void btnUndoImagenClick(ActionEvent event) {
		imgProducto.setImage(imgOriginal);
	}

	@FXML
	private void chkTieneIvaClick(ActionEvent event) {
		if (chkTieneIva.isSelected()) {
			cmbIva.setVisible(true);
			cargarComboTarifaIva();
			actualizaConValoresTodasLasTablas();
		} else {
			cmbIva.setVisible(false);
			cargarComboTarifaIvaCero();
			actualizaConValoresTodasLasTablas();
		}
	}

	@FXML
	private void chkEsDeducibleClick(ActionEvent event) {
		if (chkEsDeducible.isSelected()) {
			cmbTipoGastoDeducible.setVisible(true);
		} else {
			cmbTipoGastoDeducible.setVisible(false);
		}
	}

	@FXML
	private void cmbIceClick(ActionEvent event) {
		actualizaConValoresTodasLasTablas();
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCategoriaProducto.getText().isEmpty()
				|| txtPeso.getText().isEmpty() || cmbUnidadMedidaPeso.getItems().size() == 0
				|| txtStockMinimo.getText().isEmpty() || cmbIva.getItems().size() == 0 || cmbIce.getItems().size() == 0
				|| cmbUnidadMedidaCompra.getItems().size() == 0 || cmbUnidadMedidaVenta.getItems().size() == 0
				|| txtCantidadCompra.getText().isEmpty() || txtCantidadVenta.getText().isEmpty()) {
			llenos = false;
		}
		if (chkEsDeducible.isSelected() && cmbTipoGastoDeducible.getItems().size() == 0) {
			llenos = false;
		}
		return llenos;
	}
}