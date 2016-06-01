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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.UnidadMedida;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class UnidadMedidaListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JButton btnBuscar;
	private UnidadMedidaCrudFrm unidadMedidaCrudFrm = new UnidadMedidaCrudFrm();
	private UnidadMedida unidadMedida;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<UnidadMedida> jPanelWithTable;

	public UnidadMedidaListFrm() {
		setTitle("Listado de Unidad de Medida");
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
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		List<UnidadMedida> listaUnidadMedida = unidadMedidaController.getUnidadMedidaList(txtBuscar.getText());

		if (totalItems == 0 && listaUnidadMedida != null)
			totalItems = listaUnidadMedida.size();

		jPanelWithTable = new JPanelWithTable<UnidadMedida>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre", "abreviatura" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 400, 198 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRE", "ABREVIATURA" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaUnidadMedida), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}

	private void capturaYAgregaUnidadMedidaATabla() {
		if (unidadMedida == unidadMedidaCrudFrm.getUnidadMedida() && unidadMedida.getId() != null) {
			crearTabla();
		} else {
			if (unidadMedidaCrudFrm.getUnidadMedida() != null
					&& unidadMedidaCrudFrm.getUnidadMedida().getId() != null) {
				totalItems++;
				txtBuscar.setText(unidadMedidaCrudFrm.getUnidadMedida().getNombre());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarAUnidadMedidaCrudFrm() {
		unidadMedida = new UnidadMedida();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			unidadMedida.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			unidadMedida.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			unidadMedida.setAbreviatura(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString());
			unidadMedidaCrudFrm.setUnidadMedida(unidadMedida);
			return true;
		}
		return false;
	}

	private void eliminarUnidadMedida() {
		if (llenarEntidadParaEnviarAUnidadMedidaCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + unidadMedida.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
				String error = unidadMedidaController.deleteUnidadMedida(unidadMedida);
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
		unidadMedidaCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaUnidadMedidaATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				unidadMedida = new UnidadMedida();
				unidadMedidaCrudFrm.setUnidadMedida(unidadMedida);
				if (!unidadMedidaCrudFrm.isVisible()) {
					unidadMedidaCrudFrm.setModal(true);
					unidadMedidaCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarAUnidadMedidaCrudFrm()) {
					if (!unidadMedidaCrudFrm.isVisible()) {
						unidadMedidaCrudFrm.setModal(true);
						unidadMedidaCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarUnidadMedida();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		setBounds(100, 100, 611, 379);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(UnidadMedidaListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(UnidadMedidaListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar
				.setIcon(new ImageIcon(UnidadMedidaListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(UnidadMedidaListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(450, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));

		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));

		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba nombre o abreviatura");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(UnidadMedidaListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);

		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
	}
}
