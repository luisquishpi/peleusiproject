package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.TipoRetencionController;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.TipoRetencionEnum;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TipoRetencionListFxController extends AnchorPane {
	@FXML
	private TextField txtCodigo;
	@FXML
	private ComboBox<TipoRetencionEnum> cmbTipoRetencion;		
	@FXML
	private TextField txtDescripcion;
	@FXML
	private TextField txtPorcentaje;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<TipoRetencion> tblLista;
	@FXML
	TableColumn<TipoRetencion, Integer> idCol;
	@FXML
	TableColumn<TipoRetencion, String> codigoCol;
	@FXML
	TableColumn<TipoRetencion, TipoRetencionEnum> tipoRetencionCol;
	@FXML
	TableColumn<TipoRetencion, String> descripcionCol;
	@FXML
	TableColumn<TipoRetencion, Double> porcentajeCol;
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
	
	ObservableList<TipoRetencion> tipoRetencionsList;	
	private Integer posicionObjetoEnTabla;
	private TipoRetencion tipoRetencion;
	private TipoRetencionController tipoRetencionController = new TipoRetencionController();
	private String error = null;
	
	@FXML
	private void initialize() {
		tipoRetencionsList = FXCollections.observableList(tipoRetencionController.tipoRetencionList());
		tblLista.setItems(tipoRetencionsList);			
		codigoCol.setCellValueFactory(new PropertyValueFactory<TipoRetencion, String>("codigo"));
		tipoRetencionCol.setCellValueFactory(new PropertyValueFactory<TipoRetencion, TipoRetencionEnum>("tipo"));
		System.out.println("----" + tipoRetencionCol);
		descripcionCol.setCellValueFactory(new PropertyValueFactory<TipoRetencion, String>("descripcion"));
		porcentajeCol.setCellValueFactory(new PropertyValueFactory<TipoRetencion, Double>("porcentaje"));
		final ObservableList<TipoRetencion> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);			
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});		
		cargarComboTipoRetencion();
	}
		
	private final ListChangeListener<TipoRetencion> escuchaCambiosEnTabla = new ListChangeListener<TipoRetencion>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TipoRetencion> c) {
			cargarObjetoSeleccionadaEnFormulario();
		}
	};	

	public Object getObjetoSeleccionadoDeTabla() {
		if (tblLista != null) {
			if (tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				return tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
		return null;
	}
	
	private void cargarComboTipoRetencion() {
		cmbTipoRetencion.setItems( FXCollections.observableArrayList(TipoRetencionEnum.values()));
	//	cmbTipoRetencion.getItems().setAll(TipoRetencionEnum.values()); 		
	}

	
	private void cargarObjetoSeleccionadaEnFormulario() {
		tipoRetencion = (TipoRetencion) getObjetoSeleccionadoDeTabla();
		if (tipoRetencion != null) {
			posicionObjetoEnTabla = tipoRetencionsList.indexOf(tipoRetencion);
			txtCodigo.setText(tipoRetencion.getCodigo());				
			cmbTipoRetencion.getSelectionModel().select(tipoRetencion.getTipoRetencionEnum());
			//cmbTipoRetencion.setSelectedItem(tipoRetencion.getTipoRetencionEnum());		
			txtDescripcion.setText(tipoRetencion.getDescripcion());
			txtPorcentaje.setText(Double.toString(tipoRetencion.getPorcentaje()));			
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = tipoRetencionController.createTipoRetencion(tipoRetencion);
		if (error == null) {
			tipoRetencionsList.add(tipoRetencion);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = tipoRetencionController.update(tipoRetencion);
		if (error == null) {
			tipoRetencionsList.set(posicionObjetoEnTabla, tipoRetencion);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = tipoRetencionController.delete(tipoRetencion);
		if (error == null){
			tipoRetencionsList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tipoRetencion.setCodigo(txtCodigo.getText());		
		tipoRetencion.setTipoRetencionEnum((TipoRetencionEnum) cmbTipoRetencion.getValue());	
		//tipoRetencion.setTipoRetencionEnum((TipoRetencionEnum) cmbTipoRetencion.getSelectedItem());
		tipoRetencion.setDescripcion(txtDescripcion.getText());
		tipoRetencion.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
	}

	private void limpiarCampos() {
		tipoRetencion = new TipoRetencion();
		txtCodigo.setText("");
		
		
		txtDescripcion.setText("");
		txtPorcentaje.setText("0");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtCodigo.requestFocus();
	}

	private boolean camposLlenosTarifaIva() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtDescripcion.getText().isEmpty() || txtPorcentaje.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	@FXML
	private void btnNuevoClick(ActionEvent event) {
		limpiarCampos();
	}

	@FXML
	private void btnGuardarClick(ActionEvent event) {
		llenarEntidadAntesDeGuardar();
		if (camposLlenosTarifaIva()) {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + tipoRetencion.getDescripcion());
		if (result.get() == ButtonType.OK) {
			eliminar();
		}
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<TipoRetencion> tipoRetencionList = tipoRetencionController.getTipoRetencionList(txtBuscar.getText());
		if (tipoRetencionList != null) {
			tipoRetencionsList = FXCollections.observableList(tipoRetencionList);
			tblLista.setItems(tipoRetencionsList);
		} else {
			tipoRetencionsList.clear();
		}
		btnNuevoClick(null);
		tblLista.requestFocus();
	}

	@FXML
	private void txtBuscarReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			btnBuscarClick(null);			
		}
	}
	
	@FXML
	private void tblListaReleased(KeyEvent event) {
		TableViewUtils.tblListaReleased(event,txtBuscar);
	}
}
