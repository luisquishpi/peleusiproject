package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.TipoPrecio;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TipoPrecioListFxController extends AnchorPane {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtPorcentaje;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<TipoPrecio> tblLista;
	@FXML
	TableColumn<TipoPrecio, String> idCol;
	@FXML
	TableColumn<TipoPrecio, String> nombreCol;
	@FXML
	TableColumn<TipoPrecio, Double> porcentajeCol;
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

	ObservableList<TipoPrecio> tipoPreciosList;
	private Integer posicionObjetoEnTabla;
	private TipoPrecio tipoPrecio;
	private TipoPrecioController tipoPrecioController = new TipoPrecioController();
	private String error = null;

	@FXML
	private void initialize() {
		tipoPreciosList = FXCollections.observableList(tipoPrecioController.tipoPrecioList());
		tblLista.setItems(tipoPreciosList);
		idCol.setMinWidth(0);
		idCol.setMaxWidth(0);
		idCol.setPrefWidth(0);
		nombreCol.setCellValueFactory(new PropertyValueFactory<TipoPrecio, String>("nombre"));
		porcentajeCol.setCellValueFactory(new PropertyValueFactory<TipoPrecio, Double>("porcentaje"));
		final ObservableList<TipoPrecio> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
	}

	private final ListChangeListener<TipoPrecio> escuchaCambiosEnTabla = new ListChangeListener<TipoPrecio>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TipoPrecio> c) {
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
		tipoPrecio = (TipoPrecio) getObjetoSeleccionadoDeTabla();
		if (tipoPrecio != null) {
			posicionObjetoEnTabla = tipoPreciosList.indexOf(tipoPrecio);
			txtNombre.setText(tipoPrecio.getNombre());
			txtPorcentaje.setText(Double.toString(tipoPrecio.getPorcentaje()));
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = tipoPrecioController.createTipoPrecio(tipoPrecio);
		if (error == null) {
			tipoPreciosList.add(tipoPrecio);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = tipoPrecioController.updateTipoPrecio(tipoPrecio);
		if (error == null) {
			tipoPreciosList.set(posicionObjetoEnTabla, tipoPrecio);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = tipoPrecioController.deleteTipoPrecio(tipoPrecio);
		if (error == null) {
			tipoPreciosList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tipoPrecio.setNombre(txtNombre.getText());
		tipoPrecio.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
	}

	private void limpiarCampos() {
		tipoPrecio = new TipoPrecio();
		txtNombre.setText("");
		txtPorcentaje.setText("0");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtNombre.requestFocus();
	}

	private boolean camposLlenosTipoPrecio() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtPorcentaje.getText().isEmpty())
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
		if (camposLlenosTipoPrecio()) {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + tipoPrecio.getNombre());
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
		List<TipoPrecio> tipoPrecioList = tipoPrecioController.getTipoPrecioList(txtBuscar.getText());
		if (tipoPrecioList != null) {
			tipoPreciosList = FXCollections.observableList(tipoPrecioList);
			tblLista.setItems(tipoPreciosList);
		} else {
			tipoPreciosList.clear();
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
