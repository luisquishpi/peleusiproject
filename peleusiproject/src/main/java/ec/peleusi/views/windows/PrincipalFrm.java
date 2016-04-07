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
	private CiudadCrudFrm paisCrudFrm;
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		JMenuItem mntmPaiscrudfrm = new JMenuItem("PaisCrudFrm");
		mntmPaiscrudfrm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (paisCrudFrm == null || paisCrudFrm.isClosed()) {
					paisCrudFrm = new CiudadCrudFrm();
					dpContenedor.add(paisCrudFrm);
					paisCrudFrm.show();
				}
			}
		});
		mnArchivo.add(mntmPaiscrudfrm);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		dpContenedor = new JDesktopPane();
		contentPane.add(dpContenedor, BorderLayout.CENTER);
	}
}
