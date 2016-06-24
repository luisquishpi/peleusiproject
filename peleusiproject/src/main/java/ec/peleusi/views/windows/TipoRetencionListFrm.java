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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import ec.peleusi.controllers.TipoRetencionController;
import ec.peleusi.models.entities.TipoRetencion;
import ec.peleusi.utils.TipoRetencionEnum;
import ec.peleusi.utils.JPanelWithTable;
import ec.peleusi.utils.JTextFieldPH;
import java.awt.Font;
import javax.swing.BoxLayout;

public class TipoRetencionListFrm extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnEliminar;
	private JButton btnEditar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JButton btnBuscar;
	private JTextFieldPH txtBuscar;	
	private TipoRetencionCrudFrm tipoRetencionCrudFrm = new TipoRetencionCrudFrm();
	private TipoRetencion tipoRetencion;
	private JPanel pnlBuscar;
	private JPanel pnlTabla;
	private Integer totalItems = 0;
	JPanelWithTable<TipoRetencion> jPanelWithTable;	

	public TipoRetencionListFrm() {
		setTitle("Listado Tipos de Retencion");
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
		//Object[] cabecera = { "Id", "Código", "Tipo", "Descripcion", "Porcentaje" };
		TipoRetencionController tipoRetencionController = new TipoRetencionController();
		List<TipoRetencion> listaTipoRetencion = (List<TipoRetencion>) tipoRetencionController.getTipoRetencionList(txtBuscar.getText());

		if (totalItems == 0 && listaTipoRetencion != null)
			totalItems = listaTipoRetencion.size();	
		
		jPanelWithTable = new JPanelWithTable<TipoRetencion>(txtBuscar);
		jPanelWithTable.setCamposEntidad(new String[] { "id", "codigo", "tipoRetencionEnum", "descripcion", "porcentaje" });
		jPanelWithTable.setAnchoColumnas(new Integer[] { 0, 60, 80, 370 ,90 });
		jPanelWithTable.setColumnasFijas(new Integer[] { 0 });
		jPanelWithTable.setTotalItems(totalItems);
		String[] cabecera = new String[] { "ID", "CODIGO", "TIPO", "DESCRIPCION", "PORCENTAJE" };
		
		pnlTabla.removeAll();
		pnlTabla.add(jPanelWithTable.crear(cabecera, listaTipoRetencion), BorderLayout.CENTER);
		pnlTabla.revalidate();
		pnlTabla.repaint();
		txtBuscar.requestFocus();
	}			

	private void capturaYAgregaTipoRetencionATabla() {
		if (tipoRetencion == tipoRetencionCrudFrm.getTipoRetencion() && tipoRetencion.getId() != null) {
			txtBuscar.setText(tipoRetencion.getDescripcion());
			crearTabla();
		} else {
			if (tipoRetencionCrudFrm.getTipoRetencion() != null && tipoRetencionCrudFrm.getTipoRetencion().getId() != null) {
				totalItems++;
				txtBuscar.setText(tipoRetencionCrudFrm.getTipoRetencion().getDescripcion());
				crearTabla();
			}
		}
	}

	private boolean llenarEntidadParaEnviarATipoRetencionCrudFrm() {
		tipoRetencion = new TipoRetencion();
		if (jPanelWithTable.getJTable().getSelectedRow() != -1) {
			tipoRetencion.setId(Integer.parseInt(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 0).toString()));
			tipoRetencion.setCodigo(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 1).toString());
			tipoRetencion.setTipoRetencionEnum((TipoRetencionEnum)jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 2));			
			tipoRetencion.setDescripcion(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 3).toString());
			tipoRetencion.setPorcentaje(Double.parseDouble(jPanelWithTable.getJTable().getValueAt(jPanelWithTable.getJTable().getSelectedRow(), 4).toString()));
			tipoRetencionCrudFrm.setTipoRetencion(tipoRetencion);
			return true;
		}
		return false;
	}

	private void eliminarTipoRetencion() {
		if (llenarEntidadParaEnviarATipoRetencionCrudFrm()) {
			int confirmacion = JOptionPane.showConfirmDialog(null,
					"Está seguro que desea eliminar:\n\"" + tipoRetencion.getDescripcion() + "\"?", "Confirmación",
					JOptionPane.YES_NO_OPTION);
			if (confirmacion == 0) {
				TipoRetencionController tipoRetencionController = new TipoRetencionController();
				String error = tipoRetencionController.delete(tipoRetencion);
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
		tipoRetencionCrudFrm.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				capturaYAgregaTipoRetencionATabla();
			}
		});
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tipoRetencion = new TipoRetencion();
				tipoRetencionCrudFrm.setTipoRetencion(tipoRetencion);
				if (!tipoRetencionCrudFrm.isVisible()) {
					tipoRetencionCrudFrm.setModal(true);
					tipoRetencionCrudFrm.setVisible(true);
				}
			}
		});
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (llenarEntidadParaEnviarATipoRetencionCrudFrm()) {
					if (!tipoRetencionCrudFrm.isVisible()) {
						tipoRetencionCrudFrm.setModal(true);
						tipoRetencionCrudFrm.setVisible(true);
					}
				}
			}
		});
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarTipoRetencion();
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
		setBounds(100, 100, 613, 381);

		JPanel panelCabecera = new JPanel();
		panelCabecera.setPreferredSize(new Dimension(200, 70));
		panelCabecera.setBackground(Color.LIGHT_GRAY);
		getContentPane().add(panelCabecera, BorderLayout.NORTH);
		panelCabecera.setLayout(null);

		btnNuevo = new JButton("Nuevo");
		btnNuevo.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/new.png")));
		btnNuevo.setBounds(15, 14, 130, 39);
		panelCabecera.add(btnNuevo);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/edit.png")));
		btnEditar.setBounds(160, 14, 130, 39);
		panelCabecera.add(btnEditar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar
				.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/delete.png")));
		btnEliminar.setBounds(305, 14, 130, 39);
		panelCabecera.add(btnEliminar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar
				.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/cancel.png")));
		btnCancelar.setBounds(450, 14, 130, 39);
		panelCabecera.add(btnCancelar);

		JPanel panelCuerpo = new JPanel();
		getContentPane().add(panelCuerpo, BorderLayout.CENTER);
		panelCuerpo.setLayout(new BorderLayout(0, 0));
		
		pnlBuscar = new JPanel();
		panelCuerpo.add(pnlBuscar, BorderLayout.NORTH);
		pnlBuscar.setLayout(new BoxLayout(pnlBuscar, BoxLayout.X_AXIS));
		
		txtBuscar = new JTextFieldPH();	
		txtBuscar.setPlaceholder("Escriba código o descripción o porcentaje");
		txtBuscar.setFont(new Font(txtBuscar.getFont().getName(), Font.PLAIN, 16));
		pnlBuscar.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setIcon(new ImageIcon(TipoRetencionListFrm.class.getResource("/ec/peleusi/utils/images/search.png")));
		pnlBuscar.add(btnBuscar);
		
		pnlTabla = new JPanel();
		panelCuerpo.add(pnlTabla, BorderLayout.CENTER);
		pnlTabla.setLayout(new BoxLayout(pnlTabla, BoxLayout.X_AXIS));		
	}
}
