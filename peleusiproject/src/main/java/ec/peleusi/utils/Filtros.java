package ec.peleusi.utils;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JRadioButton;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ec.peleusi.pruebas.LinkViewRadioButtonUI;

public class Filtros {
	
	private Box box;
	private transient TableRowSorter<? extends TableModel> sorter;
	private static final int LR_PAGE_SIZE = 5;
	private DefaultTableModel modelo;
	private static final LinkViewRadioButtonUI LINKVIEW_RADIOBUTTON_UI = new LinkViewRadioButtonUI();

	public Filtros() {
	}
	
	public void paginationBox(final int itemsPerPage, final int currentPageIndex, Box box, DefaultTableModel modelo, TableRowSorter<? extends TableModel> sorter){
		this.box=box;
		this.modelo=modelo;
		this.sorter=sorter;
		initLinkBox(itemsPerPage,currentPageIndex);
		box.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
	}
	private void initLinkBox(final int itemsPerPage, final int currentPageIndex) {
		sorter.setRowFilter(new RowFilter<TableModel, Integer>() {
			@Override
			public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
				int ti = currentPageIndex - 1;
				int ei = entry.getIdentifier();
				return ti * itemsPerPage <= ei && ei < ti * itemsPerPage + itemsPerPage;
			}
		});

		int startPageIndex = currentPageIndex - LR_PAGE_SIZE;
		if (startPageIndex <= 0) {
			startPageIndex = 1;
		}
		int rowCount = modelo.getRowCount();
		int v = rowCount % itemsPerPage == 0 ? 0 : 1;
		int maxPageIndex = rowCount / itemsPerPage + v;
		int endPageIndex = currentPageIndex + LR_PAGE_SIZE - 1;
		if (endPageIndex > maxPageIndex) {
			endPageIndex = maxPageIndex;
		}

		box.removeAll();
		if (startPageIndex >= endPageIndex) {
			return;
		}

		ButtonGroup bg = new ButtonGroup();
		JRadioButton f = makePrevNextRadioButton(itemsPerPage, 1, "|<", currentPageIndex > 1);
		box.add(f);
		bg.add(f);

		JRadioButton p = makePrevNextRadioButton(itemsPerPage, currentPageIndex - 1, "<", currentPageIndex > 1);
		box.add(p);
		bg.add(p);

		box.add(Box.createHorizontalGlue());
		for (int i = startPageIndex; i <= endPageIndex; i++) {
			JRadioButton c = makeRadioButton(itemsPerPage, currentPageIndex, i);
			box.add(c);
			bg.add(c);
		}
		box.add(Box.createHorizontalGlue());

		JRadioButton n = makePrevNextRadioButton(itemsPerPage, currentPageIndex + 1, ">",
				currentPageIndex < maxPageIndex);
		box.add(n);
		bg.add(n);

		JRadioButton l = makePrevNextRadioButton(itemsPerPage, maxPageIndex, ">|", currentPageIndex < maxPageIndex);
		box.add(l);
		bg.add(l);

		box.revalidate();
		box.repaint();
	}

	private JRadioButton makeRadioButton(final int itemsPerPage, int current, final int target) {
		JRadioButton radio = new JRadioButton(String.valueOf(target)) {
			private static final long serialVersionUID = 1L;

			@Override
			protected void fireStateChanged() {
				ButtonModel model = getModel();
				if (model.isEnabled()) {
					if (model.isPressed() && model.isArmed()) {
						setForeground(Color.GREEN);
					} else if (model.isSelected()) {
						setForeground(Color.RED);
						// } else if (isRolloverEnabled() && model.isRollover())
						// {
						// setForeground(Color.BLUE);
					}
				} else {
					setForeground(Color.GRAY);
				}
				super.fireStateChanged();
			}
		};
		radio.setForeground(Color.BLUE);
		radio.setUI(LINKVIEW_RADIOBUTTON_UI);
		if (target == current) {
			radio.setSelected(true);
		}
		radio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initLinkBox(itemsPerPage, target);
			}
		});
		return radio;
	}

	private JRadioButton makePrevNextRadioButton(final int itemsPerPage, final int target, String title, boolean flag) {
		JRadioButton radio = new JRadioButton(title);
		radio.setForeground(Color.BLUE);
		radio.setUI(LINKVIEW_RADIOBUTTON_UI);
		radio.setEnabled(flag);
		radio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initLinkBox(itemsPerPage, target);
			}
		});
		return radio;
	}
	/*
	 * public static void filterModel(JTable tabla, DefaultTableModel modelo,
	 * String query, int[] columnas) { TableRowSorter<DefaultTableModel>
	 * tableRowSorter = new TableRowSorter<DefaultTableModel>(modelo);
	 * tabla.setRowSorter(tableRowSorter);
	 * tableRowSorter.setRowFilter((RowFilter.regexFilter(
	 * Pattern.compile("(?i)" + query, Pattern.CASE_INSENSITIVE |
	 * Pattern.UNICODE_CASE).toString(), columnas))); }
	 */
}
