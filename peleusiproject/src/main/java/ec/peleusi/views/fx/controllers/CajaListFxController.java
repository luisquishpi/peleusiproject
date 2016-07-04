package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.CajaController;
import ec.peleusi.controllers.SucursalController;
import ec.peleusi.models.entities.Caja;
import ec.peleusi.models.entities.Sucursal;
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

public class CajaListFxController extends AnchorPane {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtSaldoInicial;
	@FXML
	private ComboBox<Sucursal> cmbSucursal;		
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Caja> tblLista;
	@FXML
	TableColumn<Caja, Integer> idCol;
	@FXML
	TableColumn<Caja, String> nombreCol;
	@FXML
	TableColumn<Caja, Double> saldoInicialCol;
	@FXML
	TableColumn<Caja, Sucursal> sucursalCol;	
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
	
	ObservableList<Caja> cajasList;	
	ObservableList<Sucursal> sucursalsList;
	private Integer posicionObjetoEnTabla;
	private Caja caja;
	private CajaController cajaController = new CajaController();
	private String error = null;
	
	@FXML
	private void initialize() {
		cajasList = FXCollections.observableList(cajaController.cajaList());
		tblLista.setItems(cajasList);			
		nombreCol.setCellValueFactory(new PropertyValueFactory<Caja, String>("nombre"));
		saldoInicialCol.setCellValueFactory(new PropertyValueFactory<Caja, Double>("saldoInicial"));
		sucursalCol.setCellValueFactory(new PropertyValueFactory<Caja, Sucursal>("sucursal"));
		final ObservableList<Caja> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);			
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});		
		cargarComboSucursal();
	}
		
	private final ListChangeListener<Caja> escuchaCambiosEnTabla = new ListChangeListener<Caja>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Caja> c) {
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
	
	private void cargarComboSucursal() {
		SucursalController sucursalController = new SucursalController();
		sucursalsList = FXCollections.observableList(sucursalController.SucursalList());
		cmbSucursal.setItems(sucursalsList);	
	}
	
	private void cargarObjetoSeleccionadaEnFormulario() {
		caja = (Caja) getObjetoSeleccionadoDeTabla();
		if (caja != null) {
			posicionObjetoEnTabla = cajasList.indexOf(caja);
			txtNombre.setText(caja.getNombre());	
			txtSaldoInicial.setText(Double.toString(caja.getSaldoInicial()));	
			cmbSucursal.getSelectionModel().select(caja.getSucursal());
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = cajaController.createCaja(caja);
		if (error == null) {
			cajasList.add(caja);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = cajaController.updateCaja(caja);
		if (error == null) {
			cajasList.set(posicionObjetoEnTabla, caja);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = cajaController.deleteCaja(caja);
		if (error == null){
			cajasList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		caja.setNombre(txtNombre.getText());	
		caja.setSaldoInicial(Double.parseDouble(txtSaldoInicial.getText().toString()));
		caja.setSucursal((Sucursal) cmbSucursal.getValue());				
	}

	private void limpiarCampos() {
		caja = new Caja();
		txtNombre.setText("");		
		txtSaldoInicial.setText("0");		
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtNombre.requestFocus();
	}

	private boolean camposLlenosTarifaIva() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtSaldoInicial.getText().isEmpty())
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + caja.getNombre());
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
		List<Caja> cajaList = cajaController.getCajaList(txtBuscar.getText());
		if (cajaList != null) {
			cajasList = FXCollections.observableList(cajaList);
			tblLista.setItems(cajasList);
		} else {
			cajasList.clear();
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
