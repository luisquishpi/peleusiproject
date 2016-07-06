package ec.peleusi.views.fx.controllers;

import ec.peleusi.views.fx.main.menu.MainBar;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MainController extends AnchorPane {

	@FXML
	private VBox vbMenu;

	@FXML
	private TabPane tpPrincipal;

	@FXML
	private void initialize() {
		MainBar mainBar = new MainBar(tpPrincipal);
		vbMenu.getChildren().add(mainBar.get());
	}
}
