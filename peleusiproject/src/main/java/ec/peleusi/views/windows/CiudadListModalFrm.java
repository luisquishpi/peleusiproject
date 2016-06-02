package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ec.peleusi.controllers.CiudadController;
import ec.peleusi.models.entities.Ciudad;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;

public class CiudadListModalFrm extends JDialog {
	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private CiudadCrudFrm ciudadCrudFrm = new CiudadCrudFrm();
	private Ciudad ciudad;
	private JTextFieldPH txtBuscar;
	JPanelWithTable<Ciudad> jPanelWithTable;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;

	public static void main(String[] args) {
		try {
			CiudadListModalFrm dialog = new CiudadListModalFrm();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CiudadListModalFrm() {
		crearControles();
		crearEventos();
		crearTabla();
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
				txtBuscar.requestFocus();
			}
		});
	}

	private void crearTabla() {
		CiudadController ciudadController = new CiudadController();
		List<Ciudad> listaCiudad = ciudadController.ciudadList(txtBuscar.getText());

		if (totalItems == 0 && listaCiudad != null)
			totalItems = listaCiudad.size();

		jPanelWithTable = new JPanelWithTable<Ciudad>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 400 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRE" };

		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaCiudad), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();

		if (jPanelWithTable.getJTable() != null) {
			jPanelWithTable.getJTable().addKeyListener(new KeyAdapter() {
				public void keyReleased(KeyEvent e) {
					if (KeyEvent.VK_ENTER == e.getKeyCode()) {
						aceptar();
					}
				}
			});
		}
		txtBuscar.requestFocus();
	}

	private void aceptar() {
		int fila = jPanelWithTable.getJTable().getSelectedRow();
		if (fila != -1) {
			ciudad = new Ciudad();
			ciudad.setId(Integer.parseInt(jPanelWithTable.getJTable()
					.getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			ciudad.setNombre(
					jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			ciudadCrudFrm.setCiudad(ciudad);
			ciudadCrudFrm.setCiudad(ciudad);
		}
		dispose();
	}

	public Ciudad getCiudad() {
		return ciudad;
	}

	private void crearEventos() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearTabla();
			}
		});

		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aceptar();
			}
		});

		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (KeyEvent.VK_ENTER == e.getKeyCode()) {
					crearTabla();
					if (jPanelWithTable.getJTable() != null) {
						jPanelWithTable.getJTable().addRowSelectionInterval(0, 0);
						jPanelWithTable.getJTable().requestFocus();
					}
				}

			}
		});
	}

	public void crearControles() {
		setTitle("Lista de Ciudades");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			pnlBuscar = new JPanel();
			contentPanel.add(pnlBuscar, BorderLayout.NORTH);
			pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));
			{
				txtBuscar = new JTextFieldPH();
				txtBuscar.setPlaceholder("Escriba nombre");
				txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
				pnlBuscar.add(txtBuscar);
				txtBuscar.setColumns(10);
			}
			{
				btnBuscar = new JButton("Buscar");
				btnBuscar.setIcon(new ImageIcon(
						UnidadMedidaListModalFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
				pnlBuscar.add(btnBuscar);
			}
		}
		{
			pnlTabla = new JPanel();
			contentPanel.add(pnlTabla, BorderLayout.CENTER);
			pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnAceptar = new JButton("Aceptar");
				btnAceptar.setIcon(
						new ImageIcon(CiudadListModalFrm.class.getResource("/ec/peleusi/utils/images/Select.png")));
				btnAceptar.setActionCommand("OK");
				buttonPane.add(btnAceptar);
				getRootPane().setDefaultButton(btnAceptar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.setIcon(
						new ImageIcon(CiudadListModalFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
}
