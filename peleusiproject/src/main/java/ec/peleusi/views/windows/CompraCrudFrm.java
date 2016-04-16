package ec.peleusi.views.windows;



import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class CompraCrudFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	public CompraCrudFrm() {
		setBounds(100, 100, 709, 518);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(200, 70));
		panel.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panel, BorderLayout.NORTH);
		
		JButton button = new JButton("Nuevo");
		button.setBounds(10, 11, 130, 39);
		panel.add(button);
		
		JButton button_1 = new JButton("Guardar");
		button_1.setBounds(150, 11, 130, 39);
		panel.add(button_1);
		
		JButton button_2 = new JButton("Eliminar");
		button_2.setBounds(290, 11, 130, 39);
		panel.add(button_2);
		
		JButton button_3 = new JButton("Cancelar");
		button_3.setBounds(430, 11, 130, 39);
		panel.add(button_3);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos proveedor", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 255)));
		panel_2.setBounds(-11, 49, 660, 159);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(106, 24, 165, 20);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JLabel lblContribuyente = new JLabel("Contribuyente:");
		lblContribuyente.setBounds(10, 49, 81, 14);
		panel_2.add(lblContribuyente);
		
		JLabel lblNewLabel = new JLabel("Ruc/CI:");
		lblNewLabel.setBounds(10, 30, 46, 14);
		panel_2.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(106, 46, 522, 20);
		panel_2.add(textField_1);
		
		JLabel lblDireccin = new JLabel("Dirección:");
		lblDireccin.setBounds(10, 71, 81, 14);
		panel_2.add(lblDireccin);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(106, 68, 522, 20);
		panel_2.add(textField_2);
		
		JLabel label = new JLabel("Dirección:");
		label.setBounds(10, 97, 81, 14);
		panel_2.add(label);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(106, 94, 522, 20);
		panel_2.add(textField_3);

	}
}
