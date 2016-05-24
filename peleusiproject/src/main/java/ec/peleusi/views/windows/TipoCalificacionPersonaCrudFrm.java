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
import ec.peleusi.controllers.TipoCalificacionPersonaController;
import ec.peleusi.models.entities.TipoCalificacionPersona;

public class TipoCalificacionPersonaCrudFrm extends JDialog{

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private TipoCalificacionPersona tipoCalificacionPersona;

	public TipoCalificacionPersonaCrudFrm() {
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
		if (tipoCalificacionPersona != null && tipoCalificacionPersona.getId() != null) {
			this.setTitle("Actualizando Tipo Calificaciòn Persona");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(tipoCalificacionPersona.getNombre());

		} else {
			this.setTitle("Creando Tipo Calificaciòn Persona");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}
	
	private void llenarEntidadAntesDeGuardar() {
		tipoCalificacionPersona.setNombre(txtNombre.getText());
	}
	
	private void guardarNuevoTipoCalificacionPersona() {
		tipoCalificacionPersona= new TipoCalificacionPersona();
		llenarEntidadAntesDeGuardar();
		TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
		String error = tipoCalificacionPersonaController.createTipoCalificacionPersona(tipoCalificacionPersona);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void actualizarTipoCalificacionPersona() {
		llenarEntidadAntesDeGuardar();
		TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
		String error = tipoCalificacionPersonaController.updateTipoCalificacionPersona(tipoCalificacionPersona);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public TipoCalificacionPersona getTipoCalificacionPersona() {
		return tipoCalificacionPersona;
	}
	
	public void setTipoCalificacionPersona(TipoCalificacionPersona tipoCalificacionPersona) {
		this.tipoCalificacionPersona = new TipoCalificacionPersona();
		this.tipoCalificacionPersona = tipoCalificacionPersona;
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
				tipoCalificacionPersona = new TipoCalificacionPersona();
				llenarCamposConEntidad();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (tipoCalificacionPersona != null && tipoCalificacionPersona.getId() != null) {
					actualizarTipoCalificacionPersona();
				} else {
					guardarNuevoTipoCalificacionPersona();
				}
			}

		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoCalificacionPersona = new TipoCalificacionPersona();
				dispose();
			}
		});
	}
	
	private void crearControles() {		
		setBounds(100, 100, 611, 225);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(
				new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(
				new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(
				new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre*");
		lblNombre.setBounds(31, 39, 65, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(106, 39, 210, 20);
		panelCuerpo.add(txtNombre);
	}
}
