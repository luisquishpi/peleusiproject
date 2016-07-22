package ec.peleusi.views.fx.controllers;

import ec.peleusi.controllers.CompraController;
import ec.peleusi.models.entities.Compra;
import ec.peleusi.models.entities.Proveedor;
import ec.peleusi.models.entities.TipoPago;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;


public class CompraTipoPagoListFxController extends GenericController {
	
	@FXML
	private Button btnNuevo;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnBuscar;
	@FXML
	private Button btnBuscarTipoPago;	
	@FXML	
	private TextField txtBuscar;	
	@FXML
	public TextField txtBaseImponible0;
	@FXML
	public TextField txtBaseImponibleDiferente0;
	@FXML
	public TextField txtDescuento;
	@FXML
	public TextField txtMontoIva;
	@FXML
	public TextField txtMontoIce;
	@FXML
	public TextField txtTotal;
	@FXML
	public TextField txtNombre;
	@FXML
	public TextField txtValor;
	@FXML
	public TextField txtDetalle;
	@FXML
	public TableView< TipoPago> tblTipoPago;
	@FXML
	private Pagination pagination;
	@FXML
	private TableView<Compra> tblLista;
	
	ObservableList<Compra> compraList;
	final static int rowsPerPage = 100;
	
	@FXML
	private void initialize() {
		
		crearTablaListaProductos();
		paginar();
		
	}
	@FXML
	private void btnNuevoClick(ActionEvent event) {
		
	}
	@FXML
	private void btnGuardarClick(ActionEvent event) {		
	}
	@FXML
	private void btnEliminarClick(ActionEvent event) {		
	}
	
	@FXML
	private void btnCancelarClick(ActionEvent event) {		
	}
	@FXML
	private void btnBuscarClick(ActionEvent event) {		
	}
	@FXML
	private void btnBuscarTipoPagoClick(ActionEvent event) {		
	}
	
	private void crearTablaListaProductos() {
		tblLista = new TableView<Compra>();
		
		TableColumn<Compra, Proveedor> identificacionCol = new TableColumn<Compra, Proveedor>("Identificacion");
		identificacionCol.setCellValueFactory(new PropertyValueFactory<Compra, Proveedor>("identificacion"));
		
		TableColumn<Compra, Proveedor> razonSocialCol = new TableColumn<Compra, Proveedor>("Razon Social");
		razonSocialCol.setCellValueFactory(new PropertyValueFactory<Compra, Proveedor>("razonSocial"));
		
		TableColumn<Compra, String> establecimientoCol = new TableColumn<Compra, String>("Estableciento");
		establecimientoCol.setCellValueFactory(new PropertyValueFactory<Compra, String>("establecimiento"));
		
		TableColumn<Compra, String> puntoEmisionCol = new TableColumn<Compra, String>("Punto Emision");
		puntoEmisionCol.setCellValueFactory(new PropertyValueFactory<Compra, String>("puntoEmision"));
		
		TableColumn<Compra, String> secuencialCol = new TableColumn<Compra, String>("Secuencial");
		secuencialCol.setCellValueFactory(new PropertyValueFactory<Compra, String>("secuencial"));	
		

		tblLista.getColumns().add(identificacionCol);
		tblLista.getColumns().add(razonSocialCol);
		tblLista.getColumns().add(establecimientoCol);
		tblLista.getColumns().add(puntoEmisionCol);
		tblLista.getColumns().add(secuencialCol);
		
		
		CompraController compraController= new CompraController();

		compraList = FXCollections.observableList(compraController.compraList());
		tblLista.setItems(compraList);
		final ObservableList<Compra> comprasListSelected = tblLista.getSelectionModel().getSelectedItems();
		comprasListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<Compra> escuchaCambiosEnTabla = new ListChangeListener<Compra>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Compra> c) {
			cargarObjetoSeleccionadaEnFormulario();
		}
	};
	
	private void cargarObjetoSeleccionadaEnFormulario() {		
		
	}
	
	private void paginar() {
		int count = compraList.size() / rowsPerPage;
		if (count < ((double) (compraList.size()) / rowsPerPage))
			count++;
		if (count == 0 && compraList.size() > 0)
			count++;
		if (compraList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, compraList.size());
					tblLista.setItems(FXCollections.observableArrayList(compraList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}
	
	}
