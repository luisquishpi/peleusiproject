package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import ec.peleusi.controllers.CategoriaProductoController;
import ec.peleusi.models.entities.CategoriaProducto;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.FlowLayout;

public class CategoriaProductoListModalFrm extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree tree;
	private DefaultTreeModel modelo;
	private JPanel panel;
	private JButton btnCancelar;
	private JPanel panel_1;
	private JButton btnAceptar;
	private CategoriaProducto categoriaProducto;

	public CategoriaProductoListModalFrm() {
		setResizable(false);
		setTitle("Categorías de Productos");
		crearControles();
		crearEventos();
		cargarArbolCategoriaProductos();
		 btnAceptar.setEnabled(false);

	}

	public void cargarArbolCategoriaProductos() {
		DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Categorias");
		modelo = new DefaultTreeModel(raiz);
		getHojas(raiz, "0");
		tree.setModel(modelo);
	}

	public void getHojas(DefaultMutableTreeNode raiz, String id) {
		CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
		List<CategoriaProducto> lista;
		lista = categoriaProductoController.CategoriaProductoList(Integer.parseInt(id));
		for (int i = 0; i < lista.size(); i++) {
			DefaultMutableTreeNode hoja = new DefaultMutableTreeNode();
			hoja.setUserObject(lista.get(i));
			getHojas(hoja, lista.get(i).getId().toString());
			raiz.add(hoja);
		}
	}

	private void crearEventos() {
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (nodo.getLevel() != 0) {
					categoriaProducto = (CategoriaProducto) nodo.getUserObject();
					
					if(categoriaProducto.getContieneProductos()==true)
					{
						btnAceptar.setEnabled(true);
						System.out.println("Categoría seleccionado: " + categoriaProducto);
					}
					else btnAceptar.setEnabled(false);
						
					
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}

	private void crearControles() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 305, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setIcon(new ImageIcon(CategoriaProductoListModalFrm.class.getResource("/ec/peleusi/utils/images/Select.png")));
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_1.add(btnAceptar);
		btnCancelar = new JButton("Cancelar");
		panel_1.add(btnCancelar);
		btnCancelar.setIcon(new ImageIcon(CategoriaProductoListModalFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));

		tree = new JTree();
		panel.add(tree, BorderLayout.CENTER);
	}

	public CategoriaProducto getCategoriaProducto() {
		return categoriaProducto;
	}

	public void addConfirmListener(ActionListener listener) {
		btnAceptar.addActionListener(listener);
	}

}
