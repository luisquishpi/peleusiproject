package ec.peleusi.views.fx.controllers;

import java.util.List;
import ec.peleusi.controllers.TipoRetencionController;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.TipoRetencionEnum;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TipoRetencionListModalFxController {

	@FXML
	private ComboBox<TipoRetencionEnum> cmbTipoRetencion;		
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<TipoRetencion> tblLista;
	@FXML
	private Pagination pagination;

	ObservableList<TipoRetencion> tipoRetencionsList;
	private TipoRetencionController tipoRetencionController = new TipoRetencionController();
	private TipoRetencion tipoRetencion;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {
		crearTabla();
		cargarComboTipoRetencion();
		paginar();		
	}

	private void paginar() {
		int count = tipoRetencionsList.size() / rowsPerPage;
		if (count < ((double) ( tipoRetencionsList.size()) / rowsPerPage))
			count++;
		if (count == 0 && tipoRetencionsList.size() > 0)
			count++;
		if (tipoRetencionsList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, tipoRetencionsList.size());
					tblLista.setItems(FXCollections.observableArrayList(tipoRetencionsList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<TipoRetencion>();
		TableColumn<TipoRetencion, String> codigoCol = new TableColumn<TipoRetencion, String>("Codigo");
		codigoCol.setCellValueFactory(new PropertyValueFactory<TipoRetencion, String>("codigo"));
		codigoCol.setPrefWidth(80);
		TableColumn<TipoRetencion, String> descripcionCol = new TableColumn<TipoRetencion, String>("Descripcion");
		descripcionCol.setCellValueFactory(new PropertyValueFactory<TipoRetencion, String>("descripcion"));
		descripcionCol.setPrefWidth(290);
		TableColumn<TipoRetencion, Double> porcentajeCol = new TableColumn<TipoRetencion, Double>("Porcentaje");
		porcentajeCol.setCellValueFactory(new PropertyValueFactory<TipoRetencion, Double>("porcentaje"));
		porcentajeCol.setPrefWidth(80);	
		tblLista.getColumns().addAll(codigoCol, descripcionCol, porcentajeCol);
		tipoRetencionsList = FXCollections.observableList(tipoRetencionController.tipoRetencionList());
		tblLista.setItems(tipoRetencionsList);
		final ObservableList<TipoRetencion> tipoRetencionListSelected = tblLista.getSelectionModel().getSelectedItems();
		tipoRetencionListSelected.addListener(escuchaCambiosEnTabla);		
	}

	private final ListChangeListener<TipoRetencion> escuchaCambiosEnTabla = new ListChangeListener<TipoRetencion>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TipoRetencion> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				tipoRetencion = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};
		private void cargarComboTipoRetencion() {
		cmbTipoRetencion.setItems(FXCollections.observableArrayList(TipoRetencionEnum.values()));
		}
		
		@FXML
		private void cmbTipoRetencionClick(ActionEvent event) {
			List<TipoRetencion> tipoRetencionList = tipoRetencionController.tipoRetencionTipoList(cmbTipoRetencion.getValue());
			if (tipoRetencionList != null) {
				tipoRetencionsList = FXCollections.observableList(tipoRetencionList);
				tblLista.setItems(tipoRetencionsList);
			} else {
				tipoRetencionsList.clear();
			}
			paginar();
			tblLista.requestFocus();			
		}		
	
	@FXML
	private void btnAceptarClick(ActionEvent event) {
		System.err.println("pr: " + tipoRetencion);
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		tipoRetencion= null;
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void paginationReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			btnAceptarClick(null);
		}	
	}
	
	public TipoRetencion getTipoRetencion() {
		return tipoRetencion;
	}
}
