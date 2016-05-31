package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
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
public class CiudadListModalFrm extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuscar;
	private JTable tblCiudad;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private CiudadCrudFrm ciudadCrudFrm = new CiudadCrudFrm();
	private Ciudad ciudad;
	private JScrollPane scrollPane;
	@SuppressWarnings("unused")
	private CiudadListModalFrm ciudadListModalFrm;
	

	public CiudadListModalFrm() {
		setTitle("Listado de Ciudades");
		crearControles();
		crearEventos();
		crearTabla();
	}

	

	private void crearTabla() {
		Object[] cabecera = { "Id", "Ciudad" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblCiudad = new JTable(modelo) {

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
				default:
					return String.class;
				}
			}
		};
		tblCiudad.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblCiudad.setPreferredScrollableViewportSize(tblCiudad.getPreferredSize());
		tblCiudad.getTableHeader().setReorderingAllowed(true);
		tblCiudad.getColumnModel().getColumn(0).setMaxWidth(0);
		tblCiudad.getColumnModel().getColumn(0).setMinWidth(0);
		tblCiudad.getColumnModel().getColumn(0).setPreferredWidth(0);
		tblCiudad.getColumnModel().getColumn(1).setPreferredWidth(292);
		scrollPane.setViewportView(tblCiudad);
	}

	private Object[] agregarDatosAFila(Ciudad ciudad) {
		filaDatos[0] = ciudad.getId();
		filaDatos[1] = ciudad.getNombre();
		return filaDatos;
	}

	private void cargarTabla() {
		CiudadController ciudadController = new CiudadController();
		List<Ciudad> listaCiudad = ciudadController.ciudadList();
		for (Ciudad ciudad : listaCiudad) {
			modelo.addRow(agregarDatosAFila(ciudad));
		}
	}

	private void capturaYAgregaCiudadATabla() {
		Ciudad ciudad = new Ciudad();
		ciudad = ciudadCrudFrm.getCiudad();
		System.out.println("Captura UnidadMedida retornado: " + ciudad);
		if (ciudad != null && ciudad.getId() != null) {
			modelo.addRow(agregarDatosAFila(ciudad));
			tblCiudad.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	public Ciudad getCiudad() {
		return ciudad;
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
		ciudadCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaCiudadATabla();
			}

		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CiudadController ciudadController = new CiudadController();
				List<Ciudad> listaCiudad = ciudadController.ciudadList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (Ciudad ciudad : listaCiudad) {
					modelo.addRow(agregarDatosAFila(ciudad));
				}

			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int filaseleccionada = tblCiudad.getSelectedRow();				
				try { 
					filaseleccionada = tblCiudad.getSelectedRow();
				  
				  if (filaseleccionada == -1)
				  { 
					  System.out.println(">>>> fila  " + filaseleccionada + "<<<<<");
					  JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila"); 
				  }	
				  else { 
					  	ciudad = new Ciudad();
						ciudad.setId(Integer.parseInt(modelo.getValueAt(tblCiudad.getSelectedRow(), 0).toString()));
						ciudad.setNombre(modelo.getValueAt(tblCiudad.getSelectedRow(), 1).toString());
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
		setTitle("Lista de Ciudades");
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
					new ImageIcon(CiudadListModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
			btnBuscar.setBounds(311, 11, 115, 47);
			contentPanel.add(btnBuscar);
		}

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 56, 295, 145);
		contentPanel.add(scrollPane);
		{
			tblCiudad = new JTable();			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAceptar = new JButton("Aceptar");
				btnAceptar.setIcon(
						new ImageIcon(CiudadListModalFrm.class.getResource("/ec/peleusi/utils/images/Select.png")));
				btnAceptar.setActionCommand("OK");
				buttonPane.add(btnAceptar);
				getRootPane().setDefaultButton(btnAceptar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.setIcon(
						new ImageIcon(CiudadListModalFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
}
