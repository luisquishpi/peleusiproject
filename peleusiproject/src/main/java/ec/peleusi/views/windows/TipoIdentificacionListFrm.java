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
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.models.entities.TipoIdentificacion;

import java.awt.Font;

public class TipoIdentificacionListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtBuscar;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private JTable tblTipoIdentificacion;
	private TipoIdentificacionCrudFrm tipoIdentificacionCrudFrm = new TipoIdentificacionCrudFrm();
	private JButton btnBuscar;

	public TipoIdentificacionListFrm() {
		setTitle("Lista del Tipo de Identificaciòn");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Còdigo", "Nombre", "Valida" };
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
		tblTipoIdentificacion = new JTable(modelo) {

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
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		tblTipoIdentificacion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTipoIdentificacion.setPreferredScrollableViewportSize(tblTipoIdentificacion.getPreferredSize());
		tblTipoIdentificacion.getTableHeader().setReorderingAllowed(true);
		tblTipoIdentificacion.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTipoIdentificacion.getColumnModel().getColumn(0).setMinWidth(0);
		tblTipoIdentificacion.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTipoIdentificacion.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblTipoIdentificacion.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblTipoIdentificacion.getColumnModel().getColumn(3).setPreferredWidth(143);
		scrollPane.setViewportView(tblTipoIdentificacion);

	}

	private Object[] agregarDatosAFila(TipoIdentificacion tipoIdentificacion) {
		filaDatos[0] = tipoIdentificacion.getId();
		filaDatos[1] = tipoIdentificacion.getCodigo();
		filaDatos[2] = tipoIdentificacion.getNombre();
		filaDatos[3] = tipoIdentificacion.getValida();
		return filaDatos;
	}

	private void cargarTabla() {
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		List<TipoIdentificacion> listaTipoIdentificacion = tipoIdentificacionController.tipoIdentificacionList();
		for (TipoIdentificacion tipoIdentificacion : listaTipoIdentificacion) {
			modelo.addRow(agregarDatosAFila(tipoIdentificacion));
		}
	}

	private void capturaYAgregaTipoIdentificacionATabla() {
		TipoIdentificacion tipoIdentificacion = new TipoIdentificacion();
		tipoIdentificacion = tipoIdentificacionCrudFrm.getTipoIdentificacion();
		if (tipoIdentificacion != null && tipoIdentificacion.getId() != null) {
			modelo.addRow(agregarDatosAFila(tipoIdentificacion));
			tblTipoIdentificacion.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	private void crearEventos() {
		tipoIdentificacionCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoIdentificacionATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!tipoIdentificacionCrudFrm.isVisible()) {
					tipoIdentificacionCrudFrm.setModal(true);
					tipoIdentificacionCrudFrm.setVisible(true);
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
			public void actionPerformed(ActionEvent arg0) {
				TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
				List<TipoIdentificacion> listaTipoIdentificacion = tipoIdentificacionController
						.getTipoIdentificacionList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TipoIdentificacion tipoIdentificacion : listaTipoIdentificacion) {
					modelo.addRow(agregarDatosAFila(tipoIdentificacion));					
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
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 9, 119, 41);
		panelCuerpo.add(btnBuscar);
	}
}
