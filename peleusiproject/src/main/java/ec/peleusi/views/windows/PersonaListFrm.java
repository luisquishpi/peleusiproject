package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import ec.peleusi.controllers.PersonaController;
import ec.peleusi.models.entities.Persona;

public class PersonaListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtBuscar;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private JTable tablaPersona;
	private PersonaCrudFrm personaCrudFrm = new PersonaCrudFrm();

	public PersonaListFrm() {
		setTitle("Listado Persona");
		crearControles();		
		crearEventos();
		crearTabla();
	}	

	private void crearTabla() {
		Object[] cabecera = { "Id", "Identificaciòn", "Razòn Social", "Tipo Precio" };
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
		tablaPersona = new JTable(modelo) {

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
		tablaPersona.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tablaPersona.setPreferredScrollableViewportSize(tablaPersona.getPreferredSize());
		tablaPersona.getTableHeader().setReorderingAllowed(true);
		tablaPersona.getColumnModel().getColumn(0).setPreferredWidth(100);
		tablaPersona.getColumnModel().getColumn(1).setPreferredWidth(300);
		tablaPersona.getColumnModel().getColumn(2).setPreferredWidth(150);
		scrollPane.setViewportView(tablaPersona);

	}

	private Object[] agregarDatosAFila(Persona persona) {
		filaDatos[0] = persona.getId();
		filaDatos[1] = persona.getIdentificacion();
		filaDatos[2] = persona.getRazonSocial();
		filaDatos[3] = persona.getTipoPrecio().getNombre();
		return filaDatos;
	}

	private void cargarTabla() {
		PersonaController personaController = new PersonaController();
		List<Persona> listaPersona = personaController.PersonaList();
		for (Persona persona : listaPersona) {
			modelo.addRow(agregarDatosAFila(persona));
		}

	}

	private void capturaYAgregaPersonaATabla() {
		Persona persona = new Persona();
		persona = personaCrudFrm.getPersona();
		if (persona != null && persona.getId() != null) {
			System.out.println("Captura Persona retornado: " + persona);
			modelo.addRow(agregarDatosAFila(persona));
			tablaPersona.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
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
		btnNuevo.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(PersonaListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
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

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 446, 208);
		panelCuerpo.add(scrollPane);
	}

	private void crearEventos() {

		personaCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaPersonaATabla();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!personaCrudFrm.isVisible()) {
					personaCrudFrm.setModal(true);
					personaCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				
				
				
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
			}
						});		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {							
				PersonaController personaController = new PersonaController();				
				List<Persona> listaPersona = personaController.PersonaList(txtBuscar.getText());
				modelo.getDataVector().removeAllElements();
				modelo.fireTableDataChanged();
				for (Persona persona : listaPersona) {
					modelo.addRow(agregarDatosAFila(persona));
				}
			}
		});
	}

}
