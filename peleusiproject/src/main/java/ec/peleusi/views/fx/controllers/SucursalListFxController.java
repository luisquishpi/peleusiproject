package ec.peleusi.views.fx.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.EmpresaController;
import ec.peleusi.controllers.SucursalController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Empresa;
import ec.peleusi.models.entities.Sucursal;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SucursalListFxController extends GenericController {
	@FXML
	private ComboBox<Empresa> cmbEmpresa;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtCiudad;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtFax;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Sucursal> tblLista;
	@FXML
	TableColumn<Sucursal, String> nombreCol;
	@FXML
	TableColumn<Sucursal, String> ciudadCol;
	@FXML
	TableColumn<Sucursal, String> direccionCol;
	@FXML
	TableColumn<Sucursal, String> telefonoCol;
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
	private Empresa empresa;
	ObservableList<Sucursal> sucursalsList;
	private Sucursal sucursal;
	private SucursalController sucursalController = new SucursalController();
	ObservableList<Empresa> empresasList;
	private Integer posicionObjetoEnTabla;
	private String error = null;
	Boolean actualizarSucursal = false;

	@FXML
	private void initialize() {
		sucursalsList = FXCollections.observableList(sucursalController.SucursalList());
		tblLista.setItems(sucursalsList);
		nombreCol.setCellValueFactory(new PropertyValueFactory<Sucursal, String>("nombre"));
		ciudadCol.setCellValueFactory(new PropertyValueFactory<Sucursal, String>("ciudad"));
		direccionCol.setCellValueFactory(new PropertyValueFactory<Sucursal, String>("direccion"));
		telefonoCol.setCellValueFactory(new PropertyValueFactory<Sucursal, String>("telefono"));
		final ObservableList<Sucursal> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
		cargarComboEmpresa();
	}

	private final ListChangeListener<Sucursal> escuchaCambiosEnTabla = new ListChangeListener<Sucursal>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Sucursal> c) {
			cargarObjetoSeleccionadaEnFormulario();
			actualizarSucursal = true;
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

	private void cargarComboEmpresa() {
		EmpresaController empresaController = new EmpresaController();
		empresasList = FXCollections.observableList(empresaController.EmpresaList());
		cmbEmpresa.setItems(empresasList);
	}

	private void cargarObjetoSeleccionadaEnFormulario() {
		sucursal = (Sucursal) getObjetoSeleccionadoDeTabla();
		if (sucursal != null) {
			posicionObjetoEnTabla = sucursalsList.indexOf(sucursal);
			cmbEmpresa.getSelectionModel().select(sucursal.getEmpresa());
			txtNombre.setText(sucursal.getNombre());
			txtCiudad.setText(sucursal.getCiudad().getNombre());
			txtDireccion.setText(sucursal.getDireccion());
			txtTelefono.setText(sucursal.getTelefono());
			txtFax.setText(sucursal.getFax());
			txtEmail.setText(sucursal.getEmail());
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo(Empresa empresaEntrada) {
		sucursal = llenarEntidadAntesDeGuardar(empresaEntrada, false);
		error = sucursalController.createSucursal(sucursal);
		if (error == null) {
			sucursalsList.add(sucursal);
			txtBuscar.setText(txtNombre.getText());
			btnBuscarClick(null);
			btnNuevoClick(null);
			AlertsUtil.alertExito("Guardado correctamente");

		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar(Sucursal sucursal) {
		sucursal = llenarEntidadAntesDeGuardar(empresa, true);
		sucursalController = new SucursalController();
		error = sucursalController.updateSucursal(sucursal);
		if (error == null) {
			sucursalsList.set(posicionObjetoEnTabla, sucursal);
			System.out.println("actualizado " + sucursal);
			txtBuscar.setText(txtNombre.getText());
			btnBuscarClick(null);
			btnNuevoClick(null);
			AlertsUtil.alertExito("Actualizado correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = sucursalController.deleteSucursal(sucursal);
		if (error == null) {
			sucursalsList.remove(getObjetoSeleccionadoDeTabla());
			btnBuscarClick(null);
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private Sucursal llenarEntidadAntesDeGuardar(Empresa empresa, Boolean actualizarSucursal) {
		if (actualizarSucursal == false) {
			sucursal = new Sucursal();
		}
		sucursal.setEmpresa((Empresa) cmbEmpresa.getValue());
		sucursal.setNombre(txtNombre.getText());
		sucursal.setCiudad((Ciudad) txtCiudad.getUserData());
		sucursal.setDireccion(txtDireccion.getText());
		sucursal.setTelefono(txtTelefono.getText());
		sucursal.setFax(txtFax.getText());
		sucursal.setEmail(txtEmail.getText());
		System.out.println("antes de guardar " + sucursal);
		return sucursal;
	}

	private void limpiarCampos() {
		txtNombre.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtFax.setText("");
		txtEmail.setText("");
		txtNombre.requestFocus();
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtNombre.requestFocus();
	}

	private boolean camposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()
				|| txtEmail.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	@FXML
	private void btnNuevoClick(ActionEvent event) {
		limpiarCampos();
	}

	@FXML
	private void btnGuardarClick(ActionEvent event) {
		if (camposLlenos()) {
			if (btnGuardar.getText().toLowerCase().equals("actualizar")) {
				actualizar(sucursal);
			} else {
				guardarNuevo(empresa);
			}
		} else {
			AlertsUtil.alertWarning("Datos incompletos, no es posible guardar");
		}
	}

	@FXML
	private void btnEliminarClick(ActionEvent event) {
		Optional<ButtonType> result = AlertsUtil
				.alertConfirmation("Está seguro que desea eliminar: \n" + sucursal.getNombre());
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
		List<Sucursal> sucursalList = sucursalController.getSucursalList(txtBuscar.getText());
		if (sucursalList != null) {
			sucursalsList = FXCollections.observableList(sucursalList);
			tblLista.setItems(sucursalsList);
		} else {
			sucursalsList.clear();
		}
		btnNuevoClick(null);
		tblLista.requestFocus();
	}

	@FXML
	private void btnBuscarCiudadClick(ActionEvent event) {
		Ciudad ciudad = new Ciudad();
		Parent parent = null;
		Stage stage = new Stage();
		CiudadListModalController control = new CiudadListModalController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/CiudadListModalFx.fxml"));
		loader.setController(control);
		try{
			parent = (Parent) loader.load();
			stage.setTitle("Lista de Ciudad");
			stage.setScene(new Scene(parent));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			ciudad = control.getCiudad();			
		}	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
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
