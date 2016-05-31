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


public class UsuarioCrudFrm<TipoUsuario> extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private JLabel lblApellidos;
	private JTextField txtApellido;
	private JLabel lblUsuario;
	private JTextField txtUsuario;
	private JLabel lblContrasenia;
	private JTextField txtContrasenia;
	private JLabel lblTipoUsuario;
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
	
	@SuppressWarnings("unchecked")
	private void llenarCamposConEntidad() {
		if (usuario != null && usuario.getId() != null) {
			this.setTitle("Actualizando Usuario");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(usuario.getNombres());
			txtApellido.setText(usuario.getApellidos());
			txtUsuario.setText(usuario.getUsuario());
			txtContrasenia.setText(usuario.getContrasenia());
			cmbTipoUsuario.setSelectedItem((TipoUsuario)usuario.getTipoUsuario());
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
			   
			  //TipoUsuarioEnum tipoUsuario = (TipoUsuarioEnum) cmbTipoUsuario.getSelectedItem(); 
			    if (usuario != null && usuario.getId() != null) {
					actualizarUsuario();
				} else {
					guardarNuevoUsuario();
				}
			    
			}
			
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		setBounds(100, 100, 666, 340);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(UsuarioCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(UsuarioCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(UsuarioCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(UsuarioCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);
		
		JLabel lblNombres = new JLabel("Nombres");
		lblNombres.setBounds(10, 11, 65, 14);
		panelCuerpo.add(lblNombres);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(106, 8, 210, 20);
		panelCuerpo.add(txtNombre);
		
		lblApellidos = new JLabel("Apellidos");
		lblApellidos.setBounds(10, 56, 65, 14);
		panelCuerpo.add(lblApellidos);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(106, 53, 210, 20);
		panelCuerpo.add(txtApellido);
		
		lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 95, 65, 14);
		panelCuerpo.add(lblUsuario);
		
		txtUsuario = new JTextField();
		txtUsuario.setColumns(10);
		txtUsuario.setBounds(106, 92, 210, 20);
		panelCuerpo.add(txtUsuario);
		
		lblContrasenia = new JLabel("Contraseña");
		lblContrasenia.setBounds(10, 139, 86, 14);
		panelCuerpo.add(lblContrasenia);
		
		txtContrasenia = new JTextField();
		txtContrasenia.setColumns(10);
		txtContrasenia.setBounds(106, 136, 210, 20);
		panelCuerpo.add(txtContrasenia);
		
		lblTipoUsuario = new JLabel("Tipo Usuario");
		lblTipoUsuario.setBounds(10, 186, 65, 14);
		panelCuerpo.add(lblTipoUsuario);
		
		cmbTipoUsuario = new JComboBox<TipoUsuarioEnum>();
		cmbTipoUsuario.setBounds(106, 183, 210, 20);
		panelCuerpo.add(cmbTipoUsuario);
	}
}
