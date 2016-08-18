package ec.peleusi.views.fx.main;

import java.io.IOException;

import ec.peleusi.views.fx.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private static Parent parent;

	@Override
	public void start(Stage stage) throws IOException {
		stage.setTitle("Peleusí");
		// stage.initStyle(StageStyle.UNDECORATED);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/Main.fxml"));
		loader.setController(new MainController());
		parent = (Parent) loader.load();
		stage.setMaximized(true);
		stage.setScene(new Scene(parent));
		// stage.setFullScreen(true);
		stage.show();
	}

	public static void main(String[] args) throws Exception {
		launch(args);
	}
}
