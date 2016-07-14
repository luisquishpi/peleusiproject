package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.UsuarioController;
import ec.peleusi.models.entities.Usuario;
import ec.peleusi.utils.TipoUsuarioEnum;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class UsuarioListFxController extends GenericController {
	@FXML
	private TextField txtNombres;
	@FXML
	private TextField txtApellidos;
	@FXML
	private TextField txtUsuario;
	@FXML
	private PasswordField txtContrasenia;
	@FXML
	private ComboBox<TipoUsuarioEnum> cmbTipoUsuario;		
	@FXML	
	private TextField txtBuscar;
	@FXML
	private TableView<Usuario> tblLista;
	@FXML
	TableColumn<Usuario, Integer> idCol;
	@FXML
	TableColumn<Usuario, String> nombresCol;
	@FXML
	TableColumn<Usuario, String> apellidosCol;
	@FXML
	TableColumn<Usuario, String> usuarioCol;
	@FXML
	TableColumn<Usuario, TipoUsuarioEnum> tipoUsuarioCol;
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
	
	ObservableList<Usuario> usuariosList;	
	private Integer posicionObjetoEnTabla;
	private Usuario usuario;
	private UsuarioController usuarioController = new UsuarioController();
	private String error = null;
	
	@FXML
	private void initialize() {
		usuariosList = FXCollections.observableList(usuarioController.usuarioList());
		tblLista.setItems(usuariosList);			
		nombresCol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombres"));
		apellidosCol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidos"));
		usuarioCol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("usuario"));
		tipoUsuarioCol.setCellValueFactory(new PropertyValueFactory<Usuario, TipoUsuarioEnum>("tipoUsuario"));
		final ObservableList<Usuario> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);			
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});		
		cargarComboUsuario();
	}
		
	private final ListChangeListener<Usuario> escuchaCambiosEnTabla = new ListChangeListener<Usuario>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Usuario> c) {
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
	
	private void cargarComboUsuario() {
		cmbTipoUsuario.setItems(FXCollections.observableArrayList(TipoUsuarioEnum.values()));			
	}

	
	private void cargarObjetoSeleccionadaEnFormulario() {
		usuario = (Usuario) getObjetoSeleccionadoDeTabla();
		if (usuario != null) {
			posicionObjetoEnTabla = usuariosList.indexOf(usuario);
			txtNombres.setText(usuario.getNombres());
			txtApellidos.setText(usuario.getApellidos());
			txtUsuario.setText(usuario.getUsuario());
			txtContrasenia.setText(usuario.getContrasenia());
			cmbTipoUsuario.getSelectionModel().select(usuario.getTipoUsuario());								
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = usuarioController.createUsuario(usuario);
		if (error == null) {
			usuariosList.add(usuario);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = usuarioController.updateUsuario(usuario);
		if (error == null) {
			usuariosList.set(posicionObjetoEnTabla, usuario);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = usuarioController.deleteUsuario(usuario);
		if (error == null){
			usuariosList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		usuario.setNombres(txtNombres.getText());
		usuario.setApellidos(txtApellidos.getText());
		usuario.setUsuario(txtUsuario.getText());
		usuario.setContrasenia(txtContrasenia.getText());
		usuario.setTipoUsuario((TipoUsuarioEnum) cmbTipoUsuario.getValue());			
	}

	private void limpiarCampos() {
		usuario = new Usuario();
		txtNombres.setText("");		
		txtApellidos.setText("");
		txtUsuario.setText("");
		txtContrasenia.setText("");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtNombres.requestFocus();
	}

	private boolean camposLlenosTarifaIva() {
		boolean llenos = true;
		if (txtNombres.getText().isEmpty() || txtApellidos.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty())
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + usuario.getNombres());
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
		List<Usuario> usuarioList = usuarioController.getUsuarioList(txtBuscar.getText());
		if (usuarioList != null) {
			usuariosList = FXCollections.observableList(usuarioList);
			tblLista.setItems(usuariosList);
		} else {
			usuariosList.clear();
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
