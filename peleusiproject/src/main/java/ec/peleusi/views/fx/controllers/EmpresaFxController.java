package ec.peleusi.views.fx.controllers;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.EmpresaController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Empresa;
import ec.peleusi.utils.fx.AlertsUtil;
import ec.peleusi.utils.fx.ImageUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class EmpresaFxController extends GenericController {

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
	@FXML
	private AnchorPane anchorPanePrincipal;

	private EmpresaController empresaController = new EmpresaController();
	private Empresa empresa = new Empresa();
	private String error = null;
	ObservableList<Ciudad> ciudadesList;
	private Image imgOriginal;

	@FXML
	private void initialize() {
		anchorPanePrincipal.getStyleClass().add("anchorPanePrincipal");
		llenarComboCiudad();
		recuperarEmpresa();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			}
		});
	}

	private void recuperarEmpresa() {
		Image image = new Image(getClass().getResourceAsStream("../images/foto.jpg"));
		imgEmpresa.setImage(image);
		imgOriginal = image;
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
			imgEmpresa.setEffect(new DropShadow(10, Color.BLACK));
			if (empresa.getFoto() != null) {
				byte[] byteArray = empresa.getFoto();
				imgEmpresa.setImage(new Image(new ByteArrayInputStream(byteArray)));
				imgOriginal = new Image(new ByteArrayInputStream(byteArray));
			}
			btnGuardar.setText("Actualizar");
		} else {
			btnGuardar.setText("Guardar");
		}

	}

	private void llenarComboCiudad() {
		CiudadController ciudadController = new CiudadController();
		ciudadesList = FXCollections.observableList(ciudadController.ciudadList());
		cmbCiudad.setItems(ciudadesList);
	}

	private void llenarEntidadAntesDeGuardar() {
		Ciudad ciudad = cmbCiudad.getValue();
		empresa.setNombre(txtNombre.getText());
		empresa.setIdentificacion(txtIdentificacion.getText());
		empresa.setDireccion(txtDireccion.getText());
		empresa.setTelefono(txtTelefono.getText());
		empresa.setFax(txtFax.getText());
		empresa.setEmail(txtEmail.getText());
		empresa.setUrl(txtUrl.getText());
		empresa.setCiudad(ciudad);
		empresa.setFoto(ImageUtils.getByteFoto(imgEmpresa.getImage()));
		imgEmpresa.setImage(new Image(new ByteArrayInputStream(empresa.getFoto())));
	}

	private void guardarNuevo() {
		llenarEntidadAntesDeGuardar();
		error = empresaController.createEmpresa(empresa);
		if (error == null) {
			recuperarEmpresa();
			AlertsUtil.alertExito("Guardado correctamente");
		} else {
			AlertsUtil.alertError(error);
		}
	}

	private void actualizar() {
		llenarEntidadAntesDeGuardar();
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
		// Stage stage = (Stage) btnCancelar.getScene().getWindow();
		// stage.close();
		Button btnCloseTab = (Button) event.getSource();
		Scene btnScene = btnCloseTab.getScene();
		TabPane thisTabPane = (TabPane) btnScene.lookup("#tpPrincipal");
		thisTabPane.getTabs().remove(tabIndex);
	}

	@FXML
	private void btnCargarImagenClick(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.gif"));
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			try {
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				Image image = SwingFXUtils.toFXImage(bufferedImage, null);
				imgEmpresa.setImage(image);
			} catch (Exception ex) {
				AlertsUtil.alertError("Archivo no es imagen o imagen es muy grande");
			}
		}
	}

	@FXML
	private void btnEliminarImagenClick(ActionEvent event) {
		Image image = new Image(getClass().getResourceAsStream("../images/foto.jpg"));
		imgEmpresa.setImage(image);
	}

	@FXML
	private void btnUndoImagenClick(ActionEvent event) {
		imgEmpresa.setImage(imgOriginal);
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtIdentificacion.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty()
				|| txtDireccion.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty()
				|| cmbCiudad.getValue() == null)
			llenos = false;
		return llenos;
	}
}
