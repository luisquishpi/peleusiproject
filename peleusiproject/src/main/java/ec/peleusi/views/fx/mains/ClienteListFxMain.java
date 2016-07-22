package ec.peleusi.views.fx.mains;

import java.io.IOException;

import ec.peleusi.views.fx.controllers.ClienteListFxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClienteListFxMain extends Application {
	private static Parent parent;
	
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/ClienteListFx.fxml"));
		loader.setController(new ClienteListFxController());
		parent = (Parent) loader.load();
		stage.setTitle("Lista de Cliente");
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
