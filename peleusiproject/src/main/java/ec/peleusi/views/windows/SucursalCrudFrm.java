package ec.peleusi.views.windows;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.EmpresaController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Empresa;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
	private JTextField txtUrl;
	private JButton btnNuevo;
	private JButton btnGuardar;
	private JButton btnEliminar;
	private JButton btnCancelar;
	@SuppressWarnings("rawtypes")	
	private JComboBox cmbEmpresa;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCiudad;
	private JLabel lblFoto;
	private JButton btnSeleccionar;
	private JTextField txtImagen;
	

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
		lista=ciudadController.CiudadList();
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
	        if ( txtNombre.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtEmail.getText().isEmpty() || txtUrl.getText().isEmpty() || txtFax.getText().isEmpty() || txtImagen.getText().isEmpty())
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
			txtUrl.setText("");
			txtImagen.setText("");			
			txtNombre.requestFocus();		
			
		}	 
	
	private static byte[] readBytesFromFile(String filePath) throws IOException {
	        File inputFile = new File(filePath);
	        FileInputStream inputStream = new FileInputStream(inputFile);
	         
	        byte[] fileBytes = new byte[(int) inputFile.length()];
	        inputStream.read(fileBytes);
	        inputStream.close();	
	        
	        System.out.println("ruta " +fileBytes);
	        return fileBytes;
	        
	    }
	
	public void crearEventos()
	{
		

		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				FileNameExtensionFilter filtro= new FileNameExtensionFilter("Formatos de Archivos JPEG (*.JPG; *.JPEG) ", "jpg","jpeg");				
				JFileChooser archivo= new JFileChooser();
				archivo.addChoosableFileFilter(filtro);
				archivo.setDialogTitle("Abrir archivo");
				int ventana= archivo.showOpenDialog(null) ;
				if(ventana==JFileChooser.APPROVE_OPTION){
					File file=archivo.getSelectedFile();
					
					txtImagen.setText(String.valueOf(file));
					Image foto= getToolkit().getImage(txtImagen.getText());
					foto=foto.getScaledInstance(110, 110, Image.SCALE_DEFAULT);
					lblFoto.setIcon(new ImageIcon(foto));		
					
				}		
				
			}
		});	
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
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		
		cmbEmpresa = new JComboBox<Empresa>();
		cmbEmpresa.setBounds(95, 28, 249, 20);
		panel_1.add(cmbEmpresa);
		
		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setBounds(39, 90, 46, 14);
		panel_1.add(lblCiudad);
		
		cmbCiudad = new JComboBox<Ciudad>();
		cmbCiudad.setBounds(95, 87, 249, 20);
		panel_1.add(cmbCiudad);
		
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
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(95, 211, 249, 20);
		panel_1.add(txtEmail);
		
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
		
		txtImagen = new JTextField();
		txtImagen.setColumns(10);
		txtImagen.setBounds(95, 259, 30, 20);
		panel_1.add(txtImagen);
		txtImagen.setVisible(false);

	}
}
