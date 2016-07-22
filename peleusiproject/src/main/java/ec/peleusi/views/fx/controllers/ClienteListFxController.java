package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.ClienteController;
import ec.peleusi.controllers.TipoCalificacionClienteController;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.models.entities.TipoCalificacionCliente;
import ec.peleusi.models.entities.TipoIdentificacion;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class ClienteListFxController extends GenericController {
	@FXML
	private ComboBox<TipoIdentificacion> cmbTipoIdentificacion;
	@FXML
	private TextField txtIdentificacion;
	@FXML
	private TextField txtRazonSocial;
	@FXML
	private TextField txtCiudad;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtCelular;
	@FXML
	private TextArea txtDescripcion;
	@FXML
	private TextField txtPorcentajeDescuento;
	@FXML
	private TextField txtDiasCredito;
	@FXML
	private ComboBox<TipoCalificacionCliente> cmbTipoCalificacionCliente;
	@FXML
	private ComboBox<TipoPrecio> cmbTipoPrecio;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Cliente> tblLista;
	@FXML
	private Pagination pagination;	
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

	ObservableList<Cliente> clientesList;
	private Cliente cliente;
	private ClienteController clienteController = new ClienteController();
	ObservableList<TipoIdentificacion> tipoIdentificacionsList;
	//private TipoIdentificacion tipoIdentificacion;
	ObservableList<TipoCalificacionCliente> tipoCalificacionClientesList;
//	private TipoCalificacionCliente tipoCalificacionCliente;
	ObservableList<TipoPrecio> tipoPreciosList;
	//private TipoPrecio tipoPrecio;
	private Integer posicionObjetoEnTabla;	
	private String error = null;
	final static int rowsPerPage = 100;
	

	@FXML
	private void initialize() {
		crearTabla();		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
		paginar();
		cargarComboTipoIdentificacion();
		cargarComboTipoCalificacionCliente();
		cargarComboTipoPrecio();
	}

	private void paginar() {
		int count = clientesList.size() / rowsPerPage;
		if (count < ((double) (clientesList.size()) / rowsPerPage))
			count++;
		if (count == 0 && clientesList.size() > 0)
			count++;
		if (clientesList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, clientesList.size());
					tblLista.setItems(FXCollections.observableArrayList(clientesList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	private void crearTabla() {
		tblLista = new TableView<Cliente>();
		TableColumn<Cliente, String> identificacionCol = new TableColumn<Cliente, String>("Identificacion");
		identificacionCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("identificacion"));
		identificacionCol.setPrefWidth(120);
		tblLista.getColumns().add(identificacionCol);
		TableColumn<Cliente, String> razonSocialCol = new TableColumn<Cliente, String>("RazónSocial");
		razonSocialCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("razonSocial"));
		razonSocialCol.setPrefWidth(150);
		tblLista.getColumns().add(razonSocialCol);
		TableColumn<Cliente, String> direccionCol = new TableColumn<Cliente, String>("Dirección");
		direccionCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
		direccionCol.setPrefWidth(180);
		tblLista.getColumns().add(direccionCol);
		TableColumn<Cliente, String> telefonoCol = new TableColumn<Cliente, String>("Teléfono");
		telefonoCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
		telefonoCol.setPrefWidth(100);
		tblLista.getColumns().add(telefonoCol);
		TableColumn<Cliente, TipoPrecio> tipoPrecioCol = new TableColumn<Cliente, TipoPrecio>("Tipo Precio");
		tipoPrecioCol.setCellValueFactory(new PropertyValueFactory<Cliente, TipoPrecio>("tipoPrecio"));
		tipoPrecioCol.setPrefWidth(80);		
		tblLista.getColumns().add(tipoPrecioCol);
		clientesList = FXCollections.observableList(clienteController.ClienteList());
		tblLista.setItems(clientesList);
		final ObservableList<Cliente> clientesListSelected = tblLista.getSelectionModel().getSelectedItems();
		clientesListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<Cliente> escuchaCambiosEnTabla = new ListChangeListener<Cliente>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Cliente> c) {
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

	private void cargarComboTipoIdentificacion() {
		TipoIdentificacionController tipoIdenficacionController = new TipoIdentificacionController();
		tipoIdentificacionsList = FXCollections.observableList(tipoIdenficacionController.tipoIdentificacionList());
		cmbTipoIdentificacion.setItems(tipoIdentificacionsList);
	}
	
	private void cargarComboTipoCalificacionCliente() {
		TipoCalificacionClienteController tipoCalificacionClienteController = new TipoCalificacionClienteController();
		tipoCalificacionClientesList = FXCollections.observableList(tipoCalificacionClienteController.tipoCalificacionClienteList());
		cmbTipoCalificacionCliente.setItems(tipoCalificacionClientesList);
	}
	
	private void cargarComboTipoPrecio() {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		tipoPreciosList = FXCollections.observableList(tipoPrecioController.tipoPrecioList());
		cmbTipoPrecio.setItems(tipoPreciosList);
	}

	private void cargarObjetoSeleccionadaEnFormulario() {
		cliente = (Cliente) getObjetoSeleccionadoDeTabla();
		if (cliente != null) {
			posicionObjetoEnTabla = clientesList.indexOf(cliente);
			cmbTipoIdentificacion.getSelectionModel().select(cliente.getTipoIdentificacion());
			txtIdentificacion.setText(cliente.getIdentificacion());
			txtRazonSocial.setText(cliente.getRazonSocial());
			txtCiudad.setText(cliente.getCiudad().getNombre());
			txtDireccion.setText(cliente.getDireccion());
			txtEmail.setText(cliente.getEmail());
			txtTelefono.setText(cliente.getTelefono());
			txtCelular.setText(cliente.getCelular());
			txtDescripcion.setText(cliente.getDescripcion());
			txtPorcentajeDescuento.setText(Double.toString(cliente.getPorcentajeDescuento()));
			txtDiasCredito.setText(Integer.toString(cliente.getDiasCredito()));
			cmbTipoCalificacionCliente.getSelectionModel().select(cliente.getTipoCalificacionCliente());
			cmbTipoPrecio.getSelectionModel().select(cliente.getTipoPrecio());
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);					
		}
	}

	private void guardarNuevo() {
		error = clienteController.createCliente(cliente);
		if (error == null) {
			clientesList.add(cliente);
			txtBuscar.setText(txtRazonSocial.getText());
			btnBuscarClick(null);
			btnNuevoClick(null);
			AlertsUtil.alertExito("Guardado correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = clienteController.updateCliente(cliente);
		if (error == null) {
			clientesList.set(posicionObjetoEnTabla, cliente);
			txtBuscar.setText(txtRazonSocial.getText());
			btnBuscarClick(null);
			btnNuevoClick(null);
			AlertsUtil.alertExito("Actualizado correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
	}	
	
	private void eliminar() {
		error = clienteController.deleteCliente(cliente);
		if (error == null) {				
			clientesList.remove(getObjetoSeleccionadoDeTabla());	
			btnBuscarClick(null);
			btnNuevoClick(null);			
		} else {
		AlertsUtil.alertError(error);
	}
}	

	private void llenarEntidadAntesDeGuardar() {
		//tipoIdentificacion = new TipoIdentificacion();
		cliente.setTipoIdentificacion((TipoIdentificacion) cmbTipoIdentificacion.getValue());
		cliente.setIdentificacion(txtIdentificacion.getText());
		cliente.setRazonSocial(txtRazonSocial.getText());
		cliente.setCiudad(objetoCiudad());
		cliente.setDireccion(txtDireccion.getText());
		cliente.setEmail(txtEmail.getText());
		cliente.setTelefono(txtTelefono.getText());
		cliente.setCelular(txtCelular.getText());
		cliente.setDescripcion(txtDescripcion.getText());
		cliente.setPorcentajeDescuento(Double.parseDouble(txtPorcentajeDescuento.getText().toString()));		
		cliente.setDiasCredito(Integer.parseInt(txtDiasCredito.getText().toString()));
		//tipoCalificacionCliente = new TipoCalificacionCliente();
		cliente.setTipoCalificacionCliente((TipoCalificacionCliente) cmbTipoCalificacionCliente.getValue());
		//tipoPrecio = new TipoPrecio();
		cliente.setTipoPrecio((TipoPrecio) cmbTipoPrecio.getValue());
	}

	private void limpiarCampos() {
		cliente = new Cliente();
		txtIdentificacion.setText("");
		txtRazonSocial.setText("");
		txtCiudad.setText("");
		txtDireccion.setText("");
		txtEmail.setText("");
		txtTelefono.setText("");
		txtCelular.setText("");
		txtDescripcion.setText("");		
		txtDiasCredito.setText("0");
		txtPorcentajeDescuento.setText("0");		
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		cmbTipoIdentificacion.requestFocus();		
	}

	private boolean camposLlenos() {
		boolean llenos = true;
		if (txtIdentificacion.getText().isEmpty() || txtRazonSocial.getText().isEmpty()
				|| txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()
				|| txtDiasCredito.getText().isEmpty() || txtPorcentajeDescuento.getText().isEmpty()
				)
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
		if (camposLlenos()) {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + cliente.getRazonSocial());
		if (result.get() == ButtonType.OK) {
			eliminar();
			//tblLista.refresh();
			//limpiarCampos();
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
		List<Cliente> clienteList = clienteController.getClienteList(txtBuscar.getText());
		if (clienteList != null) {
			clientesList = FXCollections.observableList(clienteList);
			tblLista.setItems(clientesList);
		} else {
			clientesList.clear();
		}
		paginar();
		//cliente = clienteList.get(0);
		btnNuevoClick(null);
		tblLista.requestFocus();
	}		

	private Ciudad objetoCiudad() {
		Ciudad ciudadTmp = new Ciudad();
		ciudadTmp.setNombre("Ambato");
		ciudadTmp.setId(1);
		return ciudadTmp;
	}

	@FXML
	private void btnBuscarCiudadClick(ActionEvent event) {
		txtCiudad.setText(objetoCiudad().getNombre());
	}
}
