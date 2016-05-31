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
import ec.peleusi.controllers.TipoCalificacionClienteController;
import ec.peleusi.models.entities.TipoCalificacionCliente;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class TipoCalificacionClienteListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JButton btnBuscar;
	private TipoCalificacionClienteCrudFrm tipoCalificacionClienteCrudFrm = new TipoCalificacionClienteCrudFrm();
	private TipoCalificacionCliente tipoCalificacionCliente;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<TipoCalificacionCliente> jPanelWithTable;

	public TipoCalificacionClienteListFrm() {
		setTitle("Listado de Tipo Calificación Cliente");
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
		TipoCalificacionClienteController tipoCalificacionClienteController = new TipoCalificacionClienteController();
		List<TipoCalificacionCliente> listaTipoCalificacionCliente = tipoCalificacionClienteController
				.getTipoCalificacionClienteList(txtBuscar.getText());

		if (totalItems == 0 && listaTipoCalificacionCliente != null)
			totalItems = listaTipoCalificacionCliente.size();

		jPanelWithTable = new JPanelWithTable<TipoCalificacionCliente>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 597 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRE" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaTipoCalificacionCliente), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}

	private void capturaYAgregaTipoCalificacionClienteATabla() {
		if (tipoCalificacionCliente == tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente()
				&& tipoCalificacionCliente.getId() != null) {
			txtBuscar.setText(tipoCalificacionCliente.getNombre());
			crearTabla();
		} else {
			if (tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente() != null
					&& tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente().getId() != null) {
				totalItems++;
				txtBuscar.setText(tipoCalificacionClienteCrudFrm.getTipoCalificacionCliente().getNombre());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarATipoCalificacionClienteCrudFrm() {
		tipoCalificacionCliente = new TipoCalificacionCliente();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			tipoCalificacionCliente.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			tipoCalificacionCliente.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			tipoCalificacionClienteCrudFrm.setTipoCalificacionCliente(tipoCalificacionCliente);
			return true;
		}
		return false;
	}

	private void eliminarTipoCalificacionCliente() {
		if (llenarEntidadParaEnviarATipoCalificacionClienteCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoCalificacionCliente.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoCalificacionClienteController tipoCalificacionClienteController = new TipoCalificacionClienteController();
				String error = tipoCalificacionClienteController.deleteTipoCalificacionCliente(tipoCalificacionCliente);
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
		tipoCalificacionClienteCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoCalificacionClienteATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoCalificacionCliente = new TipoCalificacionCliente();
				tipoCalificacionClienteCrudFrm.setTipoCalificacionCliente(tipoCalificacionCliente);
				if (!tipoCalificacionClienteCrudFrm.isVisible()) {
					tipoCalificacionClienteCrudFrm.setModal(true);
					tipoCalificacionClienteCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoCalificacionClienteCrudFrm()) {
					if (!tipoCalificacionClienteCrudFrm.isVisible()) {
						tipoCalificacionClienteCrudFrm.setModal(true);
						tipoCalificacionClienteCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoCalificacionCliente();
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
		btnNuevo.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 15, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(450, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));

		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));

		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba nombre");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(
				new ImageIcon(TipoCalificacionClienteListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);

		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
		
	}
}
