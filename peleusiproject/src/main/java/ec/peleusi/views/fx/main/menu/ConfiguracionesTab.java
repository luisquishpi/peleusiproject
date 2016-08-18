package ec.peleusi.views.fx.main.menu;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;

public class ConfiguracionesTab {
	private Tab tab;
	private TabPane tpPrincipal;

	public ConfiguracionesTab(TabPane tpPrincipal) {
		this.tpPrincipal = tpPrincipal;
		tab = new Tab("Configuraciones");
		buildTab();
	}

	public Tab get() {
		return this.tab;
	}

	private void buildTab() {
		tab.setClosable(false);
		HBox container = new HBox();
		container.setId("container"); // Set preferred height.
		container.setPrefHeight(90);
		container.setSpacing(5);

		ConfiguracionesGenerales configuracionesGenerales = new ConfiguracionesGenerales(tpPrincipal);
		container.getChildren().add(configuracionesGenerales.get());
		tab.setContent(container);

	}
}
