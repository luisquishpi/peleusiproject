package ec.peleusi.views.fx.controllers;

import java.util.List;

import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class CiudadListFxController extends AnchorPane {
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtBuscar;
	@FXML
	private TableView<Ciudad> tblCiudades = new TableView<Ciudad>();

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {
		txtNombre.setText("Hola");
		
		//ObservableList<TableColumn<Ciudad, ?>> observableList = tblCiudades.getColumns();
		//observableList.remove(0,2);
		
		tblCiudades.setEditable(true);
		TableColumn<Ciudad, Integer> idCol = new TableColumn<Ciudad, Integer>("Id");
		TableColumn<Ciudad, String> nombreCol = new TableColumn<Ciudad, String>("Nombre");
		tblCiudades.getColumns().addAll(idCol, nombreCol);
		
		CiudadController ciudadController = new CiudadController();
		List<Ciudad> listaCiudad = ciudadController.ciudadList(txtBuscar.getText());
		//ObservableList<Ciudad> observableList = FXCollections.observableList(listaCiudad);
		idCol.setMinWidth(0);
		idCol.setMaxWidth(0);
		idCol.setPrefWidth(0);
		idCol.setCellValueFactory(new PropertyValueFactory<Ciudad, Integer>("id"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<Ciudad, String>("nombre"));
		tblCiudades.setItems(FXCollections.observableList(listaCiudad));
	}

	public CiudadListFxController() {
	}

}
