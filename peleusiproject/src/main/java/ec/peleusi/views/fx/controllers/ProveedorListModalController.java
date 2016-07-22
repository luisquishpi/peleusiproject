package ec.peleusi.views.fx.controllers;

import java.util.List;

import ec.peleusi.controllers.ProveedorController;
import ec.peleusi.models.entities.Proveedor;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ProveedorListModalController {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<Proveedor> tblLista;
	@FXML
	private Pagination pagination;

	ObservableList<Proveedor> proveedorsList;
	private ProveedorController proveedorController = new ProveedorController();
	private Proveedor proveedor;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {
		crearTabla();
		paginar();

	}

	private void paginar() {
		int count = proveedorsList.size() / rowsPerPage;
		if (count < ((double) ( proveedorsList.size()) / rowsPerPage))
			count++;
		if (count == 0 && proveedorsList.size() > 0)
			count++;
		if (proveedorsList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, proveedorsList.size());
					tblLista.setItems(FXCollections.observableArrayList(proveedorsList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<Proveedor>();
		TableColumn<Proveedor, String> identificacionCol = new TableColumn<Proveedor, String>("Identificación");
		identificacionCol.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("identificacion"));
		identificacionCol.setPrefWidth(120);
		TableColumn<Proveedor, String> razonSocialCol = new TableColumn<Proveedor, String>("RazonSocial");
		razonSocialCol.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("razonSocial"));
		razonSocialCol.setPrefWidth(195);		
		TableColumn<Proveedor, String> tipoIdentificacionCol = new TableColumn<Proveedor, String>("Tipo Identificación");
		tipoIdentificacionCol.setCellValueFactory(new PropertyValueFactory<Proveedor, String>("tipoIdentificacion"));
		tipoIdentificacionCol.setPrefWidth(110);		
		
		tblLista.getColumns().addAll(identificacionCol, razonSocialCol, tipoIdentificacionCol);
		proveedorsList = FXCollections.observableList(proveedorController.proveedorList());
		tblLista.setItems(proveedorsList);
		final ObservableList<Proveedor> proveedorListSelected = tblLista.getSelectionModel().getSelectedItems();
		proveedorListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<Proveedor> escuchaCambiosEnTabla = new ListChangeListener<Proveedor>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Proveedor> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				proveedor = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<Proveedor> proveedorList = proveedorController.getProveedorList(txtBuscar.getText());
		if (proveedorList != null) {
			proveedorsList = FXCollections.observableList(proveedorList);
			tblLista.setItems(proveedorsList);
		} else {
			proveedorsList.clear();
		}
		paginar();
		tblLista.requestFocus();
	}

	@FXML
	private void btnAceptarClick(ActionEvent event) {
		System.err.println("pr: " + proveedor);
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		proveedor = null;
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

	public Proveedor getProveedor() {
		return proveedor;
	}

}
