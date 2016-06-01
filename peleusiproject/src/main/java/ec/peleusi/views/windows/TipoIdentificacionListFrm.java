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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.models.entities.TipoIdentificacion;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class TipoIdentificacionListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private TipoIdentificacionCrudFrm tipoIdentificacionCrudFrm = new TipoIdentificacionCrudFrm();
	private JButton btnBuscar;
	private TipoIdentificacion tipoIdentificacion;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<TipoIdentificacion> jPanelWithTable;

	public TipoIdentificacionListFrm() {
		setTitle("Listado de los Tipos de Identificación");
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
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		List<TipoIdentificacion> listaTipoIdentificacion = tipoIdentificacionController
				.getTipoIdentificacionList(txtBuscar.getText());

		if (totalItems == 0 && listaTipoIdentificacion != null)
			totalItems = listaTipoIdentificacion.size();

		jPanelWithTable = new JPanelWithTable<TipoIdentificacion>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "codigo", "nombre", "valida" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 150, 302, 120 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTipoColumnas(new Class[] { Integer.class, String.class, String.class, Boolean.class });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "CODIGO", "NOMBRE", "VALIDA" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaTipoIdentificacion), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}

	private void capturaYAgregaTipoIdentificacionATabla() {
		if (tipoIdentificacion == tipoIdentificacionCrudFrm.getTipoIdentificacion()
				&& tipoIdentificacion.getId() != null) {
			crearTabla();
		} else {
			if (tipoIdentificacionCrudFrm.getTipoIdentificacion() != null
					&& tipoIdentificacionCrudFrm.getTipoIdentificacion().getId() != null) {
				totalItems++;
				txtBuscar.setText(tipoIdentificacionCrudFrm.getTipoIdentificacion().getNombre());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarATipoIdentificacionCrudFrm() {
		tipoIdentificacion = new TipoIdentificacion();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			tipoIdentificacion.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			tipoIdentificacion.setCodigo(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			tipoIdentificacion.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString());
			tipoIdentificacion.setValida(
					(Boolean) jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 3));
			tipoIdentificacionCrudFrm.setTipoIdentificacion(tipoIdentificacion);
			return true;
		}
		return false;
	}

	private void eliminarTipoIdentificacion() {
		if (llenarEntidadParaEnviarATipoIdentificacionCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoIdentificacion.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
				String error = tipoIdentificacionController.deleteTipoIdentificacion(tipoIdentificacion);
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
		tipoIdentificacionCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoIdentificacionATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoIdentificacion = new TipoIdentificacion();
				tipoIdentificacionCrudFrm.setTipoIdentificacion(tipoIdentificacion);
				if (!tipoIdentificacionCrudFrm.isVisible()) {
					tipoIdentificacionCrudFrm.setModal(true);
					tipoIdentificacionCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoIdentificacionCrudFrm()) {
					if (!tipoIdentificacionCrudFrm.isVisible()) {
						tipoIdentificacionCrudFrm.setModal(true);
						tipoIdentificacionCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoIdentificacion();
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
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					crearTabla();
					if (jPanelWithTable.getJTable() != null) {
						jPanelWithTable.getJTable().addRowSelectionInterval(0, 0);
						jPanelWithTable.getJTable().requestFocus();
					}
				}
			}
		});
	}

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 585, 387);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));

		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));

		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba código o nombre");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(
				new ImageIcon(TipoIdentificacionListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);

		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
	}
}
