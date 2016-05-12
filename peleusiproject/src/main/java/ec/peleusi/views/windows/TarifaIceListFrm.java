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
import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.models.entities.TarifaIce;
import java.awt.Font;

public class TarifaIceListFrm extends JInternalFrame {

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
	private JTable tblTarifaIce;
	private TarifaIceCrudFrm tarifaIceCrudFrm = new TarifaIceCrudFrm();

	public TarifaIceListFrm() {
		setTitle("Lista de las Tarifas ICE");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Còdigo", "Nombre", "Porcentaje" };
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
		tblTarifaIce = new JTable(modelo) {

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
		tblTarifaIce.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTarifaIce.setPreferredScrollableViewportSize(tblTarifaIce.getPreferredSize());
		tblTarifaIce.getTableHeader().setReorderingAllowed(true);
		tblTarifaIce.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTarifaIce.getColumnModel().getColumn(0).setMinWidth(0);
		tblTarifaIce.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTarifaIce.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblTarifaIce.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblTarifaIce.getColumnModel().getColumn(3).setPreferredWidth(143);
		scrollPane.setViewportView(tblTarifaIce);

	}

	private Object[] agregarDatosAFila(TarifaIce tarifaIce) {
		filaDatos[0] = tarifaIce.getId();
		filaDatos[1] = tarifaIce.getCodigo();
		filaDatos[2] = tarifaIce.getNombre();
		filaDatos[3] = tarifaIce.getPorcentaje();
		return filaDatos;
	}

	private void cargarTabla() {
		TarifaIceController tarifaIceController = new TarifaIceController();
		List<TarifaIce> listaTarifaIce = tarifaIceController.tarifaIceList();
		for (TarifaIce tarifaIce : listaTarifaIce) {
			modelo.addRow(agregarDatosAFila(tarifaIce));
		}
	}

	private void capturaYAgregaTarifaIceATabla() {
		TarifaIce tarifaIce = new TarifaIce();
		tarifaIce = tarifaIceCrudFrm.getTarifaIce();
		if (tarifaIce != null && tarifaIce.getId() != null) {
			modelo.addRow(agregarDatosAFila(tarifaIce));
			tblTarifaIce.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	private void crearEventos() {
		tarifaIceCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTarifaIceATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!tarifaIceCrudFrm.isVisible()) {
					tarifaIceCrudFrm.setModal(true);
					tarifaIceCrudFrm.setVisible(true);
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
				TarifaIceController tarifaIceController = new TarifaIceController();
				List<TarifaIce> listaTarifaIce = tarifaIceController.getTarifaIceList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TarifaIce tarifaIce : listaTarifaIce) {
					modelo.addRow(agregarDatosAFila(tarifaIce));
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
		btnNuevo.setIcon(new ImageIcon(TarifaIceListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TarifaIceListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TarifaIceListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TarifaIceListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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
		btnBuscar.setIcon(new ImageIcon(TarifaIceListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);
	}

}
