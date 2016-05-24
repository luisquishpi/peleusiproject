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
import ec.peleusi.controllers.TipoCalificacionPersonaController;
import ec.peleusi.models.entities.TipoCalificacionPersona;
import java.awt.Font;

public class TipoCalificacionPersonaListFrm extends JInternalFrame {

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
	private JTable tblTipoCalificacionPersona;
	private TipoCalificacionPersonaCrudFrm tipoCalificacionPersonaCrudFrm = new TipoCalificacionPersonaCrudFrm();
	private TipoCalificacionPersona tipoCalificacionPersona;

	public TipoCalificacionPersonaListFrm() {
		setTitle("Listado de Tipo Calificacion Persona");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Nombre"};
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
		tblTipoCalificacionPersona = new JTable(modelo) {

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
		tblTipoCalificacionPersona.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTipoCalificacionPersona.setPreferredScrollableViewportSize(tblTipoCalificacionPersona.getPreferredSize());
		tblTipoCalificacionPersona.getTableHeader().setReorderingAllowed(true);
		tblTipoCalificacionPersona.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTipoCalificacionPersona.getColumnModel().getColumn(0).setMinWidth(0);
		tblTipoCalificacionPersona.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTipoCalificacionPersona.getColumnModel().getColumn(1).setPreferredWidth(443);
		scrollPane.setViewportView(tblTipoCalificacionPersona);

	}

	private Object[] agregarDatosAFila(TipoCalificacionPersona tipoCalificacionPersona) {
		filaDatos[0] = tipoCalificacionPersona.getId();
		filaDatos[1] = tipoCalificacionPersona.getNombre();
		return filaDatos;
	}

	private void cargarTabla() {
		TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
		List<TipoCalificacionPersona> listaTipoCalificacionPersona = tipoCalificacionPersonaController.tipoCalificacionPersonaList();
		for (TipoCalificacionPersona tipoCalificacionPersona : listaTipoCalificacionPersona) {
			modelo.addRow(agregarDatosAFila(tipoCalificacionPersona));
		}
	}

	private void capturaYAgregaTipoCalificacionPersonaATabla() {
		if (tipoCalificacionPersona == tipoCalificacionPersonaCrudFrm.getTipoCalificacionPersona() && tipoCalificacionPersona.getId() != null) {
			modelo.setValueAt(tipoCalificacionPersona.getNombre(), tblTipoCalificacionPersona.getSelectedRow(), 1);
		} else {
			if (tipoCalificacionPersonaCrudFrm.getTipoCalificacionPersona() != null && tipoCalificacionPersonaCrudFrm.getTipoCalificacionPersona().getId() != null) {
				modelo.addRow(agregarDatosAFila(tipoCalificacionPersonaCrudFrm.getTipoCalificacionPersona()));
				tblTipoCalificacionPersona.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
			}
		}		
	}
	
	private boolean llenarEntidadParaEnviarATipoCalificacionPersonaCrudFrm() {
		tipoCalificacionPersona = new TipoCalificacionPersona();
		if (tblTipoCalificacionPersona.getSelectedRow() != -1) {
			tipoCalificacionPersona.setId(Integer.parseInt(modelo.getValueAt(tblTipoCalificacionPersona.getSelectedRow(), 0).toString()));
			tipoCalificacionPersona.setNombre(modelo.getValueAt(tblTipoCalificacionPersona.getSelectedRow(), 1).toString());
			tipoCalificacionPersonaCrudFrm.setTipoCalificacionPersona(tipoCalificacionPersona);
			return true;
		}
		return false;
	}
	
	private void eliminarTipoCalificacionPersona() {
		if (llenarEntidadParaEnviarATipoCalificacionPersonaCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoCalificacionPersona.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
				String error = tipoCalificacionPersonaController.deleteTipoCalificacionPersona(tipoCalificacionPersona);
				if (error == null) {
					modelo.removeRow(tblTipoCalificacionPersona.getSelectedRow());
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
	}

	private void crearEventos() {
		tipoCalificacionPersonaCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoCalificacionPersonaATabla();
			}
		});
		
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoCalificacionPersona = new TipoCalificacionPersona();
				tipoCalificacionPersonaCrudFrm.setTipoCalificacionPersona(tipoCalificacionPersona);
				if (!tipoCalificacionPersonaCrudFrm.isVisible()) {
					tipoCalificacionPersonaCrudFrm.setModal(true);
					tipoCalificacionPersonaCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoCalificacionPersonaCrudFrm()) {
					if (!tipoCalificacionPersonaCrudFrm.isVisible()) {
						tipoCalificacionPersonaCrudFrm.setModal(true);
						tipoCalificacionPersonaCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoCalificacionPersona();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
				List<TipoCalificacionPersona> listaTipoCalificacionPersona = tipoCalificacionPersonaController.getTipoCalificacionPersonaList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TipoCalificacionPersona tipoCalificacionPersona : listaTipoCalificacionPersona) {
					modelo.addRow(agregarDatosAFila(tipoCalificacionPersona));
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
		btnNuevo.setIcon(new ImageIcon(TipoCalificacionPersonaListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TipoCalificacionPersonaListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar
				.setIcon(new ImageIcon(TipoCalificacionPersonaListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(TipoCalificacionPersonaListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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
		btnBuscar.setIcon(new ImageIcon(TipoCalificacionPersonaListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);
	}

}
