package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.peleusi.controllers.TipoGastoDeducibleController;
import ec.peleusi.models.entities.TipoGastoDeducible;

public class TipoGastoDeducibleCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private TipoGastoDeducible tipoGastoDeducible;

	public TipoGastoDeducibleCrudFrm() {
		setTitle("Tipo Gasto Deducible");
		crearControles();
		crearEventos();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				llenarCamposConEntidad();
				txtNombre.requestFocus();
				}			
		});
	}

	private void llenarCamposConEntidad() {
		if (tipoGastoDeducible != null && tipoGastoDeducible.getId() != null) {
			this.setTitle("Actualizando Tipo Gasto Deducible");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(tipoGastoDeducible.getNombre());
		} else {
			this.setTitle("Creando Tipo Gasto Deducible");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}
	
	private void llenarEntidadAntesDeGuardar() {
		tipoGastoDeducible.setNombre(txtNombre.getText());
	}
	
	private void guardarNuevoTipoGastoDeducible() {
		tipoGastoDeducible= new TipoGastoDeducible();
		llenarEntidadAntesDeGuardar();
		TipoGastoDeducibleController tipoGastoDeducibleController = new TipoGastoDeducibleController();
		String error = tipoGastoDeducibleController.createTipoGastoDeducible(tipoGastoDeducible);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actualizarTipoGastoDeducible() {
		llenarEntidadAntesDeGuardar();
		TipoGastoDeducibleController tipoGastoDeducibleController = new TipoGastoDeducibleController();
		String error = tipoGastoDeducibleController.updateTipoGastoDeducible(tipoGastoDeducible);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public TipoGastoDeducible getTipoGastoDeducible() {
		return tipoGastoDeducible;
	}
	
	public void setTipoGastoDeducible(TipoGastoDeducible tipoGastoDeducible) {
		this.tipoGastoDeducible = new TipoGastoDeducible();
		this.tipoGastoDeducible = tipoGastoDeducible;
	}
	
	private void limpiarCampos() {
		txtNombre.setText("");
		txtNombre.requestFocus();
	}
	
	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty())
			llenos = false;
		return llenos;
	}
	
	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoGastoDeducible = new TipoGastoDeducible();
				llenarCamposConEntidad();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (tipoGastoDeducible != null && tipoGastoDeducible.getId() != null) {
					actualizarTipoGastoDeducible();
				} else {
					guardarNuevoTipoGastoDeducible();
				}

			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoGastoDeducible = new TipoGastoDeducible();
				dispose();
			}
		});
	}	
	
	private void crearControles() {
		setBounds(100, 100, 503, 197);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(
				new ImageIcon(TipoGastoDeducibleCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(20, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(
				new ImageIcon(TipoGastoDeducibleCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(180, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoGastoDeducibleCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(340, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre *");
		lblNombre.setBounds(31, 39, 65, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(106, 39, 214, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(10);
	}
}
