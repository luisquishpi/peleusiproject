package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.TipoCalificacionClienteController;
import ec.peleusi.models.entities.TipoCalificacionCliente;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TipoCalificacionClienteListFxController extends GenericController {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<TipoCalificacionCliente> tblLista;
	@FXML
	TableColumn<TipoCalificacionCliente, Integer> idCol;
	@FXML
	TableColumn<TipoCalificacionCliente, String> nombreCol;
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
	ObservableList<TipoCalificacionCliente> tipoCalificacionClientesList;
	private Integer posicionObjetoEnTabla;
	private TipoCalificacionCliente tipoCalificacionCliente;
	private TipoCalificacionClienteController tipoCalificacionClienteController = new TipoCalificacionClienteController();
	private String error = null;

	@FXML
	private void initialize() {
		tipoCalificacionClientesList = FXCollections
				.observableList(tipoCalificacionClienteController.tipoCalificacionClienteList());
		tblLista.setItems(tipoCalificacionClientesList);		
		nombreCol.setCellValueFactory(new PropertyValueFactory<TipoCalificacionCliente, String>("nombre"));
		final ObservableList<TipoCalificacionCliente> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
	}

	private final ListChangeListener<TipoCalificacionCliente> escuchaCambiosEnTabla = new ListChangeListener<TipoCalificacionCliente>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TipoCalificacionCliente> c) {
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
		tipoCalificacionCliente = (TipoCalificacionCliente) getObjetoSeleccionadoDeTabla();
		if (tipoCalificacionCliente != null) {
			posicionObjetoEnTabla = tipoCalificacionClientesList.indexOf(tipoCalificacionCliente);
			txtNombre.setText(tipoCalificacionCliente.getNombre());
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = tipoCalificacionClienteController.createTipoCalificacionCliente(tipoCalificacionCliente);
		if (error == null) {
			tipoCalificacionClientesList.add(tipoCalificacionCliente);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = tipoCalificacionClienteController.updateTipoCalificacionCliente(tipoCalificacionCliente);
		if (error == null) {
			tipoCalificacionClientesList.set(posicionObjetoEnTabla, tipoCalificacionCliente);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = tipoCalificacionClienteController.deleteTipoCalificacionCliente(tipoCalificacionCliente);
		if (error == null) {
			tipoCalificacionClientesList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tipoCalificacionCliente.setNombre(txtNombre.getText());
	}

	private void limpiarCampos() {
		tipoCalificacionCliente = new TipoCalificacionCliente();
		txtNombre.setText("");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtNombre.requestFocus();
	}

	private boolean camposLlenosTipoCalificacionCliente() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty())
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
		if (camposLlenosTipoCalificacionCliente()) {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + tipoCalificacionCliente.getNombre());
		if (result.get() == ButtonType.OK) {
			eliminar();
		}
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		Button btnCloseTab = (Button) event.getSource();
		Scene btnScene = btnCloseTab.getScene();
		TabPane thisTabPane = (TabPane) btnScene.lookup("#tpPrincipal");
		thisTabPane.getTabs().remove(tabIndex);
	}

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<TipoCalificacionCliente> tipoCalificacionClienteList = tipoCalificacionClienteController
				.getTipoCalificacionClienteList(txtBuscar.getText());
		if (tipoCalificacionClientesList != null) {
			tipoCalificacionClientesList = FXCollections.observableList(tipoCalificacionClienteList);
			tblLista.setItems(tipoCalificacionClientesList);
		} else {
			tipoCalificacionClientesList.clear();
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
