package ec.peleusi.views.fx.controllers;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.TarifaIva;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
	@FXML private Button btnNuevo;
	@FXML private Button btnGuardar;
	@FXML private Button btnEliminar;
	@FXML private Button btnCancelar;
	@FXML private Button btnBuscar;
	private TarifaIva tarifaIva = new TarifaIva(); 
	
	@FXML 
	private void initialize() {
		//txtNombre.setText("Hola");
		
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
		idCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, Integer>("id"));
		codigoCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, String>("codigo"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, String>("nombre"));
		porcentajeCol.setCellValueFactory(new PropertyValueFactory<TarifaIva, Double>("porcentaje"));
		
		tblLista.setItems(FXCollections.observableList(listaTarifaIva));
		
		tblLista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showTarifaIvaDetails(newValue));
	}

	private Object showTarifaIvaDetails(TarifaIva newValue) {
		txtCodigo.setText(tarifaIva.getCodigo());
		txtNombre.setText(tarifaIva.getNombre());
		txtPorcentaje.setText(Double.toString(tarifaIva.getPorcentaje()));
		return null;
	}
	
	@FXML
	private void btnNuevo(ActionEvent event)
	{		
		limpiarCampos();
	}

	@FXML
	private void btnGuardar(ActionEvent event)
	{		
		if (!camposLlenosTarifaIva()) {
			JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
					JOptionPane.WARNING_MESSAGE);
			return;
		}		
		
		if (tarifaIva != null && tarifaIva.getId() != null) {
			actualizarTarifaIva();
		} else {
			guardarNuevoTarifaIva();
		}	
	}
	
    @FXML
	private void btnCancelar(ActionEvent event)
	{		
		@SuppressWarnings("unused")
		Stage stage = (Stage) btnCancelar.getScene().getWindow(); 		 
	 }	
	 
    
    private void guardarNuevoTarifaIva() {
		tarifaIva = new TarifaIva();
		llenarEntidadAntesDeGuardar();
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		String error = tarifaIvaController.createTarifaIva(tarifaIva);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
//			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTarifaIva() {
		llenarEntidadAntesDeGuardar();
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		String error = tarifaIvaController.updateTarifaIva(tarifaIva);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
	//		dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

    private void llenarEntidadAntesDeGuardar() {
		tarifaIva.setCodigo(txtCodigo.getText());
		tarifaIva.setNombre(txtNombre.getText());
		tarifaIva.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
	}
    
    private void limpiarCampos() {
		txtCodigo.setText("");
		txtNombre.setText("");
		txtPorcentaje.setText("0");
		txtCodigo.requestFocus();
	}

	  private boolean camposLlenosTarifaIva() {
			boolean llenos = true;
			if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()
					|| txtPorcentaje.getText().isEmpty())
				llenos = false;
			return llenos;
		}

	
	public TarifaIvaListFxController() {
	}

}
