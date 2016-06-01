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
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.peleusi.controllers.UsuarioController;
import ec.peleusi.models.entities.Usuario;
import ec.peleusi.utils.TipoUsuarioEnum;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class UsuarioCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtUsuario;
	private JTextField txtContrasenia;
	private JComboBox <TipoUsuarioEnum> cmbTipoUsuario;
	private Usuario usuario;

	public UsuarioCrudFrm() {
		setTitle("Usuario");
		crearControles();
		crearEventos();
		cargarComboTipoUsuario();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				llenarCamposConEntidad();
				txtNombre.requestFocus();
			}
		});
	}
	
	private void cargarComboTipoUsuario(){
		cmbTipoUsuario.setModel(new DefaultComboBoxModel<TipoUsuarioEnum>(TipoUsuarioEnum.values()));
	}
	
	private void llenarCamposConEntidad() {
		if (usuario != null && usuario.getId() != null) {
			this.setTitle("Actualizando Usuario");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(usuario.getNombres());
			txtApellido.setText(usuario.getApellidos());
			txtUsuario.setText(usuario.getUsuario());
			txtContrasenia.setText(usuario.getContrasenia());
			cmbTipoUsuario.setSelectedItem((TipoUsuarioEnum)usuario.getTipoUsuario());
		} else {
			this.setTitle("Creando Unidad de Medida");
			btnGuardar.setText("Guardar");
			limpiarCampos();
		}
	}
	
	private void llenarEntidadAntesDeGuardar() {
		usuario.setNombres(txtNombre.getText());
		usuario.setApellidos(txtApellido.getText());
		usuario.setUsuario(txtUsuario.getText());
		usuario.setContrasenia(txtContrasenia.getText());
		usuario.setTipoUsuario((TipoUsuarioEnum) cmbTipoUsuario.getSelectedItem());
		}
	
	private void guardarNuevoUsuario() {
		usuario = new Usuario();
		llenarEntidadAntesDeGuardar();
		UsuarioController usuarioController = new UsuarioController();
		String error = usuarioController.createUsuario(usuario);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarUsuario() {
		llenarEntidadAntesDeGuardar();
		UsuarioController usuarioController = new UsuarioController();
		String error = usuarioController.updateUsuario(usuario);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = new Usuario();
		this.usuario = usuario;
	}
	
	private void limpiarCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtUsuario.setText("");
		txtContrasenia.setText("");
		txtNombre.requestFocus();
		
	}
	
	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtApellido.getText().isEmpty() || txtUsuario.getText().isEmpty() || txtContrasenia.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usuario = new Usuario();
				llenarCamposConEntidad();
			}
		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    if (!isCamposLlenos()){
			    	JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
			    	return;
			    }
			   
			    if (usuario != null && usuario.getId() != null) {
					actualizarUsuario();
				} else {
					guardarNuevoUsuario();
				}
			    
			}
			
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuario = new Usuario();
				dispose();
			}
		});
	}

	
	private void crearControles() {		
		setBounds(100, 100, 510, 321);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(UsuarioCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(20, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(UsuarioCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(180, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(UsuarioCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(340, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);
		
		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(10, 15, 65, 14);
		panelCuerpo.add(lblNombres);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(106, 15, 210, 20);
		panelCuerpo.add(txtNombre);
		
		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(10, 55, 65, 14);
		panelCuerpo.add(lblApellidos);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(106, 55, 210, 20);
		panelCuerpo.add(txtApellido);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 95, 65, 14);
		panelCuerpo.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(106, 92, 210, 20);
		panelCuerpo.add(txtUsuario);
		
		JLabel lblContrasenia = new JLabel("Contraseña");
		lblContrasenia.setBounds(10, 135, 86, 14);
		panelCuerpo.add(lblContrasenia);
		
		txtContrasenia = new JTextField();
		txtContrasenia.setColumns(10);
		txtContrasenia.setBounds(106, 135, 210, 20);
		panelCuerpo.add(txtContrasenia);
		
		JLabel lblTipoUsuario = new JLabel("Tipo Usuario");
		lblTipoUsuario.setBounds(10, 175, 86, 14);
		panelCuerpo.add(lblTipoUsuario);
		
		cmbTipoUsuario = new JComboBox<TipoUsuarioEnum>();
		cmbTipoUsuario.setBounds(106, 175, 210, 20);
		panelCuerpo.add(cmbTipoUsuario);
	}
}
