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
import ec.peleusi.controllers.TarifaIvaController;
import ec.peleusi.models.entities.TarifaIva;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class TarifaIvaListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JButton btnBuscar;
	private TarifaIvaCrudFrm tarifaIvaCrudFrm = new TarifaIvaCrudFrm();
	private TarifaIva tarifaIva;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<TarifaIva> jPanelWithTable;

	public TarifaIvaListFrm() {
		setTitle("Listado de Tarifas IVA");
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
		TarifaIvaController tarifaIvaController = new TarifaIvaController();
		List<TarifaIva> listaTarifaIva = tarifaIvaController.getTarifaIvaList(txtBuscar.getText());

		if (totalItems == 0 && listaTarifaIva != null)
			totalItems = listaTarifaIva.size();

		jPanelWithTable = new JPanelWithTable<TarifaIva>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "codigo", "nombre", "porcentaje" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 100, 328, 170 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0, 1 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "CODIGO", "NOMBRE", "PORCENTAJE" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaTarifaIva), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();

	}

	private void capturaYAgregaTarifaIvaATabla() {
		if (tarifaIva == tarifaIvaCrudFrm.getTarifaIva() && tarifaIva.getId() != null) {
			txtBuscar.setText(tarifaIva.getNombre());
			crearTabla();
		} else {
			if (tarifaIvaCrudFrm.getTarifaIva() != null && tarifaIvaCrudFrm.getTarifaIva().getId() != null) {
				totalItems++;
				txtBuscar.setText(tarifaIvaCrudFrm.getTarifaIva().getNombre());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarATarifaIvaCrudFrm() {
		tarifaIva = new TarifaIva();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			tarifaIva.setId(Integer.parseInt(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			tarifaIva.setCodigo(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			tarifaIva.setNombre(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString());
			tarifaIva.setPorcentaje(Double.parseDouble(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 3).toString()));
			tarifaIvaCrudFrm.setTarifaIva(tarifaIva);
			return true;
		}
		return false;
	}

	private void eliminarTarifaIva() {
		if (llenarEntidadParaEnviarATarifaIvaCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tarifaIva.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TarifaIvaController tarifaiIvaController = new TarifaIvaController();
				String error = tarifaiIvaController.deleteTarifaIva(tarifaIva);
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
		tarifaIvaCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTarifaIvaATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tarifaIva = new TarifaIva();
				tarifaIvaCrudFrm.setTarifaIva(tarifaIva);
				if (!tarifaIvaCrudFrm.isVisible()) {
					tarifaIvaCrudFrm.setModal(true);
					tarifaIvaCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATarifaIvaCrudFrm()) {
					if (!tarifaIvaCrudFrm.isVisible()) {
						tarifaIvaCrudFrm.setModal(true);
						tarifaIvaCrudFrm.setVisible(true);
					}
				}
			}
		});

		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTarifaIva();
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
		btnNuevo.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(450, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));

		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));

		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba código o nombre o porcentaje");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(TarifaIvaListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);

		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
	}
}
