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

import ec.peleusi.controllers.TipoCalificacionClienteController;
import ec.peleusi.models.entities.TipoCalificacionCliente;

import java.awt.Font;

public class TipoCalificacionClienteListFrm extends JInternalFrame {

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
	private JTable tblTipoCalificacionCliente;
	private TipoCalificacionClienteCrudFrm tipoCalificacionClienteCrudFrm = new TipoCalificacionClienteCrudFrm();
	private TipoCalificacionCliente tipoCalificacionCliente;

	public TipoCalificacionClienteListFrm() {
		setTitle("Listado de Tipo Calificacion Cliente");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Nombre" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblTipoCalificacionCliente = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		tblTipoCalificacionCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTipoCalificacionCliente.setPreferredScrollableViewportSize(tblTipoCalificacionCliente.getPreferredSize());
		tblTipoCalificacionCliente.getTableHeader().setReorderingAllowed(true);
		tblTipoCalificacionCliente.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTipoCalificacionCliente.getColumnModel().getColumn(0).setMinWidth(0);
		tblTipoCalificacionCliente.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTipoCalificacionCliente.getColumnModel().getColumn(1).setPreferredWidth(443);
		scrollPane.setViewportView(tblTipoCalificacionCliente);

	}

	private Object[] agregarDatosAFila(TipoCalificacionCliente tipoCalificacionCliente) {
		filaDatos[0] = tipoCalificacionCliente.getId();
		filaDatos[1] = tipoCalificacionCliente.getNombre();
		return filaDatos;
	}

	private void cargarTabla() {
		TipoCalificacionClienteController tipoCalificacionClienteController = new TipoCalificacionClienteController();
		List<TipoCalificacionCliente> listaTipoCalificacionCliente = tipoCalificacionClienteController.tipoCalificacionClienteList();
		for (TipoCalificacionCliente tipoCalificacionCliente : listaTipoCalificacionCliente) {
			modelo.addRow(agregarDatosAFila(tipoCalificacionCliente));
		}
	}

	private void capturaYAgregaTipoCalificacionClienteATabla() {
		if (tipoCalificacionCliente == tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente()
				&& tipoCalificacionCliente.getId() != null) {
			modelo.setValueAt(tipoCalificacionCliente.getNombre(), tblTipoCalificacionCliente.getSelectedRow(), 1);
		} else {
			if (tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente() != null
					&& tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente().getId() != null) {
				modelo.addRow(agregarDatosAFila(tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente()));
				tblTipoCalificacionCliente.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
			}
		}
	}

	private boolean llenarEntidadParaEnviarATipoCalificacionClienteCrudFrm() {
		tipoCalificacionCliente = new TipoCalificacionCliente();
		if (tblTipoCalificacionCliente.getSelectedRow() != -1) {
			tipoCalificacionCliente.setId(Integer.parseInt(modelo.getValueAt(tblTipoCalificacionCliente.getSelectedRow(), 0).toString()));
			tipoCalificacionCliente.setNombre(modelo.getValueAt(tblTipoCalificacionCliente.getSelectedRow(), 1).toString());
			tipoCalificacionClienteCrudFrm.setTipoCalificacionCliente(tipoCalificacionCliente);
			return true;
		}
		return false;
	}

	private void eliminarTipoCalificacionCliente() {
		if (llenarEntidadParaEnviarATipoCalificacionClienteCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoCalificacionCliente.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoCalificacionClienteController tipoCalificacionClienteController = new TipoCalificacionClienteController();
				String error = tipoCalificacionClienteController.deleteTipoCalificacionCliente(tipoCalificacionCliente);
				if (error == null) {
					modelo.removeRow(tblTipoCalificacionCliente.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}

		}
	}

	private void crearEventos() {
		tipoCalificacionClienteCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoCalificacionClienteATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoCalificacionCliente = new TipoCalificacionCliente();
				tipoCalificacionClienteCrudFrm.setTipoCalificacionCliente(tipoCalificacionCliente);
				if (!tipoCalificacionClienteCrudFrm.isVisible()) {
					tipoCalificacionClienteCrudFrm.setModal(true);
					tipoCalificacionClienteCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoCalificacionClienteCrudFrm()) {
					if (!tipoCalificacionClienteCrudFrm.isVisible()) {
						tipoCalificacionClienteCrudFrm.setModal(true);
						tipoCalificacionClienteCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoCalificacionCliente();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoCalificacionClienteController tipoCalificacionClienteController = new TipoCalificacionClienteController();
				List<TipoCalificacionCliente> listaTipoCalificacionCliente = tipoCalificacionClienteController
						.getTipoCalificacionClienteList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TipoCalificacionCliente tipoCalificacionCliente : listaTipoCalificacionCliente) {
					modelo.addRow(agregarDatosAFila(tipoCalificacionCliente));
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
		btnNuevo.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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
		btnBuscar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);
	}

}
