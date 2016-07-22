package ec.peleusi.views.fx.controllers;

import java.util.List;

import ec.peleusi.controllers.ProductoPrecioController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.ProductoPrecio;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProductoListModalController {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<ProductoPrecio> tblLista;
	@FXML
	private Pagination pagination;
	@FXML
	private ComboBox<TipoPrecio> cmbTipoPrecio;
	@FXML
	private Label lblTipoPrecio;

	ObservableList<ProductoPrecio> productoPrecioList;
	private ProductoPrecioController productoPrecioController = new ProductoPrecioController();
	private ProductoPrecio productoPrecio;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {

		/*
		 * ProductoPrecioController p=new ProductoPrecioController(); TipoPrecio
		 * tipoPrecio=new TipoPrecio(); tipoPrecio.setId(2); List<Object> lp=
		 * new ArrayList<Object>(); lp= p.productoConPrecioList(tipoPrecio);
		 * System.out.println("lp: "+lp);
		 */
		cargarComboTipoPrecio();
		crearTabla();
		paginar();

	}

	private void cargarComboTipoPrecio() {

		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		List<TipoPrecio> listaTipoPrecio = tipoPrecioController.tipoPrecioList();
		cmbTipoPrecio.setItems(FXCollections.observableArrayList(listaTipoPrecio));
		if (cmbTipoPrecio != null) {
			cmbTipoPrecio.getSelectionModel().select(0);
			lblTipoPrecio.setText("PRECIOS: " + cmbTipoPrecio.getValue().getNombre());
		}
	}

	private void paginar() {
		int count = productoPrecioList.size() / rowsPerPage;
		if (count < ((double) (productoPrecioList.size()) / rowsPerPage))
			count++;
		if (count == 0 && productoPrecioList.size() > 0)
			count++;
		if (productoPrecioList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, productoPrecioList.size());
					tblLista.setItems(
							FXCollections.observableArrayList(productoPrecioList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<ProductoPrecio>();
		TableColumn<ProductoPrecio, String> codigoCol = new TableColumn<ProductoPrecio, String>("Código");
		codigoCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getProducto().getCodigo()));

		codigoCol.setPrefWidth(100);

		TableColumn<ProductoPrecio, String> nombreCol = new TableColumn<ProductoPrecio, String>("Nombre");
		nombreCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getProducto().getNombre()));
		nombreCol.setPrefWidth(180);

		TableColumn<ProductoPrecio, Double> stockCol = new TableColumn<ProductoPrecio, Double>("Stock");
		stockCol.setCellValueFactory(
				cellData -> new SimpleDoubleProperty(cellData.getValue().getProducto().getStock()).asObject());
		stockCol.setPrefWidth(50);

		TableColumn<ProductoPrecio, Double> precioUnitarioCol = new TableColumn<ProductoPrecio, Double>(
				"Precio Unidad");
		precioUnitarioCol.setCellValueFactory(new PropertyValueFactory<ProductoPrecio, Double>("precioBrutoUnitario"));
		precioUnitarioCol.setPrefWidth(80);

		TableColumn<ProductoPrecio, Double> precioLoteCol = new TableColumn<ProductoPrecio, Double>("Precio Lote");
		precioLoteCol.setCellValueFactory(new PropertyValueFactory<ProductoPrecio, Double>("precioBrutoLote"));
		precioLoteCol.setPrefWidth(80);

		TableColumn<ProductoPrecio, Boolean> tieneIvaCol = new TableColumn<ProductoPrecio, Boolean>("Iva");
		tieneIvaCol.setCellValueFactory(
				cellData -> new SimpleBooleanProperty(cellData.getValue().getProducto().getTieneIva()));
		tieneIvaCol.setCellFactory(CheckBoxTableCell.forTableColumn(tieneIvaCol));
		tieneIvaCol.setPrefWidth(35);

		tblLista.getColumns().addAll(codigoCol, nombreCol, stockCol, precioUnitarioCol, precioLoteCol, tieneIvaCol);

		productoPrecioList = FXCollections
				.observableList(productoPrecioController.productoConPrecioList("", cmbTipoPrecio.getValue()));
		tblLista.setItems(productoPrecioList);

		final ObservableList<ProductoPrecio> productoPrecioListSelected = tblLista.getSelectionModel()
				.getSelectedItems();
		productoPrecioListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<ProductoPrecio> escuchaCambiosEnTabla = new ListChangeListener<ProductoPrecio>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends ProductoPrecio> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				productoPrecio = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<ProductoPrecio> pPrecioList = productoPrecioController.productoConPrecioList(txtBuscar.getText(),
				cmbTipoPrecio.getValue());
		if (pPrecioList != null) {
			productoPrecioList = FXCollections.observableList(pPrecioList);
			tblLista.setItems(productoPrecioList);
		} else {
			productoPrecioList.clear();
		}
		paginar();
		tblLista.requestFocus();

	}

	@FXML
	private void btnAceptarClick(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		productoPrecio = null;
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
	private void paginationReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			btnAceptarClick(null);
		}
	}

	@FXML
	private void cmbTipoPrecioClick(ActionEvent event) {
		lblTipoPrecio.setText("PRECIOS: " + cmbTipoPrecio.getValue().getNombre());
		btnBuscarClick(null);
	}

	public ProductoPrecio getProducto() {
		return productoPrecio;
	}
}
