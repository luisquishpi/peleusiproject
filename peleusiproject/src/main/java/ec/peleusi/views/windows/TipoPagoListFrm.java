package ec.peleusi.views.windows;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import com.sun.glass.events.WindowEvent;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ec.peleusi.controllers.TipoPagoController;
import ec.peleusi.models.entities.TipoPago;


public class TipoPagoListFrm extends JInternalFrame{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel modelo;
	private JTable tablaTipoPago;
	private Object [] filaDatos;
	private JScrollPane ScrollPane;
	private TipoPagoCrudFrm tipoPagoCrudFrm = new TipoPagoCrudFrm();
	private JButton btnEditar;
	private JButton btnEliminar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JTextField txtBuscar;
	
	public TipoPagoListFrm(){
		setTitle("Listado Tipo de Pago");
		setClosable(true);
		crearControles();
		crearEventos();
		crearTabla();
		//capturaYAgregaTipoRetencionATabla();
		cargarTabla();
	}
	private void crearTabla(){
		Object[] cabecera = {"Id","codigo"};
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 12;
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex){
				if(columnIndex==0 || columnIndex==1 ) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		//cargarTabla();
		
		    tablaTipoPago = new JTable(modelo){
			private static final long serialVersionUID = 1L;
			@Override
			public Class<?> getColumnClass(int column){
				switch(column){
				case 0:
					return String.class;
				case 1:
					return String.class;
				default:
					return String.class;
				}
			}
		};	
		tablaTipoPago.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaTipoPago.setPreferredScrollableViewportSize(tablaTipoPago.getPreferredSize());
		tablaTipoPago.getTableHeader().setReorderingAllowed(true);
		tablaTipoPago.getColumnModel().getColumn(0).setPreferredWidth(50);
		tablaTipoPago.getColumnModel().getColumn(1).setPreferredWidth(200);
		ScrollPane.setViewportView(tablaTipoPago);
		
	}
		
		//tablaTipoPago.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		/*
		
		
		
		}*/
	private Object[] agregarDatosAFila(TipoPago tipoPago){
		filaDatos[0] = tipoPago.getId();
		filaDatos[1] = tipoPago.getNombre();
		return filaDatos;
			
	}
	public void cargarTabla(){
		TipoPagoController tipoPagoController = new TipoPagoController();
		List <TipoPago> listaTipoPago = tipoPagoController.tipoPagoList();				          
		for (TipoPago tipoPago: listaTipoPago){
			modelo.addRow(agregarDatosAFila(tipoPago));			
		}	
		
		System.out.println("Captura Tipo Pago retornado" +listaTipoPago);
		
	}
	
	
	
	
	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 742, 379);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(122, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(262, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(406, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(546, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 8, 446, 41);
		panelCuerpo.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		ScrollPane = new JScrollPane();
		ScrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(ScrollPane);
	}
	
		
	private void crearEventos() {
		
		
		
		btnNuevo.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(!tipoPagoCrudFrm.isVisible());{
					
				//tipoPagoCrudFrm.setModal(true);
					tipoPagoCrudFrm.setVisible(true);
				
				}
				
			}
		});	

		btnEditar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnBuscar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				TipoPagoController tipoPagoController = new TipoPagoController();
				List<TipoPago> listaTipoPago = tipoPagoController.tipoPagoList();
				
				//List<TipoRetencion> listaTipoRetencion = tipoRetencionController.tipoRetencionList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for(TipoPago tipoPago: listaTipoPago){
					modelo.addRow(agregarDatosAFila(tipoPago));
						
			}
		}
		});
	}
}

		
		
	
	