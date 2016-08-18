package ec.peleusi.views.fx.controllers;

import java.util.List;
import ec.peleusi.controllers.CategoriaProductoController;
import ec.peleusi.models.entities.CategoriaProducto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class CategoriaProductoListModalFxController {

	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TreeView<CategoriaProducto> tvwProducto;
	CategoriaProducto categoriaProducto;
	CategoriaProducto catTmp;

	@FXML
	private void initialize() {
		cargarArbolCategoriaProductos();
		tvwProducto.getSelectionModel().select(0);
		tvwProducto.getSelectionModel().selectedItemProperty().addListener((v, oldValue, getObjetoSeleccionado) -> {
			if (getObjetoSeleccionado != null)
				cargarDatos();
		});
	}

	private void cargarDatos() {
		categoriaProducto = null;
		if (tvwProducto.getSelectionModel().getSelectedItem().getValue().getId() != 0) {
			if (tvwProducto.getSelectionModel().getSelectedItem().getValue().getContieneProductos()) {
				categoriaProducto = tvwProducto.getSelectionModel().getSelectedItem().getValue();
				btnAceptar.setDisable(false);
			} else {
				btnAceptar.setDisable(true);
			}

		}
	}

	@FXML
	private void btnAceptarClick() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick() {
		categoriaProducto = null;
		btnAceptarClick();
	}

	public void cargarArbolCategoriaProductos() {
		catTmp = new CategoriaProducto();
		catTmp.setNombre("Categoria de Productos");
		catTmp.setId(0);
		catTmp.setContieneProductos(false);
		TreeItem<CategoriaProducto> raiz = new TreeItem<CategoriaProducto>(catTmp);
		getHojas(raiz, "0");
		tvwProducto.setRoot(raiz);

	}

	public void getHojas(TreeItem<CategoriaProducto> raiz, String id) {
		raiz.setExpanded(false);
		CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
		List<CategoriaProducto> lista;
		lista = categoriaProductoController.CategoriaProductoList(Integer.parseInt(id));
		for (CategoriaProducto categoriaProducto : lista) {
			TreeItem<CategoriaProducto> hoja = new TreeItem<CategoriaProducto>(categoriaProducto);
			hoja.getValue();
			getHojas(hoja, categoriaProducto.getId().toString());
			raiz.getChildren().add(hoja);
		}
	}

	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}
}
