package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class CiudadCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private Ciudad ciudad;

	public CiudadCrudFrm() {
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
		if (ciudad != null && ciudad.getId() != null) {
			this.setTitle("Actualizando Ciudad");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(ciudad.getNombre());

		} else {
			this.setTitle("Creando Ciudad");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		ciudad.setNombre(txtNombre.getText());
	}

	private void guardarNuevoCiudad() {
		ciudad = new Ciudad();
		llenarEntidadAntesDeGuardar();
		CiudadController ciudadController = new CiudadController();
		String error = ciudadController.createCiudad(ciudad);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void actualizarCiudad() {
		llenarEntidadAntesDeGuardar();
		CiudadController ciudadController = new CiudadController();
		String error = ciudadController.updateCiudad(ciudad);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	public void setCiudad(Ciudad ciudad) {
		this.ciudad = new Ciudad();
		this.ciudad = ciudad;
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

	private void crearControles() {
		setBounds(100, 100, 611, 162);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(CiudadCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(CiudadCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(CiudadCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(CiudadCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre de la ciudad");
		lblNombre.setBounds(10, 11, 117, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(137, 8, 210, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(10);
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ciudad = new Ciudad();
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
				if (ciudad != null && ciudad.getId() != null) {
					actualizarCiudad();
				} else {
					guardarNuevoCiudad();

				}
			}

		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ciudad = new Ciudad();
				dispose();
			}
		});
	}

}
