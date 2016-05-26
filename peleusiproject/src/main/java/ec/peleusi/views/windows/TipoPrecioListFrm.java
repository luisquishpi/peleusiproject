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
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.TipoPrecio;

import java.awt.Font;

public class TipoPrecioListFrm extends JInternalFrame {

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
	private JTable tblTipoPrecio;
	private TipoPrecioCrudFrm tipoPrecioCrudFrm = new TipoPrecioCrudFrm();
	private TipoPrecio tipoPrecio;

	public TipoPrecioListFrm() {
		setTitle("Lista de Tipo Precio");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Nombre", "Porcentaje" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblTipoPrecio = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return Double.class;
				default:
					return String.class;
				}
			}
		};
		tblTipoPrecio.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTipoPrecio.setPreferredScrollableViewportSize(tblTipoPrecio.getPreferredSize());
		tblTipoPrecio.getTableHeader().setReorderingAllowed(true);
		tblTipoPrecio.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTipoPrecio.getColumnModel().getColumn(0).setMinWidth(0);
		tblTipoPrecio.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTipoPrecio.getColumnModel().getColumn(1).setPreferredWidth(250);
		tblTipoPrecio.getColumnModel().getColumn(2).setPreferredWidth(193);
		scrollPane.setViewportView(tblTipoPrecio);

	}

	private Object[] agregarDatosAFila(TipoPrecio tipoPrecio) {
		filaDatos[0] = tipoPrecio.getId();
		filaDatos[1] = tipoPrecio.getNombre();
		filaDatos[2] = tipoPrecio.getPorcentaje();
		return filaDatos;
	}

	private void cargarTabla() {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		List<TipoPrecio> listaTipoPrecio = tipoPrecioController.tipoPrecioList();
		for (TipoPrecio tipoPrecio : listaTipoPrecio) {
			modelo.addRow(agregarDatosAFila(tipoPrecio));
		}
	}

	private void capturaYAgregaTipoPrecioATabla() {
		if (tipoPrecio == tipoPrecioCrudFrm.getTipoPrecio() && tipoPrecio.getId() != null) {
			modelo.setValueAt(tipoPrecio.getNombre(), tblTipoPrecio.getSelectedRow(), 1);
			modelo.setValueAt(tipoPrecio.getPorcentaje(), tblTipoPrecio.getSelectedRow(), 2);
		} else {
			if (tipoPrecioCrudFrm.getTipoPrecio() != null && tipoPrecioCrudFrm.getTipoPrecio().getId() != null) {
				modelo.addRow(agregarDatosAFila(tipoPrecioCrudFrm.getTipoPrecio()));
				tblTipoPrecio.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
			}
		}
	}
	private boolean llenarEntidadParaEnviarATipoPrecioCrudFrm() {
		tipoPrecio= new TipoPrecio();
		if (tblTipoPrecio.getSelectedRow() != -1) {
			tipoPrecio.setId(Integer.parseInt(modelo.getValueAt(tblTipoPrecio.getSelectedRow(), 0).toString()));
			tipoPrecio.setNombre(modelo.getValueAt(tblTipoPrecio.getSelectedRow(), 1).toString());
			tipoPrecio.setPorcentaje(Double.parseDouble(modelo.getValueAt(tblTipoPrecio.getSelectedRow(), 2).toString()));
			tipoPrecioCrudFrm.setTipoPrecio(tipoPrecio);
			return true;
		}
		return false;
	}
	
	private void eliminarTipoPrecio(){		
		if (llenarEntidadParaEnviarATipoPrecioCrudFrm()){
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoPrecio.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoPrecioController tipoPrecioController = new TipoPrecioController();
				String error = tipoPrecioController.deleteTipoPrecio(tipoPrecio);
				if (error == null) {
					modelo.removeRow(tblTipoPrecio.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}			
		}		
	}
	private void crearEventos() {
		tipoPrecioCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			capturaYAgregaTipoPrecioATabla();
			}
	});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoPrecio = new TipoPrecio();
				tipoPrecioCrudFrm.setTipoPrecio(tipoPrecio);
				if (!tipoPrecioCrudFrm.isVisible()) {
					tipoPrecioCrudFrm.setModal(true);
					tipoPrecioCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoPrecioCrudFrm()) {
					if (!tipoPrecioCrudFrm.isVisible()) {
						tipoPrecioCrudFrm.setModal(true);
						tipoPrecioCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoPrecio();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoPrecioController tipoPrecioController = new TipoPrecioController();
				List<TipoPrecio> listaTipoPrecio = tipoPrecioController.getTipoPrecioList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TipoPrecio tipoPrecio : listaTipoPrecio) {
					modelo.addRow(agregarDatosAFila(tipoPrecio));
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
		btnNuevo.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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
		btnBuscar.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);
	}

}
