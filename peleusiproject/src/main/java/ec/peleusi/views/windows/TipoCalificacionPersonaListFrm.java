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
		TipoCalificacionPersona tipoCalificacionPersona = new  TipoCalificacionPersona();
		tipoCalificacionPersona = tipoCalificacionPersonaCrudFrm.getTipoCalificacionPersona();
		if (tipoCalificacionPersona != null && tipoCalificacionPersona.getId() != null) {
			modelo.addRow(agregarDatosAFila(tipoCalificacionPersona));
			tblTipoCalificacionPersona.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
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
				if (!tipoCalificacionPersonaCrudFrm.isVisible()) {
					tipoCalificacionPersonaCrudFrm.setModal(true);
					tipoCalificacionPersonaCrudFrm.setVisible(true);
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
