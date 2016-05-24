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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.peleusi.controllers.TipoPagoController;
import ec.peleusi.models.entities.TipoPago;

public class TipoPagoCrudFrm extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private TipoPago tipoPago;
	
	public TipoPagoCrudFrm() {
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
		if (tipoPago != null && tipoPago.getId() != null) {
			this.setTitle("Actualizando Tipo Pago");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(tipoPago.getNombre());
		} else {
			this.setTitle("Creando Tipo Pago");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}		
			
	private void llenarEntidadAntesDeGuardar() {
		tipoPago.setNombre(txtNombre.getText());
	}		
			
	private void guardarNuevoTipoPago() {
		tipoPago = new TipoPago();
		llenarEntidadAntesDeGuardar();
		TipoPagoController tipoPagoController = new TipoPagoController();
		String error = tipoPagoController.createTipoPago(tipoPago);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
			
	private void actualizarTipoPago() {
		llenarEntidadAntesDeGuardar();
		TipoPagoController tipoPagoController = new TipoPagoController();
		String error = tipoPagoController.updateTipoPago(tipoPago);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
			
	public TipoPago getTipoPago() {
		return tipoPago;
	}	
		
	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = new TipoPago();
		this.tipoPago = tipoPago;
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
				tipoPago = new TipoPago();
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
				
				if (tipoPago != null && tipoPago.getId() != null) {
					actualizarTipoPago();
				} else {		
					guardarNuevoTipoPago();
				}
			}			
		});
		
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoPago = new TipoPago();
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
		btnNuevo.setIcon(new ImageIcon(TipoPagoCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(TipoPagoCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TipoPagoCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoPagoCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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
	
	
	
		
	
