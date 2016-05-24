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
import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.TarifaIva;
import java.awt.Font;

public class TarifaIvaListFrm extends JInternalFrame {

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
	private JTable tblTarifaIva;
	private TarifaIvaCrudFrm tarifaIvaCrudFrm = new TarifaIvaCrudFrm();
	private TarifaIva tarifaIva;

	public TarifaIvaListFrm() {
		setTitle("Lista de las Tarifas IVA");
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
		tblTarifaIva = new JTable(modelo) {

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
		tblTarifaIva.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTarifaIva.setPreferredScrollableViewportSize(tblTarifaIva.getPreferredSize());
		tblTarifaIva.getTableHeader().setReorderingAllowed(true);
		tblTarifaIva.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTarifaIva.getColumnModel().getColumn(0).setMinWidth(0);
		tblTarifaIva.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTarifaIva.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblTarifaIva.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblTarifaIva.getColumnModel().getColumn(3).setPreferredWidth(143);
		scrollPane.setViewportView(tblTarifaIva);

	}

	private Object[] agregarDatosAFila(TarifaIva tarifaIva) {
		filaDatos[0] = tarifaIva.getId();
		filaDatos[1] = tarifaIva.getCodigo();
		filaDatos[2] = tarifaIva.getNombre();
		filaDatos[3] = tarifaIva.getPorcentaje();
		return filaDatos;
	}

	private void cargarTabla() {
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		List<TarifaIva> listaTarifaIva = tarifaIvaController.TarifaIvaList();
		for (TarifaIva tarifaIva : listaTarifaIva) {
			modelo.addRow(agregarDatosAFila(tarifaIva));
		}
	}

	private void capturaYAgregaTarifaIvaATabla() {
		if (tarifaIva == tarifaIvaCrudFrm.getTarifaIva() && tarifaIva.getId() != null) {
			modelo.setValueAt(tarifaIva.getCodigo(), tblTarifaIva.getSelectedRow(), 1);
			modelo.setValueAt(tarifaIva.getNombre(), tblTarifaIva.getSelectedRow(), 2);
			modelo.setValueAt(tarifaIva.getPorcentaje(), tblTarifaIva.getSelectedRow(), 3);
		} else {
			if (tarifaIvaCrudFrm.getTarifaIva() != null && tarifaIvaCrudFrm.getTarifaIva().getId() != null) {
				modelo.addRow(agregarDatosAFila(tarifaIvaCrudFrm.getTarifaIva()));
				tblTarifaIva.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
			}
		}
	}

	private boolean llenarEntidadParaEnviarATarifaIvaCrudFrm() {
		tarifaIva = new TarifaIva();
		if (tblTarifaIva.getSelectedRow() != -1) {
			tarifaIva.setId(Integer.parseInt(modelo.getValueAt(tblTarifaIva.getSelectedRow(), 0).toString()));
			tarifaIva.setCodigo(modelo.getValueAt(tblTarifaIva.getSelectedRow(), 1).toString());
			tarifaIva.setNombre(modelo.getValueAt(tblTarifaIva.getSelectedRow(), 2).toString());
			tarifaIva.setPorcentaje(Double.parseDouble(modelo.getValueAt(tblTarifaIva.getSelectedRow(), 3).toString()));
			tarifaIvaCrudFrm.setTarifaIva(tarifaIva);
			return true;
		}
		return false;
	}
	
	private void eliminarTarifaIva(){
		
		if (llenarEntidadParaEnviarATarifaIvaCrudFrm()){
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tarifaIva.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TarifaIvaController tarifaiIvaController = new TarifaIvaController();
				String error = tarifaiIvaController.deleteTarifaIva(tarifaIva);
				if (error == null) {
					modelo.removeRow(tblTarifaIva.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}			
		}		
	}
	

	private void crearEventos() {
		tarifaIvaCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTarifaIvaATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tarifaIva = new TarifaIva();
				tarifaIvaCrudFrm.setTarifaIva(tarifaIva);
				if (!tarifaIvaCrudFrm.isVisible()) {
					tarifaIvaCrudFrm.setModal(true);
					tarifaIvaCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATarifaIvaCrudFrm()) {
					if (!tarifaIvaCrudFrm.isVisible()) {
						tarifaIvaCrudFrm.setModal(true);
						tarifaIvaCrudFrm.setVisible(true);
					}
				}
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTarifaIva();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TarifaIvaController tarifaIvaController = new TarifaIvaController();
				List<TarifaIva> listaTarifaIva = tarifaIvaController.getTarifaIvaList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TarifaIva tarifaIva : listaTarifaIva) {
					modelo.addRow(agregarDatosAFila(tarifaIva));
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
		btnNuevo.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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
		btnBuscar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);
	}

}
