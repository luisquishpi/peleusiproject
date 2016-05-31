package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.Cliente;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.models.entities.TipoPrecio;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;

public class ProductoListCostoModalFrm extends JDialog {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuscar;
	private JTable tblProductos;
	private JScrollPane scrollPane;
	private JComboBox<TipoPrecio> cmbTipoPrecio;
	private JButton btnAceptar;
	Producto producto= new Producto();
	

	public static void main(String[] args) {
		try {
			ProductoListCostoModalFrm dialog = new ProductoListCostoModalFrm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProductoListCostoModalFrm() {
		crearControles();
		crearEventos();
		cargarCompoTipoPrecio();
		crearTabla();
		cargarTabla();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarCompoTipoPrecio() {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		List<TipoPrecio> listaTipoPrecio;
		listaTipoPrecio = tipoPrecioController.tipoPrecioList();
		cmbTipoPrecio.setModel(new DefaultComboBoxModel(listaTipoPrecio.toArray()));
	}
	public Producto getProducto() {
		return producto;
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Código", "Nombre", "Stock", "Costo", "Unidad", "IVA" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4
						|| columnIndex == 5 || columnIndex == 6) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		tblProductos = new JTable(modelo){
			private static final long serialVersionUID = 1L;

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case 6:
					return Boolean.class;
				default:
					return String.class;
				}
			}
		};
		tblProductos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblProductos.setPreferredScrollableViewportSize(tblProductos.getPreferredSize());
		tblProductos.getTableHeader().setReorderingAllowed(true);
		tblProductos.getColumnModel().getColumn(0).setMaxWidth(0);
		tblProductos.getColumnModel().getColumn(0).setMinWidth(0);
		tblProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblProductos.getColumnModel().getColumn(1).setPreferredWidth(50);
		tblProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
		tblProductos.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblProductos.getColumnModel().getColumn(4).setPreferredWidth(85);
		tblProductos.getColumnModel().getColumn(5).setPreferredWidth(80);
		scrollPane.setViewportView(tblProductos);
	}

	private Object[] agregarDatosAFila(Producto producto) {
		filaDatos[0] = producto.getId();
		filaDatos[1] = producto.getCodigo();
		filaDatos[2] = producto.getNombre();
		filaDatos[3] = producto.getStock();
		filaDatos[4] = producto.getCosto();
		filaDatos[5] = producto.getUnidadMedidaCompra();
		filaDatos[6] = producto.getTieneIva();
		return filaDatos;
	}

	public void cargarTabla() {
		ProductoController productoController = new ProductoController();
		List<Producto> listaProducto = productoController.productoList();
		for (Producto producto : listaProducto) {
			modelo.addRow(agregarDatosAFila(producto));
		}
	}

	private void crearEventos()
	{
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int fila = tblProductos.getSelectedRow();

				System.out.println(">>>> " + fila + "<<<<<");
				if (fila != -1) {
					producto = new Producto();
					producto.setId(Integer.parseInt(modelo.getValueAt(tblProductos.getSelectedRow(), 0).toString()));
					producto.setCodigo(modelo.getValueAt(tblProductos.getSelectedRow(), 1).toString());
					producto.setNombre(modelo.getValueAt(tblProductos.getSelectedRow(), 2).toString());
					producto.setStock(Double.parseDouble(modelo.getValueAt(tblProductos.getSelectedRow(), 3).toString()));
					producto.setCosto(Double.parseDouble(modelo.getValueAt(tblProductos.getSelectedRow(), 4).toString()));
					producto.setTieneIva(Boolean.parseBoolean(modelo.getValueAt(tblProductos.getSelectedRow(), 6).toString()));
					
					
					
				}
				dispose();
				

			}
		});
		
	}
	private void crearControles() {
		setTitle("Lista de Productos");
		setBounds(100, 100, 501, 375);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(300, 100));
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(null);
			{
				cmbTipoPrecio = new JComboBox<TipoPrecio>();
				cmbTipoPrecio.setBounds(0, 0, 465, 20);
				panel.add(cmbTipoPrecio);
			}
			{
				txtBuscar = new JTextField();
				txtBuscar.setBounds(0, 27, 354, 38);
				txtBuscar.setPreferredSize(new Dimension(6, 38));
				txtBuscar.setColumns(25);
				panel.add(txtBuscar);
			}
			{
				JButton btnBuscar = new JButton("Buscar");
				btnBuscar.setBounds(364, 26, 101, 41);
				btnBuscar.setIcon(new ImageIcon(
						ProductoListCostoModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
				panel.add(btnBuscar);
			}
		}
		{
			scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				tblProductos = new JTable();
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAceptar = new JButton("Aceptar");
				
				btnAceptar.setIcon(new ImageIcon(
						ProductoListCostoModalFrm.class.getResource("/ec/peleusi/utils/images/Select.png")));
				buttonPane.add(btnAceptar);
				getRootPane().setDefaultButton(btnAceptar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setIcon(new ImageIcon(
						ProductoListCostoModalFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
				buttonPane.add(btnCancelar);
			}
		}

	}

}
