package ec.peleusi.views.fx.controllers;

import java.io.IOException;
import java.util.List;
import ec.peleusi.controllers.SeteoController;
import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.models.entities.Seteo;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.utils.IdentificacionDecimalEnum;
import ec.peleusi.utils.SignoMonedaEnum;
import ec.peleusi.utils.TipoInventarioEnum;
import ec.peleusi.utils.fx.AlertsUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SeteoFxController extends GenericController {
	@FXML
	private TabPane tpnlSeteo;
	@FXML
	private Tab pnlGeneral;
	@FXML
	private ComboBox<TarifaIva> cmbTarifaIva;
	@FXML
	private TextField txtNumeroDecimales;	
	@FXML
	private ComboBox<IdentificacionDecimalEnum> cmbIdentificacionDecimal;	
	@FXML
	private ComboBox<SignoMonedaEnum> cmbSignoMoneda;	
	@FXML
	private Tab pnlFacturacion;
	@FXML
	private TextField txtIdentificacion;
	@FXML
	private Button btnBuscarCliente;
	@FXML
	private TextField txtRazonSocial;
	@FXML
	private CheckBox chkConServicioAdicional;
	@FXML
	private TitledPane tlpServicioAdicional;
	@FXML
	private TextField txtNombreCampoServicioAdicional;
	@FXML
	private TextField txtPorcentajeServicioAdicional;
	@FXML
	private Tab pnlContabilidad;
	@FXML
	private ComboBox<TipoInventarioEnum> cmbTipoInventario;
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;
	
	ObservableList<TarifaIva> tarifaIvasList;

	//Cliente cliente;
	TarifaIva tarifaIva;
	Seteo seteo = new Seteo();
	SeteoController seteoController = new SeteoController();
	private String error = null;
	Cliente cliente = new Cliente();
	
	
	@FXML
	private void initialize() {
		cargarComboTarifaIva();
		cargarComboIdentificacionDecimal();
		cargarComboSignoMoneda();
		cargarComboTipoInventario();	
		txtNumeroDecimales.setText("0");
		txtPorcentajeServicioAdicional.setText("0.00");
		cargarDatosSeteos();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			}
		});
	}			
			
	private void cargarComboTarifaIva() {
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		tarifaIvasList = FXCollections.observableList(tarifaIvaController.tarifaIvaList());
		cmbTarifaIva.setItems(tarifaIvasList);		
	}
	
	private void cargarComboIdentificacionDecimal() {
		cmbIdentificacionDecimal.setItems( FXCollections.observableArrayList(IdentificacionDecimalEnum.values()));		
	}
	
	private void cargarComboSignoMoneda() {
		cmbSignoMoneda.setItems( FXCollections.observableArrayList(SignoMonedaEnum.values()));		
	}
	
	private void cargarComboTipoInventario() {
		cmbTipoInventario.setItems(FXCollections.observableArrayList(TipoInventarioEnum.values()));	
	}	
	
	private void cargarDatosSeteos() {
	seteo = new Seteo();
	List<Seteo> listaSeteo;
	listaSeteo = seteoController.seteoList();
		if (listaSeteo.size() != 0) {
			seteo = listaSeteo.get(0);
			cliente = seteo.getCliente();			
			cmbTarifaIva.getSelectionModel().select(seteo.getTarifaIva());
			txtNumeroDecimales.setText(seteo.getNumeroDecimales().toString());
			cmbIdentificacionDecimal.getSelectionModel().select(seteo.getIdentificacionDecimal());
			cmbSignoMoneda.getSelectionModel().select(seteo.getSignoMoneda());
			txtIdentificacion.setText(seteo.getCliente().getIdentificacion());
			txtRazonSocial.setText(seteo.getCliente().getRazonSocial());		
			if (seteo.getConServicioAdicional() == true) {
				tlpServicioAdicional.setVisible(true);			
				chkConServicioAdicional.setSelected(true);
				txtNombreCampoServicioAdicional.setText(seteo.getNombrePercentajeServicioAdicional());
				txtPorcentajeServicioAdicional.setText(seteo.getPorcentajeServicioAdicional().toString());
			} else {
				chkConServicioAdicional.setSelected(false);
				tlpServicioAdicional.setVisible(false);
			}	
			cmbTipoInventario.getSelectionModel().select(seteo.getTipoInventario());
			btnGuardar.setText("Actualizar");
		} else {
			seteo = null;
			btnGuardar.setText("Guardar");
		}
	}
	private void llenarEntidadAntesDeGuardar() {
		TarifaIva tarifaIva= cmbTarifaIva.getValue();
		seteo.setTarifaIva(tarifaIva);
		seteo.setNumeroDecimales(Integer.parseInt(txtNumeroDecimales.getText()));
		seteo.setIdentificacionDecimal((IdentificacionDecimalEnum) cmbIdentificacionDecimal.getValue());
		seteo.setSignoMoneda((SignoMonedaEnum) cmbSignoMoneda.getValue());
		seteo.setCliente(cliente);
		seteo.setNombrePercentajeServicioAdicional(txtNombreCampoServicioAdicional.getText());
		seteo.setPorcentajeServicioAdicional(Double.parseDouble(txtPorcentajeServicioAdicional.getText()));
		seteo.setTipoInventario((TipoInventarioEnum) cmbTipoInventario.getValue());
		seteo.setConServicioAdicional(chkConServicioAdicional.isSelected());	
		System.out.println("antes guardar " + seteo);
	}	
	
	private void guardarNuevo() {
		llenarEntidadAntesDeGuardar();
		error = seteoController.createSeteos(seteo);
		if (error == null) {			
			cargarDatosSeteos();
			AlertsUtil.alertExito("Guardado correctamente");			
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		llenarEntidadAntesDeGuardar();
		error = seteoController.update(seteo);
		if (error == null) {
			AlertsUtil.alertExito("Actualizado correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
	}	
	private boolean camposLlenos() {
		boolean llenos = true;
		if (cmbTarifaIva.getValue()== null || txtNumeroDecimales.getText().trim().isEmpty()
				|| cmbIdentificacionDecimal.getValue()==null || cmbSignoMoneda.getValue()==null				
				|| cmbTipoInventario.getValue() == null)
			llenos = false;
		return llenos;
	}
	
	@FXML
	private void btnGuardarClick(ActionEvent event) {
		if (camposLlenos()) {
			if (btnGuardar.getText().toLowerCase().equals("actualizar")) {
				actualizar();
			} else {
				guardarNuevo();
			}
		} else {
			AlertsUtil.alertWarning("Datos incompletos, no es posible guardar");
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
	private void btnBuscarClienteClick(ActionEvent event) {
		Parent parent = null;
		Stage stage = new Stage();
		ClienteListModalController control = new ClienteListModalController();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../designs/ClienteListModalFx.fxml"));
		loader.setController(control);
		try{
			parent = (Parent) loader.load();
			stage.setTitle("Lista de Cliente");
			stage.setScene(new Scene(parent));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			cliente = control.getCliente();
			txtIdentificacion.setText(cliente.getIdentificacion());
			txtRazonSocial.setText(cliente.getRazonSocial());
		}	catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

