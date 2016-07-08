package ec.peleusi.views.fx.controllers;

import java.awt.Button;
import java.util.List;

import ec.peleusi.controllers.DireccionProveedorController;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.ProveedorController;
import ec.peleusi.controllers.SeteoController;
import ec.peleusi.models.entities.DireccionProveedor;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.Proveedor;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.general.CompraDetalle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.DoubleStringConverter;

public class CompraProductoFxController extends AnchorPane {

	@FXML
	private TableView<CompraDetalle> tblDetalleCompra;
	@FXML
	private TableColumn<CompraDetalle, Integer> idCol;
	@FXML
	private TableColumn<CompraDetalle, Producto> idProductoCol;
	@FXML
	private TableColumn<CompraDetalle, Double> stockCol;
	@FXML
	private TableColumn<CompraDetalle, String> codigoCol;
	@FXML
	private TableColumn<CompraDetalle, String> nombreCol;
	@FXML
	private TableColumn<CompraDetalle, Double> cantidadCol;
	@FXML
	private TableColumn<CompraDetalle, Double> costoCol;
	@FXML
	private TableColumn<CompraDetalle, Double> porcentajeDescuentoCol;
	@FXML
	private TableColumn<CompraDetalle, Double> valorDescuentoCol;
	@FXML
	private TableColumn<CompraDetalle, Double> precioNetoCol;
	@FXML
	private TableColumn<CompraDetalle, Double> subtotalCol;
	@FXML
	private TableColumn<CompraDetalle, Double> porcentaIvaCol;
	@FXML
	private TableColumn<CompraDetalle, Double> valorIvaCol;
	@FXML
	private TableColumn<CompraDetalle, Double> porcentajeIceCol;
	@FXML
	private TableColumn<CompraDetalle, Double> valorIceCol;
	@FXML
	private TableColumn<CompraDetalle, Double> totalCol;
	@FXML
	public TextField txtProducto;
	@FXML
	public Button bntBuscarProducto;
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
	public TextField txtRuc;
	@FXML
	public TextField txtContribuyente;
	@FXML
	public TextField txtDireccion;
	@FXML
	public TextField txtTelefono;
	@FXML
	public TextField txtEstablecimiento;
	@FXML
	public TextField txtPuntoEmision;
	@FXML
	public TextField txtSecuencial;
	@FXML
	public TextField txtAutorizacion;
	@FXML
	public TextField txtDiasCredito;
	@FXML
	public DatePicker dtpFechaEmision;
	@FXML
	public DatePicker dtpFechaAutorizacion;
	@FXML
	public DatePicker dtpFechaVencimiento;
	@FXML
	public DatePicker dtpFechaRegistro;
	@FXML
	public javafx.scene.control.Button btnBuscarProveedor;

	ObservableList<CompraDetalle> oblCompraDetalleList = FXCollections.observableArrayList();
	Proveedor proveedor;

	// @FXML
	// private Button btnNuevo;
	// @FXML
	// private Button btnGuardar;
	// @FXML
	// private Button btnEliminar;
	// @FXML
	// private Button btnCancelar;
	// @FXML
	// private Button btnBuscar;

	public Double precioNetoFila = 0.0;
	public Double precioBrutoFila = 0.0;
	public Double valorDescuentoFila = 0.0;
	public Double subtotalFila = 0.0;
	public Double valorIvaFila = 0.0;
	public Double totalFila = 0.0;
	public Double cantidadFila = 1.0;
	public Double porcentajeDescuentoFila = 0.0;
	public Double valorIceFila = 0.0;
	private Double subtotalIva0 = 0.0;
	private Double subtotalIvaDiferente0 = 0.0;
	private Double totalDescuento = 0.0;
	private Double totalIva = 0.0;
	private Double total = 0.0;
	private Double totalIce = 0.0;

	@FXML
	private void initialize() {

		idCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Integer>("id"));
		idProductoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Producto>("idProducto"));
		codigoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, String>("codigo"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, String>("nombre"));
		cantidadCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("cantidad"));
		costoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("costo"));
		porcentajeDescuentoCol
				.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("porcentajeDescuento"));
		valorDescuentoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("valorDescuento"));
		precioNetoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("precioNeto"));
		subtotalCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("subtotal"));
		porcentaIvaCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("porcentaIva"));
		valorIvaCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("valorIva"));
		stockCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("stock"));
		porcentajeIceCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("porcentajeIce"));
		valorIceCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("valorIce"));
		totalCol.setCellValueFactory(new PropertyValueFactory<CompraDetalle, Double>("total"));
		cantidadCol.setEditable(true);
		porcentajeDescuentoCol.setEditable(true);
		costoCol.setEditable(true);

		cantidadCol
				.setCellFactory(TextFieldTableCell.<CompraDetalle, Double> forTableColumn(new DoubleStringConverter()));
		porcentajeDescuentoCol
				.setCellFactory(TextFieldTableCell.<CompraDetalle, Double> forTableColumn(new DoubleStringConverter()));
		costoCol.setCellFactory(TextFieldTableCell.<CompraDetalle, Double> forTableColumn(new DoubleStringConverter()));

		cantidadCol.setOnEditCommit((CellEditEvent<CompraDetalle, Double> t) -> {
			((CompraDetalle) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCantidad(t.getNewValue());
			actualizarValores(oblCompraDetalleList, tblDetalleCompra, t.getTablePosition().getRow());

		});
		porcentajeDescuentoCol.setOnEditCommit((CellEditEvent<CompraDetalle, Double> t) -> {
			((CompraDetalle) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setPorcentajeDescuento(t.getNewValue());
			actualizarValores(oblCompraDetalleList, tblDetalleCompra, t.getTablePosition().getRow());
		});
		costoCol.setOnEditCommit((CellEditEvent<CompraDetalle, Double> t) -> {
			((CompraDetalle) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCosto(t.getNewValue());

			actualizarValores(oblCompraDetalleList, tblDetalleCompra, t.getTablePosition().getRow());
		});

		tblDetalleCompra.setItems(oblCompraDetalleList);
		limpiarControles();

	}

	private void cargarDetalleProducto(Producto producto) {
		System.out.println("Producto" + producto.getStock());

		precioBrutoFila = producto.getCosto();
		valorDescuentoFila = precioBrutoFila * ((porcentajeDescuentoFila) / 100);
		precioNetoFila = precioBrutoFila - valorDescuentoFila;
		subtotalFila = precioNetoFila * cantidadFila;
		valorIvaFila = subtotalFila * (PorcentajeIvaProducto(producto.getTieneIva()) / 100);
		valorIceFila = subtotalFila * (producto.getTarifaIce().getPorcentaje() / 100);
		totalFila = valorIvaFila + subtotalFila + valorIceFila;
		oblCompraDetalleList.add(new CompraDetalle(0, producto, producto.getCodigo(), producto.getNombre(),
				cantidadFila, producto.getCosto(), porcentajeDescuentoFila, valorDescuentoFila, precioNetoFila,
				subtotalFila, PorcentajeIvaProducto(producto.getTieneIva()), valorIvaFila, producto.getStock(),
				producto.getTarifaIce().getPorcentaje(), valorIceFila, totalFila));

		calcularTotales();
	}

	private Double PorcentajeIvaProducto(Boolean productoConIva) {

		double tarifaIva = 0;
		SeteoController seteoController = new SeteoController();
		List<Seteo> listaSeteo;
		listaSeteo = seteoController.seteoList();
		if (productoConIva == true)
			tarifaIva = listaSeteo.get(0).getTarifaIva().getPorcentaje();
		else
			tarifaIva = 0;
		return tarifaIva;

	}

	private void limpiarControles() {
		txtBaseImponible0.setText("0.00");
		txtBaseImponibleDiferente0.setText("0.00");
		txtDescuento.setText("0.00");
		txtMontoIva.setText("0.00");
		txtMontoIce.setText("0.00");
		txtTotal.setText("0.00");
		txtProducto.requestFocus();
	}

	private void calcularTotales() {
		subtotalIvaDiferente0 = 0.0;
		subtotalIva0 = 0.0;
		totalIva = 0.0;
		totalIce = 0.0;
		totalDescuento = 0.0;
		total = 0.0;
		System.out.println("Numero de datos....>" + oblCompraDetalleList.size());

		for (CompraDetalle compraDetalle : oblCompraDetalleList) {
			if (compraDetalle.getPorcentaIva() != 0) {
				subtotalIvaDiferente0 = subtotalIvaDiferente0 + compraDetalle.getSubtotal();
			} else {
				subtotalIva0 = subtotalIva0 + compraDetalle.getSubtotal();
			}
			totalIva = totalIva + compraDetalle.getValorIva();
			totalDescuento = totalDescuento + compraDetalle.getValorDescuento();
			total = total + compraDetalle.getTotal();
			totalIce = totalIce + compraDetalle.getValorIce();
		}
		txtBaseImponible0.setText(subtotalIva0.toString());
		txtBaseImponibleDiferente0.setText(subtotalIvaDiferente0.toString());
		txtDescuento.setText(totalDescuento.toString());
		txtMontoIva.setText(totalIva.toString());
		txtTotal.setText(total.toString());
		txtMontoIce.setText(totalIce.toString());
	}

	public void actualizarValores(ObservableList<CompraDetalle> modelo, TableView<CompraDetalle> table,
			int filaPosition) {
		try {

			precioBrutoFila = modelo.get(filaPosition).getCosto();
			porcentajeDescuentoFila = modelo.get(filaPosition).getPorcentajeDescuento();
			valorDescuentoFila = precioBrutoFila * ((porcentajeDescuentoFila) / 100);
			precioNetoFila = precioBrutoFila - valorDescuentoFila;
			cantidadFila = modelo.get(filaPosition).getCantidad();
			subtotalFila = precioNetoFila * cantidadFila;
			valorIvaFila = subtotalFila * (modelo.get(filaPosition).getPorcentaIva() / 100);
			valorIceFila = subtotalFila * (modelo.get(filaPosition).getPorcentajeIce() / 100);
			totalFila = valorIvaFila + subtotalFila + valorIceFila;
			modelo.get(filaPosition).setPrecioNeto(precioNetoFila);
			modelo.get(filaPosition).setValorDescuento(valorDescuentoFila);
			modelo.get(filaPosition).setSubtotal(subtotalFila);
			modelo.get(filaPosition).setValorIva(valorIvaFila);
			modelo.get(filaPosition).setValorIce(valorIceFila);
			modelo.get(filaPosition).setTotal(totalFila);
			table.refresh();
			calcularTotales();

		} catch (Exception e) {
		}
	}

	@FXML
	private void bntBuscarProductoClick() {
		ProductoController productoController = new ProductoController();
		Producto producto = new Producto();
		producto = productoController.getProductoCodigo(txtProducto.getText());
		cargarDetalleProducto(producto);
	}

	@FXML
	private void txtProductorReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			bntBuscarProductoClick();
		}
	}

	@FXML
	private void btnBuscarProveedorClick() {
		buscarProveedoridentificacion(txtRuc.getText());

	}

	@FXML
	private void txtRucReleased(KeyEvent event) {

		System.out.println("hhhhhhhhhhhhh");
		if (event.getCode() == KeyCode.ENTER) {
			System.out.println(txtRuc.getText());
			buscarProveedoridentificacion(txtRuc.getText());
		}
	}

	public void calcularFechaVencimiento() {

		/*
		 * Calendar cal = Calendar.getInstance();
		 * cal.setTime(dtpFechaEmision.getValue().);
		 * cal.add(Calendar.DAY_OF_YEAR,
		 * Integer.parseInt(txtDiasCredito.getText()));
		 * dtcFechaVencimiento.setDate(cal.getTime());
		 */
	}

	private void buscarProveedoridentificacion(String identificacion) {
		ProveedorController proveedoersonaController = new ProveedorController();

		proveedor = new Proveedor();
		proveedor = proveedoersonaController.getProveedorIdentificacion(identificacion);
		if (proveedor != null) {
			System.out.println("Categoría seleccionado: " + proveedor);
			txtRuc.setText(proveedor.getIdentificacion());
			txtContribuyente.setText(proveedor.getRazonSocial());
			// cargarDireccionProveedorPorDefecto(proveedor);
		} else {

			txtContribuyente.setText("");
			txtDireccion.setText("");
			txtTelefono.setText("");
			// llamarVentanaProveedor();

		}

	}

	private void cargarDireccionProveedorPorDefecto(Proveedor proveedor) {
		DireccionProveedorController direccionProvedorController = new DireccionProveedorController();
		DireccionProveedor direccionProveedor = new DireccionProveedor();
		direccionProveedor = direccionProvedorController.getDireccionProveedorPorDefecto(proveedor);
		if (direccionProveedor != null) {
			txtDireccion.setText(direccionProveedor.getNombre());
			txtTelefono.setText(direccionProveedor.getTelefono());
		} else {
			txtDireccion.setText("");
			txtTelefono.setText("");
		}
	}

}
