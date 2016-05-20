package ec.peleusi.views.windows;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
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
import javax.swing.JInternalFrame;
import javax.swing.JComboBox;

public class CajaCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JFormattedTextField txtSaldoInicial;
	private JComboBox<Sucursal> cmbSucursal;
	

	public CajaCrudFrm() {
		setTitle("Caja");
		crearControles();
		crearEventos();
		cargarSucursal();
		limpiarCampos();
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarSucursal(){
		SucursalController sucursalController = new SucursalController();
		List<Sucursal> listaSucursal;
		listaSucursal = sucursalController.SucursalList();
		cmbSucursal.setModel(new DefaultComboBoxModel(listaSucursal.toArray()));
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
				limpiarCampos();
			}
		});

		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "Datos incompletos, no es posible guardar", "Atenciòn",
							JOptionPane.WARNING_MESSAGE);
					return;
				}
				Sucursal sucursal = (Sucursal) cmbSucursal.getSelectedItem();
				Caja caja = new Caja(txtNombre.getText(), Double.parseDouble(txtSaldoInicial.getText()),sucursal );
				CajaController cajaController = new CajaController();
				String error = cajaController.createCaja(caja);
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
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 262);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(CajaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(CajaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panelCabecera.add(btnGuardar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(CajaCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(CajaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		lblNombre = new JLabel("Nombre");
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
