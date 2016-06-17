package ec.peleusi.utils.fx;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertsUtil {
	public static void alertExito(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Éxito");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public static void alertError(String mensaje) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public static void alertInformation(String mensaje) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();
	}

	public static Optional<ButtonType> alertConfirmation(String mensaje) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmación");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		return alert.showAndWait();
	}

	public static void alertWarning(String mensaje) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Advertencia");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		alert.showAndWait();

	}

}
