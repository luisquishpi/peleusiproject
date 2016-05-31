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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import ec.peleusi.controllers.UsuarioController;
import ec.peleusi.models.entities.Usuario;
import ec.peleusi.utils.TipoUsuarioEnum;

import java.awt.Font;

public class UsuarioListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtBuscar;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private JTable tblUsuario;
	@SuppressWarnings("rawtypes")
	private UsuarioCrudFrm usuarioCrudFrm = new UsuarioCrudFrm();
	private Usuario usuario;

	public UsuarioListFrm() {
		setTitle("Lista de Usuarios");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Nombres", "Apellidos", "Usuario", "Contraseña", "TipoUsuario" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4
						|| columnIndex == 5) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblUsuario = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		tblUsuario.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblUsuario.setPreferredScrollableViewportSize(tblUsuario.getPreferredSize());
		tblUsuario.getTableHeader().setReorderingAllowed(true);
		tblUsuario.getColumnModel().getColumn(0).setMaxWidth(0);
		tblUsuario.getColumnModel().getColumn(0).setMinWidth(0);
		tblUsuario.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblUsuario.getColumnModel().getColumn(1).setPreferredWidth(132);
		tblUsuario.getColumnModel().getColumn(2).setPreferredWidth(132);
		tblUsuario.getColumnModel().getColumn(3).setPreferredWidth(75);
		tblUsuario.getColumnModel().getColumn(4).setMaxWidth(0);
		tblUsuario.getColumnModel().getColumn(4).setMinWidth(0);
		tblUsuario.getColumnModel().getColumn(4).setPreferredWidth(0);
		tblUsuario.getColumnModel().getColumn(5).setPreferredWidth(104);
		scrollPane.setViewportView(tblUsuario);

	}

	private Object[] agregarDatosAFila(Usuario usuario) {
		filaDatos[0] = usuario.getId();
		filaDatos[1] = usuario.getNombres();
		filaDatos[2] = usuario.getApellidos();
		filaDatos[3] = usuario.getUsuario();
		filaDatos[4] = usuario.getContrasenia();
		filaDatos[5] = usuario.getTipoUsuario();
		return filaDatos;
	}

	private void cargarTabla() {
		UsuarioController usuarioController = new UsuarioController();
		List<Usuario> listaUsuario = usuarioController.usuarioList();
		for (Usuario usuario : listaUsuario) {
			modelo.addRow(agregarDatosAFila(usuario));
		}
	}

	private void capturaYAgregaUsuarioATabla() {
		if (usuario == usuarioCrudFrm.getUsuario() && usuario.getId() != null) {
			modelo.setValueAt(usuario.getNombres(), tblUsuario.getSelectedRow(), 1);
			modelo.setValueAt(usuario.getApellidos(), tblUsuario.getSelectedRow(), 2);
			modelo.setValueAt(usuario.getUsuario(), tblUsuario.getSelectedRow(), 3);
			modelo.setValueAt(usuario.getContrasenia(), tblUsuario.getSelectedRow(), 4);
			modelo.setValueAt(usuario.getTipoUsuario(), tblUsuario.getSelectedRow(), 5);
		} else {
			if (usuarioCrudFrm.getUsuario() != null && usuarioCrudFrm.getUsuario().getId() != null) {
				modelo.addRow(agregarDatosAFila(usuarioCrudFrm.getUsuario()));
				tblUsuario.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
			}
		}
	}
	
	private boolean llenarEntidadParaEnviarAUsuarioCrudFrm() {
		usuario = new Usuario();
		if (tblUsuario.getSelectedRow() != -1) {
			usuario.setId(Integer.parseInt(modelo.getValueAt(tblUsuario.getSelectedRow(), 0).toString()));
			usuario.setNombres(modelo.getValueAt(tblUsuario.getSelectedRow(), 1).toString());
			usuario.setApellidos(modelo.getValueAt(tblUsuario.getSelectedRow(), 2).toString());
			usuario.setUsuario(modelo.getValueAt(tblUsuario.getSelectedRow(), 3).toString());
			usuario.setContrasenia(modelo.getValueAt(tblUsuario.getSelectedRow(), 4).toString());
			usuario.setTipoUsuario((TipoUsuarioEnum)(modelo.getValueAt(tblUsuario.getSelectedRow(), 5)));
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
					modelo.removeRow(tblUsuario.getSelectedRow());
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
				UsuarioController usuarioController = new UsuarioController();
				List<Usuario> listaUsuario = usuarioController.getUsuarioList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (Usuario usuario : listaUsuario) {
					modelo.addRow(agregarDatosAFila(usuario));
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
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		txtBuscar = new JTextField();
		txtBuscar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBuscar.setBounds(10, 8, 446, 41);
		panelCuerpo.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(UsuarioListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);
	}

}
