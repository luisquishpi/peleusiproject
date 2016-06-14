package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import ec.peleusi.controllers.UsuarioController;
import ec.peleusi.models.entities.Usuario;
import ec.peleusi.utils.TipoUsuarioEnum;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class UsuarioListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JTextFieldPH txtBuscar;
	private UsuarioCrudFrm usuarioCrudFrm = new UsuarioCrudFrm();
	private Usuario usuario;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<Usuario> jPanelWithTable;	


	public UsuarioListFrm() {
		setTitle("Listado de Usuarios");
		crearControles();
		crearEventos();
		crearTabla();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				txtBuscar.requestFocus();
			}
		});		
	}

	private void crearTabla() {
		UsuarioController usuarioController = new UsuarioController();
		List<Usuario> listaUsuario = usuarioController.getUsuarioList(txtBuscar.getText());

		if (totalItems == 0 && listaUsuario != null)
			totalItems = listaUsuario.size();	
		
		jPanelWithTable = new JPanelWithTable<Usuario>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombres", "apellidos", "usuario", "contrasenia", "tipoUsuario" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 150, 150, 100 ,90, 108 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRES", "APELLIDOS", "USUARIO", "CONTRASEÑA", "TIPO USUARIO" };
		
		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaUsuario), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}			

	private void capturaYAgregaUsuarioATabla() {
		if (usuario == usuarioCrudFrm.getUsuario() && usuario.getId() != null) {
			txtBuscar.setText(usuario.getNombres());
			crearTabla();
		} else {
			if (usuarioCrudFrm.getUsuario() != null && usuarioCrudFrm.getUsuario().getId() != null) {
				totalItems++;
				txtBuscar.setText(usuarioCrudFrm.getUsuario().getNombres());
				crearTabla();	
			}
		}
	}
	
	private boolean llenarEntidadParaEnviarAUsuarioCrudFrm() {
		usuario = new Usuario();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			usuario.setId(Integer.parseInt(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			usuario.setNombres(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			usuario.setApellidos(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString());
			usuario.setUsuario(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 3).toString());
			usuario.setContrasenia(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 4).toString());
			usuario.setTipoUsuario((TipoUsuarioEnum)(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 5)));
			usuarioCrudFrm.setUsuario(usuario);
			return true;
		}
		return false;
	}
	
	private void eliminarUsuario() {
		if (llenarEntidadParaEnviarAUsuarioCrudFrm()) {			
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + usuario.getNombres()+ "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				UsuarioController usuarioController = new UsuarioController();
				String error = usuarioController.deleteUsuario(usuario);
				if (error == null) {
					totalItems--;
					crearTabla();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	

	private void crearEventos() {
		usuarioCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaUsuarioATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				usuario = new Usuario();
				usuarioCrudFrm.setUsuario(usuario);				
				if (!usuarioCrudFrm.isVisible()) {
					usuarioCrudFrm.setModal(true);
					usuarioCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarAUsuarioCrudFrm()){
					if (!usuarioCrudFrm.isVisible()) {
						usuarioCrudFrm.setModal(true);
						usuarioCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarUsuario();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearTabla();
			}
		});
		
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					crearTabla();
					if (jPanelWithTable.getJTable() != null) {
						jPanelWithTable.getJTable().addRowSelectionInterval(0, 0);
						jPanelWithTable.getJTable().requestFocus();
					}
				}
			}
		});				
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 379);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(450, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));
		
		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));
		
		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba nombre o apellido o usuario");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);
		
		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));	
		
	}
}
