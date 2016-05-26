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
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.Formatos;
import javax.swing.JFormattedTextField;

public class TipoPrecioCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private JFormattedTextField txtPorcentaje;
	private TipoPrecio tipoPrecio;

	public TipoPrecioCrudFrm() {
		setTitle("Tipo de Precio");
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
		if (tipoPrecio != null && tipoPrecio.getId() != null) {
			this.setTitle("Actualizar Tipo Precio");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(tipoPrecio.getNombre());
			txtPorcentaje.setText(Double.toString(tipoPrecio.getPorcentaje()));
		} else {
			this.setTitle("Creando Tipo Precio");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tipoPrecio.setNombre(txtNombre.getText());
		tipoPrecio.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
	}

	private void guardarNuevoTipoPrecio() {
		tipoPrecio = new TipoPrecio();
		llenarEntidadAntesDeGuardar();
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		String error = tipoPrecioController.createTipoPrecio(tipoPrecio);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTipoPrecio() {
		llenarEntidadAntesDeGuardar();
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		String error = tipoPrecioController.updateTipoPrecio(tipoPrecio);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public TipoPrecio getTipoPrecio() {
		return tipoPrecio;
	}

	public void setTipoPrecio(TipoPrecio tipoPrecio) {
		this.tipoPrecio = new TipoPrecio();
		this.tipoPrecio = tipoPrecio;
	}
	
	private void limpiarCampos() {
		txtNombre.setText("");
		txtPorcentaje.setText("0");
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtPorcentaje.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoPrecio = new TipoPrecio();
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
				if (tipoPrecio != null && tipoPrecio.getId() != null) {
					actualizarTipoPrecio();
				} else {
					guardarNuevoTipoPrecio();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoPrecio = null;
				dispose();
			}
		});
	}


	private void crearControles() {
		setBounds(100, 100, 508, 221);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoPrecioCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(20, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(TipoPrecioCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(180, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoPrecioCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(340, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(50, 30, 75, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField(50);
		txtNombre.setBounds(133, 27, 214, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(50);

		JLabel lblPorcentaje = new JLabel("Porcentaje");
		lblPorcentaje.setBounds(50, 72, 85, 14);
		panelCuerpo.add(lblPorcentaje);

		txtPorcentaje = new JFormattedTextField();
		txtPorcentaje.setToolTipText("");
		txtPorcentaje.setSize(75, 20);
		txtPorcentaje.setLocation(134, 69);
		txtPorcentaje.setFormatterFactory(new Formatos().getDecimalFormat());
		panelCuerpo.add(txtPorcentaje);
	}
}
