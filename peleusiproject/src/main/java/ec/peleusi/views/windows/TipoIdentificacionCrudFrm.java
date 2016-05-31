package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.models.entities.TipoIdentificacion;
import javax.swing.JCheckBox;
import javax.swing.JDialog;

public class TipoIdentificacionCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private JCheckBox chkValida;
	private TipoIdentificacion tipoIdentificacion;
	int limitecaja = 15;

	public TipoIdentificacionCrudFrm() {
		setTitle("Tipo Identificación");
		crearControles();
		crearEventos();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				llenarCamposConEntidad();
				txtCodigo.requestFocus();
			}
		});
	}

	private void llenarCamposConEntidad() {
		if (tipoIdentificacion != null && tipoIdentificacion.getId() != null) {
			this.setTitle("Actualizar Tipo Identificaciòn");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtCodigo.setText(tipoIdentificacion.getCodigo());
			txtNombre.setText(tipoIdentificacion.getNombre());
			chkValida.setSelected(tipoIdentificacion.getValida());
		} else {
			this.setTitle("Creando Tipo Identificaciòn");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tipoIdentificacion.setCodigo(txtCodigo.getText());
		tipoIdentificacion.setNombre(txtNombre.getText());
		tipoIdentificacion.setValida(chkValida.isSelected());
	}

	private void guardarNuevoTipoIdentificacion() {
		tipoIdentificacion = new TipoIdentificacion();
		llenarEntidadAntesDeGuardar();
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		String error = tipoIdentificacionController.createTipoIdentificacion(tipoIdentificacion);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTipoIdentificacion() {
		llenarEntidadAntesDeGuardar();
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		String error = tipoIdentificacionController.updateTipoIdentificacion(tipoIdentificacion);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = new TipoIdentificacion();
		this.tipoIdentificacion = tipoIdentificacion;
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

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoIdentificacion = new TipoIdentificacion();
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
				if (tipoIdentificacion != null && tipoIdentificacion.getId() != null) {
					actualizarTipoIdentificacion();
				} else {
					guardarNuevoTipoIdentificacion();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoIdentificacion = null;
				dispose();
			}
		});
	}

	private void crearControles() {
		setBounds(100, 100, 505, 223);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(
				new ImageIcon(TipoIdentificacionCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(20, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(
				new ImageIcon(TipoIdentificacionCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(180, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoIdentificacionCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(340, 14, 130, 39);
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
		txtCodigo.setBounds(106, 29, 214, 20);
		panelCuerpo.add(txtCodigo);
		txtCodigo.setColumns(15);

		chkValida = new JCheckBox("Validar Dato");
		chkValida.setBounds(350, 70, 97, 23);
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
}
