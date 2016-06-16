package ec.peleusi.views.fx.mains;

import java.io.IOException;
import ec.peleusi.views.fx.controllers.TarifaIvaListFxController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TarifaIvaListFxMain extends Application {
	private static Parent parent;

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/TarifaIvaListFx.fxml"));
		loader.setController(new TarifaIvaListFxController());
		parent = (Parent) loader.load();
		stage.setTitle("Lista de Tarifa IVA");
		stage.setScene(new Scene(parent));}
	
	//btnNuevo.setDefaultButton(true);

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
