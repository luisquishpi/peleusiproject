package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import ec.peleusi.views.windows.CiudadCrudFrm;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;

public class PrincipalFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CiudadCrudFrm ciudaCrudFrm;
	private CategoriaProductoCrudFrm categoriaProductoCrudFrm;
	private ProductoCrudFrm productoCrudFrm;
	private UnidadMedidaCrudFrm unidadMedidaCrudFrm;
	private EmpresaCrudFrm empresaCrudFrm;
	private SucursalCrudFrm sucursalCrudFrm;
	private TarifaIceCrudFrm tarifaIceCrudFrm;
	private TarifaIvaCrudFrm tarifaIvaCrudFrm;
	private TipoCalificacionPersonaCrudFrm tipoCalificacionPersonaCrudFrm;
	private TipoGastoDeducibleCrudFrm tipoGastoDeducibleCrudFrm;
	private TipoIdentificacionCrudFrm tipoIdentificacionCrudFrm;
	private TipoPagoCrudFrm tipoPagoCrudFrm;
	private TipoPrecioCrudFrm tipoPrecioCrudFrm;
	private TipoRetencionCrudFrm tipoRetencionCrudFrm;
	private PersonaCrudFrm personaCrudFrm;
	private JDesktopPane dpContenedor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalFrm frame = new PrincipalFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PrincipalFrm() {
		setTitle("Peleusí v.1.0.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 457);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnMaestros = new JMenu("Maestros");
		menuBar.add(mnMaestros);

		JMenuItem mntmPaiscrudfrm = new JMenuItem("Ciudad");
		mntmPaiscrudfrm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ciudaCrudFrm == null || ciudaCrudFrm.isClosed()) {
					ciudaCrudFrm = new CiudadCrudFrm();
					dpContenedor.add(ciudaCrudFrm);
					ciudaCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmPaiscrudfrm);

		JMenuItem mntmCategorasDeProductos = new JMenuItem("Categorías de Productos");
		mntmCategorasDeProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (categoriaProductoCrudFrm == null || categoriaProductoCrudFrm.isClosed()) {
					categoriaProductoCrudFrm = new CategoriaProductoCrudFrm();
					dpContenedor.add(categoriaProductoCrudFrm);
					categoriaProductoCrudFrm.show();
				}

			}
		});
		mnMaestros.add(mntmCategorasDeProductos);

		JMenuItem mntmProductos = new JMenuItem("Productos");
		mntmProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (productoCrudFrm == null || productoCrudFrm.isClosed()) {
					productoCrudFrm = new ProductoCrudFrm();
					dpContenedor.add(productoCrudFrm);
					productoCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmProductos);

		JMenuItem mntmUnidadDeMedida = new JMenuItem("Unidad de Medida");
		mntmUnidadDeMedida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (unidadMedidaCrudFrm == null || unidadMedidaCrudFrm.isClosed()) {
					unidadMedidaCrudFrm = new UnidadMedidaCrudFrm();
					dpContenedor.add(unidadMedidaCrudFrm);
					unidadMedidaCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmUnidadDeMedida);

		JMenuItem mntmIva = new JMenuItem("IVA");
		mntmIva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tarifaIvaCrudFrm == null || tarifaIvaCrudFrm.isClosed()) {
					tarifaIvaCrudFrm = new TarifaIvaCrudFrm();
					dpContenedor.add(tarifaIvaCrudFrm);
					tarifaIvaCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmIva);

		JMenuItem mntmIce = new JMenuItem("ICE");
		mntmIce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tarifaIceCrudFrm == null || tarifaIceCrudFrm.isClosed()) {
					tarifaIceCrudFrm = new TarifaIceCrudFrm();
					dpContenedor.add(tarifaIceCrudFrm);
					tarifaIceCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmIce);

		JMenuItem mntmEmpresa = new JMenuItem("Empresa");
		mntmEmpresa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (empresaCrudFrm == null || empresaCrudFrm.isClosed()) {
					empresaCrudFrm = new EmpresaCrudFrm();
					dpContenedor.add(empresaCrudFrm);
					empresaCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmEmpresa);

		JMenuItem mntmSocursal = new JMenuItem("Socursal");
		mntmSocursal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (sucursalCrudFrm == null || sucursalCrudFrm.isClosed()) {
					sucursalCrudFrm = new SucursalCrudFrm();
					dpContenedor.add(sucursalCrudFrm);
					sucursalCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmSocursal);

		JMenuItem mntmCalificacinPersona = new JMenuItem("Calificación Persona");
		mntmCalificacinPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoCalificacionPersonaCrudFrm == null || tipoCalificacionPersonaCrudFrm.isClosed()) {
					tipoCalificacionPersonaCrudFrm = new TipoCalificacionPersonaCrudFrm();
					dpContenedor.add(tipoCalificacionPersonaCrudFrm);
					tipoCalificacionPersonaCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmCalificacinPersona);

		JMenuItem mntmGastosDeducibles = new JMenuItem("Gastos Deducibles");
		mntmGastosDeducibles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoGastoDeducibleCrudFrm == null || tipoGastoDeducibleCrudFrm.isClosed()) {
					tipoGastoDeducibleCrudFrm = new TipoGastoDeducibleCrudFrm();
					dpContenedor.add(tipoGastoDeducibleCrudFrm);
					tipoGastoDeducibleCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmGastosDeducibles);

		JMenuItem mntmTipoDeIdentificacin = new JMenuItem("Tipo de Identificación");
		mntmTipoDeIdentificacin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoIdentificacionCrudFrm == null || tipoIdentificacionCrudFrm.isClosed()) {
					tipoIdentificacionCrudFrm = new TipoIdentificacionCrudFrm();
					dpContenedor.add(tipoIdentificacionCrudFrm);
					tipoIdentificacionCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTipoDeIdentificacin);

		JMenuItem mntmTiposDePagos = new JMenuItem("Tipos de Pagos");
		mntmTiposDePagos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoPagoCrudFrm == null || tipoPagoCrudFrm.isClosed()) {
					tipoPagoCrudFrm = new TipoPagoCrudFrm();
					dpContenedor.add(tipoPagoCrudFrm);
					tipoPagoCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTiposDePagos);

		JMenuItem mntmTiposDePrecios = new JMenuItem("Tipos de Precios");
		mntmTiposDePrecios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoPrecioCrudFrm == null || tipoPrecioCrudFrm.isClosed()) {
					tipoPrecioCrudFrm = new TipoPrecioCrudFrm();
					dpContenedor.add(tipoPrecioCrudFrm);
					tipoPrecioCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTiposDePrecios);

		JMenuItem mntmTiposDeRetencin = new JMenuItem("Tipos de Retención");
		mntmTiposDeRetencin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoRetencionCrudFrm == null || tipoRetencionCrudFrm.isClosed()) {
					tipoRetencionCrudFrm = new TipoRetencionCrudFrm();
					dpContenedor.add(tipoRetencionCrudFrm);
					tipoRetencionCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTiposDeRetencin);
		
		JMenuItem mntmPersonas = new JMenuItem("Personas");
		mntmPersonas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (personaCrudFrm == null || personaCrudFrm.isClosed()) {
					personaCrudFrm = new PersonaCrudFrm();
					dpContenedor.add(personaCrudFrm);
					personaCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmPersonas);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		dpContenedor = new JDesktopPane();
		contentPane.add(dpContenedor, BorderLayout.CENTER);
	}
}
