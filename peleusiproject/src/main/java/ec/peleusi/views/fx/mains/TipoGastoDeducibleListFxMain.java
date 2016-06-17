package ec.peleusi.views.fx.mains;

import java.io.IOException;


import ec.peleusi.models.entities.TipoGastoDeducible;
import ec.peleusi.views.fx.controllers.TipoGastoDeducibleListFxController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TipoGastoDeducibleListFxMain extends Application{
	private static Parent parent;
	
	 private ObservableList<TipoGastoDeducible> tipoGastoDeducible = FXCollections.observableArrayList();
	 
	@Override
	
	public void start(Stage stage) throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/TipoGastoDeducibleListFx.fxml"));
		loader.setController(new TipoGastoDeducibleListFxController());		
	
		parent = (Parent) loader.load();        
		stage.setTitle("Lista de Gastos Deducibles");
		stage.setScene(new Scene(parent));
		stage.show();		
		/*
		 * FadeTransition ft = new FadeTransition(Duration.millis(900), parent);
		 * ft.setFromValue(0.0); ft.setToValue(0.97); ft.play();
		 */
		
	}
	public static void main(String[] args) throws Exception {
		launch(args);
	}
	
	public ObservableList<TipoGastoDeducible> getTipoGastoDeducible() {
    	return tipoGastoDeducible ;
    }

}
