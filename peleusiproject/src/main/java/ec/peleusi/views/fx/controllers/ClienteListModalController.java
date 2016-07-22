package ec.peleusi.views.fx.controllers;

import java.util.List;
import ec.peleusi.controllers.ClienteController;
import ec.peleusi.models.entities.Cliente;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ClienteListModalController {

	@FXML
	private TextField txtBuscar;
	@FXML
	private Button btnAceptar;
	@FXML
	private Button btnCancelar;
	@FXML
	private TableView<Cliente> tblLista;
	@FXML
	private Pagination pagination;

	ObservableList<Cliente> clientesList;
	private ClienteController clienteController = new ClienteController();
	private Cliente cliente;
	final static int rowsPerPage = 100;

	@FXML
	private void initialize() {
		crearTabla();
		paginar();

	}

	private void paginar() {
		int count = clientesList.size() / rowsPerPage;
		if (count < ((double) ( clientesList.size()) / rowsPerPage))
			count++;
		if (count == 0 && clientesList.size() > 0)
			count++;
		if (clientesList.size() > 0) {
			pagination.setVisible(true);
			pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
			pagination.setPageCount(count);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				public Node call(Integer pageIndex) {
					int fromIndex = pageIndex * rowsPerPage;
					int toIndex = Math.min(fromIndex + rowsPerPage, clientesList.size());
					tblLista.setItems(FXCollections.observableArrayList(clientesList.subList(fromIndex, toIndex)));
					return tblLista;
				}
			});

		} else {
			pagination.setVisible(false);
		}
	}

	@SuppressWarnings("unchecked")
	private void crearTabla() {
		tblLista = new TableView<Cliente>();
		TableColumn<Cliente, String> identificacionCol = new TableColumn<Cliente, String>("Identificación");
		identificacionCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("identificacion"));
		identificacionCol.setPrefWidth(105);
		TableColumn<Cliente, String> razonSocialCol = new TableColumn<Cliente, String>("RazonSocial");
		razonSocialCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("razonSocial"));
		razonSocialCol.setPrefWidth(150);		
		TableColumn<Cliente, String> direccionCol = new TableColumn<Cliente, String>("Direccion");
		direccionCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
		direccionCol.setPrefWidth(100);		
		TableColumn<Cliente, String> telefonoCol = new TableColumn<Cliente, String>("Teléfono");
		telefonoCol.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
		telefonoCol.setPrefWidth(80);

		tblLista.getColumns().addAll(identificacionCol, razonSocialCol, direccionCol, telefonoCol);
		clientesList = FXCollections.observableList(clienteController.ClienteList());
		tblLista.setItems(clientesList);
		final ObservableList<Cliente> clienteListSelected = tblLista.getSelectionModel().getSelectedItems();
		clienteListSelected.addListener(escuchaCambiosEnTabla);
		tblLista.setOnKeyReleased(event -> {
			TableViewUtils.tblListaReleased(event, txtBuscar);
		});
	}

	private final ListChangeListener<Cliente> escuchaCambiosEnTabla = new ListChangeListener<Cliente>() {
		@Override
		public void onChanged(ListChangeListener.Change<? extends Cliente> c) {
			if (tblLista != null && tblLista.getSelectionModel().getSelectedItems().size() >= 1) {
				cliente = tblLista.getSelectionModel().getSelectedItems().get(0);
			}
		}
	};

	@FXML
	private void btnBuscarClick(ActionEvent event) {
		List<Cliente> clienteList = clienteController.getClienteList(txtBuscar.getText());
		if (clienteList != null) {
			clientesList = FXCollections.observableList(clienteList);
			tblLista.setItems(clientesList);
		} else {
			clientesList.clear();
		}
		paginar();
		tblLista.requestFocus();
	}

	@FXML
	private void btnAceptarClick(ActionEvent event) {
		System.err.println("pr: " + cliente);
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void btnCancelarClick(ActionEvent event) {
		cliente = null;
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void txtBuscarReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			btnBuscarClick(null);
		}
	}

	@FXML
	private void paginationReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER) {
			btnAceptarClick(null);
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

}
