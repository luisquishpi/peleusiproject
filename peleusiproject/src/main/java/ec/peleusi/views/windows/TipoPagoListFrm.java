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
import ec.peleusi.controllers.TipoPagoController;
import ec.peleusi.models.entities.TipoPago;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class TipoPagoListFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JButton btnBuscar;
	private TipoPagoCrudFrm tipoPagoCrudFrm = new TipoPagoCrudFrm();
	private TipoPago tipoPago;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<TipoPago> jPanelWithTable;

	public TipoPagoListFrm() {
		setTitle("Listado Tipo de Pago");
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
		TipoPagoController tipoPagoController = new TipoPagoController();
		List<TipoPago> listaTipoPago = tipoPagoController.getTipoPagoList(txtBuscar.getText());

		if (totalItems == 0 && listaTipoPago != null)
			totalItems = listaTipoPago.size();

		jPanelWithTable = new JPanelWithTable<TipoPago>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 597 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRE" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaTipoPago), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}

	private void capturaYAgregaTipoPagoATabla() {
		if (tipoPago == tipoPagoCrudFrm.getTipoPago() && tipoPago.getId() != null) {
			txtBuscar.setText(tipoPago.getNombre());
			crearTabla();
		} else {
			if (tipoPagoCrudFrm.getTipoPago() != null && tipoPagoCrudFrm.getTipoPago().getId() != null) {
				totalItems++;
				txtBuscar.setText(tipoPagoCrudFrm.getTipoPago().getNombre());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarATipoPagoCrudFrm() {
		tipoPago = new TipoPago();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			tipoPago.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			tipoPago.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			tipoPagoCrudFrm.setTipoPago(tipoPago);
			return true;
		}
		return false;
	}

	private void eliminarTipoPago() {
		if (llenarEntidadParaEnviarATipoPagoCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoPago.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoPagoController tipoPagoController = new TipoPagoController();
				String error = tipoPagoController.deleteTipoPago(tipoPago);
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
		tipoPagoCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoPagoATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipoPago = new TipoPago();
				tipoPagoCrudFrm.setTipoPago(tipoPago);
				if (!tipoPagoCrudFrm.isVisible()) {
					tipoPagoCrudFrm.setModal(true);
					tipoPagoCrudFrm.setVisible(true);
				}
			}
		});

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoPagoCrudFrm()) {
					if (!tipoPagoCrudFrm.isVisible()) {
						tipoPagoCrudFrm.setModal(true);
						tipoPagoCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoPago();
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
		setBounds(100, 100, 611, 378);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
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
		btnBuscar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);

		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
	}
}
