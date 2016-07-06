package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.models.entities.TarifaIce;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class TarifaIceListFxController extends GenericController {
	@FXML
	private TextField txtCodigo;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtPorcentaje;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<TarifaIce> tblLista;
	@FXML
	TableColumn<TarifaIce, Integer> idCol;
	@FXML
	TableColumn<TarifaIce, String> codigoCol;
	@FXML
	TableColumn<TarifaIce, String> nombreCol;
	@FXML
	TableColumn<TarifaIce, Double> porcentajeCol;
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

	ObservableList<TarifaIce> tarifaIcesList;
	private Integer posicionObjetoEnTabla;
	private TarifaIce tarifaIce;
	private TarifaIceController tarifaIceController = new TarifaIceController();
	private String error = null;

	@FXML
	private void initialize() {
		tarifaIcesList = FXCollections.observableList(tarifaIceController.tarifaIceList());
		tblLista.setItems(tarifaIcesList);
		idCol.setMinWidth(0);
		idCol.setMaxWidth(0);
		idCol.setPrefWidth(0);
		idCol.setCellValueFactory(new PropertyValueFactory<TarifaIce, Integer>("id"));
		codigoCol.setCellValueFactory(new PropertyValueFactory<TarifaIce, String>("codigo"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<TarifaIce, String>("nombre"));
		porcentajeCol.setCellValueFactory(new PropertyValueFactory<TarifaIce, Double>("porcentaje"));
		final ObservableList<TarifaIce> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
	}

	private final ListChangeListener<TarifaIce> escuchaCambiosEnTabla = new ListChangeListener<TarifaIce>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TarifaIce> c) {
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

	private void cargarObjetoSeleccionadaEnFormulario() {
		tarifaIce = (TarifaIce) getObjetoSeleccionadoDeTabla();
		if (tarifaIce != null) {
			posicionObjetoEnTabla = tarifaIcesList.indexOf(tarifaIce);
			txtCodigo.setText(tarifaIce.getCodigo());
			txtNombre.setText(tarifaIce.getNombre());
			txtPorcentaje.setText(Double.toString(tarifaIce.getPorcentaje()));
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = tarifaIceController.createTarifaIce(tarifaIce);
		if (error == null) {
			tarifaIcesList.add(tarifaIce);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = tarifaIceController.updateTarifaIce(tarifaIce);
		if (error == null) {
			tarifaIcesList.set(posicionObjetoEnTabla, tarifaIce);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = tarifaIceController.deleteTarifaIce(tarifaIce);
		if (error == null) {
			tarifaIcesList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tarifaIce.setCodigo(txtCodigo.getText());
		tarifaIce.setNombre(txtNombre.getText());
		tarifaIce.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
	}

	private void limpiarCampos() {
		tarifaIce = new TarifaIce();
		txtCodigo.setText("");
		txtNombre.setText("");
		txtPorcentaje.setText("0");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtCodigo.requestFocus();
	}

	private boolean camposLlenosTarifaIce() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtPorcentaje.getText().isEmpty())
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
		if (camposLlenosTarifaIce()) {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + tarifaIce.getNombre());
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
		List<TarifaIce> tarifaIceList = tarifaIceController.getTarifaIceList(txtBuscar.getText());
		if (tarifaIceList != null) {
			tarifaIcesList = FXCollections.observableList(tarifaIceList);
			tblLista.setItems(tarifaIcesList);
		} else {
			tarifaIcesList.clear();
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
		TableViewUtils.tblListaReleased(event, txtBuscar);
	}
}
