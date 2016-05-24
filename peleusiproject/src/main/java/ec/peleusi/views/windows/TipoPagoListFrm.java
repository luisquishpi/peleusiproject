package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import ec.peleusi.controllers.TipoPagoController;
import ec.peleusi.models.entities.TipoPago;

public class TipoPagoListFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;
	private JTable tblTipoPago;
	private Object[] filaDatos;
	private JScrollPane ScrollPane;
	private TipoPagoCrudFrm tipoPagoCrudFrm = new TipoPagoCrudFrm();
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	private TipoPago tipoPago;

	public TipoPagoListFrm() {
		setTitle("Listado Tipo de Pago");
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
		tblTipoPago = new JTable(modelo) {

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
		tblTipoPago.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTipoPago.setPreferredScrollableViewportSize(tblTipoPago.getPreferredSize());
		tblTipoPago.getTableHeader().setReorderingAllowed(true);
		tblTipoPago.getColumnModel().getColumn(0).setMinWidth(0);
		tblTipoPago.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTipoPago.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTipoPago.getColumnModel().getColumn(1).setPreferredWidth(443);
		ScrollPane.setViewportView(tblTipoPago);
	}

	private Object[] agregarDatosAFila(TipoPago tipoPago) {
		filaDatos[0] = tipoPago.getId();
		filaDatos[1] = tipoPago.getNombre();
		return filaDatos;
	}

	public void cargarTabla() {
		TipoPagoController tipoPagoController = new TipoPagoController();
		List<TipoPago> listaTipoPago = tipoPagoController.tipoPagoList();
		for (TipoPago tipoPago : listaTipoPago) {
			modelo.addRow(agregarDatosAFila(tipoPago));
		}
	}

	private void capturaYAgregaTipoPagoATabla() {
		if (tipoPago == tipoPagoCrudFrm.getTipoPago() && tipoPago.getId() != null) {
			modelo.setValueAt(tipoPago.getNombre(), tblTipoPago.getSelectedRow(), 1);
		} else {
			if (tipoPagoCrudFrm.getTipoPago() != null && tipoPagoCrudFrm.getTipoPago().getId() != null) {
				modelo.addRow(agregarDatosAFila(tipoPagoCrudFrm.getTipoPago()));
				tblTipoPago.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
			}
		}
	}

	private boolean llenarEntidadParaEnviarATipoPagoCrudFrm() {
		tipoPago = new TipoPago();
		if (tblTipoPago.getSelectedRow() != -1) {
			tipoPago.setId(Integer.parseInt(modelo.getValueAt(tblTipoPago.getSelectedRow(), 0).toString()));
			tipoPago.setNombre(modelo.getValueAt(tblTipoPago.getSelectedRow(), 1).toString());
			tipoPagoCrudFrm.setTipoPago(tipoPago);
			return true;
		}
		return false;
	}

	private void eliminarTipoPago() {
		if (llenarEntidadParaEnviarATipoPagoCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoPago.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoPagoController tipoPagoController = new TipoPagoController();
				String error = tipoPagoController.deleteTipoPago(tipoPago);
				if (error == null) {
					modelo.removeRow(tblTipoPago.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void crearEventos() {
		tipoPagoCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoPagoATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoPago = new TipoPago();
				tipoPagoCrudFrm.setTipoPago(tipoPago);
				if (!tipoPagoCrudFrm.isVisible()) {
					tipoPagoCrudFrm.setModal(true);
					tipoPagoCrudFrm.setVisible(true);
				}
			}
		});

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoPagoCrudFrm()) {
					if (!tipoPagoCrudFrm.isVisible()) {
						tipoPagoCrudFrm.setModal(true);
						tipoPagoCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoPago();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoPagoController tipoPagoController = new TipoPagoController();
				List<TipoPago> listaTipoPago = tipoPagoController.getTipoPagoList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TipoPago tipoPago : listaTipoPago) {
					modelo.addRow(agregarDatosAFila(tipoPago));
				}
			}
		});
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 378);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(432, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 8, 446, 41);
		panelCuerpo.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		ScrollPane = new JScrollPane();
		ScrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(ScrollPane);
	}
}
