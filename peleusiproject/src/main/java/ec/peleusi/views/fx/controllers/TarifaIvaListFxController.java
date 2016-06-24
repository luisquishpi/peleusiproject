package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.TarifaIva;
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

public class TarifaIvaListFxController extends AnchorPane {
	@FXML
	private TextField txtCodigo;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtPorcentaje;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<TarifaIva> tblLista;
	@FXML
	TableColumn<TarifaIva, Integer> idCol;
	@FXML
	TableColumn<TarifaIva, String> codigoCol;
	@FXML
	TableColumn<TarifaIva, String> nombreCol;
	@FXML
	TableColumn<TarifaIva, Double> porcentajeCol;
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

	ObservableList<TarifaIva> tarifaIvasList;
	private Integer posicionObjetoEnTabla;
	private TarifaIva tarifaIva;
	private TarifaIvaController tarifaIvaController = new TarifaIvaController();
	private String error = null;

	@FXML
	private void initialize() {
		tarifaIvasList = FXCollections.observableList(tarifaIvaController.tarifaIvaList());
		tblLista.setItems(tarifaIvasList);
		idCol.setMinWidth(0);
		idCol.setMaxWidth(0);
		idCol.setPrefWidth(0);
		idCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, Integer>("id"));
		codigoCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, String>("codigo"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, String>("nombre"));
		porcentajeCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, Double>("porcentaje"));
		final ObservableList<TarifaIva> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
	}

	private final ListChangeListener<TarifaIva> escuchaCambiosEnTabla = new ListChangeListener<TarifaIva>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TarifaIva> c) {
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
		tarifaIva = (TarifaIva) getObjetoSeleccionadoDeTabla();
		if (tarifaIva != null) {
			posicionObjetoEnTabla = tarifaIvasList.indexOf(tarifaIva);
			txtCodigo.setText(tarifaIva.getCodigo());
			txtNombre.setText(tarifaIva.getNombre());
			txtPorcentaje.setText(Double.toString(tarifaIva.getPorcentaje()));
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = tarifaIvaController.createTarifaIva(tarifaIva);
		if (error == null) {
			tarifaIvasList.add(tarifaIva);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = tarifaIvaController.updateTarifaIva(tarifaIva);
		if (error == null) {
			tarifaIvasList.set(posicionObjetoEnTabla, tarifaIva);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = tarifaIvaController.deleteTarifaIva(tarifaIva);
		if (error == null) {
			tarifaIvasList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tarifaIva.setCodigo(txtCodigo.getText());
		tarifaIva.setNombre(txtNombre.getText());
		tarifaIva.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
	}

	private void limpiarCampos() {
		tarifaIva = new TarifaIva();
		txtCodigo.setText("");
		txtNombre.setText("");
		txtPorcentaje.setText("0");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtCodigo.requestFocus();
	}

	private boolean camposLlenosTarifaIva() {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + tarifaIva.getNombre());
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
		List<TarifaIva> tarifaIvaList = tarifaIvaController.getTarifaIvaList(txtBuscar.getText());
		if (tarifaIvaList != null) {
			tarifaIvasList = FXCollections.observableList(tarifaIvaList);
			tblLista.setItems(tarifaIvasList);
		} else {
			tarifaIvasList.clear();
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
