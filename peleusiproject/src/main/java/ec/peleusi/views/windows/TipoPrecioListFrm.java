package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import ec.peleusi.controllers.TipoPrecioController;
import ec.peleusi.models.entities.TipoPrecio;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class TipoPrecioListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JTextFieldPH txtBuscar;
	private JButton btnBuscar;
	private TipoPrecioCrudFrm tipoPrecioCrudFrm = new TipoPrecioCrudFrm();
	private TipoPrecio tipoPrecio;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<TipoPrecio> jPanelWithTable;

	public TipoPrecioListFrm() {
		setTitle("Listado de Tipo Precio");
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
		TipoPrecioController tipoPrecioController = new TipoPrecioController();
		List<TipoPrecio> listaTipoPrecio = tipoPrecioController.getTipoPrecioList(txtBuscar.getText());

		if (totalItems == 0 && listaTipoPrecio != null)
			totalItems = listaTipoPrecio.size();

		jPanelWithTable = new JPanelWithTable<TipoPrecio>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "nombre", "porcentaje" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 400,198 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "NOMBRE", "PORCENTAJE" };	
		
		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaTipoPrecio), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();	
	}
	
	private void capturaYAgregaTipoPrecioATabla() {
		if (tipoPrecio == tipoPrecioCrudFrm.getTipoPrecio() && tipoPrecio.getId() != null) {
			crearTabla();
		} else {
			if (tipoPrecioCrudFrm.getTipoPrecio() != null && tipoPrecioCrudFrm.getTipoPrecio().getId() != null) {
				totalItems++;
				txtBuscar.setText(tipoPrecioCrudFrm.getTipoPrecio().getNombre());				
				crearTabla();
			}
		}
	}
	private boolean llenarEntidadParaEnviarATipoPrecioCrudFrm() {
		tipoPrecio= new TipoPrecio();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			tipoPrecio.setId(Integer.parseInt(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			tipoPrecio.setNombre(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			tipoPrecio.setPorcentaje(Double.parseDouble(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2).toString()));
			tipoPrecioCrudFrm.setTipoPrecio(tipoPrecio);
			return true;
		}
		return false;
	}
	
	private void eliminarTipoPrecio(){		
		if (llenarEntidadParaEnviarATipoPrecioCrudFrm()){
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoPrecio.getNombre() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoPrecioController tipoPrecioController = new TipoPrecioController();
				String error = tipoPrecioController.deleteTipoPrecio(tipoPrecio);
				if (error == null) {
					totalItems--;
					crearTabla();
				} else {
					JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}			
		}		
	}
	private void crearEventos() {
		tipoPrecioCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
			capturaYAgregaTipoPrecioATabla();
			}
	});

		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoPrecio = new TipoPrecio();
				tipoPrecioCrudFrm.setTipoPrecio(tipoPrecio);
				if (!tipoPrecioCrudFrm.isVisible()) {
					tipoPrecioCrudFrm.setModal(true);
					tipoPrecioCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (llenarEntidadParaEnviarATipoPrecioCrudFrm()) {
					if (!tipoPrecioCrudFrm.isVisible()) {
						tipoPrecioCrudFrm.setModal(true);
						tipoPrecioCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoPrecio();
			}
		});
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				crearTabla();
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

	private void crearControles() {
		setIconifiable(true);
		setClosable(true);
		setBounds(100, 100, 611, 379);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setToolTipText("");
		btnCancelar.setIcon(new ImageIcon(TipoPrecioListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(450, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));
		
		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));
		
		txtBuscar = new JTextFieldPH();
		txtBuscar.setPlaceholder("Escriba nombre o porcentaje");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(TipoPagoListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);
		
		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));		
	}
}
