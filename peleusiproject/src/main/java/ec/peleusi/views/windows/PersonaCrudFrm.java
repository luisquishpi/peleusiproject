package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import ec.peleusi.controllers.PersonaController;
import ec.peleusi.controllers.TipoCalificacionPersonaController;
import ec.peleusi.controllers.TipoIdentificacionController;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.Persona;
import ec.peleusi.models.entities.TipoCalificacionPersona;
import ec.peleusi.models.entities.TipoIdentificacion;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.Formatos;
import ec.peleusi.utils.TipoPersonaEnum;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class PersonaCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtIdentificacion;
	private JTextField txtRazonSocial;
	private JTextField txtDiasCredito;
	private JFormattedTextField txtPorcentajeDescuento;
	private JTextField txtDescripcion;
	private Persona personaRetorno;

	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoPrecio;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoCalificacionPersona;
	private JComboBox<TipoPersonaEnum> cmbTipoPersona;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbTipoIdentificacion;

	public PersonaCrudFrm() {
		setTitle("Persona");
		crearControles();
		crearEventos();
		cargarCombos();
		limpiarCampos();
	}

	private void cargarCombos() {
		CargarComboTipoPersona();
		CargarListaTipoIdentificacion();
		CargarListaTipoCalificiacionPersona();
		CargarListaTipoPrecio();
	}
	
	public Persona getPersona() {
		return personaRetorno;
	}
	
	private void CargarComboTipoPersona() {
		cmbTipoPersona.setModel(new DefaultComboBoxModel<TipoPersonaEnum>(TipoPersonaEnum.values()));

	}
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaTipoIdentificacion() {
		TipoIdentificacionController tipoIdentificacionController = new TipoIdentificacionController();
		List<TipoIdentificacion> listaTipoIdentificacion;
		listaTipoIdentificacion = tipoIdentificacionController.tipoIdentificacionList();
		cmbTipoIdentificacion.setModel(new DefaultComboBoxModel(listaTipoIdentificacion.toArray()));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaTipoPrecio() {
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		List<TipoPrecio> listaTipoPrecio;
		listaTipoPrecio = tipoPrecioController.tipoPrecioList();
		cmbTipoPrecio.setModel(new DefaultComboBoxModel(listaTipoPrecio.toArray()));
	}
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void CargarListaTipoCalificiacionPersona() {
		TipoCalificacionPersonaController tipoCalificacionPersonaController = new TipoCalificacionPersonaController();
		List<TipoCalificacionPersona> listaTipoCalificacionPersona;
		listaTipoCalificacionPersona = tipoCalificacionPersonaController.tipoCalificacionPersonaList();
		cmbTipoCalificacionPersona.setModel(new DefaultComboBoxModel(listaTipoCalificacionPersona.toArray()));
	}
	
	private void limpiarCampos() {
		txtIdentificacion.setText("");
		txtRazonSocial.setText("");
		txtDiasCredito.setText("0");
		txtPorcentajeDescuento.setText("0");
		txtDescripcion.setText("");

	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtIdentificacion.getText().isEmpty() || txtRazonSocial.getText().isEmpty()
				|| txtDiasCredito.getText().isEmpty() || txtPorcentajeDescuento.getText().isEmpty()
				)
			llenos = false;
		return llenos;
	}

	private void crearEventos() {

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limpiarCampos();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn", JOptionPane.WARNING_MESSAGE);
					return;
				}											
				Boolean tipoPersona = true;
				TipoIdentificacion tipoIdentificacion = (TipoIdentificacion) cmbTipoIdentificacion.getSelectedItem();
				TipoPrecio tipoPrecio = (TipoPrecio) cmbTipoPrecio.getSelectedItem();
				TipoCalificacionPersona tipoCalificacionPersona = (TipoCalificacionPersona) cmbTipoCalificacionPersona
						.getSelectedItem();
				Persona persona = new Persona(tipoIdentificacion, txtIdentificacion.getText(), txtRazonSocial.getText(),
						tipoPrecio, tipoCalificacionPersona, Integer.parseInt(txtDiasCredito.getText()),
						Double.parseDouble(txtPorcentajeDescuento.getText()), txtDescripcion.getText(), tipoPersona);
				PersonaController personaControllers = new PersonaController();
				String error = personaControllers.createPersona(persona);

				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
					limpiarCampos();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}

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
	}

	private void crearControles() {
	
		setBounds(100, 100, 611, 398);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(PersonaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblCodigo = new JLabel("Tipo Identificaciòn");
		lblCodigo.setBounds(29, 50, 95, 14);
		panelCuerpo.add(lblCodigo);

		JLabel lblIdentificacin = new JLabel("Identificaciòn");
		lblIdentificacin.setBounds(29, 80, 83, 14);
		panelCuerpo.add(lblIdentificacin);

		txtIdentificacion = new JTextField();
		txtIdentificacion.setBounds(180, 80, 144, 20);
		panelCuerpo.add(txtIdentificacion);
		txtIdentificacion.setColumns(10);

		txtRazonSocial = new JTextField();
		txtRazonSocial.setBounds(180, 110, 340, 20);
		panelCuerpo.add(txtRazonSocial);
		txtRazonSocial.setColumns(10);

		JLabel lblTipoPrecio = new JLabel("Tipo Precio");
		lblTipoPrecio.setBounds(29, 200, 83, 14);
		panelCuerpo.add(lblTipoPrecio);

		JLabel lblRaznSocial = new JLabel("Razòn Social");
		lblRaznSocial.setBounds(29, 110, 84, 14);
		panelCuerpo.add(lblRaznSocial);

		cmbTipoPrecio = new JComboBox<TipoPrecio>();
		cmbTipoPrecio.setBounds(180, 200, 150, 20);
		panelCuerpo.add(cmbTipoPrecio);

		JLabel lblCalificacinPersona = new JLabel("Tipo Calificaciòn Persona");
		lblCalificacinPersona.setBounds(28, 140, 144, 14);
		panelCuerpo.add(lblCalificacinPersona);

		cmbTipoCalificacionPersona = new JComboBox<TipoCalificacionPersona>();
		cmbTipoCalificacionPersona.setBounds(180, 140, 125, 20);
		panelCuerpo.add(cmbTipoCalificacionPersona);

		JLabel lblDasCrdito = new JLabel("Dìas Crèdito");
		lblDasCrdito.setBounds(30, 170, 94, 14);
		panelCuerpo.add(lblDasCrdito);

		txtDiasCredito = new JTextField();
		txtDiasCredito.setBounds(180, 170, 150, 20);
		panelCuerpo.add(txtDiasCredito);
		txtDiasCredito.setColumns(10);

		txtPorcentajeDescuento = new JFormattedTextField();
		txtPorcentajeDescuento.setBounds(180, 230, 150, 20);
		txtPorcentajeDescuento.setFormatterFactory(new Formatos().getDecimalFormat());
		panelCuerpo.add(txtPorcentajeDescuento);
		
		JLabel lblDescripcin = new JLabel("Descripciòn");
		lblDescripcin.setBounds(29, 260, 83, 14);
		panelCuerpo.add(lblDescripcin);

		txtDescripcion = new JTextField();
		txtDescripcion.setBounds(180, 260, 415, 20);
		panelCuerpo.add(txtDescripcion);
		txtDescripcion.setColumns(10);

		JLabel lblTipopersona = new JLabel("Tipo Persona");
		lblTipopersona.setBounds(29, 20, 83, 14);
		panelCuerpo.add(lblTipopersona);

		cmbTipoPersona = new JComboBox<TipoPersonaEnum>();
		cmbTipoPersona.setBounds(180, 20, 150, 20);
		panelCuerpo.add(cmbTipoPersona);

		JLabel lblPorcentajeDescuento = new JLabel("Porcentaje Descuento");
		lblPorcentajeDescuento.setBounds(29, 230, 143, 14);
		panelCuerpo.add(lblPorcentajeDescuento);

		cmbTipoIdentificacion = new JComboBox<TipoIdentificacion>();
		cmbTipoIdentificacion.setBounds(180, 50, 144, 20);
		panelCuerpo.add(cmbTipoIdentificacion);

	}

}
