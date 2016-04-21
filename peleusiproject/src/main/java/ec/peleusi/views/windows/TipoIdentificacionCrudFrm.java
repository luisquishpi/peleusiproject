package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.models.entities.TipoIdentificacion;
import javax.swing.JCheckBox;

public class TipoIdentificacionCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private JCheckBox chkValida;

	int limitecaja = 15;

	public TipoIdentificacionCrudFrm() {
		setTitle("Tipo Identificaciòn");
		crearControles();
		crearEventos();
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 223);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(
				new ImageIcon(TipoIdentificacionCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(
				new ImageIcon(TipoIdentificacionCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(
				new ImageIcon(TipoIdentificacionCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoIdentificacionCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		lblNombre = new JLabel("Nombre*");
		lblNombre.setBounds(50, 70, 101, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField(50);
		txtNombre.setBounds(106, 70, 214, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(50);

		JLabel lblCodigo = new JLabel("Còdigo*");
		lblCodigo.setBounds(50, 29, 46, 14);
		panelCuerpo.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setToolTipText("");
		txtCodigo.setBounds(106, 29, 141, 20);
		panelCuerpo.add(txtCodigo);
		txtCodigo.setColumns(15);

		chkValida = new JCheckBox("Validar Dato");
		chkValida.setBounds(348, 69, 97, 23);
		panelCuerpo.add(chkValida);

		txtCodigo.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				if (txtCodigo.getText().length() == limitecaja) {
					e.consume();
				}
			}

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}
		});

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
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				TipoIdentificacion tipoIdentificacion = new TipoIdentificacion(txtCodigo.getText(), txtNombre.getText(),
						chkValida.isSelected());
				TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
				String error = tipoIdentificacionController.createTipoIdentificacion(tipoIdentificacion);
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE );
					limpiarCampos();
				} else {
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
				dispose();
			}
		});
	}

	private void limpiarCampos() {
		txtCodigo.setText("");
		txtNombre.setText("");
		chkValida.setSelected(false);
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty())
			llenos = false;
		return llenos;
	}
}
