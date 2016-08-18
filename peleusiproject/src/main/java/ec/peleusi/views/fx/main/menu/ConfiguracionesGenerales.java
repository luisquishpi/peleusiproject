package ec.peleusi.views.fx.main.menu;

import java.io.IOException;

import ec.peleusi.views.fx.controllers.CiudadListFxController;
import ec.peleusi.views.fx.controllers.EmpresaFxController;
import ec.peleusi.views.fx.controllers.GenericController;
import ec.peleusi.views.fx.controllers.TarifaIceListFxController;
import ec.peleusi.views.fx.controllers.TarifaIvaListFxController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ConfiguracionesGenerales {
	private Button btnCiudad, btnEmpresa, btnTarifaIva, btnTarifaIce, btnTipoPago, btnCaja;
	private VBox root;
	private TabPane tpPrincipal;
	private SingleSelectionModel<Tab> selectionModel;
	private Tab ciudadesTab = new Tab();
	private Tab empresaTab = new Tab();
	private Tab tarifaIvaTab = new Tab();
	private Tab tarifaIceTab = new Tab();

	public ConfiguracionesGenerales(TabPane tpPrincipal) {
		this.tpPrincipal = tpPrincipal;
		this.root = new VBox();
		build();
	}

	public VBox get() {
		return this.root;
	}

	private void build() {

		GridPane layout = new GridPane();
		layout.setGridLinesVisible(false);
		layout.setHgap(5);

		// Build UI Controls
		this.buildCiudadesButton();
		this.buildEmpresaButton();
		this.buildTarifaIvaButton();
		this.buildTarifaIceButton();

		// Add All Componets to the GridPane.
		layout.add(this.btnCiudad, 0, 0);
		layout.add(this.btnEmpresa, 1, 0);
		layout.add(this.btnTarifaIva, 2, 0);
		layout.add(this.btnTarifaIce, 3, 0);

		Label label = new Label("Generales");
		label.getStyleClass().add("ribbonLabel");
		label.setTooltip(new Tooltip("Parámetros y Configuraciones Generales del Sistema"));

		// TODO: find a better way to center a label.
		VBox vbox = new VBox();
		vbox.getChildren().add(label);
		VBox.setVgrow(label, Priority.ALWAYS);
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.setStyle("-fx-padding: 5 0 0 0");
		layout.add(vbox, 0, 2, 6, 1);

		// Center alignment in the VBox, add GridPane, set VBox CSS Selector.
		this.root.setAlignment(Pos.CENTER);
		this.root.getChildren().add(layout);
		this.root.getStyleClass().add("toolbarContainer");
	}

	private void buildTarifaIceButton() {
		this.btnTarifaIce = new Button("Tarifa ICE");
		buildButton(btnTarifaIce, "Lista de tarifas ICE", null, tarifaIceTab, "Tarifa ICE", "TarifaIceListFx.fxml",
				new TarifaIceListFxController());
	}

	private void buildTarifaIvaButton() {
		this.btnTarifaIva = new Button("Tarifa IVA");
		buildButton(btnTarifaIva, "Lista de tarifas IVA", null, tarifaIvaTab, "Tarifa IVA", "TarifaIvaListFx.fxml",
				new TarifaIvaListFxController());
	}

	private void buildCiudadesButton() {
		this.btnCiudad = new Button("Ciudades");
		buildButton(btnCiudad, "Lista de Ciudades", "fx-new.png", ciudadesTab, "Ciudades", "CiudadListFx.fxml",
				new CiudadListFxController());
	}

	private void buildEmpresaButton() {
		this.btnEmpresa = new Button("Empresa");
		buildButton(btnEmpresa, "Datos de la empresa", "fx-new.png", empresaTab, "Empresa", "EmpresaFx.fxml",
				new EmpresaFxController());
	}

	private void buildButton(Button btn, String toolTipButton, String nombreImagen, Tab tab, String txtTab,
			String nombreDisenoFx, GenericController controlador) {
		btn.setTooltip(new Tooltip(toolTipButton));
		tab.setText(txtTab);
		// Set the Image above the text.
		btn.setContentDisplay(ContentDisplay.TOP);
		if (nombreImagen != null) {
			Image image = new Image(this.getClass().getResourceAsStream("../../images/main/" + nombreImagen), 24.0,
					24.0, true, true);
			ImageView imageView = new ImageView(image);
			btn.setGraphic(imageView);
		}
		btn.getStyleClass().add("ribbonToggleButton");
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				selectionModel = tpPrincipal.getSelectionModel();
				if (!tpPrincipal.getTabs().contains(tab)) {
					try {
						tab.setClosable(true);
						FXMLLoader loader = new FXMLLoader(getClass().getResource("../../designs/" + nombreDisenoFx));
						loader.setController(controlador);
						tab.setContent(loader.load());
					} catch (IOException e) {
						e.printStackTrace();
					}
					tpPrincipal.getTabs().add(tab);
					controlador.tabIndex = tpPrincipal.getTabs().indexOf(tab);
					selectionModel.selectLast();
				}
			}
		});
	}
}
