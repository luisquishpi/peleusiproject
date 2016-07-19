package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.UnidadMedida;
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

public class UnidadMedidaListFxController extends GenericController {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtAbreviatura;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<UnidadMedida> tblLista;
	@FXML
	TableColumn<UnidadMedida, String> nombreCol;
	@FXML
	TableColumn<UnidadMedida, String> abreviaturaCol;
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
	ObservableList<UnidadMedida> unidadMedidasList;
	private Integer posicionObjetoEnTabla;
	private UnidadMedida unidadMedida;
	private UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
	private String error = null;

	@FXML
	private void initialize() {
		unidadMedidasList = FXCollections.observableList(unidadMedidaController.unidadMedidaList());
		tblLista.setItems(unidadMedidasList);
		nombreCol.setCellValueFactory(new PropertyValueFactory<UnidadMedida, String>("nombre"));
		abreviaturaCol.setCellValueFactory(new PropertyValueFactory<UnidadMedida, String>("abreviatura"));
		final ObservableList<UnidadMedida> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
	}

	private final ListChangeListener<UnidadMedida> escuchaCambiosEnTabla = new ListChangeListener<UnidadMedida>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends UnidadMedida> c) {
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
		unidadMedida = (UnidadMedida) getObjetoSeleccionadoDeTabla();
		if (unidadMedida != null) {
			posicionObjetoEnTabla = unidadMedidasList.indexOf(unidadMedida);
			txtNombre.setText(unidadMedida.getNombre());
			txtAbreviatura.setText(unidadMedida.getAbreviatura());
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = unidadMedidaController.createUnidadMedida(unidadMedida);
		if (error == null) {
			unidadMedidasList.add(unidadMedida);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = unidadMedidaController.updateUnidadMedida(unidadMedida);
		if (error == null) {
			unidadMedidasList.set(posicionObjetoEnTabla, unidadMedida);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = unidadMedidaController.deleteUnidadMedida(unidadMedida);
		if (error == null) {
			unidadMedidasList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		unidadMedida.setNombre(txtNombre.getText());
		unidadMedida.setAbreviatura(txtAbreviatura.getText());
	}

	private void limpiarCampos() {
		unidadMedida = new UnidadMedida();
		txtNombre.setText("");
		txtAbreviatura.setText("");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtNombre.requestFocus();
	}

	private boolean camposLlenosUnidadMedida() {
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
		if (camposLlenosUnidadMedida()) {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + unidadMedida.getNombre());
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
		List<UnidadMedida> unidadMedidaList = unidadMedidaController.getUnidadMedidaList(txtBuscar.getText());
		if (unidadMedidaList != null) {
			unidadMedidasList = FXCollections.observableList(unidadMedidaList);
			tblLista.setItems(unidadMedidasList);
		} else {
			unidadMedidasList.clear();
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
