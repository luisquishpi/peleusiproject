package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import ec.peleusi.models.entities.CategoriaProducto;
import ec.peleusi.controllers.CategoriaProductoController;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

public class CategoriaProductoCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree tree;
	private DefaultTreeModel modelo;
	private JLabel lblId;
	private JLabel lblDepen;
	private JCheckBox chbxContieneProductos;
	private JButton btnNuevo;
	private JLabel lblNombre;
	private JLabel lblDependencia;
	private JButton btnGuardar;
	private JPanel panel;
	private JButton btnCancelar;
	private JScrollPane scrollPane;
	private JPanel panel_1;
	private JPanel panel_2;
	private JTextField txtNombre;
	private JTextField txtDependencia;
	private ec.peleusi.models.entities.CategoriaProducto categoriaProducto;
	private JButton button;

	public CategoriaProductoCrudFrm() {
		setIconifiable(true);
		setClosable(true);
		setTitle("Categorías de Productos");
		crearControles();
		crearEventos();
		cargarArbolCategoriaProductos();

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

	private void limpiarcampos() {

		lblId.setText("0");
		txtNombre.setText("");
		chbxContieneProductos.setSelected(false);
		chbxContieneProductos.setEnabled(true);		
		txtNombre.requestFocus();

	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || lblDependencia.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void verificarUbicacionNodoNuevo() {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (nodo.getLevel() == 0) {
			lblDepen.setText("0");
			txtDependencia.setText("Categorias");
		} else {
			lblDepen.setText(lblId.getText());
			txtDependencia.setText(txtNombre.getText());
			txtDependencia.setText(nodo.toString());
		}
		
		
		
	}	

	private void agregarNodoArbol(CategoriaProducto categoriaProducto) {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.insertNodeInto(new DefaultMutableTreeNode(categoriaProducto), nodo, model.getChildCount(nodo));

	}

	private void actualizarNodoArbol(CategoriaProducto categoriaProducto) {
		DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		nodo.setUserObject(categoriaProducto);
		((DefaultTreeModel) tree.getModel()).nodeChanged(nodo);

	}

	private void crearEventos() {
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if (nodo.getLevel() != 0) {
					categoriaProducto = (CategoriaProducto) nodo.getUserObject();
					lblId.setText(categoriaProducto.getId().toString());
					txtNombre.setText(categoriaProducto.getNombre());
					txtDependencia.setText(nodo.getParent().toString());
					lblDepen.setText(categoriaProducto.getDependencia().toString());
					chbxContieneProductos.setSelected(categoriaProducto.getContieneProductos());

					if (nodo.getChildCount() > 0) {
						chbxContieneProductos.setEnabled(false);
					} else {
						chbxContieneProductos.setEnabled(true);
					}

					System.out.println("raiz " + nodo.getParent());
					System.out.println("Cate: " + categoriaProducto);
					System.out.println("Cate: " + nodo.getChildCount());
					System.out.println("nivel: " + nodo);
					

				}
			}
		});

		chbxContieneProductos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				
				categoriaProducto.getContieneProductos();
				System.out.println("raiz " + categoriaProducto.getContieneProductos());
				
				if (chbxContieneProductos.isSelected() == true) {
					btnNuevo.setEnabled(false);
					
					
				} else {
					
					if(categoriaProducto.getContieneProductos()==true)
					{
						btnNuevo.setEnabled(false);
					}
					else
					{
						btnNuevo.setEnabled(true);
					}
				}
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verificarUbicacionNodoNuevo();
				limpiarcampos();
			}

		});
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "No deje campos vacíos");
					return;
				}
				if (lblId.getText().toString() == "0") {
					CategoriaProducto categoriaProducto = new CategoriaProducto(txtNombre.getText(),
							Integer.parseInt(lblDepen.getText()), chbxContieneProductos.isSelected());
					CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
					categoriaProductoController.saveCategoriaProducto(categoriaProducto);
					if (categoriaProducto.getId() != 0) {
						agregarNodoArbol(categoriaProducto);
						limpiarcampos();
					}
				} else {
					CategoriaProducto categoriaProducto = new CategoriaProducto(Integer.parseInt(lblId.getText()),
							txtNombre.getText(), Integer.parseInt(lblDepen.getText()),
							chbxContieneProductos.isSelected());
					CategoriaProductoController categoriaProductoController = new CategoriaProductoController();
					if (categoriaProductoController.updateCategoriaProducto(categoriaProducto)) {
						actualizarNodoArbol(categoriaProducto);
					} else {
						JOptionPane.showMessageDialog(null, "Error, no es posible actualizar");
						return;
					}
					System.out.println(categoriaProducto);
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
		setBounds(100, 100, 833, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(CategoriaProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(CategoriaProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(CategoriaProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		
		button = new JButton("Cancelar");
		button.setIcon(new ImageIcon(CategoriaProductoCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNuevo, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnGuardar, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
					.addGap(35))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNuevo, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
						.addComponent(btnGuardar, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
						.addComponent(button, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
						.addComponent(btnCancelar, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE))
					.addGap(23))
		);
		panel_1.setLayout(gl_panel_1);

		panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);

		lblId = new JLabel("0");
		lblId.setBounds(57, 163, 16, 14);
		lblId.setForeground(Color.DARK_GRAY);
		lblId.setVisible(false);

		chbxContieneProductos = new JCheckBox("Contiene productos");
		chbxContieneProductos.setBounds(59, 123, 162, 23);
		lblDepen = new JLabel("0");
		lblDepen.setBounds(71, 163, 16, 14);
		lblDepen.setVisible(false);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(59, 11, 181, 14);

		lblDependencia = new JLabel("Dependencia:");
		lblDependencia.setBounds(59, 70, 210, 14);

		txtNombre = new JTextField();
		txtNombre.setBounds(59, 28, 406, 20);
		txtNombre.setColumns(10);

		txtDependencia = new JTextField();
		txtDependencia.setBounds(59, 88, 406, 20);
		txtDependencia.setEditable(false);
		txtDependencia.setColumns(10);
		panel_2.setLayout(null);
		panel_2.add(lblDependencia);
		panel_2.add(lblNombre);
		panel_2.add(lblDepen);
		panel_2.add(lblId);
		panel_2.add(chbxContieneProductos);
		panel_2.add(txtNombre);
		panel_2.add(txtDependencia);

		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.WEST);
		scrollPane.setPreferredSize(new Dimension(200, 200));

		tree = new JTree();
		scrollPane.setViewportView(tree);
	}
}
