package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
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
import ec.peleusi.controllers.TipoRetencionController;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.Formatos;
import ec.peleusi.utils.TipoRetencionEnum;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;

public class TipoRetencionCrudFrm extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JFormattedTextField txtPorcentaje;
	private JComboBox<TipoRetencionEnum> cmbTipoRetencion;
	private TipoRetencion tipoRetencion;
	public TipoRetencion getTipoRetencion;
	final int limitecaja = 15;

	public TipoRetencionCrudFrm() {
		setTitle("Tipo de Retenciòn");
		crearControles();
		crearEventos();
		cargarComboTipoRetencion();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				llenarCamposConEntidad();
				txtCodigo.requestFocus();
			}
		});
	}

	private void cargarComboTipoRetencion() {
		cmbTipoRetencion.setModel(new DefaultComboBoxModel<TipoRetencionEnum>(TipoRetencionEnum.values()));
	}

	private void llenarCamposConEntidad() {
		if (tipoRetencion != null && tipoRetencion.getId() != null) {
			this.setTitle("Actualizar Tipo Retención");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtCodigo.setText(tipoRetencion.getCodigo());
			cmbTipoRetencion.setSelectedItem(tipoRetencion.getTipoRetencionEnum());			
			txtDescripcion.setText(tipoRetencion.getDescripcion());
			txtPorcentaje.setText(Double.toString(tipoRetencion.getPorcentaje()));
		} else {
			this.setTitle("Creando Tipo Retención");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}

	private void llenarEntidadAntesDeGuardar() {
		tipoRetencion.setCodigo(txtCodigo.getText());
		tipoRetencion.setTipoRetencionEnum((TipoRetencionEnum) cmbTipoRetencion.getSelectedItem());
		tipoRetencion.setDescripcion(txtDescripcion.getText());
		tipoRetencion.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
	}

	private void guardarNuevoTipoRetencion() {
		tipoRetencion = new TipoRetencion();
		llenarEntidadAntesDeGuardar();
		TipoRetencionController tipoRetencionController = new TipoRetencionController();
		String error = tipoRetencionController.createTipoRetencion(tipoRetencion);		 
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTipoRetencion() {
		llenarEntidadAntesDeGuardar();
		TipoRetencionController tipoRetencionController = new TipoRetencionController();
		String error = tipoRetencionController.update(tipoRetencion);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public TipoRetencion getTipoRetencion() {
		return tipoRetencion;
	}

	public void setTipoRetencion(TipoRetencion tipoRetencion) {
		this.tipoRetencion = new TipoRetencion();
		this.tipoRetencion = tipoRetencion;
	}

	private void limpiarCampos() {
		txtCodigo.setText("");
		txtPorcentaje.setText("0");
		txtDescripcion.setText("");
		txtCodigo.requestFocus();
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtPorcentaje.getText().isEmpty() || txtDescripcion.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoRetencion = new TipoRetencion();
				llenarCamposConEntidad();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atención",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (tipoRetencion != null && tipoRetencion.getId() != null) {
					actualizarTipoRetencion();
				} else {
					guardarNuevoTipoRetencion();
				}
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoRetencion = null;
				dispose();
			}
		});
	}

	private void crearControles() {

		setBounds(100, 100, 570, 287);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(25, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(215, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(405, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblTipo = new JLabel("Tipo*");
		lblTipo.setBounds(15, 60, 91, 14);
		panelCuerpo.add(lblTipo);

		JLabel lblPorcentaje = new JLabel("Porcentaje*");
		lblPorcentaje.setBounds(15, 140, 75, 14);
		panelCuerpo.add(lblPorcentaje);

		JLabel lblCodigo = new JLabel("Còdigo*");
		lblCodigo.setBounds(15, 20, 91, 14);
		panelCuerpo.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setToolTipText("");
		txtCodigo.setBounds(90, 20, 120, 20);
		panelCuerpo.add(txtCodigo);
		txtCodigo.setColumns(15);

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

		txtPorcentaje = new JFormattedTextField();
		txtPorcentaje.setToolTipText("");
		txtPorcentaje.setSize(120, 20);
		txtPorcentaje.setLocation(90, 140);
		txtPorcentaje.setFormatterFactory(new Formatos().getDecimalFormat());
		panelCuerpo.add(txtPorcentaje);

		JLabel lblNombre = new JLabel("Nombre*");
		lblNombre.setBounds(15, 100, 91, 14);
		panelCuerpo.add(lblNombre);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(90, 100, 449, 20);
		panelCuerpo.add(txtDescripcion);
		txtDescripcion.setColumns(10);

		cmbTipoRetencion = new JComboBox<TipoRetencionEnum>();
		cmbTipoRetencion.setBounds(90, 60, 120, 20);
		panelCuerpo.add(cmbTipoRetencion);
	}

}
