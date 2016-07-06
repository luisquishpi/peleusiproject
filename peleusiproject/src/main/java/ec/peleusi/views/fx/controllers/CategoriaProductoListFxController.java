package ec.peleusi.views.fx.controllers;

import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import ec.peleusi.controllers.CategoriaProductoController;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.utils.fx.AlertsUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CategoriaProductoListFxController extends AnchorPane {

	@FXML
	public TextField txtNombre;
	@FXML
	private TextField txtDependencia;
	@FXML
	private CheckBox chkContieneProductos;
	@FXML
	private Button btnNuevo;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TreeView<CategoriaProducto> tvwProducto;
	CategoriaProducto categoriaProducto;
	CategoriaProducto catTmp;

	@FXML
	private void initialize()

	{
		cargarArbolCategoriaProductos();
		System.out.println("Cargando nuevo "+categoriaProducto);
		//btnNuevoClick();
		
	   tvwProducto.getSelectionModel().select(0);
	   btnNuevoClick();
		tvwProducto.getSelectionModel().selectedItemProperty().addListener((v, oldValue, getObjetoSeleccionado) -> {
			if (getObjetoSeleccionado != null)
				cargarDatos(getObjetoSeleccionado.getValue());
		});

	}

	public Object getObjetoSeleccionado() {
		if (tvwProducto != null) {
			if (tvwProducto.getSelectionModel().getSelectedItems().size() >= 1) {
				return tvwProducto.getSelectionModel().getSelectedItems().get(0);
			}
		}
		return null;
	}

	private void cargarDatos(CategoriaProducto categoriaProductoCargar) {
		
		
		if (categoriaProductoCargar != null) {

			if (tvwProducto.getSelectionModel().getSelectedItem().getValue().getId() != 0) {
				txtNombre.setText(categoriaProductoCargar.getNombre());

				if (tvwProducto.getTreeItemLevel(tvwProducto.getSelectionModel().getSelectedItem().getParent()) != 0) {
					txtDependencia.setText(
							tvwProducto.getSelectionModel().getSelectedItem().getParent().getValue().toString());
				} else {
					txtDependencia.setText("Categoria de Productos");
				}
				chkContieneProductos.setSelected(categoriaProductoCargar.getContieneProductos());
				categoriaProducto = categoriaProductoCargar;
				btnGuardar.setText("Actualizar");
				chkContieneProductosClick();
				btnEliminar.setDisable(false);
				if(contieneSubcategorias()==0) chkContieneProductos.setDisable(false);
				else chkContieneProductos.setDisable(true);
				 

			} else {
				btnNuevoClick();
				txtNombre.requestFocus();
			}

		} else {

		}
	}

	private void limpiarcampos() {

		txtNombre.setText("");
		chkContieneProductos.setSelected(false);
		categoriaProducto = null;
		txtDependencia.setText(tvwProducto.getSelectionModel().getSelectedItem().getValue().toString());
		btnGuardar.setText("Guardar");
		btnEliminar.setDisable(true);
		txtNombre.requestFocus();

	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	@FXML
	private void btnNuevoClick() {
		limpiarcampos();
	}

	@FXML
	private void btnGuardarClick() {

		String err = "";

		if (!isCamposLlenos()) {
			JOptionPane.showMessageDialog(null, "No deje campos vacíos");
			return;
		}

		if (categoriaProducto == null) {
			
			CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
			if(categoriaProductoController.existCategoriaProducto(0,txtNombre.getText(),tvwProducto.getSelectionModel().getSelectedItem().getValue().getId() )==false)
			{

			categoriaProducto = new CategoriaProducto();
			categoriaProducto.setNombre(txtNombre.getText());
			categoriaProducto.setDependencia(tvwProducto.getSelectionModel().getSelectedItem().getValue().getId());
			categoriaProducto.setContieneProductos(chkContieneProductos.isSelected());			
			err = categoriaProductoController.saveCategoriaProducto(categoriaProducto);
			if (err == null) {
				AgregarCategoria(categoriaProducto);
				limpiarcampos();
				AlertsUtil.alertExito("Guardado correctamente");
				chkContieneProductosClick();
				
			} else {
				AlertsUtil.alertError(err);
			}
			}
			else
			{
				AlertsUtil.alertError("Ya existe una categoría con este mismo nombre");
			}
		} else {
			
			CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
			if(categoriaProductoController.existCategoriaProducto(tvwProducto.getSelectionModel().getSelectedItem().getValue().getId(),txtNombre.getText(),tvwProducto.getSelectionModel().getSelectedItem().getValue().getDependencia() )==false)
			{
				
				System.out.println(tvwProducto.getSelectionModel().getSelectedItem().getValue().getId()+""+txtNombre.getText()+""+tvwProducto.getSelectionModel().getSelectedItem().getValue().getId());

			categoriaProducto.setNombre(txtNombre.getText());
			categoriaProducto
					.setDependencia(tvwProducto.getSelectionModel().getSelectedItem().getValue().getDependencia());
			categoriaProducto.setContieneProductos(chkContieneProductos.isSelected());
			
			err = categoriaProductoController.updateCategoriaProducto(categoriaProducto);
			if (err == null) {
				ActualizatCategoria(categoriaProducto);
				AlertsUtil.alertExito("Actualizado correctamente");
				chkContieneProductosClick();				

			} else {
				AlertsUtil.alertError(err);
			}
			}
			else
			{
				AlertsUtil.alertError("Ya existe una categoría con este mismo nombre");
			}
			
			
			
		}
	}

	@FXML
	private void btnEliminarClick() {
		TreeItem<CategoriaProducto> paren = tvwProducto.getSelectionModel().getSelectedItem();
		Optional<ButtonType> result = AlertsUtil.alertConfirmation(
				"Está seguro que desea eliminar: \n" + tvwProducto.getSelectionModel().getSelectedItem().getValue());
		if (result.get() == ButtonType.OK) {

			if (paren != null) {
				if (paren.getChildren().size() == 0) {
					TreeItem<CategoriaProducto> parentNode = paren.getParent();
					if (parentNode != null) {

						CategoriaProductoController categoriaProducto = new CategoriaProductoController();
						categoriaProducto.delete(paren.getValue().getId());
						parentNode.getChildren().remove(paren);

					}
				} else {

					AlertsUtil.alertError("No es posible eliminar porque contiene subcategorias.");
				}

			}
		}

	}

	@FXML
	private void btnCancelarClick() {
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	public void AgregarCategoria(CategoriaProducto categoriaNuevo) {
		TreeItem<CategoriaProducto> parent = tvwProducto.getSelectionModel().getSelectedItem();
		if (parent == null) {
			parent = tvwProducto.getRoot();
		}
		final TreeItem<CategoriaProducto> newNode = new TreeItem<CategoriaProducto>(categoriaNuevo);
		parent.getChildren().add(newNode);
		parent.setExpanded(true);
		tvwProducto.getSelectionModel().select(newNode);
	}

	public void ActualizatCategoria(CategoriaProducto categoriaNuevo) {
		TreeItem<CategoriaProducto> parent = tvwProducto.getSelectionModel().getSelectedItem();
		if (parent == null) {
			parent = tvwProducto.getRoot();
		}
		parent.setValue(categoriaNuevo);
		tvwProducto.refresh();
		parent.setExpanded(true);
		tvwProducto.getSelectionModel().select(parent);
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
		raiz.setExpanded(true);
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

	@FXML
	private void chkContieneProductosClick() {	
		
		if (categoriaProducto == null) {
			btnNuevo.setDisable(false);
		} else {
			if (categoriaProducto.getContieneProductos() == true) {

				btnNuevo.setDisable(true);
			} else {
				btnNuevo.setDisable(false);
			}
		}
	}
	private Integer contieneSubcategorias()
	{
		TreeItem<CategoriaProducto> paren = tvwProducto.getSelectionModel().getSelectedItem();		
		Integer nSubcatgorias= paren.getChildren().size();
		return nSubcatgorias;	
	}

}
