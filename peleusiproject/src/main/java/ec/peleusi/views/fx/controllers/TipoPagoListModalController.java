package ec.peleusi.views.fx.controllers;

import java.util.List;

import ec.peleusi.controllers.TipoPagoController;
import ec.peleusi.models.entities.TipoPago;
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

public class TipoPagoListModalController {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<TipoPago> tblLista;
	@FXML
	private Pagination pagination;

	ObservableList<TipoPago> tipoPagosList;
	private TipoPagoController tipoPagoController = new TipoPagoController();
	private TipoPago tipoPago;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {
		crearTabla();
		paginar();

	}

	private void paginar() {
		int count = tipoPagosList.size() / rowsPerPage;
		if (count < ((double) ( tipoPagosList.size()) / rowsPerPage))
			count++;
		if (count == 0 && tipoPagosList.size() > 0)
			count++;
		if (tipoPagosList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, tipoPagosList.size());
					tblLista.setItems(FXCollections.observableArrayList(tipoPagosList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<TipoPago>();
		TableColumn<TipoPago, String> nombreCol = new TableColumn<TipoPago, String>("Nombre");
		nombreCol.setCellValueFactory(new PropertyValueFactory<TipoPago, String>("nombre"));
		nombreCol.setPrefWidth(425);
		tblLista.getColumns().addAll(nombreCol);
		tipoPagosList = FXCollections.observableList(tipoPagoController.tipoPagoList());
		tblLista.setItems(tipoPagosList);
		final ObservableList<TipoPago> tipoPagoListSelected = tblLista.getSelectionModel().getSelectedItems();
		tipoPagoListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<TipoPago> escuchaCambiosEnTabla = new ListChangeListener<TipoPago>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TipoPago> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				tipoPago = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<TipoPago> tipoPagoList = tipoPagoController.getTipoPagoList(txtBuscar.getText());
		if (tipoPagoList != null) {
			tipoPagosList = FXCollections.observableList(tipoPagoList);
			tblLista.setItems(tipoPagosList);
		} else {
			tipoPagosList.clear();
		}
		paginar();
		tblLista.requestFocus();
	}

	@FXML
	private void btnAceptarClick(ActionEvent event) {
		System.err.println("pr: " + tipoPago);
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		tipoPago = null;
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

	public TipoPago getTipoPago() {
		return tipoPago;
	}

}
