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
	
	private ObservableList<TipoGastoDeducible> tipoGastoDeducibleObs = FXCollections.observableArrayList();
	
	 private TipoGastoDeducibleListFxMain mainApp;
	 
	 
	 public void setMainApp(TipoGastoDeducibleListFxMain mainApp) {
	        this.mainApp = mainApp;

	        // Add observable list data to the table
	        tblLista.setItems(mainApp.getTipoGastoDeducible());
	       
	    }

	@FXML
	private void initialize() {
		
	
		TipoGastoDeducibleController tipoGastoController= new TipoGastoDeducibleController();
		List<TipoGastoDeducible> listaGastoDeducible= tipoGastoController.getTipoGastoDeducibleList(txtBuscar.getText());		
		idCol.setMinWidth(0);
		idCol.setMaxWidth(0);
		idCol.setPrefWidth(0);
		idCol.setCellValueFactory(new PropertyValueFactory<TipoGastoDeducible, Integer>("id"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<TipoGastoDeducible, String>("nombre"));	
		showTipoGastoDeducibleDetails(null);
		
		tipoGastoDeducibleObs = FXCollections.observableList(listaGastoDeducible);		
	//	tblLista.setItems(FXCollections.observableList(tipoGastoDeducibleObs));
		
		tblLista.setItems(tipoGastoDeducibleObs);	
		
		
		tblLista.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showTipoGastoDeducibleDetails(newValue));		
		
		
	}
	
	 private void showTipoGastoDeducibleDetails(TipoGastoDeducible tipoGastoDeducible) {
	    	if (tipoGastoDeducible != null) {	    	
	    		txtNombre.setText(tipoGastoDeducible.getNombre());
	    		
	    	} else {	    		
	    		txtNombre.setText("");
	    		
	    	}
	    }
	 
	 @FXML
	    private void handlebtnGuardar() {
	        if (isCamposLlenos()) {	      	
	        	TipoGastoDeducible tipoGastoDeducible = new TipoGastoDeducible();	
	    		TipoGastoDeducibleController tipoGastoDeducibleController = new TipoGastoDeducibleController();
	    		tipoGastoDeducible.setNombre(txtNombre.getText());	    		
	    		String error = tipoGastoDeducibleController.createTipoGastoDeducible(tipoGastoDeducible);
	    		tipoGastoDeducibleObs.add(tipoGastoDeducible);
	    		
	    		if (error == null) {	    					
	    			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
	    			
	    			
	    		} else {
	    			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
	    		}         
	        }
	    }
	 @FXML
	 private void handlebtnNuevo() {
		 
		 txtNombre.setText("");
		}
	 
	 private boolean isCamposLlenos() {
			boolean llenos = true;
			if (txtNombre.getText().isEmpty())
				llenos = false;
			return llenos;
		}

}
