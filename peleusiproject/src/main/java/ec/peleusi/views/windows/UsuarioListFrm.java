package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
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
	private UsuarioCrudFrm usuarioCrudFrm = new UsuarioCrudFrm();

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
		List<Usuario> listaUsuario = usuarioController.UsuarioList();
		for (Usuario usuario : listaUsuario) {
			modelo.addRow(agregarDatosAFila(usuario));
		}
	}

	private void capturaYAgregaUsuarioATabla() {
		Usuario usuario = new Usuario();
		usuario = usuarioCrudFrm.getUsuario();
		if (usuario != null && usuario.getId() != null) {
			modelo.addRow(agregarDatosAFila(usuario));
			tblUsuario.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
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
				if (!usuarioCrudFrm.isVisible()) {
					usuarioCrudFrm.setModal(true);
					usuarioCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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
