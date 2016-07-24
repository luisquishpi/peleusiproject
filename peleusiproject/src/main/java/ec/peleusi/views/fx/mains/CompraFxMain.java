package ec.peleusi.views.fx.mains;

import java.io.IOException;

import ec.peleusi.views.fx.controllers.CompraFxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CompraFxMain extends Application{
	
	private static Parent parent;	
	 
	@Override	
	public void start(Stage stage) throws IOException {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/Compra.fxml"));
		loader.setController(new CompraFxController());		
	
		parent = (Parent) loader.load();        
		stage.setTitle("Compra de productos");
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

}
