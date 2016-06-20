package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;

import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class CiudadListFxController extends AnchorPane {

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Ciudad> tblLista;
	@FXML
	TableColumn<Ciudad, Integer> idCol;
	@FXML
	TableColumn<Ciudad, String> nombreCol;

	@FXML
	private Button btnNuevo;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnCancelar;

	ObservableList<Ciudad> ciudadesList;
	private Integer posicionObjetoEnTabla;
	private CiudadController ciudadController = new CiudadController();
	private String error = null;
	private Ciudad ciudad;

	@FXML
	private void initialize() {

		ciudadesList = FXCollections.observableList(ciudadController.ciudadList());
		tblLista.setItems(ciudadesList);

		nombreCol.setCellValueFactory(new PropertyValueFactory<Ciudad, String>("nombre"));

		final ObservableList<Ciudad> tblListaObs = tblLista.getSelectionModel().getSelectedItems();
		tblListaObs.addListener(escuchaCambiosEnTabla);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				btnNuevoClick(null);
			}
		});
		
		Pagination paginacion=new Pagination((ciudadesList.size()/ rowsPerPage() + 1),0);
		paginacion.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                if (pageIndex > ciudadesList.size() / rowsPerPage() + 1) {
                    return null;
                } else {
                    return createPage(pageIndex);
                }
            }
        });
		
		
	}
	private Node createPage(int pageIndex) {

	    int fromIndex = pageIndex * rowsPerPage();
	    int toIndex = Math.min(fromIndex + rowsPerPage(), ciudadesList.size());
	    tblLista.setItems(FXCollections.observableArrayList(ciudadesList.subList(fromIndex, toIndex)));

	    return new BorderPane((Node) ciudadesList);
	}

	 public int rowsPerPage() {
	        return 3;
	    }

	private final ListChangeListener<Ciudad> escuchaCambiosEnTabla = new ListChangeListener<Ciudad>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Ciudad> c) {
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
		ciudad = (Ciudad) getObjetoSeleccionadoDeTabla();
		if (ciudad != null) {
			posicionObjetoEnTabla = ciudadesList.indexOf(ciudad);
			txtNombre.setText(ciudad.getNombre());
			btnGuardar.setText("Actualizar");
			btnGuardar.setDisable(false);
			btnEliminar.setDisable(false);
		}
	}

	private void guardarNuevo() {
		error = ciudadController.createCiudad(ciudad);
		if (error == null) {
			ciudadesList.add(ciudad);
			AlertsUtil.alertExito("Guardado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = ciudadController.updateCiudad(ciudad);
		if (error == null) {
			ciudadesList.set(posicionObjetoEnTabla, ciudad);
			AlertsUtil.alertExito("Actualizado correctamente");
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void eliminar() {
		error = ciudadController.deleteCiudad(ciudad);
		if (error == null) {
			ciudadesList.remove(getObjetoSeleccionadoDeTabla());
			btnNuevoClick(null);
		} else {
			AlertsUtil.alertError(error);
		}
	}

	@FXML
	private void btnNuevoClick(ActionEvent event) {
		ciudad = new Ciudad();
		txtNombre.setText("");
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		btnGuardar.setDisable(false);
		txtNombre.requestFocus();
	}

	@FXML
	private void btnGuardarClick(ActionEvent event) {
		ciudad.setNombre(txtNombre.getText());
		if (isCamposLlenos()) {
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
				.alertConfirmation("Está seguro que desea eliminar: \n" + ciudad.getNombre());
		if (result.get() == ButtonType.OK) {
			eliminar();
		}
	}

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<Ciudad> ciudadList = ciudadController.ciudadList(txtBuscar.getText());
		if (ciudadList != null) {
			ciudadesList = FXCollections.observableList(ciudadList);
			tblLista.setItems(ciudadesList);
		} else {
			ciudadesList.clear();
		}
		btnNuevoClick(null);
		tblLista.requestFocus();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
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
	

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().trim().isEmpty())
			llenos = false;
		return llenos;
	}
}
