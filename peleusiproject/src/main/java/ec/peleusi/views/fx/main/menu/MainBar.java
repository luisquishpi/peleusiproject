package ec.peleusi.views.fx.main.menu;

import javafx.scene.control.TabPane;

public class MainBar {
	private TabPane tabPane;
	private TabPane tpPrincipal;

	public MainBar(TabPane tpPrincipal) {
		this.tpPrincipal = tpPrincipal;
		tabPane = new TabPane();
		buildTabs();
	}

	public TabPane get() {
		return this.tabPane;
	}

	private void buildTabs() {
		ConfiguracionesTab configuracionesTab = new ConfiguracionesTab(tpPrincipal);
		tabPane.getTabs().addAll(configuracionesTab.get());
	}
}
