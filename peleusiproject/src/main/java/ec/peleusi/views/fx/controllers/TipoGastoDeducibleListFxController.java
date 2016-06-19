package ec.peleusi.views.fx.controllers;

import java.util.List;

import javax.swing.JOptionPane;

import ec.peleusi.controllers.TipoGastoDeducibleController;
import ec.peleusi.models.entities.TipoGastoDeducible;
import ec.peleusi.views.fx.mains.TipoGastoDeducibleListFxMain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TipoGastoDeducibleListFxController extends AnchorPane {

	@FXML
	public TextField txtNombre;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<TipoGastoDeducible> tblLista = new TableView<TipoGastoDeducible>();
	@FXML
	TableColumn<TipoGastoDeducible, Integer> idCol;
	@FXML
	TableColumn<TipoGastoDeducible, String> nombreCol;
	Integer posicionTipoGastoSeleccionado;
	Boolean actualizar;

	private ObservableList<TipoGastoDeducible> tipoGastoDeducibleObs = FXCollections.observableArrayList();

	@FXML
	private void initialize() {

		TipoGastoDeducibleController tipoGastoController = new TipoGastoDeducibleController();
		List<TipoGastoDeducible> listaGastoDeducible = tipoGastoController
				.getTipoGastoDeducibleList(txtBuscar.getText());
		idCol.setMinWidth(0);
		idCol.setMaxWidth(0);
		idCol.setPrefWidth(0);
		idCol.setCellValueFactory(new PropertyValueFactory<TipoGastoDeducible, Integer>("id"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<TipoGastoDeducible, String>("nombre"));
		tipoGastoDeducibleObs = FXCollections.observableList(listaGastoDeducible);
		tblLista.setItems(tipoGastoDeducibleObs);
		tblLista.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTipoGastoDeducibleDetails(newValue));

	}

	private void showTipoGastoDeducibleDetails(TipoGastoDeducible tipoGastoDeducible) {

		posicionTipoGastoSeleccionado = tipoGastoDeducibleObs.indexOf(tipoGastoDeducible);
		if (tipoGastoDeducible != null) {

			txtNombre.setText(tipoGastoDeducible.getNombre());
			actualizar = true;

		} else {
			txtNombre.setText("");

		}
	}

	private void limpiarCampos() {
		actualizar = false;
		txtNombre.setText("");
		txtNombre.requestFocus();
		// tblLista.setSelectionModel(null);
	}

	@FXML
	private void handlebtnGuardar() {

		if (isCamposLlenos()) {

			TipoGastoDeducible tipoGastoDeducible = new TipoGastoDeducible();
			TipoGastoDeducibleController tipoGastoDeducibleController = new TipoGastoDeducibleController();
			String error = "";
			TipoGastoDeducible tipoGastoDeducibleSeleccionado = tblLista.getSelectionModel().getSelectedItem();

			if (tipoGastoDeducibleSeleccionado != null && actualizar == true) {
				tipoGastoDeducible.setNombre(txtNombre.getText());
				error = tipoGastoDeducibleController.updateTipoGastoDeducible(tipoGastoDeducible);
				tipoGastoDeducibleObs.set(posicionTipoGastoSeleccionado, tipoGastoDeducible);
			} else {
				tipoGastoDeducible.setNombre(txtNombre.getText());
				error = tipoGastoDeducibleController.createTipoGastoDeducible(tipoGastoDeducible);
				tipoGastoDeducibleObs.add(tipoGastoDeducible);
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);

				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

	}

	@FXML
	private void handlebtnNuevo() {
		limpiarCampos();
	}

	@FXML
	private void handlebtnEliminar() {

		int selectedIndex = tblLista.getSelectionModel().getSelectedIndex();
		System.out.println("Fila " + selectedIndex);

		if (selectedIndex >= 0) {

			TipoGastoDeducible tipoGastoDeducible = new TipoGastoDeducible();
			tipoGastoDeducible = (TipoGastoDeducible) tipoGastoDeducibleObs.get(posicionTipoGastoSeleccionado);
			TipoGastoDeducibleController tipoGastoDeducibleController = new TipoGastoDeducibleController();
			tipoGastoDeducibleController.deleteTipoGastoDeducible(tipoGastoDeducible);
			tblLista.getItems().remove(selectedIndex);
		} else {
			// Nothing selected.

			// Dialogs.create()
			// .title("No Selection")
			// .masthead("No Person Selected")
			// .message("Please select a person in the table.")
			// .showWarning();

		}
	}

	@FXML
	private void handleCancelar() {
		//Stage stage = (Stage) btnCancelar.getScene().getWindow();       
        //stage.close();
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty())
			llenos = false;
		return llenos;
	}
}
