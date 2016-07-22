package ec.peleusi.views.fx.controllers;

import java.util.List;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.UnidadMedida;
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

public class UnidadMedidaListModalController {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<UnidadMedida> tblLista;
	@FXML
	private Pagination pagination;

	ObservableList<UnidadMedida> unidadMedidasList;
	private UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
	private UnidadMedida unidadMedida;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {
		crearTabla();
		paginar();

	}

	private void paginar() {
		int count = unidadMedidasList.size() / rowsPerPage;
		if (count < ((double) ( unidadMedidasList.size()) / rowsPerPage))
			count++;
		if (count == 0 && unidadMedidasList.size() > 0)
			count++;
		if (unidadMedidasList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, unidadMedidasList.size());
					tblLista.setItems(FXCollections.observableArrayList(unidadMedidasList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<UnidadMedida>();
		TableColumn<UnidadMedida, String> nombreCol = new TableColumn<UnidadMedida, String>("Nombre");
		nombreCol.setCellValueFactory(new PropertyValueFactory<UnidadMedida, String>("nombre"));
		nombreCol.setPrefWidth(300);
		TableColumn<UnidadMedida, String> abreviaturaCol = new TableColumn<UnidadMedida, String>("Abreviatura");
		abreviaturaCol.setCellValueFactory(new PropertyValueFactory<UnidadMedida, String>("abreviatura"));
		abreviaturaCol.setPrefWidth(125);		
		tblLista.getColumns().addAll(nombreCol, abreviaturaCol);
		unidadMedidasList = FXCollections.observableList(unidadMedidaController.unidadMedidaList());
		tblLista.setItems(unidadMedidasList);
		final ObservableList<UnidadMedida> unidadMedidaListSelected = tblLista.getSelectionModel().getSelectedItems();
		unidadMedidaListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<UnidadMedida> escuchaCambiosEnTabla = new ListChangeListener<UnidadMedida>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends UnidadMedida> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				unidadMedida = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<UnidadMedida> unidadMedidaList = unidadMedidaController.getUnidadMedidaList(txtBuscar.getText());
		if (unidadMedidaList != null) {
			unidadMedidasList = FXCollections.observableList(unidadMedidaList);
			tblLista.setItems(unidadMedidasList);
		} else {
			unidadMedidasList.clear();
		}
		paginar();
		tblLista.requestFocus();
	}

	@FXML
	private void btnAceptarClick(ActionEvent event) {
		System.err.println("pr: " + unidadMedida);
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		unidadMedida = null;
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

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

}
