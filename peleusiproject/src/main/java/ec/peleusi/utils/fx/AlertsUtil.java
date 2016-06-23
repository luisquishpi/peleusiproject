package ec.peleusi.utils.fx;

import java.util.Optional;

import ec.peleusi.notification.Notifications;
import ec.peleusi.notification.TrayNotification;
import ec.peleusi.notification.animations.Animations;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;

public class AlertsUtil {
	public static void alertExito(String mensaje) {
		/*
		 * Alert alert = new Alert(AlertType.INFORMATION);
		 * alert.setTitle("Éxito"); alert.setHeaderText(null);
		 * alert.setContentText(mensaje); alert.showAndWait();
		 */
		TrayNotification tr = new TrayNotification("Éxito", mensaje, Notifications.SUCCESS);
		tr.setAnimation(Animations.FADE);
		tr.showAndDismiss(new Duration(900));
	}

	public static void alertError(String mensaje) {
		/*
		 * Alert alert = new Alert(AlertType.ERROR); alert.setTitle("Error");
		 * alert.setHeaderText(null); alert.setContentText(mensaje);
		 * alert.showAndWait();
		 */
		TrayNotification tr = new TrayNotification("Error", mensaje, Notifications.ERROR);
		tr.setAnimation(Animations.FADE);
		tr.showAndDismiss(new Duration(900));
	}

	public static void alertInformation(String mensaje) {
		/*
		 * Alert alert = new Alert(AlertType.INFORMATION);
		 * alert.setTitle("Información"); alert.setHeaderText(null);
		 * alert.setContentText(mensaje); alert.showAndWait();
		 */
		TrayNotification tr = new TrayNotification("Información", mensaje, Notifications.INFORMATION);
		tr.setAnimation(Animations.FADE);
		tr.showAndDismiss(new Duration(900));
	}

	public static Optional<ButtonType> alertConfirmation(String mensaje) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmación");
		alert.setHeaderText(null);
		alert.setContentText(mensaje);
		return alert.showAndWait();
	}

	public static void alertWarning(String mensaje) {
		/*
		 * Alert alert = new Alert(AlertType.WARNING);
		 * alert.setTitle("Advertencia"); alert.setHeaderText(null);
		 * alert.setContentText(mensaje); alert.showAndWait();
		 */
		TrayNotification tr = new TrayNotification("Advertencia", mensaje, Notifications.WARNING);
		tr.setAnimation(Animations.FADE);
		tr.showAndDismiss(new Duration(900));

	}

	public static void alertNotice(String mensaje) {
		TrayNotification tr = new TrayNotification("Notificación", mensaje, Notifications.NOTICE);
		tr.setAnimation(Animations.FADE);
		tr.showAndDismiss(new Duration(900));

	}

}
