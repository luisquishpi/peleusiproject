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

//import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.TipoCalificacionPersonaController;
//import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.TipoCalificacionPersona;

public class TipoCalificacionPersonaCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;

	public TipoCalificacionPersonaCrudFrm() {
		setTitle("Tipo Calificación Persona");
		crearControles();
		crearEventos();
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 225);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoCalificacionPersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre ");
		lblNombre.setBounds(10, 39, 65, 14);
		panelCuerpo.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(106, 36, 210, 20);
		panelCuerpo.add(txtNombre);
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarCampos();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if (!isCamposLlenos()){
			    	JOptionPane.showMessageDialog(null, "No dejar campos vacíos");
			    	return;
			    }
			    
			  TipoCalificacionPersona tipoCalificacionPersona = new TipoCalificacionPersona(txtNombre.getText());
			  TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
			  String error = tipoCalificacionPersonaController.createTipoCalificacionPersona(tipoCalificacionPersona);
			  if (error == null) {
				  JOptionPane.showMessageDialog(null, "Guardado Correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				  limpiarCampos();
			  }
			  else {
				  JOptionPane.showMessageDialog(null,  error, "Error", JOptionPane.ERROR_MESSAGE);
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
		txtNombre.setText(" ");
		txtNombre.requestFocus();
	}
	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty())
			llenos = false;
		return llenos;
	}
	
}
