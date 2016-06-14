package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.UnidadMedida;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;


public class UnidadMedidaListModalFrm extends JDialog {	
	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	UnidadMedida unidadMedida = new UnidadMedida();
	private UnidadMedidaCrudFrm unidadMedidaCrudFrm = new UnidadMedidaCrudFrm();
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private JTextFieldPH txtBuscar;
	JPanelWithTable<UnidadMedida> jPanelWithTable;
	private Integer totalItems = 0;
	
	public static void main(String[] args) {
		try {
		CiudadListModalFrm dialog = new	CiudadListModalFrm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	 

	public UnidadMedidaListModalFrm() {
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
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		List<UnidadMedida> listaUnidadMedida = unidadMedidaController.getUnidadMedidaList(txtBuscar.getText());

		if (totalItems == 0 && listaUnidadMedida != null)
			totalItems = listaUnidadMedida.size();

		jPanelWithTable = new JPanelWithTable<UnidadMedida>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre", "abreviatura" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 300, 121 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRE", "ABREVIATURA" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaUnidadMedida), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
	
		if (jPanelWithTable.getJTable() != null) {
			jPanelWithTable.getJTable().addKeyListener(new KeyAdapter() {
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
			unidadMedida = new UnidadMedida();
			unidadMedida.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			unidadMedida.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			unidadMedida.setAbreviatura(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString());
			unidadMedidaCrudFrm.setUnidadMedida(unidadMedida);
		}		
		dispose();
	}

		
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	private void crearEventos() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearTabla();
			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			} 
		});
		

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
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

	public void crearControles() {
		setTitle("Lista de Unidades de Medida");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			pnlBuscar = new JPanel();
			contentPanel.add(pnlBuscar, BorderLayout.NORTH);
			pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));
			{
				txtBuscar = new JTextFieldPH();
				txtBuscar.setPlaceholder("Escriba nombre o abreviatura");
				txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
				pnlBuscar.add(txtBuscar);
				txtBuscar.setColumns(10);
			}
			{
				btnBuscar = new JButton("Buscar");				
				btnBuscar.setIcon(new ImageIcon(UnidadMedidaListModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
				pnlBuscar.add(btnBuscar);
			}
		}
		{
			pnlTabla = new JPanel();
			contentPanel.add(pnlTabla, BorderLayout.CENTER);
			pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAceptar = new JButton("Aceptar");
				btnAceptar.setIcon(
						new ImageIcon(UnidadMedidaListModalFrm.class.getResource("/ec/peleusi/utils/images/Select.png")));
				btnAceptar.setActionCommand("OK");
				buttonPane.add(btnAceptar);
				getRootPane().setDefaultButton(btnAceptar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.setIcon(
						new ImageIcon(UnidadMedidaListModalFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
}
