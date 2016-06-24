package ec.peleusi.views.fx.controllers;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ec.peleusi.controllers.CategoriaProductoController;
import ec.peleusi.models.entities.CategoriaProducto;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CategoriaProductoListFxController extends AnchorPane {
	
	
	@FXML
	private TreeView<String> tvwProducto;
	TreeItem<String> rootNode = new TreeItem<String>("Categorias");
	
	@FXML	
	private void initialize()
	
	{
		cargarArbolCategoriaProductos();		
	
	}
	
	public void cargarArbolCategoriaProductos() {				
		TreeItem<String> raiz = new TreeItem<String>("categoria");
		getHojas(raiz, "0");
		tvwProducto.setRoot(raiz);
		
	}
	public void getHojas(TreeItem<String> raiz, String id) {
		raiz.setExpanded(true);
		CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
		List<CategoriaProducto> lista;
		lista = categoriaProductoController.CategoriaProductoList(Integer.parseInt(id));
		for (CategoriaProducto categoriaProducto : lista) 
		{			
			TreeItem<String> hoja = new TreeItem<String>(categoriaProducto.getNombre());		
			hoja.getValue();			
			getHojas(hoja, categoriaProducto.getId().toString());			
			raiz.getChildren().add(hoja);				 
		}		
	}
}
