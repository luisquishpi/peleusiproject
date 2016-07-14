package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.models.entities.TipoIdentificacion;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TipoIdentificacionListFxController extends GenericController {
	@FXML
	private TextField txtCodigo;
	@FXML
	private TextField txtNombre;
	@FXML
	private CheckBox chkValida;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<TipoIdentificacion> tblLista;
	@FXML
	TableColumn<TipoIdentificacion, Integer> idCol;
	@FXML
	TableColumn<TipoIdentificacion, String> codigoCol;
	@FXML
	TableColumn<TipoIdentificacion, String> nombreCol;
	@FXML
	TableColumn<TipoIdentificacion, Boolean> validaCol;
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
	ObservableList<TipoIdentificacion> tipoIdentificacionsList;
	private Integer posicionObjetoEnTabla;
	private TipoIdentificacion tipoIdentificacion;
	private TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
	private String error = null;

	@FXML
	private void initialize() {
		tipoIdentificacionsList = FXCollections.observableList(tipoIdentificacionController.tipoIdentificacionList());
		tblLista.setItems(tipoIdentificacionsList);
		codigoCol.setCellValueFactory(new PropertyValueFactory<TipoIdentificacion, String>("codigo"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<TipoIdentificacion, String>("nombre"));
		validaCol.setCellValueFactory(new PropertyValueFactory<TipoIdentificacion, Boolean>("valida"));
		validaCol.setCellFactory(CheckBoxTableCell.forTableColumn(validaCol));

		//TableColumn<TipoIdentificacion,Boolean>  validaCol = new TableColumn<>("Check");
		//validaCol.setCellValueFactory( new PropertyValueFactory<TipoIdentificacion,Boolean>( "valida" ) );
		//validaCol.setCellFactory( CheckBoxTableCell.forTableColumn( validaCol ) );
		
		
		/*TableColumn<Person, CheckBox> column = (TableColumn<Person, CheckBox>) personTable.getColumns().get(0);
		column.setCellValueFactory(new PersonUnemployedValueFactory());*/
		
		final ObservableList<TipoIdentificacion> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);		 
	 
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
	}
	
	/*TableColumn<Job,Boolean>  checkCol = new TableColumn<>("Check");
	checkCol.setCellValueFactory( new PropertyValueFactory<Job,Boolean>( "checkBoxValue" ) );
	checkCol.setCellFactory( CheckBoxTableCell.forTableColumn( checkCol ) );*/	
	
	
	 //chkValida.setCellFactory(new Callback<TableColumn<TipoIdentificacion, Boolean>, TableCell<TipoIdentificacion, Boolean>>() {

       //  public TableCell<TipoIdentificacion, Boolean> call(TableColumn<TipoIdentificacion, Boolean> p) {
         //    return new CheckBoxTableCell<TipoIdentificacion, Boolean>();
         //}
	
	
         
	private final ListChangeListener<TipoIdentificacion> escuchaCambiosEnTabla = new ListChangeListener<TipoIdentificacion>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends TipoIdentificacion> c) {
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
		tipoIdentificacion = (TipoIdentificacion) getObjetoSeleccionadoDeTabla();
		if (tipoIdentificacion != null) {
			posicionObjetoEnTabla = tipoIdentificacionsList.indexOf(tipoIdentificacion);
			txtCodigo.setText(tipoIdentificacion.getCodigo());
			txtNombre.setText(tipoIdentificacion.getNombre());
			chkValida.setSelected(tipoIdentificacion.getValida());			
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = tipoIdentificacionController.createTipoIdentificacion(tipoIdentificacion);
		if (error == null) {
			tipoIdentificacionsList.add(tipoIdentificacion);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = tipoIdentificacionController.updateTipoIdentificacion(tipoIdentificacion);
		if (error == null) {
			tipoIdentificacionsList.set(posicionObjetoEnTabla, tipoIdentificacion);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = tipoIdentificacionController.deleteTipoIdentificacion(tipoIdentificacion);
		if (error == null) {
			tipoIdentificacionsList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tipoIdentificacion.setCodigo(txtCodigo.getText());
		tipoIdentificacion.setNombre(txtNombre.getText());
		tipoIdentificacion.setValida(chkValida.isSelected());		
	}

	private void limpiarCampos() {
		tipoIdentificacion = new TipoIdentificacion();
		txtCodigo.setText("");
		txtNombre.setText("");	
		chkValida.setSelected(false);		
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtCodigo.requestFocus();
	}

	private boolean camposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty()|| txtNombre.getText().isEmpty())
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + tipoIdentificacion.getNombre());
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
		List<TipoIdentificacion> tipoIdentificacionList = tipoIdentificacionController.getTipoIdentificacionList(txtBuscar.getText());
		if (tipoIdentificacionList != null) {
			tipoIdentificacionsList = FXCollections.observableList(tipoIdentificacionList);
			tblLista.setItems(tipoIdentificacionsList);
		} else {
			tipoIdentificacionsList.clear();
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
