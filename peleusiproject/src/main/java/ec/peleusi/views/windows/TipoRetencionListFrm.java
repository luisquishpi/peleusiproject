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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ec.peleusi.controllers.TipoRetencionController;
import ec.peleusi.models.entities.TipoRetencion;

public class TipoRetencionListFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;
	private JTable tblTipoRetencion;
	private Object[] filaDatos;
	private JScrollPane scrollPane;
	private TipoRetencionCrudFrm tipoRetencionCrudFrm = new TipoRetencionCrudFrm();
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	private TipoRetencion tipoRetencion;

	public TipoRetencionListFrm() {
		setTitle("Listado Tipos de Retencion");
		setClosable(true);
		crearControles();
		crearEventos();
		crearTabla();
		cargarTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Código", "Descripcion", "Porcentaje", "Tipo" };
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
		tblTipoRetencion = new JTable(modelo);
		tblTipoRetencion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblTipoRetencion.setPreferredScrollableViewportSize(tblTipoRetencion.getPreferredSize());
		tblTipoRetencion.getTableHeader().setReorderingAllowed(true);
		tblTipoRetencion.getColumnModel().getColumn(0).setMaxWidth(0);
		tblTipoRetencion.getColumnModel().getColumn(0).setMinWidth(0);
		tblTipoRetencion.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblTipoRetencion.getColumnModel().getColumn(1).setPreferredWidth(120);
		tblTipoRetencion.getColumnModel().getColumn(2).setPreferredWidth(350);
		tblTipoRetencion.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblTipoRetencion.getColumnModel().getColumn(4).setPreferredWidth(100);
		scrollPane.setViewportView(tblTipoRetencion);
	}

	private Object[] agregarDatosAFila(TipoRetencion tipoRetencion) {
		filaDatos[0] = tipoRetencion.getId();
		filaDatos[1] = tipoRetencion.getCodigo();
		filaDatos[2] = tipoRetencion.getDescripcion();
		filaDatos[3] = tipoRetencion.getPorcentaje();
		filaDatos[4] = tipoRetencion.getTipoRetencionEnum();
		return filaDatos;
	}

	public void cargarTabla() {
		TipoRetencionController tipoRetencionController = new TipoRetencionController();
		List<TipoRetencion> listaTipoRetencion = tipoRetencionController.tipoRetencionList();
		for (TipoRetencion tipoRetencion : listaTipoRetencion) {
			modelo.addRow(agregarDatosAFila(tipoRetencion));
		}
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 742, 379);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(122, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(262, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(406, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(546, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 8, 577, 41);
		panelCuerpo.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(597, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 706, 208);
		panelCuerpo.add(scrollPane);
	}

	private void capturaYAgregaTipoRetencionATabla() {
		tipoRetencion = new TipoRetencion();
		tipoRetencion = tipoRetencionCrudFrm.getTipoRetencion();
		if (tipoRetencion != null && tipoRetencion.getId() != null) {
			System.out.println("Captura TipoRetencion retornado: " + tipoRetencion);
			modelo.addRow(agregarDatosAFila(tipoRetencion));
			tblTipoRetencion.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}

	}

	private void crearEventos() {
		tipoRetencionCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoRetencionATabla();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!tipoRetencionCrudFrm.isVisible()) {
					tipoRetencionCrudFrm.setModal(true);
					tipoRetencionCrudFrm.setVisible(true);
				}
			}
		});
		btnBuscar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				TipoRetencionController tipoRetencionController = new TipoRetencionController();
				List<TipoRetencion> listaTipoRetencion = tipoRetencionController.tipoRetencionList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (TipoRetencion tipoRetencion : listaTipoRetencion) {
					modelo.addRow(agregarDatosAFila(tipoRetencion));
				}

			}
		});
	}
}
