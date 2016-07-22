package ec.peleusi.views.fx.controllers;

import java.util.List;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
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

public class CiudadListModalController {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<Ciudad> tblLista;
	@FXML
	private Pagination pagination;

	ObservableList<Ciudad> ciudadsList;
	private CiudadController ciudadController = new CiudadController();
	private Ciudad ciudad;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {
		crearTabla();
		paginar();

	}

	private void paginar() {
		int count = ciudadsList.size() / rowsPerPage;
		if (count < ((double) ( ciudadsList.size()) / rowsPerPage))
			count++;
		if (count == 0 && ciudadsList.size() > 0)
			count++;
		if (ciudadsList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, ciudadsList.size());
					tblLista.setItems(FXCollections.observableArrayList(ciudadsList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<Ciudad>();
		TableColumn<Ciudad, String> nombreCol = new TableColumn<Ciudad, String>("Nombre");
		nombreCol.setCellValueFactory(new PropertyValueFactory<Ciudad, String>("nombre"));
		nombreCol.setPrefWidth(425);
		tblLista.getColumns().addAll(nombreCol);
		ciudadsList = FXCollections.observableList(ciudadController.ciudadList());
		tblLista.setItems(ciudadsList);
		final ObservableList<Ciudad> ciudadListSelected = tblLista.getSelectionModel().getSelectedItems();
		ciudadListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<Ciudad> escuchaCambiosEnTabla = new ListChangeListener<Ciudad>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Ciudad> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				ciudad = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<Ciudad> ciudadList = ciudadController.ciudadList(txtBuscar.getText());
		if (ciudadList != null) {
			ciudadsList = FXCollections.observableList(ciudadList);
			tblLista.setItems(ciudadsList);
		} else {
			ciudadsList.clear();
		}
		paginar();
		tblLista.requestFocus();
	}

	@FXML
	private void btnAceptarClick(ActionEvent event) {
		System.err.println("pr: " + ciudad);
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		ciudad = null;
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

	public Ciudad getCiudad() {
		return ciudad;
	}

}
