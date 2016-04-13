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

import ec.peleusi.controllers.TipoRetencionController;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.Formatos;

import javax.swing.JFormattedTextField;

public class TipoRetencionCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblTipo;
	private JTextField txtTipo;
	private JTextField txtCodigo;
	private JTextField txtDescripcion;
	private JFormattedTextField txtPorcentaje;
	
	
	int limitecaja = 15;	

	public TipoRetencionCrudFrm() {
		setTitle("Tipo de Retenciòn");
		crearControles();
		crearEventos();
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
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
		btnEliminar.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoRetencionCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(20, 68, 91, 14);
		panelCuerpo.add(lblTipo);

		txtTipo = new JTextField(50);
		txtTipo.setBounds(96, 68, 195, 20);
		panelCuerpo.add(txtTipo);
		txtTipo.setColumns(50);

		JLabel lblPorcentaje = new JLabel("Porcentaje");
		lblPorcentaje.setBounds(20, 103, 91, 14);
		panelCuerpo.add(lblPorcentaje);

		JLabel lblCodigo = new JLabel("Còdigo ");
		lblCodigo.setBounds(20, 33, 91, 14);
		panelCuerpo.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setToolTipText("");
		txtCodigo.setBounds(96, 33, 113, 20);
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
		txtPorcentaje.setLocation(96, 103);
		txtPorcentaje.setFormatterFactory(new Formatos().getDecimalFormat());
		panelCuerpo.add(txtPorcentaje);
		
		JLabel lblDescripcin = new JLabel("Descripciòn");
		lblDescripcin.setBounds(20, 138, 91, 14);
		panelCuerpo.add(lblDescripcin);
		
		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(96, 138, 467, 25);
		panelCuerpo.add(txtDescripcion);
		txtDescripcion.setColumns(10);
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
				TipoRetencion tipoRetencion = new TipoRetencion(txtCodigo.getText(), txtTipo.getText(),
						Double.parseDouble(txtPorcentaje.getText()), txtDescripcion.getText());
				TipoRetencionController tipoRetencionController = new TipoRetencionController();
				String error = tipoRetencionController.createTipoRetencion(tipoRetencion);
				
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					limpiarCampos();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
					limpiarCampos();
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
		txtCodigo.setText("");
		txtTipo.setText("");
		txtPorcentaje.setText("0");
		txtDescripcion.setText("");
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtTipo.getText().isEmpty() || txtPorcentaje.getText().isEmpty() || txtDescripcion.getText().isEmpty())
			llenos = false;
		return llenos;
	}
}
