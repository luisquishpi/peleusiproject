package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ec.peleusi.controllers.UnidadMedidaController;
import ec.peleusi.models.entities.UnidadMedida;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
@SuppressWarnings("serial")
public class UnidadMedidaListModalFrm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuscar;
	private JTable tblUnidadMedida;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private UnidadMedidaCrudFrm unidadMedidaCrudFrm = new UnidadMedidaCrudFrm();
	private UnidadMedida unidadMedida;
	private JScrollPane scrollPane;
	@SuppressWarnings("unused")
	private UnidadMedidaListModalFrm unidadMedidaListModalFrm;
	

	public UnidadMedidaListModalFrm() {
		setType(Type.UTILITY);
		//setTitle("Lista de Unidad de Medida");
		crearControles();
		crearEventos();
		crearTabla();
	}	

	private void crearTabla() {
		Object[] cabecera = { "Id", "UnidadMedida", "Abreviatura" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblUnidadMedida = new JTable(modelo) {

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
		tblUnidadMedida.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblUnidadMedida.setPreferredScrollableViewportSize(tblUnidadMedida.getPreferredSize());
		tblUnidadMedida.getTableHeader().setReorderingAllowed(true);
		tblUnidadMedida.getColumnModel().getColumn(0).setMaxWidth(0);
		tblUnidadMedida.getColumnModel().getColumn(0).setMinWidth(0);
		tblUnidadMedida.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblUnidadMedida.getColumnModel().getColumn(1).setPreferredWidth(170);
		tblUnidadMedida.getColumnModel().getColumn(2).setPreferredWidth(122);
		scrollPane.setViewportView(tblUnidadMedida);
	}

	private Object[] agregarDatosAFila(UnidadMedida unidadMedida) {
		filaDatos[0] = unidadMedida.getId();
		filaDatos[1] = unidadMedida.getNombre();
		filaDatos[2] = unidadMedida.getAbreviatura();
		return filaDatos;
	}

	private void cargarTabla() {
		UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
		List<UnidadMedida> listaUnidadMedida = unidadMedidaController.unidadMedidaList();
		for (UnidadMedida unidadMedida : listaUnidadMedida) {
			modelo.addRow(agregarDatosAFila(unidadMedida));
		}
	}

	private void capturaYAgregaUnidadMedidaATabla() {
		UnidadMedida unidadMedida = new UnidadMedida();
		unidadMedida = unidadMedidaCrudFrm.getUnidadMedida();
		System.out.println("Captura UnidadMedida retornado: " + unidadMedida);
		if (unidadMedida != null && unidadMedida.getId() != null) {
			modelo.addRow(agregarDatosAFila(unidadMedida));
			tblUnidadMedida.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			//CiudadListModalFrm dialog = new	ciudadListModalFrm();
			/*ciudadListModalFrm dialog = new ciudadListModalFrm(new javax.swing.JFrame(), true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */

	private void crearEventos() {
		unidadMedidaCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaUnidadMedidaATabla();
			}

		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnidadMedidaController unidadMedidaController = new UnidadMedidaController();
				List<UnidadMedida> listaUnidadMedida = unidadMedidaController.getUnidadMedidaList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (UnidadMedida unidadMedida : listaUnidadMedida) {
					modelo.addRow(agregarDatosAFila(unidadMedida));
				}

			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaseleccionada = tblUnidadMedida.getSelectedRow();				
				try { 
					filaseleccionada = tblUnidadMedida.getSelectedRow();
				  
				  if (filaseleccionada == -1)
				  { 
					  System.out.println(">>>> fila  " + filaseleccionada + "<<<<<");
					  JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila"); 
				  }	
				  else { 
					  	unidadMedida = new UnidadMedida();
						unidadMedida.setId(Integer.parseInt(modelo.getValueAt(tblUnidadMedida.getSelectedRow(), 0).toString()));
						unidadMedida.setNombre(modelo.getValueAt(tblUnidadMedida.getSelectedRow(), 1).toString());
						unidadMedida.setAbreviatura(modelo.getValueAt(tblUnidadMedida.getSelectedRow(), 2).toString());
						
				  		}
				  dispose();
				}
				catch (HeadlessException ex) {
				  JOptionPane.showMessageDialog(null, "Error: " + ex + "\nInténtelo nuevamente", " .::Error En la Operacion::.", JOptionPane.ERROR_MESSAGE);
				  } 
				} 
			});
		

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

	}

	public void crearControles() {
		setTitle("Lista de Unidades de Medida");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			txtBuscar = new JTextField();
			txtBuscar.setBounds(10, 17, 295, 32);
			txtBuscar.setFont(new Font("Tahoma", Font.PLAIN, 13));
			txtBuscar.setColumns(10);
			contentPanel.add(txtBuscar);
		}
		{
			btnBuscar = new JButton("Buscar");
			btnBuscar.setIcon(
					new ImageIcon(UnidadMedidaListModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
			btnBuscar.setBounds(311, 11, 115, 47);
			contentPanel.add(btnBuscar);
		}

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 295, 145);
		contentPanel.add(scrollPane);
		{
			tblUnidadMedida = new JTable();
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
