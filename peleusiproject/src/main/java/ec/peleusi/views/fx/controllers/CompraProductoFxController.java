package ec.peleusi.views.fx.controllers;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import ec.peleusi.controllers.CompraController;
import ec.peleusi.controllers.CompraDetalleController;
import ec.peleusi.controllers.DireccionProveedorController;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.ProveedorController;
import ec.peleusi.controllers.SeteoController;
import ec.peleusi.controllers.SucursalController;
import ec.peleusi.controllers.UsuarioController;
import ec.peleusi.models.entities.Compra;
import ec.peleusi.models.entities.CompraDetalle;
import ec.peleusi.models.entities.DireccionProveedor;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.Proveedor;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.Sucursal;
import ec.peleusi.models.entities.Usuario;
import ec.peleusi.models.entities.general.CompraDetalleAux;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DoubleStringConverter;

public class CompraProductoFxController extends AnchorPane {

	@FXML
	private TableView<CompraDetalleAux> tblDetalleCompra;
	@FXML
	private TableColumn<CompraDetalleAux, Integer> idCol;
	@FXML
	private TableColumn<CompraDetalleAux, Producto> idProductoCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> stockCol;
	@FXML
	private TableColumn<CompraDetalleAux, String> codigoCol;
	@FXML
	private TableColumn<CompraDetalleAux, String> nombreCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> cantidadCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> costoCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> porcentajeDescuentoCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> valorDescuentoCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> precioNetoCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> subtotalCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> porcentaIvaCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> valorIvaCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> porcentajeIceCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> valorIceCol;
	@FXML
	private TableColumn<CompraDetalleAux, Double> totalCol;
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
	public Button btnBuscarProveedor;

	ObservableList<CompraDetalleAux> oblCompraDetalleList = FXCollections.observableArrayList();
	Proveedor proveedor;
	Sucursal sucursal;
	Usuario usuario;

	@FXML
	private Button btnNuevo;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnCancelar;

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

		crerColumnasCompraDetalle();
		editarColumnas();
		limpiarControles();
		cargarUsuario();
		cargarSucursal();

	}

	private void crerColumnasCompraDetalle() {

		idCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Integer>("id"));
		idProductoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Producto>("idProducto"));
		codigoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, String>("codigo"));
		nombreCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, String>("nombre"));
		cantidadCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("cantidad"));
		costoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("costo"));
		porcentajeDescuentoCol
				.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("porcentajeDescuento"));
		valorDescuentoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("valorDescuento"));
		precioNetoCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("precioNeto"));
		subtotalCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("subtotal"));
		porcentaIvaCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("porcentaIva"));
		valorIvaCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("valorIva"));
		stockCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("stock"));
		porcentajeIceCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("porcentajeIce"));
		valorIceCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("valorIce"));
		totalCol.setCellValueFactory(new PropertyValueFactory<CompraDetalleAux, Double>("total"));
		cantidadCol.setEditable(true);
		porcentajeDescuentoCol.setEditable(true);
		costoCol.setEditable(true);
		tblDetalleCompra.setItems(oblCompraDetalleList);

	}

	private void editarColumnas() {
		cantidadCol.setCellFactory(
				TextFieldTableCell.<CompraDetalleAux, Double> forTableColumn(new DoubleStringConverter()));
		porcentajeDescuentoCol.setCellFactory(
				TextFieldTableCell.<CompraDetalleAux, Double> forTableColumn(new DoubleStringConverter()));
		costoCol.setCellFactory(
				TextFieldTableCell.<CompraDetalleAux, Double> forTableColumn(new DoubleStringConverter()));

		cantidadCol.setOnEditCommit((CellEditEvent<CompraDetalleAux, Double> t) -> {
			((CompraDetalleAux) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCantidad(t.getNewValue());
			actualizarValores(oblCompraDetalleList, tblDetalleCompra, t.getTablePosition().getRow());

		});
		porcentajeDescuentoCol.setOnEditCommit((CellEditEvent<CompraDetalleAux, Double> t) -> {
			((CompraDetalleAux) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setPorcentajeDescuento(t.getNewValue());
			actualizarValores(oblCompraDetalleList, tblDetalleCompra, t.getTablePosition().getRow());
		});
		costoCol.setOnEditCommit((CellEditEvent<CompraDetalleAux, Double> t) -> {
			((CompraDetalleAux) t.getTableView().getItems().get(t.getTablePosition().getRow()))
					.setCosto(t.getNewValue());

			actualizarValores(oblCompraDetalleList, tblDetalleCompra, t.getTablePosition().getRow());
		});

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
		oblCompraDetalleList.add(new CompraDetalleAux(0, producto, producto.getCodigo(), producto.getNombre(),
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
		txtRuc.setText("");
		txtContribuyente.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtEstablecimiento.setText("");
		txtPuntoEmision.setText("");
		txtSecuencial.setText("");
		txtDiasCredito.setText("0");
		txtAutorizacion.setText("0");
		dtpFechaAutorizacion.setValue(LocalDate.now());
		dtpFechaEmision.setValue(LocalDate.now());
		dtpFechaRegistro.setValue(LocalDate.now());
		dtpFechaVencimiento.setValue(LocalDate.now());
		txtRuc.requestFocus();
	}

	private void calcularTotales() {
		subtotalIvaDiferente0 = 0.0;
		subtotalIva0 = 0.0;
		totalIva = 0.0;
		totalIce = 0.0;
		totalDescuento = 0.0;
		total = 0.0;
		System.out.println("Numero de datos....>" + oblCompraDetalleList.size());

		for (CompraDetalleAux compraDetalle : oblCompraDetalleList) {
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

	public void actualizarValores(ObservableList<CompraDetalleAux> modelo, TableView<CompraDetalleAux> table,
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
	private void bntNuevoClick() {

		System.out.println("Hola Lucho, como fastidia ud.....");
		limpiarControles();

	}

	@FXML
	private void bntGuardarClick() {
		try {
			Double baseImponibleExentoIva = 0.0;
			Double baseImponibleNoObjetoIva = 0.0;
			Boolean estado = false;
			Compra compra = new Compra();
			CompraController compraController = new CompraController();
			compra.setBaseImponibleExentoIva(baseImponibleExentoIva);
			compra.setBaseImponibleIva0(Double.parseDouble(txtBaseImponible0.getText()));
			compra.setBaseImponibleIvaDiferente0(Double.parseDouble(txtBaseImponibleDiferente0.getText()));
			compra.setBaseImponibleNoObjetoIva(baseImponibleNoObjetoIva);
			compra.setDiasCredito(Double.parseDouble(txtDiasCredito.getText()));
			compra.setEstablecimiento(txtEstablecimiento.getText());
			compra.setEstado(estado);
			compra.setFechaAutorizacion(Fecha(dtpFechaAutorizacion.getValue()));
			compra.setFechaEmision(Fecha(dtpFechaEmision.getValue()));
			compra.setFechaRegistro(Fecha(dtpFechaRegistro.getValue()));
			compra.setFechaVencimiento(Fecha(dtpFechaVencimiento.getValue()));
			compra.setMontoIce(Double.parseDouble(txtMontoIce.getText()));
			compra.setMontoIva(Double.parseDouble(txtMontoIva.getText()));
			compra.setNumeroAutorizacion(txtAutorizacion.getText());
			compra.setPuntoEmision(txtPuntoEmision.getText());
			compra.setSecuencial(txtSecuencial.getText());
			compra.setValorDescuento(Double.parseDouble(txtDescuento.getText()));
			compra.setProveedor(proveedor);
			compra.setSucursal(sucursal);
			compra.setUsuario(usuario);
			compraController.createCompra(compra);
			grabarDetalle(compra);

		} catch (Exception err) {
		}

		System.out.println("Hola Lucho, como fastidia ud.....");

	}

	private void grabarDetalle(Compra compraAux) {

		for (CompraDetalleAux compraDetalleAux : oblCompraDetalleList) {

			CompraDetalle compraDetalle = new CompraDetalle();

			CompraDetalleController compraDetalleController = new CompraDetalleController();
			compraDetalle.setStock(compraDetalleAux.getStock());
			compraDetalle.setCodigoProducto(compraDetalleAux.getCodigo());
			compraDetalle.setCostoProducto(compraDetalle.getCostoProducto());
			compraDetalle.setNombreProducto(compraDetalleAux.getNombre());
			compraDetalle.setPorcentajeDescuentoProducto(compraDetalleAux.getPorcentajeDescuento());
			compraDetalle.setPorcentajeIceProducto(compraDetalleAux.getPorcentajeIce());
			compraDetalle.setPorcentajeIvaProducto(compraDetalleAux.getPorcentaIva());
			compraDetalle.setSubtotal(compraDetalleAux.getSubtotal());
			compraDetalle.setValorDescuentoProducto(compraDetalleAux.getValorDescuento());
			compraDetalle.setValorIceProducto(compraDetalleAux.getValorIce());
			compraDetalle.setValorIvaProducto(compraDetalleAux.getValorIva());
			compraDetalle.setCompra(compraAux);
			compraDetalle.setProducto(compraDetalleAux.getIdProducto());
			compraDetalleController.createCompra(compraDetalle);

		}

	}

	@FXML
	private void bntEliminarClick() {

		System.out.println("Hola Lucho, como fastidia ud.....");

	}

	@FXML
	private void bntCancelarClick() {

		System.out.println("Hola Lucho, como fastidia ud.....");

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

		if (event.getCode() == KeyCode.ENTER) {
			System.out.println(txtRuc.getText());
			buscarProveedoridentificacion(txtRuc.getText());
		}
	}

	@FXML
	public void dtpFechaEmisionChanged(KeyEvent event) {
		dtpFechaVencimiento.setValue(dtpFechaEmision.getValue().plusDays(Long.parseLong(txtDiasCredito.getText())));

	}

	@FXML
	public void txtDiasCreditoChanged(KeyEvent event) {
		dtpFechaVencimiento.setValue(dtpFechaEmision.getValue().plusDays(Long.parseLong(txtDiasCredito.getText())));

	}

	@FXML
	public void txtDiasCreditoReleased(KeyEvent event) {
		dtpFechaVencimiento.setValue(dtpFechaEmision.getValue().plusDays(Long.parseLong(txtDiasCredito.getText())));

	}

	@FXML
	public void dtpFechaEmisionReleased(KeyEvent event) {
		dtpFechaVencimiento.setValue(dtpFechaEmision.getValue().plusDays(Long.parseLong(txtDiasCredito.getText())));

	}

	@FXML
	private void tblDetalleCompraOnKeyPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.DELETE) {
			System.out.println("Borrar" + tblDetalleCompra.getSelectionModel().getSelectedIndex());
			oblCompraDetalleList.remove(tblDetalleCompra.getSelectionModel().getSelectedIndex());
			tblDetalleCompra.refresh();
			calcularTotales();

		}
	}

	private void buscarProveedoridentificacion(String identificacion) {

		ProveedorController proveedoersonaController = new ProveedorController();
		proveedor = new Proveedor();
		proveedor = proveedoersonaController.getProveedorIdentificacion(identificacion);
		if (proveedor != null) {
			System.out.println("Categoría seleccionado: " + proveedor);
			txtRuc.setText(proveedor.getIdentificacion());
			txtContribuyente.setText(proveedor.getRazonSocial());
			cargarDireccionProveedorPorDefecto(proveedor);
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
	public static Date Fecha(LocalDate ld) {

		Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		return date;
	}

	public void cargarUsuario() {

		UsuarioController usuarioController = new UsuarioController();
		List<Usuario> usuariosList = usuarioController.usuarioList();
		usuario = usuariosList.get(0);

	}

	public void cargarSucursal() {

		SucursalController sucursalController = new SucursalController();
		List<Sucursal> sucursalList = sucursalController.SucursalList();
		sucursal = sucursalList.get(0);

	}
}
