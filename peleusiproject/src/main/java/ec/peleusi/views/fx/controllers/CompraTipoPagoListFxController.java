package ec.peleusi.views.fx.controllers;

import java.io.IOException;
import java.util.Optional;
import ec.peleusi.controllers.CompraController;
import ec.peleusi.controllers.CompraTipoPagoController;
import ec.peleusi.models.entities.Compra;
import ec.peleusi.models.entities.CompraTipoPago;
import ec.peleusi.models.entities.TipoPago;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.TableViewUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CompraTipoPagoListFxController extends GenericController {

	@FXML
	public TableView<CompraTipoPago> tblTipoPago;

	@FXML
	private TableColumn<CompraTipoPago, Integer> IdColPago;
	@FXML
	private TableColumn<CompraTipoPago, String> nombreColPago;
	@FXML
	private TableColumn<CompraTipoPago, Double> valorColPago;
	@FXML
	private TableColumn<CompraTipoPago, String> detalleColPago;

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
	private Pagination pagination;
	@FXML
	private TableView<Compra> tblLista;

	ObservableList<Compra> compraList;

	ObservableList<CompraTipoPago> compraTipoPagoList;
	final static int rowsPerPage = 100;

	private Compra compra;
	private CompraTipoPago compraTipoPago;
	private Integer posicionObjetoEnTabla = 0;
	private Integer posicionObjetoTipoPagoEnTabla = 0;
	private String error = null;
	TipoPago tipoPago = new TipoPago();

	@FXML
	private void initialize() {

		
		crearTablaListaProductos();
		paginar();
	}

	@FXML
	private void btnNuevoClick(ActionEvent event) {
		limpiarCampos();

	}

	@FXML
	private void btnGuardarClick(ActionEvent event) {

		if (isCamposLlenos()) {
			CompraTipoPagoController compraTipoPagoControlles = new CompraTipoPagoController();
			if (compraTipoPago == null) {
				error = compraTipoPagoControlles.createCompraTipoPago(llenarCampos(false));
			} else {
				error = compraTipoPagoControlles.updateCompraTipoPago(llenarCampos(true));
			}
			if (error == null) {
				AlertsUtil.alertExito("Guardado correctamente");
				crearTablaListaPagos();

			} else {
				AlertsUtil.alertError(error);
			}

		} else {
			AlertsUtil.alertWarning("Datos incompletos, no es posible guardar");
		}
	}

	@FXML
	private void btnEliminarClick(ActionEvent event) {
		
		
		if (tblTipoPago.getSelectionModel().getSelectedIndex() >= 0) {

			compraTipoPago = new CompraTipoPago();
			compraTipoPago = (CompraTipoPago) compraTipoPagoList.get(posicionObjetoTipoPagoEnTabla);
			Optional<ButtonType> result = AlertsUtil
					.alertConfirmation("Está seguro que desea eliminar: \n" + compraTipoPago.getValor());
			if (result.get() == ButtonType.OK) {
				CompraTipoPagoController compraTipoPagoController = new CompraTipoPagoController();
				 error = compraTipoPagoController.deleteCompraTipoPago(compraTipoPago);
				if (error == null) {
					tblTipoPago.getItems().remove(tblTipoPago.getSelectionModel().getSelectedIndex());
				} else {
					AlertsUtil.alertError(error);
				}
			} else {

			}
		}

	}
	@FXML
	private void btnCancelarClick(ActionEvent event) {
		
		Button btnCloseTab = (Button) event.getSource();
		Scene btnScene = btnCloseTab.getScene();
		TabPane thisTabPane = (TabPane) btnScene.lookup("#tpPrincipal");
		thisTabPane.getTabs().remove(tabIndex);
	}

	@FXML
	private void btnBuscarClick(ActionEvent event) {
	}

	@FXML
	private void btnBuscarTipoPagoClick(ActionEvent event) {

		tipoPago = new TipoPago();
		Parent parent = null;
		Stage stage = new Stage();
		TipoPagoListModalController control = new TipoPagoListModalController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/TipoPagoListModalFx.fxml"));
		loader.setController(control);
		try {
			parent = (Parent) loader.load();
			stage.setTitle("Lista de Caja");
			stage.setScene(new Scene(parent));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			tipoPago = control.getTipoPago();
			txtNombre.setText(tipoPago.getNombre());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void crearTablaListaProductos() {

		tblLista = new TableView<Compra>();
		TableColumn<Compra, String> identificacionCol = new TableColumn<Compra, String>("Identificación");
		identificacionCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getProveedor().getIdentificacion()));

		TableColumn<Compra, String> razonSocialCol = new TableColumn<Compra, String>("Razon Socia");
		razonSocialCol.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getProveedor().getRazonSocial()));

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
		CompraController compraController = new CompraController();
		compraList = FXCollections.observableList(compraController.compraList());
		tblLista.setItems(compraList);
		final ObservableList<Compra> comprasListSelected = tblLista.getSelectionModel().getSelectedItems();
		comprasListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});

	}

	private void crearTablaListaPagos() {

		CompraTipoPagoController compraTipoPagoController = new CompraTipoPagoController();
		compraTipoPagoList = FXCollections.observableList(compraTipoPagoController.listCompraTipoPago(compra));
		nombreColPago.setCellValueFactory(
				cellData -> new SimpleStringProperty(cellData.getValue().getTipoPago().getNombre()));
		valorColPago.setCellValueFactory(new PropertyValueFactory<CompraTipoPago, Double>("valor"));
		detalleColPago.setCellValueFactory(new PropertyValueFactory<CompraTipoPago, String>("detalle"));

		tblTipoPago.setItems(compraTipoPagoList);
		final ObservableList<CompraTipoPago> comprasTipoPagoListSelected = tblTipoPago.getSelectionModel()
				.getSelectedItems();
		comprasTipoPagoListSelected.addListener(escuchaCambiosEnTablaPagos);
		tblTipoPago.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});

	}

	private final ListChangeListener<Compra> escuchaCambiosEnTabla = new ListChangeListener<Compra>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Compra> c) {
			cargarObjetoSeleccionadaEnFormulario();
		}
	};
	private final ListChangeListener<CompraTipoPago> escuchaCambiosEnTablaPagos = new ListChangeListener<CompraTipoPago>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends CompraTipoPago> c) {
			cargarTipoPagosFormulario();
		}
	};

	public Object getObjetoSeleccionadoDeTabla() {
		if (tblLista != null) {
			if (tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				return tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
		return null;
	}

	public Object getObjetoTipoPagoSeleccionadoDeTabla() {
		if (tblTipoPago != null) {
			if (tblTipoPago.getSelectionModel().getSelectedItems().size() >= 1) {
				return tblTipoPago.getSelectionModel().getSelectedItems().get(0);
			}
		}
		return null;
	}

	private void cargarTipoPagosFormulario() {

		compraTipoPago = (CompraTipoPago) getObjetoTipoPagoSeleccionadoDeTabla();
		if (compraTipoPago != null) {

			posicionObjetoTipoPagoEnTabla = compraTipoPagoList.indexOf(compraTipoPago);
			txtNombre.setText(compraTipoPago.getTipoPago().getNombre());
			txtValor.setText(compraTipoPago.getValor().toString());
			txtDetalle.setText(compraTipoPago.getDetalle());
			tipoPago = compraTipoPago.getTipoPago();
			btnGuardar.setText("Actualizar");

		}
	}

	private void cargarObjetoSeleccionadaEnFormulario() {

		compra = (Compra) getObjetoSeleccionadoDeTabla();
		if (compra != null) {

			posicionObjetoEnTabla = compraList.indexOf(compra);
			txtBaseImponible0.setText(compra.getBaseImponibleIva0().toString());
			txtBaseImponibleDiferente0.setText(compra.getBaseImponibleIvaDiferente0().toString());
			txtDescuento.setText(compra.getValorDescuento().toString());
			txtMontoIva.setText(compra.getMontoIva().toString());
			txtMontoIce.setText(compra.getMontoIce().toString());
			Double total = 0.0;
			total = compra.getBaseImponibleIva0() + compra.getBaseImponibleIvaDiferente0() + compra.getMontoIva()
					+ compra.getMontoIce() - compra.getValorDescuento();
			txtTotal.setText(total.toString());
			crearTablaListaPagos();
			limpiarCampos();
		}
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

	private CompraTipoPago llenarCampos(Boolean actualizar) {

		if (actualizar == false) {
			compraTipoPago = new CompraTipoPago();
		} else {
			compraTipoPago = tblTipoPago.getSelectionModel().getSelectedItem();
		}
		compraTipoPago.setCompra(compra);
		compraTipoPago.setTipoPago(tipoPago);
		compraTipoPago.setValor(Double.parseDouble(txtValor.getText()));
		compraTipoPago.setDetalle(txtDetalle.getText());
		return compraTipoPago;
	}

	private void limpiarCampos() {
		txtNombre.setText("");
		txtValor.setText("");
		txtDetalle.setText("");
		btnGuardar.setText("Guardar");
		compraTipoPago = null;
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtValor.getText().isEmpty())
			llenos = false;
		return llenos;
	}
	
	private void pagoTotal(){
		
		
		
		
	}

}
