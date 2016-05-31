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
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class CiudadListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private CiudadCrudFrm ciudadCrudFrm = new CiudadCrudFrm();
	private Ciudad ciudad;
	private JPanel panel;
	private JPanel panel_1;
	private JButton btnBuscar;
	private Integer totalItems = 0;
	private JPanel panel_3;
	JPanelWithTable<Ciudad> jPanelWithTable;

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
		List<Ciudad> listaCiudad = ciudadController.ciudadList(txtBuscar.getText());
		if (totalItems == 0 && listaCiudad != null)
			totalItems = listaCiudad.size();
		
		jPanelWithTable = new JPanelWithTable<Ciudad>();
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 441 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "CIUDADES" };

		panel_3.removeAll();
		panel_3.add(jPanelWithTable.crear(cabecera, listaCiudad), BorderLayout.CENTER);
		panel_3.revalidate();
		panel_3.repaint();
		txtBuscar.requestFocus();

	}

	private void capturaYAgregaCiudadATabla() {
		if (ciudad == ciudadCrudFrm.getCiudad() && ciudad.getId() != null) {
			txtBuscar.setText(ciudad.getNombre());
			crearTabla();
		} else {
			if (ciudadCrudFrm.getCiudad() != null && ciudadCrudFrm.getCiudad().getId() != null) {
				totalItems++;
				txtBuscar.setText(ciudadCrudFrm.getCiudad().getNombre());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarACiudadCrudFrm() {
		ciudad = new Ciudad();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			ciudad.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			ciudad.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
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
					totalItems--;
					crearTabla();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
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
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearTabla();
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

		panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

		txtBuscar = new JTextFieldPH();
		txtBuscar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtBuscar.setColumns(10);
		txtBuscar.setPlaceholder("Buscar");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		panel_1.add(txtBuscar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(CiudadListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		panel_1.add(btnBuscar);

		panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

	}
}
