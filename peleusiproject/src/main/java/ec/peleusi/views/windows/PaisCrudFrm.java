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
import ec.peleusi.controllers.PaisController;
import ec.peleusi.models.entities.Pais;

public class PaisCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private JLabel lblEjEc;

	public PaisCrudFrm() {
		setTitle("Pais");
		crearControles();
		crearEventos();
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 204);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(PaisCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(PaisCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(PaisCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(PaisCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblCdigo = new JLabel("C\u00F3digo del pa\u00EDs");
		lblCdigo.setBounds(10, 11, 88, 14);
		panelCuerpo.add(lblCdigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(98, 8, 46, 20);
		panelCuerpo.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblNombre = new JLabel("Nombre del pa\u00EDs");
		lblNombre.setBounds(10, 42, 88, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(98, 36, 210, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(10);

		lblEjEc = new JLabel("Ej: EC");
		lblEjEc.setBounds(154, 11, 46, 14);
		panelCuerpo.add(lblEjEc);
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
					JOptionPane.showMessageDialog(null, "No deje campos vac�os");
					return;
				}
				Pais pais = new Pais(txtCodigo.getText(), txtNombre.getText());
				PaisController paisController = new PaisController();
				System.out.println(":"+pais);
				paisController.createPais(pais);
				System.out.println(":::"+pais);
				if (pais.getId() != null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente");
					limpiarCampos();
				}
			}

			private boolean isCamposLlenos() {
				boolean llenos = true;
				if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty())
					llenos = false;
				return llenos;
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
		txtNombre.setText("");
	}
}
