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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.utils.Filtros;
import ec.peleusi.utils.JTableCustomized;
import ec.peleusi.utils.JTextFieldPH;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CiudadListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private JTable tblCiudad;
	private CiudadCrudFrm ciudadCrudFrm = new CiudadCrudFrm();
	private Ciudad ciudad;

	public CiudadListFrm() {
		setTitle("Listado de Ciudades");
		crearControles();
		crearEventos();
		crearTabla();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				txtBuscar.requestFocus();
			}
		});
	}

	private void crearTabla() {
		CiudadController ciudadController = new CiudadController();
		List<Ciudad> listaCiudad = ciudadController.ciudadList();
		String[] cabecera = new String[] { "ID", "NOMBRE DE LA CIUDAD" };
		JTableCustomized<Ciudad> tblCiudadCustomized = new JTableCustomized<>();
		tblCiudadCustomized.setAnchoColumnas(new Integer[] { 0, 441 });
		tblCiudadCustomized.setColumnasFijas(new Integer[] { 0 });
		tblCiudadCustomized.setCamposEntidad(new String[] { "id", "nombre" });

		tblCiudad = tblCiudadCustomized.crearTabla(cabecera, listaCiudad);
		modelo = (DefaultTableModel) tblCiudad.getModel();
		tblCiudad.setRowSorter(new TableRowSorter<TableModel>(modelo));
		filaDatos = new Object[cabecera.length];
		scrollPane.setViewportView(tblCiudad);
	}

	private Object[] agregarDatosAFila(Ciudad ciudad) {
		filaDatos[0] = ciudad.getId();
		filaDatos[1] = ciudad.getNombre();
		return filaDatos;
	}

	private void capturaYAgregaCiudadATabla() {
		if (ciudad == ciudadCrudFrm.getCiudad() && ciudad.getId() != null) {
			tblCiudad.setValueAt(ciudad.getNombre(), tblCiudad.getSelectedRow(), 1);
			txtBuscar.setText(ciudad.getNombre());
		} else {
			if (ciudadCrudFrm.getCiudad() != null && ciudadCrudFrm.getCiudad().getId() != null) {
				modelo.addRow(agregarDatosAFila(ciudadCrudFrm.getCiudad()));
				txtBuscar.setText(ciudadCrudFrm.getCiudad().getNombre());
			}
		}
		filtrarTabla();
		if (tblCiudad.getRowCount() > 0)
			tblCiudad.setRowSelectionInterval(tblCiudad.getRowCount() - 1, tblCiudad.getRowCount() - 1);
	}

	private boolean llenarEntidadParaEnviarACiudadCrudFrm() {
		ciudad = new Ciudad();
		if (tblCiudad.getSelectedRow() != -1) {
			ciudad.setId(Integer.parseInt(tblCiudad.getValueAt(tblCiudad.getSelectedRow(), 0).toString()));
			ciudad.setNombre(tblCiudad.getValueAt(tblCiudad.getSelectedRow(), 1).toString());
			ciudadCrudFrm.setCiudad(ciudad);
			return true;
		}
		return false;
	}

	private void eliminarCiudad() {
		if (llenarEntidadParaEnviarACiudadCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + ciudad.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				CiudadController ciudadController = new CiudadController();
				String error = ciudadController.deleteCiudad(ciudad);
				if (error == null) {
					crearTabla();
					filtrarTabla();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private void filtrarTabla() {
		Filtros.filterModel(tblCiudad, modelo, txtBuscar.getText(), new int[] { 1 });
		txtBuscar.requestFocus();
	}

	private void crearEventos() {
		ciudadCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaCiudadATabla();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!ciudadCrudFrm.isVisible()) {
					ciudad = new Ciudad();
					ciudadCrudFrm.setCiudad(ciudad);
					ciudadCrudFrm.setModal(true);
					ciudadCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarACiudadCrudFrm()) {
					if (!ciudadCrudFrm.isVisible()) {
						ciudadCrudFrm.setModal(true);
						ciudadCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarCiudad();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrarTabla();
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
		btnNuevo.setIcon(new ImageIcon(CiudadListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(CiudadListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(CiudadListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(CiudadListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		txtBuscar = new JTextFieldPH();
		txtBuscar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBuscar.setBounds(10, 8, 575, 41);
		txtBuscar.setPlaceholder("Buscar");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		panelCuerpo.add(txtBuscar);
		txtBuscar.setColumns(10);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 575, 208);
		panelCuerpo.add(scrollPane);

	}
}
