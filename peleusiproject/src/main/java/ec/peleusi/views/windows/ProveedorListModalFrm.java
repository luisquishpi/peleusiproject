package ec.peleusi.views.windows;

import java.awt.BorderLayout;


import javax.swing.JButton;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import ec.peleusi.controllers.ProveedorController;
import ec.peleusi.models.entities.Proveedor;

public class ProveedorListModalFrm extends javax.swing.JDialog  {
	
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuscar;
	private JButton btnBuscar;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnNuevo;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private ProveedorCrudFrm proveedorCrudFrm = new ProveedorCrudFrm();
	private JTable tblProveedor;
	private JScrollPane scrollPane;
	private Proveedor proveedor;

	
	public ProveedorListModalFrm() {
		
		crearControles();
		crearEventos();
		crearTabla(); 
	}
	private void crearTabla() {
		Object[] cabecera = { "Id", "Identificaciòn", "Razòn Social", "Tipo Identificaciòn" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblProveedor = new JTable(modelo) {

			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		tblProveedor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblProveedor.setPreferredScrollableViewportSize(tblProveedor.getPreferredSize());
		tblProveedor.getTableHeader().setReorderingAllowed(true);
		tblProveedor.getColumnModel().getColumn(0).setMaxWidth(0);
		tblProveedor.getColumnModel().getColumn(0).setMinWidth(0);
		tblProveedor.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblProveedor.getColumnModel().getColumn(1).setPreferredWidth(125);
		tblProveedor.getColumnModel().getColumn(2).setPreferredWidth(210);
		tblProveedor.getColumnModel().getColumn(3).setPreferredWidth(108);
		scrollPane.setViewportView(tblProveedor);

	}

	private Object[] agregarDatosAFila(Proveedor proveedor) {
		filaDatos[0] = proveedor.getId();
		filaDatos[1] = proveedor.getIdentificacion();
		filaDatos[2] = proveedor.getRazonSocial();
		filaDatos[3] = proveedor.getTipoIdentificacion().getNombre();
		return filaDatos;
	}

	private void cargarTabla() {
		ProveedorController proveedorController = new ProveedorController();
		List<Proveedor> listaProveedor = proveedorController.ProveedorList();
		for (Proveedor proveedor : listaProveedor) {
			modelo.addRow(agregarDatosAFila(proveedor));
		}
		
		System.out.println("Captura Proveedor retornado: " + listaProveedor.get(0));

	}

	private void capturaYAgregaProveedorATabla() {
		Proveedor proveedor = new Proveedor();
		proveedor = proveedorCrudFrm.getProveedor();
		if (proveedor != null && proveedor.getId() != null) {
			System.out.println("Captura Proveedor retornado: " + proveedor);
			modelo.addRow(agregarDatosAFila(proveedor));
			tblProveedor.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void addConfirmListener(ActionListener listener) {
		btnAceptar.addActionListener(listener);
	}
	public void crearEventos()
	{
		proveedorCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaProveedorATabla();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!proveedorCrudFrm.isVisible()) {
					proveedorCrudFrm.setModal(true);
					proveedorCrudFrm.setVisible(true);
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				int fila = tblProveedor.getSelectedRow();

				System.out.println(">>>> " + fila + "<<<<<");
				if (fila != -1) {
					proveedor = new Proveedor();
					proveedor.setId(Integer.parseInt(modelo.getValueAt(tblProveedor.getSelectedRow(), 0).toString()));
					proveedor.setIdentificacion(modelo.getValueAt(tblProveedor.getSelectedRow(), 1).toString());
					proveedor.setRazonSocial(modelo.getValueAt(tblProveedor.getSelectedRow(), 2).toString());				
				}
				dispose();
				
			}
			});	
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ProveedorController proveedorController = new ProveedorController();
				List<Proveedor> listaProveedor = proveedorController.getProveedorList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (Proveedor proveedor : listaProveedor) {
					modelo.addRow(agregarDatosAFila(proveedor));
				}
			
			}
		});
	}	
	public void crearControles()
	{
		setBounds(100, 100, 651, 425);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		txtBuscar = new JTextField();
		txtBuscar.setColumns(10);
		txtBuscar.setBounds(10, 11, 446, 41);
		contentPanel.add(txtBuscar);
		
		btnBuscar = new JButton("Buscar");
		
		btnBuscar.setIcon(new ImageIcon(ProveedorListModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 11, 119, 41);
		contentPanel.add(btnBuscar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 63, 595, 239);
		contentPanel.add(scrollPane);
		
		tblProveedor = new JTable(); 
			
		tblProveedor.setPreferredScrollableViewportSize(new Dimension(0, 0));
		tblProveedor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(tblProveedor);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 313, 595, 70);
		contentPanel.add(panel);
		
		btnNuevo = new JButton("Nuevo");
	
		btnNuevo.setIcon(new ImageIcon(ProveedorListModalFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(376, 11, 130, 39);
		panel.add(btnNuevo);
		
		btnAceptar = new JButton("Aceptar");		
		btnAceptar.setIcon(new ImageIcon(ProveedorListModalFrm.class.getResource("/ec/peleusi/utils/images/Select.png")));
		btnAceptar.setBounds(96, 11, 130, 39);
		panel.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		
		btnCancelar.setIcon(new ImageIcon(ProveedorListModalFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(236, 11, 130, 39);
		panel.add(btnCancelar);
	}
}
