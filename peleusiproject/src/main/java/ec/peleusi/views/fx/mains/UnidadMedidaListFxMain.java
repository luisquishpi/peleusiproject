package ec.peleusi.views.fx.mains;

import java.io.IOException;

import ec.peleusi.views.fx.controllers.UnidadMedidaListFxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UnidadMedidaListFxMain extends Application {
	private static Parent parent;

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/UnidadMedidaListFx.fxml"));
		loader.setController(new UnidadMedidaListFxController());
		parent = (Parent) loader.load();
		stage.setTitle("Lista de Unidad de Medida");
		stage.setScene(new Scene(parent));
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
