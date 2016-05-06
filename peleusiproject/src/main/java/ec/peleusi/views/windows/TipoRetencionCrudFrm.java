package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
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
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblTipo;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JFormattedTextField txtPorcentaje;
	private JComboBox<TipoRetencionEnum> cmbTipoRetencion;
	private TipoRetencion tipoRetencionReturn;

	final int limitecaja = 15;
	public TipoRetencion getTipoRetencion;

	public TipoRetencionCrudFrm() {
		setTitle("Tipo de Retenciòn");
		crearControles();
		crearEventos();
		cargarComboTipoRetencion();
		limpiarCampos();
	}

	private void cargarComboTipoRetencion() {
		cmbTipoRetencion.setModel(new DefaultComboBoxModel<TipoRetencionEnum>(TipoRetencionEnum.values()));

	}

	private void crearControles() {

		setBounds(100, 100, 611, 301);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar
				.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		lblTipo = new JLabel("Tipo*");
		lblTipo.setBounds(20, 65, 91, 14);
		panelCuerpo.add(lblTipo);

		JLabel lblPorcentaje = new JLabel("Porcentaje*");
		lblPorcentaje.setBounds(20, 135, 91, 14);
		panelCuerpo.add(lblPorcentaje);

		JLabel lblCodigo = new JLabel("Còdigo*");
		lblCodigo.setBounds(20, 30, 91, 14);
		panelCuerpo.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setToolTipText("");
		txtCodigo.setBounds(96, 30, 113, 20);
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
		txtPorcentaje.setSize(75, 20);
		txtPorcentaje.setLocation(96, 135);
		txtPorcentaje.setFormatterFactory(new Formatos().getDecimalFormat());
		panelCuerpo.add(txtPorcentaje);

		JLabel lblNombre = new JLabel("Nombre*");
		lblNombre.setBounds(20, 100, 91, 14);
		panelCuerpo.add(lblNombre);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(96, 100, 467, 20);
		panelCuerpo.add(txtDescripcion);
		txtDescripcion.setColumns(10);

		cmbTipoRetencion = new JComboBox<TipoRetencionEnum>();
		cmbTipoRetencion.setBounds(96, 65, 113, 20);
		panelCuerpo.add(cmbTipoRetencion);

	}

	private void guardarNuevoTipoRetencion() {
		String por = txtPorcentaje.getText();
		TipoRetencionEnum tipoRetencionEnum = (TipoRetencionEnum) cmbTipoRetencion.getSelectedItem();
		TipoRetencion tipoRetencion = new TipoRetencion(txtCodigo.getText(), tipoRetencionEnum,
				Double.parseDouble(por.toString()), txtDescripcion.getText());
		TipoRetencionController tipoRetencionController = new TipoRetencionController();
		String error = tipoRetencionController.createTipoRetencion(tipoRetencion);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			tipoRetencionReturn = tipoRetencion;
			limpiarCampos();
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
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
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atención",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				guardarNuevoTipoRetencion();
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
		txtPorcentaje.setText("0");
		txtDescripcion.setText("");
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtPorcentaje.getText().isEmpty() || txtDescripcion.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	public TipoRetencion getTipoRetencion() {
		return tipoRetencionReturn;
	}
}
