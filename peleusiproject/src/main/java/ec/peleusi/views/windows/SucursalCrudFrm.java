package ec.peleusi.views.windows;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.EmpresaController;
import ec.peleusi.controllers.SucursalController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Empresa;
import ec.peleusi.models.entities.Sucursal;

import javax.swing.JComboBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SucursalCrudFrm extends JInternalFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtFax;
	private JTextField txtEmail;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnEliminar;
	private JButton btnCancelar;
	@SuppressWarnings("rawtypes")	
	private JComboBox cmbEmpresa;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCiudad;
	

	public SucursalCrudFrm() 
   {
	   
		crearControles() ;
		crearEventos();
		llenarCiudad();
		llenarEmpresa();
   }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void llenarCiudad()
	{		
		CiudadController ciudadController= new CiudadController();
		List<Ciudad> lista;
		lista=ciudadController.ciudadList();
		cmbCiudad.setModel(new DefaultComboBoxModel(lista.toArray()));	
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void llenarEmpresa()
	{		
		EmpresaController empresaController= new EmpresaController();
		List<Empresa> lista;
		lista=empresaController.EmpresaList();
		cmbEmpresa.setModel(new DefaultComboBoxModel(lista.toArray()));	
	}
	 private boolean isCamposLlenos() {
	        boolean llenos = true;
	        if ( txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtEmail.getText().isEmpty() ||  txtFax.getText().isEmpty() )
	            llenos = false;
	        return llenos;
	    }

	 private void limpiarCampos()
		{
			
			txtNombre.setText("");
			txtDireccion.setText("");
			txtTelefono.setText("");
			txtFax.setText("");
			txtEmail.setText("");				
			txtNombre.requestFocus();				
			
		}	 
		
	public void crearEventos()
	{
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "No deje campos vacíos");
					return;
				}
					
				Ciudad ciudad=(Ciudad) cmbCiudad.getSelectedItem();
				Empresa empresa=(Empresa) cmbEmpresa.getSelectedItem();
			    System.out.println("ciudad " + ciudad);
			   	Sucursal sucursal= new Sucursal(txtNombre.getText(), txtDireccion.getText(), txtTelefono.getText(), txtFax.getText(), txtEmail.getText(), ciudad, empresa);
				SucursalController sucursalController= new SucursalController();
				String error= sucursalController.createSucursal(sucursal);		
				System.out.println("raiz " + error);
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					limpiarCampos();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}			
				
				
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		});
	}
	public void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 592, 360);		
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
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setIcon(new ImageIcon(SucursalCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		panel.add(btnCancelar);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(53, 59, 79, 14);
		panel_1.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(148, 56, 271, 20);
		panel_1.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblLocal = new JLabel("Local");
		lblLocal.setBounds(53, 28, 91, 14);
		panel_1.add(lblLocal);
		
		cmbEmpresa = new JComboBox<Empresa>();
		cmbEmpresa.setBounds(148, 28, 271, 20);
		panel_1.add(cmbEmpresa);
		
		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(53, 93, 72, 14);
		panel_1.add(lblCiudad);
		
		cmbCiudad = new JComboBox<Ciudad>();
		cmbCiudad.setBounds(148, 87, 271, 20);
		panel_1.add(cmbCiudad);
		
		JLabel lblDireccin = new JLabel("Dirección ");
		lblDireccin.setBounds(53, 124, 79, 14);
		panel_1.add(lblDireccin);
		
		txtDireccion = new JTextField();
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(148, 118, 271, 20);
		panel_1.add(txtDireccion);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setBounds(53, 155, 79, 14);
		panel_1.add(lblTelfono);
		
		txtTelefono = new JTextField();
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(148, 149, 271, 20);
		panel_1.add(txtTelefono);
		
		JLabel lblFax = new JLabel("Fax");
		lblFax.setBounds(53, 186, 79, 14);
		panel_1.add(lblFax);
		
		txtFax = new JTextField();
		txtFax.setColumns(10);
		txtFax.setBounds(148, 180, 271, 20);
		panel_1.add(txtFax);
		
		JLabel lblMail = new JLabel("Mail");
		lblMail.setBounds(53, 217, 72, 14);
		panel_1.add(lblMail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(148, 211, 271, 20);
		panel_1.add(txtEmail);

	}
}
