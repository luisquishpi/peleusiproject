package ec.peleusi.views.windows;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mysql.jdbc.Blob;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.controllers.EmpresaController;
import ec.peleusi.models.entities.Ciudad;
import ec.peleusi.models.entities.Empresa;



public class EmpresaCrudFrm extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtNombreEmpresa;
	private JTextField txtIdentificacion;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtEMail;
	private JTextField txtUrl;
	private JTextField txtFax;
	private JTextField txtImagen;
	private JLabel lblFoto;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbCiudad;
	private JButton btnSeleccionar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnEliminar;

	
	public EmpresaCrudFrm() {
		crearControles();
		crearEventos();
		//llenarCiudad();

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void llenarCiudad()
	{		
		CiudadController ciudadController= new CiudadController();
		List<Ciudad> lista;
		lista=ciudadController.CiudadList();
		cmbCiudad.setModel(new DefaultComboBoxModel(lista.toArray()));	
	}
	 private boolean isCamposLlenos() {
	        boolean llenos = true;
	        if (txtIdentificacion.getText().isEmpty() || txtNombreEmpresa.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtEMail.getText().isEmpty() || txtUrl.getText().isEmpty() || txtFax.getText().isEmpty())
	            llenos = false;
	        return llenos;
	    }

	 private void limpiarCampos()
		{
			txtIdentificacion.setText("");
			txtNombreEmpresa.setText("");
			txtDireccion.setText("");
			txtTelefono.setText("");
			txtFax.setText("");
			txtEMail.setText("");
			txtUrl.setText("");
			txtImagen.setText("");
			
			txtIdentificacion.requestFocus();		
			
		}
	private void crearEventos()
	{
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
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
		
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (!isCamposLlenos()) {
					JOptionPane.showMessageDialog(null, "No deje campos vacíos");
					return;
				}
				
				
			
				FileInputStream archivofoto=null;
				
				try {
					
					archivofoto= new FileInputStream(txtImagen.getText());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
				
				
				Ciudad ciudad=(Ciudad) cmbCiudad.getSelectedItem();
			   	Empresa empresa= new Empresa(txtNombreEmpresa.getText(), txtIdentificacion.getText(), txtDireccion.getText(), txtTelefono.getText(), txtFax.getText(), txtEMail.getText(), txtUrl.getText(),  archivofoto, txtImagen.getText(), ciudad);
				EmpresaController empresaController= new EmpresaController();
				String error= empresaController.createEmpresa(empresa);
				if (error == null) {
					JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					limpiarCampos();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}

				
				
				
			}
		});
		
	}
	private void crearControles()
	{
		setBounds(100, 100, 599, 421);		
		JPanel pnlCabecera = new JPanel();
		pnlCabecera.setLayout(null);
		pnlCabecera.setPreferredSize(new Dimension(200, 70));
		pnlCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(pnlCabecera, BorderLayout.NORTH);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(EmpresaCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(10, 11, 130, 39);
		pnlCabecera.add(btnNuevo);
		
		btnGuardar = new JButton("Guardar");
		
		btnGuardar.setIcon(new ImageIcon(EmpresaCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(150, 11, 130, 39);
		pnlCabecera.add(btnGuardar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(EmpresaCrudFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(290, 11, 130, 39);
		pnlCabecera.add(btnEliminar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(EmpresaCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(430, 11, 130, 39);
		pnlCabecera.add(btnCancelar);
		
		JPanel pnlCuerpo = new JPanel();
		pnlCuerpo.setLayout(null);
		getContentPane().add(pnlCuerpo, BorderLayout.CENTER);
		
		JLabel lblNombreDeLa = new JLabel("Nombre");
		lblNombreDeLa.setBounds(10, 62, 65, 14);
		pnlCuerpo.add(lblNombreDeLa);
		
		txtNombreEmpresa = new JTextField(13);	
						
		txtNombreEmpresa.setColumns(10);
		txtNombreEmpresa.setBounds(79, 56, 414, 20);
		pnlCuerpo.add(txtNombreEmpresa);
		
		JLabel lblRuc = new JLabel("RUC");
		lblRuc.setBounds(10, 33, 65, 14);
		pnlCuerpo.add(lblRuc);
		
		txtIdentificacion = new JTextField(10);
		txtIdentificacion.setBounds(79, 27, 180, 20);
		pnlCuerpo.add(txtIdentificacion);
		
		JLabel lblCuidad = new JLabel("Cuidad");
		lblCuidad.setBounds(10, 90, 65, 14);
		pnlCuerpo.add(lblCuidad);
		
		
		cmbCiudad = new JComboBox<Ciudad>();
		cmbCiudad.setBounds(79, 84, 304, 20);
		pnlCuerpo.add(cmbCiudad);
		
		JLabel lblDirecci = new JLabel("Dirección");
		lblDirecci.setBounds(10, 121, 65, 14);
		pnlCuerpo.add(lblDirecci);
		
		txtDireccion = new JTextField(10);
		txtDireccion.setBounds(79, 115, 304, 20);
		pnlCuerpo.add(txtDireccion);
		
		JLabel lblTelfono = new JLabel("Teléfono");
		lblTelfono.setBounds(10, 152, 65, 14);
		pnlCuerpo.add(lblTelfono);
		
		txtTelefono = new JTextField(10);
		txtTelefono.setBounds(79, 146, 304, 20);
		pnlCuerpo.add(txtTelefono);
		
		JLabel lblEMail = new JLabel("E - mail");
		lblEMail.setBounds(10, 211, 65, 14);
		pnlCuerpo.add(lblEMail);
		
		txtEMail = new JTextField(10);
		txtEMail.setBounds(79, 205, 304, 20);
		pnlCuerpo.add(txtEMail);
		
		JLabel lblUrl = new JLabel("Url");
		lblUrl.setBounds(10, 242, 65, 14);
		pnlCuerpo.add(lblUrl);
		
		txtUrl = new JTextField(10);
		txtUrl.setBounds(79, 236, 304, 20);
		pnlCuerpo.add(txtUrl);
		
		JLabel lblFax = new JLabel("Fax");
		lblFax.setBounds(10, 183, 65, 14);
		pnlCuerpo.add(lblFax);
		
		txtFax = new JTextField(10);
		txtFax.setBounds(79, 177, 304, 20);
		pnlCuerpo.add(txtFax);
		
		txtImagen = new JTextField();
		txtImagen.setBounds(79, 267, 304, 20);
		pnlCuerpo.add(txtImagen);
		txtImagen.setColumns(10);
		
		JLabel lblImagen = new JLabel("Imagen");
		lblImagen.setBounds(10, 267, 65, 14);
		pnlCuerpo.add(lblImagen);
		
		btnSeleccionar = new JButton("Seleccionar");
		
		btnSeleccionar.setBounds(421, 263, 89, 23);
		pnlCuerpo.add(btnSeleccionar);
		
		lblFoto = new JLabel("");
		lblFoto.setBounds(393, 87, 138, 169);
		pnlCuerpo.add(lblFoto);
		
	}
	
}
