package ec.peleusi.views.fx.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.EmpresaController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Empresa;
import ec.peleusi.utils.fx.AlertsUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class EmpresaFxController extends AnchorPane {

	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtIdentificacion;
	@FXML
	private ComboBox<Ciudad> cmbCiudad;
	@FXML
	private TextField txtDireccion;
	@FXML
	private TextField txtTelefono;
	@FXML
	private TextField txtFax;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtUrl;
	@FXML
	private ImageView imgEmpresa;
	
	@FXML
	private Button btnGuardar;
	@FXML
	private Button btnCancelar;
	@FXML
	private Button btnEliminarImagen;
	@FXML
	private Button btnCargarImagen;
	

	private EmpresaController empresaController=new EmpresaController();
	private Empresa empresa=new Empresa();
	private String error = null;
	ObservableList<Ciudad> ciudadesList;
	private Image image;

	@FXML
	private void initialize() {
		llenarCiudad();
		recuperarEmpresa();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			}
		});
	}
	private void recuperarEmpresa() {
		List<Empresa> listaEmpresa;
		listaEmpresa = empresaController.EmpresaList();
		if (listaEmpresa.size() != 0) {
			empresa = listaEmpresa.get(0);
			txtNombre.setText(empresa.getNombre());
			txtIdentificacion.setText(empresa.getIdentificacion());
			txtDireccion.setText(empresa.getDireccion());
			txtTelefono.setText(empresa.getTelefono());
			txtFax.setText(empresa.getFax());
			txtEmail.setText(empresa.getEmail());
			txtUrl.setText(empresa.getUrl());
			cmbCiudad.getSelectionModel().select(empresa.getCiudad());
			
			if (empresa.getFoto() != null){
				byte[] byteArray = empresa.getFoto();
	            //image.setImage(new Image(new ByteArrayInputStream(byteArray)));
	            imgEmpresa.setImage( new Image(new ByteArrayInputStream(byteArray)));
	            }
;
				//imgEmpresa.setImage(empresa.getFoto());
			//else
				//lblFoto.setIcon(new ImageIcon(EmpresaCrudFrm.class.getResource("/ec/peleusi/utils/images/foto.jpg")));
		
			
			btnGuardar.setText("Actualizar");
		} else {
			btnGuardar.setText("Guardar");
		}

	}
	private void llenarCiudad() {
		CiudadController ciudadController = new CiudadController();
		ciudadesList = FXCollections.observableList(ciudadController.ciudadList());
		cmbCiudad.setItems(ciudadesList);
	}
	private void guardarNuevo() {
		error = empresaController.createEmpresa(empresa);
		if (error == null) {
			AlertsUtil.alertExito("Guardado correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		error = empresaController.updateEmpresa(empresa);
		if (error == null) {
			AlertsUtil.alertExito("Actualizado correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
	}

	@FXML
	private void btnGuardarClick(ActionEvent event) {
		empresa.setNombre(txtNombre.getText());
		if (isCamposLlenos()) {
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
		Stage stage = (Stage) btnCancelar.getScene().getWindow();
		stage.close();
	}

	
	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().trim().isEmpty())
			llenos = false;
		return llenos;
	}
}
