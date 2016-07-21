package ec.peleusi.views.fx.controllers;

import java.util.List;

import ec.peleusi.controllers.ProductoController;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class ProductoListCostoModalController {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<Producto> tblLista;
	@FXML
	private Pagination pagination;

	ObservableList<Producto> productosList;
	private ProductoController productoController = new ProductoController();
	private Producto producto;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {
		crearTabla();
		paginar();

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

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<Producto>();
		TableColumn<Producto, String> codigoCol = new TableColumn<Producto, String>("Código");
		codigoCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("codigo"));
		codigoCol.setPrefWidth(100);

		TableColumn<Producto, String> nombreCol = new TableColumn<Producto, String>("Nombre");
		nombreCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));
		nombreCol.setPrefWidth(150);

		TableColumn<Producto, Double> stockCol = new TableColumn<Producto, Double>("Stock");
		stockCol.setCellValueFactory(new PropertyValueFactory<Producto, Double>("stock"));
		stockCol.setPrefWidth(50);

		TableColumn<Producto, Double> costoCol = new TableColumn<Producto, Double>("Costo");
		costoCol.setCellValueFactory(new PropertyValueFactory<Producto, Double>("costo"));
		costoCol.setPrefWidth(50);

		TableColumn<Producto, String> unidadMedidaCompraCol = new TableColumn<Producto, String>("Medida");
		unidadMedidaCompraCol.setCellValueFactory(new PropertyValueFactory<Producto, String>("unidadMedidaCompra"));
		unidadMedidaCompraCol.setPrefWidth(70);

		// TableColumn<Producto, Boolean> tieneIvaCol = new
		// TableColumn<Producto, Boolean>("Iva");
		// tieneIvaCol.setCellValueFactory(new PropertyValueFactory<Producto,
		// Boolean>("tieneIva"));
		// tieneIvaCol.setPrefWidth(30);

		TableColumn<Producto, Boolean> tieneIvaCol = new TableColumn<Producto, Boolean>("Iva");
		tieneIvaCol.setCellValueFactory(new PropertyValueFactory<Producto, Boolean>("tieneIva"));
		tieneIvaCol.setCellFactory(CheckBoxTableCell.forTableColumn(tieneIvaCol));
		tieneIvaCol.setPrefWidth(35);

		tblLista.getColumns().addAll(codigoCol, nombreCol, stockCol, unidadMedidaCompraCol, tieneIvaCol);
		productosList = FXCollections.observableList(productoController.productoList());
		tblLista.setItems(productosList);
		final ObservableList<Producto> productoListSelected = tblLista.getSelectionModel().getSelectedItems();
		productoListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<Producto> escuchaCambiosEnTabla = new ListChangeListener<Producto>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Producto> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				producto = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};

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
		tblLista.requestFocus();
	}

	@FXML
	private void btnAceptarClick(ActionEvent event) {
		System.err.println("pr: " + producto);
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		producto = null;
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

	public Producto getProducto() {
		return producto;
	}

}
