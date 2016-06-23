package ec.peleusi.views.fx.mains;

import java.io.IOException;

import ec.peleusi.views.fx.controllers.EmpresaFxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EmpresaFxMain extends Application {
	private static Parent parent;
	
	@Override
	public void start(Stage stage) throws IOException {
		stage.setTitle("Datos de la Empresa");

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/EmpresaFx.fxml"));
		loader.setController(new EmpresaFxController());
		parent = (Parent) loader.load();
		stage.setScene(new Scene(parent));
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
