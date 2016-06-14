package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.peleusi.controllers.CajaController;
import ec.peleusi.controllers.SucursalController;
import ec.peleusi.models.entities.Caja;
import ec.peleusi.models.entities.Sucursal;
import ec.peleusi.utils.Formatos;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.JDialog;

public class CajaCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextField txtNombre;
	private JFormattedTextField txtSaldoInicial;
	private JComboBox<Sucursal> cmbSucursal;
	private Caja caja;
	
	public CajaCrudFrm() {
		setTitle("Caja");
		crearControles();
		crearEventos();
		cargarSucursal();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0){
				llenarCamposConEntidad();
				txtNombre.requestFocus();
			}
		});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarSucursal(){
		SucursalController sucursalController = new SucursalController();
		List<Sucursal> listaSucursal;
		listaSucursal = sucursalController.SucursalList();
		cmbSucursal.setModel(new DefaultComboBoxModel(listaSucursal.toArray()));
	}
	
	private void llenarCamposConEntidad(){
		if (caja !=null && caja.getId() != null){
			this.setTitle("Actualizar Caja");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtNombre.setText(caja.getNombre());		
			txtSaldoInicial.setText(Double.toString(caja.getSaldoInicial()));
			cmbSucursal.setSelectedItem(caja.getSucrusal());
		}else{
			this.setTitle("Creando Caja");
			btnGuardar.setText("Guardar");
			limpiarCampos();			
		}
	}
	
	private void llenarEntidadAntesDeGuardar() {
		caja.setNombre(txtNombre.getText());
		caja.setSaldoInicial(Double.parseDouble(txtSaldoInicial.getText().toString()));
		caja.setSucrusal((Sucursal) cmbSucursal.getSelectedItem());
	}
	
	private void guardarNuevaCaja() {
		caja = new Caja();
		llenarEntidadAntesDeGuardar();
		CajaController cajaController = new CajaController();
		String error = cajaController.createCaja(caja);		 
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarCaja() {
		llenarEntidadAntesDeGuardar();
		CajaController cajaController = new CajaController();
		String error = cajaController.updateCaja(caja);
		if (error == null) {
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = new Caja();
		this.caja = caja;
	}
	
	private void limpiarCampos() {
		txtNombre.setText("");
		txtSaldoInicial.setText("0");		
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtNombre.getText().isEmpty() || txtSaldoInicial.getText().isEmpty())
			llenos = false;
		return llenos;
	}

	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				caja = new Caja();
				llenarCamposConEntidad();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				if ( caja != null && caja.getId() != null) {
					actualizarCaja();
				} else {
					guardarNuevaCaja();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				caja = new Caja();
				dispose();
			}
		});
	}

	private void crearControles() {
		
		setBounds(100, 100, 500, 255);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(CajaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(20, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(CajaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(180, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(CajaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(340, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(30, 30, 100, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField(50);
		txtNombre.setBounds(110, 30, 214, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(50);

		JLabel lblSaldoInicial = new JLabel("Saldo Incial");
		lblSaldoInicial.setBounds(30, 70, 85, 14);
		panelCuerpo.add(lblSaldoInicial);

		txtSaldoInicial = new JFormattedTextField();
		txtSaldoInicial.setToolTipText("");
		txtSaldoInicial.setSize(75, 20);
		txtSaldoInicial.setLocation(110, 70);
		txtSaldoInicial.setFormatterFactory(new Formatos().getDecimalFormat());
		panelCuerpo.add(txtSaldoInicial);

		JLabel lblSucursal = new JLabel("Sucursal");
		lblSucursal.setBounds(30, 110, 70, 14);
		panelCuerpo.add(lblSucursal);

		cmbSucursal = new JComboBox<Sucursal>();
		cmbSucursal.setBounds(110, 107, 196, 20);
		panelCuerpo.add(cmbSucursal);

	}
}
