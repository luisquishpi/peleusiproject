package ec.peleusi.views.windows;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDesktopPane;
import java.awt.Color;

public class PrincipalFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CiudadListFrm ciudaListFrm;
	private CategoriaProductoCrudFrm categoriaProductoCrudFrm;
	private ProductoCrudFrm productoCrudFrm;
	private UnidadMedidaListFrm unidadMedidaListFrm;
	private EmpresaCrudFrm empresaCrudFrm;
	private SucursalCrudFrm sucursalCrudFrm;
	private TarifaIceListFrm tarifaIceListFrm;
	private TarifaIvaListFrm tarifaIvaListFrm;
	private TipoCalificacionClienteListFrm tipoCalificacionClienteListFrm;
	private TipoGastoDeducibleListFrm tipoGastoDeducibleListFrm;
	private TipoIdentificacionListFrm tipoIdentificacionListFrm;
	private TipoPagoListFrm tipoPagoListFrm;
	private TipoPrecioListFrm tipoPrecioListFrm;
	private TipoRetencionListFrm tipoRetencionListFrm;
	private ClienteListFrm clienteListFrm;
	private SeteoCrudFrm seteoCrudFrm;
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
	public PrincipalFrm() {
		this.setExtendedState(MAXIMIZED_BOTH);

		setTitle("Peleusí v.1.0.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 600);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnMaestros = new JMenu("Maestros");
		menuBar.add(mnMaestros);

		JMenuItem mntmPaiscrudfrm = new JMenuItem("Ciudad");
		mntmPaiscrudfrm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ciudaListFrm == null || ciudaListFrm.isClosed()) {
					ciudaListFrm = new CiudadListFrm();
					dpContenedor.add(ciudaListFrm);
					ciudaListFrm.show();
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
				if (unidadMedidaListFrm == null || unidadMedidaListFrm.isClosed()) {
					unidadMedidaListFrm = new UnidadMedidaListFrm();
					dpContenedor.add(unidadMedidaListFrm);
					unidadMedidaListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmUnidadDeMedida);

		JMenuItem mntmIva = new JMenuItem("IVA");
		mntmIva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tarifaIvaListFrm == null || tarifaIvaListFrm.isClosed()) {
					tarifaIvaListFrm = new TarifaIvaListFrm();
					dpContenedor.add(tarifaIvaListFrm);
					tarifaIvaListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmIva);

		JMenuItem mntmIce = new JMenuItem("ICE");
		mntmIce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tarifaIceListFrm == null || tarifaIceListFrm.isClosed()) {
					tarifaIceListFrm = new TarifaIceListFrm();
					dpContenedor.add(tarifaIceListFrm);
					tarifaIceListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmIce);

		

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
				if (tipoCalificacionClienteListFrm == null || tipoCalificacionClienteListFrm.isClosed()) {
					tipoCalificacionClienteListFrm = new TipoCalificacionClienteListFrm();
					dpContenedor.add(tipoCalificacionClienteListFrm);
					tipoCalificacionClienteListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmCalificacinPersona);

		JMenuItem mntmGastosDeducibles = new JMenuItem("Gastos Deducibles");
		mntmGastosDeducibles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoGastoDeducibleListFrm == null || tipoGastoDeducibleListFrm.isClosed()) {
					tipoGastoDeducibleListFrm = new TipoGastoDeducibleListFrm();
					dpContenedor.add(tipoGastoDeducibleListFrm);
					tipoGastoDeducibleListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmGastosDeducibles);

		JMenuItem mntmTipoDeIdentificacin = new JMenuItem("Tipo de Identificación");
		mntmTipoDeIdentificacin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoIdentificacionListFrm == null || tipoIdentificacionListFrm.isClosed()) {
					tipoIdentificacionListFrm = new TipoIdentificacionListFrm();
					dpContenedor.add(tipoIdentificacionListFrm);
					tipoIdentificacionListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTipoDeIdentificacin);

		JMenuItem mntmTiposDePagos = new JMenuItem("Tipos de Pagos");
		mntmTiposDePagos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoPagoListFrm == null || tipoPagoListFrm.isClosed()) {
					tipoPagoListFrm = new TipoPagoListFrm();
					dpContenedor.add(tipoPagoListFrm);
					tipoPagoListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTiposDePagos);

		JMenuItem mntmTiposDePrecios = new JMenuItem("Tipos de Precios");
		mntmTiposDePrecios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoPrecioListFrm == null || tipoPrecioListFrm.isClosed()) {
					tipoPrecioListFrm = new TipoPrecioListFrm();
					dpContenedor.add(tipoPrecioListFrm);
					tipoPrecioListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTiposDePrecios);

		JMenuItem mntmTiposDeRetencin = new JMenuItem("Lista Tipos de Retención");
		mntmTiposDeRetencin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tipoRetencionListFrm == null || tipoRetencionListFrm.isClosed()) {
					tipoRetencionListFrm = new TipoRetencionListFrm();
					dpContenedor.add(tipoRetencionListFrm);
					tipoRetencionListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmTiposDeRetencin);
		
		JMenuItem mntmSeteos = new JMenuItem("Seteos");
		mntmSeteos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (seteoCrudFrm == null || seteoCrudFrm.isClosed()) {
					seteoCrudFrm = new SeteoCrudFrm();
					dpContenedor.add(seteoCrudFrm);
					seteoCrudFrm.show();
				}
			}
		});
		mnMaestros.add(mntmSeteos);
		
		JMenuItem mntmListaDeClientes = new JMenuItem("Lista de Clientes");
		mntmListaDeClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clienteListFrm == null || clienteListFrm.isClosed()) {
					clienteListFrm = new ClienteListFrm();
					dpContenedor.add(clienteListFrm);
					clienteListFrm.show();
				}
			}
		});
		mnMaestros.add(mntmListaDeClientes);
		
		JMenu mnConfiguraciones = new JMenu("Configuraciones");
		menuBar.add(mnConfiguraciones);
		
		JMenu mnAdministracin = new JMenu("Administración");
		menuBar.add(mnAdministracin);
		
		JMenuItem mntmEmpresa_1 = new JMenuItem("Empresa");
		mntmEmpresa_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (empresaCrudFrm == null || empresaCrudFrm.isClosed()) {
					empresaCrudFrm = new EmpresaCrudFrm();
					dpContenedor.add(empresaCrudFrm);
					empresaCrudFrm.show();
				}
			}
		});
		mnAdministracin.add(mntmEmpresa_1);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		dpContenedor = new JDesktopPane();
		dpContenedor.setBackground(Color.WHITE);
		contentPane.add(dpContenedor, BorderLayout.CENTER);
	}
}
