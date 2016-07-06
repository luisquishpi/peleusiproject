package ec.peleusi.views.fx.controllers;


import java.awt.Button;

import ec.peleusi.models.entities.CompraDetalle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;


public class CompraProductoFxController extends AnchorPane {
	
	@FXML
	private TableView<Object> tvwProducto;
	@FXML
	private TableColumn<Object, Integer> idCol;
	@FXML
	private TableColumn<Object, String> codigoCol ;
	@FXML
	private TableColumn<Object, String> nombreCol;
	@FXML
	private TableColumn<Object, Double> cantidadCol;
	@FXML
	private TableColumn<Object, Double> costoCol;
	@FXML
	private TableColumn<Object, Double> porcentajeDescuentoCol;
	@FXML
	private TableColumn<Object, Double> valorDescuentoCol;
	@FXML
	private TableColumn<Object, Double> precioNetoCol;
	@FXML
	private TableColumn<Object, Double> subtotalCol;
	@FXML
	private TableColumn<Object, Double> porcentaIvaCol;
	@FXML
	private TableColumn<Object, Double> valorIvaCol;
	@FXML
	private TableColumn<Object, Double> stockCol;
	@FXML
	private TableColumn<Object, Double> porcentajeIceCol;
	@FXML
	private TableColumn<Object, Double> valorIceCol;
	@FXML
	private TableColumn<Object, Double> totalCol;
	@FXML
	private TableColumn<Object, Integer> idProductoCol;	
	@FXML
	public TextField txtProducto;		
	@FXML
	public Button bntBuscarProducto;	
	//@FXML
	//private Button btnNuevo;
//	@FXML
	//private Button btnGuardar;
	//@FXML
	//private Button btnEliminar;
	//@FXML
	//private Button btnCancelar;
	//@FXML
	//private Button btnBuscar;
	
	
	@FXML
	private void initialize() {
		
      System.out.println("Hola");
	

	}
	

}
