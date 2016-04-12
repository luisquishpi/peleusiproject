package ec.peleusi.views.windows;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;

public class SucursalCrudFrm extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtFax;
	private JTextField txtMail;
	private JTextField txtUrl;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnEliminar;
	private JButton btnCancelar;
	private JComboBox cmbLocal;
	private JComboBox cmdCiudad;
	private JLabel lblFoto;
	private JButton btnSeleccionar;
	

	public SucursalCrudFrm() 
   {
	   
		crearControles() ;
		crearEventos();
   }
	
	public void crearEventos()
	{
		
		
		
	}
	public void crearControles() {
		
		setBounds(100, 100, 602, 389);		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(SucursalCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		panel.add(btnNuevo);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(SucursalCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		panel.add(btnGuardar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(SucursalCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		panel.add(btnEliminar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(SucursalCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panel.add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(39, 59, 46, 14);
		panel_1.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(95, 56, 249, 20);
		panel_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblLocal = new JLabel("Local");
		lblLocal.setBounds(39, 28, 46, 14);
		panel_1.add(lblLocal);
		
		cmbLocal = new JComboBox();
		cmbLocal.setBounds(95, 28, 249, 20);
		panel_1.add(cmbLocal);
		
		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(39, 90, 46, 14);
		panel_1.add(lblCiudad);
		
		cmdCiudad = new JComboBox();
		cmdCiudad.setBounds(95, 87, 249, 20);
		panel_1.add(cmdCiudad);
		
		JLabel lblDireccin = new JLabel("Dirección ");
		lblDireccin.setBounds(39, 124, 46, 14);
		panel_1.add(lblDireccin);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(95, 118, 249, 20);
		panel_1.add(txtDireccion);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setBounds(39, 155, 46, 14);
		panel_1.add(lblTelfono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(95, 149, 249, 20);
		panel_1.add(txtTelefono);
		
		JLabel lblFax = new JLabel("Fax");
		lblFax.setBounds(39, 186, 46, 14);
		panel_1.add(lblFax);
		
		txtFax = new JTextField();
		txtFax.setColumns(10);
		txtFax.setBounds(95, 180, 249, 20);
		panel_1.add(txtFax);
		
		JLabel lblMail = new JLabel("Mail");
		lblMail.setBounds(39, 217, 46, 14);
		panel_1.add(lblMail);
		
		txtMail = new JTextField();
		txtMail.setColumns(10);
		txtMail.setBounds(95, 211, 249, 20);
		panel_1.add(txtMail);
		
		JLabel lblUrl = new JLabel("Url");
		lblUrl.setBounds(39, 248, 46, 14);
		panel_1.add(lblUrl);
		
		txtUrl = new JTextField();
		txtUrl.setColumns(10);
		txtUrl.setBounds(95, 242, 249, 20);
		panel_1.add(txtUrl);
		
		lblFoto = new JLabel("foto");
		lblFoto.setBounds(397, 28, 157, 187);
		panel_1.add(lblFoto);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setIcon(new ImageIcon(SucursalCrudFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		btnSeleccionar.setBounds(412, 217, 123, 44);
		panel_1.add(btnSeleccionar);

	}
}
