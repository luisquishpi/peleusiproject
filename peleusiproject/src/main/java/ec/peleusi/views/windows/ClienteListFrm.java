package ec.peleusi.views.windows;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import ec.peleusi.controllers.ClienteController;
import ec.peleusi.models.entities.Cliente;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class ClienteListFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtBuscar;
	private JTable tblCliente;
	private JButton btnNuevo;
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnCancelar;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private JScrollPane scrollPane;

	private ClienteCrudFrm clienteCrudFrm = new ClienteCrudFrm();
	private JButton btnBuscar;

	public ClienteListFrm() {

		setTitle("Lista de clientes");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Identificaciòn", "Razòn Social","Dirección","Teléfono", "Tipo Precio" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblCliente = new JTable(modelo) {

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
		tblCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblCliente.setPreferredScrollableViewportSize(tblCliente.getPreferredSize());
		tblCliente.getTableHeader().setReorderingAllowed(true);
		tblCliente.getColumnModel().getColumn(0).setMaxWidth(0);
		tblCliente.getColumnModel().getColumn(0).setMinWidth(0);
		tblCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblCliente.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblCliente.getColumnModel().getColumn(2).setPreferredWidth(300);
		tblCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblCliente.getColumnModel().getColumn(5).setPreferredWidth(100);
		scrollPane.setViewportView(tblCliente);

	}

	private Object[] agregarDatosAFila(Cliente cliente) {
		filaDatos[0] = cliente.getId();
		filaDatos[1] = cliente.getIdentificacion();
		filaDatos[2] = cliente.getRazonSocial();
		filaDatos[3] = cliente.getDireccion();
		filaDatos[4] = cliente.getTelefono();		
		filaDatos[5] = cliente.getTipoPrecio().getNombre();
		return filaDatos;
	}

	private void cargarTabla() {
		ClienteController clienteController = new ClienteController();
		List<Cliente> listaCliente = clienteController.PersonaList();
		for (Cliente cliente : listaCliente) {
			modelo.addRow(agregarDatosAFila(cliente));
		}

	}

	private void capturaYAgregaPersonaATabla() {
		Cliente cliente = new Cliente();
		cliente = clienteCrudFrm.getCliente();
		if (cliente != null && cliente.getId() != null) {
			System.out.println("Captura Cliente retornado: " + cliente);
			modelo.addRow(agregarDatosAFila(cliente));
			tblCliente.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	private void crearEventos() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ClienteController clienteController = new ClienteController();
				List<Cliente> listaCliente = clienteController.ClienteList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (Cliente cliente : listaCliente) {
					modelo.addRow(agregarDatosAFila(cliente));
					}

			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				
					if (!clienteCrudFrm.isVisible()) {
						clienteCrudFrm.setModal(true);
						clienteCrudFrm.setVisible(true);
					
				}
			}
		});
		clienteCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaPersonaATabla();
			}
		});

	}

	private void crearControles() {

		setTitle("Lista de clientes");
		setBounds(100, 100, 700, 417);
		setClosable(true);
		setIconifiable(true);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);

		btnNuevo = new JButton("Nuevo");
		
		btnNuevo.setIcon(new ImageIcon(ClienteListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panel.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(ClienteListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panel.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(ClienteListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panel.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		
		btnCancelar.setIcon(new ImageIcon(ClienteListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panel.add(btnCancelar);

		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);

		txtBuscar = new JTextField();
		txtBuscar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBuscar.setColumns(10);
		txtBuscar.setBounds(20, 18, 510, 41);
		panel_1.add(txtBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 70, 654, 237);
		panel_1.add(scrollPane);

		tblCliente = new JTable((TableModel) null);
		tblCliente.setPreferredScrollableViewportSize(new Dimension(0, 0));
		tblCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tblCliente);
		
				btnBuscar = new JButton("Buscar");
				
						btnBuscar.setIcon(new ImageIcon(ClienteListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
						btnBuscar.setBounds(533, 19, 120, 41);
						panel_1.add(btnBuscar);

	}
}
