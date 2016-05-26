package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import ec.peleusi.controllers.TarifaIceController;
import ec.peleusi.models.entities.TarifaIce;
import ec.peleusi.utils.Formatos;
import javax.swing.JFormattedTextField;

public class TarifaIceCrudFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JLabel lblNombre;
	private JTextField txtNombre;
	private JTextField txtCodigo;
	private JFormattedTextField txtPorcentaje;
	private TarifaIce tarifaIce;
	int limitecaja = 15;

	public TarifaIceCrudFrm() {
		setTitle("Tarifa Ice");
		crearControles();
		crearEventos();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0){
				llenarCamposConEntidad();
				txtCodigo.requestFocus();
			}
		});
	}
	
	private void llenarCamposConEntidad(){
		if (tarifaIce !=null && tarifaIce.getId() != null){
			this.setTitle("Actualizar Tarifa ICE");
			btnGuardar.setText("Actualizar");
			limpiarCampos();
			txtCodigo.setText(tarifaIce.getCodigo());
			txtNombre.setText(tarifaIce.getNombre());		
			txtPorcentaje.setText(Double.toString(tarifaIce.getPorcentaje()));
		}else{
			this.setTitle("Creando Tarifa Ice");
			btnGuardar.setText("Guardar");
			limpiarCampos();			
		}
	}
	
	private void llenarEntidadAntesDeGuardar(){
		tarifaIce.setCodigo(txtCodigo.getText());
		tarifaIce.setNombre(txtNombre.getText());
		tarifaIce.setPorcentaje(Double.parseDouble(txtPorcentaje.getText().toString()));
		} 
	
	private void guardarNuevoTarifaIce(){
		tarifaIce = new TarifaIce();
		llenarEntidadAntesDeGuardar();
		TarifaIceController tarifaIceController = new TarifaIceController();
		String error = tarifaIceController.createTarifaIce(tarifaIce);
		if (error == null){
			JOptionPane.showMessageDialog(null, "Guardado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}
	
	private void actualizarTarifaIce(){
		llenarEntidadAntesDeGuardar();
		TarifaIceController tarifaIceController = new TarifaIceController();
		String error = tarifaIceController.updateTarifaIce(tarifaIce);
		if (error == null){
			JOptionPane.showMessageDialog(null, "Actualizado correctamente", "Éxito", JOptionPane.PLAIN_MESSAGE);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
		}		
	}

	public TarifaIce getTarifaIce() {
		return tarifaIce;
	}
	
	public void setTarifaIce(TarifaIce tarifaIce){
		this.tarifaIce = new TarifaIce();
		this.tarifaIce = tarifaIce;
	}

	private void limpiarCampos() {
		txtCodigo.setText("");
		txtNombre.setText("");
		txtPorcentaje.setText("0");
		txtCodigo.requestFocus();		
	}

	private boolean isCamposLlenos() {
		boolean llenos = true;
		if (txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty() || txtPorcentaje.getText().isEmpty())
			llenos = false;
		return llenos;
	}
	
	private void crearEventos() {
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tarifaIce = new TarifaIce();
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
				if (tarifaIce != null && tarifaIce.getId()!= null) {
					actualizarTarifaIce();
				} else {
					guardarNuevoTarifaIce();
				}
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tarifaIce = null;
				dispose();
			}
		});
	}

	private void crearControles() {
		setBounds(100, 100, 505, 255);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TarifaIceCrudFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(20, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setIcon(new ImageIcon(TarifaIceCrudFrm.class.getResource("/ec/peleusi/utils/images/save.png")));
		btnGuardar.setBounds(180, 14, 130, 39);
		panelCabecera.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(TarifaIceCrudFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(340, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(null);

		lblNombre = new JLabel("Nombre*");
		lblNombre.setBounds(50, 70, 101, 14);
		panelCuerpo.add(lblNombre);

		txtNombre = new JTextField(50);
		txtNombre.setBounds(133, 67, 214, 20);
		panelCuerpo.add(txtNombre);
		txtNombre.setColumns(50);

		JLabel lblPorcentaje = new JLabel("Porcentaje*");
		lblPorcentaje.setBounds(50, 112, 85, 14);
		panelCuerpo.add(lblPorcentaje);

		JLabel lblCodigo = new JLabel("Còdigo*");
		lblCodigo.setBounds(50, 29, 46, 14);
		panelCuerpo.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setToolTipText("");
		txtCodigo.setBounds(133, 26, 214, 20);
		panelCuerpo.add(txtCodigo);
		txtCodigo.setColumns(15);

		txtCodigo.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				if (txtCodigo.getText().length() == limitecaja) {
					e.consume();
				}
			}

			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
			}
		});

		txtPorcentaje = new JFormattedTextField();
		txtPorcentaje.setToolTipText("");
		txtPorcentaje.setSize(213, 20);
		txtPorcentaje.setLocation(134, 109);
		txtPorcentaje.setFormatterFactory(new Formatos().getDecimalFormat());
		panelCuerpo.add(txtPorcentaje);
	}
}
