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
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.UnidadMedida;

public class UnidadMedidaCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblUnidadDeMedida;
	private JTextField txtNombre;
	private JTextField txtAbreviatura;
	private UnidadMedida unidadMedida;

	public UnidadMedidaCrudFrm() {
		setTitle("Unidad de Medida");
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
		if (unidadMedida != null && unidadMedida.getId() != null) {
			this.setTitle("Actualizando Unidad de Medida");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(unidadMedida.getNombre());
			txtAbreviatura.setText(unidadMedida.getAbreviatura());
		} else {
			this.setTitle("Creando Unidad de Medida");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		unidadMedida.setNombre(txtNombre.getText());
		unidadMedida.setAbreviatura(txtAbreviatura.getText());
	}

	private void guardarNuevoUnidadMedida() {
		unidadMedida = new UnidadMedida();
		llenarEntidadAntesDeGuardar();
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		String error = unidadMedidaController.createUnidadMedida(unidadMedida);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarUnidadMedida() {
		llenarEntidadAntesDeGuardar();
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		String error = unidadMedidaController.updateUnidadMedida(unidadMedida);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = new UnidadMedida();
		this.unidadMedida = unidadMedida;
	}

	private void limpiarCampos() {
		txtNombre.setText("");
		txtAbreviatura.setText("");
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtAbreviatura.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unidadMedida = new UnidadMedida();
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
				if (unidadMedida != null && unidadMedida.getId() != null) {
					actualizarUnidadMedida();
				} else {
					guardarNuevoUnidadMedida();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				unidadMedida = new UnidadMedida();
				dispose();
			}
		});
	}

	private void crearControles() {
		setBounds(100, 100, 500, 211);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(UnidadMedidaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(20, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(UnidadMedidaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(180, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(UnidadMedidaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(340, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		lblUnidadDeMedida = new JLabel("Unidad de Medida");
		lblUnidadDeMedida.setBounds(43, 24, 101, 14);
		panelCuerpo.add(lblUnidadDeMedida);

		txtNombre = new JTextField(10);

		txtNombre.setBounds(155, 21, 185, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(10);

		JLabel lblAbreviatura = new JLabel("Abreviatura ");
		lblAbreviatura.setBounds(43, 66, 85, 14);
		panelCuerpo.add(lblAbreviatura);

		txtAbreviatura = new JTextField(50);
		txtAbreviatura.setBounds(155, 63, 101, 20);
		panelCuerpo.add(txtAbreviatura);
		txtAbreviatura.setColumns(10);
	}
}
