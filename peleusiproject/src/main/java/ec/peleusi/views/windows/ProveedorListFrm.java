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
import ec.peleusi.controllers.ProveedorController;
import ec.peleusi.models.entities.Proveedor;
import ec.peleusi.models.entities.TipoIdentificacion;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class ProveedorListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JButton btnBuscar;
	private ProveedorCrudFrm proveedorCrudFrm = new ProveedorCrudFrm();
	private Proveedor proveedor;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<Proveedor> jPanelWithTable;

	public ProveedorListFrm() {
		setTitle("Listado Proveedor");
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
		ProveedorController proveedorController = new ProveedorController();
		List<Proveedor> listaProveedor = proveedorController.getProveedorList(txtBuscar.getText());

		if (totalItems == 0 && listaProveedor != null)
			totalItems = listaProveedor.size();

		jPanelWithTable = new JPanelWithTable<Proveedor>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "tipoIdentificacion", "identificacion", "razonSocial", "diasCredito", "porcentajeDescuento", "descripcion" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 0, 150, 300, 0, 0, 0 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID","tipoIdentificacion", "identificacion", "razonSocial", "diasCredito", "porcentajeDescuento", "descripcion" };
		
		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaProveedor), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}
	
	private void capturaYAgregaProveedorATabla() {
		if (proveedor == proveedorCrudFrm.getProveedor() && proveedor.getId() != null) {
			txtBuscar.setText(proveedor.getRazonSocial());
			crearTabla();
		} else {
			if (proveedorCrudFrm.getProveedor() != null && proveedorCrudFrm.getProveedor().getId() != null) {
				totalItems++;
				txtBuscar.setText(proveedorCrudFrm.getProveedor().getRazonSocial());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarAProveedorCrudFrm() {
		proveedor = new Proveedor();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			proveedor.setId(Integer.parseInt(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			proveedor.setTipoIdentificacion((TipoIdentificacion)jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1));
			proveedor.setRazonSocial(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString());
			proveedor.setDiasCredito(Integer.parseInt(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 3).toString()));
			proveedor.setPorcentajeDescuento(Double.parseDouble(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 4).toString()));
			proveedor.setDescripcion(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 5).toString());
			proveedorCrudFrm.setProveedor(proveedor);
			return true;
		}
		return false;
	}

	private void eliminarProveedor() {
		if (llenarEntidadParaEnviarAProveedorCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + proveedor.getRazonSocial() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				ProveedorController proveedorController = new ProveedorController();
				String error = proveedorController.deleteProveedor(proveedor);
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

		proveedorCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaProveedorATabla();
			}
		});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				proveedor = new Proveedor();
				proveedorCrudFrm.setProveedor(proveedor);
				if (!proveedorCrudFrm.isVisible()) {
					proveedorCrudFrm.setModal(true);
					proveedorCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarAProveedorCrudFrm()) {
					if (!proveedorCrudFrm.isVisible()) {
						proveedorCrudFrm.setModal(true);
						proveedorCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarProveedor();
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
		setBounds(100, 100, 611, 379);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(ProveedorListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(ProveedorListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(ProveedorListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(ProveedorListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));
		
		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));
		
		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba código o descripción o porcentaje");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(ProveedorListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);
		
		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));		
	}
}
