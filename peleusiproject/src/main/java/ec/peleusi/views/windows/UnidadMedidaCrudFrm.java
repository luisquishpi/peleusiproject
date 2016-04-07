package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.UnidadMedida;


public class UnidadMedidaCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblUnidadDeMedida;
	private JTextField txtNombre;
	private JTextField txtAbreviatura;

	public UnidadMedidaCrudFrm() {
		setTitle("Unidad de Medida");
		crearControles();
		crearEventos();
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 222);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(UnidadMedidaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(UnidadMedidaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar
				.setIcon(new ImageIcon(UnidadMedidaCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(UnidadMedidaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
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

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarCampos();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "No existen datos para grabar");
					return;
				}
				UnidadMedida unidadMedida = new UnidadMedida(txtNombre.getText(), txtAbreviatura.getText());
				UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
				String error = unidadMedidaController.createUnidadMedida(unidadMedida);
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					limpiarCampos();
				}
				else {					
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);					
				}

			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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

}
