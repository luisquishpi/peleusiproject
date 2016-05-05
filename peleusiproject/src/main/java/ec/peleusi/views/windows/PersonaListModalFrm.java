package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

public class PersonaListModalFrm extends javax.swing.JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtBuscar;
	private JButton btnBuscar;
	private JScrollPane scrollPane;
	private DefaultTableModel modelo;
	private Object[] filaDatos;
	private JTable tblPersona;
	private PersonaCrudFrm personaCrudFrm = new PersonaCrudFrm();
	private Persona persona;
	// private CompraCrudFrm compraCrudFrm= new CompraCrudFrm();

	public PersonaListModalFrm() {
		setTitle("Listado Persona");
		crearControles();
		crearEventos();
		crearTabla();
	}

	private void crearTabla() {
		Object[] cabecera = { "Id", "Identificación", "Razón Social", "Dirección", "Teléfono" };
		modelo = new DefaultTableModel(null, cabecera) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				if (columnIndex == 0 || columnIndex == 1 || columnIndex == 2 || columnIndex == 3 || columnIndex == 4) {
					return false;
				}
				return true;
			}
		};
		filaDatos = new Object[cabecera.length];
		cargarTabla();
		tblPersona = new JTable(modelo) {

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
		tblPersona.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblPersona.setPreferredScrollableViewportSize(tblPersona.getPreferredSize());
		tblPersona.getTableHeader().setReorderingAllowed(true);

		tblPersona.getColumnModel().getColumn(0).setMaxWidth(0);
		tblPersona.getColumnModel().getColumn(0).setMinWidth(0);
		tblPersona.getColumnModel().getColumn(0).setPreferredWidth(0);

		tblPersona.getColumnModel().getColumn(1).setPreferredWidth(130);
		tblPersona.getColumnModel().getColumn(2).setPreferredWidth(280);
		tblPersona.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblPersona.getColumnModel().getColumn(4).setPreferredWidth(100);
		scrollPane.setViewportView(tblPersona);

	}

	private Object[] agregarDatosAFila(Persona persona) {
		filaDatos[0] = persona.getId();
		filaDatos[1] = persona.getIdentificacion();
		filaDatos[2] = persona.getRazonSocial();
		filaDatos[3] = persona.getDireccion();
		filaDatos[4] = persona.getTelefono();
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
			tblPersona.setRowSelectionInterval(modelo.getRowCount() - 1, modelo.getRowCount() - 1);
		}
	}

	private void crearControles() {

		setBounds(100, 100, 611, 448);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(10, 8, 446, 41);
		panelCuerpo.add(txtBuscar);
		txtBuscar.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(PersonaListModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnBuscar.setBounds(466, 8, 119, 41);
		panelCuerpo.add(btnBuscar);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 60, 575, 269);
		panelCuerpo.add(scrollPane);
		JPanel panelCabecera = new JPanel();
		panelCabecera.setBounds(0, 340, 595, 70);
		panelCuerpo.add(panelCabecera);
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(PersonaListModalFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(376, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setIcon(new ImageIcon(PersonaListModalFrm.class.getResource("/ec/peleusi/utils/images/Select.png")));
		btnAceptar.setBounds(96, 11, 130, 39);
		panelCabecera.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(PersonaListModalFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(236, 11, 130, 39);
		panelCabecera.add(btnCancelar);
	}

	public Persona getPersona() {
		return persona;
	}

	public void addConfirmListener(ActionListener listener) {
		btnAceptar.addActionListener(listener);
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
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fila = tblPersona.getSelectedRow();

				 System.out.println(">>>> " + fila + "<<<<<");
				if (fila != -1) {
					persona = new Persona();
					 persona.setId(Integer.parseInt(modelo.getValueAt(tblPersona.getSelectedRow(),
					 0).toString()));
					 persona.setIdentificacion(modelo.getValueAt(tblPersona.getSelectedRow(),
					 1).toString());
					 persona.setRazonSocial(modelo.getValueAt(tblPersona.getSelectedRow(),
					 2).toString());
					 persona.setDireccion(modelo.getValueAt(tblPersona.getSelectedRow(),
					 3).toString());
					 persona.setTelefono(modelo.getValueAt(tblPersona.getSelectedRow(),
					 4).toString());
					 System.out.println(">>>> " + persona + "<<<<<");
				}
				dispose();
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
