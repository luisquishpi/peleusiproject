package ec.peleusi.views.fx.controllers;

import java.util.List;

import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.TarifaIva;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class TarifaIvaListFxController extends AnchorPane {
	@FXML private TextField txtCodigo;
	@FXML private TextField txtNombre;
	@FXML private TextField txtPorcentaje;
	@FXML private TextField txtBuscar;
	@FXML private TableView<TarifaIva> tblLista = new TableView<TarifaIva>();
	@FXML TableColumn<TarifaIva, Integer> idCol;
	@FXML TableColumn<TarifaIva, String> codigoCol;
	@FXML TableColumn<TarifaIva, String> nombreCol;
	@FXML TableColumn<TarifaIva, Double> porcentajeCol;
	
	@FXML
	private void initialize() {
		txtNombre.setText("Hola");
		
		//ObservableList<TableColumn<Ciudad, ?>> observableList = tblCiudades.getColumns();
		//observableList.remove(0,2);
		//tblCiudades.getColumns().clear();
		
		//tblCiudades.setEditable(true);
		//TableColumn<Ciudad, Integer> idCol = new TableColumn<Ciudad, Integer>("Id");
		//TableColumn<Ciudad, String> nombreCol = new TableColumn<Ciudad, String>("Nombre");
		//tblCiudades.getColumns().addAll(idCol, nombreCol);
		
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		List<TarifaIva> listaTarifaIva = tarifaIvaController.getTarifaIvaList(txtBuscar.getText());
		//ObservableList<Ciudad> observableList = FXCollections.observableList(listaCiudad);
		idCol.setMinWidth(0);
		idCol.setMaxWidth(0);
		idCol.setPrefWidth(0);
		codigoCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, String>("codigo"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, String>("nombre"));
		porcentajeCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, Double>("porcentaje"));
		
		tblLista.setItems(FXCollections.observableList(listaTarifaIva));
	}

	public TarifaIvaListFxController() {
	}

}
