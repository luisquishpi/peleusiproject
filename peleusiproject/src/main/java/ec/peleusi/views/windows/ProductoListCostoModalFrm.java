package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ec.peleusi.controllers.ProductoController;
import ec.peleusi.models.entities.Producto;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ProductoListCostoModalFrm extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextFieldPH txtBuscar;
	private JButton btnAceptar;
	Producto producto = new Producto();
	private JPanel pnlTabla;
	JPanelWithTable<Producto> jPanelWithTable;
	private Integer totalItems = 0;
	private JButton btnBuscar;

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
		crearTabla();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				txtBuscar.requestFocus();
			}
		});
	}

	public Producto getProducto() {
		return producto;
	}

	private void crearTabla() {
		ProductoController productoController = new ProductoController();
		List<Producto> listaProducto = productoController.productoList(txtBuscar.getText());

		if (totalItems == 0 && listaProducto != null)
			totalItems = listaProducto.size();

		jPanelWithTable = new JPanelWithTable<Producto>();
		jPanelWithTable.setCamposEntidad(
				new String[] { "id", "codigo", "nombre", "stock", "costo", "unidadMedidaCompra", "tieneIva" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 70, 200, 50, 50, 100, 50 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTipoColumnas(new Class[] { Integer.class, String.class, String.class, Double.class,
				Double.class, String.class, Boolean.class });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "Id", "Código", "Nombre", "Stock", "Costo", "Unidad", "IVA" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaProducto), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();

		if (jPanelWithTable.getJTable() != null) {
			jPanelWithTable.getJTable().addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					if (KeyEvent.VK_ENTER == e.getKeyCode()) {
						aceptar();
					}
				}
			});
		}
		txtBuscar.requestFocus();
	}

	private void aceptar() {
		int fila = jPanelWithTable.getJTable().getSelectedRow();
		if (fila != -1) {
			producto = new Producto();
			producto.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			producto.setCodigo(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			producto.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString());
			producto.setStock(Double.parseDouble(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 3).toString()));
			producto.setCosto(Double.parseDouble(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 4).toString()));
			producto.setTieneIva(Boolean.parseBoolean(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 6).toString()));
		}
		System.out.println("producto: " + producto);
		dispose();
	}

	private void crearEventos() {
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
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
		setTitle("Lista de Productos - Costos de compra");
		setBounds(100, 100, 550, 400);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPanel.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JPanel pnlBuscar = new JPanel();
		panel.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));
		{
			txtBuscar = new JTextFieldPH();
			pnlBuscar.add(txtBuscar);
			txtBuscar.setPreferredSize(new Dimension(6, 38));
			txtBuscar.setColumns(25);
			txtBuscar.setPlaceholder("Buscar");
			txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		}
		{
			btnBuscar = new JButton("Buscar");
			pnlBuscar.add(btnBuscar);
			btnBuscar.setIcon(
					new ImageIcon(ProductoListCostoModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		}
		{
			pnlTabla = new JPanel();
			panel.add(pnlTabla, BorderLayout.CENTER);
			pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
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
				// getRootPane().setDefaultButton(btnAceptar);
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
