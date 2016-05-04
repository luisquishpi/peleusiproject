package ec.peleusi.views.windows;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ec.peleusi.controllers.ClienteController;
import ec.peleusi.models.entities.Cliente;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;

public class ClienteListModal extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtBuscar;
	private JTable tblCliente;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnNuevo;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	ClienteCrudFrm clienteCrudFrm = new ClienteCrudFrm();
	private Cliente cliente;

	public ClienteListModal() {
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Identificación", "Razón Social", "Dirección", "Teléfono" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4) {
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

		tblCliente.getColumnModel().getColumn(1).setPreferredWidth(130);
		tblCliente.getColumnModel().getColumn(2).setPreferredWidth(280);
		tblCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblCliente.getColumnModel().getColumn(4).setPreferredWidth(100);
		scrollPane.setViewportView(tblCliente);

	}

	private Object[] agregarDatosAFila(Cliente cliente) {
		filaDatos[0] = cliente.getId();
		filaDatos[1] = cliente.getIdentificacion();
		filaDatos[2] = cliente.getRazonSocial();
		filaDatos[3] = cliente.getDireccion();
		filaDatos[4] = cliente.getTelefono();
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
			modelo.addRow(agregarDatosAFila(cliente));
			tblCliente.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void addConfirmListener(ActionListener listener) {
		btnAceptar.addActionListener(listener);
	}

	private void crearEventos() {

		clienteCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaPersonaATabla();
			}
		});

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
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int fila = tblCliente.getSelectedRow();

				System.out.println(">>>> " + fila + "<<<<<");
				if (fila != -1) {
					cliente = new Cliente();
					cliente.setId(Integer.parseInt(modelo.getValueAt(tblCliente.getSelectedRow(), 0).toString()));
					cliente.setIdentificacion(modelo.getValueAt(tblCliente.getSelectedRow(), 1).toString());
					cliente.setRazonSocial(modelo.getValueAt(tblCliente.getSelectedRow(), 2).toString());
					cliente.setDireccion(modelo.getValueAt(tblCliente.getSelectedRow(), 3).toString());
					cliente.setTelefono(modelo.getValueAt(tblCliente.getSelectedRow(), 4).toString());
				}
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

	}

	private void crearControles() {
		setTitle("Lista de clientes");
		setBounds(100, 100, 620, 431);

		JPanel panel = new JPanel();
		panel.setLayout(null);
		getContentPane().add(panel, BorderLayout.CENTER);

		txtBuscar = new JTextField();
		txtBuscar.setColumns(10);
		txtBuscar.setBounds(10, 8, 446, 41);
		panel.add(txtBuscar);

		btnBuscar = new JButton("Buscar");

		btnBuscar.setIcon(new ImageIcon(ClienteListModal.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panel.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 575, 269);
		panel.add(scrollPane);

		tblCliente = new JTable();
		tblCliente.setPreferredScrollableViewportSize(new Dimension(0, 0));
		tblCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tblCliente);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setPreferredSize(new Dimension(200, 70));
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(0, 340, 595, 70);
		panel.add(panel_1);

		btnNuevo = new JButton("Nuevo");

		btnNuevo.setIcon(new ImageIcon(ClienteListModal.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(376, 11, 130, 39);
		panel_1.add(btnNuevo);

		btnAceptar = new JButton("Aceptar");

		btnAceptar.setIcon(new ImageIcon(ClienteListModal.class.getResource("/ec/peleusi/utils/images/Select.png")));
		btnAceptar.setBounds(96, 11, 130, 39);
		panel_1.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(ClienteListModal.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(236, 11, 130, 39);
		panel_1.add(btnCancelar);

	}

}
