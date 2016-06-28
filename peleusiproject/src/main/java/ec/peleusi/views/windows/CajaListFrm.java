package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import ec.peleusi.controllers.CajaController;
import ec.peleusi.models.entities.Caja;
import ec.peleusi.models.entities.Sucursal;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import javax.swing.BoxLayout;

public class CajaListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JButton btnBuscar;
	private CajaCrudFrm cajaCrudFrm = new CajaCrudFrm();
	private Caja caja;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<Caja> jPanelWithTable;

	public CajaListFrm() {
		setTitle("Listado de Caja");
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
		CajaController cajaController = new CajaController();
		List<Caja> listaCaja = cajaController.getCajaList(txtBuscar.getText());

		if (totalItems == 0 && listaCaja != null)
			totalItems = listaCaja.size();

		jPanelWithTable = new JPanelWithTable<Caja>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre", "saldoInicial", "sucursal" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 308, 120, 170 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0, 1 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRE", "SALDO INICIAL", "SUCURSAL" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaCaja), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}

	private void capturaYAgregaCajaATabla() {
		if (caja == cajaCrudFrm.getCaja() && caja.getId() != null) {
			txtBuscar.setText(caja.getNombre());
			crearTabla();
		} else {
			if (cajaCrudFrm.getCaja() != null && cajaCrudFrm.getCaja().getId() != null) {
				totalItems++;
				txtBuscar.setText(cajaCrudFrm.getCaja().getNombre());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarACajaCrudFrm() {
		caja = new Caja();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			caja.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			caja.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			caja.setSaldoInicial(Double.parseDouble(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString()));
			caja.setSucursal((Sucursal)jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 3));
			cajaCrudFrm.setCaja(caja);
			return true;
		}
		return false;
	}

	private void eliminarCaja() {
		if (llenarEntidadParaEnviarACajaCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + caja.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				CajaController cajaController = new CajaController();
				String error = cajaController.deleteCaja(caja);
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
		cajaCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaCajaATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				caja = new Caja();
				cajaCrudFrm.setCaja(caja);
				if (!cajaCrudFrm.isVisible()) {
					cajaCrudFrm.setModal(true);
					cajaCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarACajaCrudFrm()) {
					if (!cajaCrudFrm.isVisible()) {
						cajaCrudFrm.setModal(true);
						cajaCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarCaja();
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
		btnNuevo.setIcon(new ImageIcon(CajaListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(CajaListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(CajaListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(CajaListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(450, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));

		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));

		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba nombre o saldo inicial");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(CajaListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);

		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
	}
}
