package ec.peleusi.views.fx.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.SeteoController;
import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.controllers.TipoGastoDeducibleController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.TablaPreciosProducto;
import ec.peleusi.models.entities.TarifaIce;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.models.entities.TipoGastoDeducible;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.models.entities.UnidadMedida;
import ec.peleusi.utils.UnidadMedidaPesoEnum;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;

public class ProductoListFxController extends AnchorPane {

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
	private TableView<Producto> tblLista;
	@FXML
	private TableView<TablaPreciosProducto> tblPreciosUnitario;
	@FXML
	private Pagination pagination;
	@FXML
	private ImageView imgProducto;
	@FXML
	VBox vbParaTabla;

	ObservableList<Producto> productosList;
	ObservableList<TablaPreciosProducto> PreciosUnitarioList;

	private Integer posicionObjetoEnTabla;
	private ProductoController productoController = new ProductoController();
	private String error = null;
	private Producto producto;
	final static int rowsPerPage = 100;
	private Image imgOriginal;
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
	CheckBox chkTieneIva;
	@FXML
	CheckBox chkSePuedeFraccionar;
	@FXML
	CheckBox chkManejaInventario;
	@FXML
	CheckBox chkEsDeducible;

	@FXML
	private void initialize() {
		crearTabla();
		paginar();
		cargarCombosUnidaMedida();
		cargarComboTarifaIva();
		cargarComboTarifaIce();
		cargarComboTipoGastoDeducible();
		limpiarCampos();
		crearColumnasTablaPreciosUnitario();

		tarifaIva = cmbIva.getValue();
		tarifaIce = cmbIce.getValue();
		Double costoProducto = Double.parseDouble(txtCostoUnitario.getText());
		cargarTablaConValores(costoProducto, tarifaIva, tarifaIce);
		tblPreciosUnitario.setItems(PreciosUnitarioList);

		txtCostoCompra.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txtCostoCompraFocusLost();
				}
			}
		});

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});

	}

	private void txtCostoCompraFocusLost() {
		txtCostoLote.setText(txtCostoCompra.getText());
		Double costoUnitario = (Double.parseDouble(txtCostoCompra.getText())
				* Double.parseDouble(txtCantidadVenta.getText())) / Double.parseDouble(txtCantidadCompra.getText());
		txtCostoUnitario.setText(costoUnitario.toString());
		// actualizaConValoresTodasLasTablas();
	}

	private void crearColumnasTablaPreciosUnitario() {
		PreciosUnitarioList = tblPreciosUnitario.getItems();
		tipoPrecioCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, String>("nombre"));
		porcentajeUtilidadCol
				.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("porcentajeUtilidad"));
		subtotalCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("subtotal"));
		iceCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("ice"));
		ivaCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("iva"));
		totalCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("total"));
		utilidadCol.setCellValueFactory(new PropertyValueFactory<TablaPreciosProducto, Double>("utilidad"));
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
			// pagination.setPageFactory(this::createSubList);
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

	private void crearTabla() {
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
			txtNombre.setText(producto.getNombre());
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

	private void cargarTablaConValores(Double costoProducto, TarifaIva tarifaIva, TarifaIce tarifaIce) {
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
			PreciosUnitarioList.add(tablaPreciosProducto);
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
		chkTieneIva.setSelected(true);
		cmbTipoGastoDeducible.setVisible(false);
		imgProducto.setImage(new Image(getClass().getResourceAsStream("../images/foto.jpg")));
		txtCostoUnitario.setText("0");
		txtCostoLote.setText("0");
		txtCostoCompra.setText("0");
		txtCodigo.requestFocus();
		// actualizaConValoresTodasLasTablas();
	}

	private void guardarNuevo() {

	}

	private void actualizar() {

	}

	private void eliminar() {

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
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void txtBuscarReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			btnBuscarClick(null);
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
		Image image = new Image(getClass().getResourceAsStream("../images/foto.jpg"));
		imgProducto.setImage(image);
	}

	@FXML
	private void btnUndoImagenClick(ActionEvent event) {
		imgProducto.setImage(imgOriginal);
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().trim().isEmpty())
			llenos = false;
		return llenos;
	}
}