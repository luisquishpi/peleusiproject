package ec.peleusi.utils.fx;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class TableViewUtils {
	
	public static void tblListaReleased(KeyEvent event, TextField txtBuscar) {
		if (event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE) {
			txtBuscar.requestFocus();
		}
		try {
			if (Character.isLetter(event.getText().toCharArray()[0])
					|| Character.isDigit(event.getText().toCharArray()[0])) {
				txtBuscar.requestFocus();
				txtBuscar.setText(event.getText());
				txtBuscar.deselect(); 
				txtBuscar.end();
			}
		} catch (Exception e) {

		}
	}
}
